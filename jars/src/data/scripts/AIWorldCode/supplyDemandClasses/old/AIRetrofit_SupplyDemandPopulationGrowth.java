package data.scripts.AIWorldCode.supplyDemandClasses.old;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandChange;

import java.util.List;

/*
 */
public class AIRetrofit_SupplyDemandPopulationGrowth extends crewReplacer_SupplyDemandChange implements MarketImmigrationModifier {
    public AIRetrofit_SupplyDemandPopulationGrowth(String nametemp, boolean supplytemp) {
        super(nametemp, supplytemp);
    }
    static String id3 = "population_AIRetrofit_3";
    static String id4 = "population_AIRetrofit_4";
    static String id10 = "AIPopRemover";
    @Override
    public void applyExtraData(Industry industry, String ID){
        if(!industry.getSpec().getId().equals("population")){
            return;
        }else{
            undoExtraData(industry,ID);
        }
        modifyGrowth(industry.getMarket(),ID);
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
    @Override
    public void undoExtraData(Industry industry, String ID){
        //industry.getDemand("metals").getQuantity().modifyFlat(id3,industry.getMarket().getSize() * -1," from hazard pay building drones");
        //industry.getDemand("rare_metals").getQuantity().modifyFlat(id3,industry.getMarket().getSize() * -1," from hazard pay building drones");
        //industry.getDemand("heavy_machinery").getQuantity().modifyFlat(id3,industry.getMarket().getSize() * -1," from hazard pay building drones");
        /*industry.getDemand("metals").getQuantity().unmodify(ID);
        industry.getDemand("rare_metals").getQuantity().unmodify(ID);
        industry.getDemand("heavy_machinery").getQuantity().unmodify(ID);*/
    }


    /*private void modifyGrowth(MarketAPI market,String ID){
        market.getIncoming();//nothing.
        market.getPopulation();//nothing
        market.getAllImmigrationModifiers();
        market.getTransientImmigrationModifiers();
        market.getImmigrationModifiers();

        PopulationComposition incoming = market.getIncoming();
        //incoming.getWeight().modifyMult(ID,0);
        incoming.getWeight().modifyFlatAlways(ID,25,"bla bla bla bla");
    }*/
    private void modifyGrowth(MarketAPI market,String ID){
        PopulationComposition incoming = market.getIncoming();
        String[] activeGrowth = {//HERE <-- this needs to be swapped with data from config
                "population_AIRetrofit_0",
                "population_AIRetrofit_1",
                "population_AIRetrofit_2",
                "population_AIRetrofit_3",
                "AIRetrofits_BasicDroneFactory_0",
                "AIRetrofits_AdvancedDroneFactory_0",
        };
        int a = 0;
        while(incoming.getWeight().getFlatMods().keySet().size() != 0 && a < incoming.getWeight().getFlatMods().keySet().size()){
            boolean out = true;
            String m = incoming.getWeight().getFlatMods().keySet().toArray()[a].toString();
            for (String s : activeGrowth) {
                if (m.equals(s)) {
                    a++;
                    out = false;
                    break;
                }
            }
            if(out) {
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
        addRobotFactory(incoming,market);
        addHazardPay(incoming,market);
        market.setIncoming(incoming);
    }
    private String T1FactoryName = "LocalRobotFactory's";
    private String T1FactoryDescription = "Robots are being produced in this system, for this system!";
    private String T2FactoryName = "FactionWideRobotFactory's";
    private String T2FactoryDescription = "Robots are being produced in this faction, for this faction!";
    private void addRobotFactory(PopulationComposition incoming,MarketAPI market){
        List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
        float[] mods = AIRetorift_GetMarketBoost.forceCalculate(markets,market);
        incoming.getWeight().modifyFlat(T1FactoryName, (float) mods[1],T1FactoryDescription);
        incoming.getWeight().modifyFlat(T2FactoryName, (float) mods[0],T2FactoryDescription);
    }
    static String hazzardPayName = "Hazard pay robot factory's";
    static String hazzardPayDescription = "Building robots with hazard pay";
    static float hazzardGrowthPerSize = 5;

    private void addHazardPay(PopulationComposition incoming,MarketAPI market){
        if(market.isImmigrationIncentivesOn()){
            float growth = market.getSize() * hazzardGrowthPerSize;
            incoming.getWeight().modifyFlat(hazzardPayName,growth,hazzardPayDescription);
        }
    }
    private float getIncentivesGrowth(MarketAPI market){
        return (market.getSize() * 8) - 10;
    }

    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        modifyGrowth(market,id10);
    }
}
