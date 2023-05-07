package data.scripts.hullmods.robotDroneStroge;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofit_Log;

import java.awt.*;

public class AIRetrofit_RobotDroneStorage_Base extends BaseHullMod {

    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id/*, MutableCharacterStatsAPI c*/) {

    }
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        switch(index) {
        }
        return null;
    }
    @Override
    public boolean isApplicableToShip(ShipAPI ship/*, MutableCharacterStatsAPI wat*/){
        //if(ship == null) return false;
        return ship != null && super.isApplicableToShip(ship);
    }
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
        if(ship == null) return "";
        String IncombatableHullModText = "";
        try {
            String hullmods = incompatibleHullMods(ship);
            if (hullmods != null) {
                return IncombatableHullModText + hullmods;//"not compatible with: " + hullmods;
            }
        }catch (Exception E){
            AIRetrofit_Log.loging("Error: failed to run getUnapplicableReason. wonder why?",this,true);
        }
        return super.getUnapplicableReason(ship);
    }
    @Override
    public boolean canBeAddedOrRemovedNow(ShipAPI ship, MarketAPI marketOrNull, CampaignUIAPI.CoreUITradeMode mode){
        setDisplayValues(ship);
        return super.canBeAddedOrRemovedNow(ship,marketOrNull,mode);
    }
    protected String incompatibleHullMods(ShipAPI ship){
        final String[] compatible = {
                /*"AIRetrofit_ShipyardBase",
                "AIRetrofit_ShipyardGamma",
                "AIRetrofit_ShipyardBeta",
                "AIRetrofit_ShipyardAlpha",
                "AIRetrofit_ShipyardOmega"*/
        };
        final String[] names = {
                /*Global.getSettings().getHullModSpec(compatible[0]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[1]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[2]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[3]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[4]).getDisplayName(),*/
        };
        for(int a = 0; a < compatible.length; a++){
            if(ship.getVariant().hasHullMod(compatible[a])){
                return names[a];
            }
        }
        return null;
    }

    private void setDisplayValues(ShipAPI ship){
        if(ship == null){
            return;
        }
    }
}
