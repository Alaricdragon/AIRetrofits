package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_ShipyardAlpha  extends AIRetrofit_ShipyardBase {
    private static final float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;

    private String[] parm = {"0","1","2","3","4","5","6","7","8","9","10"};

    private static final float[] maxOp = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_maxOp;
    private static final float[] CostPerCrewPerSize = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_CrewPerCostPerSize;

    private final String CrewCostDivider = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Alpha_CrewCostDivider");
    private final String MaxOpDivider = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Alpha_MaxOpDivider");
    private final String SupplyPercent = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Alpha_SupplyUsePercent");
    private final String RepairPercent = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Alpha_RepairChangePercent");
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = stats.getVariant().getHullSpec().getMaxCrew();
        float SupplyIncrease = stats.getSuppliesPerMonth().getBaseValue() * SUPPLY_USE_MULT;
        stats.getSuppliesPerMonth().modifyFlat(id,SupplyIncrease);
        stats.getMinCrewMod().modifyMult(id,0);
        stats.getMaxCrewMod().modifyMult(id,getCrewSpaceRemoved(stats.getVariant().getHullSpec(),CREW_USE_MULT));
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
        switch (hullSize){
            case FRIGATE:
                return (int)Math.min(crew / CostPerCrewPerSize[1],maxOp[1]);
            case DESTROYER:
                return (int)Math.min(crew / CostPerCrewPerSize[2],maxOp[2]);
            case CRUISER:
                return (int)Math.min(crew / CostPerCrewPerSize[3],maxOp[3]);
            case CAPITAL_SHIP:
                return (int)Math.min(crew / CostPerCrewPerSize[4],maxOp[4]);
            case FIGHTER:
            default:
                return (int)Math.min(crew / CostPerCrewPerSize[0],maxOp[0]);
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
        parm[3] = "" + (int)getCrewSpaceRemoved(ship.getHullSpec(),CREW_USE_MULT);
        parm[4] = ""+0;

        parm[5] = AIRetrofits_StringHelper.getSplitString(CrewCostDivider,""+(int)reqCrew(CostPerCrewPerSize[1]),""+(int)reqCrew(CostPerCrewPerSize[2]),""+(int)reqCrew(CostPerCrewPerSize[3]),""+(int)reqCrew(CostPerCrewPerSize[4]));
        parm[6] = AIRetrofits_StringHelper.getSplitString(MaxOpDivider,""+(int)maxOp[1],""+(int)maxOp[2],""+(int)maxOp[3],""+(int)maxOp[4]);

        parm[7] = "" + (int)cost;
    }
    private int reqCrew(float in){
        if(in == 0){
            return 0;
        }
        return (int)(1 / in);
    }

    @Override
    public int getCrewSpaceRemoved(ShipHullSpecAPI spec, float CREW_USE_MULT) {
        return (int) (spec.getMaxCrew()*CREW_USE_MULT);
    }
}
