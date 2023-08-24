package data.scripts.AIWorldCode.submarkets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.CoreUIAPI;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
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

    public static final float PPS_B = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_PowerPerSize");
    public static final float BP_B = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BasePower");
    public static final float CPS_B =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_CoresPerSize");
    public static final float BC_B =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BaseCores");
    public static final float MiPW_B=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_minWeight");
    public static final float MaPW_B=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_maxWeight");


    public static final float PPS_G = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_PowerPerSize_Gamma");
    public static final float BP_G = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BasePower_Gamma");
    public static final float CPS_G =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_CoresPerSize_Gamma");
    public static final float BC_G =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BaseCores_Gamma");
    public static final float MiPW_G=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_minWeight_Gamma");
    public static final float MaPW_G=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_maxWeight_Gamma");

    public static final float PPS_Be = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_PowerPerSize_beta");
    public static final float BP_Be = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BasePower_beta");
    public static final float CPS_Be =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_CoresPerSize_beta");
    public static final float BC_Be =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BaseCores_beta");
    public static final float MiPW_Be=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_minWeight_beta");
    public static final float MaPW_Be=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_maxWeight_beta");

    public static final float PPS_A = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_PowerPerSize_alpha");
    public static final float BP_A = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BasePower_alpha");
    public static final float CPS_A =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_CoresPerSize_alpha");
    public static final float BC_A =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BaseCores_alpha");
    public static final float MiPW_A=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_minWeight_alpha");
    public static final float MaPW_A=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_maxWeight_alpha");

    public static final float PPS_O = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_PowerPerSize_omega");
    public static final float BP_O = Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BasePower_omega");
    public static final float CPS_O =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_CoresPerSize_omega");
    public static final float BC_O =Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_BaseCores_omega");
    public static final float MiPW_O=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_minWeight_omega");
    public static final float MaPW_O=Global.getSettings().getFloat("AIRetrofit_AINodeProducetionFacility_Submarket_maxWeight_omega");
    public static float[] getStats(Industry industry){
        MarketAPI market = industry.getMarket();
        float totalPower=0;
        int cores=0;
        float minPowerWeight=0;//the diffrence between the two numbers here is how mush range the cores power will have.
        float maxPowerWeight=0;//--
        float produce = market.getIndustry(AIRetrofits_Constants.Industry_AINodeProductionFacility).getSupply(AIRetrofits_Constants.RobotForge_SubCommandNode).getQuantity().getModifiedValue();
        if(market.getIndustry(AIRetrofits_Constants.Industry_AINodeProductionFacility).getAICoreId() != null) {
            switch (market.getIndustry(AIRetrofits_Constants.Industry_AINodeProductionFacility).getAICoreId()) {
                case Commodities.ALPHA_CORE:
                    totalPower = (produce * PPS_A) + BP_A;
                    cores = (int) ((produce * CPS_A) + BC_A);
                    minPowerWeight = MiPW_A;//the diffrence between the two numbers here is how mush range the cores power will have.
                    maxPowerWeight = MaPW_A;//--
                    break;
                case Commodities.BETA_CORE:
                    totalPower = (produce * PPS_Be) + BP_Be;
                    cores = (int) ((produce * CPS_Be) + BC_Be);
                    minPowerWeight = MiPW_Be;//the diffrence between the two numbers here is how mush range the cores power will have.
                    maxPowerWeight = MaPW_Be;//--
                    break;
                case Commodities.GAMMA_CORE:
                    totalPower = (produce * PPS_G) + BP_G;
                    cores = (int) ((produce * CPS_G) + BC_G);
                    minPowerWeight = MiPW_G;//the diffrence between the two numbers here is how mush range the cores power will have.
                    maxPowerWeight = MaPW_G;//--
                    break;
                case Commodities.OMEGA_CORE:
                    totalPower = (produce * PPS_O) + BP_O;
                    cores = (int) ((produce * CPS_O) + BC_O);
                    minPowerWeight = MiPW_O;//the diffrence between the two numbers here is how mush range the cores power will have.
                    maxPowerWeight = MaPW_O;//--
                    break;
                default:
                    totalPower = (produce * PPS_B) + BP_B;
                    cores = (int) ((produce * CPS_B) + BC_B);
                    minPowerWeight = MiPW_B;//the diffrence between the two numbers here is how mush range the cores power will have.
                    maxPowerWeight = MaPW_B;//--
                    break;
            }
        }else {
            totalPower = (produce * PPS_B) + BP_B;
            cores = (int) ((produce * CPS_B) + BC_B);
            minPowerWeight = MiPW_B;//the diffrence between the two numbers here is how mush range the cores power will have.
            maxPowerWeight = MaPW_B;//--
        }
        if(market.getIndustry(AIRetrofits_Constants.Industry_AINodeProductionFacility).isImproved()){
            //run whatever improving this industry will do. extra core and power per level maybe? just more power per level? mmmm
        }
        return new float[]{totalPower,cores,minPowerWeight,maxPowerWeight};
    }
    public void resetCargo(CargoAPI cargo){
        AIRetrofit_Log.loging("resetting Command Node submarket cargo",this,true);
        //float[] power = getPower();
        emptyCargo(cargo);
        float[] temp = getStats(market.getIndustry(AIRetrofits_Constants.Industry_AINodeProductionFacility));
        float totalPower=temp[0];
        int cores=(int)temp[1];
        float minPowerWeight=temp[2];//the diffrence between the two numbers here is how mush range the cores power will have.
        float maxPowerWeight=temp[3];//--

        AIRetrofits_CreatePeople.addCores(cargo,this.market.getFaction(),totalPower,cores,maxPowerWeight,minPowerWeight);
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
        /*
        if(!(market.hasIndustry(industry) && market.getIndustry(industry).isFunctional())){
            tooltip.addPara(cantUseDescription,pad);
            return;
        }
        AIRetrofit_shipYard.AIRetrofit_ShipyardDescription(tooltip,market);*/
    }
    @Override
    public CargoAPI getCargo(){
        AIRetrofit_Log.loging("running getCargo",this,true);
        CargoAPI cargo = super.getCargo();
        AIRetrofit_Log.loging("last update when?"+this.sinceSWUpdate,this,true);
        if(market.hasIndustry(AIRetrofits_Constants.Industry_AINodeProductionFacility) && !market.getIndustry(AIRetrofits_Constants.Industry_AINodeProductionFacility).isFunctional()) {
            emptyCargo(cargo);
        }else if(this.okToUpdateShipsAndWeapons()){//||true){
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

