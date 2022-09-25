package data.scripts.robot_forge;


import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Items;

import java.util.List;

public class AIRetrofits_RobotForgeSecondary {
    static float CorruptedMetalMultiplier = Global.getSettings().getFloat("AIRetrofits_CorruptedMetal");
    static float PristineMetalMultiplier = Global.getSettings().getFloat("AIRetrofits_PristineMetal");
    //static float SalvageModifier = Global.getSettings().getFloat("AIRetrofits_SalvageGantry");
    static float ForgePowerMulti = Global.getSettings().getFloat("AIRetrofits_RobotForgePerCrewMulti");

    public static float iCalculateBonus(CampaignFleetAPI fleet) {
            //oldHERE. mush to do
            //float iCorrupted = fleet.getCargo().getQuantity(CargoAPI.CargoItemType.SPECIAL, new SpecialItemData(Items.CORRUPTED_NANOFORGE, null));
            //float iPristine = fleet.getCargo().getQuantity(CargoAPI.CargoItemType.SPECIAL, new SpecialItemData(Items.PRISTINE_NANOFORGE, null));
            //float iSalvageCoomer = 0f;
            List<FleetMemberAPI> playerFleetList = Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy();
            //int iShipSize = playerFleetList.size();
            //iSalvageCoomer = getFleetsForgeModules(fleet);
            //float iBonus = iSalvageCoomer;
            //float temp = Math.min(iPristine,iSalvageCoomer);
            //iBonus += temp * PristineMetalMultiplier;
            //iSalvageCoomer -= temp;
            //temp = Math.min(iCorrupted,iSalvageCoomer);
            //iBonus += temp * CorruptedMetalMultiplier;
            float iBonus = getFleetsForgeModules(fleet);
            return iBonus + (Math.min(iBonus,getFleetsNanoforgePower(fleet)))/*+1*/;
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
