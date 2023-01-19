package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;

import java.awt.*;

public class AIRetrofit_AIRetrofitPurgeOption extends AIRetrofit_IndustryBase {
    //static private int minSize = 3;
    static private String condition = "AIRetrofit_AIPop";
    static float buildSet = 9999;
    private float prugeTime = 0;
    private float purgeProgress = 0;
    private boolean test = true;
    static private boolean active = Global.getSettings().getBoolean("AIRetrofits_SwapRobotAndNormalPopulationBuilding");

    static private float baseCost = Global.getSettings().getFloat("AIRetrofits_PopulationReplacementCenter_baseCost");//1000;
    static private float baseCostExpental = Global.getSettings().getFloat("AIRetrofits_PopulationReplacementCenter_baseCostExpental");//2;

    static private final float baseTime = Global.getSettings().getFloat("AIRetrofits_PopulationReplacementCenter_baseTime");//1;
    static private final float bastTimeExpencal = Global.getSettings().getFloat("AIRetrofits_PopulationReplacementCenter_bastTimeExpencal");//2f;
    private static final float exstraSize = Global.getSettings().getFloat("AIRetrofits_PopulationReplacementCenter_exstraSize");//1;
    //eq = baseTime *= math.power(bastTimeExpencal,marlet.getSize + exstraSize).
    //if time market has exsisted is less them timesForFree, it takes a single day.
    static private final float timesForFree = Global.getSettings().getFloat("AIRetrofits_PopulationReplacementCenter_timesForFree");//90;
    static private final String exstraDescription = Global.getSettings().getString("AIRetrofits_PopulationReplacementCenter_exstraDescription");//"takes a single day to build if built within %s days of founding the market";

    @Override
    public void apply(){
        super.apply(true);
        if(isBuilding()){
            if(getBuildProgress() == 0){
                prugeTime = getUpgradeTime(market.getSize());
                setBuildProgress(buildSet - prugeTime);
            }
        }
    }
    @Override
    public void unapply(){
        //test = true;
        super.unapply();
    }
    @Override
    public void advance(float time){
        if(/*getBuildProgress() <= 1*/true && test){
            prugeTime = getUpgradeTime(market.getSize());
            setBuildProgress(buildSet - prugeTime);
            test = false;
        }
        super.advance(time);
    }
    @Override
    public boolean isAvailableToBuild(){
        setBuildCostOverride((float) (baseCost * Math.pow(market.getSize(),baseCostExpental)));
        return active && AIretrofit_canBuild.hasRobotForge();
    }
    /*
    @Override
    public*/
    @Override
    public void buildingFinished(){
        super.buildingFinished();
        //int size = market.getSize();
        if(market.hasCondition(condition)){
            market.removeCondition(condition);
        }else{
            market.addCondition(condition);
        }
        //market.getPopulation()
        market.setPopulation(new PopulationComposition());
        //MarketAPI.MarketInteractionMode.REMOTE
        market.removeIndustry(this.getId(),MarketAPI.MarketInteractionMode.REMOTE,false);
    }
    @Override
    public float getBuildTime(){
        /*if(prugeTime != 0){return prugeTime;}
        prugeTime = getUpgradeTime(market.getSize() + 1);
        return prugeTime;*/
        prugeTime = getUpgradeTime(market.getSize());
        this.buildTime = prugeTime;
        return getUpgradeTime(market.getSize());
    };
    //2 pow 3 = 8, 16,32,64,128,256,512,1024
    //2 pow size+3 = 64,128,256,512
    //2 pow size+1 = 16,32,64,128,256,
    private float getUpgradeTime(int size){
        if(market.getDaysInExistence() <= timesForFree){
            return 1;
        }
        return (float) (baseTime * Math.pow(bastTimeExpencal,size + exstraSize));
    }

    @Override
    protected void addRightAfterDescriptionSection(TooltipMakerAPI tooltip, Industry.IndustryTooltipMode mode) {
        //tooltip.addPara("current time left: " + (buildSet - getBuildProgress()) + " time threshold to reduce size " + getUpgradeTime(market.getSize()),5);
        String[] exstra = {"" + timesForFree};
        Color highlight = Misc.getHighlightColor();
        tooltip.addPara(exstraDescription,5,highlight,exstra);
        //tooltip.addPara("takes a single day to build if built within " + timesForFree + " days of founding the market",5);
    }

}
