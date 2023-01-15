package data.scripts.AIWorldCode.market_conditions;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIWorldCode.AIRetrofit_AIRelations;
import data.scripts.AIWorldCode.growth.AIRetrofit_MarketGrowthMods;
import data.scripts.AIWorldCode.growth.AIRetrofits_RemoveUnwantedGrowth;

/*
applys things like market condition changes, and supply demand changes.
 */
public class AIRetrofitsAIPop extends BaseMarketConditionPlugin implements MarketImmigrationModifier {
    public static float STABILITY_BONUS = 2;
    String ID;
    private static boolean can = Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    //private boolean setUpStart = false;
    //private boolean setUpDone = false;
    public void apply(String id) {
        super.apply(id);
        ID = id;
        if(can) {
            market.getStability().modifyFlat(id, STABILITY_BONUS, "robots don't rebel... right?");
            market.addTransientImmigrationModifier(this);
            supplyDemandChange(market);
            ChangeMarketConditions(market);
            //changePeople();
        }else{
            unapply(id);
        }
    }

    public void unapply(String id) {
        super.unapply(id);
        removeSupplyDemandChange(market);
        market.getStability().unmodify(id);
        market.removeTransientImmigrationModifier(this);
        //rempveSupplyDemandChange(market);
    }
    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        AIRetrofits_RemoveUnwantedGrowth.removeGrowthOther(market,incoming);
        AIRetrofits_RemoveUnwantedGrowth.removeKnownImmigration(market,incoming);
        AIRetrofit_MarketGrowthMods.applyGrowth(incoming,market);
        //AIRetrofit_RemoveBaseImrgration.apply(market,incoming);
        //incoming.getWeight().modifyMult(getModId() + "_0",0,"test remove");
        //incoming.getWeight().modifyFlatAlways(getModId() + "_1", 10, "test growth");
        //AIRetrofit_MarketGrowthMods.applyGrowth(incoming,market);
        //AIRetrofit_MarketGrowthMods.applyData(market,"");
        //CrewReplacer_Log.loging("HERE!!! AI-Retrofit tried to run growth again...",this.getClass());
        //modifyGrowth(incoming);
    }
    protected void createTooltipAfterDescription(TooltipMakerAPI tooltip, boolean expanded) {
        super.createTooltipAfterDescription(tooltip, expanded);

        tooltip.addPara("%s stability",
                10f, Misc.getHighlightColor(),
                "+" + (int) STABILITY_BONUS);
    }
    @Override
    public void advance(float amount) {
        if (amount <= 0) return;
        super.advance(amount);
    }
    @Override
    public boolean showIcon(){
        return true;
    }
    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded){
        super.createTooltip(tooltip,expanded);
    }
    private void addHazzardPayCost(){
        if(market.isImmigrationIncentivesOn()){
            //market what do i even do here!?!?!
        }else{

        }
    }
    private void supplyDemandChange(MarketAPI market){
        //crewReplacer_SupplyDemandLists.getRuleSet("AIRetrofits_AIPop").applyMarket(market,false);
    }
    public void removeSupplyDemandChange(MarketAPI market){
        //crewReplacer_SupplyDemandLists.getRuleSet("AIRetrofits_AIPop").applyMarket(market,true);
        //crewReplacer_SupplyDemandLists.getRuleSet("AIRetrofits_AIPopGrowth").applyMarket(market,true);

    }
    private void ChangeMarketConditions(MarketAPI market){
        if(false && abort()){
            market.removeCondition("AIRetrofit_AIPop");
            market.addCondition("AIRetrofit_Purging_AI_World");
            return;
        }
        if (market.hasCondition("decivilized")){
            market.removeCondition("decivilized");
            market.removeCondition("AIRetrofit_AIPop");
            if(market.hasCondition("pather_cells")){
                market.addCondition("AIRetrofits_AILudicWar");
                return;
            }
            market.addCondition("AIRetrofit_Broken_AI_Workers");
            return;
        }
        if(false && market.hasCondition("pather_cells")){
            //market.removeCondition("pather_cells");
            //once every momth, run an calculation, and destroy the cell if you calculate the right number.
            return;
        }
        if(market.isPlanetConditionMarketOnly()){
            market.removeCondition("AIRetrofit_AIPop");
            //market.addCondition("AIRetrofits_AILudicWar");
            return;
        }

        /* all market condtion changes will happen here*/
        /* wanted market interaction:
        if deciv subpop and this condtion are on an world, remove deciv subpop and add broken worker robots
        if ludic path cell ACTIVE is on an world, make an angry lucid path cell?
        if an inactive lucic path cell is on this world, add an chane for the AIPop condition to destroy it.
        desing dock. i must edit it.
        */
    }
    private boolean abort(){
        if(AIRetrofit_AIRelations.AIRelation(market.getFactionId()) == -1){
            return true;
        }
        return false;
    }
}