package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import data.scripts.crewReplacer_Main;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

public class AIRetrofit_PlayerGroundUnitSwaper {
    private final static String basic = "marine", heavy = "heavy";
    private static final String[][] definitions = {{"AIRetrofit_CombatRobots_T0_marine","AIRetrofit_CombatRobots_T1_marine","AIRetrofit_CombatRobots_T2_marine"},{"AIRetrofit_CombatRobots_T0_Heavy","AIRetrofit_CombatRobots_T1_Heavy","AIRetrofit_CombatRobots_T2_Heavy"}};
    public static void attemptToSwap(GroundUnit b, GroundBattleIntel intel){
        //GroundUnit.returnCommodityMapToCargo();
        int presentTypes = 0;
        for (String[] c : definitions) {
            for (String a : c) {
                if (b.getPersonnelMap().containsKey(a)) {
                    presentTypes++;
                }
            }
        }
        if (presentTypes != b.getPersonnelMap().size()) return;
        if (presentTypes == 1){
            changeTemp(b,intel);
            return;
            /*if (b.getUnitDefId().equals(basic)){

                return;
            }
            if (b.getUnitDefId().equals(heavy)){

                return;
            }*/
        }
        if (b.getUnitDefId().equals(basic)){
            b.setUnitDef(definitions[0][0]);
            return;
        }
        if (b.getUnitDefId().equals(heavy)){
            b.setUnitDef(definitions[1][0]);
            return;
        }
    }
    public void changeMarine(GroundUnit b){

    }
    public void changeHeavy(GroundUnit b){

    }
    public static void changeTemp(GroundUnit b,GroundBattleIntel intel){
        /*
        * 1) find out what definition i will change this unit to (by checking the personell map)
        * 2) send all contents of unit maps back to cargo.
        * 3) set unit definition to the new definition.
        * 4) set unit size to the recommended size, taking items from cargo to do it.*/
        String definition = getDefinition(b);
        if (definition == null) return;
        //b.;
        CargoAPI cargo = Global.getSector().getPlayerFleet().getCargo();
        GroundUnit.returnCommodityMapToCargo(b.getPersonnelMap(),cargo,1);
        GroundUnit.returnCommodityMapToCargo(b.getEquipmentMap(),cargo,1);

        b.setUnitDef(definition);
        int sizeA = (int) (crewReplacer_Main.getJob(b.getUnitDef().equipment.crewReplacerJobId).getAvailableCrewPower(cargo) / b.getUnitDef().equipment.mult);
        int sizeB = (int) (crewReplacer_Main.getJob(b.getUnitDef().personnel.crewReplacerJobId).getAvailableCrewPower(cargo) / b.getUnitDef().personnel.mult);
        int available = Math.min(sizeA,sizeB);
        b.setSize(Math.min(available,intel.getUnitSize().getMaxSizeForType(definition)),true);
    }
    public static String getDefinition(GroundUnit b){
        for (String[] c : definitions) {
            for (String a : c) {
                if (b.getPersonnelMap().containsKey(a)) {
                    return a;
                }
            }
        }
        return null;
    }
}
