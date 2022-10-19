package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CharacterDataAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.starfarer.api.impl.campaign.rulemd.AIRetrofit_StartAutomatedColony;

import java.util.List;

//HERE. this might not be required or wanted in the slightest? i need to swap the people out with new people.
public class AIRetrofit_MakretListener  extends BaseCampaignEventListener {
    public static float time = 0;
    static String Condition = "AIRetrofit_AIPop";
    public AIRetrofit_MakretListener(boolean permaRegister) {
        super(permaRegister);
    }
    //static String look1 = Global.getSettings().getString("characters","AIRetofit_AIOfficer");
    @Override
    public void reportEconomyMonthEnd(){
        time++;
        /*Global.getSector().getEconomy().getMarketsCopy();
        for(MarketAPI market: Global.getSector().getEconomy().getMarketsCopy() ){
            if(market.hasCondition("AIRetrofit_AIPop") && market.getDaysInExistence() < 34) {
                List<PersonAPI> peopletemp = market.getPeopleCopy();
                //Global.getSettings().createPerson().setId("?");
                for (PersonAPI person : peopletemp) {
                    market.removePerson(person);
                    person.setPortraitSprite(setDataLists.getRandom(2));
                    person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
                    market.addPerson(person);
                }
            }*/
            /*if(false) {
                market = Global.getSector().getEconomy().getMarket(market.getId());
                market.getPeopleCopy().get(0).getId();
                for(PersonAPI person: market.getPeopleCopy()){
                    //detect if the person has been swapped out with an AI core already.
                    //if so, do nothing.
                    //if not so, swap it out with an AI core...
                    //or do i really really want to? I think i should.
                    //maybe.
                    //ill keep the name then change the image? maybe...
                    //should 100% do so when i create an market however...
                }

            }*/
            //market.getCommDirectory().getEntryForPerson().
        /*}*/
        /*if(setDataLists.fleetMod(fleet)){
            for(int a = 0; a < fleet.getFleetData().getMembersInPriorityOrder().size(); a++){
                fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain().setPortraitSprite(setDataLists.getRandom(2));
                fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain().setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
            }
        }*/
    }
    @Override
    public void reportPlayerOpenedMarket(MarketAPI market){
        if(market.hasCondition("AIRetrofit_AIPop")){
            changePeople(market);
        }
        addAIRetrofits();

        /*AIRetrofit_StartAutomatedColony.markets.size();
        for(int a = 0; a < AIRetrofit_StartAutomatedColony.markets.size(); a++){
            if(changePeople(Global.getSector().getEconomy().getMarket(AIRetrofit_StartAutomatedColony.markets.get(a)))){
                AIRetrofit_StartAutomatedColony.markets.remove(a);
                a--;
            }
        }*/
    }
    private boolean changePeople(MarketAPI market){
        List<PersonAPI> peopletemp = market.getPeopleCopy();
        if(peopletemp.size() > 1){
            for (PersonAPI person : peopletemp) {
                if(!market.getAdmin().equals(person) && market.getFaction().getId().equals(person.getFaction().getId())) {
                    market.removePerson(person);
                    person.setPortraitSprite(setDataLists.getRandom(2));
                    person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
                    market.addPerson(person);
                }
            }
            return true;
        }
        return false;
    }
    static String hullmod = "AIretrofit_AutomatedCrewReplacementDrones";
    static String hullmod2 = "AIretrofit_airetrofit";
    static String ability = "AIretrofit_robot_drone_forge";
    static String skill = "automated_ships";
    static boolean alwaysSkilled = Global.getSettings().getBoolean("AIRetrofit_alwaysGiveSkillsAndHullmods");
    private void addAIRetrofits(){
        CharacterDataAPI character = Global.getSector().getCharacterData();
        if(character.getPerson().getStats().hasSkill(skill) || alwaysSkilled){
            character.addAbility(ability);
            character.addHullMod(hullmod);
            character.addHullMod(hullmod2);
            return;
        }
        addReqAbility();
    }
    private void addReqAbility(){
        CharacterDataAPI character = Global.getSector().getCharacterData();
        for(String a: character.getAbilities()){
            if(a.equals(ability)){
                return;
            }
        }
        for(String a: character.getHullMods()) {
            if (a.equals(hullmod)) {
                character.addAbility(ability);
                return;
            }
        }
    }
}
