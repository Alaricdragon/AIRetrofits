package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;

public class AIRetrofit_ShipyardBase extends BaseHullMod {

/*
    private static final float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
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
 */

    final static String permanentWord = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_permament;
    final static String cantRemoveReason = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_cantRemoveReason;//"cannot be added or removed outside of a robotic shipyard";
    final static String industry = "AIRetrofit_shipYard";
    //final static String name = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_;//"AIRetrofit_ShipyardBase";
    private static final float SUPPLY_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_SUPPLY_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_CREW_USE_MULT;//Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = AIRetrofits_Constants_3.AIRetrofit_Perma_Base_REPAIR_LOSE;//Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
    private String[] parm = {"","","","",""};
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float SupplyIncrease = stats.getSuppliesPerMonth().getBaseValue() * SUPPLY_USE_MULT;
        stats.getSuppliesPerMonth().modifyFlat(id,SupplyIncrease);
        stats.getMinCrewMod().modifyMult(id,0);
        stats.getMaxCrewMod().modifyFlat(id,(getCrewSpaceRemoved(stats.getVariant().getHullSpec(),CREW_USE_MULT)) * -1);
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
        if(ship != null && (ship.getFleetMember().getFleetData().getCommander().isPlayer())){
            return false;
        }
        if(marketOrNull != null){// && (marketOrNull.hasIndustry(industry) && marketOrNull.getIndustry(industry).isFunctional())){
            return true;
        }
        return false;
    }
    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        return cantRemoveReason;
    }
    @Override
    public boolean isApplicableToShip(ShipAPI ship/*, MutableCharacterStatsAPI wat*/){
        return true;//ship != null && true;
    }
    private void setDisplayValues(ShipAPI ship){
        if(ship == null){
            return;
        }
        parm[0] = permanentWord;//this.spec.getDisplayName();;
        parm[1] = (int)((SUPPLY_USE_MULT) * 100) + "%";
        parm[2] = "" + (int)(REPAIR_LOSE * 100) + "%";
        parm[3] = "" + (int)getCrewSpaceRemoved(ship.getHullSpec(),CREW_USE_MULT);
        parm[4] = "" + (int)(0);
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        //this is how im trying to highlight thigns. dose not work right. no idea why
        //setDisplayValues(ship);


        if (Global.getSettings().getCurrentState() == GameState.TITLE) return;
        Color h = Misc.getHighlightColor();
        tooltip.addPara("",
                0, h,
                ""
        );
    }

    public int getCrewSpaceRemoved(ShipHullSpecAPI spec,float CREW_USE_MULT){
        return (int)Math.min(spec.getMinCrew()*CREW_USE_MULT,spec.getMaxCrew());
    }
}
