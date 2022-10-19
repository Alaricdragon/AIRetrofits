package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

import java.util.Random;

public class AIRetrofit_advancedSurveyRobotManufactory extends BaseIndustry{
    static String C1 = "metals";
    static String C2 = "rare_metals";
    static String C3 = "hand_weapons";
    static String S1 = "AIretrofit_CombatDrone";
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
    // @Override
    public CargoAPI generateCargoForGatheringPoint(Random random){
        return null;
        /*if (!isFunctional()) {
            return null;
        }
        CargoAPI result = new
        return result;*/
    }
    @Override
    public String getUnavailableReason() {
        if (!super.isAvailableToBuild()) return super.getUnavailableReason();
        return "requires AI market.";
    }
}