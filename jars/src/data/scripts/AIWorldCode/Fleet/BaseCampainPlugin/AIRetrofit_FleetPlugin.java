package data.scripts.AIWorldCode.Fleet.BaseCampainPlugin;

import com.fs.starfarer.api.PluginPick;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.fleets.DefaultFleetInflaterParams;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIWorldCode.Fleet.fleetnflater.AIRetrofit_fleetInflater;

import java.util.List;
import java.util.Map;

public class AIRetrofit_FleetPlugin extends BaseCampaignPlugin {
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        return false;
    }
    @Override
    public boolean isTransient() {
        return true;
    }
    @Override
    public PluginPick<FleetInflater> pickFleetInflater(CampaignFleetAPI fleet, Object params) {
        if (params instanceof DefaultFleetInflaterParams) {
            DefaultFleetInflaterParams p = (DefaultFleetInflaterParams) params;
            return new PluginPick<FleetInflater>(new AIRetrofit_fleetInflater(p), CampaignPlugin.PickPriority.CORE_GENERAL);
        }
        return null;
    }
}
