package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.loading.VariantSource;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.AIRetrofits_AbilityAndHullmodAdding;
import data.scripts.AIWorldCode.AIRetrofits_ChangePeople;
import data.scripts.memory.AIRetrofit_ItemFoundMemory;
import data.scripts.memory.AIRetrofits_ItemInCargoMemory;
import data.scripts.notifications.AIRetrofit_ShipyardNotification;
import data.scripts.notifications.ShipyardUpgradeData.AIRetrofit_Shipyard_UpgradeList;
import data.scripts.notifications.ShipyardUpgradeData.AIRetrofit_Shipyard_UpgradeShips;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.List;

public class AIRetrofit_MakretListener  extends BaseCampaignEventListener {
    public AIRetrofit_MakretListener(boolean permaRegister) {
        super(permaRegister);
    }
    @Override
    public void reportEconomyMonthEnd(){
        runAIRetrofit_Shipyard();
    }
    @Override
    public void reportPlayerOpenedMarket(MarketAPI market){
        runAIRetrofit_Shipyard();
        changePeople(market);
        AIRetrofits_AbilityAndHullmodAdding.addAIRetrofits();
        AIRetrofits_ItemInCargoMemory.runall();
        AIRetrofit_ItemFoundMemory.changeMemory();
        try {
            if (market != null && !market.getFaction().isNeutralFaction() && market.getFaction().getRelationshipLevel(Global.getSector().getPlayerFaction()).isAtWorst(RepLevel.SUSPICIOUS)) {
                //AIRetrofit_Log.loging("faction of world im at is: "+market.getFaction().getId(),this,true);
                AIRetrofits_AbilityAndHullmodAdding.swapPatchworkForAIRetrofit();
            }
        }catch (Exception e){

        }
        unapplySubMarkets(market);
    }

    @Override
    public void reportPlayerClosedMarket(MarketAPI market) {
        super.reportPlayerClosedMarket(market);
        /*if (market.hasSubmarket(AIRetrofits_Constants_3.Submarket_AINodeProductionFacility)){
            BaseSubmarketPlugin b = (BaseSubmarketPlugin)market.getSubmarket(AIRetrofits_Constants_3.Submarket_AINodeProductionFacility);
            AIRetrofit_AINodeProduction_Submarket a = (AIRetrofit_AINodeProduction_Submarket)b;
            a.backupCargo();
        }*/
    }

    private void changePeople(MarketAPI market){
        AIRetrofits_ChangePeople.changePeopleMarket(market);
    }
    final static String shipYardIndustry = AIRetrofits_Constants_3.ASIC_shipYardIndustry;//"AIRetrofit_shipYard";
    final static String shipYardSubmarket = AIRetrofits_Constants_3.ASIC_subbmarket;//"AIRetrofit_ShipyardSubmarket";
    static float shipyard_IValue = AIRetrofits_Constants_3.ASIC_improveValue;//Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    static float shipyardDValue = AIRetrofits_Constants_3.ASIC_defaultValue;//Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");
    static float[] shipyard_costPerShip = AIRetrofits_Constants_3.ASIC_costPerShip;
    private void runAIRetrofit_Shipyard(){
        AIRetrofit_Shipyard_UpgradeList memory = new AIRetrofit_Shipyard_UpgradeList();
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
            memory.addLocation(market.getId(),runSingleAIRetrofit_Shipyard(market));
        }
        memory.applyCosts();
        displayAIRetrofit_ShipYardNotification(memory);
    }
    //boolean logging = true;
    private AIRetrofit_Shipyard_UpgradeShips runSingleAIRetrofit_Shipyard(MarketAPI market){
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
                AIRetrofits_Constants_3.Hullmod_AIRetrofit,
                AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit};
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
        AIRetrofit_Shipyard_UpgradeShips upgraded = new AIRetrofit_Shipyard_UpgradeShips(type);
        //CrewReplacer_Log.loging("   looking at ships... ",this,logging);
        for(int ship22 = 0; ship22 < ships.size(); ship22++){
            FleetMemberAPI ship2 = ships.get(ship22);
            //CrewReplacer_Log.loging("       looking at ship " + ship2.getShipName(),this,logging);
            ShipVariantAPI ship = ship2.getVariant().clone();
            ship.setSource(VariantSource.REFIT);
            //ship2.getVariant().get
            boolean exit = false;
            for(String a : stopHullMods) {
                if (ship.getPermaMods().contains(a)) {
                //if(ship.hasHullMod(a)) {
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
                    if(ship.getPermaMods().contains(AIRetrofits_Constants_3.Hullmod_AIRetrofit)){
                        upgraded.addShip(ship2,size,true);
                    }else{
                        upgraded.addShip(ship2,size);
                    }
                    for(String a : addHullMods){
                        ship.removeMod(a);
                        ship.removePermaMod(a);
                        //AIRetrofit_Log.loging("trying to remove hullmod: " + a,this,true);
                    }
                    ship.addPermaMod(addHullMod);
                    //ship.addMod(addHullMod);
                }
            }
            ship2.setVariant(ship,false,true);
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
        if(market.hasSubmarket(AIRetrofits_Constants_3.Submarket_AINodeProductionFacility) && !market.hasIndustry(AIRetrofits_Constants_3.Industry_AINodeProductionFacility)){
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
            market.removeSubmarket(AIRetrofits_Constants_3.Submarket_AINodeProductionFacility);
        }
    }

    private void displayAIRetrofit_ShipYardNotification(AIRetrofit_Shipyard_UpgradeList memory){
        if(memory.Types.size() == 0){
            upgrades = null;
            return;
        }
        upgrades = memory;
        //memory.runNotification();
        Global.getSector().getIntelManager().addIntel(new AIRetrofit_ShipyardNotification(upgrades));
        //Global.getSector().getCharacterData().getPerson().getStats().addBonusXP();
    }

    public static AIRetrofit_Shipyard_UpgradeList upgrades;
    public static void displayAIRetrofit_ShipYardNotification(TooltipMakerAPI info,AIRetrofit_Shipyard_UpgradeList list){
        //
        try {
            list.display(info);
        }catch (Exception e){

        }
    }
}