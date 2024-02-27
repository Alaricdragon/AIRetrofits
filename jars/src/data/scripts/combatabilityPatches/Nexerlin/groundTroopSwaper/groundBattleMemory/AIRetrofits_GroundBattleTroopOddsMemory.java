package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.groundBattleMemory;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.combatabilityPatches.Nexerlin.AIRetrofits_GroundBattleListiner2;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

import java.util.ArrayList;

public class AIRetrofits_GroundBattleTroopOddsMemory {
    public ArrayList<AIRetrofits_GB_WroldFactionMemory> data = new ArrayList();
    public ArrayList<GroundUnit> unitsChanged = new ArrayList<>();
    public float getOdds(GroundBattleIntel battle,GroundUnit b, AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker type){
        String factionID = b.getFaction().getId();
        MarketAPI market = type.getUnitsMarket(b,battle);
        if (market == null) {
            //AIRetrofit_Log.loging("market null, failing to get data",this,true);
            return 0f;
        }
        return getMemory(type,factionID,market).getValue();
    }

    public AIRetrofits_GB_WroldFactionMemory getMemory(AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker type,String factionID,MarketAPI market){
        for (AIRetrofits_GB_WroldFactionMemory a : data){
            if(a.compare(type,factionID,market)) return a;
        }
        AIRetrofits_GB_WroldFactionMemory temp = new AIRetrofits_GB_WroldFactionMemory(type,factionID,market,type.getOddsOfRobot(market));
        data.add(temp);
        return temp;
    }
    public boolean isUnitsChanged(GroundUnit unit){
        return unitsChanged.contains(unit);
    }
    public void addUnitToChanged(GroundUnit unit){
        unitsChanged.add(unit);
    }
}
