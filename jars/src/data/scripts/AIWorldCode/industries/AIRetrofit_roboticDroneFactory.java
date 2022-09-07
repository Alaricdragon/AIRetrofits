package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.util.Pair;

public class AIRetrofit_roboticDroneFactory   extends BaseIndustry implements MarketImmigrationModifier {
    static String C1 = "metals";
    static String C2 = "rare_metals";
    static String C3 = "volatiles";
    static String C4 = "hand_weapons";

    static String S1 = "AIretrofit_WorkerDrone";
    static String S2 = "AIretrofit_SurveyDrone";
    static String S3 = "AIretrofit_CombatDrone";
    @Override
    public void apply() {
        super.apply(true);
        int size = 2;//market.getSize();
        //demand;
        demand(C1,size);
        demand(C2,size);
        demand(C3,size - 1);
        demand(C4,size - 1);
        supply(S1,size);
        supply(S2,size);
        supply(S3,size);

        Pair<String, Integer> deficit = getMaxDeficit(C1,C2,C3);
        applyDeficitToProduction(1, deficit,S1,S2,S3);
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

    static String m1 = "AIRetrofits_BasicDroneFactory_0";
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        float bonus = 3;//getPopulationGrowthBonus();

        incoming.getWeight().modifyFlat(m1, bonus, getNameForModifier());
    }
}
