package data.scripts.combatabilityPatches.Nexerlin;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofit_groundTroopSwaper_Base;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Defender;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.groundBattleMemory.AIRetrofits_GroundBattleTroopOddsMemory;
import exerelin.campaign.intel.groundbattle.GroundBattleCampaignListener;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

import java.util.ArrayList;

public class AIRetrofits_GroundBattleListiner implements GroundBattleCampaignListener/*, CampaignEventListener */{
    public AIRetrofits_GroundBattleTroopOddsMemory My_Memory = new AIRetrofits_GroundBattleTroopOddsMemory();
    @Override
    public void reportBattleStarted(GroundBattleIntel battle) {
        AIRetrofit_Log.loging("the thing below is da thing",this,true);
        AIRetrofit_Log.loging(battle.getSide(false).getUnits().get(0).getPersonnelMap().toString(),this,true);
        runSwapers2(battle);
    }
    @Override
    public void reportBattleBeforeTurn(GroundBattleIntel battle, int turn) {
        runSwapers2(battle);
    }

    @Override
    public void reportBattleAfterTurn(GroundBattleIntel battle, int turn) {
        runSwapers2(battle);
    }

    @Override
    public void reportBattleEnded(GroundBattleIntel battle) {
    }


    public void runSwapers(String[] exsclude,GroundBattleIntel battle){
        for(AIRetrofits_Robot_Types_calculater_2 a : AIRetrofits_Robot_Types_calculater_2.masterList){
            if(a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Defender){
                ArrayList<String> temp = getCanDoUnits(exsclude,((AIRetrofits_Robot_Types_calculater_GroundUnits_Defender) a).replaced);
                for (GroundUnit b : battle.getSide(false).getUnits()){
                    if (!b.isPlayer() && temp.contains(b.getUnitDefId())){
                        ((AIRetrofits_Robot_Types_calculater_GroundUnits_Defender) a).swap(battle,b);
                    }
                }
            }else if(a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker){
                ArrayList<String> temp = getCanDoUnits(exsclude,((AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) a).replaced);
                for (GroundUnit b : battle.getSide(true).getUnits()){
                    if (!b.isPlayer() && temp.contains(b.getUnitDefId())){
                        ((AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) a).swap(battle,b);
                    }
                }
            }
        }
    }
    public ArrayList<String> getCanDoUnits(String[] exclude,String[] available){
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

        for(AIRetrofits_Robot_Types_calculater_2 a : AIRetrofits_Robot_Types_calculater_2.masterList){
            if(a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Defender){
                for (GroundUnit b : battle.getSide(false).getUnits()){
                    if (!b.isPlayer() && !isUnitMarked(b)){
                        float odds = My_Memory.getOdds(battle,b, (AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) a);
                        ((AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) a).swap(battle,b,odds);
                    }
                }
            }else if(a instanceof AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker){
                for (GroundUnit b : battle.getSide(true).getUnits()){
                    if (!b.isPlayer() && !isUnitMarked(b)){
                        float odds = My_Memory.getOdds(battle,b, (AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) a);
                        ((AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker) a).swap(battle,b,odds);
                    }
                }

            }
        }

        for (GroundUnit a : battle.getAllUnits()){
            markUnit(a);
        }
    }

    public boolean isUnitMarked(GroundUnit unit){
        return false;
    }
    public void markUnit(GroundUnit unit){
        if (isUnitMarked(unit)) return;
        //mark unit here
    }




    public static MarketAPI getUnitsMarket(GroundUnit b, GroundBattleIntel battle){
        MarketAPI market;
        if (b.getFleet() == null){
            if (b.isAttacker()){
                return null;
            }else{
                market = battle.getMarket();
            }
        }else{
            market = Global.getSector().getEconomy().getMarket(b.getFleet().getMemory().getString(MemFlags.MEMORY_KEY_SOURCE_MARKET));
            if (market == null){
                return null;
            }
        }
        return market;
    }
}
