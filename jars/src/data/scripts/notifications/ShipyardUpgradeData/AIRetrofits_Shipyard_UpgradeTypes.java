package data.scripts.notifications.ShipyardUpgradeData;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.ArrayList;

public class AIRetrofits_Shipyard_UpgradeTypes {
    int type;
    ArrayList<AIRetrofit_Shipyard_UpgradeShips> upgrades = new ArrayList<AIRetrofit_Shipyard_UpgradeShips>();
    ArrayList<String> markets = new ArrayList<String>();

    AIRetrofits_Shipyard_UpgradeTypes(int type){
        this.type = type;
    }
    public void addLocation(String market,AIRetrofit_Shipyard_UpgradeShips ships){
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
            String text = AIRetrofits_Constants.ASIC_NotificationMarket;
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
