package data.scripts.robot_forge.createItemSupport.CommandNodeTypes;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoTransferHandlerAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.impl.campaign.rulecmd.SetStoryOption;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.rpg.Person;
import data.scripts.AIRetrofit_Log;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.robot_forge.dilogs.AIRetrofits_Dialog_PeopleMaker;
import data.scripts.startupData.AIRetrofits_Constants;
import exerelin.campaign.intel.agents.AgentIntel;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AIRetrofit_CommandNodeType_NexerlinOperative extends AIRetorfit_CommandNodeTypesBase{
    public static final float CostPerLevel = Global.getSettings().getFloat("AIRetrofit_CreatePerson_Nexerlin_Opertive_baseLevelCost");
    public static final float CostMultiIncreasePerLevel = Global.getSettings().getFloat("AIRetrofit_CreatePerson_Nexerlin_Opertive_LEvelCostMulti");
    public static final String CommandNodeType = Global.getSettings().getString("AIRetrofit_CommandNode_Nexerlin_Operative_Text");
    public static final String CommandNodeType2 = Global.getSettings().getString("AIRetrofit_CommandNode_Nexerlin_Operative_Text2");
    public static final int maxLevel = (int) Global.getSettings().getFloat("AIRetrofit_CreatePerson_Nexerlin_Opertive_MaxLevel");
    public AIRetrofit_CommandNodeType_NexerlinOperative(String name, float weight,boolean addToCommandNodes,boolean addToRobotForge) {
        super(name, weight,addToCommandNodes,addToRobotForge);
    }
    float tempPad = 0;
    @Override
    public void commandNodeTooltip(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource, AIRetrofit_CommandNode commandNode) {
        PersonAPI person = commandNode.person;
        float pad = 3f;
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        int level = person.getStats().getLevel();
        String type = getType(person).getName();
        TooltipMakerAPI text = tooltip.beginImageWithText(person.getPortraitSprite(), 48);
        text.addPara(CommandNodeType2,pad,highlight,person.getNameString(),""+level,CommandNodeType,type);
        tooltip.addImageWithText(opad);
        if(expanded){
            String text2 = "this command node is capable of doing the following jobs: ";
            String[] actions = getType(person).getAllowedActionNames(false).toArray(new String[0]);
            for(int a = 0; a < actions.length - 1; a++){
                text2 += actions[a] + ", ";
            }
            text2 += actions[actions.length - 1];
            tempPad = text.addPara(text2,pad,highlight,actions).computeTextHeight(text2)*4;
            //put what the operative can do here?
        }else{
            tempPad = 0;
        }
    }

    @Override
    public void addCostLabel(AIRetrofit_CommandNode specalItem, TooltipMakerAPI tooltip, float pad, CargoTransferHandlerAPI transferHandler, Object stackSource) {
        super.addCostLabel(specalItem, tooltip, pad+tempPad, transferHandler, stackSource);
    }

    @Override
    public PersonAPI createPersonForNode(CargoAPI cargo, int power, int personality) {
        int level=1;
        float powerUsed = CostPerLevel;
        float powerMulti = CostMultiIncreasePerLevel;
        while(level < maxLevel && powerUsed + (CostPerLevel * powerMulti) <= power){
            powerUsed+=(CostPerLevel * powerMulti);
            powerMulti*=CostMultiIncreasePerLevel;
            level++;
        }
        PersonAPI person = Global.getSector().getPlayerFaction().createRandomPerson();//;//OfficerManagerEvent.createAdmin(Global.getSector().getPlayerFaction(),tier,new Random());
        person.setRankId(Ranks.AGENT);
        person.setPostId(Ranks.POST_AGENT);

        /*AgentIntel intel = new AgentIntel(agent, Global.getSector().getPlayerFaction(), level);

        if (tmp.length > 2) {
            AgentIntel.Specialization spec = AgentIntel.Specialization.valueOf();
            intel.addSpecialization(spec);
        }*/
        String type = AgentIntel.Specialization.pickRandomSpecialization().getName();
        person.getStats().setLevel(level);
        person.addTag(type);


        AIRetrofits_CreatePeople.setPerson(person);
        this.setAmountOfPowerUsed((int)powerUsed);
        return person;
    }

    @Override
    public void performRightClickAction(PersonAPI person) {
        AgentIntel.Specialization type = getType(person);
        int level = person.getStats().getLevel();
        AgentIntel intel = new AgentIntel(person, Global.getSector().getPlayerFaction(), level);
        intel.addSpecialization(type);
        intel.setMarket(getClosestMarket());
        intel.init();
    }
    public static AgentIntel.Specialization getType(PersonAPI person){
        AgentIntel.Specialization type = AgentIntel.Specialization.HYBRID;
        if(person.hasTag(AgentIntel.Specialization.HYBRID.getName())){
            type = AgentIntel.Specialization.HYBRID;
        }
        if(person.hasTag(AgentIntel.Specialization.NEGOTIATOR.getName())){
            type = AgentIntel.Specialization.NEGOTIATOR;
        }
        if(person.hasTag(AgentIntel.Specialization.SABOTEUR.getName())){
            type = AgentIntel.Specialization.SABOTEUR;
        }
        return type;
    }
    public static MarketAPI getClosestMarket(){
        MarketAPI market = Global.getSector().getPlayerFleet().getMarket();
        if(market != null){
            return market;
        }
        /*market = Global.getSector().getPlayerFleet().getInteractionTarget().getMarket();
        if(market != null){
            return market;
        }*/
        double Distance = Double.MAX_VALUE;
        double HyperDistance = Double.MAX_VALUE;
        boolean found = false;
        boolean inSystem = false;
        float[] playerLocationHyper = {
                Global.getSector().getPlayerFleet().getLocationInHyperspace().getX(),
                Global.getSector().getPlayerFleet().getLocationInHyperspace().getY()
        };
        float[] playerLocation = {
                Global.getSector().getPlayerFleet().getLocation().getX(),
                Global.getSector().getPlayerFleet().getLocation().getY()
        };
        List<MarketAPI> temp = Global.getSector().getEconomy().getMarketsCopy();
        for(int a = 0; a < temp.size(); a++){
            double distanceTemp = Misc.getDistance(Global.getSector().getPlayerFleet(),temp.get(a).getPrimaryEntity());
            double hyperDistanceTemp = Misc.getDistanceToPlayerLY(temp.get(a).getPrimaryEntity());
            if(!found || hyperDistanceTemp <= HyperDistance || (hyperDistanceTemp == HyperDistance && distanceTemp < Distance)){
                if(!found || hyperDistanceTemp <= HyperDistance) {
                    Distance = distanceTemp;
                }
                HyperDistance = hyperDistanceTemp;
                market = temp.get(a);
                AIRetrofit_Log.loging("name, distance, distanceHyper, forced find: "+temp.get(a).getName()+", "+Distance+", "+HyperDistance+","+!found,new AIRetrofit_Log().getClass());
                found = true;
            }
        }
        return market;
    }






    private static final int officerCreditCost = AIRetrofits_Constants.RobotForge_officerCreditCost;//Global.getSettings().getInt("AIRetrofits_Officer_credits");//1000;
    private static final int officerSubCommandNodeCost = AIRetrofits_Constants.RobotForge_officerSubCommandNodeCost;//Global.getSettings().getInt("AIRetrofits_Officer_SCN");
    private static final int officerCreditsPerMomth = AIRetrofits_Constants.RobotForge_officerCreditsPerMomth;//900;
    private static final String officerPage_0 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_officerPage_0");
    private static final String officerConfirmPage_0 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_0");
    private static final String officerConfirmPage_1 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_1");
    private static final String officerConfirmPage_2 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_officerConfirmPage_2");
    private static final String exitOfficer_0 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_exitOfficer_0");
    private static final String exitOfficer_1 = Global.getSettings().getString("AIRetrofit_RobotForge_PeopleMaker_exitOfficer_1");
    private static final String[] personalities = {AgentIntel.Specialization.SABOTEUR.getName(),AgentIntel.Specialization.HYBRID.getName(),AgentIntel.Specialization.NEGOTIATOR.getName()};

    private int temp=0;
    private PersonAPI personTemp=null;
    private static final String MyOptionData = "operative";
    private static final String MyOptionText = "create operative";
    private static String MyOptionHoverOver = "requires a sub command node";
    public boolean canBuildCommandNode(){
        Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(AIRetrofits_Constants.Commodity_SubCommandNode);//,officerSubCommandNodeCost);
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
    private void addAdjent(){
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
        }
        temp = power;
        exstras = new String[]{"" + officerCreditsPerMomth};
        dialog.getTextPanel().addPara(officerConfirmPage_1,highlight,exstras);//"the officer you create will cost " + officerCreditsPerMomth + "per month. and more as they level up.");
        exstras = new String[]{"" + officerSubCommandNodeCost, "" + officerCreditCost};
        dialog.getTextPanel().addPara(officerConfirmPage_2,highlight,exstras);//"you require " + officerSubCommandNodeCost + " sub command node and " + officerCreditCost + " credits to create an officer");
        if(Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(AIRetrofits_Constants.Commodity_SubCommandNode) >= officerSubCommandNodeCost && Global.getSector().getPlayerFleet().getCargo().getCredits().get() >= officerCreditCost){
            options.addOption("continue","createOfficer");
        }
        SetStoryOption.set(dialog,1,"createOfficer","promoteCrewMember","ui_char_spent_story_point","");
        AIRetrofits_Dialog_PeopleMaker.addBack(options);
    }
    @Override
    public void startPage(OptionPanelAPI options, InteractionDialogAPI dialog, String optionText, Object optionData){
        dialog.getTextPanel().addPara(officerPage_0);
        options.clearOptions();
        options.addOption(personalities[0],"officerConfirmPage_0");
        options.addOption(personalities[1],"officerConfirmPage_1");
        options.addOption(personalities[2],"officerConfirmPage_2");
        //fearless dose not work. no idea why.
        //this.options.addOption("fearless","officerConfirmPage_5");

        options.addOption("back","menu");
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
        Global.getSector().getPlayerFleet().getCargo().removeCommodity(AIRetrofits_Constants.Commodity_SubCommandNode,officerSubCommandNodeCost);
        Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(officerCreditCost);
        PersonAPI person = AIRetrofits_CreatePeople.createPerson();
        String type2 = personalities[powerTemp];
        AgentIntel.Specialization type = AgentIntel.Specialization.HYBRID;
        if(type2.equals(AgentIntel.Specialization.HYBRID.getName())){
            type = AgentIntel.Specialization.HYBRID;
        }
        if(type2.equals(AgentIntel.Specialization.NEGOTIATOR.getName())){
            type = AgentIntel.Specialization.NEGOTIATOR;
        }
        if(type2.equals(AgentIntel.Specialization.SABOTEUR.getName())) {
            type = AgentIntel.Specialization.SABOTEUR;
        }
        AgentIntel intel = new AgentIntel(person, Global.getSector().getPlayerFaction(), 1);
        intel.addSpecialization(type);
        intel.setMarket(getClosestMarket());
        intel.init();
        personTemp = person;

    }

}
