package data.scripts.AIWorldCode.industries.basicCommodites;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;
import data.scripts.AIWorldCode.submarkets.AIRetrofit_AINodeProduction_Submarket;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;

public class AIRetrofit_AINodeProductionFacility extends AIRetrofit_IndustryBase {

    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "volatiles";

    private final static String S1 = "AIretrofit_SubCommandNode";
    private final static String S2 = "AIretrofit_CommandRely";


    private final static String BaseAICoreDescription = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Base_Description");

    //private final static String AICore_descriptions_B_0 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Base_D0");
    //private final static String AICore_descriptions_B_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Base_D1");
    private final static String AICore_descriptions_B_2 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Base_D2");

    //private final static String AICore_descriptions_G_0 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Gamma_D0");
    //private final static String AICore_descriptions_G_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Gamma_D1");
    private final static String AICore_descriptions_G_2 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Gamma_D2");

    //private final static String AICore_descriptions_Be_0 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Beta_D0");
    //private final static String AICore_descriptions_Be_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Beta_D1");
    private final static String AICore_descriptions_Be_2 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Beta_D2");

    //private final static String AICore_descriptions_A_0 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Alpha_D0");
    //private final static String AICore_descriptions_A_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Alpha_D1");
    private final static String AICore_descriptions_A_2 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Alpha_D2");

    //private final static String AICore_descriptions_O_0 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Omega_D0");
    //private final static String AICore_descriptions_O_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Omega_D1");
    private final static String AICore_descriptions_O_2 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Omega_D2");



    private final static String BaseAICoreDescription_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Base_Description_1");

    private final static String AICore_descriptions_G_0_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Gamma_D0_1");
    private final static String AICore_descriptions_G_1_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Gamma_D1_1");
    private final static String AICore_descriptions_G_2_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Gamma_D2_1");

    private final static String AICore_descriptions_Be_0_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Beta_D0_1");
    private final static String AICore_descriptions_Be_1_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Beta_D1_1");
    private final static String AICore_descriptions_Be_2_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Beta_D2_1");

    private final static String AICore_descriptions_A_0_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Alpha_D0_1");
    private final static String AICore_descriptions_A_1_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Alpha_D1_1");
    private final static String AICore_descriptions_A_2_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Alpha_D2_1");

    private final static String AICore_descriptions_O_0_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Omega_D0_1");
    private final static String AICore_descriptions_O_1_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Omega_D1_1");
    private final static String AICore_descriptions_O_2_1 = Global.getSettings().getString("AIRetrofit_AINodeProductionFacility_Omega_D2_1");
    @Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();
        //demand;
        demand(C1,size - 1);
        demand(C2,size + 1);
        demand(C3,size - 1);
        supply(S1,size - 2);
        supply(S2,size);

        Pair<String, Integer> deficit = getMaxDeficit(C1,C2,C3);
        applyDeficitToProduction(1, deficit,S1,S2);
        if (!isFunctional()) {
            supply.clear();
        }
        if(!this.market.hasSubmarket(AIRetrofits_Constants.Submarket_AINodeProductionFacility) && isFunctional()) {
            this.market.addSubmarket(AIRetrofits_Constants.Submarket_AINodeProductionFacility);
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
        String pre = "Alpha-level AI core currently assigned. ";
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            pre = "Alpha-level AI core. ";
        }
        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            applyCoreTooltip(text,pre+BaseAICoreDescription_1,AICore_descriptions_A_0_1,AICore_descriptions_A_1_1,AICore_descriptions_A_2_1);
            //text.addPara(pre + alphaDescription,pad);
            tooltip.addImageWithText(pad);
            return;
        }
        applyCoreTooltip(tooltip,pre+BaseAICoreDescription_1,AICore_descriptions_A_0_1,AICore_descriptions_A_1_1,AICore_descriptions_A_2_1);
        //tooltip.addPara(pre + alphaDescription,pad);
    }
    @Override
    protected void	addBetaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        String pre = "Beta-level AI core currently assigned. ";
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            pre = "Beta-level AI core. ";
        }
        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            applyCoreTooltip(text,pre+BaseAICoreDescription_1,AICore_descriptions_Be_0_1,AICore_descriptions_Be_1_1,AICore_descriptions_Be_2_1);
            //text.addPara(pre + betaDescription,pad);
            tooltip.addImageWithText(pad);
            return;
        }
        applyCoreTooltip(tooltip,pre+BaseAICoreDescription_1,AICore_descriptions_Be_0_1,AICore_descriptions_Be_1_1,AICore_descriptions_Be_2_1);
        //tooltip.addPara(pre + betaDescription,pad);

    }
    @Override
    protected void	addGammaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        String pre = "Gamma-level AI core currently assigned. ";
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            pre = "Gamma-level AI core. ";
        }
        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            //text.addPara(pre + gammaDescription,pad);
            applyCoreTooltip(text,BaseAICoreDescription_1,AICore_descriptions_G_0_1,AICore_descriptions_G_1_1,AICore_descriptions_G_2_1);

            tooltip.addImageWithText(pad);
            return;
        }
        applyCoreTooltip(tooltip,BaseAICoreDescription_1,AICore_descriptions_G_0_1,AICore_descriptions_G_1_1,AICore_descriptions_G_2_1);
        //tooltip.addPara(gammaDescription,pad);

    }
    public void applyCoreTooltip(TooltipMakerAPI text,String main,String D0,String D1,String D2){
        float pad = 5;
        Color highlight = Misc.getHighlightColor();
        text.addPara(main,pad,highlight,D0,D1,D2);
    }
    @Override
    public void addImproveDesc(TooltipMakerAPI info, ImprovementDescriptionMode mode) {
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();

        //String aStr = "" + (100 * improveValue) + "%";

        if (mode == ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
            //info.addPara(improveDescription, 0f, highlight, aStr);
        } else {
            //info.addPara(improvedDescription, 0f, highlight, aStr);
        }

        info.addSpacer(opad);
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
        tooltip.addSpacer(opad);
        String temp = this.getAICoreId();
        String[] temp2 = this.getDescriptionValues();
        if (temp == null){
            applyCoreTooltip(tooltip,BaseAICoreDescription,temp2[1],temp2[0],AICore_descriptions_B_2);
            return;
        }
        switch (temp){
            case Commodities.ALPHA_CORE:
                applyCoreTooltip(tooltip,BaseAICoreDescription,temp2[1],temp2[0],AICore_descriptions_A_2);
                break;
            case Commodities.BETA_CORE:
                applyCoreTooltip(tooltip,BaseAICoreDescription,temp2[1],temp2[0],AICore_descriptions_Be_2);
                break;
            case Commodities.GAMMA_CORE:
                applyCoreTooltip(tooltip,BaseAICoreDescription,temp2[1],temp2[0],AICore_descriptions_G_2);
                break;
            case Commodities.OMEGA_CORE:
                applyCoreTooltip(tooltip,BaseAICoreDescription,temp2[1],temp2[0],AICore_descriptions_O_2);
                break;
            default:
                applyCoreTooltip(tooltip,BaseAICoreDescription,temp2[1],temp2[0],AICore_descriptions_B_2);
                break;
        }
    }
    @Override
    protected void applyAlphaCoreModifiers() {
    }
    @Override
    protected void applyBetaCoreModifiers(){
    }
    @Override
    protected void applyGammaCoreModifiers(){
    }
    @Override
    protected void applyImproveModifiers() {
    }
    public String[] getDescriptionValues(){
        //2 values are here: number of cores text, and power in cores text.
        String[] out = {"",""};
        float[] temp = AIRetrofit_AINodeProduction_Submarket.getStats(this);
        if(temp[1] != 0) {
            temp[0] = temp[0] / temp[1];
        }else{
            temp[0] = 0;
        }
        for(int a = 0; a < setDataLists.AINodeProductionFacility_powerCoreThresholds.size(); a++){
            if(temp[0] < setDataLists.AINodeProductionFacility_powerCoreThresholds.get(a) || a+1 >= setDataLists.AINodeProductionFacility_powerCores.size()){
                out[0] = setDataLists.AINodeProductionFacility_powerCores.get(a);
                break;
            }
        }
        for(int a = 0; a < setDataLists.AINodeProductionFacility_numCoreThresholds.size(); a++){
            if(temp[1] < setDataLists.AINodeProductionFacility_numCoreThresholds.get(a) || a+1 >= setDataLists.AINodeProductionFacility_numCores.size()){
                out[1] = setDataLists.AINodeProductionFacility_numCores.get(a);
                break;
            }
        }
        return out;
    }
}
