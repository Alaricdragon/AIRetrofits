package data.scripts.SpecalItems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionDoctrineAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.characters.PersonAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.startupData.AIRetrofits_Constants;

public class AIRetrofit_CommandNode_SpecalItemData extends SpecialItemData {
    PersonAPI person = null;
    public AIRetrofit_CommandNode_SpecalItemData(String id, String data,PersonAPI person) {
        super(id, data);
        this.person = person;
    }
    public AIRetrofit_CommandNode_SpecalItemData(String id, String data) {
        super(id, data);
        createPerson();
    }
    public PersonAPI getPerson(){
        return person;
    }
    public void setPerson(PersonAPI person){
        this.person = person;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = super.hashCode();
        result = prime * result + ((person == null) ? 0 : person.hashCode());
        return result;
    }

    @Override
    public void setData(String data) {
        super.setData(data);
    }

    @Override
    public String getData() {
        return super.getData();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        AIRetrofit_CommandNode_SpecalItemData other = (AIRetrofit_CommandNode_SpecalItemData) obj;
        if (getData() == null) {
            if (other.getData() != null)
                return false;
        } else if (!getData().equals(other.getData()))
            return false;

        if (getId() == null) {
            if (other.getId() != null)
                return false;
        } else if (!getId().equals(other.getId()))
            return false;

        if (person == null) {
            return other.person == null;
        } else return person.equals(other.person);

    }
    private final static float maxRandomPower0 = 7;
    private final static float minRandomPower0 = 1;
    private final static float maxRandomPower1 = 3;
    private final static float minRandomPower1 = 1;
    //String personType = "";
    public static String getPersonTypeByWeight(){
        String[] persons = {
                AIRetrofits_Constants.PersonTypes_Officer,
                AIRetrofits_Constants.PersonTypes_Admin
        };
        float[] weight = {
                AIRetrofits_Constants.PersonWeight_Officer,
                AIRetrofits_Constants.PersonWeight_Admin
        };
        float totalWeight = 0;
        for(float a : weight){
            totalWeight+=a;
        }
        float random = (float) (Math.random()*totalWeight);
        AIRetrofit_Log.loging("got a weight of: "+random,AIRetrofit_CommandNode.class,true);
        for(int a = 0; a < weight.length; a++){
            if(random <= weight[a]){
                return persons[a];
            }
            random -= weight[a];
        }
        return "ERROR";
    }
    public void createPerson(){
        createPerson(getPersonTypeByWeight());
    }
    public void createPerson(String type){
        float a = minRandomPower0 + Math.round(Math.random() * (maxRandomPower0) - minRandomPower0);
        float b = minRandomPower1 + Math.round(Math.random() * (maxRandomPower1) - minRandomPower1);
        createPerson((int)a,(int)b,type);
    }
    public void createPerson(int power,int power2, String type){
        AIRetrofit_Log.loging("got a power,power,type of: "+power+", "+power2+", "+type,this,true);
        switch (type){
            case AIRetrofits_Constants.PersonTypes_Officer:
                //this.personType = type;
                //person = AIRetrofits_CreatePeople.createOfficer(personalityMix(this.doctrine),power,power2);
                person = AIRetrofits_CreatePeople.createOfficer(personalityMix(Global.getSector().getPlayerFaction().getDoctrine()),power,power2);
                break;
            case AIRetrofits_Constants.PersonTypes_Admin:
                //this.personType = type;
                person = AIRetrofits_CreatePeople.createAdmen(power);
                break;
        }
    }
    private int personalityMix(FactionDoctrineAPI doctrine){
        float maxChange = 10;
        int aggression = doctrine.getAggression();
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
        AIRetrofit_Log.loging("got aggression level of: "+aggression,this,true);
        return aggression;
    }
}
