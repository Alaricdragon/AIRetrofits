package data.scripts.AIWorldCode.Robot_Percentage_Calculater;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class AIRetrofits_Robot_Types_checker_Demand_1 extends AIRetrofits_Robot_Types_checker_Base{
    public AIRetrofits_Robot_Types_checker_Demand_1(String CalculaterID,float demandPerSize) {
        super(CalculaterID);
        this.demandPerSize = demandPerSize;
    }
    public float demandPerSize;
    @Override
    public float getDemand(MarketAPI market) {
        String factionID = market.getFactionId();
        float demand = 0;
        for (MarketAPI a : Global.getSector().getEconomy().getMarketsCopy()){
            if(a.getFactionId().equals(factionID)){
                demand+=a.getSize()*demandPerSize;
            }
        }
        return demand;
    }
}
