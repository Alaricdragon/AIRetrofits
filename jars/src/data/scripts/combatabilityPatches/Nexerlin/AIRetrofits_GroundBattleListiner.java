package data.scripts.combatabilityPatches.Nexerlin;

import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofit_groundTroopSwaper_Base;
import data.scripts.startupData.AIRetrofits_Constants;
import exerelin.campaign.intel.groundbattle.GroundBattleCampaignListener;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;
import exerelin.campaign.intel.invasion.InvasionIntel;

import java.util.List;

public class AIRetrofits_GroundBattleListiner implements GroundBattleCampaignListener/*, CampaignEventListener */{
    public static final String marrienDefID = "marine";
    public static final String heveyUnitDefId = "heavy";
    @Override
    public void reportBattleStarted(GroundBattleIntel battle) {
        AIRetrofit_groundTroopSwaper_Base.replaceAllForces(battle);
        AIRetrofit_Log.loging("the thing below is da thing",this,true);
        AIRetrofit_Log.loging(battle.getSide(false).getUnits().get(0).getPersonnelMap().toString(),this,true);

    }
    @Override
    public void reportBattleBeforeTurn(GroundBattleIntel battle, int turn) {
        AIRetrofit_groundTroopSwaper_Base.replaceAllForces(battle);
    }

    @Override
    public void reportBattleAfterTurn(GroundBattleIntel battle, int turn) {
        AIRetrofit_groundTroopSwaper_Base.replaceAllForces(battle);
    }

    @Override
    public void reportBattleEnded(GroundBattleIntel battle) {
        AIRetrofit_groundTroopSwaper_Base.replaceAllForces(battle);
    }


    public void swapGroundForces(GroundBattleIntel battle){
        AIRetrofit_Log.loging("AAAAAAAAAAAAAAAAAAAA atempting to swap swap swap swap!!!! AAAAAAAAAAAAAAAAAAAA",this,true);
        List<GroundUnit> c = battle.getSide(true).getUnits();
        for(int a = c.size() - 1; a >= 0; a--){
            GroundUnit b = c.get(a);
            if (unitTypeNotSwaped(b) && shouldSwapUnits(b)){
                swapUnit(b,battle,true);
            }
        }
        if (!battle.getMarket().hasCondition(AIRetrofits_Constants.Market_Condition)){
            //return;
        }
        c = battle.getSide(false).getUnits();
        for(int a = c.size() - 1; a >= 0; a--){
            GroundUnit b = c.get(a);
            if (unitTypeNotSwaped(b)){
                swapUnit(b,battle,false);
            }
        }

    }
    private String determineSwappedUnitDefinition(GroundUnit unit,GroundBattleIntel battle,boolean isAttacker){
        int tier = 0;
        if (isAttacker){

        }else{

        }
        switch (unit.getUnitDefId()){
            case marrienDefID:
                return "CombatRobots";
            case heveyUnitDefId:
                return "CombatRobots_Heavy";
        }
        return null;
    }
    private void swapUnit(GroundUnit unit,GroundBattleIntel battle,boolean isAttacker){
        AIRetrofit_Log.loging("WE BE SWAPING BOIES!!!",this,true);
        unit.getUnitDefId();
        String unit2 = determineSwappedUnitDefinition(unit,battle,isAttacker);
        if (unit2 == null || unit2.equals(null)){
            return;
        }
            unit.getNumUnitEquivalents();
            unit.getDestination();
            unit.getLocation();
            unit.getFaction();
            GroundUnit a = battle.getSide(isAttacker).createUnit(unit2,unit.getFaction(),unit.getSize());
            a.setLocation(unit.getLocation());
            a.setDestination(unit.getDestination());

            unit.removeUnit(false);
    }
    private boolean shouldSwapUnits(GroundUnit unit){
        if (unit.getFleet().isPlayerFleet()) return false;
        return setDataLists.fleetMod(unit.getFleet()) || true;
    }
    private boolean unitTypeNotSwaped(GroundUnit unit){
        AIRetrofit_Log.loging("unitID of a combat unit: "+unit.getUnitDefId(),this,true);
        return unit.getUnitDefId().equals(marrienDefID) || unit.getUnitDefId().equals(heveyUnitDefId);
    }
}
