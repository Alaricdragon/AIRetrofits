package data.scripts.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.listeners.ShowLootListener;

public class ShowLootListiner implements ShowLootListener {
    /// automaticly regesters this plugin.
    public ShowLootListiner() {
        if (!Global.getSector().getListenerManager().hasListenerOfClass(ShowLootListiner.class)){
            Global.getSector().getListenerManager().addListener(this, true);
        }
    }
    public void reportAboutToShowLootToPlayer(CargoAPI loot, InteractionDialogAPI dialog) {
        /*todo:
            1: create the CSV file. 4 fields.
                1: name of 'drop group'.
                2: type (group, or salvage entity) this is looking at.
                3: name of the 'linked' salvage entiry / drop group.
                4: the 'odds' for this type of drop. (basicly a multiplyer to the drop rate?)

         */

    }
}
