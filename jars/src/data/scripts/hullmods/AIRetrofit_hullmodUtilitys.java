package data.scripts.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;

public class AIRetrofit_hullmodUtilitys {
    public static MutableShipStatsAPI getIndexShip(){
        try {
            MutableShipStatsAPI ship = Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy().get(0).getStats();
            return ship;
        }catch (Exception e){
            return null;
        }
    }
}
