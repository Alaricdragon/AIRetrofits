package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_Robots extends crewReplacer_Crew{
    @Override
    public float getCrewPower(CargoAPI cargo) {
        float multi = getCorePower(cargo);
        return crewPower * multi;
    }
    public String getMaxCore(CargoAPI cargo){
        for(int a = 0; a < AIRetrofits_Constants_3.robot_AICores.length; a++){
            if(cargo.getCommodityQuantity(AIRetrofits_Constants_3.robot_AICores[a]) > 0){
                return Global.getSector().getEconomy().getCommoditySpec(AIRetrofits_Constants_3.robot_AICores[a]).getName();
            }
        }
        return AIRetrofits_Constants_3.robot_baseText;//"no AI-Core or SubCommandNode";
    }
    public float getCorePower(CargoAPI cargo){
        for(int a = 0; a < AIRetrofits_Constants_3.robot_AICores.length; a++){
            if(cargo.getCommodityQuantity(AIRetrofits_Constants_3.robot_AICores[a]) > 0){
                return AIRetrofits_Constants_3.robot_AICoreBonus[a];
            }
        }
        return AIRetrofits_Constants_3.robot_baseBonus;
    }
    }
