package data.scripts.starfarer.api.impl.campaign.rulemd;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.rules.MemKeys;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.impl.campaign.rulecmd.BaseCommandPlugin;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Misc.Token;

import java.awt.*;
import java.text.MessageFormat;
import java.util.Map;
import java.util.List;
public class my_mod_DilogOptionTest extends BaseCommandPlugin {
    //protected static final String WING = Misc.ucFirst(StringHelper.getString("fighterWingShort"));
    //@Override
    public boolean execute(String ruleId, InteractionDialogAPI dialog, List<Token> params, Map<String, MemoryAPI> memoryMap) {
        //dialog.getTextPanel() // TextPanelAPI
        if (dialog == null) return false;
        String arg = params.get(0).getString(memoryMap);
        switch (arg) {
            case "fleet":
                //printFleetInfo(dialog.getTextPanel());
                break;
            case "tools":
                //printMiningTools(dialog.getTextPanel());
                break;
            case "planet":
                dialog.getTextPanel().addParagraph("THIS IS OUR WORLD NOW! GO GO ATTACK CARS!");
                //printPlanetInfo(dialog.getInteractionTarget(), dialog.getTextPanel(), memoryMap);
                break;
            default:
                //printPlanetInfo(dialog.getInteractionTarget(), dialog.getTextPanel(), memoryMap);
        }

        dialog.getTextPanel().addParagraph("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAaaaaaaaaaaaaaaaaaaaaaaAAAAAAAAAA AAAAAAAAAAAAAAAAAAA");
        return true;
    }
}
