package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

import java.util.ArrayList;

public class AIRetrofit_groundTroopSwaper_Base {
    protected static ArrayList<AIRetrofit_groundTroopSwaper_Base> swappers = new ArrayList<>();
    public final static String unitType_marine = "marine", unitType_heavy = "heavy", unitType_militia = "militia", unitType_rebel = "rebel";
    public static void replaceAllForces(GroundBattleIntel battle){
        for (GroundUnit a : battle.getAllUnits()){
            replaceForces(a,battle,a.isAttacker(),battle.getTurnNum());
        }
    }
    public static void replaceForces(GroundUnit unit,GroundBattleIntel battle,boolean isAttacker,int turn){
        for (AIRetrofit_groundTroopSwaper_Base a : swappers){
            a.createPlatoon(unit, battle, isAttacker, turn);
        }
    }
    public String definition;
    public String[] swapedUnitType;
    public float platoonsMulti = 1;
    public float swapPercentage = 1;
    //'active' on a per unit basis
    public AIRetrofit_groundTroopSwaper_Base(String definition, String[] swapedUnitType){
        this.definition = definition;
        this.swapedUnitType = swapedUnitType;
        swappers.add(this);
    }
    public AIRetrofit_groundTroopSwaper_Base(String definition, String[] swapedUnitType,float platoonsMulti, float swapPercentage){
        this.definition = definition;
        this.swapedUnitType = swapedUnitType;
        this.platoonsMulti = platoonsMulti;
        this.swapPercentage = swapPercentage;
        swappers.add(this);
    }
    public boolean active(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker,int turn){
        for (String a : swapedUnitType){
            if (a.equals(unit.getUnitDefId())) return true;
        }
        return false;
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
    //the act of creating the new platoon itself. removing the old one is also handled here.
    //ISSUE: this requires that the total number of units being converted be inputed, or we risk removing all created units in situations were we suffer from a lot of rounding error.
    //that or its a non issue do tot he fact that im getting number of guys in a squad and not something else.
    public void createPlatoon(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker,int turn){
        if (!active(unit, battle, isAttacker, turn)) return;
        /*unit.getUnitDefId();
        unit.getNumUnitEquivalents();
        unit.getDestination();
        unit.getLocation();
        unit.getFaction();*/
        //unit.data.get();
        GroundUnit a = battle.createUnit(definition,unit.getFaction(),isAttacker,(int) (unit.getSize()*getPlatoonsCreatedMulti(unit, battle,isAttacker,turn)),unit.getFleet(),unit.getIndex());
        //GroundUnit a = battle.getSide(isAttacker).createUnit(getDefinition(unit, battle, isAttacker, turn),unit.getFaction(), (int) (unit.getSize()*getPlatoonsCreatedMulti(unit, battle,isAttacker,turn)));
        a.setLocation(unit.getLocation());
        a.setDestination(unit.getDestination());

        unit.removeUnit(false);
    }
}
