package data.scripts.combatabilityPatches.starlords;

import data.scripts.combatabilityPatches.AIRetrofits_PatchBase;

public class AIRetrofits_Pathches_Starlords extends AIRetrofits_PatchBase {
    public void onGameLoad(boolean newGame){
        new AIRetrofits_StarlordGeneratorListiner();
    }
}
