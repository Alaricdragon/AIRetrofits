package data.scripts.lunaLib;
import com.fs.starfarer.api.Global;
import data.scripts.hullmods.AIRetrofit_AIretrofit;
import data.scripts.hullmods.AIRetrofit_PatchworkAIRetrofit;
import data.scripts.hullmods.Shipyard.*;
import lunalib.lunaSettings.LunaSettings;

public class StoredSettings {

    public static void getSettings(){
        getBaseSettings();
        if (Global.getSettings().getModManager().isModEnabled("lunalib")){
            getLunaSettings();
        }else {
            getConfigSettings();
        }
    }
    public static void getBaseSettings(){
    }
    public static void attemptEnableLunalib(){
        if (!Global.getSettings().getModManager().isModEnabled("lunalib")) return;
        LunaSettings.addSettingsListener(new ApplySettingsOnChange());
    }
    private static void getLunaSettings(){
        getAIRetrofits();
    }
    private static void getAIRetrofits(){
        AIRetrofit_AIretrofit.SUPPLY_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI0_2");
        AIRetrofit_AIretrofit.CREW_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI0_3");
        AIRetrofit_AIretrofit.REPAIR_LOSE = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI0_4");

        AIRetrofit_PatchworkAIRetrofit.SUPPLY_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI1_2");
        AIRetrofit_PatchworkAIRetrofit.CREW_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI1_3");
        AIRetrofit_PatchworkAIRetrofit.REPAIR_LOSE = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI1_4");

        AIRetrofit_ShipyardBase.SUPPLY_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI2_2");
        AIRetrofit_ShipyardBase.CREW_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI2_3");
        AIRetrofit_ShipyardBase.REPAIR_LOSE = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI2_4");

        AIRetrofit_ShipyardGamma.SUPPLY_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI3_2");
        AIRetrofit_ShipyardGamma.CREW_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI3_3");
        AIRetrofit_ShipyardGamma.REPAIR_LOSE = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI3_4");

        AIRetrofit_ShipyardBeta.SUPPLY_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI4_2");
        AIRetrofit_ShipyardBeta.CREW_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI4_3");
        AIRetrofit_ShipyardBeta.REPAIR_LOSE = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI4_4");

        AIRetrofit_ShipyardAlpha.SUPPLY_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI5_2");
        //AIRetrofit_ShipyardAlpha.CREW_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI5_3");
        AIRetrofit_ShipyardAlpha.REPAIR_LOSE = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI5_4");
        AIRetrofit_ShipyardAlpha.COSTS_AUTOPOINTS = LunaSettings.getBoolean("AI-Retrofits","HULLMODS_AI5_5");

        AIRetrofit_ShipyardOmega.SUPPLY_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI5_2");
        //AIRetrofit_ShipyardOmega.CREW_USE_MULT = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI5_3");
        AIRetrofit_ShipyardOmega.REPAIR_LOSE = LunaSettings.getFloat("AI-Retrofits","HULLMODS_AI5_4");
        AIRetrofit_ShipyardOmega.COSTS_AUTOPOINTS = LunaSettings.getBoolean("AI-Retrofits","HULLMODS_AI6_5");
    }
    private static void getConfigSettings(){

        //EscapeHullMod.maxJumps = Global.getSettings().getInt("EmergencyEscape_MaxJumps");
    }
}
