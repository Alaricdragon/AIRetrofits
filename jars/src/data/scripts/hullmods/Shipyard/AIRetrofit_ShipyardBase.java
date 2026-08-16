package data.scripts.hullmods.Shipyard;

import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_ShipyardBase extends AIRetrofit_BaseShipyard{
    public static float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    public static float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    public static float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
    @Override
    public float getSupplyCostMulti() {
        return SUPPLY_USE_MULT;
    }
    @Override
    public float getCrewReductionMulti() {
        return CREW_USE_MULT;
    }
    @Override
    public float getRepairTimeMulti() {
        return REPAIR_LOSE;
    }
}
