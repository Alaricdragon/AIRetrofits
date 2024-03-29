package data.scripts.AIWorldCode.industries.basicCommodites;

import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;

public class AIRetrofit_roboticMaintenanceFacility   extends AIRetrofit_IndustryBase {
    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "heavy_machinery";

    private final static String S1 = "AIretrofit_maintainsPacts";
    private final static String S2 = "AIretrofit_roboticReplacementParts";
    @Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();
        //demand;
        demand(C1,size + 1);
        demand(C2,size);
        demand(C3,size - 2);
        supply(S1,size);
        supply(S2,size - 3);

        Pair<String, Integer> deficit = getMaxDeficit(C1,C2,C3);
        if(deficit.two >= 1) {
            deficit.two -= 1;
        }
        applyDeficitToProduction(1, deficit,S1);
        deficit = getMaxDeficit(C1,C2,C3);
        if(deficit.two >= 2) {
            deficit.two -= 2;
        }
        applyDeficitToProduction(1, deficit,S2);
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
}
