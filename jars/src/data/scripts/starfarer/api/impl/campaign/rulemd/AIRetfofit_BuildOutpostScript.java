package data.scripts.starfarer.api.impl.campaign.rulemd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.PlanetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import data.scripts.crewReplacer_Job;
import data.scripts.crewReplacer_Main;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.List;
import java.util.Map;

public class AIRetfofit_BuildOutpostScript extends BaseCommandPlugin {
    float reqAICore = AIRetrofits_Constants.FoundAMarket_reqAICore;
    float reqWorker = AIRetrofits_Constants.FoundAMarket_reqWorker;
    float reqSupply = AIRetrofits_Constants.FoundAMarket_reqSupply;
    float reqMachinery = AIRetrofits_Constants.FoundAMarket_reqMachinery;
    String AICoreJob = AIRetrofits_Constants.FoundAMarket_AICoreJob;
    String AIWorkerJob = AIRetrofits_Constants.FoundAMarket_AIWorkerJob;
    String SupplyJob = AIRetrofits_Constants.FoundAMarket_SupplyJob;
    String MachineryJob = AIRetrofits_Constants.FoundAMarket_MachineryJob;
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        MarketAPI market = dialog.getInteractionTarget().getMarket();
        PlanetAPI planet = dialog.getInteractionTarget().getMarket().getPlanetEntity();
        FactionAPI faction = Global.getSector().getFaction(Factions.PLAYER);
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        dialog.getTextPanel().addPara("loss and build conoly text");
        dialog.getTextPanel().addPara("");

        crewReplacer_Job tempJob = crewReplacer_Main.getJob(AICoreJob);
        tempJob.automaticlyGetDisplayAndApplyCrewLost(fleet,(int)reqAICore,reqAICore,dialog.getTextPanel());
        dialog.getTextPanel().addPara("");

        tempJob = crewReplacer_Main.getJob(AIWorkerJob);
        tempJob.automaticlyGetDisplayAndApplyCrewLost(fleet,(int)reqWorker,reqWorker,dialog.getTextPanel());
        dialog.getTextPanel().addPara("");

        tempJob = crewReplacer_Main.getJob(SupplyJob);
        tempJob.automaticlyGetDisplayAndApplyCrewLost(fleet,(int)reqSupply,reqSupply,dialog.getTextPanel());
        dialog.getTextPanel().addPara("");

        tempJob = crewReplacer_Main.getJob(MachineryJob);
        tempJob.automaticlyGetDisplayAndApplyCrewLost(fleet,(int)reqMachinery,reqMachinery,dialog.getTextPanel());
        dialog.getTextPanel().addPara("all good times.text");
        AIRetrofit_StartAutomatedColony.createColonyStatic(market,planet,faction,false,true);
        return true;
    }
}
