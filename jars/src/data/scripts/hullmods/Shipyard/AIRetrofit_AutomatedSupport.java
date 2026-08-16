package data.scripts.hullmods.Shipyard;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.startupData.AIRetrofits_Constants_3;

/// not in use yet. Please dont use. Was going to handle the automated ship tags and what not, but I decided thats slightly pointless.
public class AIRetrofit_AutomatedSupport extends BaseHullMod {
    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        super.applyEffectsAfterShipCreation(ship, id);
        if (ship.getVariant().hasHullMod("AIRetrofit_ShipyardAlpha") || ship.getVariant().hasHullMod("AIRetrofit_ShipyardOmega")){

        }else{

        }
    }
}
