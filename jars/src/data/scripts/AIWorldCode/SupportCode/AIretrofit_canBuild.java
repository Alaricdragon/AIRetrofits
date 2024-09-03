package data.scripts.AIWorldCode.SupportCode;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIretrofit_canBuild {
    private static boolean can = AIRetrofits_Constants_3.Market_EnableMarketFetures;// Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    public static boolean isAI(MarketAPI market){
        return market.hasCondition("AIRetrofit_AIPop") && can;
    }
    public static boolean hasRobotForge(){
        return Global.getSector().getPlayerFleet().hasAbility("AIretrofit_robot_drone_forge") && can;
    }
    public static boolean canFoundMarket(){
        return hasRobotForge();
    }
    /*
    public static boolean hasRoboticShips(){
        return Global.getSector().getPlayerFaction().getKnownHullMods().get("") && can;
    }*/

}
