package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_Robots extends crewReplacer_Crew{
        /*static float baseBonus = Global.getSettings().getFloat("AIRetrofits_BaseMulti");
        static float commandBonus = Global.getSettings().getFloat("AIRetrofits_CommandMulti");
        static float gammaBonus = Global.getSettings().getFloat("AIRetrofits_GammaMulti");
        static float betaBonus = Global.getSettings().getFloat("AIRetrofits_BetaMulti");
        static float alphaBonus = Global.getSettings().getFloat("AIRetrofits_AlphaMulti");
        static float omegaBonus = Global.getSettings().getFloat("AIRetrofits_OmegaMulti");
        final private static String[] AICores = {
                "AIretrofit_SubCommandNode",
                "gamma_core",
                "beta_core",
                "alpha_core",
                "omega_core",
        };*/

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
        /*
        if(cargo.getCommodityQuantity(AICores[4]) > 0){
            return Global.getSector().getEconomy().getCommoditySpec(AICores[4]).getName();
        }else if(cargo.getCommodityQuantity(AICores[3]) > 0){
            return Global.getSector().getEconomy().getCommoditySpec(AICores[3]).getName();
        }else if(cargo.getCommodityQuantity(AICores[2]) > 0){
            return Global.getSector().getEconomy().getCommoditySpec(AICores[2]).getName();
        }else if(cargo.getCommodityQuantity(AICores[1]) > 0){
            return Global.getSector().getEconomy().getCommoditySpec(AICores[1]).getName();
        }else if(cargo.getCommodityQuantity(AICores[0]) > 0){
            return Global.getSector().getEconomy().getCommoditySpec(AICores[0]).getName();
        }*/
        return AIRetrofits_Constants_3.robot_baseText;//"no AI-Core or SubCommandNode";
    }
    public float getCorePower(CargoAPI cargo){
        for(int a = 0; a < AIRetrofits_Constants_3.robot_AICores.length; a++){
            if(cargo.getCommodityQuantity(AIRetrofits_Constants_3.robot_AICores[a]) > 0){
                return AIRetrofits_Constants_3.robot_AICoreBonus[a];
            }
        }
        /*
        float multi = baseBonus;
        if(cargo.getCommodityQuantity(AICores[4]) > 0){
            multi = omegaBonus;
        }else if(cargo.getCommodityQuantity(AICores[3]) > 0){
            multi = alphaBonus;
        }else if(cargo.getCommodityQuantity(AICores[2]) > 0){
            multi = betaBonus;
        }else if(cargo.getCommodityQuantity(AICores[1]) > 0){
            multi = gammaBonus;
        }else if(cargo.getCommodityQuantity(AICores[0]) > 0){
            multi = commandBonus;
        }*/
        return AIRetrofits_Constants_3.robot_baseBonus;
    }
    }
