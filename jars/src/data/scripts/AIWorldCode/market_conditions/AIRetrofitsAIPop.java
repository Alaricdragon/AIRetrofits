package data.scripts.AIWorldCode.market_conditions;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.BaseMarketConditionPlugin;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.AIRetrofit_AIRelations;
import data.scripts.AIWorldCode.growth.AIRetrofit_MarketGrowthMods;
import data.scripts.AIWorldCode.growth.AIRetrofits_RemoveUnwantedGrowth;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.ArrayList;

/*
applys things like market condition changes, and supply demand changes.
 */
public class AIRetrofitsAIPop extends BaseMarketConditionPlugin implements MarketImmigrationModifier {
    public static float STABILITY_BONUS = 2;
    String ID;
    private static boolean can = AIRetrofits_Constants_3.Market_EnableMarketFetures;//Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    //private boolean setUpStart = false;
    //private boolean setUpDone = false;
    public void apply(String id) {
        super.apply(id);
        ID = id;
        //AIRetrofit_Log.loging("is it this? is this causeing this? why why whyw whyqhghADGHVCWDHVCWJHVCSDVC SMHDCS",this,true);
        if(can) {
            market.getStability().modifyFlat(id, STABILITY_BONUS, "robots don't rebel... right?");
            market.addTransientImmigrationModifier(this);
            //ChangeMarketConditions(market);
        }else{
            //unapply(id);
        }
    }

    public void unapply(String id) {
        super.unapply(id);
        market.getStability().unmodify(id);
        market.removeTransientImmigrationModifier(this);
    }
    @Override
    public void modifyIncoming(MarketAPI market, PopulationComposition incoming) {
        ArrayList<String> removed = AIRetrofits_RemoveUnwantedGrowth.removeGrowthOther(market,incoming);
        AIRetrofits_RemoveUnwantedGrowth.removeKnownImmigration(market,incoming,removed);
        AIRetrofit_MarketGrowthMods.applyGrowth(incoming,market);
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
    private void ChangeMarketConditions(MarketAPI market){
        if(true){
            return;
        }
        if(false && abort()){
            AIRetrofit_Log.loging("AI-Pop, removing condition by purge",this);
            //market.removeCondition("AIRetrofit_AIPop");
            market.addCondition("AIRetrofit_Purging_AI_World");
            return;
        }
        if (false && market.hasCondition("decivilized")){
            AIRetrofit_Log.loging("AI-Pop, removing condition by deciv",this);
            AIRetrofit_Log.push();
            //market.removeCondition("decivilized");
            //market.removeCondition("AIRetrofit_AIPop");
            if(false && market.hasCondition("pather_cells")){
                AIRetrofit_Log.loging("AI-Pop, adding lucic AI -War",this);
                market.addCondition("AIRetrofits_AILudicWar");
                AIRetrofit_Log.loging("finished",this);
                AIRetrofit_Log.pop();
                return;
            }
            AIRetrofit_Log.loging("AI-Pop, adding broken AI Workers",this);
            market.addCondition("AIRetrofit_Broken_AI_Workers");
            AIRetrofit_Log.loging("finished",this);
            AIRetrofit_Log.pop();
            return;
        }
        if(false && market.hasCondition("pather_cells")){
            //market.removeCondition("pather_cells");
            //once every momth, run an calculation, and destroy the cell if you calculate the right number.
            return;
        }
        if(market.isPlanetConditionMarketOnly()){
            AIRetrofit_Log.loging("AI-Pop, removing condition by misc",this);
            //market.removeCondition("AIRetrofit_AIPop");
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