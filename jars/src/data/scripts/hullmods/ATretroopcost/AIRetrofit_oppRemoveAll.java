package data.scripts.hullmods.ATretroopcost;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

public class AIRetrofit_oppRemoveAll  extends BaseHullMod {
    public boolean isApplicableToShip(ShipAPI ship) {
        boolean temp = ship.getVariant().getSMods().contains("AIRetrofit_ShipyardAlpha");
        return ship != null && ship.getVariant().hasHullMod("AIRetrofit_ShipyardAlpha") && !temp;
    }
}