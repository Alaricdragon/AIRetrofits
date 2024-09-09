package data.scripts.robot_forge.createItemSupport;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.ids.Skills;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.SpecalItems.AIRetrofit_CommandNode_SpecalItemData;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetorfit_CommandNodeTypesBase;
import data.scripts.startupData.AIRetrofits_Constants_3;

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
        //String[] types = AIRetrofits_Constants_3.PersonTypes_List;
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
        float[] weight = new float[CommandNodeTypes.size()]; //AIRetrofits_Constants_3.PersonWeight_List;
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
        for(int a = AIRetrofits_Constants_3.SpecalItemID_CommandNodes.length - 1; a >= 0; a--){
            if(AIRetrofits_Constants_3.SpecalItem_CommandNodes_thresholds[a] <= powerLastUsed){
                cost = a;
                break;
            }
        }
        AIRetrofit_CommandNode_SpecalItemData amb = new AIRetrofit_CommandNode_SpecalItemData(AIRetrofits_Constants_3.SpecalItemID_CommandNodes[cost], null, item.person);
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
