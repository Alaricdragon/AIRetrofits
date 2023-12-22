package data.scripts.AIWorldCode.Robot_Percentage_Calculater;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.AIRetrofit_Log;

import java.util.ArrayList;

public class AIRetrofits_Robot_Types_calculater_2 {
    public static ArrayList<AIRetrofits_Robot_Types_calculater_2> masterList = new ArrayList<>();
    public static AIRetrofits_Robot_Types_calculater_2 getType(String ID){
        for (AIRetrofits_Robot_Types_calculater_2 a : masterList){
            if (a.ID.equals(ID)){
                return a;
            }
        }
        return null;
    }

    public String ID;
    protected ArrayList<AIRetrofits_Robot_Types_checker_Base> addons = new ArrayList();
    public AIRetrofits_Robot_Types_calculater_2(String ID){
        this.ID = ID;
        masterList.add(this);
    }
    public void addChecker(AIRetrofits_Robot_Types_checker_Base checker){
        addons.add(checker);
    }
    public float getOddsOfRobot(MarketAPI market){
        float odds = getSupply(market) / Math.max(getDemand(market),1);
        AIRetrofit_Log.loging("odds of robot: "+this.ID+" are "+odds,this,true);
        return odds + getLocalSupply(market);
    }
    public float getDemand(MarketAPI market){
        float power = 0f;
        for (AIRetrofits_Robot_Types_checker_Base a : this.addons){
            power += a.getDemand(market);
        }
        AIRetrofit_Log.loging("demand of (global) robot: "+this.ID+" are "+power,this,true);
        return power;
    }
    public float getSupply(MarketAPI market){
        float power = 0f;
        for (AIRetrofits_Robot_Types_checker_Base a : this.addons){
            power += a.getSupply(market);
        }
        AIRetrofit_Log.loging("supply of (global) robot: "+this.ID+" are "+power,this,true);
        return power;
    }
    public float getLocalSupply(MarketAPI market){
        float power = 0f;
        for (AIRetrofits_Robot_Types_checker_Base a : this.addons){
            power += a.getLocalSupply(market);
        }
        AIRetrofit_Log.loging("supply of (local) robot: "+this.ID+" are "+power,this,true);
        return power;
    }

}
