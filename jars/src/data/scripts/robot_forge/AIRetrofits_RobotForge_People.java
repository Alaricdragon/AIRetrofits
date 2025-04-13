package data.scripts.robot_forge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.crewReplacer_Job;
import data.scripts.crewReplacer_Main;
import data.scripts.robot_forge.dilogs.AIRetrofits_Dialog_PeopleMaker;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
public class AIRetrofits_RobotForge_People extends AIRetrofits_ForgeItem{
    public boolean endInteraction = true;
    public AIRetrofits_RobotForge_People(String nameTemp, String descriptionTemp, float speedTemp) {
        super(nameTemp, descriptionTemp, speedTemp);
    }
    @Override
    public void getDescription(TextPanelAPI text, float power) {
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
    }
    @Override
    public boolean activateDialog(InteractionDialogAPI dialog){
        AIRetrofits_Dialog_PeopleMaker a = new AIRetrofits_Dialog_PeopleMaker();
        a.init(dialog);
        AIRetrofits_RobotForgeDiologPlugin2.Dialog = a;
        return true;
    }
}
