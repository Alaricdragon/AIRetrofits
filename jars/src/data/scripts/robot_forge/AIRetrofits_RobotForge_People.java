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
/*
    ISSUE:
        when i try to open this diolog here, nothing happens. maybe i cant open other diologs from inside of one?
    i have no clue what so ever how to use this.
    so this is what needs to happen:
    when Robot Forge sellects this option, swap to this interaction diolog.
    dilog map:
        create admen
            (2 story pont) teach industreal planing skill   -> create admen, return to start of robot forge.
            (story point) conform                           -> create admen, return to start of robot forge.
            back                                            -> return to start of this dialog
        create caption
            (story point)timid                              -> create caption, return to start of robot forge
            (story point)catsis                             -> create caption, return to start of robot forge
            (story point)steady                             -> create caption, return to start of robot forge
            (story point)aggressive                         -> create caption, return to start of robot forge
            (story point)reckless                           -> create caption, return to start of robot forge
            (2 story points)fearless                        -> create caption, return to start of robot forge
            back                                            -> return to start of this dialog
        back
            return                                          -> to start of robot forge dialog.


 */
public class AIRetrofits_RobotForge_People extends AIRetrofits_ForgeItem{
    public boolean endInteraction = true;
    public AIRetrofits_RobotForge_People(String nameTemp, String descriptionTemp, float speedTemp) {
        super(nameTemp, descriptionTemp, speedTemp);
    }
    @Override
    public void getDescription(TextPanelAPI text, float power) {
        //text.addPara("AAAAAAAAAAAAAAAAAAA");
        //exit();
        /*CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        crewReplacer_Job job = crewReplacer_Main.getJob("raiding_marines");
        ArrayList<Float> crews = job.getCrewForJob(fleet,16f);// crewPowerRequired is the amount of crew power you want crew replacer to get from an fleet.
        String[] names = job.GetCrewNames();//forgot i made this an thing.
        for(int a = 0; a < crews.size(); a++){
            text.addPara(names[a] + ": " + crews.get(a));
        }*/
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        //Global.getSector().getCampaignUI().showInteractionDialog(new AIRetrofits_RobotForgeDiologPlugin(fleet), fleet);
        //Global.getSector().getCampaignUI().showInteractionDialog(new AIRetrofits_RobotForge_People("","",0), fleet);
    }
    @Override
    public boolean activateDialog(InteractionDialogAPI dialog){
        AIRetrofits_Dialog_PeopleMaker a = new AIRetrofits_Dialog_PeopleMaker();
        a.init(dialog);
        AIRetrofits_RobotForgeDiologPlugin2.Dialog = a;
        return true;
    }
}
