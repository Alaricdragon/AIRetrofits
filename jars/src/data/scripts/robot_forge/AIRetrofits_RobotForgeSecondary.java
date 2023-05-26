package data.scripts.robot_forge;


import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Items;
import data.scripts.AIRetrofit_Log;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.List;

public class AIRetrofits_RobotForgeSecondary {
    static float CorruptedMetalMultiplier = AIRetrofits_Constants.RobotForge_CorruptedMetalMultiplier;//Global.getSettings().getFloat("AIRetrofits_CorruptedMetal");
    static float PristineMetalMultiplier = AIRetrofits_Constants.RobotForge_PristineMetalMultiplier;//Global.getSettings().getFloat("AIRetrofits_PristineMetal");
    static float ForgePowerMulti = AIRetrofits_Constants.RobotForge_ForgePowerMulti;//Global.getSettings().getFloat("AIRetrofits_RobotForgePerCrewMulti");

    public static float iCalculateBonus(CampaignFleetAPI fleet) {
        try {
            float iBonus = getFleetsForgeModules(fleet);
            return iBonus + (Math.min(iBonus, getFleetsNanoforgePower(fleet)))/*+1*/;
        }catch (Exception e){
            AIRetrofit_Log.loging("failed to get the forge power in fleet. error: "+e,new AIRetrofits_RobotForgeSecondary().getClass(),true);
            return 0f;
        }
    }


    public static float getFleetsForgeModules(CampaignFleetAPI fleet){
        float iSalvageCoomer = 0f;
        List<FleetMemberAPI> playerFleetList = fleet.getFleetData().getMembersListCopy();
        for (FleetMemberAPI member : playerFleetList) {
            if (member.isMothballed()) continue;
            if (member.getVariant().hasHullMod("AIretrofit_AutomatedCrewReplacementDrones")) {//oldHERE gets the ID of the hullmode that lets one make things
                float MinCrew = member.getVariant().getHullSpec().getMinCrew();
                float MaxCrew = member.getVariant().getHullSpec().getMaxCrew();
                iSalvageCoomer += (MaxCrew - MinCrew);// * 0.01;//ForgePowerMulti;
                //stats.getDynamic().getMod(Stats.getSurveyCostReductionId(Commodities.CREW)).modifyFlat(id,(MaxCrew - MinCrew) / DronePerCrew);
                //ReplacedCrew = (MaxCrew - MinCrew) * 0.01;
                //iSalvageCoomer += hullsizemulti[bounus];
            }
        }
        return iSalvageCoomer * ForgePowerMulti;
    }
    public static float getFleetsNanoforgePower(CampaignFleetAPI fleet){
        float iCorrupted = fleet.getCargo().getQuantity(CargoAPI.CargoItemType.SPECIAL, new SpecialItemData(Items.CORRUPTED_NANOFORGE, null)) * CorruptedMetalMultiplier;
        float iPristine = fleet.getCargo().getQuantity(CargoAPI.CargoItemType.SPECIAL, new SpecialItemData(Items.PRISTINE_NANOFORGE, null)) * PristineMetalMultiplier;
        return iCorrupted + iPristine;
    }

}
