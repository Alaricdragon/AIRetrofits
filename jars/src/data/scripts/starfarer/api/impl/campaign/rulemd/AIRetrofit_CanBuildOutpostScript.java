package data.scripts.starfarer.api.impl.campaign.rulemd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import data.scripts.crewReplacer_Job;
import data.scripts.crewReplacer_Main;

import java.util.List;
import java.util.Map;

public class AIRetrofit_CanBuildOutpostScript extends BaseCommandPlugin {
    float reqAICore = Global.getSettings().getFloat("AIRetrofit_MarketCost_reqAICore");//10
    float reqWorker = Global.getSettings().getFloat("AIRetrofit_MarketCost_reqWorker");//1000;
    float reqSupply = Global.getSettings().getFloat("AIRetrofit_MarketCost_reqSupply");//200;
    float reqMachinery = Global.getSettings().getFloat("AIRetrofit_MarketCost_reqMachinery");//200;
    String AICoreJob = "AIRetrofit_OutpostAICore";
    String AIWorkerJob = "AIRetrofit_OutpostWorker";
    String SupplyJob = "AIRetrofit_OutpostSupply";
    String MachineryJob = "AIRetrofit_OutpostMachinery";
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        float[] got = new float[4];
        //dialog.getTextPanel().addPara("buildOptionText");
        crewReplacer_Job tempJob = crewReplacer_Main.getJob(AICoreJob);
        got[0] = tempJob.getAvailableCrewPower(fleet);
        tempJob = crewReplacer_Main.getJob(AIWorkerJob);
        got[1] = tempJob.getAvailableCrewPower(fleet);
        tempJob = crewReplacer_Main.getJob(SupplyJob);
        got[2] = tempJob.getAvailableCrewPower(fleet);
        tempJob = crewReplacer_Main.getJob(MachineryJob);
        got[3] = tempJob.getAvailableCrewPower(fleet);
//FireAll
        //
        return (got[0] >= reqAICore && got[1] >= reqWorker && got[2] >= reqSupply && got[3] >= reqMachinery);
    }
}
