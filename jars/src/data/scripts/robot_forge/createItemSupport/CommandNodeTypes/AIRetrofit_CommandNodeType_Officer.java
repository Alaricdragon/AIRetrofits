package data.scripts.robot_forge.createItemSupport.CommandNodeTypes;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoTransferHandlerAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.rulecmd.SetStoryOption;
import com.fs.starfarer.api.impl.campaign.rulecmd.ShowResCost;
import com.fs.starfarer.api.plugins.OfficerLevelupPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.rpg.Person;
import data.scripts.AIRetrofit_Log;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.robot_forge.dilogs.AIRetrofits_Dialog_PeopleMaker;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRetrofit_CommandNodeType_Officer extends AIRetorfit_CommandNodeTypesBase{
    public AIRetorfit_CommandNodeTypesBase logClass = this;
    public boolean logs = false;
    public static final float[][] Officer_Costs = {
            //cost of non extra level/elite
            {Global.getSettings().getFloat("AIRetrofit_CreatePerson_Officer_costPerLevel"),Global.getSettings().getFloat("AIRetrofit_CreatePerson_Officer_costPerElite")},
            //cost of extra level/elite
            {Global.getSettings().getFloat("AIRetrofit_CreatePerson_Officer_costPerLevelExtra"),Global.getSettings().getFloat("AIRetrofit_CreatePerson_Officer_costPerEliteExtra")}
    };
    public static final float[][] Officer_Weights = {
            //cost of non extra level/elite
            {Global.getSettings().getFloat("AIRetrofit_CreatePerson_Officer_weightPerLevel"),Global.getSettings().getFloat("AIRetrofit_CreatePerson_Officer_weightPerElite")},
            //cost of extra level/elite
            {Global.getSettings().getFloat("AIRetrofit_CreatePerson_Officer_weightPerLevelExtra"),Global.getSettings().getFloat("AIRetrofit_CreatePerson_Officer_weightPerEliteExtra")}
    };
    public static final int[][] officerNumbers = {
            {0,2},
            {1,3}
    };
    public AIRetrofit_CommandNodeType_Officer(String name, float weight,boolean addToCommandNodes,boolean addToRobotForge,boolean canAddPastMax) {
        super(name, weight,addToCommandNodes,addToRobotForge,canAddPastMax);
    }
    private final static String officerText = AIRetrofits_StringGetterProtection.getString("AIRetrofit_CommandNode_officerText");//"officer";
    private final static String officerText2 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_CommandNode_officerText2");//"a command node with the designation of %s. they are a %s of level %s with a personality of %s";
    @Override
    public void commandNodeTooltip(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource, AIRetrofit_CommandNode commandNode) {
        float pad = 3f;
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        String type = officerText;
        TooltipMakerAPI text = tooltip.beginImageWithText(commandNode.person.getPortraitSprite(), 48);
        text.addPara(officerText2,pad,highlight,commandNode.person.getNameString(),type,""+commandNode.person.getStats().getLevel(),commandNode.person.getPersonalityAPI().getDisplayName());
        tooltip.addImageWithText(opad);
        if(expanded){
            tooltip.addSkillPanel(commandNode.person,pad);
        }
    }

    @Override
    public PersonAPI createPersonForNode(CargoAPI cargo, int power, int personality) {
        PersonAPI person = createOfficer(personality,power);
        return person;
    }
    @Override
    public void performRightClickAction(PersonAPI person){
        Global.getSector().getPlayerFleet().getFleetData().addOfficer(person);
    }
    public PersonAPI createOfficer(int personality, float skillPower) {
        float maxLV = 0;
        float maxESkills = 0;
        PersonAPI player = new Person();//Global.getSector().getPlayerPerson();
        player.setFleet(Global.getSector().getPlayerFleet());
        OfficerLevelupPlugin plugin = (OfficerLevelupPlugin) Global.getSettings().getPlugin("officerLevelUp");

        maxESkills = plugin.getMaxEliteSkills(player);
        maxLV = plugin.getMaxLevel(player);
        //AIRetrofit_Log.loging("max lv and elet set to: "+maxLV+", "+maxESkills,logClass,logs);
        return createOfficer(personality,skillPower,maxLV,maxESkills);
    }
    public PersonAPI createOfficer(int personality, float skillPower,float softMaxLevel,float softMaxEleteSkills){
        //skillPower*=15;
        AIRetrofit_Log.loging("running: "+"createOfficer"+" with: personality,skillPower: "+personality+", "+skillPower,logClass,logs);
        AIRetrofit_Log.push();
        //possibility to add he possibility of having an elite skill to start?
        //List<MutableCharacterStatsAPI.SkillLevelAPI> skillsCopy = person.getStats().getSkillsCopy();
        int level=0;
        int epics=0;
        int normal=0;
        float powerUsed = 0;
        //Random random = new Random();
        /*Officer_Costs[0][a] == normalskill cost, elete skill cost
        Officer_Costs[1][a] == exstra normalskill cost, exstra elete skill cost
        Officer_Weights

        */
        AIRetrofit_Log.push();
        while(skillPower > 0){
            //determine what i can add this loop.
            ArrayList<ArrayList<Integer>> types = new ArrayList<>();
            if(epics < level){//can add epics.
                AIRetrofit_Log.loging("costs array: "+Officer_Costs[0][0]+","+Officer_Costs[0][1]+","+Officer_Costs[1][0]+","+Officer_Costs[1][1]+",",logClass,true);
                AIRetrofit_Log.loging("is less then?"+ (skillPower <= Officer_Costs[0][1])+","+(skillPower <= Officer_Costs[1][0]),logClass,true);
                if(epics < softMaxEleteSkills && skillPower >= Officer_Costs[0][1]){//can add moral epics.
                    AIRetrofit_Log.loging("adding type 2",logClass,true);
                    types.add(AIRetrofits_CreatePeople.getLocationArray(0,1));
                }else if(skillPower >= Officer_Costs[1][1]){//can only add exstra epics.
                    AIRetrofit_Log.loging("adding type 3",logClass,true);
                    types.add(AIRetrofits_CreatePeople.getLocationArray(1,1));
                }
            }
            if(level < softMaxLevel && skillPower >= Officer_Costs[0][0]){//can add normal level
                AIRetrofit_Log.loging("adding type 0",logClass,true);
                types.add(AIRetrofits_CreatePeople.getLocationArray(0,0));
            }else if(skillPower >= Officer_Costs[1][0]){//can only add epic levels.
                AIRetrofit_Log.loging("adding type 1",logClass,true);
                types.add(AIRetrofits_CreatePeople.getLocationArray(1,0));
            }
            AIRetrofit_Log.loging("we got "+types.size()+"types this run...",logClass,true);
            if(types.size() == 0){
                break;
            }
            //get the total random for the items i can add.
            float totalWeight = 0;
            for(int a = 0; a < types.size(); a++){
                totalWeight += Officer_Weights[types.get(a).get(0)][types.get(a).get(1)];
            }
            //sellect a random item from the gotten random.
            float sellected = (float) (Math.random()*totalWeight);
            int item = types.size() - 1;
            for(int a = 0; a < types.size() && item > 0; a++){
                if(totalWeight >= sellected) {
                    totalWeight -= Officer_Weights[types.get(a).get(0)][types.get(a).get(1)];
                    item--;
                }
            }
            //add skills to the thing.
            powerUsed+=Officer_Costs[types.get(item).get(0)][types.get(item).get(1)];
            skillPower-=Officer_Costs[types.get(item).get(0)][types.get(item).get(1)];
            AIRetrofit_Log.loging("we have"+skillPower+"power left...",logClass,true);
            switch (officerNumbers[types.get(item).get(0)][types.get(item).get(1)]){
                case 0://add normal skill.
                case 1://add bonus skill
                    //OfficerLevelupPlugin plugin = (OfficerLevelupPlugin) Global.getSettings().getPlugin("officerLevelUp");
                    //List<String> skills = plugin.pickLevelupSkills(person, new Random());
                    //OfficerManagerEvent.pickSkill(person,skills,OfficerManagerEvent.SkillPickPreference.ANY,1,new Random());
                    level++;
                    AIRetrofit_Log.loging("adding a level",logClass,logs);
                    break;
                case 2://make a skill elite
                case 3://make a bonus skill elite
                    //OfficerManagerEvent.addEliteSkills(person,1,new Random());
                    epics++;
                    AIRetrofit_Log.loging("adding a epic",logClass,logs);
                    break;
            }
        }
        AIRetrofit_Log.pop();
        AIRetrofit_Log.loging("got a level and elete of: "+level+", "+epics,logClass,logs);
        PersonAPI person = OfficerManagerEvent.createOfficer(Global.getSector().getPlayerFaction(),1,OfficerManagerEvent.SkillPickPreference.ANY,new Random());
        int levelTemp=1;
        level--;
        while(level> 0){
            OfficerLevelupPlugin plugin = (OfficerLevelupPlugin) Global.getSettings().getPlugin("officerLevelUp");
            List<String> skills = plugin.pickLevelupSkills(person, new Random());
            String temp = OfficerManagerEvent.pickSkill(person,skills,OfficerManagerEvent.SkillPickPreference.ANY,1,new Random());
            //AIRetrofit_Log.loging("adding the skill called: "+ temp,logClass,logs);
            if(temp != null) {
                person.getStats().setSkillLevel(temp, 1);
                levelTemp++;
            }
            level--;
        }
        person.getStats().setLevel(levelTemp);
        //OfficerLevelupPlugin plugin = (OfficerLevelupPlugin) Global.getSettings().getPlugin("officerLevelUp");
        //List<String> skills = plugin.pickLevelupSkills(person, new Random());
        //OfficerManagerEvent.pickSkill(person,skills,OfficerManagerEvent.SkillPickPreference.ANY,1,new Random());
        AIRetrofit_Log.push();
        AIRetrofit_Log.pop();
        person.setPersonality(AIRetrofits_CreatePeople.personalities[personality]);
        AIRetrofits_CreatePeople.setPerson(person);
        List<MutableCharacterStatsAPI.SkillLevelAPI> skillsCopy = person.getStats().getSkillsCopy();
        for(int a2 = 0; a2 < skillsCopy.size(); a2++) {
            MutableCharacterStatsAPI.SkillLevelAPI a = skillsCopy.get(a2);
            if(a.getLevel() > 1 && a.getSkill().isCombatOfficerSkill()) {
                if (epics == 0) {
                    AIRetrofit_Log.loging("removing epic skill: " + a.getSkill().getName(), logClass, logs);
                    person.getStats().decreaseSkill(a.getSkill().getId());
                } else if (epics > 0) {
                    epics--;
                }
            }
        }
        if (epics > 0){
            for(int a2 = 0; a2 < skillsCopy.size(); a2++) {
                MutableCharacterStatsAPI.SkillLevelAPI a = skillsCopy.get(a2);
                if (!(a.getLevel() > 1) && a.getSkill().isCombatOfficerSkill()) {
                    AIRetrofit_Log.loging("attempting to add a epic: "+a.getSkill().getName(),logClass,logs);
                    person.getStats().increaseSkill(a.getSkill().getId());
                    epics--;
                    if(epics == 0){
                        break;
                    }
                }
            }
        }
        AIRetrofit_Log.loging("final officer skill set has "+person.getStats().getSkillsCopy().size() + " skills",logClass,logs);
        AIRetrofit_Log.push();
        for(MutableCharacterStatsAPI.SkillLevelAPI a : person.getStats().getSkillsCopy()){
            if(a.getSkill().isCombatOfficerSkill()) {
                AIRetrofit_Log.loging(a.getSkill().getName(), logClass, logs);
            }
        }
        AIRetrofit_Log.pop();
        //person.getStats().setLevel(level);
        AIRetrofit_Log.pop();
        this.setAmountOfPowerUsed((int)powerUsed);
        return person;
    }

    @Override
    public boolean isAtMaxAmount() {
        int available = Global.getSector().getPlayerFleet().getFleetData().getOfficersCopy().size();
        int max = (int)Global.getSector().getPlayerStats().getOfficerNumber().getModifiedValue();
        return max <= available;
    }

    private static final int officerCreditCost = AIRetrofits_Constants_3.RobotForge_officerCreditCost;//Global.getSettings().getInt("AIRetrofits_Officer_credits");//1000;
    private static final int officerSubCommandNodeCost = AIRetrofits_Constants_3.RobotForge_officerSubCommandNodeCost;//Global.getSettings().getInt("AIRetrofits_Officer_SCN");
    private static final int officerCreditsPerMomth = AIRetrofits_Constants_3.RobotForge_officerCreditsPerMomth;//900;
    private static final String officerPage_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerPage_0");
    private static final String officerConfirmPage_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_0");
    private static final String officerConfirmPage_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_1");
    private static final String officerConfirmPage_2 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_2");
    private static final String officerConfirmPage_3 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_3");
    private static final String officerConfirmPage_4 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_4");
    private static final String officerConfirmPage_5 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_5");
    private static final String officerConfirmPage_6 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_6");
    private static final String officerConfirmPage_7 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_7");
    private static final String officerConfirmPage_8 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_8");
    //private static final String officerConfirmPage_9 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_9");
    private static final String officerConfirmPage_10 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_10");
    private static final String officerConfirmPage_11 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_11");
    //private static final String officerConfirmPage_12 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_12");
    private static final String exitOfficer_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_exitOfficer_0");
    private static final String exitOfficer_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_exitOfficer_1");
    private static final String[] personalities = {"timid","cautious","steady","aggressive","reckless","fearless"};
    private int temp=0;
    private PersonAPI personTemp=null;
    private static final String MyOptionData = "officer";
    private static final String MyOptionText = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_OfficerOption");//"create officer";
    private static String MyOptionHoverOver = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_PeopleMaker_OfficerHoverOver");//"requires a sub command node";
    public boolean canBuildCommandNode(){
        Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(AIRetrofits_Constants_3.Commodity_SubCommandNode);//,officerSubCommandNodeCost);
        Global.getSector().getPlayerFleet().getCargo().getCredits(); // officerCreditCost;
        //TooltipMakerAPI tooltip
        return false;
    }
    @Override
    public void createOptionForCore(OptionPanelAPI options){
        powerTemp=0;
        options.addOption(MyOptionText,MyOptionData,MyOptionHoverOver);
        //options.setEnabled(MyOptionData,canBuildCommandNode());
    }
    @Override
    public boolean optionSelected(String optionText, Object optionData) {
        String optionDataTemp = (String)optionData;
        return optionDataTemp.equals(MyOptionData);
    }
    private void officerConfirmPage(int power,OptionPanelAPI options, InteractionDialogAPI dialog,String optionText, Object optionData){
        String[] exstras = {"" + personalities[power]};
        Color highlight = Misc.getHighlightColor();
        dialog.getTextPanel().addPara(officerConfirmPage_0,highlight,exstras);//infermation about the officer your creating go here.
        options.clearOptions();
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
        exstras = new String[]{"" + officerCreditsPerMomth};
        dialog.getTextPanel().addPara(officerConfirmPage_1,highlight,exstras);//"the officer you create will cost " + officerCreditsPerMomth + "per month. and more as they level up.");
        exstras = new String[]{"" + officerSubCommandNodeCost, "" + officerCreditCost};
        dialog.getTextPanel().addPara(officerConfirmPage_2,highlight,exstras);//"you require " + officerSubCommandNodeCost + " sub command node and " + officerCreditCost + " credits to create an officer");
        boolean resources = Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(AIRetrofits_Constants_3.Commodity_SubCommandNode) >= officerSubCommandNodeCost && Global.getSector().getPlayerFleet().getCargo().getCredits().get() >= officerCreditCost;
        if (isAtMaxAmount()){
            dialog.getTextPanel().addPara(officerConfirmPage_10,Misc.getNegativeHighlightColor());
        }
        if (!resources){
            dialog.getTextPanel().addPara(officerConfirmPage_11,Misc.getNegativeHighlightColor());
        }
        options.addOption(officerConfirmPage_3,"createOfficer");
        if(!resources){
            options.setEnabled("createOfficer",false);
            //options.setTooltip("createOfficer",officerConfirmPage_12);
        }
        SetStoryOption.set(dialog,1,"createOfficer","promoteCrewMember","ui_char_spent_story_point","");
        AIRetrofits_Dialog_PeopleMaker.addBack(options);
    }
    @Override
    public void startPage(OptionPanelAPI options, InteractionDialogAPI dialog, String optionText, Object optionData){
        dialog.getTextPanel().addPara(officerPage_0);
        options.clearOptions();
        options.addOption(officerConfirmPage_4,"officerConfirmPage_0");
        options.addOption(officerConfirmPage_5,"officerConfirmPage_1");
        options.addOption(officerConfirmPage_6,"officerConfirmPage_2");
        options.addOption(officerConfirmPage_7,"officerConfirmPage_3");
        options.addOption(officerConfirmPage_8,"officerConfirmPage_4");
        //fearless dose not work. no idea why.
        //this.options.addOption("fearless","officerConfirmPage_5");

        AIRetrofits_Dialog_PeopleMaker.addBack(options);
    }
    @Override
    public void confermPage(OptionPanelAPI options, InteractionDialogAPI dialog,String optionText, Object optionData){
        switch ((String)optionData) {
            case "officerConfirmPage_0":
                officerConfirmPage(0,options,dialog,optionText,optionData);
                break;
            case "officerConfirmPage_1":
                officerConfirmPage(1,options,dialog,optionText,optionData);
                break;
            case "officerConfirmPage_2":
                officerConfirmPage(2,options,dialog,optionText,optionData);
                break;
            case "officerConfirmPage_3":
                officerConfirmPage(3,options,dialog,optionText,optionData);
                break;
            case "officerConfirmPage_4":
                officerConfirmPage(4,options,dialog,optionText,optionData);
                break;
            case "officerConfirmPage_5":
                officerConfirmPage(5,options,dialog,optionText,optionData);
                break;
        }
    }
    @Override
    public void exitPage(OptionPanelAPI options, InteractionDialogAPI dialog,String optionText, Object optionData){
        Color highlight = Misc.getHighlightColor();
        dialog.getVisualPanel().showPersonInfo(personTemp);
        dialog.getTextPanel().addSkillPanel(personTemp,false);
        dialog.getTextPanel().addPara(exitOfficer_0,highlight,"" + officerCreditCost,""+officerSubCommandNodeCost);//"removed the credits and subcommand node");
        dialog.getTextPanel().addPara(exitOfficer_1);//"information about the officer you have created here");
        options.clearOptions();
        AIRetrofits_Dialog_PeopleMaker.addExit(options);
    }
    @Override
    public void createPerson(){
        Global.getSector().getPlayerFleet().getCargo().removeCommodity(AIRetrofits_Constants_3.Commodity_SubCommandNode,officerSubCommandNodeCost);
        Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(officerCreditCost);
        PersonAPI person = AIRetrofits_CreatePeople.createOfficer(temp);
        Global.getSector().getPlayerFleet().getFleetData().addOfficer(person);
        personTemp = person;
    }
}
