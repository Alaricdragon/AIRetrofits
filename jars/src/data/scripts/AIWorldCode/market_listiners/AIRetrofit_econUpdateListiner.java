package data.scripts.AIWorldCode.market_listiners;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.EconomyAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.AIWorldCode.growth.AIRetrofit_MarketGrowthMods;
import data.scripts.startupData.AIRetrofits_Constants;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandLists;

import java.util.ArrayList;

public class AIRetrofit_econUpdateListiner implements EconomyAPI.EconomyUpdateListener {
    private static boolean can = AIRetrofits_Constants.Market_EnableMarketFetures;//Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
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
    private final static String defenceID_A = "something or other????";
    private final static String defenceDescription_A = "something or other????";
    private final static String defenceID_O = "something or other2????";
    private final static String defenceDescription_O = "something or other2????";
    private final static float defenceMulti_OD = 1f;
    private final static float defenceMulti_AD = 1f;
    public ArrayList<String> defencive_factionIDs;
    public ArrayList<Float> defencive_factionPower;
    public float getPowerTemp(MarketAPI market,String ID){
        AIRetrofits_Robot_Types_calculater_2.getType(ID).getGlobalOddsOfRobot(market);

        int typeID = -1;
        try {
            for (int a = 0; a < defencive_factionIDs.size(); a++) {
                if (market.getFactionId().equals(defencive_factionIDs.get(a))) {
                    typeID = a;
                    break;
                }
            }
        }catch (Exception e){
            return 0f;
        }
        float global;
        if (typeID != -1){
            global = defencive_factionPower.get(typeID) + AIRetrofits_Robot_Types_calculater_2.getType(ID).getLocalSupply(market);
        }else{
            defencive_factionPower.add(AIRetrofits_Robot_Types_calculater_2.getType(ID).getGlobalOddsOfRobot(market));
            defencive_factionIDs.add(market.getFactionId());
            global = defencive_factionPower.get(defencive_factionPower.size() - 1);
        }
        return Math.max(1,Math.min(0,global + AIRetrofits_Robot_Types_calculater_2.getType(ID).getLocalSupply(market)));
    }
    private void applyDefenceBonuses(MarketAPI market){
        float OD = Math.min(1,getPowerTemp(market,AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence));//AIRetrofits_Robot_Types_calculater_2.getType(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence).getOddsOfRobot(market));
        float AD = Math.min(1-OD,getPowerTemp(market,AIRetrofits_Constants.RobotTypeCalculatorID_CombatT2_Defence));//AIRetrofits_Robot_Types_calculater_2.getType(AIRetrofits_Constants.RobotTypeCalculatorID_CombatT1_Defence).getOddsOfRobot(market));
        //if (AD > 1-OD) AD = 1-OD;
        if (OD <= 0){
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).unmodifyMult(defenceDescription_O);
        }else{
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMult(defenceID_O,defenceMulti_OD*(1+OD),defenceDescription_O);
        }
        if (AD <= 0){
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).unmodifyMult(defenceDescription_A);
        }else{
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMult(defenceID_A,defenceMulti_AD*(1+AD),defenceDescription_A);
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