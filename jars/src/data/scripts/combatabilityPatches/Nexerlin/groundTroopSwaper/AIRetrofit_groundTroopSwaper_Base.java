package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

public class AIRetrofit_groundTroopSwaper_Base {
    public String definition = "";
    public String swapedUnitType = "";
    public float platoonsMulti = 1;
    public float swapPercentage = 1;
    //'active' on a per unit basis
    public boolean active(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker,int turn){
        return true;
    }
    //the percentage of a battle that is swaped.
    public float getSwapPercentage(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker,int turn){
        return swapPercentage;
    }
    //the number of Platoons created per platoon converted.
    public float getPlatoonsCreatedMulti(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker,int turn){
        return platoonsMulti;
    }
    //the unit type that is created by this method
    public String getDefinition(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker,int turn){
        return definition;
    }
    //the unit type that is removed by this methood.
    public String getswapedUnitType(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker,int turn){
        return swapedUnitType;
    }
    //the act of creating the new platoon itself. removing the old one is also handled here.
    //ISSUE: this requires that the total number of units being converted be inputed, or we risk removing all created units in situations were we suffer from a lot of rounding error.
    //that or its a non issue do tot he fact that im getting number of guys in a squad and not something else.
    public void createPlatoon(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker,int turn){
        /*unit.getUnitDefId();
        unit.getNumUnitEquivalents();
        unit.getDestination();
        unit.getLocation();
        unit.getFaction();*/
        //unit.data.get();
        GroundUnit a = battle.getSide(isAttacker).createUnit(definition,unit.getFaction(), (int) (unit.getSize()*getPlatoonsCreatedMulti(unit, battle,isAttacker,turn)));
        a.setLocation(unit.getLocation());
        a.setDestination(unit.getDestination());

        unit.removeUnit(false);
    }
}
