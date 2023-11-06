package data.scripts.notifications.support.shipyard;


import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.ArrayList;

public class AIRetrofit_UpgradedShip {
    public FleetMemberAPI ship;
    public boolean bonus;
    public int size;
    static float storyGain = Global.getSettings().getFloat("AIRetrofitShipyard_storyPointsPerSMod");
    public AIRetrofit_UpgradedShip(FleetMemberAPI ship, int size, boolean bonus){
        this.ship = ship;
        this.bonus = bonus;
        this.size = size;
    }
    public void addShipToFleet(ArrayList<FleetMemberAPI> fleet){
        fleet.add(ship);
        //cargo.addMothballedShip();//(ship);
    }
    public float getCost(){
        float[] costTemp = AIRetrofits_Constants.ASIC_creditsPerShip;
        return costTemp[size];
    }
    public float getBonusXP(){
        if(bonus){
            float xp = 1 - Misc.getBuildInBonusXP(Misc.getMod(AIRetrofits_Constants.Hullmod_AIRetrofit),ship.getHullSpec().getHullSize());
            return xp * storyGain;//Misc.getBonusXPForScuttling(ship)[0] * getBonusXPForScuttling(ship)[1];
        }
        return 0f;//AIRetrofits_Constants.ASIC_bonusXPForRemoveSMod;
    }
    public void display(TooltipMakerAPI info, boolean playerOwned){
    }
}
