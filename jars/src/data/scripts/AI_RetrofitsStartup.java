package data.scripts;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import data.scripts.robot_forge.AIRetrofits_ForgeItem;
import data.scripts.robot_forge.AIRetrofits_ForgeList;
import data.scripts.robot_forge.AIRetrofits_RobotForge;

public class AI_RetrofitsStartup extends BaseModPlugin {
    @Override
    public void onApplicationLoad() {
        //crew_replacer.addCrewType("AIretrofit_WorkerDrone");
        crew_replacer_start_new();
        robot_forge_set();
    }
    @Override
    public void onGameLoad(boolean newGame) {
        //something wrong with crewreplacer addCrewType. causes out of bounds
        //crew_replacer_start_outdated();
        super.onGameLoad(newGame);
    }
//300,1550
//2000,150
    private void crew_replacer_start_outdated(){
        //crew_replacer.addCrewType("AIretrofit_WorkerDrone");
        /*crew_replacer.addCrewType("AIretrofit_WorkerDrone");
        crew_replacer.setCrewJob("AIretrofit_WorkerDrone","salvage",true);
        crew_replacer.setCrewLost("AIretrofit_WorkerDrone","salvage",(float)1,(float)1);

        crew_replacer.addCrewType("AIretrofit_SurveyDrone");
        crew_replacer.setCrewJob("AIretrofit_SurveyDrone","survey",true);
        crew_replacer.setCrewPower("crew_replacer","survey",3);*/
        //crew_replacer.setCrewLost("AIretrofit_SurveyDrone","survey",(float)1,(float)1);
        //crew_replacer.setCrewPower("AIretrofit_WorkerDrone","salvage",(float)1);
    }
    float Sa_Po = Global.getSettings().getFloat("AIRetrofits_Salvage_Power");
    float Sa_Pr = Global.getSettings().getFloat("AIRetrofits_Salvage_priority");

    float Su_Po = Global.getSettings().getFloat("AIRetrofits_Survey_Power");
    float Su_Pr = Global.getSettings().getFloat("AIRetrofits_Survey_priority");

    float Co_Po = Global.getSettings().getFloat("AIRetrofits_Combat_Power");
    float Co_Pr = Global.getSettings().getFloat("AIRetrofits_Combat_priority");
    private void crew_replacer_start_new(){
        crewReplacer_Job tempJob = crewReplacer_Main.getJob("survey_crew");
        /*surveyDrones tempcrew = new surveyDrones();
        tempcrew.name = "AIretrofit_SurveyDrone";
        tempcrew.crewPower = 2.5f;
        tempJob.addCrew(tempcrew);*/
        tempJob.addNewCrew("AIretrofit_SurveyDrone",Su_Po,Su_Pr);
        //tempJob.addNewCrew("AIretrofit_WorkerDrone",1,10,0,0,true);

        tempJob = crewReplacer_Main.getJob("salvage_crew");//survey_main
        tempJob.addNewCrew("AIretrofit_WorkerDrone",Sa_Po,Sa_Pr);//,1,1,true);

        tempJob = crewReplacer_Main.getJob("raiding_marines");
        tempJob.addNewCrew("AIretrofit_CombatDrone",Co_Po,Co_Pr);
/*
        tempJob = crewReplacer_Main.getJob("survey_crew");
        tempJob.addNewCrew("AIretrofit_WorkerDrone",25,10);

        tempJob = crewReplacer_Main.getJob("survey_supply");
        tempJob.addNewCrew("AIretrofit_WorkerDrone",25,10);

        tempJob = crewReplacer_Main.getJob("survey_heavyMachinery");
        tempJob.addNewCrew("AIretrofit_WorkerDrone",25,10);*/

    }
    float SaS = Global.getSettings().getFloat("AIRetrofits_Sa_S");
    float SaB = Global.getSettings().getFloat("AIRetrofits_Sa_B");
    String Sa1N = Global.getSettings().getString("AIRetrofits_Sa_1N");
    float Sa1C = Global.getSettings().getFloat("AIRetrofits_Sa_1C");
    String Sa2N = Global.getSettings().getString("AIRetrofits_Sa_2N");
    float Sa2C = Global.getSettings().getFloat("AIRetrofits_Sa_2C");
    String Sa3N = Global.getSettings().getString("AIRetrofits_Sa_3N");
    float Sa3C = Global.getSettings().getFloat("AIRetrofits_Sa_3C");

    float SuS = Global.getSettings().getFloat("AIRetrofits_Su_S");
    float SuB = Global.getSettings().getFloat("AIRetrofits_Su_B");
    String Su1N = Global.getSettings().getString("AIRetrofits_Su_1N");
    float Su1C = Global.getSettings().getFloat("AIRetrofits_Su_1C");
    String Su2N = Global.getSettings().getString("AIRetrofits_Su_2N");
    float Su2C = Global.getSettings().getFloat("AIRetrofits_Su_2C");
    String Su3N = Global.getSettings().getString("AIRetrofits_Su_3N");
    float Su3C = Global.getSettings().getFloat("AIRetrofits_Su_3C");

    float CoS = Global.getSettings().getFloat("AIRetrofits_Co_S");
    float CoB = Global.getSettings().getFloat("AIRetrofits_Co_B");
    String Co1N = Global.getSettings().getString("AIRetrofits_Co_1N");
    float Co1C = Global.getSettings().getFloat("AIRetrofits_Co_1C");
    String Co2N = Global.getSettings().getString("AIRetrofits_Co_2N");
    float Co2C = Global.getSettings().getFloat("AIRetrofits_Co_2C");
    String Co3N = Global.getSettings().getString("AIRetrofits_Co_3N");
    float Co3C = Global.getSettings().getFloat("AIRetrofits_Co_3C");

    String SaD = Global.getSettings().getString("AIRetrofits_Sa_D");
    String SuD = Global.getSettings().getString("AIRetrofits_Su_D");
    String CoD = Global.getSettings().getString("AIRetrofits_Co_D");
    private void robot_forge_set(){
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
        //item.addRequiredItem(Su1N, Su1C);
        //item.addRequiredItem(Su2N, Su2C);
        item.addOutputItem("AIretrofit_SurveyDrone",SuB);//50
        AIRetrofits_ForgeList.addItem(item);

        item = new AIRetrofits_ForgeItem("raiding drones",CoD,CoS);
        //item.addRequiredItem("supplies", (float) 0.1);
        robotAddReq(Co1C,Co1N,item);
        robotAddReq(Co2C,Co2N,item);
        robotAddReq(Co3C,Co3N,item);
        item.addOutputItem("AIretrofit_CombatDrone",CoB);//200
        AIRetrofits_ForgeList.addItem(item);
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
    private void robotAddReq(float cost, String name, AIRetrofits_ForgeItem thing){
        if(cost != 0 && name.length() != 0){
            thing.addRequiredItem(name,cost);
        }
    }
}