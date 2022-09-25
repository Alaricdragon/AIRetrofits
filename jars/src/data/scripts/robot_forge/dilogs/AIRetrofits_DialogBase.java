package data.scripts.robot_forge.dilogs;

import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.InteractionDialogPlugin;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;

import java.util.Map;

public class AIRetrofits_DialogBase {
    public void init(InteractionDialogAPI dialog) {

    }

    public void optionSelected(String optionText, Object optionData) {

    }

    public void optionMousedOver(String optionText, Object optionData) {

    }

    public void advance(float amount) {

    }

    public void backFromEngagement(EngagementResultAPI battleResult) {

    }

    public Object getContext() {
        return null;
    }

    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }
}
