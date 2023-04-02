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
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.List;
import java.util.Map;

public class AIRetrofit_CanBuildOutpostScript extends BaseCommandPlugin {
    float reqAICore = AIRetrofits_Constants.FoundAMarket_reqAICore;
    float reqWorker = AIRetrofits_Constants.FoundAMarket_reqWorker;
    float reqSupply = AIRetrofits_Constants.FoundAMarket_reqSupply;
    float reqMachinery = AIRetrofits_Constants.FoundAMarket_reqMachinery;
    String AICoreJob = AIRetrofits_Constants.FoundAMarket_AICoreJob;
    String AIWorkerJob = AIRetrofits_Constants.FoundAMarket_AIWorkerJob;
    String SupplyJob = AIRetrofits_Constants.FoundAMarket_SupplyJob;
    String MachineryJob = AIRetrofits_Constants.FoundAMarket_MachineryJob;
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
