package data.scripts.AIWorldCode.Fleet.fleetnflater;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflater;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflaterParams;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.AIWorldCode.Fleet.setDataLists;

public class AIRetrofit_fleetInflater extends DefaultFleetInflater {
    static String Condition = "AIRetrofit_AIPop";
    static String Hullmod = "AIretrofit_airetrofit";
    static String shipYardIndustry = "AIRetrofit_shipYard";
    static String[] hullmods = {
            "AIRetrofit_ShipyardGamma",
            "AIRetrofit_ShipyardBeta",
            "AIRetrofit_ShipyardAlpha",
            "AIRetrofit_ShipyardOmega",
            "AIRetrofit_ShipyardBase"};
    public AIRetrofit_fleetInflater(DefaultFleetInflaterParams p) {
        super(p);
    }
    public void inflate(CampaignFleetAPI fleet){
        super.inflate(fleet);
        if (setDataLists.fleetMod(fleet)) {
            String addHullMod;
            MarketAPI market = Global.getSector().getEconomy().getMarket(fleet.getMemory().getString(MemFlags.MEMORY_KEY_SOURCE_MARKET));
            if (market.hasIndustry(shipYardIndustry) && market.getIndustry(shipYardIndustry).isFunctional()){
                switch (market.getIndustry(shipYardIndustry).getAICoreId()) {
                    case "gamma_core":
                        addHullMod = hullmods[0];
                        break;
                    case "beta_core":
                        addHullMod = hullmods[1];
                        break;
                    case "alpha_core":
                        addHullMod = hullmods[2];
                        break;
                    case "omega_core"://are omega cores even obtainable?
                        addHullMod = hullmods[3];
                        break;
                    default:
                        addHullMod = hullmods[4];
                        break;
                }
            }else{
                addHullMod = Hullmod;
            }
            for (int a = 0; a < fleet.getFleetData().getMembersInPriorityOrder().size(); a++) {
                fleet.getFleetData().getMembersInPriorityOrder().get(a).getStats().getVariant().addMod(addHullMod);
            }
        }
    }
}
