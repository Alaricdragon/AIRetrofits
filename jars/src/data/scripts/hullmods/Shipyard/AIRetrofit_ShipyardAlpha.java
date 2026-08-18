package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_ShipyardAlpha extends AIRetrofit_BaseShipyard {
    private static final String isFreeString = AIRetrofits_StringGetterProtection.getString("Shipyard_FullAuto_FreeText");//"Ships automated this way do not cost automated ship points.";
    public static float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    //public static float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    public static float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
    public static boolean COSTS_AUTOPOINTS = AIRetrofits_Constants_3.AIRetrofit_Perma_Alpha_IS_FREE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
    @Override
    public float getSupplyCostMulti() {
        return SUPPLY_USE_MULT;
    }
    @Override
    public float getCrewReductionMulti() {
        //AIRetrofit_Log.loging("crew use multi is: "+CREW_USE_MULT,this,true);
        return 0;//CREW_USE_MULT;
    }
    @Override
    public float getRepairTimeMulti() {
        return REPAIR_LOSE;
    }
    public boolean costsPoints(){
        return COSTS_AUTOPOINTS;
    }
    /*public int getCrewReduction(MutableShipStatsAPI stats){
        int minCrew = (int) (getMinCrew(stats) * getCrewReductionMulti());
        int maxCrew = (int) stats.getMaxCrewMod().computeEffective(stats.getVariant().getHullSpec().getMaxCrew());
        int a = Math.min(minCrew,maxCrew);
        return a;
    }*/
    /// This is modified here. The reason behind that is to 'trick' my math into changing the crew removed to the max crew with min changes.
    /*@Override
    public int getMinCrew(MutableShipStatsAPI stats){
        int currentMod=0;
        if (stats.getMaxCrewMod().getMultBonus(spec.getId())!=null){
            stats.getMaxCrewMod().unmodifyFlat(spec.getId());
            currentMod = (int) stats.getMaxCrewMod().computeEffective(stats.getVariant().getHullSpec().getMaxCrew());
            stats.getMaxCrewMod().modifyFlat(spec.getId(),currentMod);
            AIRetrofit_Log.loging("useing process a",this,true);
        }else{
            currentMod = (int) stats.getMaxCrewMod().computeEffective(stats.getVariant().getHullSpec().getMaxCrew());
            AIRetrofit_Log.loging("useing process b",this,true);
        }
        AIRetrofit_Log.loging("got unmodified max crew as: "+currentMod,this,true);
        //AIRetrofit_Log.loging("(getMinCrew) base = "+currentMod,this,true);
        return currentMod;
    }
    @Override
    public int getCrewReduction(MutableShipStatsAPI stats){
        int minCrew = (int) (getMinCrew(stats) * getCrewReductionMulti());
        AIRetrofit_Log.loging("got crew reduction as: "+minCrew,this,true);
        return minCrew;
    }*/
    @Override
    protected String getDescriptionParam(int index, ShipAPI.HullSize hullSize, MutableShipStatsAPI ship){
        switch (index) {
            case 4:
                return ""+getRepairTimeChangeDescription(ship);
            case 5:
                if (!costsPoints()) return isFreeString;
                return "";
        }
        return super.getDescriptionParam(index, hullSize, ship);
    }

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        super.applyEffectsBeforeShipCreation(hullSize, stats, id);
        if (stats == null || stats.getVariant() == null || stats.getVariant().getTags() == null) return;
        if (costsPoints()){
            if (stats.getVariant().hasTag(Tags.TAG_AUTOMATED_NO_PENALTY)) stats.getVariant().removeTag(Tags.TAG_AUTOMATED_NO_PENALTY);
        }else{
            if (!stats.getVariant().hasTag(Tags.TAG_AUTOMATED_NO_PENALTY)) stats.getVariant().addTag(Tags.TAG_AUTOMATED_NO_PENALTY);
        }
    }
}

