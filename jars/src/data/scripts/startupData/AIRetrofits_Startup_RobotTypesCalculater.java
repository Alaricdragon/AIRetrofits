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
    }
    public static final String
            BMar = "",BHev="",BReb="",BMil="",
            T0Mar = "",T0Hev="",T0Reb="",T0Mil="",
            T1Mar = "",T1Hev="",T1Reb="",T1Mil="",
            T2Mar = "",T2Hev="",T2Reb="",T2Mil="";


    public static final String marineID="marines";
    public static final String I_T0_CRForge=AIRetrofits_Constants.Industry_AIRetrofit_combatRobotManufactory;
    public static final String I_T1_CRForge=AIRetrofits_Constants.Industry_AIRetrofit_salvageRobotManufactory_V2;
    public static final String I_T0_WRForge=AIRetrofits_Constants.Industry_AIRetrofit_salvageRobotManufactory;
    public static final String I_T1_WRForge=AIRetrofits_Constants.Industry_AIRetrofit_salvageRobotManufactory_V2;

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



        //marines -> advanced combat robots, attack, supply
        ACR_CAl_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_ACR_CAl_T0_L_A"),
        ACR_CAl_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_ACR_CAl_T0_G_A"),
        ACR_CAl_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_ACR_CAl_T1_L_A"),
        ACR_CAl_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_ACR_CAl_T1_G_A"),

        //marines -> advanced combat robots, defence, supply
        ACR_CAl_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_ACR_CAl_T0_L_D"),
        ACR_CAl_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_ACR_CAl_T0_G_D"),
        ACR_CAl_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_ACR_CAl_T1_L_D"),
        ACR_CAl_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_ACR_CAl_T1_G_D"),

        //Heavy units -> heavy advanced combat robots, attack, supply
        HeACR_CAl_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeACR_CAl_T0_L_A"),
        HeACR_CAl_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeACR_CAl_T0_G_A"),
        HeACR_CAl_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeACR_CAl_T1_L_A"),
        HeACR_CAl_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeACR_CAl_T1_G_A"),

        //Heavy units -> heavy advanced combat robots, defence, supply
        HeACR_CAl_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeACR_CAl_T0_L_D"),
        HeACR_CAl_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeACR_CAl_T0_G_D"),
        HeACR_CAl_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeACR_CAl_T1_L_D"),
        HeACR_CAl_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeACR_CAl_T1_G_D"),



        //marines -> advanced combat robots, attack, supply
        OCR_CAl_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OCR_CAl_T0_L_A"),
        OCR_CAl_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OCR_CAl_T0_G_A"),
        OCR_CAl_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OCR_CAl_T1_L_A"),
        OCR_CAl_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OCR_CAl_T1_G_A"),

        //marines -> advanced combat robots, defence, supply
        OCR_CAl_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OCR_CAl_T0_L_D"),
        OCR_CAl_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OCR_CAl_T0_G_D"),
        OCR_CAl_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OCR_CAl_T1_L_D"),
        OCR_CAl_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OCR_CAl_T1_G_D"),

        //Heavy units -> heavy advanced combat robots, attack, supply
        HeOCR_CAl_T0_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeOCR_CAl_T0_L_A"),
        HeOCR_CAl_T0_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeOCR_CAl_T0_G_A"),
        HeOCR_CAl_T1_L_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeOCR_CAl_T1_L_A"),
        HeOCR_CAl_T1_G_A=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeOCR_CAl_T1_G_A"),

        //Heavy units -> heavy advanced combat robots, defence, supply
        HeOCR_CAl_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeOCR_CAl_T0_L_D"),
        HeOCR_CAl_T0_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeOCR_CAl_T0_G_D"),
        HeOCR_CAl_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeOCR_CAl_T1_L_D"),
        HeOCR_CAl_T1_G_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_HeOCR_CAl_T1_G_D"),


        //malita -> baisc combat robots, def, supply
        Mil_CBe_T0_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_Mil_CBe_T0_L_D"),
        Mil_CBe_T1_L_D=Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_Mil_CBe_T1_L_D"),

        //#milita -> basic worker robots, def, supply
        BWRm_Cna_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_Cna_T0_L_D"),
        BWRm_Cna_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_Cna_T0_G_D"),
        BWRm_Cna_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_Cna_T1_L_D"),
        BWRm_Cna_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_Cna_T1_G_D"),

        BWRm_CGa_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_CGa_T0_L_D"),
        BWRm_CGa_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_CGa_T0_G_D"),
        BWRm_CGa_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_CGa_T1_L_D"),
        BWRm_CGa_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_CGa_T1_G_D"),

        BWRm_CBe_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_CBe_T0_L_D"),
        BWRm_CBe_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_CBe_T0_G_D"),
        BWRm_CBe_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_CBe_T1_L_D"),
        BWRm_CBe_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRm_CBe_T1_G_D"),


        //#rebel -> basic worker robots, def, supply
        BWRr_Cna_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_Cna_T0_L_D"),
        BWRr_Cna_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_Cna_T0_G_D"),
        BWRr_Cna_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_Cna_T1_L_D"),
        BWRr_Cna_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_Cna_T1_G_D"),

        BWRr_CGa_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_CGa_T0_L_D"),
        BWRr_CGa_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_CGa_T0_G_D"),
        BWRr_CGa_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_CGa_T1_L_D"),
        BWRr_CGa_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_CGa_T1_G_D"),

        BWRr_CBe_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_CBe_T0_L_D"),
        BWRr_CBe_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_CBe_T0_G_D"),
        BWRr_CBe_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_CBe_T1_L_D"),
        BWRr_CBe_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_BWRr_CBe_T1_G_D"),

        //militia => advanced worker robots, def, supply
        AWRm_CAl_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_AWRm_CAl_T0_L_D"),
        AWRm_CAl_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_AWRm_CAl_T0_G_D"),
        AWRm_CAl_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_AWRm_CAl_T1_L_D"),
        AWRm_CAl_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_AWRm_CAl_T1_G_D"),

        //#rebel -> advanced worker robots, def, supply
        AWRr_CAl_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_AWRr_CAl_T0_L_D"),
        AWRr_CAl_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_AWRr_CAl_T0_G_D"),
        AWRr_CAl_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_AWRr_CAl_T1_L_D"),
        AWRr_CAl_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_AWRr_CAl_T1_G_D"),

        //militia => advanced worker robots, def, supply
        OWRm_CAl_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OWRm_CAl_T0_L_D"),
        OWRm_CAl_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OWRm_CAl_T0_G_D"),
        OWRm_CAl_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OWRm_CAl_T1_L_D"),
        OWRm_CAl_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OWRm_CAl_T1_G_D"),

        //#rebel -> advanced worker robots, def, supply
        OWRr_CAl_T0_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OWRr_CAl_T0_L_D"),
        OWRr_CAl_T0_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OWRr_CAl_T0_G_D"),
        OWRr_CAl_T1_L_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OWRr_CAl_T1_L_D"),
        OWRr_CAl_T1_G_D = Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_OWRr_CAl_T1_G_D"),




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


        //marine -> advanced combat robots demand, attack
        D_ACR_Mar_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_ACR_Mar_A"),
        D_ACR_Bcr_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_ACR_Bcr_A"),
        D_ACR_Pop_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_ACR_Pop_A"),

        //marine -> advanced combat robots demand, attack
        D_ACR_Mar_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_ACR_Mar_D"),
        D_ACR_Bcr_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_ACR_Bcr_D"),
        D_ACR_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_ACR_Pop_D"),

        //heavy -> heavy advanced combat robots demand, attack
        D_HeACR_Mar_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeACR_Mar_A"),
        D_HeACR_Bcr_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeACR_Bcr_A"),
        D_HeACR_Pop_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeACR_Pop_A"),

        //heavy -> heacy advanced combat robots demand, attack
        D_HeACR_Mar_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeACR_Mar_D"),
        D_HeACR_Bcr_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeACR_Bcr_D"),
        D_HeACR_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeACR_Pop_D"),


        //marine -> T2 combat robots demand, attack
        D_OCR_Mar_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_OCR_Mar_A"),
        D_OCR_Bcr_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_OCR_Bcr_A"),
        D_OCR_Pop_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_OCR_Pop_A"),

        //marine -> T2 combat robots demand, attack
        D_OCR_Mar_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_OCR_Mar_D"),
        D_OCR_Bcr_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_OCR_Bcr_D"),
        D_OCR_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_OCR_Pop_D"),

        //heavy -> heavy T2 combat robots demand, attack
        D_HeOCR_Mar_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeOCR_Mar_A"),
        D_HeOCR_Bcr_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeOCR_Bcr_A"),
        D_HeOCR_Pop_A =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeOCR_Pop_A"),

        //heavy -> heacy T2 combat robots demand, attack
        D_HeOCR_Mar_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeOCR_Mar_D"),
        D_HeOCR_Bcr_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeOCR_Bcr_D"),
        D_HeOCR_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_HeOCR_Pop_D"),


    //milita -> basic worker robot. deamnd. defance
        D_BWRm_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_BWRm_Pop_D"),

        //rebel -> basic worker robot. deamnd. defance
        D_BWRr_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_BWRr_Pop_D"),

        //milita -> advanced worker robot. deamnd. defance
        D_AWRm_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_AWRm_Pop_D"),

        //rebel -> advanced worker robot. deamnd. defance
        D_AWRr_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_AWRr_Pop_D"),

        //milita -> omega worker robot. deamnd. defance
        D_OWRm_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_OWRm_Pop_D"),

        //rebel -> omega worker robot. deamnd. defance
        D_OWRr_Pop_D =Global.getSettings().getFloat("AIRetrofit_Nexerlin_GroundBattles_D_OWRr_Pop_D"),

    Temp1=1;
    public static void addT0WorkerRobots(){
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence,new String[]{BMil},new String[]{T0Mil});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence,"",I_T0_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRm_Cna_T0_L_D,BWRm_Cna_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence, "",I_T1_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRm_Cna_T1_L_D,BWRm_Cna_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence,Commodities.GAMMA_CORE,I_T0_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRm_CGa_T0_L_D,BWRm_CGa_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence, Commodities.GAMMA_CORE,I_T1_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRm_CGa_T1_L_D,BWRm_CGa_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence,Commodities.BETA_CORE,I_T0_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRm_CBe_T0_L_D,BWRm_CBe_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence, Commodities.BETA_CORE,I_T1_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRm_CBe_T1_L_D,BWRm_CBe_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Supply_2(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence, D_BWRm_Pop_D);




        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_Rebel_Defence,new String[]{BReb},new String[]{T0Reb});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_Rebel_Defence,"",I_T0_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRr_Cna_T0_L_D,BWRr_Cna_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_Rebel_Defence, "",I_T1_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRr_Cna_T1_L_D,BWRr_Cna_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_Rebel_Defence,Commodities.GAMMA_CORE,I_T0_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRr_CGa_T0_L_D,BWRr_CGa_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_Rebel_Defence, Commodities.GAMMA_CORE,I_T1_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRr_CGa_T1_L_D,BWRr_CGa_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_Rebel_Defence,Commodities.BETA_CORE,I_T0_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRr_CBe_T0_L_D,BWRr_CBe_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_Rebel_Defence, Commodities.BETA_CORE,I_T1_WRForge,AIRetrofits_Constants.Commodity_T0_WorkerDrone,BWRr_CBe_T1_L_D,BWRr_CBe_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Supply_2(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_Rebel_Defence);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT0_militia_Defence, D_BWRr_Pop_D);
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
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Attack,marineID,null, D_BCR_Mar_A,0);
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
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT0_Defence,marineID,null, D_BCR_Mar_D,0);
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
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Attack,marineID,null, D_HeBCR_Mar_A,0);
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
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT0_Defence,marineID,null, D_HeBCR_Mar_D,0);
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
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT1_militia_Defence,new String[]{BMil},new String[]{T1Mil});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT1_militia_Defence,Commodities.ALPHA_CORE,I_T0_WRForge,AIRetrofits_Constants.Commodity_T1_WorkerDrone,AWRm_CAl_T0_L_D,AWRm_CAl_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT1_militia_Defence,Commodities.ALPHA_CORE,I_T1_WRForge,AIRetrofits_Constants.Commodity_T1_WorkerDrone,AWRm_CAl_T1_L_D,AWRm_CAl_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT1_militia_Defence,D_AWRm_Pop_D);


        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT1_Rebel_Defence,new String[]{BReb},new String[]{T1Reb});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT1_Rebel_Defence,Commodities.ALPHA_CORE,I_T0_WRForge,AIRetrofits_Constants.Commodity_T1_WorkerDrone,AWRr_CAl_T0_L_D,AWRr_CAl_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT1_Rebel_Defence,Commodities.ALPHA_CORE,I_T1_WRForge,AIRetrofits_Constants.Commodity_T1_WorkerDrone,AWRr_CAl_T1_L_D,AWRr_CAl_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT1_Rebel_Defence,D_AWRr_Pop_D);
    }
    public static void addT1CombatRobots(){
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Attack,new String[]{BMar},new String[]{T1Mar});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Attack,Commodities.ALPHA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,ACR_CAl_T0_L_D,ACR_CAl_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Attack,Commodities.ALPHA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,ACR_CAl_T1_L_D,ACR_CAl_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Attack,D_ACR_Pop_A);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Attack,Commodities.MARINES,null,D_ACR_Mar_A,0);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Attack,AIRetrofits_Constants.Commodity_T0_CombatDrone,null,D_ACR_Bcr_A,0);


        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence,new String[]{BMar},new String[]{T1Mar});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence,Commodities.ALPHA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,ACR_CAl_T0_L_A,ACR_CAl_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence,Commodities.ALPHA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,ACR_CAl_T1_L_A,ACR_CAl_T1_G_A);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence,D_ACR_Pop_D);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence,Commodities.MARINES,null,D_ACR_Mar_D,0);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence,AIRetrofits_Constants.Commodity_T0_CombatDrone,null,D_ACR_Bcr_D,0);



        new AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Attack,new String[]{BHev},new String[]{T1Hev});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Attack,Commodities.ALPHA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,HeACR_CAl_T0_L_D,HeACR_CAl_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Attack,Commodities.ALPHA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,HeACR_CAl_T1_L_D,HeACR_CAl_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Attack,D_HeACR_Pop_A);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Attack,Commodities.MARINES,null,D_HeACR_Mar_A,0);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Attack,AIRetrofits_Constants.Commodity_T0_CombatDrone,null,D_HeACR_Bcr_A,0);


        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Defence,new String[]{BHev},new String[]{T1Hev});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Defence,Commodities.ALPHA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,HeACR_CAl_T0_L_A,HeACR_CAl_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Defence,Commodities.ALPHA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,HeACR_CAl_T1_L_A,HeACR_CAl_T1_G_A);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Defence,D_HeACR_Pop_D);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Defence,Commodities.MARINES,null,D_HeACR_Mar_D,0);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT1_Defence,AIRetrofits_Constants.Commodity_T0_CombatDrone,null,D_HeACR_Bcr_D,0);
    }
    public static void addT2WorkerRobots(){
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT2_militia_Defence,new String[]{BMil},new String[]{T2Mil});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT2_militia_Defence,Commodities.ALPHA_CORE,I_T0_WRForge,AIRetrofits_Constants.Commodity_T2_WorkerDrone,OWRm_CAl_T0_L_D,OWRm_CAl_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT2_militia_Defence,Commodities.ALPHA_CORE,I_T1_WRForge,AIRetrofits_Constants.Commodity_T2_WorkerDrone,OWRm_CAl_T1_L_D,OWRm_CAl_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT2_militia_Defence,D_OWRm_Pop_D);


        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT2_Rebel_Defence,new String[]{BReb},new String[]{T2Reb});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT2_Rebel_Defence,Commodities.ALPHA_CORE,I_T0_WRForge,AIRetrofits_Constants.Commodity_T2_WorkerDrone,OWRr_CAl_T0_L_D,OWRr_CAl_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT2_Rebel_Defence,Commodities.ALPHA_CORE,I_T1_WRForge,AIRetrofits_Constants.Commodity_T2_WorkerDrone,OWRr_CAl_T1_L_D,OWRr_CAl_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_WorkerT2_Rebel_Defence,D_OWRr_Pop_D);
    }
    public static void addT2CombatRobots(){
        new AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Attack,new String[]{BMar},new String[]{T2Mar});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Attack,Commodities.ALPHA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,OCR_CAl_T0_L_D,OCR_CAl_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Attack,Commodities.ALPHA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,OCR_CAl_T1_L_D,OCR_CAl_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Attack,D_OCR_Pop_A);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Attack,Commodities.MARINES,null,D_OCR_Mar_A,0);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Attack,AIRetrofits_Constants.Commodity_T0_CombatDrone,null,D_OCR_Bcr_A,0);


        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence,new String[]{BMar},new String[]{T2Mar});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence,Commodities.ALPHA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,OCR_CAl_T0_L_A,OCR_CAl_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence,Commodities.ALPHA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,OCR_CAl_T1_L_A,OCR_CAl_T1_G_A);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence,D_OCR_Pop_D);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence,Commodities.MARINES,null,D_OCR_Mar_D,0);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence,AIRetrofits_Constants.Commodity_T0_CombatDrone,null,D_OCR_Bcr_D,0);



        new AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Attack,new String[]{BHev},new String[]{T2Hev});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Attack,Commodities.ALPHA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,HeOCR_CAl_T0_L_D,HeOCR_CAl_T0_G_D);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Attack,Commodities.ALPHA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,HeOCR_CAl_T1_L_D,HeOCR_CAl_T1_G_D);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Attack,D_HeOCR_Pop_A);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Attack,Commodities.MARINES,null,D_HeOCR_Mar_A,0);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Attack,AIRetrofits_Constants.Commodity_T0_CombatDrone,null,D_HeOCR_Bcr_A,0);


        new AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Defence,new String[]{BHev},new String[]{T2Hev});

        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Defence,Commodities.ALPHA_CORE,I_T0_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,HeOCR_CAl_T0_L_A,HeOCR_CAl_T0_G_A);
        new AIRetrofits_Robot_Types_checker_Supply_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Defence,Commodities.ALPHA_CORE,I_T1_CRForge,AIRetrofits_Constants.Commodity_T1_CombatDrone,HeOCR_CAl_T1_L_A,HeOCR_CAl_T1_G_A);

        new AIRetrofits_Robot_Types_checker_Demand_1(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Defence,D_HeOCR_Pop_D);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Defence,Commodities.MARINES,null,D_HeOCR_Mar_D,0);
        new AIRetrofits_Robot_Types_checker_Demand_0(AIRetrofits_Constants.RobotTypeCalculatorID_HevCombatT2_Defence,AIRetrofits_Constants.Commodity_T0_CombatDrone,null,D_HeOCR_Bcr_D,0);
    }
}
