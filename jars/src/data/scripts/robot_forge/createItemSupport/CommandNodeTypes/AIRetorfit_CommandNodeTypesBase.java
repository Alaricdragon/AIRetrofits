package data.scripts.robot_forge.createItemSupport.CommandNodeTypes;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoTransferHandlerAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.rpg.Person;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.robot_forge.dilogs.AIRetrofits_Dialog_PeopleMaker;

public class AIRetorfit_CommandNodeTypesBase {
    public String name;
    public float weight;

    public AIRetorfit_CommandNodeTypesBase(String name,float weight,boolean addToCommandNodes,boolean addToRobotForge){
        this.name = name;
        this.weight = weight;
        if(addToCommandNodes) {
            AIRetrofits_CreatePeople.CommandNodeTypes.add(this);
        }
        if(addToRobotForge){
            AIRetrofits_Dialog_PeopleMaker.peopleTypes.add(this);
        }
    }
    /*code for command node.*/
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

    public boolean hasRightClickAction(){
        return true;
    }
    public boolean shouldRemoveOnRightClickAction(){
        return true;
    }
    public void performRightClickAction(PersonAPI person){

    }
    public void addCostLabel(AIRetrofit_CommandNode specalItem,TooltipMakerAPI tooltip, float pad, CargoTransferHandlerAPI transferHandler, Object stackSource) {
        specalItem.addCostLabel(tooltip, pad, transferHandler, stackSource);
    }
    public boolean canBuildPastMaxNumber(){
        return false;
    }
    public boolean isAtMaxAmount(){
        return false;
    }
    public boolean canAddPerson(){
        if(isAtMaxAmount() && !canBuildPastMaxNumber()) return false;
        return true;
    }
    /*code for create people*/
    public void createOptionForCore(OptionPanelAPI options){
        options.addOption("ERROR failed to get possibility for class of "+this.getClass().getCanonicalName(),"","requires a sub command node");
    }
    public boolean optionSelected(String optionText, Object optionData) {
        return false;
    }
    public void startPage(OptionPanelAPI options, InteractionDialogAPI dialog,String optionText, Object optionData){

    }
    public void confermPage(OptionPanelAPI options, InteractionDialogAPI dialog,String optionText, Object optionData){

    }
    public void exitPage(OptionPanelAPI options, InteractionDialogAPI dialog,String optionText, Object optionData){

    }
    public void createPerson(){

    }

}
