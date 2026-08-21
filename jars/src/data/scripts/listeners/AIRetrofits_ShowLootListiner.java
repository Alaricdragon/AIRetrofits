package data.scripts.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.listeners.ShowLootListener;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;
import com.fs.starfarer.api.impl.campaign.procgen.themes.SalvageEntityGeneratorOld;
import com.fs.starfarer.api.impl.campaign.rulecmd.salvage.SalvageEntity;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofit_Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.json.XMLTokener.entity;

public class AIRetrofits_ShowLootListiner implements ShowLootListener {
    /// automaticly regesters this plugin.
    public AIRetrofits_ShowLootListiner() {
        AIRetrofit_Log.loging("HERE: attempting to add show loot listiner...",this,true);
        if (!Global.getSector().getListenerManager().hasListenerOfClass(AIRetrofits_ShowLootListiner.class)){
            Global.getSector().getListenerManager().addListener(this, true);
            AIRetrofit_Log.loging("listiner added",this,true);
        }
    }
    /*
        (note: getting data from a fleet DID NOTHING)
        (data from probe)
                [nothing from set 1. does it even matter really?]
                - Got drop random 2 as:  group: ai_cores1, chances 3, maxChances -1, value -1, valueMulti 1.0
                - Got drop random 2 as:  group: extended, chances 1, maxChances -1, value 3000, valueMulti 1.0
                - Got drop value 2 as:  group: ai_cores1, chances 3, maxChances -1, value -1, valueMulti 1.0
                - Got drop value 2 as:  group: extended, chances 1, maxChances -1, value 3000, valueMulti 1.0
        (data from probes salvage field)
          - got 0 items in droprandom
          - attempting to log data....
          - got 0 items in dropvalue
          - Got drop random 2 as:  group: ai_cores1, chances 3, maxChances -1, value -1, valueMulti 0.25
          - Got drop random 2 as:  group: extended, chances 1, maxChances -1, value 3000, valueMulti 0.25
          - Got drop value 2 as:  group: ai_cores1, chances 3, maxChances -1, value -1, valueMulti 0.25
          - Got drop value 2 as:  group: extended, chances 1, maxChances -1, value 3000, valueMulti 0.25

        (mining station main)
          - Got drop random 2 as:  group: mining_station, chances 5, maxChances -1, value 3000, valueMulti 1.0
          - Got drop random 2 as:  group: mining_bulk, chances 1, maxChances -1, value 150000, valueMulti 1.0
          - Got drop random 2 as:  group: ai_cores2, chances 5, maxChances -1, value -1, valueMulti 1.0
          - Got drop random 2 as:  group: ai_cores3, chances 2, maxChances -1, value -1, valueMulti 1.0
          - Got drop random 2 as:  group: any_hullmod_medium, chances 3, maxChances -1, value -1, valueMulti 1.0
          - Got drop random 2 as:  group: rare_tech_low, chances 4, maxChances -1, value -1, valueMulti 1.0
          - Got drop random 2 as:  group: package_bp, chances 2, maxChances -1, value -1, valueMulti 1.0
          - Got drop value 2 as:  group: mining_station, chances 5, maxChances -1, value 3000, valueMulti 1.0
          - Got drop value 2 as:  group: mining_bulk, chances 1, maxChances -1, value 150000, valueMulti 1.0
          - Got drop value 2 as:  group: ai_cores2, chances 5, maxChances -1, value -1, valueMulti 1.0
          - Got drop value 2 as:  group: ai_cores3, chances 2, maxChances -1, value -1, valueMulti 1.0
          - Got drop value 2 as:  group: any_hullmod_medium, chances 3, maxChances -1, value -1, valueMulti 1.0
          - Got drop value 2 as:  group: rare_tech_low, chances 4, maxChances -1, value -1, valueMulti 1.0
          - Got drop value 2 as:  group: package_bp, chances 2, maxChances -1, value -1, valueMulti 1.0
        (mining station field)
          - Got drop random 2 as:  group: mining_station, chances 5, maxChances -1, value 3000, valueMulti 0.25
          - Got drop random 2 as:  group: mining_bulk, chances 1, maxChances -1, value 150000, valueMulti 0.25
          - Got drop random 2 as:  group: ai_cores2, chances 5, maxChances -1, value -1, valueMulti 0.25
          - Got drop random 2 as:  group: ai_cores3, chances 2, maxChances -1, value -1, valueMulti 0.25
          - Got drop random 2 as:  group: any_hullmod_medium, chances 3, maxChances -1, value -1, valueMulti 0.25
          - Got drop random 2 as:  group: rare_tech_low, chances 4, maxChances -1, value -1, valueMulti 0.25
          - Got drop random 2 as:  group: package_bp, chances 2, maxChances -1, value -1, valueMulti 0.25
          - Got drop value 2 as:  group: mining_station, chances 5, maxChances -1, value 3000, valueMulti 0.25
          - Got drop value 2 as:  group: mining_bulk, chances 1, maxChances -1, value 150000, valueMulti 0.25
          - Got drop value 2 as:  group: ai_cores2, chances 5, maxChances -1, value -1, valueMulti 0.25
          - Got drop value 2 as:  group: ai_cores3, chances 2, maxChances -1, value -1, valueMulti 0.25
          - Got drop value 2 as:  group: any_hullmod_medium, chances 3, maxChances -1, value -1, valueMulti 0.25
          - Got drop value 2 as:  group: rare_tech_low, chances 4, maxChances -1, value -1, valueMulti 0.25
          - Got drop value 2 as:  group: package_bp, chances 2, maxChances -1, value -1, valueMulti 0.25

    CONCLUSION:
        data from 'drop value' and 'drop random' is never different. at least in my very limited testing.
        debree fields cary there main ships drop data.

        max chances  are set to -1 when something has already added data to the look.
        value is keept static. if -1, item has no value (so its droping random numbers of items, not values)
        if value -1, item is a 'random' drop.
        chances is the number of chances this data would have had, normally.
        so in concllsion:
        value -1 = random drop.
        chances are chances.
        value multi is for debre fields. make sure my own code respects that.
        -
        lastly, the enteraction target is not maintained between the primary action target and its debree field.
        so any time I add in additional things to probes, said probe will not get the data added?
        ...
        THEORY: [in regards to item drops]
        please note: without replacing 'high value' items like AI-cores or colony items, I cant just make loot appear.
        but I have a maybe theory:
            1: add the loot to the none debree filed
        ...
        what the fuck am I doing? for item drops for certen items, cant I just... simply... like...
        ok, so for industrial evolution arsenal stations, they have a drop group just for that.
        so let us ask the all important question:
questions:
    1: in the salvage entity.csv, can I add drop_random or drop_value to a giving entity from my own mod? (for example, overriding 'derelict_probe' to drop_value more dropgroups from the drop_groups.csv) or would that mess up things like mod combatability?
    2:
     */
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
        SectorEntityToken entity = dialog.getInteractionTarget();
        AIRetrofit_Log.loging("entity type as: "+entity.getCustomEntityType(),this,true);
        List<SalvageEntityGenDataSpec.DropData> dropData = getDropDataFromEntity(entity);
    }
    private List<SalvageEntityGenDataSpec.DropData> getDropDataFromEntity(SectorEntityToken entity) {
        List<SalvageEntityGenDataSpec.DropData> dropData = new ArrayList<>();

        //first get drops assigned directly to entity
        if (entity.getDropRandom() != null) {
            AIRetrofit_Log.loging("got "+entity.getDropRandom().size()+" items in droprandom",this,true);
            dropData.addAll(entity.getDropRandom());
            logDrops(entity.getDropRandom(),"Got drop random as: ");
        }

        if (entity.getDropValue() != null) {
            AIRetrofit_Log.loging("got "+entity.getDropRandom().size()+" items in dropvalue",this,true);
            dropData.addAll(entity.getDropValue());
            logDrops(entity.getDropRandom(),"Got drop value as: ");
        }

        //then try to get spec from entity and the spec's drops
        String specId = entity.getCustomEntityType();
        if (specId == null || entity.getMemoryWithoutUpdate().contains(MemFlags.SALVAGE_SPEC_ID_OVERRIDE)) {
            specId = entity.getMemoryWithoutUpdate().getString(MemFlags.SALVAGE_SPEC_ID_OVERRIDE);
        }

        if (specId != null
                && SalvageEntityGeneratorOld.hasSalvageSpec(specId)) {
            SalvageEntityGenDataSpec spec = SalvageEntityGeneratorOld.getSalvageSpec(specId);

            //get drop randoms from that spec
            if (spec != null && spec.getDropRandom() != null) {
                dropData.addAll(spec.getDropRandom());
                logDrops(spec.getDropRandom(),"Got drop random 2 as: ");
            }

            if (spec != null && spec.getDropValue() != null) {
                dropData.addAll(spec.getDropValue());
                logDrops(spec.getDropRandom(),"Got drop value 2 as: ");
            }
        }

        return dropData;
    }

    private void logDrops(List<SalvageEntityGenDataSpec.DropData> drops,String log){
        AIRetrofit_Log.loging("attempting to log data....",this,true);
        for (SalvageEntityGenDataSpec.DropData a : drops){
            AIRetrofit_Log.loging(log+" group: "+a.group+", chances "+a.chances+", maxChances "+a.maxChances+", value "+a.value+", valueMulti "+a.valueMult,this,true);
        }
    }
}
