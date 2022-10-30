package data.scripts.AIWorldCode.supplyDemandClasses;

import com.fs.starfarer.api.campaign.econ.Industry;
import data.scripts.supplyDemandLibary.sets.MarketRetrofit_CCSetGenral;

public class AIRetrofit_CCSetMain extends MarketRetrofit_CCSetGenral {
    static String condition = "AIRetrofit_AIPop";
    public AIRetrofit_CCSetMain(String name) {
        super(name);
    }
    @Override
    public boolean active(Industry industry){
        return active && industry.getMarket().hasCondition(condition);
    }
}
