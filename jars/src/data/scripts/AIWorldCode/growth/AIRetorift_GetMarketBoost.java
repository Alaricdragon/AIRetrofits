package data.scripts.AIWorldCode.growth;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MutableCommodityQuantity;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIRetrofit_Log;
import data.scripts.startupData.AIRetrofits_Constants;
import org.apache.log4j.Logger;

import java.util.List;

public class AIRetorift_GetMarketBoost {
    private static final String IT1 = AIRetrofits_Constants.Market_Growth_IT1;
    private static final float T1ImprovedBonus = AIRetrofits_Constants.Market_Growth_T1ImprovedBonus;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1ImprovedBonus");//1.3f;
    private static final float T1PowerPerSize = AIRetrofits_Constants.Market_Growth_T1PowerPerSize;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1PowerPerSize");//5;
    private static final float T1HazzardBonus = AIRetrofits_Constants.Market_Growth_T1HazzardBonus;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1HazzardBonus");//1.3f;
    private static final float T1AplhaBonus = AIRetrofits_Constants.Market_Growth_T1AplhaBonus;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1AlphaCoreBonus");

    private static final String IT2 = AIRetrofits_Constants.Market_Growth_IT2;//"AIRetrofit_roboticPopFactoryV2";
    private static final float T2ImprovedBonus = AIRetrofits_Constants.Market_Growth_T2ImprovedBonus;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T2ImprovedBonus");//1.3f;
    private static final float T2PowerPerSize = AIRetrofits_Constants.Market_Growth_T2PowerPerSize;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T2PowerPerSize");//10;
    private static final float T2HazzardBonus = AIRetrofits_Constants.Market_Growth_T2HazzardBonus;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T2HazzardBonus");//1.3f;
    private static final float T2AplhaBonus = AIRetrofits_Constants.Market_Growth_T2AplhaBonus;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1AlphaCoreBonus");


    private static final float logisticTheshhold = AIRetrofits_Constants.Market_Growth_logisticTheshhold;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticTheshhold");//20;
    private static final String requredCondition = AIRetrofits_Constants.Market_Growth_requredCondition;//"AIRetrofit_AIPop";//AIRetrofits market condition ID
    private static final String AlphaCore = AIRetrofits_Constants.AlphaCore;//"alpha_core";

    private static final int maxSize = AIRetrofits_Constants.Market_Growth_maxSize;//Global.getSettings().getInt("maxColonySize");//size of an market before it stops growing.
    //(float)Math.max(1.5 * (Math.log(0.2 * power + 1) / Math.log(1.1)) + 0,0);
    //f(x) = 1.5 * log(1.1,0.2 * x + 1) + 0
    private static final float logFunctionA = AIRetrofits_Constants.Market_Growth_logFunctionA;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticA");//1.5f;
    private static final float logFunctionB = AIRetrofits_Constants.Market_Growth_logFunctionB;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticB");//1.1f;
    private static final float logFunctionC = AIRetrofits_Constants.Market_Growth_logFunctionC;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticC");//0.2f;
    private static final float logFunctionD = AIRetrofits_Constants.Market_Growth_logFunctionD;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticD");//1;
    private static final float logFunctionE = AIRetrofits_Constants.Market_Growth_logFunctionE;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticE");//0;
    //(float)Math.max(A * (Math.log(C * power + D) / Math.log(B)) + E,0);
    //(float)Math.max(logFunctionA * (Math.log(logFunctionC * power + logFunctionD) / Math.log(logFunctionB)) + logFunctionE,0);

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
        //return (float)Math.max(1.5 * (Math.log(0.2 * power + 1) / Math.log(1.1)) + 0,0);
        return (float)Math.max(logFunctionA * (Math.log(logFunctionC * power + logFunctionD) / Math.log(logFunctionB)) + logFunctionE,0);
    }
    //HERE working, but not done. gets the global growth bonus your faction has from an single market
    public static float getMarketPowerGlobal(MarketAPI market){
        if (market.hasIndustry(IT2) && market.getIndustry(IT2).isFunctional()){
            float basePower = market.getSize();
            float bonus = 1;
            if(market.getIndustry(IT2).isImproved()){
                bonus += T2ImprovedBonus;
            }
            if(market.isImmigrationIncentivesOn()){
                bonus += T2HazzardBonus;
            }
            if(market.getIndustry(IT2).getAICoreId() != null && market.getIndustry(IT2).getAICoreId().equals(AlphaCore)){
                bonus+=T2AplhaBonus;
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
                bonus += T1ImprovedBonus;
            }
            if(market.isImmigrationIncentivesOn()){
                bonus += T1HazzardBonus;
            }
            if(market.getIndustry(IT1).getAICoreId() != null && market.getIndustry(IT1).getAICoreId().equals(AlphaCore)){
                bonus+=T1AplhaBonus;
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
        AIRetrofit_Log.loging(output,new AIRetorift_GetMarketBoost());
    }
}
