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
    public static float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    public static float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    public static float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
    public static boolean IS_FREE = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_IS_FREE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
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
    /*public int getCrewReduction(MutableShipStatsAPI stats){
        int minCrew = (int) (getMinCrew(stats) * getCrewReductionMulti());
        int maxCrew = (int) stats.getMaxCrewMod().computeEffective(stats.getVariant().getHullSpec().getMaxCrew());
        int a = Math.min(minCrew,maxCrew);
        return a;
    }*/
    /// This is modified here. The reason behind that is to 'trick' my math into changing the crew removed to the max crew with min changes.
    public int getMinCrew(MutableShipStatsAPI stats){
        int currentMod=0;
        if (stats.getMaxCrewMod().getMultBonus(spec.getId())!=null){
            stats.getMaxCrewMod().unmodifyMult(spec.getId());
            currentMod = (int) stats.getMaxCrewMod().computeEffective(stats.getVariant().getHullSpec().getMaxCrew());
            //stats.getMinCrewMod().modifyMult(spec.getId(),0);
        }else{
            currentMod = (int) stats.getMaxCrewMod().computeEffective(stats.getVariant().getHullSpec().getMaxCrew());
        }
        //AIRetrofit_Log.loging("(getMinCrew) base = "+currentMod,this,true);
        return currentMod;
    }
}

