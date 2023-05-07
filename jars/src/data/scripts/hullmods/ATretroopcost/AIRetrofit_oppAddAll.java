package data.scripts.hullmods.ATretroopcost;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.startupData.AIRetrofits_Constants;

public class AIRetrofit_oppAddAll extends BaseHullMod {
    public boolean isApplicableToShip(ShipAPI ship){
        boolean temp = ship.getVariant().getSMods().contains(AIRetrofits_Constants.Hullmod_AIRetrofit) || ship.getVariant().getSMods().contains(AIRetrofits_Constants.Hullmod_PatchworkAIRetrofit);
        return ship != null && (ship.getVariant().hasHullMod(AIRetrofits_Constants.Hullmod_AIRetrofit) || ship.getVariant().hasHullMod(AIRetrofits_Constants.Hullmod_PatchworkAIRetrofit)) && !temp;
    }
}