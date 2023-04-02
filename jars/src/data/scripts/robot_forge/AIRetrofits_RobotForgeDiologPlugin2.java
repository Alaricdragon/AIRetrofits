package data.scripts.robot_forge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import data.scripts.robot_forge.dilogs.AIRetrofit_Dialog_ForgeItemAndSelection;
import data.scripts.robot_forge.dilogs.AIRetrofits_DialogBase;

import java.util.Map;

public class AIRetrofits_RobotForgeDiologPlugin2 implements InteractionDialogPlugin {
    public static AIRetrofits_DialogBase Dialog;
    @Override
    public void init(InteractionDialogAPI dialog) {
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        Dialog = new AIRetrofit_Dialog_ForgeItemAndSelection(fleet);
        Dialog.init(dialog);
    }

    @Override
    public void optionSelected(String optionText, Object optionData) {
        Dialog.optionSelected(optionText,optionData);
    }

    @Override
    public void optionMousedOver(String optionText, Object optionData) {
        Dialog.optionMousedOver(optionText,optionData);
    }

    @Override
    public void advance(float amount) {
        Dialog.advance(amount);
    }

    @Override
    public void backFromEngagement(EngagementResultAPI battleResult) {
        Dialog.backFromEngagement(battleResult);
    }

    @Override
    public Object getContext() {
        return Dialog.getContext();
    }

    @Override
    public Map<String, MemoryAPI> getMemoryMap() {
        return Dialog.getMemoryMap();
    }
}
