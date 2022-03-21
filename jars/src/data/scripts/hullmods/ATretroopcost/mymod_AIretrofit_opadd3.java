package data.scripts.hullmods.ATretroopcost;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;

public class mymod_AIretrofit_opadd3 extends BaseHullMod {
    public boolean isApplicableToShip(ShipAPI ship){
        boolean temp = ship.getVariant().getSMods().contains("AIretrofit_airetrofit");
        return ship != null && ship.getVariant().hasHullMod("AIretrofit_airetrofit") && !temp;
    }
}
