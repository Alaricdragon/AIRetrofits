package data.scripts.AIWorldCode.submarkets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;

import java.util.List;

public class AIRetrofit_Shipyard extends BaseSubmarketPlugin {
    /*
    put the upgrade code in AIRetrofit_MarketListener.
     */
    private float time = 0;
    private float power = 0;
    private static final String[] hullmods = {
            "AIretrofit_airetrofit",
            "AIRetrofit_ShipyardGamma",
            "AIRetrofit_ShipyardBeta",
            "AIRetrofit_ShipyardAlpha",
            "AIRetrofit_ShipyardOmega",
            "AIRetrofit_ShipyardBase"};
    private static final String illegalTest = Global.getSettings().getString("AIRetrofitShipyard_IllegalText");
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
    final static String shipYardIndustry = "AIRetrofit_shipYard";
    final static String shipYardSubmarket = "AIRetrofit_ShipyardSubmarket";
    private void runSingleAIRetrofit_Shipyard(MarketAPI market){
        market = Global.getSector().getEconomy().getMarket(market.getId());
        if(!market.hasSubmarket(shipYardSubmarket)){
            return;
        }/*else{
            int[] a = {};
            a[1] = a[2];//crash the game. as an console log lol.
        }*/
        final String[] stopHullMods = {"automated"};
        final String[] addHullMods = {
                "AIRetrofit_ShipyardGamma",
                "AIRetrofit_ShipyardBeta",
                "AIRetrofit_ShipyardAlpha",
                "AIRetrofit_ShipyardOmega",
                "AIRetrofit_ShipyardBase"};
        final float startingPonits = 8;
        final float bounus = 2;
        final float[] costs = {4,0.25f,1,2,4,8};
        List<FleetMemberAPI> ships = market.getSubmarket(shipYardSubmarket).getCargo().getMothballedShips().getMembersListCopy();
        //market.getSubmarket(shipYardSubmarket).getCargo().getMothballedShips().get
        //market.getSubmarket(shipYardSubmarket).getCargo().

        float points = startingPonits;
        String addHullMod;
        if(market.getIndustry(shipYardIndustry).isImproved()){
            points *= bounus;
        }
        try {
            switch (market.getIndustry(shipYardIndustry).getAICoreId()) {
                case "gamma_core":
                    addHullMod = addHullMods[0];
                    break;
                case "beta_core":
                    addHullMod = addHullMods[1];
                    break;
                case "alpha_core":
                    addHullMod = addHullMods[2];
                    break;
                case "omega_core"://are omega cores even obtainable?
                    addHullMod = addHullMods[3];
                    break;
                default:
                    addHullMod = addHullMods[4];
                    break;
            }
        }catch (Exception e){
            addHullMod = addHullMods[4];
        }
        for(FleetMemberAPI ship : ships){
            boolean exit = false;
            for(String a : stopHullMods) {
                if(ship.getVariant().hasHullMod(a)) {
                    exit = true;
                    break;
                }
            }
            if(!exit && !ship.getVariant().hasHullMod(addHullMod)){
                float cost = 0;
                switch (ship.getVariant().getHullSize()){
                    case DEFAULT:
                        cost = costs[0];
                        break;
                    case FIGHTER:
                        cost = costs[1];
                        break;
                    case FRIGATE:
                        cost = costs[2];
                        break;
                    case DESTROYER:
                        cost = costs[3];
                        break;
                    case CRUISER:
                        cost = costs[4];
                        break;
                    case CAPITAL_SHIP:
                        cost = costs[5];
                        break;
                }
                if(cost <= points){
                    points -= cost;
                    for(String a : addHullMods){
                        ship.getVariant().removeMod(a);
                    }
                    ship.getVariant().addMod(addHullMod);
                }
                if(points <= 0){
                    return;
                }
            }
        }
    }

}
