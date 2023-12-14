package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.interfaces.AIRetrofits_GroundCombatTypeReplacement;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

public class AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker extends AIRetrofits_Robot_Types_calculater_2 implements AIRetrofits_GroundCombatTypeReplacement {
    public AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(String ID, String[] replaced, String[] created) {
        super(ID);
        this.replaced = replaced;
        this.created = created;
    }
    public String[] replaced,created;
    @Override
    public String[] unitTypeReplaced() {
        return replaced;
    }

    @Override
    public String[] unitTypeCreated() {
        return created;
    }
    public void swap(GroundBattleIntel battle, GroundUnit unit){
        String newDefinition = getNewDef(unit.getUnitDefId());
        /*put the rest of the 'replacement' code here.*/
    }
    public String getNewDef(String oldDef){
        for (int a = 0; a < replaced.length; a++){
            if (replaced[a].equals(oldDef)){
                return created[a];
            }
        }
        return null;
    }
}
