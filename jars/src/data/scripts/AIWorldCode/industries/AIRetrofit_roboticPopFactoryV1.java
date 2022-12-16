package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;
import data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost;

import java.awt.*;

public class AIRetrofit_roboticPopFactoryV1 extends AIRetrofit_IndustryBase {
    final static String C1 = "metals";
    final static String C2 = "rare_metals";
    final static String C3 = "heavy_machinery";
    final static String S1 = "AIretrofit_WorkerDrone";
    final static int C1Mod = 2;
    final static int C2Mod = 1;
    final static int C3Mod = 0;
    final static int S1Mod = 2;

    @Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();
        //demand;
        demand(C1, size + C1Mod);
        demand(C2, size + C2Mod);
        demand(C3, size + C3Mod);
        supply(S1, size + S1Mod);
        Pair<String, Integer> deficit = getMaxDeficit(C1, C2, C3);
        if(deficit.one != null){
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


    final static String alphaDescription = Global.getSettings().getString("AIRetrofit_PopFactoryT1_alphaDescription");
    final static float alphaValue = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1AlphaCoreBonus");

    final static float improveValue = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1ImprovedBonus");
    final static String improveDescription =Global.getSettings().getString("AIRetrofit_PopFactoryT1_improveDescription");
    final static String improvedDescription =Global.getSettings().getString("AIRetrofit_PopFactoryT1_improvedDescription");

    final static String extraDescription =Global.getSettings().getString("AIRetrofit_PopFactoryT1_extraDescription");
    @Override
    protected void	addAlphaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        //tooltip.addPara(alphaDescription,0f);
        Color highlight = Misc.getHighlightColor();
        String aStr = "" + (int)(100*(alphaValue)) + "%";
        tooltip.addPara(alphaDescription, 0f, highlight, aStr);
    }
    @Override
    public void addImproveDesc(TooltipMakerAPI info, ImprovementDescriptionMode mode) {
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();

        String aStr = "" + (int)(100*(improveValue)) + "%";

        if (mode == ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
            info.addPara(improveDescription, 0f, highlight, aStr);
        } else {
            info.addPara(improvedDescription, 0f, highlight, aStr);
        }

        info.addSpacer(opad);
        //super.addImproveDesc(info, mode);
        float initPad = 0f;
        if (mode != ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
            info.addPara("Each improvement made at a colony doubles the number of " +
                            "" + Misc.STORY + " points required to make an additional improvement.", initPad,
                    Misc.getStoryOptionColor(), Misc.STORY + " points");
        }
    }

    @Override
    protected void	addPostDescriptionSection(TooltipMakerAPI tooltip, Industry.IndustryTooltipMode mode){
        //mode.
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        tooltip.addSpacer(opad);

        String[] exstra = {
                "" + AIRetorift_GetMarketBoost.getMarketPowerGlobal(market),
        };
        tooltip.addPara(extraDescription, 0f, highlight, exstra);
    }


}