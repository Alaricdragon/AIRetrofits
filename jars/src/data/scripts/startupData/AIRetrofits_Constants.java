package data.scripts.startupData;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;

public class AIRetrofits_Constants {
    //startup settings:
    public static final boolean AlwaysGiveThings = Global.getSettings().getBoolean("AIRetrofit_alwaysGiveSkillsAndHullmods");;
    public static final String Hullmod_CrewReplacementDrones = "AIretrofit_AutomatedCrewReplacementDrones";
    public static final String Hullmod_AIRetrofit = "AIretrofit_airetrofit";
    public static final String Hullmod_PatchworkAIRetrofit = "AIretrofit_PatchworkAIRetrofit";
    public static final boolean Hullmod_PatchworkAIRetrofit_CanSwap = Global.getSettings().getBoolean("AIRetrofits_Patchwork_CanSwapHullMpd");
    public static final String ability_RobotForge = "AIretrofit_robot_drone_forge";
    public static final String req_skill = "automated_ships";

    public static final String AlphaCore = Commodities.ALPHA_CORE;

    public static final String Commodity_T0_WorkerDrone = "AIretrofit_WorkerDrone";
    public static final String Commodity_T0_CombatDrone = "AIretrofit_CombatDrone";
    public static final String Commodity_T0_SurveyDrone = "AIretrofit_SurveyDrone";

    public static final String Commodity_T1_WorkerDrone = "AIretrofit_Advanced_WorkerDrone";
    public static final String Commodity_T1_CombatDrone = "AIretrofit_Advanced_CombatDrone";
    public static final String Commodity_T1_SurveyDrone = "AIretrofit_Advanced_SurveyDrone";

    public static final String Commodity_T2_WorkerDrone = "AIretrofit_Omega_WorkerDrone";
    public static final String Commodity_T2_CombatDrone = "AIretrofit_Omega_CombatDrone";
    public static final String Commodity_T2_SurveyDrone = "AIretrofit_Omega_SurveyDrone";

    public static final String Commodity_CommandRely = "AIretrofit_CommandRely";
    public static final String Commodity_MaintenanceParts = "AIretrofit_maintainsPacts";
    public static final String Commodity_SubCommandNode = "AIretrofit_SubCommandNode";
    public static final String Commodity_RoboticReplacementParts = "AIretrofit_roboticReplacementParts";
    public static final String Commodity_HumanInterfaceNode = "AIretrofit_humanInterfaceNode";

    //industry names:
    public static final String Industry_AINodeProductionFacility = "AIRetrofit_AINodeProductionFacility";
    public static final String Industry_AIRetrofit_salvageRobotManufactory = "AIRetrofit_salvageRobotManufactory";
    public static final String Industry_AIRetrofit_combatRobotManufactory = "AIRetrofit_combatRobotManufactory";
    public static final String Industry_AIRetrofit_salvageRobotManufactory_V2 = "AIRetrofit_salvageRobotManufactory_V2";
    public static final String Industry_AIRetrofit_combatRobotManufactory_V2 = "AIRetrofit_combatRobotManufactory_V2";
    //sub market names:
    public static final String Submarket_AINodeProductionFacility = "AIRetrofit_AINodeProductionFacilitySubmarket";

    //Robot Stats And Descriptions
    public static final String robot_Description = Global.getSettings().getString("AIRetrofits_RobotPowerDescription");//"this robot effectiveness is being multiplied by %s for having a %s in cargo, for a total power of %s per robot";
    public static final String robot_SizeDescriptions = Global.getSettings().getString("AIRetrofits_RobotCargoDescription");
    final public static String robot_baseText = Global.getSettings().getString("AIRetrofits_RobotPowerNoCoreMessage");
    public static final float robot_baseBonus = Global.getSettings().getFloat("AIRetrofits_BaseMulti");
    public static final float[] robot_AICoreBonus = {
        Global.getSettings().getFloat("AIRetrofits_OmegaMulti"),
        Global.getSettings().getFloat("AIRetrofits_AlphaMulti"),
        Global.getSettings().getFloat("AIRetrofits_BetaMulti"),
        Global.getSettings().getFloat("AIRetrofits_GammaMulti"),
        Global.getSettings().getFloat("AIRetrofits_CommandMulti"),
    };
    public static final String[] robot_AICores = {
            "omega_core",
            "alpha_core",
            "beta_core",
            "gamma_core",
            "AIretrofit_SubCommandNode",
    };
//Found A Market
    public static final float FoundAMarket_reqAICore = Global.getSettings().getFloat("AIRetrofit_MarketCost_reqAICore");//10
    public static final float FoundAMarket_reqWorker = Global.getSettings().getFloat("AIRetrofit_MarketCost_reqWorker");//1000;
    public static final float FoundAMarket_reqSupply = Global.getSettings().getFloat("AIRetrofit_MarketCost_reqSupply");//200;
    public static final float FoundAMarket_reqMachinery = Global.getSettings().getFloat("AIRetrofit_MarketCost_reqMachinery");//200;
    public static final String FoundAMarket_AICoreJob = "AIRetrofit_OutpostAICore";
    public static final String FoundAMarket_AIWorkerJob = "AIRetrofit_OutpostWorker";
    public static final String FoundAMarket_SupplyJob = "AIRetrofit_OutpostSupply";
    public static final String FoundAMarket_MachineryJob = "AIRetrofit_OutpostMachinery";


//RobotForge
    //people maker
    public static final int RobotForge_officerCreditCost = Global.getSettings().getInt("AIRetrofits_Officer_credits");//1000;
    public static final int RobotForge_administratorCreditCost = Global.getSettings().getInt("AIRetrofits_Admin_credits");///1000;
    public static final int RobotForge_operativeCreditCost = Global.getSettings().getInt("AIRetrofits_Operative_credits");//1000;
    public static final int RobotForge_officerSubCommandNodeCost = Global.getSettings().getInt("AIRetrofits_Officer_SCN");
    public static final int RobotForge_administratorSubCommandNodeCost = Global.getSettings().getInt("AIRetrofits_Admin_SCN");
    public static final int RobotForge_operativeSubCommandNodeCost = Global.getSettings().getInt("AIRetrofits_Operative_SCN");
    public static final int RobotForge_officerCreditsPerMomth = 900;
    public static final int RobotForge_administratorCreditsPerMomth = 2000;
    public static final String RobotForge_SubCommandNode = "AIretrofit_SubCommandNode";
    //forge power.
    public static final float RobotForge_CorruptedMetalMultiplier = Global.getSettings().getFloat("AIRetrofits_CorruptedMetal");
    public static final float RobotForge_PristineMetalMultiplier = Global.getSettings().getFloat("AIRetrofits_PristineMetal");
    public static final float RobotForge_ForgePowerMulti = Global.getSettings().getFloat("AIRetrofits_RobotForgePerCrewMulti");


//AIRetrofit Hullmod
    //AIRetrofit_PermaInstaled.
    //base
    public static final String AIRetrofit_Perma_Base_automationLevel = "automatedShipyard";
    public static final String AIRetrofit_Perma_Base_cantRemoveReason = "cannot be added or removed outside of a robotic shipyard";
    public static final float AIRetrofit_Perma_Base_SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardBase" + "_SUPPLY_USE_MULT");//1f;
    public static final float AIRetrofit_Perma_Base_CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardBase" + "_CREW_USE_MULT");//0f;
    public static final float AIRetrofit_Perma_Base_REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardBase" + "_REPAIR_LOSE");//0.5f;
    //gamma
    public static final String AIRetrofit_Perma_Gamma_automationLevel = "Gamma-Core";
    public static final float AIRetrofit_Perma_Gamma_SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardGamma" + "_SUPPLY_USE_MULT");//1f;
    public static final float AIRetrofit_Perma_Gamma_CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardGamma" + "_CREW_USE_MULT");//0f;
    public static final float AIRetrofit_Perma_Gamma_REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardGamma" + "_REPAIR_LOSE");//0.5f;
    //beta
    public static final String AIRetrofit_Perma_Beta_automationLevel = "Beta-Core";
    public static final float AIRetrofit_Perma_Beta_SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardBeta" + "_SUPPLY_USE_MULT");//1f;
    public static final float AIRetrofit_Perma_Beta_CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardBeta" + "_CREW_USE_MULT");//0f;
    public static final float AIRetrofit_Perma_Beta_REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardBeta" + "_REPAIR_LOSE");//0.5f;
    //alpha
    public static final String AIRetrofit_Perma_Alpha_automationLevel = "Alpha-Core";
    public static final float AIRetrofit_Perma_Alpha_SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_SUPPLY_USE_MULT");//1f;
    public static final float AIRetrofit_Perma_Alpha_CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_CREW_USE_MULT");//0f;
    public static final float AIRetrofit_Perma_Alpha_REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_REPAIR_LOSE");//0.5f;
    public static final float[] AIRetrofit_Perma_Alpha_maxOp = {
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_MaxOpOther"),
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_MaxOpFrigate"),
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_MaxOpDestroyer"),
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_MaxOpCruiser"),
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_MaxOpCapitalShip")
    };
    public static final float[] AIRetrofit_Perma_Alpha_CrewPerCostPerSize = {
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_C-OP-Other"),
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_C-OP-Frigate"),
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_C-OP-Destroyer"),
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_C-OP-Cruiser"),
            Global.getSettings().getFloat("AIRetrofits_" + "AIRetrofit_ShipyardAlpha" + "_C-OP-Capital_ship")
    };

//Automated Ship Instalation Center.
    //hullmods and required industry.
    public static final String ASIC_BaseHullmod = "AIretrofit_airetrofit";
    public static final String ASIC_shipYardIndustry = "AIRetrofit_shipYard";
    public final static String ASIC_subbmarket = "AIRetrofit_ShipyardSubmarket";
    public static final String[] ASIC_hullmods = {
            "AIRetrofit_ShipyardGamma",
            "AIRetrofit_ShipyardBeta",
            "AIRetrofit_ShipyardAlpha",
            "AIRetrofit_ShipyardOmega",
            "AIRetrofit_ShipyardBase"};
    public static final String[] ASIC_Secondary_Hullmods = {
            AIRetrofits_Constants.Hullmod_PatchworkAIRetrofit,
    };

    public final static float ASIC_improveValue = Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    public final static float ASIC_defaultValue = Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");

    public final static float[] ASIC_costPerShip = {
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewDEFAULT"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewFIGHTER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewFRIGATE"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewDESTROYER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewCRUISER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_perCrewCAPITAL_SHIP"),
    };
    public final static float[] ASIC_creditsPerShip = {
            Global.getSettings().getFloat("AIRetrofitShipyard_Credits_perCrewDEFAULT"),
            Global.getSettings().getFloat("AIRetrofitShipyard_Credits_perCrewFIGHTER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_Credits_perCrewFRIGATE"),
            Global.getSettings().getFloat("AIRetrofitShipyard_Credits_perCrewDESTROYER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_Credits_perCrewCRUISER"),
            Global.getSettings().getFloat("AIRetrofitShipyard_Credits_perCrewCAPITAL_SHIP"),
    };
    public final static String ASIC_NotificationCredits = Global.getSettings().getString("AIRetrofitShipyard_Notification_credits");
    public final static String ASIC_NotificationBonusXP = Global.getSettings().getString("AIRetrofitShipyard_Notification_bonusXP");
    public final static String ASIC_NotificationMarket = Global.getSettings().getString("AIRetrofitShipyard_Notification_market");
    public final static String ASIC_NotificationType = Global.getSettings().getString("AIRetrofitShipyard_Notification_type");
    public final static float ASIC_bonusXPForRemoveSMod = 0;

    public final static String ASIC_Description_SPM = Global.getSettings().getString("AIRetrofitShipyard_Description_shipPerMomth");
    public final static String ASIC_Description_CPS = Global.getSettings().getString("AIRetrofitShipyard_Description_creditsPerShip");
    //fleet changing crew and admins:
    public static final boolean fleetChange_ChangeCrew = Global.getSettings().getBoolean("AIRetrofits_SwapAICrew");
    //HERE swap this info out with something in an config please.
    public static final String[] fleetChange_itemIn = {"crew","marines"};
    //HERE swap this info out with something in an config please.
    public static final String[] fleetChange_itemOut = {"AIretrofit_WorkerDrone","AIretrofit_CombatDrone"};
    //HERE add a new item determining the ratio of crew change that i want to have plz.


//marketData
    //conditions and levers
    public static final boolean Market_EnableLogs = Global.getSettings().getBoolean("AIRetrofit_GrowthLogs");
    public static final boolean Market_EnableMarketFetures = Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    public static final String Market_Condition = "AIRetrofit_AIPop";

    //growth mods:
    public static final String Market_GrowthMod_hazzardPayName = "Hazard pay robot factory's";
    //public static final String Market_GrowthMod_population_AIRetrofit_0 = "population_AIRetrofit_0";
    //public static final String Market_GrowthMod_population_AIRetrofit_1 = "population_AIRetrofit_1";
    //public static final String Market_GrowthMod_population_AIRetrofit_2 = "population_AIRetrofit_2";
    public static final String Market_GrowthMod_AIRetrofits_BasicDroneFactory_0 = "AIRetrofits_BasicDroneFactory_0";
    //public static final String Market_GrowthMod_AIRetrofits_AdvancedDroneFactory_0 = "AIRetrofits_AdvancedDroneFactory_0";//unused
    public static final String Market_GrowthMod_LocalRobotFactorys = "LocalRobotFactory's";
    public static final String Market_GrowthMod_FactionWideRobotFactorys = "FactionWideRobotFactory's";
    public static final String Market_GrowthMod_AIRetrofits_RobotFactoryGrowthMod = "AIRetrofits_RobotFactoryGrowthMod";
    //growth descriptions
    public static final String Market_GrowthDescription_hazzardPay = Global.getSettings().getString("AIRetrofits_MarketGrowth_hazzardPayDescription");//"Building robots with hazard pay";
    //public static final String Market_GrowthDescription_population_AIRetrofit_0 = "";//not required
    //public static final String Market_GrowthDescription_population_AIRetrofit_1 = "";
    //public static final String Market_GrowthDescription_population_AIRetrofit_2 = "";
    public static final String Market_GrowthDescription_AIRetrofits_BasicDroneFactory_0 = "";//not required
    //public static final String Market_GrowthDescription_AIRetrofits_AdvancedDroneFactory_0 = "";//unused
    public static final String Market_GrowthDescription_LocalRobotFactorys = Global.getSettings().getString("AIRetrofits_MarketGrowth_T1GrowthDescription");//"Robots are being produced in this system, providing system wide market growth";
    public static final String Market_GrowthDescription_FactionWideRobotFactorys = Global.getSettings().getString("AIRetrofits_MarketGrowth_T2GrowthDescription");;
    public static final String Market_GrowthDescription_AIRetrofits_RobotFactoryGrowthMod = "";//not required


    public static final float Market_GrowthMod_hazzardGrowthPerSize = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_hazzardGrowthPerSize");//5;

    public static final String[] Market_WhiteListedGrowthMods = {//HERE <-- this needs to be swapped with data from config
            Market_GrowthMod_hazzardPayName,
            //Market_GrowthMod_population_AIRetrofit_0,
            //Market_GrowthMod_population_AIRetrofit_1,
            //Market_GrowthMod_population_AIRetrofit_2,
            Market_GrowthMod_AIRetrofits_BasicDroneFactory_0,
            //Market_GrowthMod_AIRetrofits_AdvancedDroneFactory_0,
            Market_GrowthMod_LocalRobotFactorys,
            Market_GrowthMod_FactionWideRobotFactorys,
            Market_GrowthMod_AIRetrofits_RobotFactoryGrowthMod,
    };

    //maz size before the market stops growing using robot factors.
    public static final int Market_Growth_maxSize = Global.getSettings().getInt("maxColonySize");//size of an market before it stops growing.
    //T1 market growth values:
    public static final String Market_Growth_IT1 = "AIRetrofit_roboticPopFactoryV1";
    public static final float Market_Growth_T1ImprovedBonus = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1ImprovedBonus");//1.3f;
    public static final float Market_Growth_T1PowerPerSize = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1PowerPerSize");//5;
    public static final float Market_Growth_T1HazzardBonus = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1HazzardBonus");//1.3f;
    public static final float Market_Growth_T1AplhaBonus = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1AlphaCoreBonus");
    //T2 market growth values
    public static final String Market_Growth_IT2 = "AIRetrofit_roboticPopFactoryV2";
    public static final float Market_Growth_T2ImprovedBonus = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T2ImprovedBonus");//1.3f;
    public static final float Market_Growth_T2PowerPerSize = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T2PowerPerSize");//10;
    public static final float Market_Growth_T2HazzardBonus = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T2HazzardBonus");//1.3f;
    public static final float Market_Growth_T2AplhaBonus = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1AlphaCoreBonus");

    //market logistic function
    public static final float Market_Growth_logisticTheshhold = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticTheshhold");//20;
    public static final String Market_Growth_requredCondition = "AIRetrofit_AIPop";//AIRetrofits market condition ID
    //(float)Math.max(1.5 * (Math.log(0.2 * power + 1) / Math.log(1.1)) + 0,0);
    //f(x) = 1.5 * log(1.1,0.2 * x + 1) + 0
    public static final float Market_Growth_logFunctionA = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticA");//1.5f;
    public static final float Market_Growth_logFunctionB = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticB");//1.1f;
    public static final float Market_Growth_logFunctionC = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticC");//0.2f;
    public static final float Market_Growth_logFunctionD = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticD");//1;
    public static final float Market_Growth_logFunctionE = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_logisticE");//0;


    //market robot types calculater ID's.
    public static final String RobotTypeCalculatorID_CombatT0_Attack = "A_CombatT0";
    public static final String RobotTypeCalculatorID_HevCombatT0_Attack = "A_Hev_CombatT0";
    //public static final String RobotTypeCalculatorID_MalCombatT0_Attack = "A_Mal_CombatT0";
    public static final String RobotTypeCalculatorID_CombatT1_Attack = "A_CombatT1";
    public static final String RobotTypeCalculatorID_HevCombatT1_Attack = "A_Hev_CombatT1";
    public static final String RobotTypeCalculatorID_CombatT2_Attack = "A_CombatT2";
    public static final String RobotTypeCalculatorID_HevCombatT2_Attack = "A_Hev_CombatT2";

    public static final String RobotTypeCalculatorID_CombatT0_Defence = "D_CombatT0";
    public static final String RobotTypeCalculatorID_HevCombatT0_Defence = "D_Hev_CombatT0";
    public static final String RobotTypeCalculatorID_MalCombatT0_Defence = "D_Mal_CombatT0";
    public static final String RobotTypeCalculatorID_CombatT1_Defence = "D_CombatT1";
    public static final String RobotTypeCalculatorID_HevCombatT1_Defence = "D_Hev_CombatT1";
    public static final String RobotTypeCalculatorID_CombatT2_Defence = "D_CombatT2";
    public static final String RobotTypeCalculatorID_HevCombatT2_Defence = "D_Hev_CombatT2";
    public static final String RobotTypeCalculatorID_WorkerT0_militia_Defence = "D_Mal_WorkerT0";
    public static final String RobotTypeCalculatorID_WorkerT0_Rebel_Defence = "D_Reb_WorkerT0";
    public static final String RobotTypeCalculatorID_WorkerT1_militia_Defence = "D_Mal_WorkerT1";
    public static final String RobotTypeCalculatorID_WorkerT1_Rebel_Defence = "D_Reb_WorkerT1";
    public static final String RobotTypeCalculatorID_WorkerT2_militia_Defence = "D_Mal_WorkerT2";
    public static final String RobotTypeCalculatorID_WorkerT2_Rebel_Defence = "D_Reb_WorkerT2";
    //Command Node and person info:
    public static final String[] SpecalItemID_CommandNodes = {
            "AIRetrofit_CommandNode_0",
            "AIRetrofit_CommandNode_1",
            "AIRetrofit_CommandNode_2",
            "AIRetrofit_CommandNode_3",
            "AIRetrofit_CommandNode_4",
            "AIRetrofit_CommandNode_5",
            "AIRetrofit_CommandNode_6",
            "AIRetrofit_CommandNode_7",
            "AIRetrofit_CommandNode_8",
            "AIRetrofit_CommandNode_9",
};
    public static final float[] SpecalItem_CommandNodes_thresholds = {
        Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_0"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_1"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_2"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_3"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_4"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_5"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_6"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_7"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_8"),
            Global.getSettings().getFloat("AIRetrofit_CommandNode_PowerCostThreshold_9"),
    };
}
