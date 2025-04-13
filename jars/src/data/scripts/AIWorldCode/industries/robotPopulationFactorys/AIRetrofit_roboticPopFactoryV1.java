package data.scripts.AIWorldCode.industries.robotPopulationFactorys;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.AIWorldCode.growth.AIRetorift_GetMarketBoost;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;

public class AIRetrofit_roboticPopFactoryV1 extends AIRetrofit_roboticPopFactoryV0 {
    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "heavy_machinery";
    private final static String S1 = "AIretrofit_WorkerDrone";
    private final static int C1Mod = 2;
    private final static int C2Mod = 1;
    private final static int C3Mod = 0;
    private final static int S1Mod = 2;

    private final static String alphaDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofit_PopFactoryT1_alphaDescription");
    private final static float alphaValue = AIRetrofits_Constants_3.Market_Growth_T1AplhaBonus;

    private final static float improveValue = AIRetrofits_Constants_3.Market_Growth_T1ImprovedBonus;//Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T1ImprovedBonus");
    private final static String improveDescription =AIRetrofits_StringGetterProtection.getString("AIRetrofit_PopFactoryT1_improveDescription");
    private final static String improvedDescription =AIRetrofits_StringGetterProtection.getString("AIRetrofit_PopFactoryT1_improvedDescription");

    private final static String extraDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofit_PopFactoryT1_extraDescription");

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

    @Override
    protected void	addAlphaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        String pre = getAlphaCoreString(mode);
        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            Color highlight = Misc.getHighlightColor();
            String aStr = "" + (int)(100*(alphaValue)) + "%";
            text.addPara(AIRetrofits_StringHelper.getSplitString(pre,alphaDescription), 0f, highlight, aStr);
            tooltip.addImageWithText(pad);
            return;
        }
        Color highlight = Misc.getHighlightColor();
        String aStr = "" + (int)(100*(alphaValue)) + "%";
        tooltip.addPara(AIRetrofits_StringHelper.getSplitString(pre,alphaDescription), 0f, highlight, aStr);
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

        applyStoryText(info, mode);
    }

    @Override
    protected void	addPostDescriptionSection(TooltipMakerAPI tooltip, Industry.IndustryTooltipMode mode){
        //mode.
        super.addPostDescriptionSection(tooltip,mode);
        float opad = 10f;
        tooltip.addSpacer(opad);
        Color highlight = Misc.getHighlightColor();
        tooltip.addSpacer(opad);

        String[] exstra = {
                "" + AIRetorift_GetMarketBoost.getMarketPowerLocal(market),
        };
        tooltip.addPara(extraDescription, 0f, highlight, exstra);
    }


}