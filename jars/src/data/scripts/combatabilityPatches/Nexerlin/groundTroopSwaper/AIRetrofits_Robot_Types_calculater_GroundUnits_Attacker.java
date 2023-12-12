package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.interfaces.AIRetrofits_GroundCombatTypeReplacement;

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
}
