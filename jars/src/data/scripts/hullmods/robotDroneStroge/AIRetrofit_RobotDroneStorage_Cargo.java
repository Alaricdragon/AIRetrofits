package data.scripts.hullmods.robotDroneStroge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;

public class AIRetrofit_RobotDroneStorage_Cargo extends AIRetrofit_RobotDroneStorage_Base{
    public float reqExtraCargo = 1;
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id/*, MutableCharacterStatsAPI c*/) {
        float a = stats.getFleetMember().getCargoCapacity();
        stats.getMaxCrewMod().modifyFlat(id,a * -1);
    }
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        switch(index) {
        }
        return null;
    }
    @Override
    public boolean isApplicableToShip(ShipAPI ship/*, MutableCharacterStatsAPI wat*/) {
        if (ship == null) return false;

        float a = ship.getFleetMember().getCargoCapacity();
        return super.isApplicableToShip(ship) && a > reqExtraCargo;
    }

    static final String incombatbleReason_1_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotDroneStorage_cargo_NCDescription_0");//"you require at least";
    static final String incombatbleReason_1_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotDroneStorage_cargo_NCDescription_1");//"cargo . you have";
    static final String incombatbleReason_1_2 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotDroneStorage_cargo_NCDescription_2");//"cargo space";
    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        if(ship == null) return "";
        final String[] incompatibleReasons = {
                incombatbleReason_1_0 + reqExtraCargo + incombatbleReason_1_1 + ship.getFleetMember().getCargoCapacity() + incombatbleReason_1_2
        };
        try {
            float a = ship.getFleetMember().getCargoCapacity();
            if(!(a > reqExtraCargo)){
                return incompatibleReasons[0];
            }
        }catch (Exception E){
            AIRetrofit_Log.loging("Error: failed to run getUnapplicableReason. wonder why?",this,true);
        }
        return super.getUnapplicableReason(ship);
    }
    protected String incompatibleHullMods(ShipAPI ship){
        final String[] compatible = {
                //"AIretrofit_AutomatedCrewReplacementDrones",
                /*"AIRetrofit_ShipyardGamma",
                "AIRetrofit_ShipyardBeta",
                "AIRetrofit_ShipyardAlpha",
                "AIRetrofit_ShipyardOmega"*/
        };
        final String[] names = {
                //Global.getSettings().getHullModSpec(compatible[0]).getDisplayName(),
                /*Global.getSettings().getHullModSpec(compatible[1]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[2]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[3]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[4]).getDisplayName(),*/
        };
        for(int a = 0; a < compatible.length; a++){
            if(ship.getVariant().hasHullMod(compatible[a])){
                return names[a];
            }
        }
        return super.incompatibleHullMods(ship);
    }
}
