package data.scripts.notifications.support.shipyard;


import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;
import java.util.ArrayList;

import static data.scripts.startupData.AIRetrofits_Constants_3.ASIC_hullmods;

public class AIRetrofit_UpgradedShips {
    public int type;
    public ArrayList<AIRetrofit_UpgradedShip> ships = new ArrayList<>();
    public AIRetrofit_UpgradedShips(int type){
        this.type = type;
    }
    public void addShip(FleetMemberAPI ship, int size){
        addShip(ship,size,false);
    }
    public void addShip(FleetMemberAPI ship, int size,boolean bonus){
        ships.add(new AIRetrofit_UpgradedShip(ship,size,bonus));
    }
    public float[] display(TooltipMakerAPI info, int type, boolean playerOwned){
        if(ships.size() == 0){
            return new float[]{0f,0f};
        }
        float pad = 5;
        Color highlight = Misc.getHighlightColor();

        //CargoAPI cargo = new CargoData(false);
        //FleetDataAPI fleet = new FleetData("production","production2");
        ArrayList<FleetMemberAPI> fleet = new ArrayList<>();
        String[] exstra = {"" + Global.getSettings().getHullModSpec(ASIC_hullmods[type]).getDisplayName()};
        String text = AIRetrofits_Constants_3.ASIC_NotificationType;
        info.addPara(text,pad,highlight,exstra);
        float cost = 0;
        float bonusXP = 0;
        for(int a = 0; a < ships.size(); a++){
            AIRetrofit_UpgradedShip ship = ships.get(a);
            ship.addShipToFleet(fleet);
            if(!playerOwned) {
                cost += ship.getCost();
            }
            bonusXP += ship.getBonusXP();

        }
        info.showShips(fleet, 20, true, 5);
        return new float[]{cost,bonusXP};
    }
    public float[] getCost(boolean playerOwned){

        if(ships.size() == 0){
            return new float[]{0f,0f};
        }
        float pad = 5;
        Color highlight = Misc.getHighlightColor();

        //CargoAPI cargo = new CargoData(false);
        //FleetDataAPI fleet = new FleetData("production","production2");
        ArrayList<FleetMemberAPI> fleet = new ArrayList<>();
        float cost = 0;
        float bonusXP = 0;
        for(int a = 0; a < ships.size(); a++){
            AIRetrofit_UpgradedShip ship = ships.get(a);
            ship.addShipToFleet(fleet);
            if(!playerOwned) {
                cost += ship.getCost();
            }
            bonusXP += ship.getBonusXP();

        }
        return new float[]{cost,bonusXP};
    }
}
