package data.scripts.notifications.support.shipyard;


import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;
import java.util.ArrayList;

public class AIRetrofit_UpgradeTypes {
    public int type;
    public ArrayList<AIRetrofit_UpgradedShips> upgrades = new ArrayList<AIRetrofit_UpgradedShips>();
    public ArrayList<String> markets = new ArrayList<String>();

    public AIRetrofit_UpgradeTypes(int type){
        this.type = type;
    }
    public void addLocation(String market, AIRetrofit_UpgradedShips ships){
        if(ships == null || ships.ships.size() == 0){return;}
        upgrades.add(ships);
        markets.add(market);
    }
    public float[] display(TooltipMakerAPI info){
        float pad = 5;
        float cost = 0;
        float bonusXP = 0;
        Color highlight = Misc.getHighlightColor();
        for(int a = 0; a < upgrades.size(); a++){
            boolean b = Global.getSector().getEconomy().getMarket(markets.get(a)).isPlayerOwned();
            String[] exstra = {Global.getSector().getEconomy().getMarket(markets.get(a)).getName()};
            String text = AIRetrofits_Constants_3.ASIC_NotificationMarket;
            info.addPara(text,pad,highlight,exstra);
            float[] temp = upgrades.get(a).display(info,type,b);
            cost += temp[0];
            bonusXP += temp[1];
        }
        return new float[]{cost,bonusXP};
    }
    public float[] getCost(){

        float cost = 0;
        float bonusXP = 0;
        for(int a = 0; a < upgrades.size(); a++){
            boolean b = Global.getSector().getEconomy().getMarket(markets.get(a)).isPlayerOwned();
            float[] temp = upgrades.get(a).getCost(b);
            cost += temp[0];
            bonusXP += temp[1];
        }
        return new float[]{cost,bonusXP};
    }
}
