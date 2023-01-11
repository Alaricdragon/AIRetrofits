package data.scripts.AIWorldCode.growth;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import data.scripts.CrewReplacer_Log;

import java.util.List;

public class AIRetrofit_MarketGrowthMods implements MarketImmigrationModifier {
    static String id3 = "population_AIRetrofit_3";
    static String id4 = "population_AIRetrofit_4";
    static String id10 = "AIPopRemover";
    static String condition = "AIRetrofit_AIPop";
    public static void applyData(MarketAPI market, String ID){
        undoExtraData(market,ID);
        if(!market.hasCondition(condition)){
            return;
        }
        modifyGrowth(market,ID);
        /*if(industry.getMarket().isImmigrationIncentivesOn()){
            industry.getDemand("metals").getQuantity().modifyFlat(id3,industry.getMarket().getSize()," from hazard pay building drones");
            industry.getDemand("rare_metals").getQuantity().modifyFlat(id3,industry.getMarket().getSize()," from hazard pay building drones");
            industry.getDemand("heavy_machinery").getQuantity().modifyFlat(id3,industry.getMarket().getSize()," from hazard pay building drones");

            Pair<String, Integer> deficit = industry.getMaxDeficit("metals", "rare_metals", "heavy_machinery");
            if(deficit.two > 0) {
                float loss = 1 - (industry.getMarket().getSize() / deficit.two);
                industry.getMarket().getIncoming().getWeight().modifyFlat(id4, loss * ((industry.getMarket().getSize() * 8) - 10) * -1, " missing resources for hazard pay's building drones");
            }
        }else{
            undoExtraData(industry,ID);
        }*/
    }
    public static void undoExtraData(MarketAPI market,String ID){
    }
    private static void modifyGrowth(MarketAPI market, String ID){
        //CrewReplacer_Log.loging("ERROR: outdated modifing data errror",new AIRetrofit_MarketGrowthMods(),true);
        PopulationComposition incoming = market.getIncoming();
        String[] activeGrowth = {//HERE <-- this needs to be swapped with data from config
                "population_AIRetrofit_0",
                "population_AIRetrofit_1",
                "population_AIRetrofit_2",
                "population_AIRetrofit_3",
                "AIRetrofits_BasicDroneFactory_0",
                "AIRetrofits_AdvancedDroneFactory_0",
                T1FactoryName,
                T2FactoryName,
                hazzardPayName,
                "AIRetrofits_RobotFactoryGrowthMod"
        };
        /*
        String cancalGrowth = "AIRetrofit_noGrowth";
        String replecate = "AIRetrofit_replicate";
        incoming.getWeight().unmodify("AIRetrofit_noGrowth");
        float multi = incoming.getWeight().getMult();
        incoming.getWeight().modifyMult(cancalGrowth,0,"remove unwanted growth");
        incoming.getWeight().modifyMultAlways(replecate,multi,"re adding modify multi");*/

        int a = 0;
        while(incoming.getWeight().getFlatMods().keySet().size() != 0 && a < incoming.getWeight().getFlatMods().keySet().size()){
            boolean out = true;
            String m = incoming.getWeight().getFlatMods().keySet().toArray()[a].toString();
            for (String s : activeGrowth) {
                if (m.equals(s)) {
                    a++;
                    out = false;
                    //incoming.getWeight().getFlatStatMod(s).getDesc()
                    break;
                }
            }
            if(out) {
                //String name = incoming.getWeight().getFlatMods().keySet().toArray()[a].toString();
                //String dec = incoming.getWeight().getFlatStatMod(name).desc;
                //incoming.getWeight().modifyFlat(name,0,dec);
                //a++;
                incoming.getWeight().unmodify(incoming.getWeight().getFlatMods().keySet().toArray()[a].toString());
            }
        }
        /*for(int a = 0; a < incoming.getWeight().getFlatMods().keySet().size(); a++){
            incoming.getWeight().unmodify(incoming.getWeight().getFlatMods().keySet().toArray()[a].toString());
        }*/
        //incoming.getWeight().modifyFlat(getModId(),getDefaltGrowth(market), Misc.ucFirst(condition.getName().toLowerCase()));

        /*if(market.isImmigrationIncentivesOn()){
            //market.getIndustry("population").getDemand();
            //Pair<String, Integer> deficit = market.getIndustry("population").getMaxDeficit("??","??");
            //int missing = deficit.two;
            incoming.getWeight().modifyFlat(ID,getIncentivesGrowth(market), "building robots with hazard pay");
        }*/

        //market.removeImmigrationModifier();//HERE. this might be usefull. might need to swap data from the market growth for diffrent population comps
        //applyGrowth(incoming,market);
        //market.setIncoming(incoming);
    }
    private static String T1FactoryName = "LocalRobotFactory's";
    private static String T1FactoryDescription = Global.getSettings().getString("AIRetrofits_MarketGrowth_T1GrowthDescription");//"Robots are being produced in this system, providing system wide market growth";
    private static String T2FactoryName = "FactionWideRobotFactory's";
    private static String T2FactoryDescription = Global.getSettings().getString("AIRetrofits_MarketGrowth_T2GrowthDescription");//"Robots are being produced in this faction, providing sector wide market growth";
    public static void applyGrowth(PopulationComposition incoming,MarketAPI market){
        addRobotFactory(incoming,market);
        addHazardPay(incoming,market);
    }
    private static void addRobotFactory(PopulationComposition incoming,MarketAPI market){
        List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
        float[] mods = AIRetorift_GetMarketBoost.forceCalculate(markets,market);
        //AIRetorift_GetMarketBoost.loging("final mods: " + mods[0] + ", " + mods[1]);
        incoming.getWeight().modifyFlat(T1FactoryName, mods[1],T1FactoryDescription);
        incoming.getWeight().modifyFlat(T2FactoryName, mods[0],T2FactoryDescription);
    }
    static String hazzardPayName = "Hazard pay robot factory's";
    static String hazzardPayDescription = Global.getSettings().getString("AIRetrofits_MarketGrowth_hazzardPayDescription");//"Building robots with hazard pay";
    static float hazzardGrowthPerSize = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_hazzardGrowthPerSize");//5;

    private static void addHazardPay(PopulationComposition incoming,MarketAPI market){
        if(market.isImmigrationIncentivesOn()){
            float growth = market.getSize() * hazzardGrowthPerSize;
            incoming.getWeight().modifyFlat(hazzardPayName,growth,hazzardPayDescription);
        }
    }
    private static float getIncentivesGrowth(MarketAPI market){
        return (market.getSize() * 8) - 10;
    }

    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        modifyGrowth(market,id10);
    }
}
