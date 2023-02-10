package data.scripts.AIWorldCode.industries.specal;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;

public class AIRetrofit_shipYard extends AIRetrofit_IndustryBase {
    private final static String C1 = "AIretrofit_SubCommandNode";
    private final static String C2 = "AIretrofit_WorkerDrone";
    //static String C3 = "heavy_machinery";
    private final static String subbmarket = AIRetrofits_Constants.ASIC_subbmarket;//"AIRetrofit_ShipyardSubmarket";

    private final static String alphaDescription = Global.getSettings().getString("AIRetrofitShipyard_ADescription");
    private final static String betaDescription = Global.getSettings().getString("AIRetrofitShipyard_BDescription");
    private final static String gammaDescription = Global.getSettings().getString("AIRetrofitShipyard_GDescription");
    private final static float improveValue = AIRetrofits_Constants.ASIC_improveValue;//Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    private final static float defaultValue = AIRetrofits_Constants.ASIC_defaultValue;//Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");
    private final static String improveDescription = Global.getSettings().getString("AIRetrofitShipyard_IDescription");
    private final static String improvedDescription = Global.getSettings().getString("AIRetrofitShipyard_IedDescription");
    private final static String extraDescription = Global.getSettings().getString("AIRetrofitShipyard_Description");
    private final static float[] costPerShip = AIRetrofits_Constants.ASIC_costPerShip;
    @Override
    public void apply() {
        super.apply(true);
        int size = market.getSize();
        //demand;
        demand(C1,1);
        demand(C2,3);
        //demand(C3,size - 2);
        //supply(S1,size);
        //Pair<String, Integer> deficit = getMaxDeficit(C1,C2,C3);
        //applyDeficitToProduction(1, deficit,S1);
        if(!this.market.hasSubmarket(subbmarket) && isFunctional()) {
            this.market.addSubmarket(subbmarket);
        }
    }
    @Override
    protected boolean canImproveToIncreaseProduction() {
        return true;
    }
    @Override
    public void unapply() {
        super.unapply();
        //this dose not work =(
        /*if(!market.hasIndustry(this.id)){
            market.removeSubmarket(subbmarket);
        }*/
        //this.market.removeSubmarket("AIRetrofit_ShipyardSubmarket");
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
            text.addPara(pre + alphaDescription,pad);
            tooltip.addImageWithText(pad);
            return;
        }
        tooltip.addPara(pre + alphaDescription,pad);
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
            text.addPara(pre + betaDescription,pad);
            tooltip.addImageWithText(pad);
            return;
        }
        tooltip.addPara(pre + betaDescription,pad);

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
            text.addPara(pre + gammaDescription,pad);
            tooltip.addImageWithText(pad);
            return;
        }
        tooltip.addPara(gammaDescription,pad);

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
    @Override
    public void addImproveDesc(TooltipMakerAPI info, ImprovementDescriptionMode mode) {
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();

        String aStr = "" + (100 * improveValue) + "%";

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
        /*
            required inputed data:
            current rate of production.
            rate of production per crew
                -{4,0.25f,1,2,4,8}
                -defalt
                -fighter
                -friget
                -destroyer
                -creuser
                -battleship
         */
        String[] exstra = {
                "" + (int)(defaultValue * (1 + improveValue)),
                //"" + costPerShip[0],
                //"" + costPerShip[1],
                "" + (int)costPerShip[2],
                "" + (int)costPerShip[3],
                "" + (int)costPerShip[4],
                "" + (int)costPerShip[5]};
        tooltip.addPara(extraDescription, 0f, highlight, exstra);
    }
}