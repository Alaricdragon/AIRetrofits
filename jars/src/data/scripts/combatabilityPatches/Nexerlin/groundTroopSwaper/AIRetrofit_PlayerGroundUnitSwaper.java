package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.crewReplacer_Main;
import data.scripts.startupData.AIRetrofits_Constants;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;
import exerelin.campaign.intel.groundbattle.GroundUnitDef;

public class AIRetrofit_PlayerGroundUnitSwaper {
    public final static boolean logs = Global.getSettings().getBoolean("AIRetrofits_Logs_Nexerlin_GroundCombat_playerUnitCreation");
    private final static String[] basicDefinitions = {"marine", "heavy"};
    private static final String[][] definitions = {{"AIRetrofit_CombatRobots_T0_marine","AIRetrofit_CombatRobots_T1_marine","AIRetrofit_CombatRobots_T2_marine"},{"AIRetrofit_CombatRobots_T0_Heavy","AIRetrofit_CombatRobots_T1_Heavy","AIRetrofit_CombatRobots_T2_Heavy"}};
    private static final String[] personell = {AIRetrofits_Constants.Commodity_T0_CombatDrone,AIRetrofits_Constants.Commodity_T1_CombatDrone,AIRetrofits_Constants.Commodity_T2_CombatDrone};
    public static void attemptToSwap(GroundUnit b, GroundBattleIntel intel){
        if (b.getPersonnelMap().size() != 0){
            swap_WithMap(b,intel);
            return;
        }
        swap_blank(b,intel);
    }
    public static void swap_WithMap(GroundUnit b, GroundBattleIntel intel){
        int robotType = -1;
        int baseType = -1;
        AIRetrofit_Log.loging("Looking into swaping a player unit of definition of "+b.getUnitDefId()+"",new AIRetrofit_Log(),logs);
        AIRetrofit_Log.loging("attempting to swap player unit for a robot type...",new AIRetrofit_Log(),logs);
        AIRetrofit_Log.loging("getting maps...",new AIRetrofit_Log(),logs);
        AIRetrofit_Log.push();
        displayMaps(b);
        AIRetrofit_Log.pop();
        //b.setUnitDef(definitions[0][1]);///<-- that works btw.
        for (int a = personell.length - 1; a >= 0; a--){
            AIRetrofit_Log.loging("chcecking peronell of: "+personell[a],new AIRetrofit_Log(),logs);
            if (b.getPersonnelMap().containsKey(personell[a])){
                robotType = a;
                AIRetrofit_Log.loging("found robot of type "+personell[a]+". continuing...",new AIRetrofit_Log(),logs);

                break;
            }
        }
        if (robotType == -1) return;
        for (int a = 0; a < basicDefinitions.length; a++){
            if (b.getUnitDefId().equals(basicDefinitions[a])){
                baseType = a;
                AIRetrofit_Log.loging("found a valid definition of: "+basicDefinitions[a]+". continuing...",new AIRetrofit_Log(),logs);
                break;
            }
        }
        if (baseType == -1) return;
        AIRetrofit_Log.loging("preparing to set unit definitions....",new AIRetrofit_Log(),logs);

        b.setUnitDef(definitions[baseType][robotType]);
        AIRetrofit_Log.loging("checking ground unit after defintion change: def, attacker, size, location, fleet "+b.getUnitDefId()+", "+b.isAttacker()+", "+b.getSize()+", "+b.getLocation()+", "+b.getFleet(),new AIRetrofit_Log(),logs);
        CargoAPI cargo = Global.getSector().getPlayerFleet().getCargo();
        GroundUnit.returnCommodityMapToCargo(b.getPersonnelMap(),cargo,1);
        GroundUnit.returnCommodityMapToCargo(b.getEquipmentMap(),cargo,1);
        AIRetrofit_Log.loging("checking ground unit after map clear: def, attacker, size, location, fleet "+b.getUnitDefId()+", "+b.isAttacker()+", "+b.getSize()+", "+b.getLocation()+", "+b.getFleet(),new AIRetrofit_Log(),logs);
        int sizeA = (int) (crewReplacer_Main.getJob(b.getUnitDef().equipment.crewReplacerJobId).getAvailableCrewPower(cargo) / b.getUnitDef().equipment.mult);
        int sizeB = (int) (crewReplacer_Main.getJob(b.getUnitDef().personnel.crewReplacerJobId).getAvailableCrewPower(cargo) / b.getUnitDef().personnel.mult);
        int available = Math.min(sizeA,sizeB);
        AIRetrofit_Log.loging("gettting available 'units' as: "+available,new AIRetrofit_Log(),logs);
        b.setSize(Math.min(available,intel.getUnitSize().getMaxSizeForType(definitions[baseType][robotType])),true);
        AIRetrofit_Log.loging("checking ground unit after new size set: def, attacker, size, location, fleet "+b.getUnitDefId()+", "+b.isAttacker()+", "+b.getSize()+", "+b.getLocation()+", "+b.getFleet(),new AIRetrofit_Log(),logs);
    }
    public static void swap_blank(GroundUnit b, GroundBattleIntel intel){
        int bd = -1;
        for (int a = 0; a < basicDefinitions.length; a++){
            if (basicDefinitions[a].equals(b.getUnitDefId())){
                bd = a;
                break;
            }
        }
        if (bd == -1) return;
        CargoAPI cargo = Global.getSector().getPlayerFleet().getCargo();
        String oldDef = b.getUnitDefId();
        for (int a = definitions[bd].length - 1; a >= 0; a--){
            b.setUnitDef(definitions[bd][a]);
            int sizeA = 999999999;
            int sizeB = 0;
            try {
                sizeA = (int) (crewReplacer_Main.getJob(b.getUnitDef().equipment.crewReplacerJobId).getAvailableCrewPower(cargo) / b.getUnitDef().equipment.mult);
            }catch (Exception e){

            }
            if (crewReplacer_Main.getJob(b.getUnitDef().personnel.crewReplacerJobId).hasCrew("marines")) {
                AIRetrofit_Log.loging("NO NO NO NO NO NO NO NO NO NO NO WHY WHY WHY WHY WHY WHY WHY", new AIRetrofit_Log(), true);
            }
            try {
                sizeB = (int) (crewReplacer_Main.getJob(b.getUnitDef().personnel.crewReplacerJobId).getAvailableCrewPower(cargo) / b.getUnitDef().personnel.mult);
            }catch (Exception e){

            }
            AIRetrofit_Log.loging("for changing from to: "+oldDef+", "+b.getUnitDefId()+" i have personell,heavy of: "+sizeA+", "+sizeB,new AIRetrofit_Log(),true);
            int available = Math.min(sizeA,sizeB);
            if (available != 0){
                return;
            }
        }
        b.setUnitDef(oldDef);
    }
    private static void displayMaps(GroundUnit unit){
        AIRetrofit_Log.loging("personell map",new AIRetrofit_Log(),logs);
        AIRetrofit_Log.push();
        for (String key : unit.getPersonnelMap().keySet()){
            AIRetrofit_Log.loging("of key: "+key + " unit count: "+unit.getPersonnelMap().get(key),new AIRetrofit_Log(),logs);
        }
        AIRetrofit_Log.pop();
        AIRetrofit_Log.loging("equipment map",new AIRetrofit_Log(),logs);
        AIRetrofit_Log.push();
        for (String key : unit.getEquipmentMap().keySet()){
            AIRetrofit_Log.loging("of key: "+key + " unit count: "+unit.getEquipmentMap().get(key),new AIRetrofit_Log(),logs);
        }
        AIRetrofit_Log.pop();
    }
}
