package data.scripts.AIWorldCode.market_conditions;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;

public class AIRetrofits_AILudicWar extends BaseMarketConditionPlugin {
    float BattleState = 0;
    float maxChange = 1;
    float minChange = -1;
    static private int ludicVictory = 100;
    static private int AIVictory = -100;
    private float time = 0;
    static private String ludic = "AIRetrofits_LudicPathWatchers";
    static private String AI = "AIRetrofits_UnbrokenAIWorkers";
    public void apply(String id) {
        super.apply(id);
        //advanceCombat();
    }

    public void unapply(String id) {
        super.unapply(id);
    }
    public void advance(float amount) {
        //randomly change the war state every momth.
        if (amount <= 0) return;
        time += amount;
        //advanceCombat();
        super.advance(amount);
    }
    private void advanceCombat(){//convertToMonths
        if(Global.getSector().getClock().convertToMonths(time) >= 1){
            time = Global.getSector().getClock().convertToMonths(time);
            BattleState += time * ((Math.random() * (maxChange - minChange + 1)) + minChange);
            time = 0;
            if(BattleState >= ludicVictory){
                market.addCondition(ludic);
                //market.removeCondition("AIRetrofits_AILudicWar");
            }else if(BattleState <= AIVictory){
                market.addCondition(AI);
                //market.removeCondition("AIRetrofits_AILudicWar");
            }
        }
    }
}
