package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AIRetrofit_PersonalRobotManufactoryBase extends AIRetrofit_IndustryBase{
    private String item = "";
    private float amount = 0;

    static private final boolean isActive = false;

    static private final float gammaMulti = 1.3f;
    static private final float improvedMulti = 1.3f;
    static private final float betaMulti = 0.5f;
    static private final float alphaMulti = 0.75f;

    static private final String gammaDescription = "improve robot output by %s";
    static private final String betaDescription = "reduce robot output by %s";
    static private final String alphaDescription = "improve the quality of produced robots, but reduced output by %s";

    static final String improvedDescription = "improve robot output by %s";
    @Override
    public CargoAPI generateCargoForGatheringPoint(Random random) {
        /*
        * id
        * alpha_core
            beta_core
            gamma_core*/
        if(getAICoreId() != null && getAICoreId().equals("alpha_core")) {
            translateOutput(getAlphaOutput());
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
    protected float getOutputMulti(){
        float multi = 1;
        switch (getAICoreId()){
            case "beta_core":
                multi += gammaMulti;
                break;
            case "gamma_core":
                multi *= betaMulti;
                break;
            case "alpha":
                multi *= alphaMulti;
        }
        if(isImproved()){
            multi += improvedMulti;
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
        String[] exstra = {"" + ((1 - alphaMulti) * 100) + "%"};
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

    }
    @Override
    public void applyBetaCoreModifiers(){

    }
    @Override
    public void applyAlphaCoreModifiers(){

    }
}
