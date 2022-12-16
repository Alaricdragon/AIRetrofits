package data.scripts.AIWorldCode.SupportCode;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;

public class AIretrofit_canBuild {
    private static boolean can = Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    public static boolean isAI(MarketAPI market){
        return market.hasCondition("AIRetrofit_AIPop") && can;
    }
    public static boolean hasAbility(){
        return Global.getSector().getPlayerFleet().hasAbility("AIretrofit_robot_drone_forge") && can;
    }

}
