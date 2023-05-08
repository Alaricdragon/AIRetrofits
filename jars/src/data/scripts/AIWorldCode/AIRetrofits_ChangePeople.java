package data.scripts.AIWorldCode;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.intel.contacts.ContactIntel;
import com.fs.starfarer.rpg.Person;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.List;

public class AIRetrofits_ChangePeople {
    public static void changePeopleMarket(MarketAPI market){
        if(!market.hasCondition(AIRetrofits_Constants.Market_Condition)){
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
                    fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain().setPortraitSprite(setDataLists.getRandom(2));
                    fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain().setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
                    swapFleetCrew(fleet);
                }
            }
        }
    }
    static private String[] itemIn = AIRetrofits_Constants.fleetChange_itemIn;
    static private String[] itemOut = AIRetrofits_Constants.fleetChange_itemOut;
    static private boolean ChangeCrew = AIRetrofits_Constants.fleetChange_ChangeCrew;
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
