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
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.hullmods.AIRetrofit_hullmodUtilitys;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;

public class AIRetrofit_BaseShipyard extends BaseHullMod {
    final static String permanentWord = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_permament;
    final static String cantRemoveReason = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_cantRemoveReason;//"cannot be added or removed outside of a robotic shipyard";
    public String getHullmodID(){
        return spec.getId();
    }
    /*this is going to act as the new AI-Retrofits. but im only going to have this one, and a few functions that extend it and work from there.*/
    public float getCrewReductionMulti(){
        return 1;
    }
    public float getRepairTimeMulti(){
        return 1f;
    }
    public float getSupplyCostMulti(){
        return 1f;
    }
    public int getMinCrew(MutableShipStatsAPI stats){
        int currentMod=0;
        if (stats.getMinCrewMod().getMultBonus(spec.getId())!=null){
            stats.getMinCrewMod().unmodifyMult(spec.getId());
            currentMod = (int) stats.getMinCrewMod().computeEffective(stats.getVariant().getHullSpec().getMinCrew());
            stats.getMinCrewMod().modifyMult(spec.getId(),0);
        }else{
            currentMod = (int) stats.getMinCrewMod().computeEffective(stats.getVariant().getHullSpec().getMinCrew());
        }
        //AIRetrofit_Log.loging("(getMinCrew) base = "+currentMod,this,true);
        return currentMod;
    }
    /*public int getRemovedCrew(MutableShipStatsAPI stats){
        int minCrew = getMinCrew(stats);
        int maxCrew = (int) stats.getMaxCrewMod().computeEffective(stats.getVariant().getHullSpec().getMaxCrew());
        int a = Math.min(minCrew,maxCrew);
        return a;
    }*/

    public float getSupplysPerCrew(){
        //10 credits per month per crew
        return 0.1f * getSupplyCostMulti();
    }
    public float getSupplyIncrease(MutableShipStatsAPI stats){
        return getMinCrew(stats) * getSupplysPerCrew();
    }
    /*public float getRepairTimeIncrease(MutableShipStatsAPI stats){
        return stats.getBaseCRRecoveryRatePercentPerDay().getBaseValue() * getRepairTimeMulti();
    }*/
    public void applyRepairTimeChange(MutableShipStatsAPI stats){
        stats.getBaseCRRecoveryRatePercentPerDay().modifyMult(spec.getId(),getRepairTimeMulti());
    }
    public String getRepairTimeChangeDescription(MutableShipStatsAPI stats){
        int change = (int) (100 - (getRepairTimeMulti()*100));
        return change + "%";
    }
    public int getCrewReduction(MutableShipStatsAPI stats){
        int minCrew = (int) (getMinCrew(stats) * getCrewReductionMulti());
        int maxCrew = (int) stats.getMaxCrewMod().computeEffective(stats.getVariant().getHullSpec().getMaxCrew());
        //AIRetrofit_Log.loging("(getCrewReduction) minCrew = "+minCrew,this,true);
        //AIRetrofit_Log.loging("(getCrewReduction) maxCrew = "+maxCrew,this,true);
        int a = Math.min(minCrew,maxCrew);
        //AIRetrofit_Log.loging("(getCrewReduction) value = "+a,this,true);
        return a;
    }
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float SupplyIncrease = getSupplyIncrease(stats);

        stats.getSuppliesPerMonth().modifyFlat(spec.getId(),SupplyIncrease);
        stats.getMinCrewMod().modifyMult(spec.getId(),0);

        stats.getMaxCrewMod().modifyFlat(spec.getId(),-1*getCrewReduction(stats));

        applyRepairTimeChange(stats);
    }

    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize, ShipAPI ship) {
        if (ship == null) return getDescriptionParam(index,hullSize);
        return getDescriptionParam(index, hullSize, ship.getMutableStats());
    }
    protected String getDescriptionParam(int index, ShipAPI.HullSize hullSize, MutableShipStatsAPI ship){
        switch (index) {
            case 0:
                return permanentWord;
            case 1:
                return ""+0;
            case 2:
                return ""+get2DOfStuff(getSupplysPerCrew());
            case 3:
                return ""+ get2DOfStuff(getSupplyIncrease(ship));
            case 4:
                return ""+getCrewReduction(ship);
            case 5:
                return getRepairTimeChangeDescription(ship);
        }
        return "";
    }
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        MutableShipStatsAPI ship = AIRetrofit_hullmodUtilitys.getIndexShip();
        if (ship == null) return "";
        return getDescriptionParam(index, hullSize,ship);
    }

    protected double get2DOfStuff(float value){
        return Math.floor(value * 100) / 100;
    }
    /*@Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        switch (index){
            case 0:
                return displayTemp[0];
            case 1:
                return displayTemp[1];
            case 2:
                return displayTemp[2];
            case 3:
                return displayTemp[3];
            case 4:
                return displayTemp[4];
        }
        return "";
    }*/
    /*@Override
    public boolean isApplicableToShip(ShipAPI ship){
        if (incompatibleHullMods(ship) != null) return false;
        int currentMod = (int) ship.getMutableStats().getMinCrewMod().computeEffective(ship.getMutableStats().getVariant().getHullSpec().getMinCrew());
        if (currentMod <= 0 && !ship.getVariant().hasHullMod(getHullmodID())) return false;
        return super.isApplicableToShip(ship);
    }*/
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        if (Global.getSettings().getCurrentState() == GameState.TITLE) return;
        Color h = Misc.getHighlightColor();
        tooltip.addPara("",
                0, h,
                ""
        );
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        return cantRemoveReason;
    }
    @Override
    public boolean canBeAddedOrRemovedNow(ShipAPI ship, MarketAPI marketOrNull, CampaignUIAPI.CoreUITradeMode mode){
        return false;
    }

    /*
    protected String incompatibleHullMods(ShipAPI ship){
        final String[] compatible = {
                AIRetrofits_Constants_3.Hullmod_AIRetrofit,
                "AIRetrofit_ShipyardBase",
                "AIRetrofit_ShipyardGamma",
                "AIRetrofit_ShipyardBeta",
                "AIRetrofit_ShipyardAlpha",
                "AIRetrofit_ShipyardOmega",
                AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit,
        };
        final String[] names = {
                Global.getSettings().getHullModSpec(compatible[0]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[1]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[2]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[3]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[4]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[5]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[6]).getDisplayName(),
        };
        for(int a = 0; a < compatible.length; a++){
            if (getHullmodID().equals(compatible[a])) continue;
            if(ship.getVariant().hasHullMod(compatible[a])){
                //AIRetrofit_Log.loging("compairing hullmods of: "+compatible[a] +", "+getHullmodID(),this,true);
                //AIRetrofit_Log.loging("     getting incombatable hullmod",this,true);
                return names[a];
            }
        }
        //AIRetrofit_Log.loging("     hullmods compatible",this,true);
        return null;
    }*/


    public static void clearExstraOpCost(MutableShipStatsAPI stats){
        String temp;
        for(int a = 4096; a >= 1; a = a / 2){
            temp = "AIretrofit_AIretrofitAplha_opRemove" + a;
            stats.getVariant().removeMod(temp);
        }
        for(int a = 4096; a >= 1; a = a / 2){
            temp = "AIretrofit_AIretrofit_opadd" + a;
            stats.getVariant().removeMod(temp);
        }
    }
}
