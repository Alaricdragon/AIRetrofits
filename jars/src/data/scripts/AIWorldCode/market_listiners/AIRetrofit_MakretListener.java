package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.CharacterDataAPI;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.loading.VariantSource;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.CrewReplacer_Log;

import java.util.List;

public class AIRetrofit_MakretListener  extends BaseCampaignEventListener {
    static String Condition = "AIRetrofit_AIPop";
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
        if(market.hasCondition("AIRetrofit_AIPop")){
            changePeople(market);
        }
        addAIRetrofits();
        unapplySubMarkets(market);
        /*AIRetrofit_StartAutomatedColony.markets.size();
        for(int a = 0; a < AIRetrofit_StartAutomatedColony.markets.size(); a++){
            if(changePeople(Global.getSector().getEconomy().getMarket(AIRetrofit_StartAutomatedColony.markets.get(a)))){
                AIRetrofit_StartAutomatedColony.markets.remove(a);
                a--;
            }
        }*/
    }
    private boolean changePeople(MarketAPI market){
        List<PersonAPI> peopletemp = market.getPeopleCopy();
        if(peopletemp.size() > 1){
            for (PersonAPI person : peopletemp) {
                if(!market.getAdmin().equals(person) && market.getFaction().getId().equals(person.getFaction().getId())) {
                    market.removePerson(person);
                    person.setPortraitSprite(setDataLists.getRandom(2));
                    person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
                    market.addPerson(person);
                }
            }
            return true;
        }
        return false;
    }
    static String hullmod = "AIretrofit_AutomatedCrewReplacementDrones";
    static String hullmod2 = "AIretrofit_airetrofit";
    static String ability = "AIretrofit_robot_drone_forge";
    static String skill = "automated_ships";
    static boolean alwaysSkilled = Global.getSettings().getBoolean("AIRetrofit_alwaysGiveSkillsAndHullmods");
    private void addAIRetrofits(){
        CharacterDataAPI character = Global.getSector().getCharacterData();
        if(character.getPerson().getStats().hasSkill(skill) || alwaysSkilled){
            character.addAbility(ability);
            character.addHullMod(hullmod);
            character.addHullMod(hullmod2);
            return;
        }
        addReqAbility();
    }
    private void addReqAbility(){
        CharacterDataAPI character = Global.getSector().getCharacterData();
        for(String a: character.getAbilities()){
            if(a.equals(ability)){
                return;
            }
        }
        for(String a: character.getHullMods()) {
            if (a.equals(hullmod)) {
                character.addAbility(ability);
                return;
            }
        }
    }
    final static String shipYardIndustry = "AIRetrofit_shipYard";
    final static String shipYardSubmarket = "AIRetrofit_ShipyardSubmarket";
    static float shipyard_IValue = Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    static float shipyardDValue = Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");
    static float[] shipyard_costPerShip = {
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewDEFAULT"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewFIGHTER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewFRIGATE"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewDESTROYER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewCRUISER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewCAPITAL_SHIP"),
    };
    private void runAIRetrofit_Shipyard(){
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
            runSingleAIRetrofit_Shipyard(market);
        }
    }
    //boolean logging = true;
    private void runSingleAIRetrofit_Shipyard(MarketAPI market){
        market = Global.getSector().getEconomy().getMarket(market.getId());
        if(!market.hasIndustry(shipYardIndustry) || !market.hasSubmarket(shipYardSubmarket) || (market.hasIndustry(shipYardIndustry) && !market.getIndustry(shipYardIndustry).isFunctional())){
            return;
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
                "AIretrofit_airetrofit"};
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
        try {
            //CrewReplacer_Log.loging("   trying to get AI core...",this,logging);
            switch (market.getIndustry(shipYardIndustry).getAICoreId()) {
                case "gamma_core":
                    //CrewReplacer_Log.loging("       got AICore named 'gamma_core'",this,logging);
                    addHullMod = addHullMods[0];
                    break;
                case "beta_core":
                    //CrewReplacer_Log.loging("       got AICore named 'beta core'",this,logging);
                    addHullMod = addHullMods[1];
                    break;
                case "alpha_core":
                    //CrewReplacer_Log.loging("       got AICore named 'alpha core'",this,logging);
                    addHullMod = addHullMods[2];
                    break;
                case "omega_core"://are omega cores even obtainable?
                    //CrewReplacer_Log.loging("       got AICore named 'omega core'",this,logging);
                    addHullMod = addHullMods[3];
                    break;
                default:
                    //CrewReplacer_Log.loging("       didn't get AI core'",this,logging);
                    addHullMod = addHullMods[4];
                    break;
            }
        }catch (Exception e){
            //CrewReplacer_Log.loging("       ERROR! no AI core gave a error",this,logging);
            addHullMod = addHullMods[4];
        }
        //CrewReplacer_Log.loging("   looking at ships... ",this,logging);
        for(FleetMemberAPI ship2 : ships){
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
                switch (ship.getHullSize()){
                    case DEFAULT:
                        cost = costs[0];
                        break;
                    case FIGHTER:
                        cost = costs[1];
                        break;
                    case FRIGATE:
                        cost = costs[2];
                        break;
                    case DESTROYER:
                        cost = costs[3];
                        break;
                    case CRUISER:
                        cost = costs[4];
                        break;
                    case CAPITAL_SHIP:
                        cost = costs[5];
                        break;
                }
                //CrewReplacer_Log.loging("           ship costs '" + cost + "' ponits remaining '" + points + "'",this,logging);
                if(cost <= points){
                    //CrewReplacer_Log.loging("          adding hullmod '" + addHullMod + "'",this,logging);
                    points -= cost;
                    for(String a : addHullMods){
                        ship.removeMod(a);
                        ship.removePermaMod(a);
                    }
                    ship.addMod(addHullMod);
                }
            }
            ship2.setVariant(ship,true,true);
            if(points <= 0){
                //CrewReplacer_Log.loging("           out of points. ending loop",this,logging);
                return;
            }
        }
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
}
