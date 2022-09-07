package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandLists;

public class AIRetrofit_econUpdateListiner implements EconomyAPI.EconomyUpdateListener {
    private static boolean can = Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    static String RuleSet = "AIRetrofits_AIPopGrowth";
    private void applyMarketData() {
        if(can) {
            for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
                market = Global.getSector().getEconomy().getMarket(market.getId());
                crewReplacer_SupplyDemandLists.getRuleSet(RuleSet).applyMarket(market, false);
            }
        }
    }

    @Override
    public void commodityUpdated(String commodityId) {

    }

    @Override
    public void economyUpdated() {
        applyMarketData();
    }
    @Override
    public boolean isEconomyListenerExpired() {
        return false;
    }
}