package data.scripts.AIWorldCode.industries.robotPopulationFactorys;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;

public class AIRetrofit_roboticPopFactoryV0 extends AIRetrofit_IndustryBase implements MarketImmigrationModifier {
    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "heavy_machinery";
    private final static String S1 = "AIretrofit_WorkerDrone";
    private final static int C1Mod = 0;
    private final static int C2Mod = -1;
    private final static int C3Mod = -2;
    private final static int S1Mod = 0;

    final static private float baseGrowth = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T0PowerPerSize");
    final static private float improveValue = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T0ImprovedBonus");//1.3f;
    final static private float alphaValue = Global.getSettings().getFloat("AIRetrofits_MarketGrowth_T0AlphaCoreBonus");//1.3f;

    final static private String improveDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofit_PopFactoryT0_improveDescription");//"";
    final static private String improvedDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofit_PopFactoryT0_improvedDescription");
    final static private String alphaDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofit_PopFactoryT0_alphaDescription");;

    final static private String GrowthText = AIRetrofits_StringGetterProtection.getString("AIRetrofit_PopFactoryT0_extraDescription");
    static String m1 = AIRetrofits_Constants_3.Market_GrowthMod_AIRetrofits_RobotFactoryGrowthMod;
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

    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        if (!isFunctional()) {
            return;
        }
        float growth = getGrowth();
        incoming.getWeight().modifyFlat(m1, growth, getNameForModifier());
    }
    public float getGrowth(){

        float base = market.getSize() * baseGrowth;
        float bonus = 1;
        if (this.isImproved()){
            bonus += improveValue;
        }
        if(getAICoreId() != null && getAICoreId().equals("alpha_core")){
            bonus += alphaValue;
        }
        return base*bonus;
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
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        tooltip.addSpacer(opad);

        String[] exstra = {
                "" + this.getGrowth(),
        };
        tooltip.addPara(GrowthText, 0f, highlight, exstra);
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

}
