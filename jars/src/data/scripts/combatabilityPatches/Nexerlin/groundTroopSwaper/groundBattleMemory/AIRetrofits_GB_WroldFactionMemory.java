package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.groundBattleMemory;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker;

public class AIRetrofits_GB_WroldFactionMemory {
    public AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker type;
    public String factionID;
    public MarketAPI market;
    public float odds;
    AIRetrofits_GB_WroldFactionMemory(AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker type,String factionID,MarketAPI market,float odds){
        this.type = type;
        this.factionID = factionID;
        this.market = market;
        this.odds = odds;
    }
    public boolean compare(AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker type,String factionID,MarketAPI market){
        return this.market.equals(market) && this.factionID.equals(factionID) && this.type.equals(type);
    }
    public float getValue(){
        return odds;
    }
}
