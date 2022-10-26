package data.scripts.AIWorldCode.submarkets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;

public class AIRetrofit_Shipyard extends BaseSubmarketPlugin {
    /*
    put the upgrade code in AIRetrofit_MarketListener.
     */
    public float time = 0;
    public float power = 0;
    @Override
    public void advance(float amount){
        //cargo.getFleetData().getMembersListCopy();
    }
    @Override
    public boolean isParticipatesInEconomy() {
        return false;
    }

    @Override
    public float getTariff() {
        return 0f;
    }

    @Override
    public boolean isFreeTransfer() {
        return true;
    }
    @Override
    public String getBuyVerb() {
        return "take";
    }

    @Override
    public String getSellVerb() {
        return "leave";
    }
    @Override
    public boolean showInCargoScreen() {
        return false;
    }
    @Override
    public boolean	isIllegalOnSubmarket(java.lang.String commodityId, SubmarketPlugin.TransferAction action){
        return false;
    }
    @Override
    public 	boolean isMilitaryMarket(){
        return true;
    }
}
