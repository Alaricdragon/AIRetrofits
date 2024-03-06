package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

public class AIRetrofits_Robot_Types_calculater_GroundUnits_Defender extends AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker{
    public AIRetrofits_Robot_Types_calculater_GroundUnits_Defender(String ID,float PTS, String[] replaced, String[] created,float[] multi) {
        super(ID,PTS, replaced, created,multi);
    }
    public static final String type = "defender";

    @Override
    public String type() {
        return type;
    }
}
