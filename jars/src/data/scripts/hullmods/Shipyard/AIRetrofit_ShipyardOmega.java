package data.scripts.hullmods.Shipyard;

import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_ShipyardOmega extends AIRetrofit_ShipyardAlpha {
    public static float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Omega_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    //public static float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Omega_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    public static float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Omega_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
    public static boolean COSTS_AUTOPOINTS = AIRetrofits_Constants_3.AIRetrofit_Perma_Omega_IS_FREE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
    @Override
    public float getSupplyCostMulti() {
        return SUPPLY_USE_MULT;
    }
    @Override
    public float getCrewReductionMulti() {
        return 0;//CREW_USE_MULT;
    }
    @Override
    public float getRepairTimeMulti() {
        return REPAIR_LOSE;
    }

    @Override
    public boolean costsPoints() {
        return COSTS_AUTOPOINTS;
    }
}