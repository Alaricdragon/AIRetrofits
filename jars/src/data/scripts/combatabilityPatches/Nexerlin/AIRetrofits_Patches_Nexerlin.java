package data.scripts.combatabilityPatches.Nexerlin;

import com.fs.starfarer.api.Global;
import data.scripts.combatabilityPatches.AIRetrofits_PatchBase;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_Admin;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_NexerlinOperative;
import data.scripts.startupData.AIRetrofits_Constants;

public class AIRetrofits_Patches_Nexerlin extends AIRetrofits_PatchBase {
    public static final float PersonWeight_Nex_Operative = Global.getSettings().getFloat("AIRetrofit_CommandNode_PersonalityWeight_Nexerlin_Operative");
    @Override
    public void apply() {
        new AIRetrofit_CommandNodeType_NexerlinOperative("NexerlinOperative",PersonWeight_Nex_Operative,true,true);
    }

    @Override
    public void onGameLoad(boolean newGame) {
        Global.getSector().getListenerManager().addListener(new AIRetrofits_GroundBattleListiner(), true);
    }
}
