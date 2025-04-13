package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CharacterDataAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.loading.VariantSource;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.List;
import java.util.Set;

public class AIRetrofits_AbilityAndHullmodAdding {
    static String hullmod = AIRetrofits_Constants_3.Hullmod_CrewReplacementDrones;//"AIretrofit_AutomatedCrewReplacementDrones";
    static String hullmod2 = AIRetrofits_Constants_3.Hullmod_AIRetrofit;//"AIretrofit_airetrofit";
    static String ability = AIRetrofits_Constants_3.ability_RobotForge;//"AIretrofit_robot_drone_forge";
    static String skill = AIRetrofits_Constants_3.req_skill;//"automated_ships";
    static boolean alwaysSkilled = AIRetrofits_Constants_3.AlwaysGiveThings;//Global.getSettings().getBoolean("AIRetrofit_alwaysGiveSkillsAndHullmods");
    public static void addAIRetrofits(){
        CharacterDataAPI character = Global.getSector().getCharacterData();
        if(character.getPerson().getStats().hasSkill(skill) || alwaysSkilled){
            character.addAbility(ability);
            character.addHullMod(hullmod);
            character.addHullMod(hullmod2);
            return;
        }
        addReqAbility();
    }
     public static void addReqAbility(){
        CharacterDataAPI character = Global.getSector().getCharacterData();
        if(character.getAbilities().contains(ability)){
            return;
        }
        if(character.getHullMods().contains(hullmod)){
            character.addAbility(ability);
            return;
        }
    }
    public static void swapPatchworkForAIRetrofit(){
        if(!AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit_CanSwap) return;
        Set<String> a = Global.getSector().getPlayerFaction().getKnownHullMods();
        if(!a.contains(AIRetrofits_Constants_3.Hullmod_AIRetrofit)) return;
        List<FleetMemberAPI> ships = Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy();
        for(int b2 = 0; b2 < ships.size(); b2++){//FleetMemberAPI b : ships){
            FleetMemberAPI b = ships.get(b2);
            ShipVariantAPI ship = b.getVariant().clone();
            ship.setSource(VariantSource.REFIT);
            if(ship.hasHullMod(AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit)) {
                ship.removeMod(AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit);
                ship.addMod(AIRetrofits_Constants_3.Hullmod_AIRetrofit);
                b.setVariant(ship, false, true);
            }
        }
    }
}
