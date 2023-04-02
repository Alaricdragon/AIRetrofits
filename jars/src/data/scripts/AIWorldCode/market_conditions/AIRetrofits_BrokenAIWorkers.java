package data.scripts.AIWorldCode.market_conditions;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;

public class AIRetrofits_BrokenAIWorkers extends BaseMarketConditionPlugin implements MarketImmigrationModifier {
    private float time = 0;
    private float HP = 100;
    private static int maxChange = 0;
    private static int minChange = 0;
    private static float stability = -1;
    static int minStability = 5;
    static String popGrowthName = "AIRetrofits_BrokenAIWorkers";
    static float growthSpeed = 5;
    /*
    things to do here:
    set all varubles in the config.
    add an changing description to show that the market removing this condition?

     */
    public void apply(String id) {
        if(market.hasCondition("AIRetrofit_AIPop")){
            //give boons to the AI with some penaltys.
            if(market.getSize() >= 6){
                //unapply(id);
            }
        }else{
            market.getStability().modifyFlat(id,stability);
            //the world will slowly remove this condition, and apply some negative stability.
        }
        super.apply(id);
        //advanceCombat();
    }

    public void unapply(String id) {
        super.unapply(id);
        market.removeTransientImmigrationModifier(this);
        market.getStability().unmodifyFlat(id);
        //incoming.getWeight().unmodify(popGrowthName);
    }
    public void advance(float amount) {
        if (amount <= 0) return;
        time += amount;
        super.advance(amount);
    }
    private void advanceCombat(){//convertToMonths
        if(Global.getSector().getClock().convertToMonths(time) >= 1){
            if(!market.hasCondition("AIRetrofit_AIPop") && market.getStability().modified >= minStability) {
                time = Global.getSector().getClock().convertToMonths(time);
                HP -= time * ((Math.random() * (maxChange - minChange + 1)) + minChange);
                time = 0;
                if(HP <= 0){
                    //market.removeCondition("AIRetrofits_BrokenAIWorkers");
                }
                return;
            }
            time = 0;
        }
    }
    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        if(market.hasCondition("AIRetrofit_AIPop")){
            //give boons to the AI with some penaltys.
            incoming.getWeight().modifyFlat(popGrowthName,growthSpeed,"repairing broken AI workers.");
        }else{
            incoming.getWeight().unmodify(popGrowthName);
            //the world will slowly remove this condition, and apply some negative stability.
        }
    }
}