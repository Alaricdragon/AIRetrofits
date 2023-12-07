package data.scripts.startupData;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.*;
import data.scripts.AIWorldCode.industries.personalRobotForge.AIRetrofit_combatRobotManufactory;

public class AIRetrofits_Startup_RobotTypesCalculater {
    public static void apply(){
        addT0Robots();
        addT1Robots();
        addT2Robots();
    }
    public static final String marineID="";
    public static void addT0Robots(){
        new AIRetrofits_Robot_Types_calculater_2(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0,"","",AIRetrofits_Constants.Commodity_T0_CombatDrone,0,0);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0, Commodities.GAMMA_CORE,"",AIRetrofits_Constants.Commodity_T0_CombatDrone,0,0);
        //I would like the beta core modifer to use a diffrent system one moment.
        //new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0, Commodities.BETA_CORE,"",AIRetrofits_Constants.Commodity_T0_CombatDrone,0,0);
        new AIRetrofits_Robot_Types_checker_Base(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0){
        };
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0,marineID,"",1,0);
        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0,1);
    }
    public static void addT1Robots(){
    }
    public static void addT2Robots(){
    }
}
