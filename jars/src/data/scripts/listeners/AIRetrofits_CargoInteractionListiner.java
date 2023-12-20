package data.scripts.listeners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.PlayerMarketTransaction;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.listeners.CargoScreenListener;

public class AIRetrofits_CargoInteractionListiner  implements CargoScreenListener {
    public static void addListener() {
        //Global.getSector().getListenerManager().addListener(new data.scripts.shadowCrew.AIRetrofits_CargoInteractionListiner(), true);
    }

    @Override
    public void reportCargoScreenOpened() {

    }

    @Override
    public void reportPlayerLeftCargoPods(SectorEntityToken entity) {

    }

    @Override
    public void reportPlayerNonMarketTransaction(PlayerMarketTransaction transaction, InteractionDialogAPI dialog) {

    }

    @Override
    public void reportSubmarketOpened(SubmarketAPI submarket) {

    }
}
