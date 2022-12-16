package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.listeners.CommodityTooltipModifier;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import org.apache.log4j.Logger;

public class AIRetrofit_RobotDescriptions implements CommodityTooltipModifier {

    @Override
    public void addSectionAfterPrice(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack) {
        loging("HERE getting data for a thing from a thing named: " + stack);
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
    private void salvageRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        info.addPara("AAAAAAAAAAAAAAAAAAA",5);
    }
    private void combatRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        info.addPara("cool",5);
    }
    private void surveyRobot(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack){
        info.addPara("POWER",5);
    }
    private void loging(String output){
        final Logger LOG = Global.getLogger(this.getClass());
        LOG.info(output);
    }
}
