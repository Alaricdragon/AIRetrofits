package data.scripts.AIWorldCode.industries.personalRobotForge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.ids.Strings;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRetrofit_PersonalRobotManufactoryBase extends AIRetrofit_IndustryBase {
    protected static final String alphaCore = Commodities.ALPHA_CORE;
    protected static final String omegaCore = Commodities.OMEGA_CORE;
    @Override
    public void apply(){
        super.apply(true);
        String[] itemsTemp = this.getItems();
        int[] amountTemp = this.getNumbers();
        //demand;
        demand(itemsTemp[0],amountTemp[0]);
        demand(itemsTemp[1],amountTemp[1]);
        demand(itemsTemp[2],amountTemp[2]);

        if(getAICoreId() != null && getAICoreId().equals(alphaCore)){
            supply.clear();
            supply(itemsTemp[4],amountTemp[4]);
        }else if(getAICoreId() != null && getAICoreId().equals(omegaCore)){
            supply.clear();
            supply(itemsTemp[5],amountTemp[5]);
        }else {
            supply.clear();
            supply(itemsTemp[3], amountTemp[3]);
        }
        Pair<String, Integer> deficit = getMaxDeficit(itemsTemp[0],itemsTemp[1],itemsTemp[2]);
        applyDeficitToProduction(1, deficit,itemsTemp[3]);
        applyDeficitToProduction(1, deficit,itemsTemp[4]);
        applyDeficitToProduction(1, deficit,itemsTemp[5]);
        if (!isFunctional()) {
            supply.clear();
        }
    }
    @Override
    public void unapply(){
        super.unapply();
        removeBetaMods();
    }
    protected String[] getItems(){
        /*
        String C1 = "req item 1";//0
        String C2 = "req item 2";//1
        String C3 = "req item 3";//2
        String S1 = "base out";//3
        String S2 = "alpha out";//4
        String S3 = "omega out";//5*/
        return null;
    }
    protected int[] getNumbers(){
        return null;
    }
    private String item = "";
    private float amount = 0;

    static private final boolean isActive = true;

    static private final float gammaMulti = Global.getSettings().getFloat("AIRetrofit_robotManufactury_gammaMulti");//1.3f;
    static private final float improvedMulti = Global.getSettings().getFloat("AIRetrofit_robotManufactury_improvedMulti");//1.3f;
    static private final float betaMulti = Global.getSettings().getFloat("AIRetrofit_robotManufactury_betaMulti");//0.5f;
    //alpha multi is not necessary.
    //static private final float alphaMulti = 0.75f;

    static private final String gammaDescription = Global.getSettings().getString("AIRetrofit_robotManufactury_gammaDescription");//"improve robot output by %s";
    static private final String betaDescription = Global.getSettings().getString("AIRetrofit_robotManufactury_betaDescription");//"reduce robot output by %s";
    static private final String alphaDescription = Global.getSettings().getString("AIRetrofit_robotManufactury_alphaDescription");//"improve the quality of produced robots, but %s output factory output";
    static private final String alphaDescriptionHighlighted = Global.getSettings().getString("AIRetrofit_robotManufactury_alphaDescriptionHighlighted");//"reduces";
    static final String improvedDescription = Global.getSettings().getString("AIRetrofit_robotManufactury_improvedDescription");//"improve robot output by %s";
    @Override
    public CargoAPI generateCargoForGatheringPoint(Random random) {
        if(!isFunctional()){
            return null;
        }
        if(getAICoreId() != null && getAICoreId().equals(alphaCore)) {
            translateOutput(getAlphaOutput());
        }else if(getAICoreId() != null && getAICoreId().equals(omegaCore)){
            translateOutput(getOmegaOutput());
        }else{
            translateOutput(getOutput());
        }
        List<SalvageEntityGenDataSpec.DropData> dropRandom = new ArrayList<SalvageEntityGenDataSpec.DropData>();
        List<SalvageEntityGenDataSpec.DropData> dropValue = new ArrayList<SalvageEntityGenDataSpec.DropData>();
        CargoAPI out = SalvageEntity.generateSalvage(random, 1f, 1f, 0, 1f, dropValue, dropRandom);
        out.clear();
        out.addCommodity(item,amount * getOutputMulti());
        return out;
    }
    protected Object[] getOutput(){
        return new Object[]{"",0};
    }
    protected Object[] getAlphaOutput(){
        return getOutput();
    }
    protected Object[] getOmegaOutput(){
        return getAlphaOutput();
    }
    protected float getOutputMulti(){
        float multi = 1;
        if(isImproved()){
            multi = getMultiOrAdd(multi,improvedMulti);
        }
        if(getAICoreId() == null){
            return multi;
        }
        switch (getAICoreId()){
            case "beta_core":
                multi = getMultiOrAdd(multi,gammaMulti);
                break;
            case "gamma_core":
                multi = getMultiOrAdd(multi,betaMulti);
                break;
            //case "alpha":
            //    multi *= alphaMulti;
        }
        return multi;
    }
    private float getMultiOrAdd(float a, float amount){
        if(amount >= 1){
            return a + (amount - 1);
        }
        return a *= amount;
        //higher then 1 = a + (amount - 1)
        //lower then 1 = a * amount.
    }
    private void translateOutput(Object[] output){
        item = (String) output[0];
        if (market != null && market.getIndustry(this.getSpec().getId()) != null) {
            amount = (int) (output[1]);
            amount *= market.getIndustry(this.getSpec().getId()).getSupply(item).getQuantity().getModifiedValue();
        }else {
            amount = (int) output[1];
        }
    }

    @Override
    public boolean isAvailableToBuild(){
        return AIretrofit_canBuild.hasRobotForge() && isActive;
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
            /*text.addPara("Reduces upkeep cost by %s. Reduces demand by %s unit. " +
                            "Increases fleet size by %s.", 0f, highlight,
                    "" + (int)((1f - UPKEEP_MULT) * 100f) + "%", "" + DEMAND_REDUCTION,
                    str);*/
            Color highlight = Misc.getHighlightColor();
            String[] exstra = {"" + ((gammaMulti - 1) * 100) + "%"};
            text.addPara(pre + gammaDescription,pad,highlight,exstra);

            tooltip.addImageWithText(pad);
            return;
        }
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {"" + ((gammaMulti - 1) * 100) + "%"};
        tooltip.addPara(pre + gammaDescription,pad,highlight,exstra);
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
            /*text.addPara("Reduces upkeep cost by %s. Reduces demand by %s unit. " +
                            "Increases fleet size by %s.", 0f, highlight,
                    "" + (int)((1f - UPKEEP_MULT) * 100f) + "%", "" + DEMAND_REDUCTION,
                    str);*/
            Color highlight = Misc.getHighlightColor();
            String[] exstra = {"" + ((1 - betaMulti) * 100) + "%"};
            exstraBetaDescription(pre,text,mode);
            text.addPara(betaDescription,pad,highlight,exstra);
            tooltip.addImageWithText(pad);
            return;
        }
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {"" + ((1 - betaMulti) * 100) + "%"};
        exstraBetaDescription(pre,tooltip,mode);
        tooltip.addPara(betaDescription,pad,highlight,exstra);
    }
    protected void exstraBetaDescription(String pre, TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){

    }
    @Override
    protected void	addAlphaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        /*
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();

        String pre = "Alpha-level AI core currently assigned. ";
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            pre = "Alpha-level AI core. ";
        }
        float a = ALPHA_CORE_BONUS;
        //String str = "" + (int)Math.round(a * 100f) + "%";
        String str = Strings.X + (1f + a);*/
        String pre = "Alpha-level AI core currently assigned. ";
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            pre = "Alpha-level AI core. ";
        }
        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            /*text.addPara("Reduces upkeep cost by %s. Reduces demand by %s unit. " +
                            "Increases fleet size by %s.", 0f, highlight,
                    "" + (int)((1f - UPKEEP_MULT) * 100f) + "%", "" + DEMAND_REDUCTION,
                    str);*/
            Color highlight = Misc.getHighlightColor();
            String[] exstra = {alphaDescriptionHighlighted};
            text.addPara(pre + alphaDescription,pad,highlight,exstra);

            tooltip.addImageWithText(pad);
            return;
        }
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {alphaDescriptionHighlighted};
        tooltip.addPara(pre + alphaDescription,pad,highlight,exstra);
    }
    @Override
    public void addImproveDesc(TooltipMakerAPI info, ImprovementDescriptionMode mode) {
        float pad = 5;
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {"" + ((improvedMulti - 1) * 100) + "%"};
        info.addPara(improvedDescription,pad,highlight,exstra);
    }
    @Override
    public void applyGammaCoreModifiers(){
        removeBetaMods();
    }
    @Override
    public void applyBetaCoreModifiers(){

    }
    @Override
    public void applyAlphaCoreModifiers(){
        removeBetaMods();
    }

    protected int getRandomBetween(float min,float max){
        return (int)(min + Math.random()*(max - min));
    }

    protected void removeBetaMods(){

    }
    /*
    protected void addAlphaCoreDescription(TooltipMakerAPI tooltip, AICoreDescriptionMode mode) {
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();

        String pre = "Alpha-level AI core currently assigned. ";
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            pre = "Alpha-level AI core. ";
        }
        float a = ALPHA_CORE_BONUS;
        //String str = "" + (int)Math.round(a * 100f) + "%";
        String str = Strings.X + (1f + a);

        if (mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            CommoditySpecAPI coreSpec = Global.getSettings().getCommoditySpec(aiCoreId);
            TooltipMakerAPI text = tooltip.beginImageWithText(coreSpec.getIconName(), 48);
            text.addPara(pre + "Reduces upkeep cost by %s. Reduces demand by %s unit. " +
                            "Increases fleet size by %s.", 0f, highlight,
                    "" + (int)((1f - UPKEEP_MULT) * 100f) + "%", "" + DEMAND_REDUCTION,
                    str);
            tooltip.addImageWithText(opad);
            return;
        }

        tooltip.addPara(pre + "Reduces upkeep cost by %s. Reduces demand by %s unit. " +
                        "Increases fleet size by %s.", opad, highlight,
                "" + (int)((1f - UPKEEP_MULT) * 100f) + "%", "" + DEMAND_REDUCTION,
                str);

    }*/
}
