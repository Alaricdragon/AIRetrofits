package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

public class AIRetrofit_shipYard extends BaseIndustry implements MarketImmigrationModifier {
    static String C1 = "metals";
    static String C2 = "rare_metals";
    static String C3 = "heavy_machinery";
    static String S1 = "AIretrofit_WorkerDrone";
    @Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();
        //demand;
        demand(C1,size);
        demand(C2,size - 1);
        demand(C3,size - 2);
        supply(S1,size);
        Pair<String, Integer> deficit = getMaxDeficit(C1,C2,C3);
        applyDeficitToProduction(1, deficit,S1);

        this.market.addSubmarket("AIRetrofit_ShipyardSubmarket");
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
        this.market.removeSubmarket("AIRetrofit_ShipyardSubmarket");
    }
    @Override
    public boolean isAvailableToBuild(){
        return AIretrofit_canBuild.isAI(market);
    }

    static String m1 = "AIRetrofits_AdvancedDroneFactory_0";
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        if (!isFunctional()) {
            return;
        }
        float bonus = market.getSize() + 3;
        incoming.getWeight().modifyFlat(m1, bonus, getNameForModifier());
    }
}