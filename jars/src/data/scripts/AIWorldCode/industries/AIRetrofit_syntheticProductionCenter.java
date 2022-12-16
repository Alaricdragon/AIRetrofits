package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

public class AIRetrofit_syntheticProductionCenter   extends AIRetrofit_IndustryBase {
    static String C1 = "organs";
    static String C2 = "organics";
    static String C3 = "rare_metals";

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
