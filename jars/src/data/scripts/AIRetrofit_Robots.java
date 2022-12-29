package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
    public class AIRetrofit_Robots extends crewReplacer_Crew{
        static float baseBonus = Global.getSettings().getFloat("AIRetrofits_BaseMulti");
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
        };
        @Override
        public float getCrewPowerInFleet(CampaignFleetAPI fleet) {
            return getCrewInFleet(fleet) * getCrewPower(fleet);//super.getCrewPowerInFleet(fleet);
        }
        public String getMaxCore(CampaignFleetAPI fleet){
            if(fleet.getCargo().getCommodityQuantity(AICores[4]) > 0){
                return Global.getSector().getEconomy().getCommoditySpec(AICores[4]).getName();
            }else if(fleet.getCargo().getCommodityQuantity(AICores[3]) > 0){
                return Global.getSector().getEconomy().getCommoditySpec(AICores[3]).getName();
            }else if(fleet.getCargo().getCommodityQuantity(AICores[2]) > 0){
                return Global.getSector().getEconomy().getCommoditySpec(AICores[2]).getName();
            }else if(fleet.getCargo().getCommodityQuantity(AICores[1]) > 0){
                return Global.getSector().getEconomy().getCommoditySpec(AICores[1]).getName();
            }else if(fleet.getCargo().getCommodityQuantity(AICores[0]) > 0){
                return Global.getSector().getEconomy().getCommoditySpec(AICores[0]).getName();
            }
            return "no AI-Core or SubCommandNode's";
        }
        public float getCorePower(CampaignFleetAPI fleet){
            float multi = baseBonus;
            if(fleet.getCargo().getCommodityQuantity(AICores[4]) > 0){
                multi = omegaBonus;
            }else if(fleet.getCargo().getCommodityQuantity(AICores[3]) > 0){
                multi = alphaBonus;
            }else if(fleet.getCargo().getCommodityQuantity(AICores[2]) > 0){
                multi = betaBonus;
            }else if(fleet.getCargo().getCommodityQuantity(AICores[1]) > 0){
                multi = gammaBonus;
            }else if(fleet.getCargo().getCommodityQuantity(AICores[0]) > 0){
                multi = commandBonus;
            }
            return multi;
        }
        public float getCrewPower(CampaignFleetAPI fleet){
            float multi = getCorePower(fleet);
            return crewPower * multi;
        }
    }
