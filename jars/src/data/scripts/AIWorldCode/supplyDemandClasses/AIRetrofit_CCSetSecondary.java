package data.scripts.AIWorldCode.supplyDemandClasses;

import com.fs.starfarer.api.campaign.econ.Industry;
import data.scripts.supplyDemandLibary.sets.MarketRetrofit_CCSetGenral;
import data.scripts.supplyDemandLibary.sets.MarketRetrofit_CCSetIndustry;

public class AIRetrofit_CCSetSecondary extends MarketRetrofit_CCSetIndustry {
    static String condition = "AIRetrofit_AIPop";
    public AIRetrofit_CCSetSecondary(String name) {
        super(name);
    }
    @Override
    public boolean active(Industry industry){
        return active && industry.getMarket().hasCondition(condition);
    }
}