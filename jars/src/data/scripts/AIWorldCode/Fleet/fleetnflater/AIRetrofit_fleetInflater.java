package data.scripts.AIWorldCode.Fleet.fleetnflater;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflater;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflaterParams;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.AIWorldCode.Fleet.setDataLists;

public class AIRetrofit_fleetInflater extends DefaultFleetInflater {
    static String Condition = "AIRetrofit_AIPop";
    static String Hullmod = "AIretrofit_airetrofit";
    public AIRetrofit_fleetInflater(DefaultFleetInflaterParams p) {
        super(p);
    }
    public void inflate(CampaignFleetAPI fleet){
        super.inflate(fleet);
        if (setDataLists.fleetMod(fleet)) {
            for (int a = 0; a < fleet.getFleetData().getMembersInPriorityOrder().size(); a++) {
                fleet.getFleetData().getMembersInPriorityOrder().get(a).getStats().getVariant().addMod(Hullmod);
            }
        }
    }
}
