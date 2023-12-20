package data.scripts.AIWorldCode.Robot_Percentage_Calculater;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.AIRetrofit_Log;

public class AIRetrofits_Robot_Types_checker_Supply_0 extends AIRetrofits_Robot_Types_checker_Base{

    public AIRetrofits_Robot_Types_checker_Supply_0(String CalculaterID, String aICore, String industry, String output, float localPowerPerOutput, float factionPowerPerOutput) {
        super(CalculaterID);
        this.aICore = aICore;
        this.industry =industry;
        this.output=output;
        this.localPowerPerOutput=localPowerPerOutput;
        this.factionPowerPerOutput=factionPowerPerOutput;
    }
    public String aICore;
    public String industry;
    public String output;
    public float localPowerPerOutput;
    public float factionPowerPerOutput;

    @Override
    public float getDemand(MarketAPI market) {
        return 0;
    }

    @Override
    public float getLocalSupply(MarketAPI market) {
        String marketCore = market.getIndustry(industry).getAICoreId();
        if (marketCore == null) marketCore = "";
        if (market.hasIndustry(industry) && marketCore.equals(aICore)){
            return localPowerPerOutput * market.getIndustry(industry).getSupply(output).getQuantity().getModifiedValue();
        }
        return 0;
    }

    @Override
    public float getSupply(MarketAPI market) {
        String factionID = market.getFactionId();
        float power = 0;
        for (MarketAPI a : Global.getSector().getEconomy().getMarketsCopy()) {
            //a.getFactionId().equals(factionID);
            //AIRetrofit_Log.loging("comparing: "+ market.getIndustry(industry).getAICoreId()+ " to "+ aICore,this,true);//.equals(aICore);
            String marketCore = market.getIndustry(industry).getAICoreId();
            if (marketCore == null) marketCore = "";
            if (a.getFactionId().equals(factionID) && market.hasIndustry(industry) && marketCore.equals(aICore)) {
                power += factionPowerPerOutput * market.getIndustry(industry).getSupply(output).getQuantity().getModifiedValue();
            }
        }
        return power;
    }
}
