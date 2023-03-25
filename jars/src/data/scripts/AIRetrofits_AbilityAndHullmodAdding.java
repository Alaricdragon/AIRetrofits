package data.scripts;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CharacterDataAPI;
import data.scripts.startupData.AIRetrofits_Constants;

public class AIRetrofits_AbilityAndHullmodAdding {
    static String hullmod = AIRetrofits_Constants.Hullmod_CrewReplacementDrones;//"AIretrofit_AutomatedCrewReplacementDrones";
    static String hullmod2 = AIRetrofits_Constants.Hullmod_AIRetrofit;//"AIretrofit_airetrofit";
    static String ability = AIRetrofits_Constants.ability_RobotForge;//"AIretrofit_robot_drone_forge";
    static String skill = AIRetrofits_Constants.req_skill;//"automated_ships";
    static boolean alwaysSkilled = AIRetrofits_Constants.AlwaysGiveThings;//Global.getSettings().getBoolean("AIRetrofit_alwaysGiveSkillsAndHullmods");
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
        for(String a: character.getAbilities()){
            if(a.equals(ability)){
                return;
            }
        }
        for(String a: character.getHullMods()) {
            if (a.equals(hullmod)) {
                character.addAbility(ability);
                return;
            }
        }
    }
}