package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.industries.personalRobotForge.AIRetrofit_PersonalRobotManufactoryBase;

import java.awt.*;
import java.util.Random;

public class AIRetrofit_surveyRobotManufactory extends AIRetrofit_PersonalRobotManufactoryBase {
    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "volatiles";
    private final static String S1 = "AIretrofit_SurveyDrone";
    private final static String S2 = "AIretrofit_Advanced_SurveyDrone";
    private final static String S3 = "AIretrofit_Omega_SurveyDrone";

    private final static int OS1Min = 40;
    private final static int OS1Max = 60;

    private final static int OS2Min = 20;
    private final static int OS2Max = 30;

    private final static int OS3Min = 10;
    private final static int OS3Max = 15;

    private final static float BetaDefenceMulti = 1;

    private final static String groundDefenceText = "from combat robot factory";
    private final static String BetaText = "temp description. does nothing yet. don't install %s";
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
    public void applyBetaCoreModifiers(){
        if (!isFunctional()) {
            return;
        }
        /*
        float bonus = BetaDefenceMulti;
        if (this.isImproved()){
            bonus *= 2;
        }
        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyFlat(this.id,bonus,groundDefenceText);*/
    }
    @Override
    protected void removeBetaMods(){
        //market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).unmodifyFlat(id);
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
        String[] exstra = {"" + ((1 - BetaDefenceMulti) * 100) + "%"};
        tooltip.addPara(BetaText,pad,highlight,exstra);
    }
}