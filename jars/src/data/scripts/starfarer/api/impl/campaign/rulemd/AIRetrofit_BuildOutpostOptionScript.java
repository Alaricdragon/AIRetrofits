package data.scripts.starfarer.api.impl.campaign.rulemd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.characters.AbilityPlugin;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.List;
import java.util.Map;

public class AIRetrofit_BuildOutpostOptionScript extends BaseCommandPlugin {
    private static boolean can = AIRetrofits_Constants_3.Market_EnableMarketFetures;
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        AbilityPlugin a = fleet.getAbility("AIretrofit_robot_drone_forge");
        //a.
        return a != null && can;
    }
}
