package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.startupData.AIRetrofits_Constants;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

public class AIRetrofit_groundTroopSwaper_AIMarket extends AIRetrofit_groundTroopSwaper_Base{
    public AIRetrofit_groundTroopSwaper_AIMarket(String definition, String[] swapedUnitType) {
        super(definition, swapedUnitType);
    }

    public AIRetrofit_groundTroopSwaper_AIMarket(String definition, String[] swapedUnitType, float platoonsMulti, float swapPercentage) {
        super(definition, swapedUnitType, platoonsMulti, swapPercentage);
    }

    @Override
    public boolean active(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker, int turn) {
        if (unit.getFleet().isPlayerFleet()) return false;
        if (isAttacker && setDataLists.fleetMod(unit.getFleet())){
            return super.active(unit, battle, isAttacker, turn);
        }
        if (!isAttacker && battle.getMarket().hasCondition(AIRetrofits_Constants.Market_Condition)){
            return super.active(unit, battle, isAttacker, turn);
        }
        return false;
    }
}
