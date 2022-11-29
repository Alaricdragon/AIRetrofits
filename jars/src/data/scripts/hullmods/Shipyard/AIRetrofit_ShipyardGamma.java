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
import data.scripts.hullmods.AIretrofit;

import java.awt.*;

public class AIRetrofit_ShipyardGamma extends AIRetrofit_ShipyardBase {
    final static String automationLevel = "Gamma-Core";
    final static String cantRemoveReason = "cannot be added or removed outside of a robotic shipyard";
    final static String industry = "AIRetrofit_shipYard";
    final static String name = "AIRetrofit_ShipyardGamma";
    private static final float SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + name + "_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + name + "_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_" + name + "_REPAIR_LOSE");//0.5f;
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
        parm[1] = ((SUPPLY_USE_MULT) * 100) + "%";
        parm[2] = "" + (REPAIR_LOSE * 100) + "%";
        parm[3] = "" + (int)MinCrew;
        parm[4] = "" + (int)(MinCrew * CREW_USE_MULT);
    }
}
