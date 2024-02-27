package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

public class AIRetrofits_Robot_Types_calculater_GroundUnits_Rebel extends AIRetrofits_Robot_Types_calculater_GroundUnits_Defender{
    public AIRetrofits_Robot_Types_calculater_GroundUnits_Rebel(String ID, float PTS, String[] replaced, String[] created, float[] multi) {
        super(ID, PTS, replaced, created, multi);
    }

    @Override
    public MarketAPI getUnitsMarket(GroundUnit b, GroundBattleIntel battle) {
        return battle.getMarket();
    }
}
