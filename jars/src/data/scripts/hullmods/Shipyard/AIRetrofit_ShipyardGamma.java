package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_ShipyardGamma extends AIRetrofit_ShipyardBase {
    final static String automationLevel = AIRetrofits_Constants_3.AIRetrofit_Perma_Gamma_automationLevel;//"Gamma-Core";
    private static final float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Gamma_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Gamma_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Gamma_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
    private String[] parm = {"","","","",""};
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float SupplyIncrease = stats.getSuppliesPerMonth().getBaseValue() * SUPPLY_USE_MULT;
        stats.getSuppliesPerMonth().modifyFlat(id,SupplyIncrease);
        stats.getMinCrewMod().modifyMult(id,CREW_USE_MULT);
        stats.getMaxCrewMod().modifyFlat(id,MinCrew * -1);
        stats.getCombatEngineRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);
        stats.getCombatWeaponRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);
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
    private void setDisplayValues(ShipAPI ship){
        if(ship == null){
            return;
        }
        float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
        parm[0] = automationLevel;
        parm[1] = (int)((SUPPLY_USE_MULT) * 100) + "%";
        parm[2] = "" + (int)(REPAIR_LOSE * 100) + "%";
        parm[3] = "" + (int)MinCrew;
        parm[4] = "" + (int)(MinCrew * CREW_USE_MULT);
    }
}
