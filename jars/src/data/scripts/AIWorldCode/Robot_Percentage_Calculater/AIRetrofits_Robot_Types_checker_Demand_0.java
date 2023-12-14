package data.scripts.AIWorldCode.Robot_Percentage_Calculater;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class AIRetrofits_Robot_Types_checker_Demand_0 extends AIRetrofits_Robot_Types_checker_Base{
    public AIRetrofits_Robot_Types_checker_Demand_0(String CalculaterID,String demand,String supply,float costPerDemand, float costPerSupply) {
        super(CalculaterID);
        this.demand=demand;
        this.supply=supply;
        this.costPerDemand=costPerDemand;
        this.costPerSupply=costPerSupply;
    }
    public String demand;
    public String supply;
    public float costPerDemand;
    public float costPerSupply;
    @Override
    public float getDemand(MarketAPI market) {
        String factionID = market.getFactionId();
        float demand = 0;
        for (MarketAPI a : Global.getSector().getEconomy().getMarketsCopy()){
            if(a.getFactionId().equals(factionID)){
                float a0 =0;
                float a1 =0;
                for(Industry b : a.getIndustries()){
                    float b0 = 0;
                    float b1 = 0;
                    if(!(this.demand == null)) b0 = b.getDemand(this.demand).getQuantity().getModifiedValue();
                    if(!(this.supply == null)) b1 = b.getSupply(this.supply).getQuantity().getModifiedValue();
                    if(a0 < b0) {
                        a0 = b0;
                    }
                    if(a1 < b1) {
                        a1 = b1;
                    }
                }
                demand += (a0*costPerDemand)+(a1*costPerSupply);
            }
        }
        return demand;
    }
}
