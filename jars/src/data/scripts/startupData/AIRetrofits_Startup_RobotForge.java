package data.scripts.startupData;

import com.fs.starfarer.api.Global;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.robot_forge.AIRetrofits_ForgeItem;
import data.scripts.robot_forge.AIRetrofits_ForgeList;
import data.scripts.robot_forge.AIRetrofits_RobotForge;
import data.scripts.robot_forge.AIRetrofits_RobotForge_People;

public class AIRetrofits_Startup_RobotForge {
    public static void apply(){
        robot_forge_set();
    }
    private static final float SaS = Global.getSettings().getFloat("AIRetrofits_Sa_S");
    private static final float SaB = Global.getSettings().getFloat("AIRetrofits_Sa_B");
    private static final String Sa1N = Global.getSettings().getString("AIRetrofits_Sa_1N");
    private static final float Sa1C = Global.getSettings().getFloat("AIRetrofits_Sa_1C");
    private static final String Sa2N = Global.getSettings().getString("AIRetrofits_Sa_2N");
    private static final float Sa2C = Global.getSettings().getFloat("AIRetrofits_Sa_2C");
    private static final String Sa3N = Global.getSettings().getString("AIRetrofits_Sa_3N");
    private static final float Sa3C = Global.getSettings().getFloat("AIRetrofits_Sa_3C");

    private static final float SuS = Global.getSettings().getFloat("AIRetrofits_Su_S");
    private static final float SuB = Global.getSettings().getFloat("AIRetrofits_Su_B");
    private static final String Su1N = Global.getSettings().getString("AIRetrofits_Su_1N");
    private static final float Su1C = Global.getSettings().getFloat("AIRetrofits_Su_1C");
    private static final String Su2N = Global.getSettings().getString("AIRetrofits_Su_2N");
    private static final float Su2C = Global.getSettings().getFloat("AIRetrofits_Su_2C");
    private static final String Su3N = Global.getSettings().getString("AIRetrofits_Su_3N");
    private static final float Su3C = Global.getSettings().getFloat("AIRetrofits_Su_3C");

    private static final float CoS = Global.getSettings().getFloat("AIRetrofits_Co_S");
    private static final float CoB = Global.getSettings().getFloat("AIRetrofits_Co_B");
    private static final String Co1N = Global.getSettings().getString("AIRetrofits_Co_1N");
    private static final float Co1C = Global.getSettings().getFloat("AIRetrofits_Co_1C");
    private static final String Co2N = Global.getSettings().getString("AIRetrofits_Co_2N");
    private static final float Co2C = Global.getSettings().getFloat("AIRetrofits_Co_2C");
    private static final String Co3N = Global.getSettings().getString("AIRetrofits_Co_3N");
    private static final float Co3C = Global.getSettings().getFloat("AIRetrofits_Co_3C");

    private static final float ScS = Global.getSettings().getFloat("AIRetrofits_Sc_S");
    private static final float ScB = Global.getSettings().getFloat("AIRetrofits_Sc_B");
    private static final String Sc1N = Global.getSettings().getString("AIRetrofits_Sc_1N");
    private static final float Sc1C = Global.getSettings().getFloat("AIRetrofits_Sc_1C");
    private static final String Sc2N = Global.getSettings().getString("AIRetrofits_Sc_2N");
    private static final float Sc2C = Global.getSettings().getFloat("AIRetrofits_Sc_2C");
    private static final String Sc3N = Global.getSettings().getString("AIRetrofits_Sc_3N");
    private static final float Sc3C = Global.getSettings().getFloat("AIRetrofits_Sc_3C");

    public static final String SaD = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Sa_D");
    private static final String SuD = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Su_D");
    private static final String CoD = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Co_D");
    private static final String ScD = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Sc_D");

    private static void robot_forge_set(){
        AIRetrofits_ForgeItem item = new AIRetrofits_ForgeItem("salvage Drones",SaD,SaS);
        robotAddReq(Sa1C,Sa1N,item);
        robotAddReq(Sa2C,Sa2N,item);
        robotAddReq(Sa3C,Sa3N,item);
        item.addOutputItem("AIretrofit_WorkerDrone",SaB);//50
        AIRetrofits_ForgeList.addItem(item);

        item = new AIRetrofits_ForgeItem("survey drones",SuD,SuS);
        robotAddReq(Su1C,Su1N,item);
        robotAddReq(Su2C,Su2N,item);
        robotAddReq(Su3C,Su3N,item);
        item.addOutputItem("AIretrofit_SurveyDrone",SuB);//50
        AIRetrofits_ForgeList.addItem(item);

        item = new AIRetrofits_ForgeItem("raiding drones",CoD,CoS);
        //item.addRequiredItem("supplies", (float) 0.1);
        robotAddReq(Co1C,Co1N,item);
        robotAddReq(Co2C,Co2N,item);
        robotAddReq(Co3C,Co3N,item);
        item.addOutputItem("AIretrofit_CombatDrone",CoB);//200
        AIRetrofits_ForgeList.addItem(item);

        item = new AIRetrofits_ForgeItem("sub command node",ScD,ScS);
        robotAddReq(Sc1C,Sc1N,item);
        robotAddReq(Sc2C,Sc2N,item);
        robotAddReq(Sc3C,Sc3N,item);
        item.addOutputItem("AIretrofit_SubCommandNode",ScB);
        AIRetrofits_ForgeList.addItem(item);

        AIRetrofits_RobotForge_People dilog = new AIRetrofits_RobotForge_People("improve an sub command node","words words words. you shold never see this",0);
        AIRetrofits_ForgeList.addItem(dilog);

        AIRetrofits_RobotForge.setInitData();
    }

    /*
    1) metals = 30
    2) supplies = 100
    3) heavy_machinery = 150
    4) rare_metals = 200
    5) volatiles = 250
    6) hand_weapons = 500

    x)crew = 50
    x)mirriens = 200

    a)AIretrofit_WorkerDrone:75
        1: 2.5, 4: 0.33~, 3: 0.5     =3.33
        1: 0.7, 4:0.1, 3:0.15
        //0.95
    b)AIretrofit_SurveyDrone:75
        1: 2.5, 4: 0.33~,5:0.3    =3.13
        1: 0.8, 4: 0.11, 5: 0.096
        //1.06
    c)AIretrofit_CombatDrone:300
        1: 10, 4: 1.5, 6: 0.6       =12.1
        1: 0.82, 4: 0.124, 6: 0.05
        //0.945

     */
    private static void robotAddReq(float cost, String name, AIRetrofits_ForgeItem thing){
        if(cost != 0 && name.length() != 0){
            thing.addRequiredItem(name,cost);
        }
    }
}
