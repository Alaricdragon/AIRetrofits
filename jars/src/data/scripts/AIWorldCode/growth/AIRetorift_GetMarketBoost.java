package data.scripts.AIWorldCode.growth;

import com.fs.graphics.G;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MutableCommodityQuantity;
import com.fs.starfarer.api.util.Pair;

import java.util.List;

public class AIRetorift_GetMarketBoost {
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
    public static double[] forceCalculate(List<MarketAPI>  markets, MarketAPI market) {
        //markets[0].getStarSystem().getPlanets();
        int size = market.getSize();
        if (size >= maxSize) {
            double[] out = new double[2];
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
                        power += getMarketPowerLocal(market);
                    }
                }
            } catch (Exception e){

            }
        }
        return power;
    }
    //DONE. gets growth your t2 factorys are supporting.
    private static float getGrowthUsedGlobal(List<MarketAPI>  markets,String factionID){
        float power = 0;
        for(int a = 0; a < markets.size(); a++){
            String factionID2 = markets.get(a).getFactionId();
            if(markets.get(a).getSize() < maxSize && factionID.equals(factionID2) && markets.get(a).hasCondition(requredCondition)){
                power += markets.get(a).getSize();
            }
        }
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
                    if (planets.get(a).getMarket().getSize() < maxSize && (factionID.equals(factionID2) || Global.getSector().getFaction(factionID).getRelationshipLevel(factionID2).isPositive()) && planets.get(a).getMarket().hasCondition(requredCondition)) {
                        power += planets.get(a).getMarket().getSize();
                    }
                }
            } catch (Exception e) {

            }
        }
        return power;
    }
    //HERE not done.
    private static double[] getGrowth(MarketAPI market,float GPSGlobal,float GPSLocal){
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
        f(x) = 0.5 * log(1.1,1.5 * x + 1) + 0
        //https://www.symbolab.com/solver/functions-calculator/f%5Cleft(x%5Cright)%3D0.5%20%5Ccdot%5Clog_%7B1.1%7D%5Cleft(1.5x%20%2B%201%5Cright)%20%2B%200?or=input
        float power = 0.5 * (Math.log(1.5 * x + 1) / Math.log(1.1)) + 0;
         */
        double[] out = new double[2];
        int size = market.getSize();
        out[0] = Math.floor(logFunction(size * GPSGlobal));
        out[1] = Math.floor(logFunction(size * GPSLocal));
        return out;
    }
    private static double logFunction(float power){
        return Math.max((0.5 * (Math.log(1.5 * power + 1) / Math.log(1.1)) + 0),0);
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
            return basePower;
        }
        return 0;
    }
    //HERE worker. but not done. gets the local growth bonus your faction has from an single market.
    public static float getMarketPowerLocal(MarketAPI market){
        if (market.hasIndustry(IT1) && market.getIndustry(IT1).isFunctional()){
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
            return basePower;
        }
        return 0;
    }
}
