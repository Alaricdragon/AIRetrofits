package data.scripts.combatabilityPatches.Nexerlin;

import com.fs.starfarer.api.Global;
import data.scripts.combatabilityPatches.AIRetrofits_PatchBase;
import data.scripts.crewReplacer_Main;
import data.scripts.memory.AIRetrofit_ItemFoundMemory;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_NexerlinOperative;
import data.scripts.startupData.AIRetrofits_Constants_3;
import data.scripts.startupData.AIRetrofits_Startup_CrewReplacer;

public class AIRetrofits_Patches_Nexerlin extends AIRetrofits_PatchBase {
    public static final float PersonWeight_Nex_Operative = Global.getSettings().getFloat("AIRetrofit_CommandNode_PersonalityWeight_Nexerlin_Operative");
    @Override
    public void apply() {
        new AIRetrofit_CommandNodeType_NexerlinOperative("NexerlinOperative",PersonWeight_Nex_Operative,true,true);
        //new AIRetrofit_groundTroopSwaper_Base("CombatRobots",new String[]{AIRetrofit_groundTroopSwaper_Base.unitType_marine});
        //new AIRetrofits_ItemInCargoMemory("$AIRetrofits_T0_Combat_Available",AIRetrofits_Constants_3.Commodity_T0_CombatDrone);
        //new AIRetrofits_ItemInCargoMemory("$AIRetrofits_T1_Combat_Available",AIRetrofits_Constants_3.Commodity_T1_CombatDrone);
        //new AIRetrofits_ItemInCargoMemory("$AIRetrofits_T2_Combat_Available",AIRetrofits_Constants_3.Commodity_T2_CombatDrone);
        new AIRetrofit_ItemFoundMemory("$AIRetrofits_T2_Combat_found", AIRetrofits_Constants_3.Commodity_T2_CombatDrone,AIRetrofits_Startup_CrewReplacer.nexMarinesJob,crewReplacer_Main.getJob(AIRetrofits_Startup_CrewReplacer.nexMarinesJob).getCrew(AIRetrofits_Constants_3.Commodity_T2_CombatDrone));
        new AIRetrofit_ItemFoundMemory("$AIRetrofits_T2_Combat_found2", AIRetrofits_Constants_3.Commodity_T2_CombatDrone,AIRetrofits_Startup_CrewReplacer.nexTankMarinesJob,crewReplacer_Main.getJob(AIRetrofits_Startup_CrewReplacer.nexTankMarinesJob).getCrew(AIRetrofits_Constants_3.Commodity_T2_CombatDrone));
        /*new AIRetrofits_ItemInCargoMemory("",AIRetrofits_Constants_3.Commodity_T2_CombatDrone){
            @Override
            public void checkMemory() {
                boolean output = false;
                try {
                    output = Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(item) != 0;
                }catch (Exception e){
                    //AIRetrofit_Log.loging("failed to get memory"+output,this,true);
                }
                //AIRetrofit_Log.loging("got memory as: "+output,this,true);
                if (output) {
                    Global.getSector().getPlayerMemoryWithoutUpdate().set(key, output);
                }
                //AIRetrofit_Log.loging("memory saved and gotten as: "+ Global.getSector().getPlayerMemoryWithoutUpdate().get(key).toString(),this,true);
                if (Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(this.item)!= 0){
                    crewReplacer_Main.getJob(AIRetrofits_Startup_CrewReplacer.nexMarinesJob).addNewCrew(this.item,AIRetrofits_Startup_CrewReplacer.GB_OCR_BB_V,AIRetrofits_Startup_CrewReplacer.GB_OCR_BB_P);
                    crewReplacer_Main.getJob(AIRetrofits_Startup_CrewReplacer.nexTankMarinesJob).addNewCrew(this.item,AIRetrofits_Startup_CrewReplacer.GB_OCR_BH_V,AIRetrofits_Startup_CrewReplacer.GB_OCR_BH_P);
                }else{
                    crewReplacer_Main.getJob(AIRetrofits_Startup_CrewReplacer.nexMarinesJob).removeCrew(this.item);
                }
                Global.getSector().getPlayerMemoryWithoutUpdate().get(this.key);
            }
        };*/
    }

    @Override
    public void onGameLoad(boolean newGame) {
        //Global.getSector().getListenerManager().addListener(new AIRetrofits_GroundBattleListiner(), true);
        //Global.getSector().getListenerManager().addListener(new AIRetrofits_GroundBattleListiner2(), true);
    }
}
