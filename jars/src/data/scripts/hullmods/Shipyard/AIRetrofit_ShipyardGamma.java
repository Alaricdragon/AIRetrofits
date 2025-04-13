package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_ShipyardGamma extends AIRetrofit_BaseShipyard {
    private static final float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Gamma_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Gamma_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Gamma_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
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

    @Override
    public void applyRepairTimeChange(MutableShipStatsAPI stats) {
        float a = stats.getBaseCRRecoveryRatePercentPerDay().getBaseValue();
        float b = (getRepairTimeMulti()-1) * 100;
        float c = (a*b)/100;
        stats.getBaseCRRecoveryRatePercentPerDay().modifyFlat(spec.getId(),c);
    }
    @Override
    public String getRepairTimeChangeDescription(MutableShipStatsAPI stats) {
        int change = (int) (100 - (getRepairTimeMulti()*100));
        change*=-1;
        return change + "%";
    }
}
