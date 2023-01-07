package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.industries.personalRobotForge.AIRetrofit_PersonalRobotManufactoryBase;

import java.awt.*;

public class AIRetrofit_salvageRobotManufactory  extends AIRetrofit_PersonalRobotManufactoryBase implements MarketImmigrationModifier {
    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "heavy_machinery";
    private final static String S1 = "AIretrofit_WorkerDrone";
    private final static String S2 = "AIretrofit_Advanced_WorkerDrone";
    private final static String S3 = "AIretrofit_Omega_WorkerDrone";

    private final static int OS1Min = 40;
    private final static int OS1Max = 60;

    private final static int OS2Min = 20;
    private final static int OS2Max = 30;

    private final static int OS3Min = 10;
    private final static int OS3Max = 15;

    private final static float BetaGrowthMod = 12;

    private final static String BetaText = "use produced salvage robots to boost population growth by %s";
    @Override
    protected String[] getItems(){
        return new String[] {C1,C2,C3,S1,S2,S3};
    }
    @Override
    protected int[] getNumbers(){
        return new int[]{3,2,1,3,2,1};
    }
    @Override
    protected Object[] getOutput(){
        return new Object[]{S1,getRandomBetween(OS1Min,OS1Max)};
    }
    @Override
    protected Object[] getAlphaOutput(){
        return new Object[]{S2,getRandomBetween(OS2Min,OS2Max)};
    }
    @Override
    protected Object[] getOmegaOutput(){
        return new Object[]{S3,getRandomBetween(OS3Min,OS3Max)};
    }
    @Override
    protected void removeBetaMods(){
        market.getIncoming().getWeight().unmodifyFlat(m1);
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
    protected void exstraBetaDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {"" + BetaGrowthMod};
        tooltip.addPara(BetaText,pad,highlight,exstra);
    }
    static String m1 = "AIRetrofits_RobotFactoryGrowthMod";
    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        if (!isFunctional() || getAICoreId() == null || !getAICoreId().equals("beta_core")) {
            return;
        }
        float bonus = BetaGrowthMod;
        if (this.isImproved()){
            bonus *= 2;
        }
        incoming.getWeight().modifyFlat(m1, bonus, getNameForModifier());
    }
}