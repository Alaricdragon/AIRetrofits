package data.scripts.AIWorldCode.Fleet.listiner;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.OfficerDataAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.AIWorldCode.AIRetrofits_ChangePeople;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.List;

public class AIRetrofit_FleetListener extends BaseCampaignEventListener {
    static private boolean ChangeCrew = AIRetrofits_Constants.fleetChange_ChangeCrew;
    public AIRetrofit_FleetListener(boolean permaRegister) {
        super(permaRegister);
    }
    @Override
    public void reportFleetSpawned(CampaignFleetAPI fleet){
        AIRetrofits_ChangePeople.changePeopleFleet(fleet);
    }
}
