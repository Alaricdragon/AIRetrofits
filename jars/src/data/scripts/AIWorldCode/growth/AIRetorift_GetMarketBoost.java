package data.scripts.AIWorldCode.growth;

import com.fs.graphics.G;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MutableCommodityQuantity;
import com.fs.starfarer.api.util.Pair;
import org.apache.log4j.Logger;

import java.util.List;

public class AIRetorift_GetMarketBoost {
    private static boolean logingActive = true;
    private static final String IT1 = "AIRetrofit_roboticPopFactoryV1";
    private static final float T1ImprovedBonus = 1.3f;
    private static final float T1PowerPerSize = 5;
    private static final float T1HazzardBonus = 1.3f;

    private static final String IT2 = "AIRetrofit_roboticPopFactoryV2";
    private static final float T2ImprovedBonus = 1.3f;
    private static final float T2PowerPerSize = 10;
    private static final float T2HazzardBonus = 1.3f;

    private static final String requredCondition = "AIRetrofit_AIPop";//AIRetrofits market condition ID

    private static final int maxSize = 6;//size of an market before it stops growing.
    //HERE. not compleat yet.
    public static float[] forceCalculate(List<MarketAPI>  markets, MarketAPI market) {
        //markets[0].getStarSystem().getPlanets();
        int size = market.getSize();
        if (size >= maxSize) {
            float[] out = new float[2];
            return out;
        }
        float global = getFactionGrowthPower(markets,market.getFactionId()) / getGrowthUsedGlobal(markets,market.getFactionId());
        float local = getSystemGrowthPower(market) / getGrowthUsedLocal(market);
        return getGrowth(market,global,local);
    }
    //DONE. gets the growth bounus your faction has.
    private static float getFactionGrowthPower(List<MarketAPI>  markets,String factionID){
        float power = 0;
        for(int a = 0; a < markets.size(); a++){
            String factionID2 = markets.get(a).getFactionId();
            if(factionID.equals(factionID2)){
                power += getMarketPowerGlobal(markets.get(a));
            }
        }
        loging("Global Power Available:" + power);
        return power;
    }
    //DONE. gets the local growth bonus in an system.
    private static float getSystemGrowthPower(MarketAPI market){
        float power = 0;
        String factionID = market.getFactionId();
        List<PlanetAPI> planets = market.getStarSystem().getPlanets();
        for(int a = 0; a < planets.size(); a++){
            try {
                if (planets.get(a).getMarket().getFactionId() != null) {
                    String factionID2 = planets.get(a).getMarket().getFactionId();
                    if (factionID.equals(factionID2) || Global.getSector().getFaction(factionID).getRelationshipLevel(factionID2).isPositive()) {
                        loging("adding local world: " + planets.get(a).getMarket().getName() + " from world slot " + a);
                        power += getMarketPowerLocal(planets.get(a).getMarket());
                    }
                }
            } catch (Exception e){

            }
        }
        loging("Local Power Available: " + power);
        return power;
    }
    //DONE. gets growth your t2 factorys are supporting.
    private static float getGrowthUsedGlobal(List<MarketAPI>  markets,String factionID){
        float power = 0;
        for(int a = 0; a < markets.size(); a++){
            String factionID2 = markets.get(a).getFactionId();
            if(factionID.equals(factionID2) && markets.get(a).hasCondition(requredCondition)){
                if(markets.get(a).getSize() < maxSize) {
                    power += markets.get(a).getSize();
                }else{
                    power += 1;
                }
            }
        }
        loging("Global Size Used: " + power);
        return power;
    }
    //DONE gets growth your T1 factory's are supporting
    private static float getGrowthUsedLocal(MarketAPI market){
        String factionID = market.getFactionId();
        List<PlanetAPI> planets = market.getStarSystem().getPlanets();
        float power = 0;
        for(int a = 0; a < planets.size(); a++) {
            try {
                if (planets.get(a).getMarket().getFactionId() != null) {
                    String factionID2 = planets.get(a).getMarket().getFactionId();
                    if ((factionID.equals(factionID2) || Global.getSector().getFaction(factionID).getRelationshipLevel(factionID2).isPositive()) && planets.get(a).getMarket().hasCondition(requredCondition)) {
                        if(planets.get(a).getMarket().getSize() < maxSize) {
                            power += planets.get(a).getMarket().getSize();
                        }else{
                            //all AI worlds that are done growing use one size worth of growth. slowing down growth for larger empires
                            power += 1;
                        }
                    }
                }
            } catch (Exception e) {

            }
        }
        loging("Local Size Used: " + power);
        return power;
    }
    //HERE not done.
    static float logisticTheshhold = 10;
    private static float[] getGrowth(MarketAPI market,float GPSGlobal,float GPSLocal){
        /*
            HERE:
            this is what needs to happen:
            i need to calculate an curve that will effect this markets growth. an equation that i can plug the GPS into, to get my market growth.
         */
        //logarithmic function <----
        /*
            f(x) = d * log(e,c*x + a) + b
            x is x ponit on grapth.
            f(x) is y ponit on grapth.
            a is how far to the left the grapth stops.
            b is how high up the graph is moved.
            c is moves the x along faster. higher numbers increase the starting values but slow the growth of the curve
            e is the logmreit compoment? it effects how fast the curve grows. smaller numbers grow faster.
            d is how extreme the curve is. higher numbers mean more exstream curve
                -notes on this: lower numbers mean that y will increase slower. higher numbers mean y will increase faster.
            notes on logarithmic functions:
            the range (y range on grapth) is always negitive to positive infinity
                -it takes mush more time to go up the higher the x in this function, but never stops.
            the domain (x range on grapth) is always (left,positive infinity)
                left can be found by: a * -1;

            equation equals 1 at: x * d = e or e / d. y equals 1 when x  = e / d
                - this equation is an good way to get an idea of how the curve of the grapth is going.


        current equadion before testing:
        f(x) = 1.5 * log(1.1,0.2 * x + 1) + 0
        //https://www.symbolab.com/solver/functions-calculator/f%5Cleft(x%5Cright)%3D0.5%20%5Ccdot%5Clog_%7B1.1%7D%5Cleft(1.5x%20%2B%201%5Cright)%20%2B%200?or=input
        float power = 1.5 * (Math.log(0.2 * x + 1) / Math.log(1.1)) + 0;
         */
        float[] out = new float[2];
        int size = market.getSize();
        loging("GPSG: " + GPSGlobal + " GPSL: " + GPSLocal);
        out[0] = size*GPSGlobal;
        out[1] = size*GPSLocal;
        if(GPSGlobal >= logisticTheshhold) {
            out[0] = (float) Math.floor(logFunction(size * GPSGlobal));
        }
        if(GPSLocal >= logisticTheshhold) {
            out[1] = (float) Math.floor(logFunction(size * GPSLocal));
        }
        loging("outGlobal: " + out[0] + " outLocal: " + out[1]);
        return out;
    }
    private static float logFunction(float power){
        return (float)Math.max(1.5 * (Math.log(0.2 * power + 1) / Math.log(1.1)) + 0,0);
    }
    //HERE working, but not done. gets the global growth bonus your faction has from an single market
    public static float getMarketPowerGlobal(MarketAPI market){
        if (market.hasIndustry(IT2) && market.getIndustry(IT2).isFunctional()){
            float basePower = market.getSize();
            float bonus = 1;
            if(market.getIndustry(IT2).isImproved()){
                bonus = T2ImprovedBonus;
            }
            if(market.isImmigrationIncentivesOn()){
                bonus *= T2HazzardBonus;
            }
            List<MutableCommodityQuantity> temp = market.getIndustry(IT2).getAllDemand();
            String[] demand = new String[temp.size()];
            for(int a = 0; a < temp.size(); a++){
                demand[a] = temp.get(a).getSpec().getId();
            }
            //List<Pair<String, Integer>> a = market.getIndustry(IT2).getAllDeficit();
            Pair<String, Integer> defect = market.getIndustry(IT2).getMaxDeficit(demand);
            basePower -= defect.two;
            basePower = Math.max(basePower,0);
            basePower *= T2PowerPerSize * bonus;
            loging("adding " + basePower + "from global");
            return basePower;
        }
        return 0;
    }
    //HERE worker. but not done. gets the local growth bonus your faction has from an single market.
    public static float getMarketPowerLocal(MarketAPI market){
        if (market.hasIndustry(IT1) && market.getIndustry(IT1).isFunctional()){
            loging("detected industry: " + market.getIndustry(IT1).getSpec().getName());
            float basePower = market.getSize();
            float bonus = 1;
            if(market.getIndustry(IT1).isImproved()){
                bonus *= T1ImprovedBonus;
            }
            if(market.isImmigrationIncentivesOn()){
                bonus *= T1HazzardBonus;
            }
            List<MutableCommodityQuantity> temp = market.getIndustry(IT1).getAllDemand();
            String[] demand = new String[temp.size()];
            for(int a = 0; a < temp.size(); a++){
                demand[a] = temp.get(a).getSpec().getId();
            }
            List<Pair<String, Integer>> a = market.getIndustry(IT1).getAllDeficit();
            Pair<String, Integer> defect = market.getIndustry(IT1).getMaxDeficit(demand);
            basePower -= defect.two;
            basePower = Math.max(basePower,0);
            basePower *= T1PowerPerSize * bonus;
            loging("adding " + basePower + "from local");
            return basePower;
        }
        return 0;
    }
    public static void loging(String output){
        if(!logingActive){
            return;
        }
        AIRetorift_GetMarketBoost a = new AIRetorift_GetMarketBoost();
        final Logger LOG = Global.getLogger(a.getClass());//Global.getLogger(this.getClass());
        LOG.info(output);
        /*
49514 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - adding 30.0from global
49514 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - Global Power Available:30.0
49514 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - Global Size Used: 3.0
49514 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - adding local world: Ayre from world slot 8
49514 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - adding local world: Shining Tor from world slot 9
49515 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - Local Power Available: 0.0
49515 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - Local Size Used: 3.0
49515 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - GPSG: 10.0 GPSL: 0.0
49515 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - outGlobal: 10.0 outLocal: 0.0
49515 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - final mods: 20.0, 0.0

101186 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - Global Power Available:0.0
101186 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - Global Size Used: 3.0
101186 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - adding local world: Ayre from world slot 8
101186 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - detected industry: robot worker factory
101186 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - adding 15.0from local
101186 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - adding local world: Shining Tor from world slot 9
101187 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - Local Power Available: 15.0
101187 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - Local Size Used: 3.0
101187 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - GPSG: 0.0 GPSL: 5.0
101187 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - outGlobal: 0.0 outLocal: 5.0
101187 [Thread-3] INFO  data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost  - final mods: 0.0, 16.0
*/
    }
}