package data.scripts.AIWorldCode.Fleet.listiner;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.characters.FullName;
import com.fs.starfarer.api.characters.OfficerDataAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
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
        if(setDataLists.fleetMod(fleet)){
            for(int a = 0; a < fleet.getFleetData().getMembersInPriorityOrder().size(); a++){
                if( fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain().getNameString().length() != 0) {
                    fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain().setPortraitSprite(setDataLists.getRandom(2));
                    fleet.getFleetData().getMembersInPriorityOrder().get(a).getCaptain().setName(new FullName(setDataLists.getRandom(0), setDataLists.getRandom(1), FullName.Gender.ANY));
                    swapFleetCrew(fleet);
                }
            }
        }
    }
    String[] itemIn = AIRetrofits_Constants.fleetChange_itemIn;
    String[] itemOut = AIRetrofits_Constants.fleetChange_itemOut;
    private void swapFleetCrew(CampaignFleetAPI fleet){
        if(!ChangeCrew){
            return;
        }
        for(int a = 0; a < itemIn.length; a++) {
            float in = fleet.getCargo().getCommodityQuantity(itemIn[a]);
            fleet.getCargo().removeCommodity(itemIn[a],in);
            float pi = Global.getSector().getEconomy().getCommoditySpec(itemIn[a]).getBasePrice();
            float po = Global.getSector().getEconomy().getCommoditySpec(itemOut[a]).getBasePrice();
            in = in*(pi/po);
            fleet.getCargo().addCommodity(itemOut[a],in);
        }
    }
}
