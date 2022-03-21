package data.scripts.hullmods;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
public class AIretofit_empty extends BaseHullMod {
    public boolean isApplicableToShip(ShipAPI ship){
        return ship != null;
    }
}
