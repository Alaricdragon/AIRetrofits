package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.hullmods.AIretrofit;

public class AIRetrofit_ShipyardAlpha  extends BaseHullMod {
    final static String cantRemoveReason = "cannot be added or removed outside of a robotic shipyard";
    final static String industry = "AIRetrofit_shipYard";
    final static String name = "AIRetrofit_ShipyardAlpha";
    private static final float SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;

    private static float[] CrewPerCostPerSize = {
            Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Other"),
            Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Frigate"),
            Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Destroyer"),
            Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Cruiser"),
            Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Capital_ship")
    };
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float SupplyIncrease = stats.getSuppliesPerMonth().getBaseValue() * SUPPLY_USE_MULT;
        stats.getSuppliesPerMonth().modifyFlat(id,SupplyIncrease);
        stats.getMinCrewMod().modifyMult(id,CREW_USE_MULT);
        stats.getMaxCrewMod().modifyFlat(id,MinCrew * -1);
        stats.getCombatEngineRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);
        stats.getCombatWeaponRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);


        int exstra_cost = GetExstraOpCost(MinCrew,hullSize);
        addExstraOpCost(exstra_cost,stats);

    }
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        //need to return extra opp cost somehow... all attempts have thus far failed... something needs to be dones..
        switch(index) {
            case 0:
                return "";
            case 1:
                return "";
            case 2:
                return "";
            case 3:
                return "";
            case 4:
                return "";
            case 5:
                return "";
            case 6:
                return "";
            case 7:
                return "";
            case 8:
                return "";
            case 9:
                return "";
        }
        return null;
    }
    //prevents the hullmod from being removed by the player
    @Override
    public boolean canBeAddedOrRemovedNow(ShipAPI ship, MarketAPI marketOrNull, CampaignUIAPI.CoreUITradeMode mode){
        if(ship != null && (ship.getFleetMember().getFleetData().getCommander().isPlayer())){
            return false;
        }
        /*if(marketOrNull != null){// && (marketOrNull.hasIndustry(industry) && marketOrNull.getIndustry(industry).isFunctional())){
            return true;
        }*/
        return true;
    }
    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        return cantRemoveReason;
    }
    @Override
    public boolean isApplicableToShip(ShipAPI ship/*, MutableCharacterStatsAPI wat*/){
        return true;//ship != null && true;
    }
    private void addExstraOpCost(int exstra_cost,MutableShipStatsAPI stats){
        //example of adding a hullmod
        //stats.getVariant().addMod("mymod_temp0");
        //d.0)
        int b;
        String temp;
        //d.1)
        for(int a = 4096; a >= 1 && exstra_cost > 0; a = a / 2){
            //d.2)
            //d.3)
            //d.3.1)
            if(a <= exstra_cost) {//if extra cost is >= to this number it will need to be added anyways.
                exstra_cost -= a;
                temp = "AIretrofit_AIretrofitAplha_opRemove" + a;
                stats.getVariant().addMod(temp);
            }
        }
    }
    private int GetExstraOpCost(float crew, ShipAPI.HullSize hullSize){
		/*if(hullSize == HullSize.FIGHTER || hullSize == HullSize.DEFAULT){
			//crew = CrewPerCostPerSize[0];//1 cost per
		}else */if(hullSize == ShipAPI.HullSize.FRIGATE){
            //crew = crew / CrewPerCostPerSize[1];
            return (int) (crew * CrewPerCostPerSize[1]);
        }else if(hullSize == ShipAPI.HullSize.DESTROYER){
            //crew = crew / CrewPerCostPerSize[2];
            return (int) (crew * CrewPerCostPerSize[2]);
        }else if(hullSize == ShipAPI.HullSize.CRUISER){
            //crew = crew / CrewPerCostPerSize[3];
            return (int) (crew * CrewPerCostPerSize[3]);
        }else if(hullSize == ShipAPI.HullSize.CAPITAL_SHIP){
            return (int) (crew * CrewPerCostPerSize[4]);
        }
        return (int) (crew * CrewPerCostPerSize[0]);
    }

}
