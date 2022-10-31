package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.CharacterDataAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import data.scripts.AIWorldCode.Fleet.setDataLists;

import java.util.List;

//HERE. this might not be required or wanted in the slightest? i need to swap the people out with new people.
public class AIRetrofit_MakretListener  extends BaseCampaignEventListener {
    static String Condition = "AIRetrofit_AIPop";
    public AIRetrofit_MakretListener(boolean permaRegister) {
        super(permaRegister);
    }
    //static String look1 = Global.getSettings().getString("characters","AIRetofit_AIOfficer");
    @Override
    public void reportEconomyMonthEnd(){
        runAIRetrofit_Shipyard();
    }
    @Override
    public void reportPlayerOpenedMarket(MarketAPI market){
        if(market.hasCondition("AIRetrofit_AIPop")){
            changePeople(market);
        }
        addAIRetrofits();
        unapplySubMarkets(market);
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
    final static String shipYardIndustry = "AIRetrofit_shipYard";
    final static String shipYardSubmarket = "AIRetrofit_ShipyardSubmarket";
    private void runAIRetrofit_Shipyard(){
        for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
            runSingleAIRetrofit_Shipyard(market);
        }
    }
    private void runSingleAIRetrofit_Shipyard(MarketAPI market){
        market = Global.getSector().getEconomy().getMarket(market.getId());
        if(!market.hasSubmarket(shipYardSubmarket)){
            return;
        }/*else{
            int[] a = {};
            a[1] = a[2];//crash the game. as an console log lol.
        }*/
        final String[] stopHullMods = {"automated"};
        final String[] addHullMods = {
                "AIRetrofit_ShipyardGamma",
                "AIRetrofit_ShipyardBeta",
                "AIRetrofit_ShipyardAlpha",
                "AIRetrofit_ShipyardOmega",
                "AIRetrofit_ShipyardBase"};
        final float startingPonits = 8;
        final float bounus = 2;
        final float[] costs = {4,0.25f,1,2,4,8};
        List<FleetMemberAPI> ships = market.getSubmarket(shipYardSubmarket).getCargo().getMothballedShips().getMembersListCopy();
        //market.getSubmarket(shipYardSubmarket).getCargo().getMothballedShips().get
        //market.getSubmarket(shipYardSubmarket).getCargo().

        float points = startingPonits;
        String addHullMod;
        if(market.getIndustry(shipYardIndustry).isImproved()){
            points *= bounus;
        }
        try {
            switch (market.getIndustry(shipYardIndustry).getAICoreId()) {
                case "gamma_core":
                    addHullMod = addHullMods[0];
                    break;
                case "beta_core":
                    addHullMod = addHullMods[1];
                    break;
                case "alpha_core":
                    addHullMod = addHullMods[2];
                    break;
                case "omega_core"://are omega cores even obtainable?
                    addHullMod = addHullMods[3];
                    break;
                default:
                    addHullMod = addHullMods[4];
                    break;
            }
        }catch (Exception e){
            addHullMod = addHullMods[4];
        }
        for(FleetMemberAPI ship : ships){
            boolean exit = false;
            for(String a : stopHullMods) {
                if(ship.getVariant().hasHullMod(a)) {
                    exit = true;
                    break;
                }
            }
            if(!exit && !ship.getVariant().hasHullMod(addHullMod)){
                float cost = 0;
                switch (ship.getVariant().getHullSize()){
                    case DEFAULT:
                        cost = costs[0];
                        break;
                    case FIGHTER:
                        cost = costs[1];
                        break;
                    case FRIGATE:
                        cost = costs[2];
                        break;
                    case DESTROYER:
                        cost = costs[3];
                        break;
                    case CRUISER:
                        cost = costs[4];
                        break;
                    case CAPITAL_SHIP:
                        cost = costs[5];
                        break;
                }
                if(cost <= points){
                    points -= cost;
                    for(String a : addHullMods){
                        ship.getVariant().removeMod(a);
                    }
                    ship.getVariant().addMod(addHullMod);
                }
                if(points <= 0){
                    return;
                }
            }
        }
    }
    private void unapplySubMarkets(MarketAPI market){
        if(market.hasSubmarket(shipYardSubmarket) && !market.hasIndustry(shipYardIndustry)){
            market.removeSubmarket(shipYardSubmarket);
        }
    }
}
