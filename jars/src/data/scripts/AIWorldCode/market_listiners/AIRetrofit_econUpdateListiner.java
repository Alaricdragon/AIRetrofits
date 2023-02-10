package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.AIWorldCode.growth.AIRetrofit_MarketGrowthMods;
import data.scripts.startupData.AIRetrofits_Constants;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandLists;

public class AIRetrofit_econUpdateListiner implements EconomyAPI.EconomyUpdateListener {
    private static boolean can = AIRetrofits_Constants.Market_EnableMarketFetures;//Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    static private boolean Override = true;//when true, act as normal.
    private void applyMarketData() {
        if(can && Override) {
            for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
                market = Global.getSector().getEconomy().getMarket(market.getId());
                //crewReplacer_SupplyDemandLists.getRuleSet(RuleSet).applyMarket(market, false);
                AIRetrofit_MarketGrowthMods.applyData(market,"");
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