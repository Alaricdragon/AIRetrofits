package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.hullmods.AIRetrofit_AIretrofit;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_ShipyardAlpha extends AIRetrofit_BaseShipyard {
    private static final float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;

    private static final float[] maxOp = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_maxOp;
    private static final float[] CostPerCrewPerSize = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_CrewPerCostPerSize;

    private final String CrewCostDivider = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Alpha_CrewCostDivider");
    private final String MaxOpDivider = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Alpha_MaxOpDivider");
    private final String SupplyPercent = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Alpha_SupplyUsePercent");
    private final String RepairPercent = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Perma_Alpha_RepairChangePercent");



    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        super.applyEffectsBeforeShipCreation(hullSize, stats, id);
        stats.getFluxCapacity();
        stats.getFluxDissipation();
        //stats.getDP
    }
}

