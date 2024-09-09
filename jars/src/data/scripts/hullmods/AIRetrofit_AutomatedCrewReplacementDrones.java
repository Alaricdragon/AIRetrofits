package data.scripts.hullmods;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;

import static data.scripts.robot_forge.AIRetrofits_RobotForgeSecondary.iCalculateBonus;
/*
    being compleatly reworked.
    job is now to:
        get all fleet memeber with this mod, output total combined number of fleet with this mod X fleet size

    to consider:
        make build power equal to crew removed?
        remove max crew substraction?
        dont know

 */


public class AIRetrofit_AutomatedCrewReplacementDrones extends BaseLogisticsHullMod {
    int DronePerCrew = 10;
    int MinReplacedCrew = 1;
    float ReplacedCrew;
    static float RobotForgePerCrewMulti = Global.getSettings().getFloat("AIRetrofits_RobotForgePerCrewMulti");
    public static final String NAString = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForgeHullmod_NA_crew");
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id) {
        try {
            if (stats.getFleetMember() != null && stats.getFleetMember().getFleetData().getFleet().isPlayerFleet()) {
                //AIRetrofit_Log.loging("adding robot forge ability from player owned robot forge ship...",this);
                Global.getSector().getCharacterData().addAbility(AIRetrofits_Constants_3.ability_RobotForge);
            }
        }catch (Exception E){

        }
        //set this value to (maxcrew - mincrew) / 10; example: (500 = 50. 10 = 1)
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = stats.getVariant().getHullSpec().getMaxCrew();
        ReplacedCrew = (MaxCrew - MinCrew);
        stats.getMaxCrewMod().modifyFlat(id,ReplacedCrew * -1);
    }
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        switch(index) {
            case 0:
                return "" + (int) ReplacedCrew;
            case 1:
                return "" + Misc.getRoundedValueMaxOneAfterDecimal(ReplacedCrew * RobotForgePerCrewMulti);
            case 2:
                return "" + Misc.getRoundedValueMaxOneAfterDecimal(iCalculateBonus(Global.getSector().getPlayerFleet()));
        }
        return null;
    }
    @Override
    public boolean isApplicableToShip(ShipAPI ship/*, MutableCharacterStatsAPI wat*/){
        float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = ship.getVariant().getHullSpec().getMaxCrew();
        ReplacedCrew = (MaxCrew - MinCrew);
        return ship != null/* && cost <= unusedOP*/&& ReplacedCrew >= MinReplacedCrew && super.isApplicableToShip(ship);
    }
    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = ship.getVariant().getHullSpec().getMaxCrew();
        ReplacedCrew = (MaxCrew - MinCrew);
        if(!(ReplacedCrew >= MinReplacedCrew)){
            return AIRetrofits_StringHelper.getSplitString(NAString, ""+MinReplacedCrew,""+ReplacedCrew);
        }
        return super.getUnapplicableReason(ship);
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        //this is how im trying to highlight thigns. dose not work right. no idea why
        if (Global.getSettings().getCurrentState() == GameState.TITLE) return;
        Color h = Misc.getHighlightColor();
        tooltip.addPara("",
                0, h,
                ""
        );
    }

}
