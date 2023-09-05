package data.scripts.robot_forge.createItemSupport.CommandNodeTypes;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoTransferHandlerAPI;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.plugins.OfficerLevelupPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.rpg.Person;
import data.scripts.AIRetrofit_Log;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.startupData.AIRetrofits_Constants;

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
    public AIRetrofit_CommandNodeType_Officer(String name, float weight) {
        super(name, weight);
    }
    private final static String officerText = Global.getSettings().getString("AIRetrofit_CommandNode_officerText");//"officer";
    private final static String officerText2 = Global.getSettings().getString("AIRetrofit_CommandNode_officerText2");//"a command node with the designation of %s. they are a %s of level %s with a personality of %s";
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
}
