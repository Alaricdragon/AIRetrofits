package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.campaign.impl.items.BlueprintProviderItem;
import com.fs.starfarer.api.campaign.impl.items.ModSpecItemPlugin;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

import java.awt.*;
import java.util.Random;

public class AIRetrofit_salvageRobotManufactory  extends AIRetrofit_PersonalRobotManufactoryBase {
    static String C1 = "metals";
    static String C2 = "rare_metals";
    static String C3 = "heavy_machinery";
    static String S1 = "AIretrofit_WorkerDrone";

    private static float min = 50;
    private static float max = 100;
    @Override
    public void apply() {
        super.apply(true);
        demand(C1,3);
        demand(C2,1);
        demand(C3,1);
        supply(S1,3);
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
    protected Object[] getOutput(){
        return new Object[]{S1,(int)(min + Math.random()*(max - min))};
    }
    @Override
    protected Object[] getAlphaOutput(){
        return super.getAlphaOutput();
    }
    @Override
    protected void exstraBetaDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){

    }
    @Override
    public void applyBetaCoreModifiers(){

    }
}