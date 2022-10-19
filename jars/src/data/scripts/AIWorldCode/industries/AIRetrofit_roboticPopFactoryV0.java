package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

public class AIRetrofit_roboticPopFactoryV0 extends BaseIndustry implements MarketImmigrationModifier {
    final static String C1 = "metals";
    final static String C2 = "rare_metals";
    final static String C3 = "heavy_machinery";
    final static String S1 = "AIretrofit_WorkerDrone";
    final static int C1Mod = 0;
    final static int C2Mod = -1;
    final static int C3Mod = -2;
    final static int S1Mod = 0;
    @Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();
        //demand;
        demand(C1,size + C1Mod);
        demand(C2,size + C2Mod);
        demand(C3,size + C3Mod);
        supply(S1,size + S1Mod);
        Pair<String, Integer> deficit = getMaxDeficit(C1,C2,C3);
        if(deficit.one != null) {
            float ratio = 0;
            float S1ModT = C1Mod;
            if (isImproved()) {
                S1ModT += 1;
            }
            switch (deficit.one) {
                case C1:
                    ratio = (float) (size + S1ModT) / (size + C1Mod);//3 / 3 = 1
                    break;
                case C2:
                    ratio = (float) (size + S1ModT) / (size + C2Mod);//3 / 2 = 1.5
                    break;
                case C3:
                    ratio = (float) (size + S1ModT) / (size + C3Mod);//3 / 1 = 3
                    break;
            }
            deficit.two = (int) (deficit.two * ratio);
        }
        applyDeficitToProduction(1, deficit, S1);
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
    @Override
    public boolean isAvailableToBuild(){
        return AIretrofit_canBuild.isAI(market);
    }

    static String m1 = "AIRetrofits_AdvancedDroneFactory_0";
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        if (!isFunctional()) {
            return;
        }
        float bonus = 0;
        bonus += market.getSize() * 3;
        if (this.isImproved()){
            bonus *= 2;
        }
        incoming.getWeight().modifyFlat(m1, bonus, getNameForModifier());
    }
}
