package data.scripts.AIWorldCode.industries.specal;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;

public class AIRetrofit_shipYard extends AIRetrofit_IndustryBase {
    private final static String C1 = "AIretrofit_SubCommandNode";
    private final static String C2 = "AIretrofit_WorkerDrone";
    //static String C3 = "heavy_machinery";
    private final static String subbmarket = AIRetrofits_Constants_3.ASIC_subbmarket;//"AIRetrofit_ShipyardSubmarket";

    private final static String alphaDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofitShipyard_ADescription");
    private final static String betaDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofitShipyard_BDescription");
    private final static String gammaDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofitShipyard_GDescription");
    private final static float improveValue = AIRetrofits_Constants_3.ASIC_improveValue;//Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    private final static float defaultValue = AIRetrofits_Constants_3.ASIC_defaultValue;//Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");
    private final static String improveDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofitShipyard_IDescription");
    private final static String improvedDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofitShipyard_IedDescription");
    //private final static String extraDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofitShipyard_Description");
    private final static float[] costPerShip = AIRetrofits_Constants_3.ASIC_costPerShip;
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
        String pre = getAlphaCoreString(mode);
        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            text.addPara(AIRetrofits_StringHelper.getSplitString(pre,alphaDescription),pad);
            tooltip.addImageWithText(pad);
            return;
        }
        tooltip.addPara(AIRetrofits_StringHelper.getSplitString(pre,alphaDescription),pad);
    }
    @Override
    protected void	addBetaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        String pre = getBetaCoreString(mode);
        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            text.addPara(AIRetrofits_StringHelper.getSplitString(pre,betaDescription),pad);
            tooltip.addImageWithText(pad);
            return;
        }
        tooltip.addPara(AIRetrofits_StringHelper.getSplitString(pre,betaDescription),pad);

    }
    @Override
    protected void	addGammaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        String pre = getGammaCoreString(mode);
        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            text.addPara(AIRetrofits_StringHelper.getSplitString(pre,gammaDescription),pad);
            tooltip.addImageWithText(pad);
            return;
        }
        tooltip.addPara(AIRetrofits_StringHelper.getSplitString(pre,gammaDescription),pad);

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
        applyStoryText(info, mode);
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
        /*
        String[] exstra = {
                "" + (int)(defaultValue * (1 + improveValue)),
                //"" + costPerShip[0],
                //"" + costPerShip[1],
                "" + (int)costPerShip[2],
                "" + (int)costPerShip[3],
                "" + (int)costPerShip[4],
                "" + (int)costPerShip[5]};
        tooltip.addPara(extraDescription, 0f, highlight, exstra);*/

        AIRetrofit_ShipyardDescription(tooltip,market,true);
    }

    static float shipyard_IValue = AIRetrofits_Constants_3.ASIC_improveValue;//Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    static float shipyardDValue = AIRetrofits_Constants_3.ASIC_defaultValue;//Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");
    static String industry = "AIRetrofit_shipYard";
    public static void AIRetrofit_ShipyardDescription(TooltipMakerAPI tooltip, MarketAPI market) {
        AIRetrofit_ShipyardDescription(tooltip,market,false);
    }
    public static void AIRetrofit_ShipyardDescription(TooltipMakerAPI tooltip, MarketAPI market,boolean forceAvoidCostModifier){

        Color highlight = Misc.getHighlightColor();
        float pad = 5;
        float startingPonits = shipyardDValue;
        final float bounus = 1 + shipyard_IValue;
        if(market.hasIndustry(industry) && market.getIndustry(industry).isImproved()){
            startingPonits *= bounus;
        }
        String[] ex = {
                "" + (int)(startingPonits / AIRetrofits_Constants_3.ASIC_costPerShip[2]),
                "" + (int)(startingPonits / AIRetrofits_Constants_3.ASIC_costPerShip[3]),
                "" + (int)(startingPonits / AIRetrofits_Constants_3.ASIC_costPerShip[4]),
                "" + (int)(startingPonits / AIRetrofits_Constants_3.ASIC_costPerShip[5]),
        };
        tooltip.addPara(AIRetrofits_Constants_3.ASIC_Description_SPM,pad,highlight,ex);
        if(!market.isPlayerOwned() && !forceAvoidCostModifier){

            ex = new String[]{
                    "" + (int) AIRetrofits_Constants_3.ASIC_creditsPerShip[2],
                    "" + (int) AIRetrofits_Constants_3.ASIC_creditsPerShip[3],
                    "" + (int) AIRetrofits_Constants_3.ASIC_creditsPerShip[4],
                    "" + (int) AIRetrofits_Constants_3.ASIC_creditsPerShip[5],
            };
            tooltip.addPara(AIRetrofits_Constants_3.ASIC_Description_CPS,pad,highlight,ex);
        }
    }
}