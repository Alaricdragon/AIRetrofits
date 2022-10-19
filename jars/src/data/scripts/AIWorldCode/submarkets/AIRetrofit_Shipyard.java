package data.scripts.AIWorldCode.submarkets;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SubmarketPlugin;
import com.fs.starfarer.api.impl.campaign.submarkets.BaseSubmarketPlugin;

public class AIRetrofit_Shipyard extends BaseSubmarketPlugin {
    /*
    going to put the action data in AIRetrofit_MarketListenr.
    because that can report at the end of every momth, so i can have ships be added quicker?
    maybe i should report the amount of time that has passed instead, and upgrade the ships one at a time?
    like have an 'ponits' data ponit, that is reset if the market is found empty, so i can have more control over the speed that ships upgrade?
    mmmmm...
     */
    private float time = 0;
    @Override
    public void advance(float amount){
        takeAction();
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
    //this might be from an industrial evolution thing
    /*@Override
    public boolean isEnabled(CoreUIAPI ui) {
        return true;
    }*/
    @Override
    public boolean	isIllegalOnSubmarket(java.lang.String commodityId, SubmarketPlugin.TransferAction action){
        return false;
    }
    @Override
    public 	boolean isMilitaryMarket(){
        return true;
    }
    private void takeAction(){
    }
}
