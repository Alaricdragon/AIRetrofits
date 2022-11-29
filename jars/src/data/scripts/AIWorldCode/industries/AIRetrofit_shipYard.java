package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

public class AIRetrofit_shipYard extends AIRetrofit_IndustryBase {
    static String C1 = "AIretrofit_SubCommandNode";
    static String C2 = "AIretrofit_WorkerDrone";
    //static String C3 = "heavy_machinery";
    static String subbmarket = "AIRetrofit_ShipyardSubmarket";

    static String alphaDescription = Global.getSettings().getString("AIRetrofitShipyard_ADescription");
    static String betaDescription = Global.getSettings().getString("AIRetrofitShipyard_BDescription");
    static String gammaDescription = Global.getSettings().getString("AIRetrofitShipyard_GDescription");
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
        tooltip.addPara(alphaDescription,0f);
    }
    @Override
    protected void	addBetaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        tooltip.addPara(betaDescription,0f);

    }
    @Override
    protected void	addGammaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        tooltip.addPara(gammaDescription,0f);

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
}