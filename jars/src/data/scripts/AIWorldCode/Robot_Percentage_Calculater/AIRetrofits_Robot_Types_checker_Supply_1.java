package data.scripts.AIWorldCode.Robot_Percentage_Calculater;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.industries.personalRobotForge.AIRetrofit_combatRobotManufactory;

public class AIRetrofits_Robot_Types_checker_Supply_1 extends AIRetrofits_Robot_Types_checker_Base{
    public AIRetrofits_Robot_Types_checker_Supply_1(String CalculaterID,String industry,String aICore,float powerMulti) {
        super(CalculaterID);
        this.industry = industry;
        this.aICore = aICore;
        this.powerMulti = powerMulti;
    }
    public AIRetrofits_Robot_Types_checker_Supply_1(String CalculaterID,String industry,String aICore) {
        super(CalculaterID);
        this.industry = industry;
        this.aICore = aICore;
    }
    public String industry;
    public String aICore;
    public float powerMulti=1;
    @Override
    public float getLocalSupply(MarketAPI market) {
        try {
            if (!market.hasIndustry(industry)) return 0f;
            String marketCore = market.getIndustry(industry).getAICoreId();
            if (marketCore == null) marketCore = "";
            if (marketCore.equals(aICore)){//market.getIndustry(industry).getAICoreId().equals(aICore)) {
                AIRetrofit_combatRobotManufactory a = (AIRetrofit_combatRobotManufactory) market.getIndustry(industry);
                float power = a.getCombatMulti();
                power = (power - 1) / power;//(DM-1)/DM
                return power * powerMulti;
            }
        }catch (Exception e){
            AIRetrofit_Log.loging("failed to get industry or relevant power. please report to alaricdragon, in regards to the AIRetrofits mod.",this,true);
        }
        return 0f;
    }
}
