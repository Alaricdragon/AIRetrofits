package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.listeners.CommodityTooltipModifier;
import com.fs.starfarer.api.impl.PlayerFleetPersonnelTracker;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import org.apache.log4j.Logger;

import java.awt.*;

public class AIRetrofit_RobotDescriptions implements CommodityTooltipModifier {
    public static CommodityTooltipModifier getCommodityTooltipModifier(){
        return (CommodityTooltipModifier) new AIRetrofit_RobotDescriptions();
    }
    @Override
    public void addSectionAfterPrice(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack) {
        String name = stack.getCommodityId();
        switch (name){
            case "AIretrofit_WorkerDrone":
                salvageRobot(info,width,expanded,stack);
                break;
            case "AIretrofit_CombatDrone":
                combatRobot(info,width,expanded,stack);
                break;
            case "AIretrofit_SurveyDrone":
                surveyRobot(info,width,expanded,stack);
                break;
        }
    }
    AIRetrofit_Robots[] robots = {
            (AIRetrofit_Robots) crewReplacer_Main.getJob("salvage_crew").getCrew("AIretrofit_WorkerDrone"),
            (AIRetrofit_Robots) crewReplacer_Main.getJob("survey_crew").getCrew("AIretrofit_SurveyDrone"),
            (AIRetrofit_Robots) crewReplacer_Main.getJob("raiding_marines").getCrew("AIretrofit_CombatDrone"),
    };
    private void salvageRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewPower(robots[0],info,width,expanded,stack);
    }
    private void combatRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewPower(robots[2],info,width,expanded,stack);
    }
    private void surveyRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewPower(robots[1],info,width,expanded,stack);
    }
    private static String robotDescription = "this robot effectiveness is being multiplied by %s for having %s in cargo, for a total power of %s per robot";
    private void displayCrewPower(AIRetrofit_Robots robot, TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {
                "" + robot.getCorePower(Global.getSector().getPlayerFleet()),
                robot.getMaxCore(Global.getSector().getPlayerFleet()),
                "" + robot.getCrewPower(Global.getSector().getPlayerFleet()),
        };
        info.addPara(robotDescription, 5, highlight, exstra);
        if(expanded){
            /*this will hold the crew power of every job for this crew.*/
        }
    }
    private void loging(String output){
        final Logger LOG = Global.getLogger(this.getClass());
        LOG.info(output);
    }
}
