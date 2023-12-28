package data.scripts.startupData;

import com.fs.starfarer.api.Global;
import data.scripts.AIRetrofit_Robots;
import data.scripts.crewReplacer_CrewSet;
import data.scripts.crewReplacer_Job;
import data.scripts.crewReplacer_Main;

public class AIRetrofits_Startup_CrewReplacer {
    public static final String AICoreJob = "AIRetrofit_OutpostAICore";
    public static final String AIWorkerJob = "AIRetrofit_OutpostWorker";
    public static final String SupplyJob = "AIRetrofit_OutpostSupply";
    public static final String MachineryJob = "AIRetrofit_OutpostMachinery";
    public static final String hijack_marinesJob = "Mission_hijack_marines";
    public static final String repairHyperRelayCrewJob = "CoronalHyperShunt_repair_Crew";
    public static final String nexMarinesJob = "nex_groundBattle_marines";

    public static final String normadicSurvival_CrewSet_Crew = "normadicSurvival_crew";
    public static final String normadicSurvival_CrewSet_Marines = "normadicSurvival_marines";


    //ground combat
    public static final String GB_BCR = "AIRetrofit_GB_BCR";
    public static final String GB_BTC = "AIRetrofit_GB_BTC";
    public static final String GB_BWRm = "AIRetrofit_GB_BWRm";
    public static final String GB_BWRr = "AIRetrofit_GB_BWRr";

    public static final String GB_ACR = "AIRetrofit_GB_ACR";
    public static final String GB_ATC = "AIRetrofit_GB_ATC";
    public static final String GB_AWRm = "AIRetrofit_GB_AWRm";
    public static final String GB_AWRr = "AIRetrofit_GB_AWRr";

    public static final String GB_OCR = "AIRetrofit_GB_OCR";
    public static final String GB_OTC = "AIRetrofit_GB_OTC";
    public static final String GB_OWRm = "AIRetrofit_GB_OWRm";
    public static final String GB_OWRr = "AIRetrofit_GB_OWRr";


    public static final float GB_BCR_V = 1f;
    public static final float GB_BTC_V= 1f;
    public static final float GB_BWRm_V = 1f;
    public static final float GB_BWRr_V = 1f;

    public static final float GB_ACR_V = 1f;
    public static final float GB_ATC_V= 1f;
    public static final float GB_AWRm_V = 1f;
    public static final float GB_AWRr_V = 1f;

    public static final float GB_OCR_V = 1f;
    public static final float GB_OTC_V= 1f;
    public static final float GB_OWRm_V = 1f;
    public static final float GB_OWRr_V = 1f;


    static public void apply(){
        addMisc();

        addSalvageRobots();
        addCombatRobots();
        addSurveyRobots();

        addAdvancedSalvageRobots();
        addAdvancedCombatRobots();
        addAdvancedSurveyRobots();

        addOmegaSalvageRobots();
        addOmegaCombatRobots();
        addOmegaSurveyRobots();
        //crewReplacer_Main.getJob("AIretrofit_groundBattle_CombatDrone").addNewCrew("ore",1,10);
    }


    static private void addMisc(){
        //AIRetrofit added jobs
        crewReplacer_Job tempJob = crewReplacer_Main.getJob(AICoreJob);//addItem AIretrofit_WorkerDrone 1000; addItem supplies 200; addItem heavy_machinery 200; addItem AIretrofit_SubCommandNode 10;
        tempJob.addNewCrew("AIretrofit_SubCommandNode",1,10);
        tempJob = crewReplacer_Main.getJob(SupplyJob);
        tempJob.addNewCrew("supplies",1,10);
        tempJob = crewReplacer_Main.getJob(MachineryJob);
        tempJob.addNewCrew("heavy_machinery",1,10);


    }

    static private void addSalvageRobots(){
        final String robotType = "AIretrofit_WorkerDrone";
        final float Sa_Po = Global.getSettings().getFloat("AIRetrofits_Salvage_Power");
        final float Sa_Pr = Global.getSettings().getFloat("AIRetrofits_Salvage_priority");

        final float RHR_Po = Global.getSettings().getFloat("AIRetrofits_repairHyperRelay_Power");
        final float RHR_Pr = Global.getSettings().getFloat("AIRetrofits_repairHyperRelay_priority");

        //salvage main
        crewReplacer_Job tempJob = crewReplacer_Main.getJob("salvage_crew");
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Sa_Po;
        tempCrew.crewPriority = Sa_Pr;
        tempJob.addCrew(tempCrew);

        //repairHyperRelayCrew
        tempJob = crewReplacer_Main.getJob(repairHyperRelayCrewJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = RHR_Po;
        tempCrew.crewPriority = RHR_Pr;
        tempJob.addCrew(tempCrew);

        //AIRetorfit found a market.
        tempJob = crewReplacer_Main.getJob(AIWorkerJob);
        tempJob.addNewCrew(robotType,1,10);//AIretrofit_CombatDrone

        crewReplacer_CrewSet tempCrewSet = crewReplacer_Main.getCrewSet(normadicSurvival_CrewSet_Crew);
        tempCrewSet.addCrew(tempCrew);

        //nex job for combat
        crewReplacer_Main.getJob(GB_BWRm).addNewCrew(robotType,GB_BWRm_V,10);
        crewReplacer_Main.getJob(GB_BWRr).addNewCrew(robotType,GB_BWRr_V,10);
    }
    static private void addCombatRobots(){
        final String robotType = "AIretrofit_CombatDrone";

        final float Co_Po = Global.getSettings().getFloat("AIRetrofits_Combat_Power");
        final float Co_Pr = Global.getSettings().getFloat("AIRetrofits_Combat_priority");

        final float Hm_Po = Global.getSettings().getFloat("AIRetrofits_MissionHijack_Power");
        final float Hm_Pr = Global.getSettings().getFloat("AIRetrofits_MissionHijack_priority");

        final float nexM_Po = Global.getSettings().getFloat("AIRetrofits_GroundBattle_Power");
        final float nexM_Pr = Global.getSettings().getFloat("AIRetrofits_GroundBattle_priority");

        //base game raiding
        crewReplacer_Job tempJob = crewReplacer_Main.getJob("raiding_marines");
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Co_Po;
        tempCrew.crewPriority = Co_Pr;
        tempJob.addCrew(tempCrew);

        //Mission_hijack_marines
        tempJob = crewReplacer_Main.getJob(hijack_marinesJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Hm_Po;
        tempCrew.crewPriority = Hm_Pr;
        tempJob.addCrew(tempCrew);

        //nex job basic:
        /*tempJob = crewReplacer_Main.getJob(nexMarinesJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = nexM_Po;//Hm_Po
        tempCrew.crewPriority = nexM_Pr;//Hm_Pr
        tempJob.addCrew(tempCrew);*/

        //temp job that i removed
        /*tempJob = crewReplacer_Main.getJob("AIretrofit_groundBattle_CombatDrone");
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = 1;//Hm_Po
        tempCrew.crewPriority = 10;//Hm_Pr
        tempJob.addCrew(tempCrew);*/

        //nex job for combat
        crewReplacer_Main.getJob(GB_BCR).addNewCrew(robotType,GB_BCR_V,10);
        crewReplacer_Main.getJob(GB_BTC).addNewCrew(robotType,GB_BTC_V,10);

        crewReplacer_CrewSet tempCrewSet = crewReplacer_Main.getCrewSet(normadicSurvival_CrewSet_Marines);
        tempCrewSet.addCrew(tempCrew);
    }
    static private void addSurveyRobots(){
        final String robotType = "AIretrofit_SurveyDrone";
        final float Su_Po = Global.getSettings().getFloat("AIRetrofits_Survey_Power");
        final float Su_Pr = Global.getSettings().getFloat("AIRetrofits_Survey_priority");

        crewReplacer_Job tempJob = crewReplacer_Main.getJob("survey_crew");
        //base game survey job
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Su_Po;
        tempCrew.crewPriority = Su_Pr;
        tempJob.addCrew(tempCrew);
    }


    static private void addAdvancedSalvageRobots(){
        final String robotType = "AIretrofit_Advanced_WorkerDrone";
        final float Sa_Po = Global.getSettings().getFloat("AIRetrofits_Advanced_Salvage_Power");
        final float Sa_Pr = Global.getSettings().getFloat("AIRetrofits_Advanced_Salvage_priority");

        final float RHR_Po = Global.getSettings().getFloat("AIRetrofits_Advanced_repairHyperRelay_Power");
        final float RHR_Pr = Global.getSettings().getFloat("AIRetrofits_Advanced_repairHyperRelay_priority");

        //salvage main
        crewReplacer_Job tempJob = crewReplacer_Main.getJob("salvage_crew");
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Sa_Po;
        tempCrew.crewPriority = Sa_Pr;
        tempJob.addCrew(tempCrew);

        //repairHyperRelayCrew
        tempJob = crewReplacer_Main.getJob(repairHyperRelayCrewJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = RHR_Po;
        tempCrew.crewPriority = RHR_Pr;
        tempJob.addCrew(tempCrew);

        //AIRetorfit found a market.
        tempJob = crewReplacer_Main.getJob(AIWorkerJob);
        tempJob.addNewCrew(robotType,1,10);//AIretrofit_CombatDrone

        crewReplacer_CrewSet tempCrewSet = crewReplacer_Main.getCrewSet(normadicSurvival_CrewSet_Crew);
        tempCrewSet.addCrew(tempCrew);

        //nex job for combat
        crewReplacer_Main.getJob(GB_AWRm).addNewCrew(robotType,GB_AWRm_V,10);
        crewReplacer_Main.getJob(GB_AWRr).addNewCrew(robotType,GB_AWRr_V,10);
    }
    static private void addAdvancedCombatRobots(){
        final String robotType = "AIretrofit_Advanced_CombatDrone";

        final float Co_Po = Global.getSettings().getFloat("AIRetrofits_Advanced_Combat_Power");
        final float Co_Pr = Global.getSettings().getFloat("AIRetrofits_Advanced_Combat_priority");

        final float Hm_Po = Global.getSettings().getFloat("AIRetrofits_Advanced_MissionHijack_Power");
        final float Hm_Pr = Global.getSettings().getFloat("AIRetrofits_Advanced_MissionHijack_priority");

        final float nexM_Po = Global.getSettings().getFloat("AIRetrofits_Advanced_GroundBattle_Power");
        final float nexM_Pr = Global.getSettings().getFloat("AIRetrofits_Advanced_GroundBattle_priority");

        //base game raiding
        crewReplacer_Job tempJob = crewReplacer_Main.getJob("raiding_marines");
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Co_Po;
        tempCrew.crewPriority = Co_Pr;
        tempJob.addCrew(tempCrew);

        //Mission_hijack_marines
        tempJob = crewReplacer_Main.getJob(hijack_marinesJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Hm_Po;
        tempCrew.crewPriority = Hm_Pr;
        tempJob.addCrew(tempCrew);

        //nex job basic:
        /*tempJob = crewReplacer_Main.getJob(nexMarinesJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = nexM_Po;//Hm_Po
        tempCrew.crewPriority = nexM_Pr;//Hm_Pr
        tempJob.addCrew(tempCrew);*/

        crewReplacer_CrewSet tempCrewSet = crewReplacer_Main.getCrewSet(normadicSurvival_CrewSet_Marines);
        tempCrewSet.addCrew(tempCrew);


        //nex job for combat
        crewReplacer_Main.getJob(GB_ACR).addNewCrew(robotType,GB_ACR_V,10);
        crewReplacer_Main.getJob(GB_ATC).addNewCrew(robotType,GB_ATC_V,10);
    }
    static private void addAdvancedSurveyRobots(){
        final String robotType = "AIretrofit_Advanced_SurveyDrone";
        final float Su_Po = Global.getSettings().getFloat("AIRetrofits_Advanced_Survey_Power");
        final float Su_Pr = Global.getSettings().getFloat("AIRetrofits_Advanced_Survey_priority");

        crewReplacer_Job tempJob = crewReplacer_Main.getJob("survey_crew");
        //base game survey job
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Su_Po;
        tempCrew.crewPriority = Su_Pr;
        tempJob.addCrew(tempCrew);
    }


    static private void addOmegaSalvageRobots(){
        final String robotType = "AIretrofit_Omega_WorkerDrone";
        final float Sa_Po = Global.getSettings().getFloat("AIRetrofits_Omega_Salvage_Power");
        final float Sa_Pr = Global.getSettings().getFloat("AIRetrofits_Omega_Salvage_priority");

        final float RHR_Po = Global.getSettings().getFloat("AIRetrofits_Omega_repairHyperRelay_Power");
        final float RHR_Pr = Global.getSettings().getFloat("AIRetrofits_Omega_repairHyperRelay_priority");

        //salvage main
        crewReplacer_Job tempJob = crewReplacer_Main.getJob("salvage_crew");
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Sa_Po;
        tempCrew.crewPriority = Sa_Pr;
        tempJob.addCrew(tempCrew);

        //repairHyperRelayCrew
        tempJob = crewReplacer_Main.getJob(repairHyperRelayCrewJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = RHR_Po;
        tempCrew.crewPriority = RHR_Pr;
        tempJob.addCrew(tempCrew);

        //AIRetorfit found a market.
        tempJob = crewReplacer_Main.getJob(AIWorkerJob);
        tempJob.addNewCrew(robotType,1,10);//AIretrofit_CombatDrone

        crewReplacer_CrewSet tempCrewSet = crewReplacer_Main.getCrewSet(normadicSurvival_CrewSet_Crew);
        tempCrewSet.addCrew(tempCrew);

        //nex job for combat
        crewReplacer_Main.getJob(GB_OWRm).addNewCrew(robotType,GB_OWRm_V,10);
        crewReplacer_Main.getJob(GB_OWRr).addNewCrew(robotType,GB_OWRr_V,10);
    }
    static private void addOmegaCombatRobots(){
        final String robotType = "AIretrofit_Omega_CombatDrone";

        final float Co_Po = Global.getSettings().getFloat("AIRetrofits_Omega_Combat_Power");
        final float Co_Pr = Global.getSettings().getFloat("AIRetrofits_Omega_Combat_priority");

        final float Hm_Po = Global.getSettings().getFloat("AIRetrofits_Omega_MissionHijack_Power");
        final float Hm_Pr = Global.getSettings().getFloat("AIRetrofits_Omega_MissionHijack_priority");

        final float nexM_Po = Global.getSettings().getFloat("AIRetrofits_Omega_GroundBattle_Power");
        final float nexM_Pr = Global.getSettings().getFloat("AIRetrofits_Omega_GroundBattle_priority");

        //base game raiding
        crewReplacer_Job tempJob = crewReplacer_Main.getJob("raiding_marines");
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Co_Po;
        tempCrew.crewPriority = Co_Pr;
        tempJob.addCrew(tempCrew);

        //Mission_hijack_marines
        tempJob = crewReplacer_Main.getJob(hijack_marinesJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Hm_Po;
        tempCrew.crewPriority = Hm_Pr;
        tempJob.addCrew(tempCrew);

        //nex job basic:
        tempJob = crewReplacer_Main.getJob(nexMarinesJob);
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = nexM_Po;//Hm_Po
        tempCrew.crewPriority = nexM_Pr;//Hm_Pr
        tempJob.addCrew(tempCrew);

        crewReplacer_CrewSet tempCrewSet = crewReplacer_Main.getCrewSet(normadicSurvival_CrewSet_Marines);
        tempCrewSet.addCrew(tempCrew);

        //nex job for combat
        crewReplacer_Main.getJob(GB_OCR).addNewCrew(robotType,GB_OCR_V,10);
        crewReplacer_Main.getJob(GB_OTC).addNewCrew(robotType,GB_OTC_V,10);
    }
    static private void addOmegaSurveyRobots(){
        final String robotType = "AIretrofit_Omega_SurveyDrone";
        final float Su_Po = Global.getSettings().getFloat("AIRetrofits_Omega_Survey_Power");
        final float Su_Pr = Global.getSettings().getFloat("AIRetrofits_Omega_Survey_priority");

        crewReplacer_Job tempJob = crewReplacer_Main.getJob("survey_crew");
        //base game survey job
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = robotType;
        tempCrew.crewPower = Su_Po;
        tempCrew.crewPriority = Su_Pr;
        tempJob.addCrew(tempCrew);
    }
}
