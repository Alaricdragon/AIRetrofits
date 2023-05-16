package data.scripts.AIWorldCode.industries.basicCommodites;

import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;
import data.scripts.startupData.AIRetrofits_Constants;

public class AIRetrofit_AINodeProductionFacility extends AIRetrofit_IndustryBase {
    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "volatiles";

    private final static String S1 = "AIretrofit_SubCommandNode";
    private final static String S2 = "AIretrofit_CommandRely";
    @Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();
        //demand;
        demand(C1,size - 1);
        demand(C2,size + 1);
        demand(C3,size - 1);
        supply(S1,size - 2);
        supply(S2,size);

        Pair<String, Integer> deficit = getMaxDeficit(C1,C2,C3);
        applyDeficitToProduction(1, deficit,S1,S2);
        if (!isFunctional()) {
            supply.clear();
        }
        if(!this.market.hasSubmarket(AIRetrofits_Constants.Submarket_AINodeProductionFacility) && isFunctional()) {
            this.market.addSubmarket(AIRetrofits_Constants.Submarket_AINodeProductionFacility);
        }
    }
    @Override
    protected boolean canImproveToIncreaseProduction() {
        return true;
    }
    @Override
    public void unapply() {
        super.unapply();
    }
}
