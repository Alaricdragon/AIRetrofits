package data.scripts.robot_forge.dilogs;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.OfficerDataAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.rulecmd.SetStoryOption;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.loading.S;
import com.fs.starfarer.rpg.OfficerData;
import com.fs.starfarer.rpg.Person;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.robot_forge.AIRetrofits_RobotForgeDiologPlugin;
import data.scripts.robot_forge.AIRetrofits_RobotForgeDiologPlugin2;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.Map;
import java.util.Random;

public class AIRetrofits_Dialog_PeopleMaker extends AIRetrofits_DialogBase {
    TextPanelAPI text;
    InteractionDialogAPI dialog;
    OptionPanelAPI options;
    int temp = 0;
    private static final int officerCreditCost = AIRetrofits_Constants.RobotForge_officerCreditCost;//Global.getSettings().getInt("AIRetrofits_Officer_credits");//1000;
    private static final int administratorCreditCost = AIRetrofits_Constants.RobotForge_administratorCreditCost;//Global.getSettings().getInt("AIRetrofits_Admin_credits");///1000;
    private static final int officerSubCommandNodeCost = AIRetrofits_Constants.RobotForge_officerSubCommandNodeCost;//Global.getSettings().getInt("AIRetrofits_Officer_SCN");
    private static final int administratorSubCommandNodeCost = AIRetrofits_Constants.RobotForge_administratorSubCommandNodeCost;//Global.getSettings().getInt("AIRetrofits_Admin_SCN");

    private static final int officerCreditsPerMomth = AIRetrofits_Constants.RobotForge_officerCreditsPerMomth;//900;
    private static final int administratorCreditsPerMomth = AIRetrofits_Constants.RobotForge_administratorCreditsPerMomth;//2000;

    private static final String SubCommandNode = AIRetrofits_Constants.RobotForge_SubCommandNode;//"AIretrofit_SubCommandNode";

    private static final String mainPage_0 = "information about improving an sub command node, into a command node goes here";
    private static final String officerPage_0 = "information about how you can chose an officers personality go here";
    private static final String officerConfirmPage_0 = "information about the cost of creating an officer here. as well as the type of officer you are creating.";
    private static final String officerConfirmPage_1 = "the officer you create will cost %s per month. and more as they level up.";
    private static final String officerConfirmPage_2 = "you require %s sub command node and %s credits to create an officer";
    private static final String admenConfirmPage_0 = "information about the admen, and its cost go here";
    private static final String admenConfirmPage_1 = "the officer you create will cost %s per month. at an reduced cost if they are not doing anything";
    private static final String admenConfirmPage_2 = "you require %s sub command node and %s credits to create an administrator";;
    private static final String exitOfficer_0 = "information about the officer you have created here";
    private static final String exitOfficer_1 = "removed the credits and subcommand node";
    private static final String exitAdmen_0 = "information about the admen you have created here";
    private static final String exitAdmen_1 = "removed the credits and subcommand node";
    private static final String init_0 = "You cafullys consider what you can produce with the knowlage you have...";
    private static final Color highlight = Misc.getHighlightColor();
    private void mainPage(){
        this.dialog.getTextPanel().addPara(mainPage_0);
        this.options.clearOptions();
        this.options.addOption("create admen","admen","requires a sub command node");
        this.options.addOption("create officer","officer","requires a sub command node");
        this.options.addOption("back","back");
    }
    /*private void admenPage(){
        this.options.clearOptions();
        this.options.addOption("conform","admenConfirmPage");
        this.options.addOption("back","menu");
    }*/
    private void officerPage(){
        this.dialog.getTextPanel().addPara(officerPage_0);
        this.options.clearOptions();
        this.options.addOption("Timid","officerConfirmPage_0");
        this.options.addOption("Cautious","officerConfirmPage_1");
        this.options.addOption("Steady","officerConfirmPage_2");
        this.options.addOption("Aggressive","officerConfirmPage_3");
        this.options.addOption("Reckless","officerConfirmPage_4");
        //fearless dose not work. no idea why.
        //this.options.addOption("fearless","officerConfirmPage_5");

        this.options.addOption("back","menu");
    }
    private void officerConfirmPage(int power){
        this.dialog.getTextPanel().addPara(officerConfirmPage_0);
        this.options.clearOptions();
        switch (power){
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
        temp = power;
        String[] exstras = {"" + officerCreditsPerMomth};
        this.dialog.getTextPanel().addPara(officerConfirmPage_1,highlight,exstras);//"the officer you create will cost " + officerCreditsPerMomth + "per month. and more as they level up.");
        exstras = new String[]{"" + officerSubCommandNodeCost, "" + officerCreditCost};
        this.dialog.getTextPanel().addPara(officerConfirmPage_2,highlight,exstras);//"you require " + officerSubCommandNodeCost + " sub command node and " + officerCreditCost + " credits to create an officer");
        if(Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(SubCommandNode) >= officerSubCommandNodeCost && Global.getSector().getPlayerFleet().getCargo().getCredits().get() >= officerCreditCost){
            this.options.addOption("continue","createOfficer");
        }
        SetStoryOption.set(dialog,1,"createOfficer","promoteCrewMember","ui_char_spent_story_point","");
        this.options.addOption("back","officer");
    }
    private void admenConfirmPage(){
        this.dialog.getTextPanel().addPara(admenConfirmPage_0);//"information about the admen, and its cost go here");
        this.options.clearOptions();
        this.dialog.getTextPanel().addPara(admenConfirmPage_1,highlight,new String[]{"" + administratorCreditsPerMomth});//"the officer you create will cost " + administratorCreditsPerMomth + "per month. at an reduced cost if they are not doing anything.");
        this.dialog.getTextPanel().addPara(admenConfirmPage_2,highlight, "" + administratorSubCommandNodeCost,"" + administratorCreditCost);//"you require " + administratorSubCommandNodeCost + " sub command node and " + administratorCreditCost + " credits to create an administrator");
        if(Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(SubCommandNode) >= administratorSubCommandNodeCost && Global.getSector().getPlayerFleet().getCargo().getCredits().get() >= administratorCreditCost){
            this.options.addOption("continue","createAdmen");
        }
        SetStoryOption.set(dialog,1,"createAdmen","promoteCrewMember","ui_char_spent_story_point","");
        this.options.addOption("back","menu");
    }
    private void createAdmen(){
        Global.getSector().getPlayerFleet().getCargo().removeCommodity(SubCommandNode,administratorSubCommandNodeCost);
        Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(administratorCreditCost);
        PersonAPI person = OfficerManagerEvent.createAdmin(Global.getSector().getPlayerFaction(),0,new Random());
        setPerson(person);
        Global.getSector().getCharacterData().addAdmin(person);
        //exit();
    }
    private static final String[] personalities = {"timid","cautious","steady","aggressive","reckless","fearless"};
    private void createOfficer(int power){
        Global.getSector().getPlayerFleet().getCargo().removeCommodity(SubCommandNode,officerSubCommandNodeCost);
        Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(officerCreditCost);
        PersonAPI person = OfficerManagerEvent.createOfficer(Global.getSector().getPlayerFaction(),1);
        person.setPersonality(personalities[power]);
        setPerson(person);
        //possibility to add he possibility of having an elite skill to start?
        for(MutableCharacterStatsAPI.SkillLevelAPI a : person.getStats().getSkillsCopy()) {
            if (a.getSkill().isElite()) {
                person.getStats().decreaseSkill(a.getSkill().getId());
            }
        }
        Global.getSector().getPlayerFleet().getFleetData().addOfficer(person);
        //exit();
    }
    private void exitOfficer(){
        this.dialog.getTextPanel().addPara(exitOfficer_0);//"information about the officer you have created here");
        this.dialog.getTextPanel().addPara(exitOfficer_1);//"removed the credits and subcommand node");
        this.options.clearOptions();
        this.options.addOption("exit","exit");
    }
    private void exitAdmen(){
        this.dialog.getTextPanel().addPara(exitAdmen_0);//"information about the admen you have created here");
        this.dialog.getTextPanel().addPara(exitAdmen_1);//"removed the credits and subcommand node");
        this.options.clearOptions();
        this.options.addOption("exit","exit");
    }
    private void setPerson(PersonAPI person){
        //PersonAPI person = Global.getFactory().createPerson();
        person.setPortraitSprite(setDataLists.getRandom(2));
        person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
    }
    private PersonAPI setPerson(){
        PersonAPI person = Global.getFactory().createPerson();
        person.setPortraitSprite(setDataLists.getRandom(2));
        person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
        return person;
    }
    @Override
    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        this.options = dialog.getOptionPanel();
        this.text = dialog.getTextPanel();
        dialog.getVisualPanel().setVisualFade(0.25F, 0.25F);
        this.text.addParagraph(init_0);//"You cafullys consider what you can produce with the knowlage you have...");
        //this.populateOptions();
        mainPage();
        dialog.setPromptText(Misc.ucFirst("Options"));
    }
    private void back(){
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        AIRetrofit_Dialog_ForgeItemAndSelection a = new AIRetrofit_Dialog_ForgeItemAndSelection(fleet);
        a.init(dialog);
        AIRetrofits_RobotForgeDiologPlugin2.Dialog = a;
    }
    private void exit(){
        dialog.dismiss();
    }
    @Override
    public void optionSelected(String optionText, Object optionData) {
        String option = (String) optionData;
        switch(option){
            case "back":
                back();
                break;
            case "exit":
                exit();
                break;
            case "menu":
                mainPage();
                break;
            case "officer":
                officerPage();
                break;
            case "admen":
                admenConfirmPage();
                break;
            case "officerConfirmPage_0":
                officerConfirmPage(0);
                break;
            case "officerConfirmPage_1":
                officerConfirmPage(1);
                break;
            case "officerConfirmPage_2":
                officerConfirmPage(2);
                break;
            case "officerConfirmPage_3":
                officerConfirmPage(3);
                break;
            case "officerConfirmPage_4":
                officerConfirmPage(4);
                break;
            case "officerConfirmPage_5":
                officerConfirmPage(5);
                break;
            //case "admenConfirmPage":
                //admenConfirmPage();
                //break;
            case "createOfficer":
                createOfficer(temp);
                exitOfficer();
                break;
            case "createAdmen":
                createAdmen();
                exitAdmen();
                break;
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
