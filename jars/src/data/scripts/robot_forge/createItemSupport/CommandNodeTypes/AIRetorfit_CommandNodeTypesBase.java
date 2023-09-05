package data.scripts.robot_forge.createItemSupport.CommandNodeTypes;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoTransferHandlerAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.rpg.Person;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;

public class AIRetorfit_CommandNodeTypesBase {
    public String name;
    public float weight;

    public AIRetorfit_CommandNodeTypesBase(String name,float weight){
        this.name = name;
        this.weight = weight;
        AIRetrofits_CreatePeople.CommandNodeTypes.add(this);
    }
    public void commandNodeTooltip(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource, AIRetrofit_CommandNode commandNode){

    }
    public PersonAPI createPersonForNode(CargoAPI cargo, int power, int personality){
        return new Person();
    }
    public int powerTemp=0;
    public void setAmountOfPowerUsed(int power){
        powerTemp=power;
    }
    public int getAmountOfPowerUsed(){
        return powerTemp;
    }
    public boolean isMyTypeOfCommandNode(PersonAPI person){
        return person.hasTag(name);
    }
    public void setTagForPerson(PersonAPI person){
        person.addTag(name);
    }
    public void performRightClickAction(PersonAPI person){

    }
}
