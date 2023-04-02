package data.scripts.AIWorldCode.supplyDemandClasses;

import com.fs.starfarer.api.campaign.econ.Industry;
import data.scripts.startupData.AIRetrofits_Constants;
import data.scripts.supplyDemandLibary.sets.MarketRetrofit_CCSetGenral;
import data.scripts.supplyDemandLibary.sets.MarketRetrofit_CCSetIndustry;

public class AIRetrofit_CCSetSecondary extends MarketRetrofit_CCSetIndustry {
    static String condition = AIRetrofits_Constants.Market_Condition;//"AIRetrofit_AIPop";
    static String spaceport = "spaceport";

    public AIRetrofit_CCSetSecondary(String name) {
        super(name);
    }
    @Override
    public boolean active(Industry industry){
        return active && industry.getMarket().hasCondition(condition);// && industry.getSpec().getId().equals(spaceport);
    }
}