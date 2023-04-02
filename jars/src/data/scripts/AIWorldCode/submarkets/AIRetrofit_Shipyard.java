package data.scripts.AIWorldCode.submarkets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CoreUIAPI;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;
import com.fs.starfarer.api.loading.IndustrySpecAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.loading.S;
import data.scripts.AIWorldCode.industries.specal.AIRetrofit_shipYard;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.List;

public class AIRetrofit_Shipyard extends BaseSubmarketPlugin {
    /*
    put the upgrade code in AIRetrofit_MarketListener.
     */
    private static final String[] hullmods = AIRetrofits_Constants.ASIC_hullmods;
    private static final String OtherHullmod = AIRetrofits_Constants.ASIC_BaseHullmod;
    private static final String illegalTest = Global.getSettings().getString("AIRetrofitShipyard_IllegalText");
    private static final String cantUseDescription = Global.getSettings().getString("AIRetrofitSubMarket_CantUpgradeDescription");
    static float shipyard_IValue = AIRetrofits_Constants.ASIC_improveValue;//Global.getSettings().getFloat("AIRetrofitShipyard_IValue");
    static float shipyardDValue = AIRetrofits_Constants.ASIC_defaultValue;//Global.getSettings().getFloat("AIRetrofitShipyard_defaultPoints");
    static String industry = "AIRetrofit_shipYard";
    @Override
    public void createTooltip(CoreUIAPI ui, TooltipMakerAPI tooltip, boolean expanded){
        super.createTooltip(ui,tooltip,expanded);
        Color highlight = Misc.getHighlightColor();
        float pad = 5;
        if(!(market.hasIndustry(industry) && market.getIndustry(industry).isFunctional())){
            tooltip.addPara(cantUseDescription,pad);
            return;
        }
        AIRetrofit_shipYard.AIRetrofit_ShipyardDescription(tooltip,market);
    }
    @Override
    public void advance(float amount){
        //runSingleAIRetrofit_Shipyard(market);
        //cargo.getFleetData().getMembersListCopy();
    }
    @Override
    public boolean isParticipatesInEconomy() {
        return false;
    }

    @Override
    public float getTariff() {
        return 0f;
    }

    @Override
    public boolean isFreeTransfer() {
        return true;
    }
    @Override
    public String getBuyVerb() {
        return "take";
    }

    @Override
    public String getSellVerb() {
        return "leave";
    }
    @Override
    public boolean showInCargoScreen() {
        return false;
    }
    @Override
    public boolean	isIllegalOnSubmarket(java.lang.String commodityId, SubmarketPlugin.TransferAction action){
        return false;
    }
    @Override
    public boolean isIllegalOnSubmarket(FleetMemberAPI member, SubmarketPlugin.TransferAction action){
        for(String a : hullmods) {
            if (member.getVariant().hasHullMod(a)) {
                return false;
            }
        }
        if(member.getVariant().hasHullMod(OtherHullmod)){
            return false;
        }
        if(member.getStats().getMinCrewMod().computeEffective(member.getVariant().getHullSpec().getMinCrew()) <= 0){
            return true;
            //ship.getMutableStats().getVariant().getHullSpec().getMinCrew
        }
        return false;
    }
    @Override
    public String getIllegalTransferText(FleetMemberAPI member,SubmarketPlugin.TransferAction action){
        return illegalTest;//"cannot preform modifications to ships that require no crew for reasons other then having a AI-Retrofit hullmod installed.";
    }
    @Override
    public 	boolean isMilitaryMarket(){
        return true;
    }
}
