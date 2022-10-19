package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

public class AIRetrofit_AINodeProductionFacility extends BaseIndustry {
    static String C1 = "metals";
    static String C2 = "rare_metals";
    static String C3 = "volatiles";

    static String S1 = "AIretrofit_SubCommandNode";
    static String S2 = "AIretrofit_CommandRely";
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
        //deficit = getMaxDeficit(C3);
    }
    @Override
    protected boolean canImproveToIncreaseProduction() {
        return true;
    }
    @Override
    public void unapply() {
        super.unapply();
    }
    @Override
    public boolean isAvailableToBuild(){
        return AIretrofit_canBuild.isAI(market);
    }
}
