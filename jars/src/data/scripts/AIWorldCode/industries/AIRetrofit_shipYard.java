package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class AIRetrofit_shipYard extends AIRetrofit_IndustryBase {
    static String C1 = "AIretrofit_SubCommandNode";
    static String C2 = "AIretrofit_WorkerDrone";
    //static String C3 = "heavy_machinery";
    static String subbmarket = "AIRetrofit_ShipyardSubmarket";

    static String alphaDescription = Global.getSettings().getString("AIRetrofitShipyard_ADescription");
    static String betaDescription = Global.getSettings().getString("AIRetrofitShipyard_BDescription");
    static String gammaDescription = Global.getSettings().getString("AIRetrofitShipyard_GDescription");
    static float improveValue = Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    static float defaultValue = Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");
    static String improveDescription = Global.getSettings().getString("AIRetrofitShipyard_IDescription");
    static String improvedDescription = Global.getSettings().getString("AIRetrofitShipyard_IedDescription");
    static String extraDescription = Global.getSettings().getString("AIRetrofitShipyard_Description");
    static int[] costPerShip = {
            (int)Global.getSettings().getFloat("AIRetrofitShipyard_perCrewDEFAULT"),
            (int)Global.getSettings().getFloat("AIRetrofitShipyard_perCrewFIGHTER"),
            (int)Global.getSettings().getFloat("AIRetrofitShipyard_perCrewFRIGATE"),
            (int)Global.getSettings().getFloat("AIRetrofitShipyard_perCrewDESTROYER"),
            (int)Global.getSettings().getFloat("AIRetrofitShipyard_perCrewCRUISER"),
            (int)Global.getSettings().getFloat("AIRetrofitShipyard_perCrewCAPITAL_SHIP"),
    };
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
    @Override
    protected void applyImproveModifiers() {
    }
    @Override
    public void addImproveDesc(TooltipMakerAPI info, ImprovementDescriptionMode mode) {
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();

        String aStr = "" + (100 * improveValue) + "%";

        if (mode == ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
            info.addPara(improveDescription, 0f, highlight, aStr);
        } else {
            info.addPara(improvedDescription, 0f, highlight, aStr);
        }

        info.addSpacer(opad);
        //super.addImproveDesc(info, mode);
        float initPad = 0f;
        if (mode != ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
            info.addPara("Each improvement made at a colony doubles the number of " +
                            "" + Misc.STORY + " points required to make an additional improvement.", initPad,
                    Misc.getStoryOptionColor(), Misc.STORY + " points");
        }
    }
    @Override
    protected void	addPostDescriptionSection(TooltipMakerAPI tooltip, Industry.IndustryTooltipMode mode){
        //mode.
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        tooltip.addSpacer(opad);
        /*
            required inputed data:
            current rate of production.
            rate of production per crew
                -{4,0.25f,1,2,4,8}
                -defalt
                -fighter
                -friget
                -destroyer
                -creuser
                -battleship
         */
        String[] exstra = {
                "" + (int)(defaultValue * (1 + improveValue)),
                //"" + costPerShip[0],
                //"" + costPerShip[1],
                "" + costPerShip[2],
                "" + costPerShip[3],
                "" + costPerShip[4],
                "" + costPerShip[5]};
        tooltip.addPara(extraDescription, 0f, highlight, exstra);
    }
}