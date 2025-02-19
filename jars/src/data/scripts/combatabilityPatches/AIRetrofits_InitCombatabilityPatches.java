package data.scripts.combatabilityPatches;

import com.fs.starfarer.api.Global;
import data.scripts.AIRetrofit_Log;
import data.scripts.combatabilityPatches.Nexerlin.AIRetrofits_Patches_Nexerlin;
import data.scripts.combatabilityPatches.starlords.AIRetrofits_Pathches_Starlords;

public class AIRetrofits_InitCombatabilityPatches {
    public static String[] modNames = {
            "nexerelin",
            "starlords"
    };
    public static AIRetrofits_PatchBase[] patches = {
            new AIRetrofits_Patches_Nexerlin(),
            new AIRetrofits_Pathches_Starlords()
    };
    public static void onApplicationLoad() {
        for(int a = 0; a < modNames.length; a++) {
            if (Global.getSettings().getModManager().isModEnabled(modNames[a])) {
                AIRetrofit_Log.loging("AIRetrofits is attempting to add a compatibility patch for the mod '" + modNames[a] + "' ... part 1", data.scripts.combatabilityPatches.AIRetrofits_InitCombatabilityPatches.class, true);
                patches[a].apply();
            }
        }
    }
    public static void onGameLoad(boolean newGame) {
        for(int a = 0; a < modNames.length; a++) {
            if (Global.getSettings().getModManager().isModEnabled(modNames[a])) {
                AIRetrofit_Log.loging("AIRetrofits is attempting to add a compatibility patch for the mod '" + modNames[a] + "' ... part 2", data.scripts.combatabilityPatches.AIRetrofits_InitCombatabilityPatches.class, true);
                patches[a].onGameLoad(newGame);
            }
        }
    }
}
