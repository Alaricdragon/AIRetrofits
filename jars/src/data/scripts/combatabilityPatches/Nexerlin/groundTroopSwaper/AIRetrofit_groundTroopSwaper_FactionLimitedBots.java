package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

import java.util.List;

public class AIRetrofit_groundTroopSwaper_FactionLimitedBots extends AIRetrofit_groundTroopSwaper_Base{

    public AIRetrofit_groundTroopSwaper_FactionLimitedBots(String definition, String[] swapedUnitType) {
        super(definition, swapedUnitType);
    }

    public AIRetrofit_groundTroopSwaper_FactionLimitedBots(String definition, String[] swapedUnitType, float platoonsMulti, float swapPercentage) {
        super(definition, swapedUnitType, platoonsMulti, swapPercentage);
    }

    @Override
    public boolean active(GroundUnit unit, GroundBattleIntel battle, boolean isAttacker, int turn) {
        if (!super.active(unit, battle, isAttacker, turn)) return false;
        float chance = 0f;
        if (unit.getFleet() != null){
            MarketAPI market = Global.getSector().getEconomy().getMarket(unit.getFleet().getMemory().getString(MemFlags.MEMORY_KEY_SOURCE_MARKET));
            //i want to see if the fleets homeworld is a place with a factory. if so:
            chance+=getChancePowerOrigin(market);
        }
        if(unit.getFleet() == null && !isAttacker){
            chance+=getChancePowerOrigin(battle.getMarket());
        }
        chance += getChancePower(unit.getFaction());
        return Math.random() < chance;
    }
    public float getChancePower(FactionAPI faction){
    /*    List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
        for(int a = 0; a < markets.size(); a++){
            String factionID2 = markets.get(a).getFactionId();
            if(factionID2.equals(factionID2)){
                power += getMarketPowerGlobal(markets.get(a));
            }
        }
`   */
        return 0f;
    }
    public float getChancePowerOrigin(MarketAPI market){
        /*
        for (String a : this.industry) {
            if (market.hasIndustry(a)){
                return powerPerOutputIfOnHomeWorld * market.getIndustry(a).getSupply(this.outputID).getQuantity().getModifiedValue();
            }
        }
         */
        return 0f;
    }
}
