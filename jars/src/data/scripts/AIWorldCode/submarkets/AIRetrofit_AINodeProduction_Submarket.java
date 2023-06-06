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
            AIRetrofit_Log.loging("resetting Command Node submarket cargo",this,true);
            //float[] power = getPower();
            emptyCargo(cargo);
            float cores = 5;
            String[] types = AIRetrofits_Constants.PersonTypes_List;
            float[] weight = AIRetrofits_Constants.PersonWeight_List;
            float totalWeight=0;
            for(float a : weight){
                totalWeight+=a;
            }
            float maxPower = 30;
            float minPower = 10;
            float maxPower2 = 30;
            float minPower2 = 10;
            for(int a = 0; a < cores; a++){
                float type = (float) (Math.random() * totalWeight);
                String index = "";
                float b2=0;
                for(int b = 0; b < weight.length;b++){
                    b2+= weight.length;
                    if(b2 >= type){
                        index = types[b];
                        break;
                    }
                }
                int power= (int) (minPower+(Math.random() * maxPower - minPower)), power2=(int) (minPower2+(Math.random() * maxPower2 - minPower2));
                int aggression = this.market.getFaction().getDoctrine().getAggression();
                addCore(cargo,power,power2,aggression,index);
            }
        }
        public void addCore(CargoAPI cargo,int power,int power2,int personality,String type){
            AIRetrofit_CommandNode item = new AIRetrofit_CommandNode();
            AIRetrofit_CommandNode_SpecalItemData amb = new AIRetrofit_CommandNode_SpecalItemData(AIRetrofits_Constants.SpecalItemID_CommandNode, null, item.person);
            //amb.setPerson();
            PersonAPI person;
            switch (type){
                case AIRetrofits_Constants.PersonTypes_Officer:
                    person = AIRetrofits_CreatePeople.createOfficer(personality,power,power2);
                    amb.setPerson(person);
                    break;
                case AIRetrofits_Constants.PersonTypes_Admin:
                    person = AIRetrofits_CreatePeople.createAdmen(power);
                    amb.setPerson(person);
                    break;
                default:
            }
            cargo.addSpecial(amb, 1);
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
            if(this.okToUpdateShipsAndWeapons()){
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

