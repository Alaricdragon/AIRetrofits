package data.scripts.AIWorldCode.submarkets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.CoreUIAPI;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.industries.specal.AIRetrofit_shipYard;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;

public class AIRetrofit_AINodeProduction_Submarket extends BaseSubmarketPlugin {
    /*
    put the upgrade code in AIRetrofit_MarketListener.
     */
        private static final String illegalTest = Global.getSettings().getString("AIRetrofitShipyard_IllegalText");
        private static final String cantUseDescription = Global.getSettings().getString("AIRetrofitSubMarket_CantUpgradeDescription");
        static String industry = "AIRetrofit_shipYard";
        public float[] getPower(){
            int amount = 5;
            float minPow = 15;
            float maxPow = 25;
            float[] output = new float[amount];
            for(int a = 0; a < output.length; a++){
                output[a] = (float)(minPow + (Math.random() * (maxPow - minPow)));
            }
            return output;
        }
        public void resetCargo(CargoAPI cargo){
            float[] power = getPower();
            for(float a: power){
                AIRetrofit_CommandNode item = new AIRetrofit_CommandNode();
                item.createPerson((int) a,1,AIRetrofit_CommandNode.getPersonTypeByWeight());
                //cargo.addSpecial(item,1);
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
            if(this.okToUpdateShipsAndWeapons()){
                resetCargo(cargo);
            }
            return cargo;
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
            return false;
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

