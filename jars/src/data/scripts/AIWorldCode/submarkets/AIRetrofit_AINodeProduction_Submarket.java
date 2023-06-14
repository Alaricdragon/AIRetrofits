package data.scripts.AIWorldCode.submarkets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.CoreUIAPI;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.rpg.Person;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.industries.specal.AIRetrofit_shipYard;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.SpecalItems.AIRetrofit_CommandNode_SpecalItemData;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.List;

public class AIRetrofit_AINodeProduction_Submarket extends BaseSubmarketPlugin {
    /*
    put the upgrade code in AIRetrofit_MarketListener.
     */
    private static final String illegalTest = Global.getSettings().getString("AIRetrofitShipyard_IllegalText");
    private static final String cantUseDescription = Global.getSettings().getString("AIRetrofitSubMarket_CantUpgradeDescription");
    static String industry = "AIRetrofit_shipYard";
    public void resetCargo(CargoAPI cargo){
        AIRetrofit_Log.loging("resetting Command Node submarket cargo",this,true);
        //float[] power = getPower();
        emptyCargo(cargo);
        float totalPower = 10;
        int cores = 4;
        float minPowerWeight = 1;//the diffrence between the two numbers here is how mush range the cores power will have.
        float maxPowerWeight = 20;//--

        int personalityVariance = 3;
        float varianceChance = 0.1f;
        int aggression = AIRetrofits_CreatePeople.personalityMix(this.market.getFaction().getDoctrine(),personalityVariance,varianceChance);//this.market.getFaction().getDoctrine().getAggression();
        AIRetrofits_CreatePeople.addCores(cargo,aggression,totalPower,cores,maxPowerWeight,minPowerWeight);
    }

    public void emptyCargo(CargoAPI cargo){
        List<CargoStackAPI> a = cargo.getStacksCopy();
        for(int b = 0; b < a.size(); b++){
            cargo.removeStack(a.get(b));
        }
    }
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
    public CargoAPI getCargo(){
        AIRetrofit_Log.loging("running getCargo",this,true);
        CargoAPI cargo = super.getCargo();
        AIRetrofit_Log.loging("last update when?"+this.sinceSWUpdate,this,true);
        if(this.okToUpdateShipsAndWeapons()||true){
            resetCargo(cargo);
            this.sinceSWUpdate = 0;
        }
        return cargo;
    }

    @Override
    public void advance(float amount){
        super.advance(amount);
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
        return false;
    }
    @Override
    public String getBuyVerb() {
        return "create";
    }
    @Override
    public String getSellVerb() {
        return "leave";
    }
    @Override
    public boolean 	showInFleetScreen(){
        return false;
    }
    @Override
    public boolean showInCargoScreen() {
        return true;
    }
    /*@Override
       public boolean	isIllegalOnSubmarket(java.lang.String commodityId, SubmarketPlugin.TransferAction action){
            //commodityId.
            return false;
        }*/
    @Override
    public boolean isIllegalOnSubmarket(CargoStackAPI stack, TransferAction action) {
        if(stack.getCargo().equals(cargo)){
            return false;
        }
        /*
            if (stack.getCommodityId() == null) return true;
            if(action.equals(TransferAction.PLAYER_SELL)){
                return true;
            }*/
        return true;
    }
        /*
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
            for(String a : AIRetrofits_Constants.ASIC_Secondary_Hullmods) {
                if (member.getVariant().hasHullMod(a)) {
                    return false;
                }
            }
            if(member.getStats().getMinCrewMod().computeEffective(member.getVariant().getHullSpec().getMinCrew()) <= 0){
                return true;
                //ship.getMutableStats().getVariant().getHullSpec().getMinCrew
            }
            return false;
        }*/
    @Override
    public String getIllegalTransferText(FleetMemberAPI member,SubmarketPlugin.TransferAction action){
        return illegalTest;//"cannot preform modifications to ships that require no crew for reasons other then having a AI-Retrofit hullmod installed.";
    }
    @Override
    public 	boolean isMilitaryMarket(){
        return true;
    }
}

