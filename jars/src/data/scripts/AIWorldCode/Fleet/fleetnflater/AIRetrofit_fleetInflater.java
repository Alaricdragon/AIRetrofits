package data.scripts.AIWorldCode.Fleet.fleetnflater;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflater;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflaterParams;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_fleetInflater extends DefaultFleetInflater {
    public static String Hullmod = AIRetrofits_Constants_3.ASIC_BaseHullmod;
    static String shipYardIndustry = AIRetrofits_Constants_3.ASIC_shipYardIndustry;//"AIRetrofit_shipYard";
    public static String[] hullmods = AIRetrofits_Constants_3.ASIC_hullmods;
    public AIRetrofit_fleetInflater(DefaultFleetInflaterParams p) {
        super(p);
    }
    public void inflate(CampaignFleetAPI fleet){
        super.inflate(fleet);
        String tempHullmod = setDataLists.forcedFleetMod(fleet);
        if (tempHullmod != null){
            for (int a = 0; a < fleet.getFleetData().getMembersInPriorityOrder().size(); a++) {
                fleet.getFleetData().getMembersInPriorityOrder().get(a).getStats().getVariant().addMod(tempHullmod);
            }
            return;
        }
        if (setDataLists.fleetMod(fleet)) {
            String addHullMod;
            MarketAPI market = Global.getSector().getEconomy().getMarket(fleet.getMemory().getString(MemFlags.MEMORY_KEY_SOURCE_MARKET));
            if (market.hasIndustry(shipYardIndustry) && market.getIndustry(shipYardIndustry).isFunctional()){
                String temp = market.getIndustry(shipYardIndustry).getAICoreId();
                if(market.getIndustry(shipYardIndustry).getAICoreId() == null) {
                    temp = "";
                }
                switch (temp) {
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
