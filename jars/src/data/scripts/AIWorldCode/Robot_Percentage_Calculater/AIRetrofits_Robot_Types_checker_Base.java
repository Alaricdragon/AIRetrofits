package data.scripts.AIWorldCode.Robot_Percentage_Calculater;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class AIRetrofits_Robot_Types_checker_Base {
    public AIRetrofits_Robot_Types_checker_Base(String CalculaterID){
        AIRetrofits_Robot_Types_calculater_2.getType(CalculaterID).addChecker(this);
    }
    public float getDemand(MarketAPI market){
        return 0f;
    }
    public float getSupply(MarketAPI market){
        return 0f;
    }
    public float getLocalSupply(MarketAPI market){
        return 0f;
    }
}
