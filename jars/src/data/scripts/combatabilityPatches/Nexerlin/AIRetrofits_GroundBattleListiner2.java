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
        runSwapers2(battle);
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

    float[][] powerOverflow = new float[2][4];
    public static final String
            BMar = "marine",BHev="heavy",BReb="rebel",BMil="militia";
    public void runSwapersPart(GroundBattleIntel battle,AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker a,boolean isAttacker){
        int atkOrDef = 0;
        if(isAttacker) atkOrDef = 1;
        AIRetrofit_Log.loging("one of da things for attacker? "+isAttacker, this, true);
        for (int c = 0; c < battle.getSide(isAttacker).getUnits().size(); c++) {
            GroundUnit b = battle.getSide(isAttacker).getUnits().get(c);
            int POT = getPowerOverflowID(b);
            boolean done = false;
            while(!done) {
                done = true;
                while (b.isPlayer() || isUnitMarked(b)) {
                    c++;
                    if (c >= battle.getSide(isAttacker).getUnits().size()) return;
                    b = battle.getSide(isAttacker).getUnits().get(c);
                    done = false;
                }
                POT = getPowerOverflowID(b);
                while (POT != -1 && powerOverflow[atkOrDef][POT] >= 1) {
                    b.removeUnit(false);
                    powerOverflow[atkOrDef][POT]--;
                    AIRetrofit_Log.loging("unit type removing: " + b.getUnitDefId()+". got "+powerOverflow[atkOrDef][POT]+" units left to remove", this, true);
                    if (c >= battle.getSide(isAttacker).getUnits().size()) return;
                    b = battle.getSide(isAttacker).getUnits().get(c);
                    POT = getPowerOverflowID(b);
                    done = false;
                }
            }
            AIRetrofit_Log.loging("unit type scaning: " + b.getUnitDefId(), this, true);
            float odds = My_Memory.getOdds(battle, b,a);
            if ((a).swap(battle, b, odds)) {
                if (POT != -1) {
                    powerOverflow[atkOrDef][POT] += a.getPowerToSizeRaitio() - 1;
                }
                c--;
            }
        }
    }
    public void runSwapers2(GroundBattleIntel battle){

        for(AIRetrofits_Robot_Types_calculater_2 d : AIRetrofits_Robot_Types_calculater_2.masterList) {
            if (d instanceof AIRetrofits_GroundCombatTypeReplacement) {
                if (((AIRetrofits_GroundCombatTypeReplacement) d).type().equals(AIRetrofits_Robot_Types_calculater_GroundUnits_Defender.type)){//a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Defender){
                    runSwapersPart(battle, (AIRetrofits_Robot_Types_calculater_GroundUnits_Defender) d,false);
                } else if (((AIRetrofits_GroundCombatTypeReplacement) d).type().equals(AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker.type)){//a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Defender){
                    runSwapersPart(battle, (AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) d,false);
                }
            }
        }

        for (GroundUnit a : battle.getAllUnits()){
            markUnit(a);
        }
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
