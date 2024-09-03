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
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.List;
import java.util.Map;

public class AIRetfofit_BuildOutpostScript extends BaseCommandPlugin {
    float reqAICore = AIRetrofits_Constants_3.FoundAMarket_reqAICore;
    float reqWorker = AIRetrofits_Constants_3.FoundAMarket_reqWorker;
    float reqSupply = AIRetrofits_Constants_3.FoundAMarket_reqSupply;
    float reqMachinery = AIRetrofits_Constants_3.FoundAMarket_reqMachinery;
    String AICoreJob = AIRetrofits_Constants_3.FoundAMarket_AICoreJob;
    String AIWorkerJob = AIRetrofits_Constants_3.FoundAMarket_AIWorkerJob;
    String SupplyJob = AIRetrofits_Constants_3.FoundAMarket_SupplyJob;
    String MachineryJob = AIRetrofits_Constants_3.FoundAMarket_MachineryJob;

    private static String execute0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_MarketFoundedText0");
    private static String execute1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_MarketFoundedText1");
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Misc.Token> params, Map<String, MemoryAPI> memoryMap) {
        MarketAPI market = dialog.getInteractionTarget().getMarket();
        PlanetAPI planet = dialog.getInteractionTarget().getMarket().getPlanetEntity();
        FactionAPI faction = Global.getSector().getFaction(Factions.PLAYER);
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        dialog.getTextPanel().addPara(execute0);
        dialog.getTextPanel().addPara("");
        dialog.getTextPanel().addPara(execute1);
        crewReplacer_Job tempJob = crewReplacer_Main.getJob(AICoreJob);
        tempJob.automaticlyGetDisplayAndApplyCrewLost(fleet,(int)reqAICore,reqAICore,dialog.getTextPanel());

        tempJob = crewReplacer_Main.getJob(AIWorkerJob);
        tempJob.automaticlyGetDisplayAndApplyCrewLost(fleet,(int)reqWorker,reqWorker,dialog.getTextPanel());

        tempJob = crewReplacer_Main.getJob(SupplyJob);
        tempJob.automaticlyGetDisplayAndApplyCrewLost(fleet,(int)reqSupply,reqSupply,dialog.getTextPanel());

        tempJob = crewReplacer_Main.getJob(MachineryJob);
        tempJob.automaticlyGetDisplayAndApplyCrewLost(fleet,(int)reqMachinery,reqMachinery,dialog.getTextPanel());
        AIRetrofit_StartAutomatedColony.createColonyStatic(market,planet,faction,false,true);
        return true;
    }
}
