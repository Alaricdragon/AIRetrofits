package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.AIRetrofits_GroundBattleListiner;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.interfaces.AIRetrofits_GroundCombatTypeReplacement;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

import java.util.ArrayList;

public class AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker extends AIRetrofits_Robot_Types_calculater_2 implements AIRetrofits_GroundCombatTypeReplacement {
    public AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(String ID, String[] replaced, String[] created) {
        super(ID);
        this.replaced = replaced;
        this.created = created;
    }
    public String[] replaced,created;
    @Override
    public String[] unitTypeReplaced() {
        return replaced;
    }

    @Override
    public String[] unitTypeCreated() {
        return created;
    }
    public void swap(GroundBattleIntel battle, GroundUnit unit){
        MarketAPI market = AIRetrofits_GroundBattleListiner.getUnitsMarket(unit,battle);
        if (market == null)return;
        this.swap(battle,unit,getOddsOfRobot(market));
    }
    public void swap(GroundBattleIntel battle, GroundUnit unit,float odds){
        String newDefinition = getNewDef(unit.getUnitDefId());
        if (newDefinition == null) return;
        if (Math.random() < odds){
            FactionAPI faction = unit.getFaction();
            /*unit.getFleet();
            unit.getLocation();
            unit.getDestination();*/
            battle.getSide(unit.isAttacker()).createUnit(newDefinition,faction,1);

        }
        /*put the rest of the 'replacement' code here.*/
    }
    public String getNewDef(String oldDef){
        for (int a = 0; a < replaced.length; a++){
            if (replaced[a].equals(oldDef)){
                return created[a];
            }
        }
        return null;
    }
}
