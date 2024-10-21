package data.scripts.robot_forge.dilogs;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.robot_forge.AIRetrofits_RobotForgeDiologPlugin2;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetorfit_CommandNodeTypesBase;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class AIRetrofits_Dialog_PeopleMaker extends AIRetrofits_DialogBase {
    protected static final String String_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMenu_0");
    protected static final String String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMenu_1");
    protected static final String String_2 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMenu_2");
    public static ArrayList<AIRetorfit_CommandNodeTypesBase> peopleTypes = new ArrayList<>();
    TextPanelAPI text;
    InteractionDialogAPI dialog;
    OptionPanelAPI options;
    int temp = 0;

    private static final String SubCommandNode = AIRetrofits_Constants_3.RobotForge_SubCommandNode;//"AIretrofit_SubCommandNode";

    private static final String mainPage_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_mainPage_0");//"information about improving an sub command node, into a command node goes here";
    //private static final String init_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_init_0");
    private static final Color highlight = Misc.getHighlightColor();

    public static int stat = 0;
    private AIRetorfit_CommandNodeTypesBase currentType = null;
    private void mainPage(){
        stat = 0;
        currentType = null;
        this.dialog.getTextPanel().addPara(mainPage_0);
        this.options.clearOptions();
        for (AIRetorfit_CommandNodeTypesBase a: peopleTypes){
            a.createOptionForCore(options);
        /*this.options.addOption("create admen","admen","requires a sub command node");
        this.options.addOption("create officer","officer","requires a sub command node");*/
        }
        this.options.addOption(String_0,"back");
    }


    @Override
    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        this.options = dialog.getOptionPanel();
        this.text = dialog.getTextPanel();
        dialog.getVisualPanel().setVisualFade(0.25F, 0.25F);
        //this.text.addParagraph(init_0);//"You cafullys consider what you can produce with the knowlage you have...");
        //this.populateOptions();
        mainPage();
        dialog.setPromptText(Misc.ucFirst("Options"));
    }
    private void back(){
        this.stat--;
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        AIRetrofit_Dialog_ForgeItemAndSelection a = new AIRetrofit_Dialog_ForgeItemAndSelection(fleet);
        a.init(dialog);
        AIRetrofits_RobotForgeDiologPlugin2.Dialog = a;
    }
    private void exit(){
        dialog.dismiss();
    }
    public static void addExit(OptionPanelAPI options){
        options.addOption(String_1,"exit");
    }
    public static void addBack(OptionPanelAPI options){
        options.addOption(String_2,"menu");
    }
    @Override
    public void optionSelected(String optionText, Object optionData) {
        String option = (String) optionData;
        switch (option){
            case "back":
                back();
                return;
            case "exit":
                exit();
                return;
            case "menu":
                mainPage();
                return;
        }
        switch (stat){
            case 0:
                for (AIRetorfit_CommandNodeTypesBase a: peopleTypes){
                    if (a.optionSelected(optionText,optionData)){
                        this.currentType = a;
                        a.startPage(options,dialog,optionText,optionData);
                        break;
                    }
                }
                stat++;
                return;
            case 1:
                currentType.confermPage(options,dialog,optionText,optionData);
                stat++;
                return;
            case 2:
                currentType.createPerson();
                currentType.exitPage(options,dialog,optionText,optionData);
                stat++;
                return;
        }
    }

    @Override
    public void optionMousedOver(String optionText, Object optionData) {

    }

    @Override
    public void advance(float amount) {

    }

    @Override
    public void backFromEngagement(EngagementResultAPI battleResult) {

    }

    @Override
    public Object getContext() {
        return null;
    }

    @Override
    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }
}
