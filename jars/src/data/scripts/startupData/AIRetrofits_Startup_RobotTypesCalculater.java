package data.scripts.startupData;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.*;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Defender;

public class AIRetrofits_Startup_RobotTypesCalculater {
    public static void apply(){
        if (!Global.getSettings().getBoolean("AIRetrofit_Nexerlin_GroundBattles_enableReplacer")) return;
        addT2CombatRobots();
        addT1CombatRobots();
        addT0CombatRobots();
        addT2WorkerRobots();
        addT1WorkerRobots();
        addT0WorkerRobots();
        Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_");
    }
    public static final String
            BMar = "",BHev="",BReb="",BMil="",
            T0Mar = "",T0Hev="",T0Reb="",T0Mil="",
            T1Mar = "",T1Hev="",T1Reb="",T1Mil="",
            T2Mar = "",T2Hev="",T2Reb="",T2Mil="";


    public static final String marineID="marines";
    public static final String I_T0_CRForge=AIRetrofits_Constants.Industry_AIRetrofit_combatRobotManufactory;
    public static final String I_T1_CRForge=AIRetrofits_Constants.Industry_AIRetrofit_salvageRobotManufactory_V2;
    public static final String I_T0_SRForge=AIRetrofits_Constants.Industry_AIRetrofit_salvageRobotManufactory;
    public static final String I_T1_SRForge=AIRetrofits_Constants.Industry_AIRetrofit_salvageRobotManufactory_V2;

    //unit type, core, forge level, local/gloal/ attack/defence
    public static final float //supply
        //marines -> basicrobot, attack, supply
        BCR_Cna_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_Cna_T0_L_A"),
        BCR_Cna_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_Cna_T0_G_A"),
        BCR_Cna_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_Cna_T1_L_A"),
        BCR_Cna_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_Cna_T1_G_A"),

        BCR_CGa_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CGa_T0_L_A"),
        BCR_CGa_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CGa_T0_G_A"),
        BCR_CGa_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CGa_T1_L_A"),
        BCR_CGa_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CGa_T1_G_A"),

        BCR_CBe_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CBe_T0_L_A"),
        BCR_CBe_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CBe_T0_G_A"),
        BCR_CBe_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CBe_T1_L_A"),
        BCR_CBe_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CBe_T1_G_A"),

        //marines -> basicrobot, def, supply
        BCR_Cna_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_Cna_T0_L_D"),
        BCR_Cna_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_Cna_T0_G_D"),
        BCR_Cna_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_Cna_T1_L_D"),
        BCR_Cna_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_Cna_T1_G_D"),

        BCR_CGa_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CGa_T0_L_D"),
        BCR_CGa_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CGa_T0_G_D"),
        BCR_CGa_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CGa_T1_L_D"),
        BCR_CGa_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CGa_T1_G_D"),

        BCR_CBe_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CBe_T0_L_D"),
        BCR_CBe_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CBe_T0_G_D"),
        BCR_CBe_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CBe_T1_L_D"),
        BCR_CBe_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BCR_CBe_T1_G_D"),


        //Heavy units -> Hevey basicrobot, atk, supply
        HeBCR_Cna_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_Cna_T0_L_A"),
        HeBCR_Cna_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_Cna_T0_G_A"),
        HeBCR_Cna_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_Cna_T1_L_A"),
        HeBCR_Cna_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_Cna_T1_G_A"),

        HeBCR_CGa_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CGa_T0_L_A"),
        HeBCR_CGa_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CGa_T0_G_A"),
        HeBCR_CGa_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CGa_T1_L_A"),
        HeBCR_CGa_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CGa_T1_G_A"),

        HeBCR_CBe_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CBe_T0_L_A"),
        HeBCR_CBe_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CBe_T0_G_A"),
        HeBCR_CBe_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CBe_T1_L_A"),
        HeBCR_CBe_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CBe_T1_G_A"),

        //Heavy units -> Hevey basicrobot, def, supply
        HeBCR_Cna_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_Cna_T0_L_D"),
        HeBCR_Cna_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_Cna_T0_G_D"),
        HeBCR_Cna_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_Cna_T1_L_D"),
        HeBCR_Cna_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_Cna_T1_G_D"),

        HeBCR_CGa_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CGa_T0_L_D"),
        HeBCR_CGa_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CGa_T0_G_D"),
        HeBCR_CGa_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CGa_T1_L_D"),
        HeBCR_CGa_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CGa_T1_G_D"),

        HeBCR_CBe_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CBe_T0_L_D"),
        HeBCR_CBe_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CBe_T0_G_D"),
        HeBCR_CBe_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CBe_T1_L_D"),
        HeBCR_CBe_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeBCR_CBe_T1_G_D"),


        //malita -> baisc combat robots, def, supply
        Mil_CBe_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_Mil_CBe_T0_L_D"),
        Mil_CBe_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_Mil_CBe_T1_L_D"),





    Temp=1;
    public static final float
        //marine -> baisc combat robots demand, attack
        D_BCR_Mar_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_BCR_Mar_A"),
        D_BCR_Pop_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_BCR_Pop_A"),

        //marine -> baisc combat robots demand, defence
        D_BCR_Mar_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_BCR_Mar_D"),
        D_BCR_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_BCR_Pop_D"),


        //Hevey -> Hevy baisc combat robots demand, attack
        D_HeBCR_Mar_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeBCR_Mar_A"),
        D_HeBCR_Pop_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeBCR_Pop_A"),

        //Hevy -> Hevy marine -> baisc combat robots demand, defence
        D_HeBCR_Mar_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeBCR_Mar_D"),
        D_HeBCR_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeBCR_Pop_D"),




    Temp1=1;
    public static void addT0WorkerRobots(){

    }
    public static void addT0CombatRobots(){
        /**
         * AIRetrofits_Robot_Types_checker_Demand_0 = demand per supply or demand
         * AIRetrofits_Robot_Types_checker_Demand_1 = demand per market size
         * AIRetrofits_Robot_Types_checker_Supply_0 = supply for industry output (With AI Core filter)
         * AIRetrofits_Robot_Types_checker_Supply_1 = supply per combat robot forge defence bonus. only works then
         * AIRetrofits_Robot_Types_checker_Supply_2 = full supply but only when AI-pop market condition is present.
         */
        /*basic combat robot forge (no core and gamma) output on faction and local Marine forces.*/
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack,new String[]{BMar},new String[]{T0Mar});
        //supply
        //NO core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack,"",I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_Cna_T0_L_A,BCR_Cna_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack, "",I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_Cna_T1_L_A,BCR_Cna_T1_G_A);
        //Gamma Core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack,Commodities.GAMMA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_CGa_T0_L_A,BCR_CGa_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack, Commodities.GAMMA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_CGa_T1_L_A,BCR_CGa_T1_G_A);
        //beta Core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack,Commodities.BETA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_CBe_T0_L_A,BCR_CBe_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack, Commodities.BETA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_CBe_T1_L_A,BCR_CBe_T1_G_A);
        //market condition:
        new AIRetrofits_Robot_Types_checker_Supply_2(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack);
        //demand
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack,marineID,"", D_BCR_Mar_A,0);
        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack, D_BCR_Pop_A);

        //basic combat robot defence
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence,new String[]{BMar},new String[]{T0Mar});
        //supply
        //NO core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence,"",I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_Cna_T0_L_D,BCR_Cna_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence, "",I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_Cna_T1_L_D,BCR_Cna_T1_G_D);
        //Gamma Core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence,Commodities.GAMMA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_CGa_T0_L_D,BCR_CGa_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence, Commodities.GAMMA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_CGa_T1_L_D,BCR_CGa_T1_G_D);
        //Beta Core - local
        new AIRetrofits_Robot_Types_checker_Supply_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_CBe_T0_L_D);
        new AIRetrofits_Robot_Types_checker_Supply_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,BCR_CBe_T1_L_D);
        //Beta Core - Global
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence,Commodities.BETA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,0,BCR_CBe_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence, Commodities.BETA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,0,BCR_CBe_T1_G_D);
        //market condition:
        new AIRetrofits_Robot_Types_checker_Supply_2(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence);
        //demand
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence,marineID,"", D_BCR_Mar_D,0);
        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence, D_BCR_Pop_D);



        /*basic combat robot forge (no core and gamma) output on faction and local heavy unit forces.*/
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack,new String[]{BHev},new String[]{T0Hev});
        //supply
        //NO core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack,"",I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_Cna_T0_L_A,HeBCR_Cna_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack, "",I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_Cna_T1_L_A,HeBCR_Cna_T1_G_A);
        //Gamma Core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack,Commodities.GAMMA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_CGa_T0_L_A,HeBCR_CGa_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack, Commodities.GAMMA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_CGa_T1_L_A,HeBCR_CGa_T1_G_A);
        //beta Core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack,Commodities.BETA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_CBe_T0_L_A,HeBCR_CBe_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack, Commodities.BETA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_CBe_T1_L_A,HeBCR_CBe_T1_G_A);
        //market condition:
        new AIRetrofits_Robot_Types_checker_Supply_2(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack);
        //demand
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack,marineID,"", D_HeBCR_Mar_A,0);
        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack, D_HeBCR_Pop_A);

        //heavy combat robot defence
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence,new String[]{BHev},new String[]{T0Hev});
        //supply
        //NO core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence,"",I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_Cna_T0_L_D,HeBCR_Cna_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence, "",I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_Cna_T1_L_D,HeBCR_Cna_T1_G_D);
        //Gamma Core
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence,Commodities.GAMMA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_CGa_T0_L_D,HeBCR_CGa_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence, Commodities.GAMMA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_CGa_T1_L_D,HeBCR_CGa_T1_G_D);
        //Beta Core - local
        new AIRetrofits_Robot_Types_checker_Supply_1(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_CBe_T0_L_D);
        new AIRetrofits_Robot_Types_checker_Supply_1(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,HeBCR_CBe_T1_L_D);
        //Beta Core - Global
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence,Commodities.BETA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,0,HeBCR_CBe_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence, Commodities.BETA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,0,HeBCR_CBe_T1_G_D);
        //market condition:
        new AIRetrofits_Robot_Types_checker_Supply_2(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence);
        //demand
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence,marineID,"", D_HeBCR_Mar_D,0);
        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence, D_HeBCR_Pop_D);



        /*basic combat robot forge (no core and gamma) output on faction and local heavy unit forces.*/
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_MalCombatT0_Defence,new String[]{BMil},new String[]{T0Mar});
        //supply
        //Beta Core - local
        new AIRetrofits_Robot_Types_checker_Supply_1(AIRetrofits_Constants.RobotTypeCalculatorID_MalCombatT0_Defence,I_T0_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,Mil_CBe_T0_L_D);
        new AIRetrofits_Robot_Types_checker_Supply_1(AIRetrofits_Constants.RobotTypeCalculatorID_MalCombatT0_Defence,I_T1_CRForge,AIRetrofits_Constants.Commodity_T0_CombatDrone,Mil_CBe_T1_L_D);




        //I would like the beta core modifer to use a diffrent system one moment.
        //new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0, Commodities.BETA_CORE,"",AIRetrofits_Constants.Commodity_T0_CombatDrone,0,0);
    }
    public static void addT1WorkerRobots(){
    }
    public static void addT1CombatRobots(){
    }
    public static void addT2WorkerRobots(){
    }
    public static void addT2CombatRobots(){
    }
}
