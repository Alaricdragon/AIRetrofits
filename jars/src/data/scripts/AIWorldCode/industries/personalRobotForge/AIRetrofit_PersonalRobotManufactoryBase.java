package data.scripts.AIWorldCode.industries.personalRobotForge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRetrofit_PersonalRobotManufactoryBase extends AIRetrofit_IndustryBase {
    private static String alphaCore = "alpha_core";
    private static String omegaCore = "omega_core";
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

    static private final float gammaMulti = 1.3f;
    static private final float improvedMulti = 1.3f;
    static private final float betaMulti = 0.5f;
    //alpha multi is not necessary.
    //static private final float alphaMulti = 0.75f;

    static private final String gammaDescription = "improve robot output by %s";
    static private final String betaDescription = "reduce robot output by %s";
    static private final String alphaDescription = "improve the quality of produced robots, but %s output factory output";
    static private final String alphaDescriptionHighlighted = "reduces";
    static final String improvedDescription = "improve robot output by %s";
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
            multi += improvedMulti;
        }
        if(getAICoreId() == null){
            return multi;
        }
        switch (getAICoreId()){
            case "beta_core":
                multi += gammaMulti;
                break;
            case "gamma_core":
                multi *= betaMulti;
                break;
            //case "alpha":
            //    multi *= alphaMulti;
        }
        return multi;
    }
    private void translateOutput(Object[] output){
        item = (String) output[0];
        amount = (int)output[1];
    }

    @Override
    public boolean isAvailableToBuild(){
        return AIretrofit_canBuild.hasRobotForge() && isActive;
    }
    @Override
    protected void	addGammaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {"" + ((gammaMulti - 1) * 100) + "%"};
        tooltip.addPara(gammaDescription,pad,highlight,exstra);
    }
    @Override
    protected void	addBetaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {"" + ((1 - betaMulti) * 100) + "%"};
        exstraBetaDescription(tooltip,mode);
        tooltip.addPara(betaDescription,pad,highlight,exstra);
    }
    protected void exstraBetaDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){

    }
    @Override
    protected void	addAlphaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        Color highlight = Misc.getHighlightColor();
        String[] exstra = {alphaDescriptionHighlighted};
        tooltip.addPara(alphaDescription,pad,highlight,exstra);
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
}
