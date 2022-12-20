package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;
import data.scripts.crew_replacer_startup;

public class AIRetrofit_AIRetrofitPurgeOption extends AIRetrofit_IndustryBase{
    static private int minSize = 3;
    static private String condition = "AIRetrofit_AIPop";
    static float buildSet = 9999;
    private float prugeTime = 0;
    private float purgeProgress = 0;
    @Override
    public void apply(){
        super.apply(true);
        if(isBuilding()){
            if(getBuildProgress() == 0){
                setBuildProgress(buildSet - prugeTime);
            }
        }
    }
    @Override
    public void unapply(){
        super.unapply();
    }
    @Override
    public void advance(float time){
        //if(time == 0){return;}              //60 - 30 = 20.
        if(getBuildProgress() <= 1){
            prugeTime = getUpgradeTime(market.getSize() + 1);
            //crew_replacer_startup.loging("time set: " + (buildSet - prugeTime),this,true);
            setBuildProgress(buildSet - prugeTime);
        }
        //crew_replacer_startup.loging("purge time: " + (prugeTime),this,true);
        //crew_replacer_startup.loging("time remaining: " + getBuildProgress(),this,true);
        /**/if(market.getSize() <= minSize){
            super.advance(time);
            return;
        }
        float purgeProgress = buildSet - getBuildProgress();
        while(market.getSize() > minSize && purgeProgress <= getUpgradeTime(market.getSize())){
            crew_replacer_startup.loging("reducing size at: " + purgeProgress + " req " + getUpgradeTime(market.getSize()),this,true);
            market.setSize(market.getSize()-1);
        }
        /*if(getBuildProgress() >= 0){
            prugeTime -= getBuildProgress();
        }
        if(prugeTime <= 1){
            market.setSize(market.getSize()-1);
            //market.setPopulation();
            prugeTime = getUpgradeTime(market.getSize());

        }/**/
        /*while(market.getSize() > minSize && prugeTime - getBuildProgress() <= getUpgradeTime(market.getSize())){
            market.setSize(market.getSize()-1);
        }*/
        super.advance(time);
    }
    static private float baseCost = 1000;
    static private float baseCostExpental = 2;
    @Override
    public boolean isAvailableToBuild(){
        setBuildCostOverride((float) (baseCost * Math.pow(market.getSize(),baseCostExpental)));
        return AIretrofit_canBuild.hasAbility();
    }
    /*
    @Override
    public*/
    @Override
    public void buildingFinished(){
        //int size = market.getSize();
        if(market.hasCondition(condition)){
            market.removeCondition(condition);
        }else{
            market.addCondition(condition);
        }
        //market.getPopulation()
        market.setPopulation(new PopulationComposition());
        this.unapply();
    }
    @Override
    public float getBuildTime(){
        /*if(prugeTime != 0){return prugeTime;}
        prugeTime = getUpgradeTime(market.getSize() + 1);
        return prugeTime;*/
        prugeTime = getUpgradeTime(market.getSize() + 1);
        this.buildTime = prugeTime;
        return getUpgradeTime(market.getSize() + 1);
    };
    static private float baseTime = 10;
    static private float bastTimeExpencal = 2f;
    private float getUpgradeTime(int size){
        return (float) (baseTime * Math.pow(size,bastTimeExpencal));
    }
    @Override
    protected void addRightAfterDescriptionSection(TooltipMakerAPI tooltip, Industry.IndustryTooltipMode mode) {
        tooltip.addPara("current time left: " + (buildSet - getBuildProgress()) + " time threshold to reduce size " + getUpgradeTime(market.getSize()),5);
    }

}
