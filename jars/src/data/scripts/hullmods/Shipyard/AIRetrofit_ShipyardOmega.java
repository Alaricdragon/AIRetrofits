package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.hullmods.AIRetrofit_AIretrofit;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_ShipyardOmega extends AIRetrofit_ShipyardAlphaOLD {
    private static final float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Omega_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    //private static final float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Omega_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Omega_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;

    private String[] parm = {"0","1","2","3","4","5","6","7","8","9","10"};

    private static final float[] CostPerCrewPerSize = AIRetrofits_Constants_3.AIRetrofit_Perma_Omega_CrewPerCostPerSize;

    private final String CrewCostDivider = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Omega_CrewCostDivider");
    private final String SupplyPercent = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Omega_SupplyUsePercent");
    private final String RepairPercent = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Omega_RepairChangePercent");
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = stats.getVariant().getHullSpec().getMaxCrew();
        float SupplyIncrease = stats.getSuppliesPerMonth().getBaseValue() * SUPPLY_USE_MULT;
        stats.getSuppliesPerMonth().modifyFlat(id,SupplyIncrease);
        stats.getMinCrewMod().modifyMult(id,0);
        stats.getMaxCrewMod().modifyMult(id,0);//getCrewSpaceRemoved(stats.getVariant().getHullSpec(),CREW_USE_MULT));
        stats.getCombatEngineRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);
        stats.getCombatWeaponRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);
        int exstra_cost = GetExstraOpCost(MaxCrew - MinCrew,hullSize);
        AIRetrofit_AIretrofit.clearExstraOpCost(stats);
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
        switch (hullSize){
            case FRIGATE:
                return (int)(crew / CostPerCrewPerSize[1]);
            case DESTROYER:
                return (int)(crew / CostPerCrewPerSize[2]);
            case CRUISER:
                return (int)(crew / CostPerCrewPerSize[3]);
            case CAPITAL_SHIP:
                return (int)(crew / CostPerCrewPerSize[4]);
            case FIGHTER:
            default:
                return (int)(crew / CostPerCrewPerSize[0]);
        }
    }


    private void setDisplayValues(ShipAPI ship){
        if(ship == null){
            return;
        }
        float MinCrew = ship.getMutableStats().getVariant().getHullSpec().getMinCrew();
        float MaxCrew = ship.getMutableStats().getVariant().getHullSpec().getMaxCrew();

        ShipAPI.HullSize hullsize = ship.getVariant().getHullSize();
        int cost = GetExstraOpCost(MaxCrew - MinCrew,hullsize);
        parm[0] = permanentWord;
        parm[1] = "" + AIRetrofits_StringHelper.getSplitString(SupplyPercent,""+(int)(SUPPLY_USE_MULT * 100));
        parm[2] = "" + AIRetrofits_StringHelper.getSplitString(RepairPercent,""+(int)(REPAIR_LOSE * 100));
        parm[3] = ""+0;
        parm[4] = ""+0;

        parm[5] = AIRetrofits_StringHelper.getSplitString(CrewCostDivider,""+(int)(CostPerCrewPerSize[1]),""+(int)(CostPerCrewPerSize[2]),""+(int)(CostPerCrewPerSize[3]),""+(int)(CostPerCrewPerSize[4]));
        parm[6] = ""+1;
        //parm[7] = AIRetrofits_StringHelper.getSplitString(MaxOpDivider,""+(int)maxOp[1],""+(int)maxOp[2],""+(int)maxOp[3],""+(int)maxOp[4]);

        parm[7] = "" + (int)cost;
    }
}