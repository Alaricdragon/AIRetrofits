package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.listeners.CommodityTooltipModifier;
import com.fs.starfarer.api.impl.PlayerFleetPersonnelTracker;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.startupData.AIRetrofits_Constants;
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
            case "AIretrofit_Advanced_WorkerDrone":
                salvageAdvancedRobot(info,width,expanded,stack);
                break;
            case "AIretrofit_Advanced_CombatDrone":
                combatAdvancedRobot(info,width,expanded,stack);
                break;
            case "AIretrofit_Advanced_SurveyDrone":
                surveyAdvancedRobot(info,width,expanded,stack);
                break;
            case "AIretrofit_Omega_WorkerDrone":
                salvageOmegaRobot(info,width,expanded,stack);
                break;
            case "AIretrofit_Omega_CombatDrone":
                combatOmegaRobot(info,width,expanded,stack);
                break;
            case "AIretrofit_Omega_SurveyDrone":
                surveyOmegaRobot(info,width,expanded,stack);
                break;
        }
    }
    private static final AIRetrofit_Robots[] robots = {
            (AIRetrofit_Robots) crewReplacer_Main.getJob("salvage_crew").getCrew("AIretrofit_WorkerDrone"),
            (AIRetrofit_Robots) crewReplacer_Main.getJob("survey_crew").getCrew("AIretrofit_SurveyDrone"),
            (AIRetrofit_Robots) crewReplacer_Main.getJob("raiding_marines").getCrew("AIretrofit_CombatDrone"),

            (AIRetrofit_Robots) crewReplacer_Main.getJob("salvage_crew").getCrew("AIretrofit_Advanced_WorkerDrone"),
            (AIRetrofit_Robots) crewReplacer_Main.getJob("survey_crew").getCrew("AIretrofit_Advanced_SurveyDrone"),
            (AIRetrofit_Robots) crewReplacer_Main.getJob("raiding_marines").getCrew("AIretrofit_Advanced_CombatDrone"),

            (AIRetrofit_Robots) crewReplacer_Main.getJob("salvage_crew").getCrew("AIretrofit_Omega_WorkerDrone"),
            (AIRetrofit_Robots) crewReplacer_Main.getJob("survey_crew").getCrew("AIretrofit_Omega_SurveyDrone"),
            (AIRetrofit_Robots) crewReplacer_Main.getJob("raiding_marines").getCrew("AIretrofit_Omega_CombatDrone"),
    };
    private void salvageRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[0].name,info,width,expanded,stack);
        displayCrewPower(robots[0],info,width,expanded,stack);
    }
    private void combatRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[2].name,info,width,expanded,stack);
        displayCrewPower(robots[2],info,width,expanded,stack);
    }
    private void surveyRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[1].name,info,width,expanded,stack);
        displayCrewPower(robots[1],info,width,expanded,stack);
    }

    private void salvageAdvancedRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[3].name,info,width,expanded,stack);
        displayCrewPower(robots[3],info,width,expanded,stack);
    }
    private void surveyAdvancedRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[4].name,info,width,expanded,stack);
        displayCrewPower(robots[4],info,width,expanded,stack);
    }
    private void combatAdvancedRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[5].name,info,width,expanded,stack);
        displayCrewPower(robots[5],info,width,expanded,stack);
    }

    private void salvageOmegaRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[6].name,info,width,expanded,stack);
        displayCrewPower(robots[6],info,width,expanded,stack);
    }
    private void surveyOmegaRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[7].name,info,width,expanded,stack);
        displayCrewPower(robots[7],info,width,expanded,stack);
    }
    private void combatOmegaRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        displayCrewSize(robots[8].name,info,width,expanded,stack);
        displayCrewPower(robots[8],info,width,expanded,stack);
    }
    private static final String robotDescription = AIRetrofits_Constants.robot_Description;//Global.getSettings().getString("AIRetrofits_RobotPowerDescription");//"this robot effectiveness is being multiplied by %s for having a %s in cargo, for a total power of %s per robot";
    private void displayCrewPower(AIRetrofit_Robots robot, TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {
                "" + robot.getCorePower(Global.getSector().getPlayerFleet().getCargo()),
                robot.getMaxCore(Global.getSector().getPlayerFleet().getCargo()),
                "" + robot.getCrewPower(Global.getSector().getPlayerFleet().getCargo()),
        };
        info.addPara(robotDescription, 5, highlight, exstra);
        if(expanded){
            /*this will hold the crew power of every job for this crew.*/
        }
    }
    private static final String robotSizeDescriptions = AIRetrofits_Constants.robot_SizeDescriptions;//Global.getSettings().getString("AIRetrofits_RobotCargoDescription");
    private void displayCrewSize(String name,TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {
                "" + Global.getSector().getEconomy().getCommoditySpec(name).getCargoSpace()
        };
        info.addPara(robotSizeDescriptions, 5, highlight, exstra); }
}
