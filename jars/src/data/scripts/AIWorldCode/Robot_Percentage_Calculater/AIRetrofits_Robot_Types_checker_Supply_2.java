package data.scripts.AIWorldCode.Robot_Percentage_Calculater;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofits_Robot_Types_checker_Supply_2 extends AIRetrofits_Robot_Types_checker_Base {
    public AIRetrofits_Robot_Types_checker_Supply_2(String CalculaterID) {
        super(CalculaterID);
    }

    @Override
    public float getLocalSupply(MarketAPI market) {
        if (market.hasCondition(AIRetrofits_Constants_3.Market_Condition)){
            return 1f;
        }
        return 0;
    }
}
