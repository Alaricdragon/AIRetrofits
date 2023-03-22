package data.scripts.AIWorldCode.market_conditions;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.util.Pair;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandLists;

public class AIRetrofits_LudicPathWatchers  extends BaseMarketConditionPlugin {
    static String combat1 = "";
    static String combat2 = "";
    static String SDCName = "AIRetrofit_combatCondition";
    private static int requiredCombat = 3;
    private float time = 0;
    private float HP = 100;
    private static int maxChange = 0;
    private static int minChange = -1;
    float minRelation = 20;
    static String factionID = "luddic_path";
    float AIStability = -5;
    float ludicStability = -2;
    float friendStability = 1;
    /*
    need to test this condition to make sure it works.
    need to swap out all variables with things from the config file.
     */
    public void apply(String id) {
        super.apply(id);
        if(market.hasCondition("AIRetrofit_AIPop") || market.getFaction().getRelationship(factionID) < minRelation) {
            crewReplacer_SupplyDemandLists.getRuleSet(SDCName).applyMarket(market, false);
            Pair<String, Integer> deficit = market.getIndustry("population").getMaxDeficit(combat1, combat2);
            if (deficit.two == 0) {
                //advanceCombat();
            }
            stability(id, deficit.two);
        }else{
            market.getStability().modifyFlat(id,friendStability,"this ludic path watchers are keeping an lookout for evil.");
        }
    }

    public void unapply(String id) {
        market.getStability().unmodify(id);
        super.unapply(id);
        crewReplacer_SupplyDemandLists.getRuleSet(SDCName).applyMarket(market,true);
    }
    public void advance(float amount) {
        if (amount <= 0) return;
        super.advance(amount);
        time += amount;
    }
    private void stability(String id,float missingCounter){
        missingCounter = Math.min(missingCounter,requiredCombat);
        if(market.hasCondition("AIRetrofit_AIPop")){//10, 5. (10 / (5)) = 2.  //10,9. (10 / (1)) = 10.
            //1 -> a = b. 0 -> b = 0.
            //a / b = 1. //6 / 3 = 2. // 6 / 1 = 6.
            //b / a. //3 / 6 = 0.5. //1 / 6 = 0.1
            float temp = AIStability * (missingCounter / requiredCombat);
            market.getStability().modifyFlat(id,temp,"this ludic path watchers are actively fighting against this market.");
            return;
        }
        if (market.getFaction().getRelationship(factionID) < minRelation){
            float temp = ludicStability;
            market.getStability().modifyFlat(id,temp,"this ludic path watchers are making sure this market dose not fall to evil.");
            return;
        }
    }
    private void advanceCombat(){//convertToMonths
        if(Global.getSector().getClock().convertToMonths(time) >= 1){
            time = Global.getSector().getClock().convertToMonths(time);
            HP += time * ((Math.random() * (maxChange - minChange + 1)) + minChange);
            time = 0;
            if(HP <= 0){
                market.removeCondition("AIRetrofits_LudicPathWatchers");
                //remove this mod.

            }
        }
    }
}
