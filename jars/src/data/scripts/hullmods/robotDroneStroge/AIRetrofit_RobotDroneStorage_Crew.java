package data.scripts.hullmods.robotDroneStroge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.AIRetrofit_Log;

public class AIRetrofit_RobotDroneStorage_Crew extends AIRetrofit_RobotDroneStorage_Base{
    /**/
    public float reqExtraCrew = 5;
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id/*, MutableCharacterStatsAPI c*/) {
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = stats.getVariant().getHullSpec().getMaxCrew();
        float a = MaxCrew - MinCrew;
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

        float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = ship.getVariant().getHullSpec().getMaxCrew();
        float a = MaxCrew - MinCrew;
        return super.isApplicableToShip(ship) && a > reqExtraCrew;
    }
    static final String incombatbleReason_1_0 = Global.getSettings().getString("AIRetrofits_RobotDroneStorage_crew_NCDescription_0");//"you require at least";
    static final String incombatbleReason_1_1 = Global.getSettings().getString("AIRetrofits_RobotDroneStorage_crew_NCDescription_1");//"extra crew space . you have";
    static final String incombatbleReason_1_2 = Global.getSettings().getString("AIRetrofits_RobotDroneStorage_crew_NCDescription_2");//"extra crew space space";
    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        if(ship == null) return "";
        float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
        float MaxCrew = ship.getVariant().getHullSpec().getMaxCrew();
        float a = MaxCrew - MinCrew;
        final String[] incompatibleReasons = {
                incombatbleReason_1_0 + reqExtraCrew + incombatbleReason_1_1 + a + incombatbleReason_1_2
        };
        try {
            if(!(a > reqExtraCrew)){
                return incompatibleReasons[0];
            }
        }catch (Exception E){
            AIRetrofit_Log.loging("Error: failed to run getUnapplicableReason. wonder why?",this,true);
        }
        return super.getUnapplicableReason(ship);
    }
    protected String incompatibleHullMods(ShipAPI ship){
        final String[] compatible = {
                "AIretrofit_AutomatedCrewReplacementDrones",
                /*"AIRetrofit_ShipyardGamma",
                "AIRetrofit_ShipyardBeta",
                "AIRetrofit_ShipyardAlpha",
                "AIRetrofit_ShipyardOmega"*/
        };
        final String[] names = {
                Global.getSettings().getHullModSpec(compatible[0]).getDisplayName(),
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
