package data.scripts.robot_forge.createItemSupport.CommandNodeTypes;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoTransferHandlerAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.impl.campaign.rulecmd.SetStoryOption;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.rpg.Person;
import data.scripts.AIRetrofit_Log;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.robot_forge.dilogs.AIRetrofits_Dialog_PeopleMaker;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRetrofit_CommandNodeType_Admin extends AIRetorfit_CommandNodeTypesBase{
    public AIRetrofit_CommandNodeType_Admin logClass = this;
    public boolean logs = false;
    public AIRetrofit_CommandNodeType_Admin(String name, float weight,boolean addToCommandNodes,boolean addToRobotForge) {
        super(name, weight,addToCommandNodes,addToRobotForge);
    }

    public static final float[] Admin_Costs = {
            //base cost of skills / cost multi per skill that said admin has
            Global.getSettings().getFloat("AIRetrofit_CreatePerson_Admin_baseSkillCost"),Global.getSettings().getFloat("AIRetrofit_CreatePerson_Admin_SkillMultiPerLevel")
    };
    public static final int Admin_MaxTier = Global.getSettings().getInt("AIRetrofit_CreatePerson_Admin_MaxTier");
    public static final float Admin_PowerForHypercognition = Global.getSettings().getFloat("AIRetrofit_CreatePerson_Admin_PowerForhypercognition");
    @Override
    public PersonAPI createPersonForNode(CargoAPI cargo, int skillPower, int personality) {
        //skillPower*=1000;
        float TempPower = skillPower;
        ArrayList<String> exstraSkills = new ArrayList<>();
        AIRetrofit_Log.loging("running: "+"createAdmen"+" with: skillPower:"+skillPower,logClass,logs);
        AIRetrofit_Log.push();
        float maxTier = 9999;
        maxTier = Math.min(maxTier,getMaxPossableNumberOfAdminSkills());
        maxTier = Math.min(maxTier,Admin_MaxTier);
        /*skillPower represents the possibility to add more skills to this caption on creation. */
        float cost = Admin_Costs[0];
        int tier = 0;
        while(skillPower >= cost && tier < maxTier){
            tier+=1;
            skillPower-=cost;
            cost *= Admin_Costs[1];
        }
        AIRetrofit_Log.loging("got a admin tier of "+tier+". ",logClass,logs);
        PersonAPI person = OfficerManagerEvent.createAdmin(Global.getSector().getPlayerFaction(),tier,new Random());

        if(skillPower >= Admin_PowerForHypercognition){
            AIRetrofit_Log.loging("giving admin an specal skill (has "+skillPower+" power. requires at least "+Admin_PowerForHypercognition+" power).",logClass,logs);
            skillPower-= Admin_PowerForHypercognition;
            exstraSkills.add("hypercognition");
        }
        for(String a : exstraSkills){
            person.getStats().increaseSkill(a);
        }
        AIRetrofit_Log.loging("this admin has "+person.getStats().getSkillsCopy().size()+" skills.",logClass,logs);
        AIRetrofit_Log.push();
        for(int a = 0; a < person.getStats().getSkillsCopy().size(); a++){
            if(person.getStats().getSkillsCopy().get(a).getSkill().isAdminSkill()) {
                AIRetrofit_Log.loging("skill: " + person.getStats().getSkillsCopy().get(a), logClass, logs);
            }
        }
        AIRetrofit_Log.pop();
        AIRetrofits_CreatePeople.setPerson(person);
        AIRetrofit_Log.pop();
        this.setAmountOfPowerUsed((int) (TempPower - skillPower));
        return person;
    }

    private final static String adminText = Global.getSettings().getString("AIRetrofit_CommandNode_adminText");//"admin";
    private final static String adminText2 = Global.getSettings().getString("AIRetrofit_CommandNode_adminText2");//"a command node with the designation of %s. they are a %s";
    @Override
    public void commandNodeTooltip(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource, AIRetrofit_CommandNode commandNode) {
        PersonAPI person = commandNode.person;
        float pad = 3f;
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        List<MutableCharacterStatsAPI.SkillLevelAPI> skills = person.getStats().getSkillsCopy();
        int level = 0;
        for(int a = 0; a < skills.size(); a++){
            if(skills.get(a).getSkill().isAdminSkill()){
                level++;
            }
        }
        String type = adminText;
        TooltipMakerAPI text = tooltip.beginImageWithText(person.getPortraitSprite(), 48);
        text.addPara(adminText2,pad,highlight,person.getNameString(),type,""+level);
        tooltip.addImageWithText(opad);
        if(expanded){
            //TextPanelAPI.addSkillPanel(person, true);
            //text.;
            //tooltip.addIconGroup(5);
            //ArrayList<String> skillsTemp = new ArrayList<>();
            List<MutableCharacterStatsAPI.SkillLevelAPI> skillsCopy = person.getStats().getSkillsCopy();
            int stat = 0;
            int stat2 = 0;
            for(int a2 = 0; a2 < skillsCopy.size(); a2++) {
                MutableCharacterStatsAPI.SkillLevelAPI a = skillsCopy.get(a2);
                if (/*true||*/a.getSkill().isAdminSkill()) {
                    text = tooltip.beginImageWithText(a.getSkill().getSpriteName(), 32);
                    text.addPara(a.getSkill().getName(), opad);
                    tooltip.addImageWithText(opad);
                    /*if (stat == 0) {
                        text = tooltip.beginImageWithText(a.getSkill().getSpriteName(), 32);
                        text.addPara(a.getSkill().getName(), opad);
                        stat = 1;
                    }else{
                        text.addImage(a.getSkill().getSpriteName(), 32);
                        text.addPara(a.getSkill().getName(), opad);
                    }
                    stat2++;
                    if (stat2 >= 2) {
                        tooltip.addImageWithText(opad);
                        stat = 0;
                        stat2 = 0;
                    }*/
                }
                /*if (stat2 != 0) {
                    tooltip.addImageWithText(opad);
                }*/
            }
            /*
            String[] temp = new String[skillsTemp.size()];
            for(int a = 0; a < skillsTemp.size(); a++){
                temp[a] = skillsTemp.get(a);
            }
            tooltip.addImages(20,20,5,5,temp);*/
            //tooltip.addSkillPanelOneColumn(person,pad);
            //tooltip.addSkillPanel(person,pad);
        }
    }
    @Override
    public void performRightClickAction(PersonAPI person){
        Global.getSector().getCharacterData().addAdmin(person);
    }


    public static int getMaxPossableNumberOfAdminSkills(){
        int a = 0;
        List<String> allSkillIds = Global.getSettings().getSortedSkillIds();
        for (String skillId : allSkillIds) {
            SkillSpecAPI skill = Global.getSettings().getSkillSpec(skillId);
            if (skill.hasTag(Skills.TAG_DEPRECATED)) continue;
            if (skill.hasTag(Skills.TAG_PLAYER_ONLY)) continue;
            if (skill.hasTag(Skills.TAG_AI_CORE_ONLY)) continue;
            if (skill.isAdminSkill()) {
                a++;
            }
        }
        return a;
    }








    private static final String admenConfirmPage_0 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_admenConfirmPage_0");
    private static final String admenConfirmPage_1 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_admenConfirmPage_1");
    private static final String admenConfirmPage_2 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_admenConfirmPage_2");
    private static final String exitAdmen_0 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_exitAdmen_0");
    private static final String exitAdmen_1 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_exitAdmen_1");
    private static final int administratorCreditCost = AIRetrofits_Constants.RobotForge_administratorCreditCost;//Global.getSettings().getInt("AIRetrofits_Admin_credits");///1000;
    private static final int administratorSubCommandNodeCost = AIRetrofits_Constants.RobotForge_administratorSubCommandNodeCost;//Global.getSettings().getInt("AIRetrofits_Admin_SCN");
    private static final int administratorCreditsPerMomth = AIRetrofits_Constants.RobotForge_administratorCreditsPerMomth;//2000;
    private static final String MyOptionData = "admin";
    private static final String MyOptionText = "create admin";
    private static final String MyOptionHoverOver = "requires a sub command node";
    public boolean canBuildCommandNode(){
        return false;
    }
    @Override
    public void createOptionForCore(OptionPanelAPI options){
        options.addOption(MyOptionText,MyOptionData,MyOptionHoverOver);
        //options.setEnabled(MyOptionData,canBuildCommandNode());
    }
    @Override
    public boolean optionSelected(String optionText, Object optionData) {
        String optionDataTemp = (String)optionData;
        return optionDataTemp.equals(MyOptionData);
    }
    @Override
    public void startPage(OptionPanelAPI options, InteractionDialogAPI dialog, String optionText, Object optionData){
        AIRetrofits_Dialog_PeopleMaker.stat++;
        confermPage(options,dialog,optionText,optionData);
    }
    @Override
    public void confermPage(OptionPanelAPI options, InteractionDialogAPI dialog,String optionText, Object optionData){
        Color highlight = Misc.getHighlightColor();
        dialog.getTextPanel().addPara(admenConfirmPage_0);//"information about the admen, and its cost go here");
        options.clearOptions();
        dialog.getTextPanel().addPara(admenConfirmPage_1,highlight,new String[]{"" + administratorCreditsPerMomth});//"the officer you create will cost " + administratorCreditsPerMomth + "per month. at an reduced cost if they are not doing anything.");
        dialog.getTextPanel().addPara(admenConfirmPage_2,highlight, "" + administratorSubCommandNodeCost,"" + administratorCreditCost);//"you require " + administratorSubCommandNodeCost + " sub command node and " + administratorCreditCost + " credits to create an administrator");
        if(Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(AIRetrofits_Constants.Commodity_SubCommandNode) >= administratorSubCommandNodeCost && Global.getSector().getPlayerFleet().getCargo().getCredits().get() >= administratorCreditCost){
            options.addOption("continue","createAdmen");
        }
        SetStoryOption.set(dialog,1,"createAdmen","promoteCrewMember","ui_char_spent_story_point","");
        AIRetrofits_Dialog_PeopleMaker.addBack(options);

    }
    protected PersonAPI personTemp = null;
    @Override
    public void exitPage(OptionPanelAPI options, InteractionDialogAPI dialog,String optionText, Object optionData){
        Color highlight = Misc.getHighlightColor();
        dialog.getVisualPanel().showPersonInfo(personTemp);
        dialog.getTextPanel().addSkillPanel(personTemp,true);
        dialog.getTextPanel().addPara(exitAdmen_0,highlight,""+administratorCreditCost,""+administratorSubCommandNodeCost);//"removed the credits and subcommand node");
        dialog.getTextPanel().addPara(exitAdmen_1);//"information about the admen you have created here");
        options.clearOptions();
        AIRetrofits_Dialog_PeopleMaker.addExit(options);
    }
    @Override
    public void createPerson(){
        Global.getSector().getPlayerFleet().getCargo().removeCommodity(AIRetrofits_Constants.Commodity_SubCommandNode,administratorSubCommandNodeCost);
        Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(administratorCreditCost);
        PersonAPI person = AIRetrofits_CreatePeople.createAdmen();
        Global.getSector().getCharacterData().addAdmin(person);
        personTemp = person;

    }
}
