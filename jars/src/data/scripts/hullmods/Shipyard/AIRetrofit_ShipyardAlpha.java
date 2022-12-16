package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class AIRetrofit_ShipyardAlpha  extends AIRetrofit_ShipyardBase {
    final static String automationLevel = "Alpha-Core";
    final static String cantRemoveReason = "cannot be added or removed outside of a robotic shipyard";
    final static String industry = "AIRetrofit_shipYard";
    final static String name = "AIRetrofit_ShipyardAlpha";
    private static final float SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;

    private String[] parm = {"0","1","2","3","4","5","6","7","8","9","10"};

    private static final float[] maxOp = {
            Global.getSettings().getFloat("AIRetrofits_" + name + "_MaxOpOther"),
            Global.getSettings().getFloat("AIRetrofits_" + name + "_MaxOpFrigate"),
            Global.getSettings().getFloat("AIRetrofits_" + name + "_MaxOpDestroyer"),
            Global.getSettings().getFloat("AIRetrofits_" + name + "_MaxOpCruiser"),
            Global.getSettings().getFloat("AIRetrofits_" + name + "_MaxOpCapitalShip")
    };
    private static final float[] CrewPerCostPerSize = {
            Global.getSettings().getFloat("AIRetrofits_" + name + "_C-OP-Other"),
            Global.getSettings().getFloat("AIRetrofits_" + name + "_C-OP-Frigate"),
            Global.getSettings().getFloat("AIRetrofits_" + name + "_C-OP-Destroyer"),
            Global.getSettings().getFloat("AIRetrofits_" + name + "_C-OP-Cruiser"),
            Global.getSettings().getFloat("AIRetrofits_" + name + "_C-OP-Capital_ship")
    };
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = stats.getVariant().getHullSpec().getMaxCrew();
        float SupplyIncrease = stats.getSuppliesPerMonth().getBaseValue() * SUPPLY_USE_MULT;
        stats.getSuppliesPerMonth().modifyFlat(id,SupplyIncrease);
        stats.getMinCrewMod().modifyMult(id,CREW_USE_MULT);
        stats.getMaxCrewMod().modifyMult(id,CREW_USE_MULT);
        stats.getCombatEngineRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);
        stats.getCombatWeaponRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);
        int exstra_cost = GetExstraOpCost(MaxCrew - MinCrew,hullSize);
        addExstraOpCost(exstra_cost,stats);

    }
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        if(index < parm.length) {
            return parm[index];
        }
        return null;
    }
    //prevents the hullmod from being removed by the player
    @Override
    public boolean canBeAddedOrRemovedNow(ShipAPI ship, MarketAPI marketOrNull, CampaignUIAPI.CoreUITradeMode mode){
        setDisplayValues(ship);
        return super.canBeAddedOrRemovedNow(ship,marketOrNull,mode);
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
            return (int) Math.min(crew * CrewPerCostPerSize[1],maxOp[1]);
        }else if(hullSize == ShipAPI.HullSize.DESTROYER){
            //crew = crew / CrewPerCostPerSize[2];
            return (int) Math.min(crew * CrewPerCostPerSize[2],maxOp[2]);
        }else if(hullSize == ShipAPI.HullSize.CRUISER){
            //crew = crew / CrewPerCostPerSize[3];
            return (int) Math.min(crew * CrewPerCostPerSize[3],maxOp[3]);
        }else if(hullSize == ShipAPI.HullSize.CAPITAL_SHIP){
            return (int) Math.min(crew * CrewPerCostPerSize[4],maxOp[4]);
        }
        return (int) Math.min(crew * CrewPerCostPerSize[0],maxOp[0]);
    }


    private void setDisplayValues(ShipAPI ship){
        if(ship == null){
            return;
        }
        float MinCrew = ship.getMutableStats().getVariant().getHullSpec().getMinCrew();
        float MaxCrew = ship.getMutableStats().getVariant().getHullSpec().getMaxCrew();

        ShipAPI.HullSize hullsize = ship.getVariant().getHullSize();
        int cost = GetExstraOpCost(MaxCrew - MinCrew,hullsize);
        parm[0] = automationLevel;
        parm[1] = "" + SUPPLY_USE_MULT * 100 + "%";
        parm[2] = "" + (REPAIR_LOSE * 100) + "%";
        parm[3] = "" + CREW_USE_MULT;

        parm[4] = "" + reqCrew(CrewPerCostPerSize[1]);
        parm[5] = "" + reqCrew(CrewPerCostPerSize[2]);
        parm[6] = "" + reqCrew(CrewPerCostPerSize[3]);
        parm[7] = "" + reqCrew(CrewPerCostPerSize[4]);

        switch(hullsize){
            case FRIGATE:
                parm[8] = ""+maxOp[1];
                break;
            case DESTROYER:
                parm[8] = ""+maxOp[2];
                break;
            case CRUISER:
                parm[8] = ""+maxOp[3];
                break;
            case CAPITAL_SHIP:
                parm[8] = ""+maxOp[4];
                break;
            default:
                parm[8] = ""+maxOp[0];
                break;
        }
        parm[9] = "" + (int)cost;
    }
    private int reqCrew(float in){
        if(in == 0){
            return 0;
        }
        return (int)(1 / in);
    }
}
