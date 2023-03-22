package data.scripts.AIWorldCode.market_conditions;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.util.Pair;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandLists;

public class AIRetrofits_UnbrokenAIWorkers  extends BaseMarketConditionPlugin {
    private float HP = 100;
    private float time = 0;
    private static final int maxChange = 1;
    private static final int minChange = 0;
    static String combat1 = "";
    static String combat2 = "";
    static String SDCName = "AIRetrofit_combatCondition";
    private static final int requiredCombat = 3;
    private static final float combatStability = -2;
    private static final float passiveStability = -1;
    /*
    need to test.
    need to add config.
    need to add bonus to have when you have an AI pop.
     */
    public void apply(String id) {
        super.apply(id);
        if (market.hasCondition("AIRetrofit_AIPop")){
            //whatever i want to do here. dont know yet.
            //i say give the world an free disrupted ground defence or patrol HQ?
            //then again, this is not an easy market condition to get.. maybe give an static increase to ita defencive stat?
            market.getStability().modifyFlat(id,passiveStability,"the robotic constructs here are somewhat disruptive, but mostly keep to themselves");
            return;
        }else{
            crewReplacer_SupplyDemandLists.getRuleSet(SDCName).applyMarket(market, false);
            Pair<String, Integer> deficit = market.getIndustry("population").getMaxDeficit(combat1, combat2);
            if (deficit.two == 0) {
                //advanceCombat();
            }
            stability(id, deficit.two);
        }
        //advanceCombat();
    }

    public void unapply(String id) {
        super.unapply(id);
        market.getStability().unmodify(id);
        crewReplacer_SupplyDemandLists.getRuleSet(SDCName).applyMarket(market,true);
    }
    public void advance(float amount) {
        if (amount <= 0) return;
        //every momth, reduce HP by x, provided the conditions to attack this market condition exsist.
        super.advance(amount);
        time += amount;
    }
    private void advanceCombat(){//convertToMonths
        if(Global.getSector().getClock().convertToMonths(time) >= 1){
            time = Global.getSector().getClock().convertToMonths(time);
            HP -= time * ((Math.random() * (maxChange - minChange + 1)) + minChange);
            time = 0;
            if(HP <= 0){
                market.removeCondition("AIRetrofits_UnbrokenAIWorkers");
            }
        }
    }
    private void stability(String id,float missingCounter){
        missingCounter = Math.min(missingCounter,requiredCombat);
        if(!market.hasCondition("pather_cells")){//10, 5. (10 / (5)) = 2.  //10,9. (10 / (1)) = 10.
            //1 -> a = b. 0 -> b = 0.
            //a / b = 1. //6 / 3 = 2. // 6 / 1 = 6.
            //b / a. //3 / 6 = 0.5. //1 / 6 = 0.1
            float temp = passiveStability;
            market.getStability().modifyFlat(id,temp,"the robotic constructs here are somewhat disruptive.");
            return;
        }
        float temp = combatStability * (missingCounter / requiredCombat);
        market.getStability().modifyFlat(id,temp,"the robotic constructs here are constantly fighting any followers of the ludic path they find ");
        return;
    }

}
