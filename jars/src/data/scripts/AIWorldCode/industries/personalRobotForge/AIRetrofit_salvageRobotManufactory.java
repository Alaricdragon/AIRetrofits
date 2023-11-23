package data.scripts.AIWorldCode.industries.personalRobotForge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.industries.personalRobotForge.AIRetrofit_PersonalRobotManufactoryBase;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;

public class AIRetrofit_salvageRobotManufactory  extends AIRetrofit_PersonalRobotManufactoryBase implements MarketImmigrationModifier {
    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "heavy_machinery";
    private final static String S1 = "AIretrofit_WorkerDrone";
    private final static String S2 = "AIretrofit_Advanced_WorkerDrone";
    private final static String S3 = "AIretrofit_Omega_WorkerDrone";
    private final static int OS1Min = Global.getSettings().getInt("AIRetrofit_robotManufactury_salvage_OS1Min");//125;
    private final static int OS1Max = Global.getSettings().getInt("AIRetrofit_robotManufactury_salvage_OS1Max");

    private final static int OS2Min = Global.getSettings().getInt("AIRetrofit_robotManufactury_salvage_OS2Min");
    private final static int OS2Max = Global.getSettings().getInt("AIRetrofit_robotManufactury_salvage_OS2Max");

    private final static int OS3Min = Global.getSettings().getInt("AIRetrofit_robotManufactury_salvage_OS3Min");
    private final static int OS3Max = Global.getSettings().getInt("AIRetrofit_robotManufactury_salvage_OS3Max");

    private final static float BetaGrowthMod = Global.getSettings().getFloat("AIRetrofit_robotManufactury_salvage_Mod");//1.1f;

    //private final static String groundDefenceText = Global.getSettings().getString("AIRetrofit_robotManufactury_salvage_exstaText");//"from combat robot factory";
    private final static String BetaText = Global.getSettings().getString("AIRetrofit_robotManufactury_salvage_betaText");//"use produced combat robots to boost ground defences by %s";

    static String m1 = AIRetrofits_Constants.Market_GrowthMod_AIRetrofits_BasicDroneFactory_0;//"AIRetrofits_RobotFactoryGrowthMod";
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
    protected void removeBetaMods(){
        market.getIncoming().getWeight().unmodifyFlat(m1);
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
    protected void exstraBetaDescription(String pre,TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        Color highlight = Misc.getHighlightColor();
        String[] itemsTemp = this.getItems();
        int bonus = 1;
        if (market != null && market.getIndustry(this.getSpec().getId()) != null) {
            bonus *= (int)market.getIndustry(this.getSpec().getId()).getSupply(itemsTemp[3]).getQuantity().getModifiedValue();
        }
        String[] exstra = {"" + (BetaGrowthMod*bonus)};
        tooltip.addPara(pre + BetaText,pad,highlight,exstra);
    }
    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        if (!isFunctional() || getAICoreId() == null || !getAICoreId().equals("beta_core")) {
            return;
        }
        float bonus = BetaGrowthMod;
        if (this.isImproved()){
            //bonus *= 2;
        }
        String[] itemsTemp = this.getItems();
        if (market != null && market.getIndustry(this.getSpec().getId()) != null) {
            bonus *= (int)market.getIndustry(this.getSpec().getId()).getSupply(itemsTemp[3]).getQuantity().getModifiedValue();
        }
        incoming.getWeight().modifyFlat(m1, bonus, getNameForModifier());
        AIRetrofit_Log.loging("trying a log for some reason plz egnore. (modfication name was: )"+m1,this,true);
    }
}