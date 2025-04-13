package data.scripts.combatabilityPatches.Nexerlin;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofit_PlayerGroundUnitSwaper;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Defender;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.groundBattleMemory.AIRetrofits_GroundBattleTroopOddsMemory;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.interfaces.AIRetrofits_GroundCombatTypeReplacement;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;
import exerelin.campaign.intel.groundbattle.plugins.BaseGroundBattlePlugin;

import java.util.ArrayList;
import java.util.List;

public class AIRetrofits_GroundBattleListiner2 extends BaseGroundBattlePlugin {
    private static final Boolean logs = Global.getSettings().getBoolean("AIRetrofits_Logs_Nexerlin_GroundCombat");
    public AIRetrofits_GroundBattleTroopOddsMemory My_Memory = new AIRetrofits_GroundBattleTroopOddsMemory();
    @Override
    public void onBattleStart(){
        //GroundBattleIntel battle = this.intel;
        //AIRetrofit_Log.loging("IS THIS WORKING",this,logs);
        //AIRetrofit_Log.loging("BS total units, attack, defender: "+battle.getAllUnits().size()+", "+battle.getSide(true).getUnits().size()+", "+battle.getSide(false).getUnits().size(),this,logs);
        //runSwapers2(battle);
    }
    @Override
    public void reportUnitCreated(GroundUnit b) {
        if (b.isPlayer() || (b.getFleet() != null && b.getFleet().isPlayerFleet())){
            if (!Global.getSettings().getBoolean("AIRetrofits_AllowPlayerNexerlineTroops")) return;
            AIRetrofit_Log.loging("Looking into swaping a player unit of definition of "+b.getUnitDefId()+"",new AIRetrofit_Log(),AIRetrofit_PlayerGroundUnitSwaper.logs);
            AIRetrofit_Log.loging("checking ground unit: def, attacker, size, location, fleet "+b.getUnitDefId()+", "+b.isAttacker()+", "+b.getSize()+", "+b.getLocation()+", "+b.getFleet(),this,AIRetrofit_PlayerGroundUnitSwaper.logs);
            AIRetrofit_Log.push();
            AIRetrofit_PlayerGroundUnitSwaper.attemptToSwap(b,this.intel);
            AIRetrofit_Log.pop();
            return;
        }
        if (!Global.getSettings().getBoolean("AIRetrofits_AllowAINexerlineTroops")) return;
        AIRetrofit_Log.loging("checking ground unit: def, attacker, size, location, fleet "+b.getUnitDefId()+", "+b.isAttacker()+", "+b.getSize()+", "+b.getLocation()+", "+b.getFleet(),this,logs);
        changeUnitOverSwapers(b);
        AIRetrofit_Log.loging("BS total units, attack, defender: "+this.intel.getAllUnits().size()+", "+this.intel.getSide(true).getUnits().size()+", "+this.intel.getSide(false).getUnits().size(),this,logs);
    }
    public boolean changeSingleUnit(GroundUnit b,AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker a){
        int atkOrDef = 0;
        if (b.isAttacker()) atkOrDef = 1;
        int POT = getPowerOverflowID(b);
        float size = b.getSize();
        //AIRetrofit_Log.loging("get me dat size???"+size,this,logs);

        if (!b.isAttacker() && POT != -1 && powerOverflow[atkOrDef][POT] >= size) {
            b.removeUnit(false);
            powerOverflow[atkOrDef][POT] -= size;
            AIRetrofit_Log.loging("Attacker? "+b.isAttacker()+" from unit calculator"+a.ID+", unit size removing: "+size+", units of definition: " + b.getUnitDefId() + ". got " + powerOverflow[atkOrDef][POT] + " units left to remove", this, true);
            return false;
        }
        //AIRetrofit_Log.loging("unit type scaning: " + b.getUnitDefId(), this, true);
        float odds = My_Memory.getOdds(this.intel, b, a);
        //AIRetrofit_Log.loging("get odds of this robot:"+odds,this,logs);
        AIRetrofit_Log.loging("Attacker? "+b.isAttacker()+" unit type: "+b.getUnitDefId()+", calculaterID "+a.ID+", odds of robot: "+odds+", unit size: "+size,this,logs);
        if ((a).swap(this.intel, b, odds)) {
            AIRetrofit_Log.loging("UNIT TYPE HAS BEEN ADDED AT BATTLE NAMED: "+this.intel.getMarket().getName(),this,logs);
            if (POT != -1) {
                powerOverflow[atkOrDef][POT] += size*(a.getPowerToSizeRaitio() - 1);
            }
            return true;
        }
        return false;
    }
    public void changeUnitOverSwapers(GroundUnit b){
        AIRetrofit_Log.loging("running changes for unit of definition "+b.getUnitDefId(),this,logs);
        AIRetrofit_Log.push();
        for(AIRetrofits_Robot_Types_calculater_2 d : AIRetrofits_Robot_Types_calculater_2.masterList) {
            if (d instanceof AIRetrofits_GroundCombatTypeReplacement) {
                if (((AIRetrofits_GroundCombatTypeReplacement) d).type().equals(AIRetrofits_Robot_Types_calculater_GroundUnits_Defender.type)){//a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Defender){
                    if (changeSingleUnit(b, (AIRetrofits_Robot_Types_calculater_GroundUnits_Defender) d)) {
                        AIRetrofit_Log.pop();
                        return;
                    }
                } else if (((AIRetrofits_GroundCombatTypeReplacement) d).type().equals(AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker.type)){//a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Defender){
                    if (changeSingleUnit(b, (AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) d)) {
                        AIRetrofit_Log.pop();
                        return;
                    }
                }
            }
        }
        AIRetrofit_Log.pop();
    }
    public int getPowerOverflowID(GroundUnit unit){
        switch (unit.getUnitDefId()){
            case BHev:
                return 0;
            case BMar:
                return 1;
            case BMil:
                return 2;
            case BReb:
                return 3;
        }
        return -1;
    }
    @Override
    public void apply() {
        super.apply();
    }
    float[][] powerOverflow = new float[2][4];
    public static final String
            BMar = "marine",BHev="heavy",BReb="rebel",BMil="militia";

}
