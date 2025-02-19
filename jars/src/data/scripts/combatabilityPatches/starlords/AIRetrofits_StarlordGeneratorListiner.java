package data.scripts.combatabilityPatches.starlords;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import data.scripts.AIWorldCode.AIRetrofits_ChangePeople;
import data.scripts.startupData.AIRetrofits_Constants_3;
import starlords.listeners.LordGeneratorListener_base;

public class AIRetrofits_StarlordGeneratorListiner extends LordGeneratorListener_base {
    @Override
    public void editLordPerson(PersonAPI lord, MarketAPI market) {
        super.editLordPerson(lord, market);
        if (!market.hasCondition(AIRetrofits_Constants_3.Market_Condition)) return;
        AIRetrofits_ChangePeople.changePerson(lord);
        lord.addTag(AIRetrofits_Constants_3.TAG_FORCE_AI_OFFICERS);
        lord.addTag(AIRetrofits_Constants_3.TAG_FORCE_AI_RETROFITS_P_BASE);
    }
}
