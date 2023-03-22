package data.scripts.AIWorldCode.market_conditions;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import data.scripts.AIWorldCode.AIRetrofit_AIRelations;

public class AIRetrofits_PurgingAIWorld  extends BaseMarketConditionPlugin {
    /*
        this market condition represents the purging of undesired population. will need to:
        make the population size reduce slowly, until it reaches size 3 and 25% or less growth, in wish it will return to being an normal market.


     */
    public void apply(String id) {
        market.getStability().modifyFlat(id, -1 * (market.getSize() - 2), "the robots are trying to rebel against the ongoing purge.?");
        if(false){//market) {//if the market growth value <= 0.
            if (market.getSize() <= 3) {
                //unapply(id);
                //market.removeCondition("AIRetrofit_Purging_AI_World");
            }else{
                market.setSize(market.getSize() - 1);
                //set the market growth % to 99%.
                //market.set
            }
        }
        super.apply(id);
        if(abort()){
            //unapply(id);
        }
    }

    public void unapply(String id) {
        market.getStability().unmodify(id);
        super.unapply(id);
    }
    public void advance(float amount) {
        if (amount <= 0) return;
        super.advance(amount);
    }
    private boolean abort(){
        if( AIRetrofit_AIRelations.AIRelation(market.getFactionId()) == 1){
            return true;
        }
        return false;
    }
}
