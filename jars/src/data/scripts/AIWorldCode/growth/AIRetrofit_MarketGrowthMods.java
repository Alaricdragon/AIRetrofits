package data.scripts.AIWorldCode.growth;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import data.scripts.AIRetrofit_Log;
import data.scripts.CrewReplacer_Log;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.List;

public class AIRetrofit_MarketGrowthMods implements MarketImmigrationModifier {
    private static final AIRetrofit_MarketGrowthMods logsClass = new AIRetrofit_MarketGrowthMods();
    static String condition = AIRetrofits_Constants.Market_Condition;//"AIRetrofit_AIPop";
    public static void applyData(MarketAPI market, String ID){
        undoExtraData(market,ID);
        if(!market.hasCondition(condition)){
            return;
        }
        modifyGrowth(market);
    }
    public static void undoExtraData(MarketAPI market,String ID){
    }
    private static void modifyGrowth(MarketAPI market){
        AIRetrofit_Log.loging("hiding notified market modifiers for market " + market.getName(),logsClass,AIRetrofits_Constants.Market_EnableLogs);
        PopulationComposition incoming = market.getIncoming();
        String[] activeGrowth = AIRetrofits_Constants.Market_WhiteListedGrowthMods;
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
                AIRetrofit_Log.loging("hiding modifier named " + incoming.getWeight().getFlatMods().keySet().toArray()[a].toString(),logsClass,AIRetrofits_Constants.Market_EnableLogs);
                incoming.getWeight().unmodify(incoming.getWeight().getFlatMods().keySet().toArray()[a].toString());
            }
        }
    }
    private static final String T1FactoryName = AIRetrofits_Constants.Market_GrowthMod_LocalRobotFactorys;
    private static final String T1FactoryDescription = AIRetrofits_Constants.Market_GrowthDescription_LocalRobotFactorys;
    private static final String T2FactoryName = AIRetrofits_Constants.Market_GrowthMod_FactionWideRobotFactorys;
    private static final String T2FactoryDescription = AIRetrofits_Constants.Market_GrowthDescription_FactionWideRobotFactorys;
    public static void applyGrowth(PopulationComposition incoming,MarketAPI market){
        addRobotFactory(incoming,market);
        addHazardPay(incoming,market);
    }
    private static void addRobotFactory(PopulationComposition incoming,MarketAPI market){
        AIRetrofit_Log.loging("adding T1 and T2 robot factory bonuses",logsClass,AIRetrofits_Constants.Market_EnableLogs);
        List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
        float[] mods = AIRetorift_GetMarketBoost.forceCalculate(markets,market);
        //AIRetorift_GetMarketBoost.loging("final mods: " + mods[0] + ", " + mods[1]);
        incoming.getWeight().modifyFlat(T1FactoryName, mods[1],T1FactoryDescription);
        incoming.getWeight().modifyFlat(T2FactoryName, mods[0],T2FactoryDescription);
    }
    static String hazzardPayName = AIRetrofits_Constants.Market_GrowthMod_hazzardPayName;
    static String hazzardPayDescription = AIRetrofits_Constants.Market_GrowthDescription_hazzardPay;
    static float hazzardGrowthPerSize = AIRetrofits_Constants.Market_GrowthMod_hazzardGrowthPerSize;

    private static void addHazardPay(PopulationComposition incoming,MarketAPI market){
        if(market.isImmigrationIncentivesOn()){
            float growth = market.getSize() * hazzardGrowthPerSize;
            incoming.getWeight().modifyFlat(hazzardPayName,growth,hazzardPayDescription);
            AIRetrofit_Log.loging("adding hazardPay boost to market...",logsClass,AIRetrofits_Constants.Market_EnableLogs);
        }
    }
    private static float getIncentivesGrowth(MarketAPI market){
        return (market.getSize() * 8) - 10;
    }

    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        modifyGrowth(market);
    }
}
