package data.scripts.starfarer.api.impl.campaign.rulemd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.ImmigrationPlugin;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.impl.campaign.procgen.NameGenData;
import com.fs.starfarer.api.impl.campaign.procgen.ProcgenUsedNames;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.fs.starfarer.api.util.Misc.getImmigrationPlugin;

public class my_mod_StartAutomatedColony extends BaseCommandPlugin {
    //protected static final String WING = Misc.ucFirst(StringHelper.getString("fighterWingShort"));
    //@Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        //dialog.getTextPanel() // TextPanelAPI
        //if (dialog == null) return false;
        dialog.getTextPanel().addParagraph("THIS IS OUR WORLD NOW! GO GO ATTACK CARS!");
        dialog.getTextPanel().addParagraph("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaaaaaaaaaaaaaaaaaaaaAAAAAAAAAA AAAAAAAAAAAAAAAAAAA");
        //dialog.getInteractionTarget().getMarket()
        //dialog.getInteractionTarget().getMarket().getPlanetEntity()
        //Global.getSector().getFaction(Factions.PLAYER);
        /**/
        MarketAPI a0 = dialog.getInteractionTarget().getMarket();
        PlanetAPI a1 = dialog.getInteractionTarget().getMarket().getPlanetEntity();
        FactionAPI a2 = Global.getSector().getFaction(Factions.PLAYER);
        createColonyStatic(a0,a1,a2);
        /**/
        /*/
        String a0 = Global.getSector().getFaction(Factions.PLAYER).getId();
        SectorEntityToken a1 = dialog.getInteractionTarget();
        ArrayList<SectorEntityToken> blank1 = new ArrayList<SectorEntityToken>();
        ArrayList<String> blank0 = new ArrayList<String>();
        addMarketplace(a0,a1,blank1,"name",3,blank0,blank0,25);
        /**/
        return true;
    }
/**/    public static void createColonyStatic(MarketAPI market, PlanetAPI planet,
                                          FactionAPI faction)
    {
        String factionId = faction.getId();

        market.setSize(3);
        market.addCondition("population_3");
        market.setFactionId(factionId);
        market.setPlanetConditionMarketOnly(false);
        //market.getMemoryWithoutUpdate().set(MEMORY_KEY_COLONY, true);
        //market.getMemoryWithoutUpdate().set(ExerelinConstants.MEMKEY_MARKET_STARTING_FACTION, factionId);

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
        //market.addIndustry(Industries.POPULATION);
        market.addIndustry("my_mod_automatedworldcore");

        // set growth level to 0%
        if (!faction.isPlayerFaction()) {
            ImmigrationPlugin plugin = getImmigrationPlugin(market);
            float min = plugin.getWeightForMarketSize(market.getSize());
            market.getPopulation().setWeight(min);
            market.getPopulation().normalize();
            market.getMemoryWithoutUpdate().set("$nex_delay_growth", true, 10);
        }

        //NexFactionConfig config = NexConfig.getFactionConfig(factionId);
        //if (config.freeMarket)
        //{
        //    market.getMemoryWithoutUpdate().set("$startingFreeMarket", true);
        //    market.setFreePort(true);
        //}
        //REMOVED TEST 0
        //market.getTariff().modifyFlat("generator", Global.getSector().getFaction(factionId).getTariffFraction());
        //NexUtilsMarket.setTariffs(market);

        // submarkets
        //SectorManager.updateSubmarkets(market, Factions.NEUTRAL, factionId);
        //REMOVED TEST 0
        market.setSurveyLevel(MarketAPI.SurveyLevel.FULL);
        for (MarketConditionAPI cond : market.getConditions())
        {
            cond.setSurveyed(true);
        }

        Global.getSector().getEconomy().addMarket(market, true);
        market.getPrimaryEntity().setFaction(factionId);	// http://fractalsoftworks.com/forum/index.php?topic=8581.0
        market.setPlayerOwned(true);
        market.addSubmarket(Submarkets.SUBMARKET_STORAGE);
        //if (!fromDeciv) {
        //    NexUtilsMarket.addPerson(Global.getSector().getImportantPeople(),
        //            market, Ranks.CITIZEN, Ranks.POST_ADMINISTRATOR, true);
            //market.setImmigrationIncentivesOn(true);
        //}
        //if (fromDeciv) {
            market.addIndustry(Industries.SPACEPORT);
        //}

        //ColonyManager.buildIndustries(market);
        //ColonyManager.getManager().processNPCConstruction(market);

        // planet desc change
        //MarketDescChanger.getInstance().reportMarketTransfered(market, faction,
                //Global.getSector().getFaction(Factions.NEUTRAL), false, false, null, 0);
    }
    /**/
}
