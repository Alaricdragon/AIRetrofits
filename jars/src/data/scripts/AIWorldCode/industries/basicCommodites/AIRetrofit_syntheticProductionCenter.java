package data.scripts.AIWorldCode.industries.basicCommodites;

import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;

public class AIRetrofit_syntheticProductionCenter   extends AIRetrofit_IndustryBase {
    private final static String C1 = "organs";
    private final static String C2 = "organics";
    private final static String C3 = "rare_metals";

    static String S1 = "AIretrofit_humanInterfaceNode";
    @Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();
        //demand;
        demand(C1,size - 3);
        demand(C2,size);
        demand(C3,size - 1);
        supply(S1,size - 2);

        Pair<String, Integer> deficit = getMaxDeficit(C1,C2,C3);
        applyDeficitToProduction(1, deficit,S1);
        if (!isFunctional()) {
            supply.clear();
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
