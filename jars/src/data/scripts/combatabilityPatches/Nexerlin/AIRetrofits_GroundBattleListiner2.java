package data.scripts.combatabilityPatches.Nexerlin;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Defender;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.groundBattleMemory.AIRetrofits_GroundBattleTroopOddsMemory;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.interfaces.AIRetrofits_GroundCombatTypeReplacement;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;
import exerelin.campaign.intel.groundbattle.plugins.BaseGroundBattlePlugin;

import java.util.ArrayList;

public class AIRetrofits_GroundBattleListiner2 extends BaseGroundBattlePlugin {
    public AIRetrofits_GroundBattleTroopOddsMemory My_Memory = new AIRetrofits_GroundBattleTroopOddsMemory();
    @Override
    public void onBattleStart(){
        GroundBattleIntel battle = this.intel;
        AIRetrofit_Log.loging("IS THIS WORKING",this,true);
        AIRetrofit_Log.loging("BS total units, attack, defender: "+battle.getAllUnits().size()+", "+battle.getSide(true).getUnits().size()+", "+battle.getSide(false).getUnits().size(),this,true);
    }

    @Override
    public void apply() {
        super.apply();
    }

    public ArrayList<String> getCanDoUnits(String[] exclude, String[] available){
        ArrayList<String> out = new ArrayList<String>();
        for(String a : available) {
            boolean can = true;
            for (String b : exclude) {
                if (a.equals(b)) {
                    can = true;
                    break;
                }
            }
            if (can) out.add(a);
        }
        return out;
    }

    public void runSwapers2(GroundBattleIntel battle){

        for(AIRetrofits_Robot_Types_calculater_2 d : AIRetrofits_Robot_Types_calculater_2.masterList) {
            if (d instanceof AIRetrofits_GroundCombatTypeReplacement) {
                if (((AIRetrofits_GroundCombatTypeReplacement) d).type().equals(AIRetrofits_Robot_Types_calculater_GroundUnits_Defender.type)){//a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Defender){
                    AIRetrofits_Robot_Types_calculater_GroundUnits_Defender a = (AIRetrofits_Robot_Types_calculater_GroundUnits_Defender) d;
                    AIRetrofit_Log.loging("one of da things for defender", this, true);
                    for (int c = 0; c < battle.getSide(false).getUnits().size(); c++) {
                        GroundUnit b = battle.getSide(false).getUnits().get(c);
                        if (!b.isPlayer() && !isUnitMarked(b)) {
                            AIRetrofit_Log.loging("unit type scaning: " + b.getUnitDefId(), this, true);
                            float odds = My_Memory.getOdds(battle, b,a);
                            if (( a).swap(battle, b, odds)) {
                                c--;
                            }
                        }
                    }
                } else if (((AIRetrofits_GroundCombatTypeReplacement) d).type().equals(AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker.type)){//a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Defender){
                    AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker a = (AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) d;
                    AIRetrofit_Log.loging("one of da things for attacker", this, true);
                    for (int c = 0; c < battle.getSide(true).getUnits().size(); c++) {
                        GroundUnit b = battle.getSide(true).getUnits().get(c);
                        AIRetrofit_Log.loging("I GOT A UNIT FOR ATTACKERS 000", this, true);
                        if (!b.isPlayer() && !isUnitMarked(b)) {
                            AIRetrofit_Log.loging("I GOT A UNIT FOR ATTACKERS 001", this, true);
                            AIRetrofit_Log.loging("unit type scaning: " + b.getUnitDefId(), this, true);
                            try {
                                AIRetrofit_Log.loging(" from market of " + getUnitsMarket(b, battle).getName(), this, true);
                            }catch (Exception e){
                                AIRetrofit_Log.loging(" null makret????",this,true);
                            }
                            float odds = My_Memory.getOdds(battle, b, a);
                            if (( a).swap(battle, b, odds)) {
                                c--;
                                AIRetrofit_Log.loging("I GOT A UNIT FOR ATTACKERS 002", this, true);
                            }
                        }
                    }

                }
            }
        }

        for (GroundUnit a : battle.getAllUnits()){
            markUnit(a);
        }
    }

    public boolean isUnitMarked(GroundUnit unit){
        return this.My_Memory.isUnitsChanged(unit);
    }
    public void markUnit(GroundUnit unit){
        if (isUnitMarked(unit)) return;
        this.My_Memory.addUnitToChanged(unit);
    }
    public static MarketAPI getUnitsMarket(GroundUnit b, GroundBattleIntel battle){
        MarketAPI market;
        if (b.getFleet() == null){
            if (b.isAttacker()){
                AIRetrofit_Log.loging("getUnitsMarket: "+0,new AIRetrofit_Log(),true);
                return null;
            }else{
                AIRetrofit_Log.loging("getUnitsMarket: "+1,new AIRetrofit_Log(),true);
                return battle.getMarket();
            }
        }else{
            market = Global.getSector().getEconomy().getMarket(b.getFleet().getMemory().getString(MemFlags.MEMORY_KEY_SOURCE_MARKET));
            if (market == null){
                if (b.isAttacker()){
                    AIRetrofit_Log.loging("getUnitsMarket: "+2,new AIRetrofit_Log(),true);
                    return null;
                }else{
                    AIRetrofit_Log.loging("getUnitsMarket: "+3,new AIRetrofit_Log(),true);
                    return battle.getMarket();
                }
            }
            AIRetrofit_Log.loging("getUnitsMarket: "+4,new AIRetrofit_Log(),true);
            return market;
        }
    }
}
