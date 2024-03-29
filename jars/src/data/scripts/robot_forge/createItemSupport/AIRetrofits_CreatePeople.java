package data.scripts.robot_forge.createItemSupport;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.FactionDoctrineAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import com.fs.starfarer.api.plugins.OfficerLevelupPlugin;
import com.fs.starfarer.api.util.WeightedRandomPicker;
import com.fs.starfarer.loading.specs.FactionDoctrine;
import com.fs.starfarer.rpg.Person;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.SpecalItems.AIRetrofit_CommandNode_SpecalItemData;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetorfit_CommandNodeTypesBase;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRetrofits_CreatePeople {
    public static ArrayList<AIRetorfit_CommandNodeTypesBase> CommandNodeTypes = new ArrayList<>();
    static AIRetrofits_CreatePeople logClass = new AIRetrofits_CreatePeople();
    static boolean logs = true;
    //PersonAPI person;
    public static PersonAPI createAdmen(){
        AIRetrofit_Log.loging("running: "+"createAdmen"+" with: ",logClass,logs);
        AIRetrofit_Log.push();
        PersonAPI person = OfficerManagerEvent.createAdmin(Global.getSector().getPlayerFaction(),0,new Random());
        setPerson(person);
        AIRetrofit_Log.pop();
        return person;
    }
    public static final String[] personalities = {"timid","cautious","steady","aggressive","reckless","fearless"};
    public static PersonAPI createOfficer(int personality){
        AIRetrofit_Log.loging("running: "+"createOfficer"+" with: personality:"+personality,logClass,logs);
        AIRetrofit_Log.push();
        PersonAPI person = OfficerManagerEvent.createOfficer(Global.getSector().getPlayerFaction(),1);
        person.setPersonality(personalities[personality]);
        setPerson(person);
        //possibility to add he possibility of having an elite skill to start?
        List<MutableCharacterStatsAPI.SkillLevelAPI> skillsCopy = person.getStats().getSkillsCopy();
        for(int a2 = 0; a2 < skillsCopy.size(); a2++) {
            MutableCharacterStatsAPI.SkillLevelAPI a = skillsCopy.get(a2);
            if (a.getSkill().isElite()) {
                person.getStats().decreaseSkill(a.getSkill().getId());
            }
        }
        AIRetrofit_Log.pop();
        return person;
    }
    public static void setPerson(PersonAPI person){
        AIRetrofit_Log.loging("running: "+"setPerson"+" with: person:"+person,logClass,logs);
        AIRetrofit_Log.push();
        //PersonAPI person = Global.getFactory().createPerson();
        person.setPortraitSprite(setDataLists.getRandom(2));
        person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
        AIRetrofit_Log.pop();
    }

/*
    public static PersonAPI createAdmen(float skillPower){
        //skillPower*=1000;
        float TempPower = skillPower;
        ArrayList<String> exstraSkills = new ArrayList<>();
        AIRetrofit_Log.loging("running: "+"createAdmen"+" with: skillPower:"+skillPower,logClass,logs);
        AIRetrofit_Log.push();
        float maxTier = 9999;
        maxTier = Math.min(maxTier,getMaxPossableNumberOfAdminSkills());
        maxTier = Math.min(maxTier,Admin_MaxTier);
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
        person.getTags().add(AIRetrofits_Constants.PersonTypes_Admin);
        setPerson(person);
        AIRetrofit_Log.pop();
        powerLastUsed = TempPower - skillPower;
        return person;
    }*/
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
    /*public static PersonAPI createOfficer(int personality, float skillPower) {
        float maxLV = 0;
        float maxESkills = 0;
        PersonAPI player = new Person();//Global.getSector().getPlayerPerson();
        player.setFleet(Global.getSector().getPlayerFleet());
        OfficerLevelupPlugin plugin = (OfficerLevelupPlugin) Global.getSettings().getPlugin("officerLevelUp");

        maxESkills = plugin.getMaxEliteSkills(player);
        maxLV = plugin.getMaxLevel(player);
        AIRetrofit_Log.loging("max lv and elet set to: "+maxLV+", "+maxESkills,logClass,logs);
        return createOfficer(personality,skillPower,maxLV,maxESkills);
    }*/
    /*
    public static PersonAPI createOfficer(int personality, float skillPower,float softMaxLevel,float softMaxEleteSkills){
        //skillPower*=15;
        AIRetrofit_Log.loging("running: "+"createOfficer"+" with: personality,skillPower: "+personality+", "+skillPower,logClass,logs);
        AIRetrofit_Log.push();
        //possibility to add he possibility of having an elite skill to start?
        //List<MutableCharacterStatsAPI.SkillLevelAPI> skillsCopy = person.getStats().getSkillsCopy();
        int level=0;
        int epics=0;
        int normal=0;
        float powerUsed = 0;
        AIRetrofit_Log.push();
        while(skillPower > 0){
            //determine what i can add this loop.
            ArrayList<ArrayList<Integer>> types = new ArrayList<>();
            if(epics < level){//can add epics.
                AIRetrofit_Log.loging("costs array: "+Officer_Costs[0][0]+","+Officer_Costs[0][1]+","+Officer_Costs[1][0]+","+Officer_Costs[1][1]+",",logClass,true);
                AIRetrofit_Log.loging("is less then?"+ (skillPower <= Officer_Costs[0][1])+","+(skillPower <= Officer_Costs[1][0]),logClass,true);
                if(epics < softMaxEleteSkills && skillPower >= Officer_Costs[0][1]){//can add moral epics.
                    AIRetrofit_Log.loging("adding type 2",logClass,true);
                    types.add(getLocationArray(0,1));
                }else if(skillPower >= Officer_Costs[1][1]){//can only add exstra epics.
                    AIRetrofit_Log.loging("adding type 3",logClass,true);
                    types.add(getLocationArray(1,1));
                }
            }
            if(level < softMaxLevel && skillPower >= Officer_Costs[0][0]){//can add normal level
                AIRetrofit_Log.loging("adding type 0",logClass,true);
                types.add(getLocationArray(0,0));
            }else if(skillPower >= Officer_Costs[1][0]){//can only add epic levels.
                AIRetrofit_Log.loging("adding type 1",logClass,true);
                types.add(getLocationArray(1,0));
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
        person.setPersonality(personalities[personality]);
        person.getTags().add(AIRetrofits_Constants.PersonTypes_Officer);
        setPerson(person);
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
        powerLastUsed = powerUsed;
        return person;
    }*/
    private static float powerLastUsed = 0;
    public static ArrayList<Integer> getLocationArray(int a, int b){
        ArrayList<Integer> temp = new ArrayList<>();
        temp.add(a);
        temp.add(b);
        return temp;
    }


    public static AIRetorfit_CommandNodeTypesBase getTypeByWeight(float[] weight){
        AIRetrofit_Log.loging("running: "+"getTypeByWeight"+" with: weight:"+weight.toString(),logClass,logs);
        AIRetrofit_Log.push();
        //String[] types = AIRetrofits_Constants.PersonTypes_List;
        AIRetorfit_CommandNodeTypesBase[] types = CommandNodeTypes.toArray(new AIRetorfit_CommandNodeTypesBase[CommandNodeTypes.size()]);
        float totalWeight=0;
        for(float a : weight){
            totalWeight+=a;
        }
        AIRetrofit_Log.loging("totalWeight: "+totalWeight,logClass,logs);
        float type = (float) (Math.random() * totalWeight);
        AIRetrofit_Log.loging("sellectedWeight:"+type,logClass,logs);
        String index;
        float b2=0;
        for(int b = 0; b < weight.length;b++){
            b2+= weight[b];
            AIRetrofit_Log.loging("compairing wieght and tope: "+b2+", "+type,logClass,logs);
            if(b2 >= type){
                AIRetrofit_Log.loging("returning type: "+types[b],logClass,logs);
                AIRetrofit_Log.pop();
                return types[b];
            }
        }
        AIRetrofit_Log.pop();
        return null;
    }
    public static AIRetorfit_CommandNodeTypesBase getTypeByWeight(){
        AIRetrofit_Log.loging("running: "+"getTypeByWeight"+" with: ",logClass,logs);
        AIRetrofit_Log.push();
        float[] weight = new float[CommandNodeTypes.size()]; //AIRetrofits_Constants.PersonWeight_List;
        for(int a = 0; a < CommandNodeTypes.size(); a++){
            weight[a] = CommandNodeTypes.get(a).weight;
        }
        AIRetrofit_Log.pop();
        return getTypeByWeight(weight);
    }
    public static int personalityMix(FactionAPI doctrine){
        AIRetrofit_Log.loging("running: "+"personalityMix"+" with: doctrine:"+doctrine.toString(),logClass,logs);
        AIRetrofit_Log.push();
        float maxChange = 10;
        int aggression=3;
        String temp = doctrine.getPersonalityPicker().pick();
        for(int a = 0; a < personalities.length; a++){
            if(personalities[a].equals(temp)){
                aggression = a;
                break;
            }
        }
        int max = 6;
        int min = 0;
        float changeChance = 0.1f;
        float changes = 0;
        while(maxChange < changes && Math.random() > 1 - changeChance){
            int change = -1;
            if((Math.random() > 0.5f && aggression < max) || aggression <= min) change = 1;
            aggression += change;
            changes++;
        }
        AIRetrofit_Log.pop();
        return aggression;
    }
    public static int getRandomPersonality(){
        AIRetrofit_Log.loging("running: "+"getRandomPersonality"+" with: ",logClass,logs);
        AIRetrofit_Log.push();
        int output = (int)(Math.random()*(personalities.length - 1));
        AIRetrofit_Log.pop();
        return output;
    }

    public static void addCores(CargoAPI cargo,int personality, float power, int numcores, float minPowerWeight, float maxPowerWeight){
        AIRetrofit_Log.loging("running: "+"addCores"+" with: cargo,personality,power,numcores,minPowerWeight,maxPowerWeight: "+cargo.toString()+", "+personality+", "+power+", "+numcores+", "+minPowerWeight+", "+maxPowerWeight,logClass,logs);
        AIRetrofit_Log.push();
        float[] powerWeight = new float[numcores];
        float totalWeight = 0;
        float powerPerWeight;
        for(int a = 0; a < powerWeight.length; a++){
            float temp = (float) ((Math.random() * (maxPowerWeight - minPowerWeight)) + minPowerWeight);
            powerWeight[a] = temp;
            totalWeight+= temp;
        }
        powerPerWeight = power / totalWeight;
        for(int a = 0; a < powerWeight.length; a++){
            addCore(cargo, (int) (powerPerWeight * powerWeight[a]),personality,AIRetrofits_CreatePeople.getTypeByWeight());
        }
        AIRetrofit_Log.pop();
    }
    public static void addCores(CargoAPI cargo, FactionAPI doctrineAPI, float power, int numcores, float minPowerWeight, float maxPowerWeight){
        AIRetrofit_Log.loging("running: "+"addCores"+" with: cargo,doctoring,power,numcores,minPowerWeight,maxPowerWeight: "+cargo.toString()+", "+doctrineAPI.toString()+", "+power+", "+numcores+", "+minPowerWeight+", "+maxPowerWeight,logClass,logs);
        AIRetrofit_Log.push();
        float[] powerWeight = new float[Math.max(numcores,0)];
        float totalWeight = 0;
        float powerPerWeight;
        for(int a = 0; a < powerWeight.length; a++){
            float temp = (float) ((Math.random() * (maxPowerWeight - minPowerWeight)) + minPowerWeight);
            powerWeight[a] = temp;
            totalWeight+= temp;
        }
        powerPerWeight = power / totalWeight;
        for(int a = 0; a < powerWeight.length; a++){
            int personality = personalityMix(doctrineAPI);
            addCore(cargo, (int) (powerPerWeight * powerWeight[a]),personality,AIRetrofits_CreatePeople.getTypeByWeight());
        }
        AIRetrofit_Log.pop();
    }
    public static void addCore(CargoAPI cargo,int power,int personality,AIRetorfit_CommandNodeTypesBase type){
        AIRetrofit_Log.loging("running: "+"addCore"+" with: cargo,power,personality,type:"+cargo.toString()+", "+power+", "+personality+", "+type,logClass,logs);
        AIRetrofit_Log.push();
        AIRetrofit_CommandNode item = new AIRetrofit_CommandNode();
        //AIRetrofit_Log.loging("cores power, personality,type,cost:"+power+", "+personality+", "+type+", "+cost,logClass,true);
        //amb.setPerson();
        PersonAPI person = type.createPersonForNode(cargo,power,personality);
        powerLastUsed = type.getAmountOfPowerUsed();
        type.setTagForPerson(person);
        int cost = 0;
        AIRetrofit_Log.loging("(used power should be <= to power.)got power, used power: "+power+", "+powerLastUsed,logClass,logs);
        for(int a = AIRetrofits_Constants.SpecalItemID_CommandNodes.length - 1; a >= 0; a--){
            if(AIRetrofits_Constants.SpecalItem_CommandNodes_thresholds[a] <= powerLastUsed){
                cost = a;
                break;
            }
        }
        AIRetrofit_CommandNode_SpecalItemData amb = new AIRetrofit_CommandNode_SpecalItemData(AIRetrofits_Constants.SpecalItemID_CommandNodes[cost], null, item.person);
        //amb.setPersonType(type);
        amb.setPerson(person);
        cargo.addSpecial(amb, 1);
        AIRetrofit_Log.pop();
    }

    private final static float maxRandomPower0 = Global.getSettings().getFloat("AIRetrofit_CommandNode_RandomPower_max");
    private final static float minRandomPower0 = Global.getSettings().getFloat("AIRetrofit_CommandNode_RandomPower_min");
    public static PersonAPI createPerson(){
        AIRetrofit_Log.loging("running: "+"createPerson"+" with: ",logClass,logs);
        return createPerson(AIRetrofits_CreatePeople.getTypeByWeight());
    }
    public static PersonAPI createPerson(AIRetorfit_CommandNodeTypesBase type){
        AIRetrofit_Log.loging("running: "+"createPerson"+" with: type: "+type,logClass,logs);
        float a = minRandomPower0 + Math.round(Math.random() * (maxRandomPower0) - minRandomPower0);
        //float b = minRandomPower1 + Math.round(Math.random() * (maxRandomPower1) - minRandomPower1);
        return createPerson((int)a,type);
    }
    public static PersonAPI createPerson(int power, AIRetorfit_CommandNodeTypesBase type){
        AIRetrofit_Log.loging("running: "+"createPerson"+" with: power,type"+power+", "+type,logClass,logs);
        return createPerson(power,type,getRandomPersonality());
    }
    public static PersonAPI createPerson(int power, AIRetorfit_CommandNodeTypesBase type,int personality){
        AIRetrofit_Log.loging("running: "+"createPerson"+" with: power,type,personality:"+power+", "+type+", "+personality,logClass,logs);
        PersonAPI person = type.createPersonForNode(null,power,personality);
        type.setTagForPerson(person);
        return person;
    }
}
