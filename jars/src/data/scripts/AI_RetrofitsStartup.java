package data.scripts;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import data.scripts.AIWorldCode.market_listiners.AIRetrofit_MakretListener;
import data.scripts.AIWorldCode.market_listiners.AIRetrofit_econUpdateListiner;
import data.scripts.AIWorldCode.supplyDemandClasses.*;
import data.scripts.AIWorldCode.Fleet.BaseCampainPlugin.AIRetrofit_FleetPlugin;
import data.scripts.AIWorldCode.Fleet.listiner.AIRetrofit_FleetListener;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.robot_forge.AIRetrofits_ForgeItem;
import data.scripts.robot_forge.AIRetrofits_ForgeList;
import data.scripts.robot_forge.AIRetrofits_RobotForge;
import data.scripts.robot_forge.AIRetrofits_RobotForge_People;
import data.scripts.supplyDemandLibary.changes.MarketRetrofit_CCSwapDemand;
import data.scripts.supplyDemandLibary.changes.MarketRetrofit_CCSwapSupply;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandChange;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandLists;

public class AI_RetrofitsStartup extends BaseModPlugin {
    @Override
    public void onApplicationLoad() {
        //crew_replacer.addCrewType("AIretrofit_WorkerDrone");
        crew_replacer_start_new();
        robot_forge_set();
        setDataLists.init();
        AISupplyDemandSet();//HERE temporally disabled.
    }
    @Override
    public void onGameLoad(boolean newGame) {
        //something wrong with crewreplacer addCrewType. causes out of bounds
        //crew_replacer_start_outdated();
        super.onGameLoad(newGame);
        AIMarketModSet();
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

    String AICoreJob = "AIRetrofit_OutpostAICore";
    String AIWorkerJob = "AIRetrofit_OutpostWorker";
    String SupplyJob = "AIRetrofit_OutpostSupply";
    String MachineryJob = "AIRetrofit_OutpostMachinery";
    private void crew_replacer_start_new(){
        crewReplacer_Job tempJob = crewReplacer_Main.getJob("survey_crew");
        /*AIRetrofit_Robots tempcrew = new AIRetrofit_Robots();
        tempcrew.name = "AIretrofit_SurveyDrone";
        tempcrew.crewPower = 2.5f;
        tempJob.addCrew(tempcrew);*/
        /*
        tempJob.addNewCrew("AIretrofit_SurveyDrone",Su_Po,Su_Pr);
        //tempJob.addNewCrew("AIretrofit_WorkerDrone",1,10,0,0,true);

        tempJob = crewReplacer_Main.getJob("salvage_crew");//survey_main
        tempJob.addNewCrew("AIretrofit_WorkerDrone",Sa_Po,Sa_Pr);//,1,1,true);

        tempJob = crewReplacer_Main.getJob("raiding_marines");
        tempJob.addNewCrew("AIretrofit_CombatDrone",Co_Po,Co_Pr);
        */
        AIRetrofit_Robots tempCrew = new AIRetrofit_Robots();
        tempCrew.name = "AIretrofit_SurveyDrone";
        tempCrew.crewPower = Su_Po;
        tempCrew.crewPriority = Su_Pr;
        tempJob.addCrew(tempCrew);
        tempJob.addNewCrew("crewname",1,10);

        tempJob = crewReplacer_Main.getJob("salvage_crew");//survey_main
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = "AIretrofit_WorkerDrone";
        tempCrew.crewPower = Sa_Po;
        tempCrew.crewPriority = Sa_Pr;
        tempJob.addCrew(tempCrew);

        tempJob = crewReplacer_Main.getJob("raiding_marines");
        tempCrew = new AIRetrofit_Robots();
        tempCrew.name = "AIretrofit_CombatDrone";
        tempCrew.crewPower = Co_Po;
        tempCrew.crewPriority = Co_Pr;
        tempJob.addCrew(tempCrew);

        tempJob = crewReplacer_Main.getJob(AICoreJob);//addItem AIretrofit_WorkerDrone 1000; addItem supplies 200; addItem heavy_machinery 200; addItem AIretrofit_SubCommandNode 10;
        tempJob.addNewCrew("AIretrofit_SubCommandNode",1,1);
        tempJob = crewReplacer_Main.getJob(AIWorkerJob);
        tempJob.addNewCrew("AIretrofit_WorkerDrone",1,1);//AIretrofit_CombatDrone
        tempJob = crewReplacer_Main.getJob(SupplyJob);
        tempJob.addNewCrew("supplies",1,1);
        tempJob = crewReplacer_Main.getJob(MachineryJob);
        tempJob.addNewCrew("heavy_machinery",1,1);
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

    float ScS = Global.getSettings().getFloat("AIRetrofits_Sc_S");
    float ScB = Global.getSettings().getFloat("AIRetrofits_Sc_B");
    String Sc1N = Global.getSettings().getString("AIRetrofits_Sc_1N");
    float Sc1C = Global.getSettings().getFloat("AIRetrofits_Sc_1C");
    String Sc2N = Global.getSettings().getString("AIRetrofits_Sc_2N");
    float Sc2C = Global.getSettings().getFloat("AIRetrofits_Sc_2C");
    String Sc3N = Global.getSettings().getString("AIRetrofits_Sc_3N");
    float Sc3C = Global.getSettings().getFloat("AIRetrofits_Sc_3C");

    String SaD = Global.getSettings().getString("AIRetrofits_Sa_D");
    String SuD = Global.getSettings().getString("AIRetrofits_Su_D");
    String CoD = Global.getSettings().getString("AIRetrofits_Co_D");
    String ScD = Global.getSettings().getString("AIRetrofits_Sc_D");
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
    private void robotAddReq(float cost, String name, AIRetrofits_ForgeItem thing){
        if(cost != 0 && name.length() != 0){
            thing.addRequiredItem(name,cost);
        }
    }
    private void AIMarketModSet(){
        //new AIRetrofit_FleetListener(false);//like this?
        Global.getSector().addTransientListener(new AIRetrofit_FleetListener(false));
        Global.getSector().registerPlugin(new AIRetrofit_FleetPlugin());
        //HERE! this is disabled until marketRetrofits is online and working =(
        /*
        AIRetrofit_econUpdateListiner a = new AIRetrofit_econUpdateListiner();
        Global.getSector().getEconomy().addUpdateListener(a);
        */
        Global.getSector().addTransientListener(new AIRetrofit_MakretListener(false));
    }
    private void AISupplyDemandSet(){
        AIRetrofit_CCSetMain a = new AIRetrofit_CCSetMain("AIRetrofits_Main");
        MarketRetrofit_CCSwapDemand d = new MarketRetrofit_CCSwapDemand("AIRetrofits_DCrew",0,"crew","AIretrofit_WorkerDrone");
        a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_Dmarines",0,"marines","AIretrofit_CombatDrone");
        a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_DFood",0,"food","AIretrofit_maintainsPacts");
        a.addChange(d);
        //d = new MarketRetrofit_CCSwapDemand("AIRetrofits_Ddomestic_goods",0,"domestic_goods","AIretrofit_CommandRely");
        //a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_Dluxury_goods",0,"luxury_goods","AIretrofit_humanInterfaceNode");
        a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_Ddrugs",0,"drugs","AIretrofit_SurveyDrone");
        a.addChange(d);
        MarketRetrofit_CCSwapSupply e = new MarketRetrofit_CCSwapSupply("AIRetrofits_DCrew",0,"crew","AIretrofit_WorkerDrone");
        a.addChange(e);
        e = new MarketRetrofit_CCSwapSupply("AIRetrofits_Dmarines",0,"marines","AIretrofit_CombatDrone");
        a.addChange(e);
        //a.addChange();


        /*AIRetrofit_CCSetSecondary b = new AIRetrofit_CCSetSecondary("AIRetrofits_Secondary1");
        e = new MarketRetrofit_CCSwapSupply("AIRetrofit_Sdrugs",0,"drugs","AIretrofit_SurveyDrone");
        b.addChange(e);
        e = new MarketRetrofit_CCSwapSupply("AIRetrofit_SCrew",0,"crew","AIretrofit_WorkerDrone");
        b.addChange(e);
        e = new MarketRetrofit_CCSwapSupply("AIRetrofit_SOrganics",0,"Organics","AIretrofit_SubCommandNode");
        b.addChange(e);
        e = new MarketRetrofit_CCSwapSupply("AIRetrofit_SHarvested Organs",0,"Harvested Organs","AIretrofit_roboticReplacementParts");
        b.addChange(e);
        e = new MarketRetrofit_CCSwapSupply("AIRetrofit_S",0,"","");
        b.addChange(e);*/
        //disabled until market retrofits is online.
        /*AIRetrofit_SuplyDemandSet set = new AIRetrofit_SuplyDemandSet("AIRetrofits_AIPop");
        crewReplacer_SupplyDemandChange supply = new crewReplacer_SupplyDemandChange("basicSupply",true);
        supply.add("crew","AIretrofit_WorkerDrone");
        supply.add("marines","AIretrofit_CombatDrone");
        supply.addException("population");
        set.addItem(supply);

        supply = new crewReplacer_SupplyDemandChange("basicCrewDemand",false);
        supply.add("crew","AIretrofit_WorkerDrone");
        supply.add("marines","AIretrofit_CombatDrone");
        set.addItem(supply);
*/
        /*
        supply = new crewReplacer_SupplyDemandChange("basicFoodDemand",false);
        supply.add("Food","AIretrofit_maintainsPacts");
        set.addItem(supply);

        supply = new crewReplacer_SupplyDemandChange("basicDomesticGoodsDemand",false);
        supply.add("Domestic Goods","AIretrofit_CommandRely");
        set.addItem(supply);
*//*
        supply = new crewReplacer_SupplyDemandChange("basicLuxuryGoodsDemand",false);
        supply.add("Luxury Goods","AIretrofit_humanInterfaceNode");
        set.addItem(supply);

        supply = new crewReplacer_SupplyDemandChange("basicDrugsDemand",false);
        supply.add("drugs","AIretrofit_SurveyDrone");
        set.addItem(supply);
*/
        /*
        supply = new crewReplacer_SupplyDemandChange("PopulationSupply",true);
        supply.add("drugs","AIretrofit_SurveyDrone");
        supply.add("crew","AIretrofit_WorkerDrone");
        supply.addRequirement("population");
        set.addItem(supply);
        */
        /*
        supply = new crewReplacer_SupplyDemandChange("PopulationDemand",false);
        supply.add("Organics","AIretrofit_SubCommandNode");
        supply.add("Harvested Organs","AIretrofit_roboticReplacementParts");
        supply.addRequirement("population");
        //supply.add("marines","AIretrofit_CombatDrone");
        set.addItem(supply);

        AIRetrofit_SupplyDemandStarport starportMod = new AIRetrofit_SupplyDemandStarport("StarportOther",false);
        starportMod.addRequirement("spaceport");
        starportMod.addRequirement("megaport");
        set.addItem(starportMod);
        crewReplacer_SupplyDemandLists.addOrMergeRuleSet(set);

        set = new AIRetrofit_SuplyDemandSet("AIRetrofits_AIPopGrowth");
        AIRetrofit_SupplyDemandPopulationGrowth pop_growth = new AIRetrofit_SupplyDemandPopulationGrowth("PopulationGrowth",false);
        pop_growth.addRequirement("population");
        set.addItem(pop_growth);

        //this one might not be needed. might want to mvoe all population growth into "PopulationGrowth"
        AIRetrofit_SupplyDemand_Population pop_supply = new AIRetrofit_SupplyDemand_Population("PopulationOther",false);
        pop_supply.addRequirement("population");
        set.addItem(pop_supply);

        crewReplacer_SupplyDemandLists.addOrMergeRuleSet(set);



        set = new AIRetrofit_SuplyDemandSet("AIRetrofit_combatCondition");
        AIRetrofit_SupplyDemand_Combat combatDemand = new AIRetrofit_SupplyDemand_Combat("combatRequest",false);
        set.addItem(combatDemand);
        crewReplacer_SupplyDemandLists.addOrMergeRuleSet(set);
        //AIRetrofit_SupplyDemand_Population pop_demand_growth = new AIRetrofit_SupplyDemand_Population("PopulationGrowthDemand",false);
        //supply.addRequirement("population");
        //AIRetrofit_SuplyDemandSet.addItem(pop_supply);

         */
    }
    private void descriptions(){
        //Global.getSector().getEconomy().getCommoditySpec("")
    }
}