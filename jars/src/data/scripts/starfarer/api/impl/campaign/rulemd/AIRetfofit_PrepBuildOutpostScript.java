package data.scripts.starfarer.api.impl.campaign.rulemd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.crewReplacer_Job;
import data.scripts.crewReplacer_Main;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.List;
import java.util.Map;

public class AIRetfofit_PrepBuildOutpostScript extends BaseCommandPlugin {
    float reqAICore = AIRetrofits_Constants_3.FoundAMarket_reqAICore;
    float reqWorker = AIRetrofits_Constants_3.FoundAMarket_reqWorker;
    float reqSupply = AIRetrofits_Constants_3.FoundAMarket_reqSupply;
    float reqMachinery = AIRetrofits_Constants_3.FoundAMarket_reqMachinery;
    String AICoreJob = AIRetrofits_Constants_3.FoundAMarket_AICoreJob;
    String AIWorkerJob = AIRetrofits_Constants_3.FoundAMarket_AIWorkerJob;
    String SupplyJob = AIRetrofits_Constants_3.FoundAMarket_SupplyJob;
    String MachineryJob = AIRetrofits_Constants_3.FoundAMarket_MachineryJob;

    private static String execute0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_MarketStartupText0");
    private static String execute1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_MarketStartupText1");
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

        dialog.getTextPanel().addPara(execute0);
        dialog.getTextPanel().addPara("");
        dialog.getTextPanel().addPara(execute1);
        texttemp("AIretrofit_SubCommandNode",got[0],reqAICore,dialog.getTextPanel());
        texttemp("AIretrofit_WorkerDrone",got[1],reqWorker,dialog.getTextPanel());
        texttemp("supplies",got[2],reqSupply,dialog.getTextPanel());
        texttemp("heavy_machinery",got[3],reqMachinery,dialog.getTextPanel());



        //dialog.getTextPanel().addPara((int)got[0] + " / " + reqAICore + " AICores");
        //dialog.getTextPanel().addPara((int)got[1] + " / " + reqWorker + " SalvageRobots");
        //dialog.getTextPanel().addPara((int)got[2] + " / " + reqSupply + " Supply");
        //dialog.getTextPanel().addPara((int)got[3] + " / " + reqMachinery + " Heavy Machinery");
        /*

         */
        return true;
    }
    private void texttemp(String name,float numberOfItems,float requiredItems, TextPanelAPI text){
        CommoditySpecAPI spec = Global.getSector().getEconomy().getCommoditySpec(name);
        String displayName = spec.getName();

        TooltipMakerAPI tt = text.beginTooltip();
        TooltipMakerAPI iwt = tt.beginImageWithText(spec.getIconName(), 24);
        String numberStr = (int)numberOfItems + "";
        String numberStr2 = (int)requiredItems + "";
        LabelAPI label = iwt.addPara(numberStr + " / " + numberStr2 + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        tt.addImageWithText(0);
        text.addTooltip();
    }
}
