package data.scripts.AIWorldCode;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.intel.contacts.ContactIntel;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.List;

public class AIRetrofits_ChangePeople {
    public static void changePeopleMarket(MarketAPI market){
        if(!market.hasCondition(AIRetrofits_Constants_3.Market_Condition)){
            return;
        }
        List<PersonAPI> peopletemp = market.getPeopleCopy();
        if(peopletemp.size() > 1){
            for (PersonAPI person : peopletemp) {
                if(!market.getAdmin().equals(person) && AIRetrofits_ChangePeople.canChangePersonMarket(person,market)) {
                    market.removePerson(person);
                    person.setPortraitSprite(setDataLists.getRandom(2));
                    person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
                    market.addPerson(person);
                }
            }
        }
    }
    public static boolean canChangePersonMarket(PersonAPI personAPI, MarketAPI market) {

        if(!market.getFaction().getId().equals(personAPI.getFaction().getId())) {
            return false;
        }
        if(setDataLists.matches(0, personAPI.getName().getFirst())
            && setDataLists.matches(1, personAPI.getName().getLast())
            && setDataLists.matches(2, personAPI.getPortraitSprite())
        ){
            return false;
        }
        /*if(Global.getSector().getImportantPeople().containsPerson(personAPI)){//maybe this will help for avoiding getting story people? hopefully?
            return false;
        }*/
        if(ContactIntel.playerHasContact(personAPI,true)){//does not change contacts
            return false;
        }
        return true;
    }
    public static boolean canChangePersonFleet(PersonAPI person){
        if(person.getNameString().length() == 0) {
            return false;
        }
        return true;
    }
    public static void changePeopleFleet(CampaignFleetAPI fleet){
        if(setDataLists.fleetMod(fleet)){
            for(int a = 0; a < fleet.getFleetData().getMembersInPriorityOrder().size(); a++){
                if(canChangePersonFleet(fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain())) {
                    changePerson(fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain());
                    swapFleetCrew(fleet);
                }
            }
        }
        if ((fleet.hasTag(AIRetrofits_Constants_3.TAG_FORCE_AI_COMMANDER) || (fleet.getCommander() != null && fleet.getCommander().hasTag(AIRetrofits_Constants_3.TAG_FORCE_AI_COMMANDER))) && canChangePersonFleet(fleet.getCommander())){
            changePerson(fleet.getCommander());
        }
        if ((fleet.hasTag(AIRetrofits_Constants_3.TAG_FORCE_AI_OFFICERS) || (fleet.getCommander() != null && fleet.getCommander().hasTag(AIRetrofits_Constants_3.TAG_FORCE_AI_OFFICERS)))){
            for(int a = 0; a < fleet.getFleetData().getMembersInPriorityOrder().size(); a++){
                if(canChangePersonFleet(fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain())) {
                    changePerson(fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain());
                    swapFleetCrew(fleet);
                }
            }
        }
        /*else{
            if (fleet.getFaction().isPlayerFaction() && setDataLists.CommanderTagCheck(fleet.getCommander())){
                for(int a = 0; a < fleet.getFleetData().getMembersInPriorityOrder().size(); a++){
                    PersonAPI person = fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain();
                    if (canChangePersonFleet(person)){
                        for(int b = 0; b < 10 && person.getPortraitSprite().equals("graphics/portraits/AIRetrofits_AICaption1.png"); b++){
                            person.setPortraitSprite(Global.getSector().getPlayerFaction().getPortraits(person.getGender()).pick());
                        }
                    }
                }
            }
        }*/
    }
    public static void changePerson(PersonAPI person){
        person.setPortraitSprite(setDataLists.getRandom(2));
        person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
    }
    static private String[] itemIn = AIRetrofits_Constants_3.fleetChange_itemIn;
    static private String[] itemOut = AIRetrofits_Constants_3.fleetChange_itemOut;
    static private boolean ChangeCrew = AIRetrofits_Constants_3.fleetChange_ChangeCrew;
    public static void swapFleetCrew(CampaignFleetAPI fleet){
        if(!ChangeCrew){
            return;
        }
        for(int a = 0; a < itemIn.length; a++) {
            float in = fleet.getCargo().getCommodityQuantity(itemIn[a]);
            fleet.getCargo().removeCommodity(itemIn[a],in);
            float pi = Global.getSector().getEconomy().getCommoditySpec(itemIn[a]).getBasePrice();
            float po = Global.getSector().getEconomy().getCommoditySpec(itemOut[a]).getBasePrice();
            in = in*(pi/po);
            fleet.getCargo().addCommodity(itemOut[a],in);
        }
    }
}
