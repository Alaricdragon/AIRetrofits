package data.scripts.combatabilityPatches.Nexerlin;

import com.fs.starfarer.api.Global;
import data.scripts.combatabilityPatches.AIRetrofits_PatchBase;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofit_groundTroopSwaper_Base;
import data.scripts.memory.AIRetrofits_ItemInCargoMemory;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_Admin;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_NexerlinOperative;
import data.scripts.startupData.AIRetrofits_Constants;

public class AIRetrofits_Patches_Nexerlin extends AIRetrofits_PatchBase {
    public static final float PersonWeight_Nex_Operative = Global.getSettings().getFloat("AIRetrofit_CommandNode_PersonalityWeight_Nexerlin_Operative");
    @Override
    public void apply() {
        new AIRetrofit_CommandNodeType_NexerlinOperative("NexerlinOperative",PersonWeight_Nex_Operative,true,true);
        //new AIRetrofit_groundTroopSwaper_Base("CombatRobots",new String[]{AIRetrofit_groundTroopSwaper_Base.unitType_marine});
        new AIRetrofits_ItemInCargoMemory("$AIRetrofits_T0_Combat_Available",AIRetrofits_Constants.Commodity_T0_CombatDrone);
        new AIRetrofits_ItemInCargoMemory("$AIRetrofits_T1_Combat_Available",AIRetrofits_Constants.Commodity_T1_CombatDrone);
        new AIRetrofits_ItemInCargoMemory("$AIRetrofits_T2_Combat_Available",AIRetrofits_Constants.Commodity_T2_CombatDrone);
    }

    @Override
    public void onGameLoad(boolean newGame) {
        Global.getSector().getListenerManager().addListener(new AIRetrofits_GroundBattleListiner(), true);
    }
}
