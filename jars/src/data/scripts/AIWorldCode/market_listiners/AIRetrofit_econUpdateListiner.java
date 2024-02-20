package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.AIWorldCode.growth.AIRetrofit_MarketGrowthMods;
import data.scripts.startupData.AIRetrofits_Constants;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandLists;

import java.util.ArrayList;

public class AIRetrofit_econUpdateListiner implements EconomyAPI.EconomyUpdateListener {
    private static boolean can = AIRetrofits_Constants.Market_EnableMarketFetures;//Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    private static final String className = "AIRetrofit_econUpdateListiner";
    static private boolean Override = true;//when true, act as normal.
    private void applyMarketData() {
        if(can && Override) {
            defencive_factionIDs = new ArrayList<>();
            defencive_factionPower = new ArrayList<>();
            for (MarketAPI market : Global.getSector().getEconomy().getMarketsCopy()) {
                market = Global.getSector().getEconomy().getMarket(market.getId());
                //crewReplacer_SupplyDemandLists.getRuleSet(RuleSet).applyMarket(market, false);
                AIRetrofit_MarketGrowthMods.applyData(market,"");
                applyDefenceBonuses(market);
            }
        }
    }
    private final static boolean robot_type_logs = AIRetrofits_Robot_Types_calculater_2.robot_type_logs;
    private final static String defenceID_A = "AIRetrofits_T1_CombatDroneSupplyBonus";
    private final static String defenceDescription_A = "%s% of robots at this colony are advanced combat robots";
    private final static String defenceID_O = "AIRetrofits_T2_CombatDroneSupplyBonus";
    private final static String defenceDescription_O = "%s% of robots at this colony are omega combat robots";
    private final static float defenceMulti_OD = Global.getSettings().getFloat("AIRetrofit_MaxGroundDefenceBonusFromT2Bots");//1f;
    private final static float defenceMulti_AD = Global.getSettings().getFloat("AIRetrofit_MaxGroundDefenceBonusFromT1Bots");//0.5f;
    public String[] defenciveType = {AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence,AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence};
    public ArrayList<String> defencive_factionIDs = new ArrayList<>();
    public ArrayList<Float> defencive_factionPower = new ArrayList<>();
    public ArrayList<String> defencive_factionID2s = new ArrayList<>();
    public ArrayList<Float> defencive_factionPowe2r = new ArrayList<>();
    public float getPowerTemp(MarketAPI market,String ID){
        AIRetrofit_Log.loging("HERE HERE HERE. runing test for ID of "+ID,this,robot_type_logs);
        AIRetrofit_Log.loging("test",this,robot_type_logs);
        AIRetrofit_Log.push();
        AIRetrofits_Robot_Types_calculater_2.getType(ID).getOddsOfRobot(market);
        AIRetrofit_Log.pop();
        AIRetrofit_Log.loging("test over. starting acsual",this,robot_type_logs);
        int typeID = -1;
        int RobotType = -1;
        ArrayList<String> factionID;
        ArrayList<Float> factionPower;
        try {
            for (int a = 0; a < defenciveType.length; a++){
                AIRetrofit_Log.loging("running check for type of "+defenciveType[a],this,robot_type_logs);
                if (ID.equals(defenciveType[a])){
                    RobotType = a;
                    AIRetrofit_Log.loging("got check for type of "+defenciveType[a],this,robot_type_logs);
                    break;
                }
            }
            switch (RobotType){
                case 0:
                    factionID = defencive_factionIDs;
                    factionPower = defencive_factionPower;
                    break;
                case 1:
                    factionID = defencive_factionID2s;
                    factionPower = defencive_factionPowe2r;
                    break;
                default:
                    return 0f;
            }
            for (int a = 0; a < factionID.size(); a++) {
                if (market.getFactionId().equals(factionID.get(a))) {
                    typeID = a;
                    break;
                }
            }
        }catch (Exception e){
            AIRetrofit_Log.loging("FAILED TO GET THE REQUIED DATA. exseption type: "+e.getLocalizedMessage(),this,robot_type_logs);
            return 0f;
        }
        float global=0;
        if (typeID != -1){
            global = factionPower.get(typeID);// + AIRetrofits_Robot_Types_calculater_2.getType(ID).getLocalSupply(market);
            AIRetrofit_Log.loging("get global stored data of: "+global,this,robot_type_logs);
        }else{
            factionPower.add(AIRetrofits_Robot_Types_calculater_2.getType(ID).getGlobalOddsOfRobot(market));
            factionID.add(market.getFactionId());
            global = factionPower.get(factionPower.size() - 1);
            AIRetrofit_Log.loging("stored global stored data of: "+global,this,robot_type_logs);
        }
        AIRetrofit_Log.loging("global: "+global,this,robot_type_logs);
        global += AIRetrofits_Robot_Types_calculater_2.getType(ID).getLocalSupply(market);
        AIRetrofit_Log.loging("total (before min ,max): "+global,this,robot_type_logs);
        return Math.min(1,Math.max(0,global));
    }
    private void applyDefenceBonuses(MarketAPI market){
        defencive_factionIDs = new ArrayList<>();
        defencive_factionPower = new ArrayList<>();
        defencive_factionID2s = new ArrayList<>();
        defencive_factionPowe2r = new ArrayList<>();
        float OD = getPowerTemp(market,AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence);//AIRetrofits_Robot_Types_calculater_2.getType(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence).getOddsOfRobot(market));
        float AD = getPowerTemp(market,AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence);//AIRetrofits_Robot_Types_calculater_2.getType(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence).getOddsOfRobot(market));
        //AIRetrofit_Log.loging("AD before min is: "+AD+" for market: "+market.getName(),this,robot_type_logs);
        //AIRetrofit_Log.loging("OD before min is: "+OD+" for market: "+market.getName(),this,robot_type_logs);
        AD = Math.min(1-OD,AD);
        //AIRetrofit_Log.loging("AD after min is: "+AD+" for market: "+market.getName(),this,robot_type_logs);
        if (OD <= 0.01f){
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).unmodifyMult(defenceID_O);
        }else{
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMult(defenceID_O,(defenceMulti_OD*OD)+1, AIRetrofits_StringHelper.getString(className,"applyDefenceBonuses",0,""+(int)(OD*100)));
        }
        if (AD <= 0.01f){
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).unmodifyMult(defenceID_A);
        }else{
            //AIRetrofit_Log.loging("market power multi is: "+AD+" for market: "+market.getName()+" for a total of "+defenceMulti_AD*(AD)+" defence multi",this,robot_type_logs);
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMult(defenceID_A,(defenceMulti_AD*AD)+1,AIRetrofits_StringHelper.getString(className,"applyDefenceBonuses",1,""+(int)(AD*100)));
        }
    }
    @Override
    public void commodityUpdated(String commodityId) {

    }

    @Override
    public void economyUpdated() {
        applyMarketData();
    }
    @Override
    public boolean isEconomyListenerExpired() {
        return false;
    }
}