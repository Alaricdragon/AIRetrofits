package data.scripts.starfarer.api.impl.campaign.rulemd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.impl.campaign.procgen.NameGenData;
import com.fs.starfarer.api.impl.campaign.procgen.ProcgenUsedNames;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.impl.campaign.submarkets.StoragePlugin;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIWorldCode.Fleet.setDataLists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AIRetrofit_StartAutomatedColony extends BaseCommandPlugin {
    //protected static final String WING = Misc.ucFirst(StringHelper.getString("fighterWingShort"));
    //@Override
    //static public ArrayList<String> markets = new ArrayList<>();
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        //dialog.getTextPanel() // TextPanelAPI
        //if (dialog == null) return false;
        //dialog.getInteractionTarget().getMarket()
        //dialog.getInteractionTarget().getMarket().getPlanetEntity()
        //Global.getSector().getFaction(Factions.PLAYER);
        /**/
        MarketAPI a0 = dialog.getInteractionTarget().getMarket();
        PlanetAPI a1 = dialog.getInteractionTarget().getMarket().getPlanetEntity();
        FactionAPI a2 = Global.getSector().getFaction(Factions.PLAYER);
        if(a0.isPlayerOwned()){//hasIndustry("my_mod_automatedworldcore")) {
            //dialog.getTextPanel().addParagraph("THIS IS OUR WORLD NOW! GO GO ATTACK CARS!");
            //dialog.getTextPanel().addParagraph("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaaaaaaaaaaaaaaaaaaaaAAAAAAAAAA AAAAAAAAAAAAAAAAAAA");
        }
        //createColonyStatic(a0,a1,a2);
        //my_mod_DilogOptionTest
        //CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        //Global.getSector().getCampaignUI().showInteractionDialog(new my_mod_DilogOptionTest(dialog,fleet,a0,a1,a2,false,true), fleet);
        //createColonyStatic(a0, a1, a2, false, true);

        return true;
    }
/**/
    public static void createColonyStatic(MarketAPI market, PlanetAPI planet,
                                          FactionAPI faction, boolean fromDeciv, boolean isPlayer)
    {
        //log.info("Colonizing market " + market.getName() + ", " + market.getId());
        String factionId = faction.getId();

        market.setSize(3);
        market.addCondition("population_3");
        market.setFactionId(factionId);
        market.setPlanetConditionMarketOnly(false);
        //HERE
        /*market.getMemoryWithoutUpdate().set(MEMORY_KEY_COLONY, true);
        market.getMemoryWithoutUpdate().set(ExerelinConstants.MEMKEY_MARKET_STARTING_FACTION, factionId);*/

        // rename generic-name worlds
        if (market.getName().startsWith(planet.getStarSystem().getBaseName() + " ")) {
            String tag = NameGenData.TAG_PLANET;
            if (planet.isMoon()) tag = NameGenData.TAG_MOON;
            String newName = ProcgenUsedNames.pickName(tag, null, null).nameWithRomanSuffixIfAny;
            market.setName(newName);
            planet.setName(newName);
            ProcgenUsedNames.notifyUsed(newName);
        }

        if (market.hasCondition(Conditions.DECIVILIZED))
        {
            market.removeCondition(Conditions.DECIVILIZED);
            market.addCondition(Conditions.DECIVILIZED_SUBPOP);
        }
        market.addCondition("AIRetrofit_AIPop");
        market.addIndustry(Industries.POPULATION);
        //market.addIndustry("my_mod_automatedworldcore");
        // set growth level to 0%; probably not needed any more?
        // fixes insta colony upsize; see https://fractalsoftworks.com/forum/index.php?topic=5061.msg343893#msg343893
        market.setIncoming(new PopulationComposition());
//HERE
        /*
        NexFactionConfig config = NexConfig.getFactionConfig(factionId);
        if (config.freeMarket)
        {
            market.getMemoryWithoutUpdate().set("$startingFreeMarket", true);
            market.setFreePort(true);
        }
*/
        market.getTariff().modifyFlat("generator", Global.getSector().getFaction(factionId).getTariffFraction());
        //HERE
        /*NexUtilsMarket.setTariffs(market);

        // submarkets
        SectorManager.updateSubmarkets(market, Factions.NEUTRAL, factionId);*/
        market.addSubmarket(Submarkets.LOCAL_RESOURCES);
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);

        market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        for (MarketConditionAPI cond : market.getConditions())
        {
            cond.setSurveyed(true);
        }

        Global.getSector().getEconomy().addMarket(market, true);//HERE this is were the market adds people to it.
        market.getPrimaryEntity().setFaction(factionId);	// http://fractalsoftworks.com/forum/index.php?topic=8581.0

        if (!fromDeciv) {
            //HERE
            /*
            NexUtilsMarket.addPerson(Global.getSector().getImportantPeople(),
                    market, Ranks.CITIZEN, Ranks.POST_ADMINISTRATOR, true);
            if (!isPlayer)
                market.setImmigrationIncentivesOn(true);*/
        }
        if (fromDeciv) {
            market.addIndustry(Industries.SPACEPORT);
        }

        if (isPlayer) {
            market.setPlayerOwned(true);
            market.addIndustry(Industries.SPACEPORT);
            market.getIndustry(Industries.SPACEPORT).startBuilding();
            SubmarketAPI storage = market.getSubmarket(Submarkets.SUBMARKET_STORAGE);
            if (storage != null) {
                ((StoragePlugin)storage.getPlugin()).setPlayerPaidToUnlock(true);
            }
        }
        else {
            /*ColonyManager.buildIndustries(market);
            ColonyManager.getManager().processNPCConstruction(market);*/
        }
        //markets.add(market.getId());
        // planet desc change
        //HERE
        /*
        MarketDescChanger.getInstance().reportMarketTransfered(market, faction,
                Global.getSector().getFaction(Factions.NEUTRAL), false, false, null, 0);*/
        /*
        market.getPeopleCopy();
        market.getAdmin();
        market.getCommDirectory().getEntriesCopy()
         */
        /*List<PersonAPI> peopletemp = market.getPeopleCopy();
        //Global.getSettings().createPerson().setId("?");
        for(PersonAPI person:peopletemp) {
            market.removePerson(person);
            person.setPortraitSprite(setDataLists.getRandom(2));
            person.setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
            market.addPerson(person);
        }*/
    }

}
