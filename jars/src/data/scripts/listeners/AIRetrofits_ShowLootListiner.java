package data.scripts.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.listeners.ShowLootListener;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity;
import com.fs.starfarer.api.util.Misc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.json.XMLTokener.entity;

@Deprecated
public class AIRetrofits_ShowLootListiner implements ShowLootListener {
    /// automaticly regesters this plugin.
    public AIRetrofits_ShowLootListiner() {
        if (!Global.getSector().getListenerManager().hasListenerOfClass(AIRetrofits_ShowLootListiner.class)){
            Global.getSector().getListenerManager().addListener(this, true);
        }
    }
    public void reportAboutToShowLootToPlayer(CargoAPI loot, InteractionDialogAPI dialog) {
        /*todo:
            1: create the CSV file. 4 fields.
                1: the 'id' of this link
                2: name of 'drop group'.
                3: type (group, salvage entity, group link (acts as a link, but links to a drop group instead)) this is looking at.
                4: name of the 'linked' salvage entiry / drop group / linked group
                5: the 'odds' for this type of drop. (basicly a multiplyer to the drop rate?)
            2: factors of CSV file:
                1: I can have multible lines for drop group and linked group. If so, all combanations will be selected.
                2: I can have multible lines for odds. if so, the odds should link up with the 'group group' NOT with the salvage target / group.
         */
        //my orignal attempt here was a modification of industreal evolutions 'SpecialItemDropsListener', and well it would work, its both unreqired in this case, and a bit to mush.
        //I might do something with it later to maybe market retrofits? Maybe I should merge market retrofits and crew replacer, call it a diffrent name...?
        //IF I merge it, it would be a way to add in additional salvage groups, something I thought was required, but no. I can just use tags and everything just workds....? Its like magic.
        //SectorEntityToken entity;
        //dialog.getInteractionTarget().addDropRandom();
    }
}
