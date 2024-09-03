package data.scripts.AIWorldCode.Fleet.listiner;

import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import data.scripts.AIWorldCode.AIRetrofits_ChangePeople;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_FleetListener extends BaseCampaignEventListener {
    static private boolean ChangeCrew = AIRetrofits_Constants_3.fleetChange_ChangeCrew;
    public AIRetrofit_FleetListener(boolean permaRegister) {
        super(permaRegister);
    }
    @Override
    public void reportFleetSpawned(CampaignFleetAPI fleet){
        AIRetrofits_ChangePeople.changePeopleFleet(fleet);
    }
}
