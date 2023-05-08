package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.plog.PlaythroughLog;
import com.fs.starfarer.api.impl.campaign.plog.SModRecord;
import com.fs.starfarer.api.loading.VariantSource;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Highlights;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIRetrofits_AbilityAndHullmodAdding;
import data.scripts.AIWorldCode.AIRetrofits_ChangePeople;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.hullmods.AIRetrofit_AIretrofit;
import data.scripts.notifications.AIRetrofit_ShipyardNotification;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static data.scripts.startupData.AIRetrofits_Constants.ASIC_hullmods;

public class AIRetrofit_MakretListener  extends BaseCampaignEventListener {
    public AIRetrofit_MakretListener(boolean permaRegister) {
        super(permaRegister);
    }
    //static String look1 = Global.getSettings().getString("characters","AIRetofit_AIOfficer");
    @Override
    public void reportEconomyMonthEnd(){
        runAIRetrofit_Shipyard();
    }
    @Override
    public void reportPlayerOpenedMarket(MarketAPI market){
        changePeople(market);
        AIRetrofits_AbilityAndHullmodAdding.addAIRetrofits();
        AIRetrofits_AbilityAndHullmodAdding.swapPatchworkForAIRetrofit();
        unapplySubMarkets(market);
    }
    private void changePeople(MarketAPI market){
        AIRetrofits_ChangePeople.changePeopleMarket(market);
    }
    final static String shipYardIndustry = AIRetrofits_Constants.ASIC_shipYardIndustry;//"AIRetrofit_shipYard";
    final static String shipYardSubmarket = AIRetrofits_Constants.ASIC_subbmarket;//"AIRetrofit_ShipyardSubmarket";
    static float shipyard_IValue = AIRetrofits_Constants.ASIC_improveValue;//Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    static float shipyardDValue = AIRetrofits_Constants.ASIC_defaultValue;//Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");
    static float[] shipyard_costPerShip = AIRetrofits_Constants.ASIC_costPerShip;
    private void runAIRetrofit_Shipyard(){
        UpgradeList memory = new UpgradeList();
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
            memory.addLocation(market.getId(),runSingleAIRetrofit_Shipyard(market));
        }
        memory.applyCosts();
        displayAIRetrofit_ShipYardNotification(memory);
    }
    //boolean logging = true;
    private UpgradedShips runSingleAIRetrofit_Shipyard(MarketAPI market){
        market = Global.getSector().getEconomy().getMarket(market.getId());
        if(!market.hasIndustry(shipYardIndustry) || !market.hasSubmarket(shipYardSubmarket) || (market.hasIndustry(shipYardIndustry) && !market.getIndustry(shipYardIndustry).isFunctional())){
            return null;
        }/*else{
            int[] a = {};
            a[1] = a[2];//crash the game. as an console log lol.
        }*/
        //CrewReplacer_Log.loging("running  runSingleAIRetrofit_Shipyard for market: " + market.getName(),this,logging);
        final String[] stopHullMods = {"automated"};
        final String[] addHullMods = {
                "AIRetrofit_ShipyardGamma",
                "AIRetrofit_ShipyardBeta",
                "AIRetrofit_ShipyardAlpha",
                "AIRetrofit_ShipyardOmega",
                "AIRetrofit_ShipyardBase",
                AIRetrofits_Constants.Hullmod_AIRetrofit,
                AIRetrofits_Constants.Hullmod_PatchworkAIRetrofit};
        final float startingPonits = shipyardDValue;
        final float bounus = 1 + shipyard_IValue;
        final float[] costs = shipyard_costPerShip;
        List<FleetMemberAPI> ships = market.getSubmarket(shipYardSubmarket).getCargo().getMothballedShips().getMembersListCopy();
        //market.getSubmarket(shipYardSubmarket).getCargo().getMothballedShips().get
        //market.getSubmarket(shipYardSubmarket).getCargo().

        float points = startingPonits;
        String addHullMod;
        if(market.getIndustry(shipYardIndustry).isImproved()){
            points *= bounus;
        }
        int type;
        try {
            //CrewReplacer_Log.loging("   trying to get AI core...",this,logging);
            switch (market.getIndustry(shipYardIndustry).getAICoreId()) {
                case "gamma_core":
                    //CrewReplacer_Log.loging("       got AICore named 'gamma_core'",this,logging);
                    addHullMod = addHullMods[0];
                    type = 0;
                    break;
                case "beta_core":
                    //CrewReplacer_Log.loging("       got AICore named 'beta core'",this,logging);
                    addHullMod = addHullMods[1];
                    type = 1;
                    break;
                case "alpha_core":
                    //CrewReplacer_Log.loging("       got AICore named 'alpha core'",this,logging);
                    addHullMod = addHullMods[2];
                    type = 2;
                    break;
                case "omega_core"://are omega cores even obtainable?
                    //CrewReplacer_Log.loging("       got AICore named 'omega core'",this,logging);
                    addHullMod = addHullMods[3];
                    type = 3;
                    break;
                default:
                    //CrewReplacer_Log.loging("       didn't get AI core'",this,logging);
                    addHullMod = addHullMods[4];
                    type = 4;
                    break;
            }
        }catch (Exception e){
            //CrewReplacer_Log.loging("       ERROR! no AI core gave a error",this,logging);
            addHullMod = addHullMods[4];
            type = 4;
        }
        UpgradedShips upgraded = new UpgradedShips(type);
        //CrewReplacer_Log.loging("   looking at ships... ",this,logging);
        for(int ship22 = 0; ship22 < ships.size(); ship22++){
            FleetMemberAPI ship2 = ships.get(ship22);
            //CrewReplacer_Log.loging("       looking at ship " + ship2.getShipName(),this,logging);
            ShipVariantAPI ship = ship2.getVariant().clone();
            ship.setSource(VariantSource.REFIT);
            //ship2.getVariant().get
            boolean exit = false;
            for(String a : stopHullMods) {
                if(ship.hasHullMod(a)) {
                    //CrewReplacer_Log.loging("           abort: bad hull mod.",this,logging);
                    exit = true;
                    break;
                }
            }
            if(!exit && !ship.hasHullMod(addHullMod)){
                float cost = 0;
                int size = 0;
                switch (ship.getHullSize()){
                    case DEFAULT:
                        cost = costs[0];
                        size = 0;
                        break;
                    case FIGHTER:
                        cost = costs[1];
                        size = 1;
                        break;
                    case FRIGATE:
                        cost = costs[2];
                        size = 2;
                        break;
                    case DESTROYER:
                        cost = costs[3];
                        size = 3;
                        break;
                    case CRUISER:
                        cost = costs[4];
                        size = 4;
                        break;
                    case CAPITAL_SHIP:
                        cost = costs[5];
                        size = 5;
                        break;
                }
                //CrewReplacer_Log.loging("           ship costs '" + cost + "' ponits remaining '" + points + "'",this,logging);
                if(cost <= points){
                    //CrewReplacer_Log.loging("          adding hullmod '" + addHullMod + "'",this,logging);
                    points -= cost;
                    if(ship.getPermaMods().contains(AIRetrofits_Constants.Hullmod_AIRetrofit)){
                        upgraded.addShip(ship2,size,true);
                    }else{
                        upgraded.addShip(ship2,size);
                    }
                    for(String a : addHullMods){
                        ship.removeMod(a);
                        ship.removePermaMod(a);
                        //AIRetrofit_Log.loging("trying to remove hullmod: " + a,this,true);
                    }
                    ship.addMod(addHullMod);
                }
            }
            ship2.setVariant(ship,true,true);
            if(points <= 0){
                //CrewReplacer_Log.loging("           out of points. ending loop",this,logging);
                break;
            }
        }
        return upgraded;
    }
    private void unapplySubMarkets(MarketAPI market){
        final String storge = "storage";
        if(market.hasSubmarket(shipYardSubmarket) && !market.hasIndustry(shipYardIndustry) && market.getSubmarket(shipYardSubmarket).getCargo().getMothballedShips().getMembersListCopy().size() == 0){
            /*if (!market.hasSubmarket(storge)){
                market.addSubmarket(storge);
            }
            FleetDataAPI ships = market.getSubmarket(shipYardSubmarket).getCargo().getMothballedShips();
            ships.getMembersListCopy()
            for(FleetMemberAPI ship2 : ships.getMembersListCopy()){
                ShipVariantAPI ship = ship2.getVariant().clone();
                ship.setSource(VariantSource.REFIT);
                market.getSubmarket(storge).getCargo().addMothballedShip(ship,ship.getHullVariantId(),ship.getDisplayName());
            }*/
            market.removeSubmarket(shipYardSubmarket);
        }
    }

    private void displayAIRetrofit_ShipYardNotification(UpgradeList memory){
        if(memory.Types.size() == 0){
            upgrades = null;
            return;
        }
        upgrades = memory;
        //memory.runNotification();
        Global.getSector().getIntelManager().addIntel(new AIRetrofit_ShipyardNotification());
        //Global.getSector().getCharacterData().getPerson().getStats().addBonusXP();
    }

    public static UpgradeList upgrades;
    public static void displayAIRetrofit_ShipYardNotification(TooltipMakerAPI info){
        //
        upgrades.display(info);
    }
}



class UpgradeList{
    ArrayList<UpgradeTypes> Types = new ArrayList<>();
    public void addLocation(String market,UpgradedShips ships){
        if(ships == null || ships.ships.size() == 0){return;}
        while(Types.size() <= ships.type){
            Types.add(new UpgradeTypes(Types.size()));
        }
        Types.get(ships.type).addLocation(market,ships);
    }
    public void runNotification(){
        AIRetrofit_ShipyardNotification intel = new AIRetrofit_ShipyardNotification();

        Global.getSector().getCampaignUI().addMessage(intel);
    }
    public void applyCosts(){
        if(Types.size() == 0){
            return;
        }
        float cost = 0;
        float bonusXP = 0;
        for(int a2 = 0; a2 < Types.size(); a2++){
            UpgradeTypes a = Types.get(a2);
            //info.addPara("upgraded with " + AIRetrofits_Constants.ASIC_hullmods[a.type],10);
            float[] temp = a.getCost();
            cost += temp[0];
            bonusXP += temp[1];
        }

        if(cost != 0){
            Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(cost);
        }
        if(bonusXP != 0){
            long XpPerStory = Global.getSector().getPlayerStats().getBonusXPForSpendingStoryPointBeforeSpendingIt();
            bonusXP*=XpPerStory;
            TextPanelAPI a = new TempText();//info.addTextField(0,0);
            Global.getSector().getPlayerStats().addBonusXP((long)bonusXP,false,a,true);
        }
    }
    public void display(TooltipMakerAPI info){
        if(Types.size() == 0){
            return;
        }
        float pad = 5;
        float cost = 0;
        float bonusXP = 0;
        Color highlight = Misc.getHighlightColor();
        for(int a2 = 0; a2 < Types.size(); a2++){
            UpgradeTypes a = Types.get(a2);
            //info.addPara("upgraded with " + AIRetrofits_Constants.ASIC_hullmods[a.type],10);
            float[] temp = a.display(info);
            cost += temp[0];
            bonusXP += temp[1];
        }

        if(cost != 0){
            String[] exstra = new String[]{"" + cost};
            String text = AIRetrofits_Constants.ASIC_NotificationCredits;
            info.addPara(text,pad,highlight,exstra);
            //Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(cost);
        }
        if(bonusXP != 0){
            long XpPerStory = Global.getSector().getPlayerStats().getBonusXPForSpendingStoryPointBeforeSpendingIt();
            bonusXP*=XpPerStory;
            highlight = Misc.getStoryOptionColor();
            AIRetrofit_Log.loging( bonusXP + " bonusXP from AIRetrofitShipyard",this,true);
            String[] exstra = new String[]{"" + (int)bonusXP};
            String text = AIRetrofits_Constants.ASIC_NotificationBonusXP;
            info.addPara(text,pad,highlight,exstra);
            //TextPanelAPI a = new TempText();//info.addTextField(0,0);
            //Global.getSector().getPlayerStats().addBonusXP((long)bonusXP,false,a,true);
        }
    }
}
class UpgradeTypes{
    int type;
    ArrayList<UpgradedShips> upgrades = new ArrayList<UpgradedShips>();
    ArrayList<String> markets = new ArrayList<String>();

    UpgradeTypes(int type){
        this.type = type;
    }
    public void addLocation(String market,UpgradedShips ships){
        if(ships == null || ships.ships.size() == 0){return;}
        upgrades.add(ships);
        markets.add(market);
    }
    public float[] display(TooltipMakerAPI info){
        float pad = 5;
        float cost = 0;
        float bonusXP = 0;
        Color highlight = Misc.getHighlightColor();
        for(int a = 0; a < upgrades.size(); a++){
            boolean b = Global.getSector().getEconomy().getMarket(markets.get(a)).isPlayerOwned();
            String[] exstra = {Global.getSector().getEconomy().getMarket(markets.get(a)).getName()};
            String text = AIRetrofits_Constants.ASIC_NotificationMarket;
            info.addPara(text,pad,highlight,exstra);
            float[] temp = upgrades.get(a).display(info,type,b);
            cost += temp[0];
            bonusXP += temp[1];
        }
        return new float[]{cost,bonusXP};
    }
    public float[] getCost(){

        float cost = 0;
        float bonusXP = 0;
        for(int a = 0; a < upgrades.size(); a++){
            boolean b = Global.getSector().getEconomy().getMarket(markets.get(a)).isPlayerOwned();
            float[] temp = upgrades.get(a).getCost(b);
            cost += temp[0];
            bonusXP += temp[1];
        }
        return new float[]{cost,bonusXP};
    }
}
class UpgradedShips{
    int type;
    ArrayList<UpgradedShip> ships = new ArrayList<>();
    UpgradedShips(int type){
        this.type = type;
    }
    public void addShip(FleetMemberAPI ship, int size){
        addShip(ship,size,false);
    }
    public void addShip(FleetMemberAPI ship, int size,boolean bonus){
        ships.add(new UpgradedShip(ship,size,bonus));
    }
    public float[] display(TooltipMakerAPI info,int type,boolean playerOwned){
        if(ships.size() == 0){
            return new float[]{0f,0f};
        }
        float pad = 5;
        Color highlight = Misc.getHighlightColor();

        //CargoAPI cargo = new CargoData(false);
        //FleetDataAPI fleet = new FleetData("production","production2");
        ArrayList<FleetMemberAPI> fleet = new ArrayList<>();
        String[] exstra = {"" + Global.getSettings().getHullModSpec(ASIC_hullmods[type]).getDisplayName()};
        String text = AIRetrofits_Constants.ASIC_NotificationType;
        info.addPara(text,pad,highlight,exstra);
        float cost = 0;
        float bonusXP = 0;
        for(int a = 0; a < ships.size(); a++){
            UpgradedShip ship = ships.get(a);
            ship.addShipToFleet(fleet);
            if(!playerOwned) {
                cost += ship.getCost();
            }
            bonusXP += ship.getBonusXP();

        }
        info.showShips(fleet, 20, true, 5);
        return new float[]{cost,bonusXP};
    }
    public float[] getCost(boolean playerOwned){

        if(ships.size() == 0){
            return new float[]{0f,0f};
        }
        float pad = 5;
        Color highlight = Misc.getHighlightColor();

        //CargoAPI cargo = new CargoData(false);
        //FleetDataAPI fleet = new FleetData("production","production2");
        ArrayList<FleetMemberAPI> fleet = new ArrayList<>();
        float cost = 0;
        float bonusXP = 0;
        for(int a = 0; a < ships.size(); a++){
            UpgradedShip ship = ships.get(a);
            ship.addShipToFleet(fleet);
            if(!playerOwned) {
                cost += ship.getCost();
            }
            bonusXP += ship.getBonusXP();

        }
        return new float[]{cost,bonusXP};
    }
}
class UpgradedShip{
    FleetMemberAPI ship;
    boolean bonus;
    int size;
    static float storyGain = Global.getSettings().getFloat("AIRetrofitShipyard_storyPointsPerSMod");
    UpgradedShip(FleetMemberAPI ship,int size,boolean bonus){
        this.ship = ship;
        this.bonus = bonus;
        this.size = size;
    }
    public void addShipToFleet(ArrayList<FleetMemberAPI> fleet){
        fleet.add(ship);
        //cargo.addMothballedShip();//(ship);
    }
    public float getCost(){
        float[] costTemp = AIRetrofits_Constants.ASIC_creditsPerShip;
        return costTemp[size];
    }
    public float getBonusXP(){
        if(bonus){
            float xp = 1 - Misc.getBuildInBonusXP(Misc.getMod(AIRetrofits_Constants.Hullmod_AIRetrofit),ship.getHullSpec().getHullSize());
            return xp * storyGain;//Misc.getBonusXPForScuttling(ship)[0] * getBonusXPForScuttling(ship)[1];
        }
        return 0f;//AIRetrofits_Constants.ASIC_bonusXPForRemoveSMod;
    }
    public void display(TooltipMakerAPI info,boolean playerOwned){
    }
}
class TempText implements TextPanelAPI{
    @Override
    public void setFontInsignia() {

    }

    @Override
    public void setFontOrbitron() {

    }

    @Override
    public void setFontVictor() {

    }

    @Override
    public void setFontSmallInsignia() {

    }

    @Override
    public LabelAPI addPara(String text) {
        return null;
    }

    @Override
    public LabelAPI addPara(String text, Color color) {
        return null;
    }

    @Override
    public LabelAPI addParagraph(String text) {
        return null;
    }

    @Override
    public LabelAPI addParagraph(String text, Color color) {
        return null;
    }

    @Override
    public void replaceLastParagraph(String text) {

    }

    @Override
    public void replaceLastParagraph(String text, Color color) {

    }

    @Override
    public void appendToLastParagraph(String text) {

    }

    @Override
    public void appendToLastParagraph(int charsToCut, String text) {

    }

    @Override
    public void highlightFirstInLastPara(String text, Color color) {

    }

    @Override
    public void highlightLastInLastPara(String text, Color color) {

    }

    @Override
    public void highlightInLastPara(Color color, String... strings) {

    }

    @Override
    public void highlightInLastPara(String... strings) {

    }

    @Override
    public void setHighlightColorsInLastPara(Color... colors) {

    }

    @Override
    public void clear() {

    }

    @Override
    public InteractionDialogAPI getDialog() {
        return null;
    }

    @Override
    public boolean isOrbitronMode() {
        return false;
    }

    @Override
    public void setOrbitronMode(boolean orbitronMode) {

    }

    @Override
    public ResourceCostPanelAPI addCostPanel(String title, float height, Color color, Color dark) {
        return null;
    }

    @Override
    public void setHighlightsInLastPara(Highlights h) {

    }

    @Override
    public LabelAPI addPara(String format, Color color, Color hl, String... highlights) {
        return null;
    }

    @Override
    public LabelAPI addPara(String format, Color hl, String... highlights) {
        return null;
    }

    @Override
    public void advance(float amount) {

    }

    @Override
    public TooltipMakerAPI beginTooltip() {
        return null;
    }

    @Override
    public void addTooltip() {

    }

    @Override
    public void updateSize() {

    }

    @Override
    public boolean addCostPanel(String title, Color color, Color dark, Object... params) {
        return false;
    }

    @Override
    public boolean addCostPanel(String title, Object... params) {
        return false;
    }

    @Override
    public void addSkillPanel(PersonAPI person, boolean admin) {

    }

    @Override
    public void setFontOrbitronUnnecessarilyLarge() {

    }

    @Override
    public void addImage(String category, String key) {

    }

    @Override
    public void addImage(String spriteName) {

    }
};