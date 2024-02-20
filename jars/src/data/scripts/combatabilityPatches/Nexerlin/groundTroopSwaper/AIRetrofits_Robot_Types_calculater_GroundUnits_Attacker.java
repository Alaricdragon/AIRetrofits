package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.AIRetrofits_GroundBattleListiner2;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.interfaces.AIRetrofits_GroundCombatTypeReplacement;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

public class AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker extends AIRetrofits_Robot_Types_calculater_2 implements AIRetrofits_GroundCombatTypeReplacement {
    private final static boolean logs = AIRetrofits_Robot_Types_calculater_2.robot_type_logs;
    public AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(String ID,float PTS, String[] replaced, String[] created,float[] multi) {
        super(ID);
        this.replaced = replaced;
        this.created = created;
        this.multi = multi;
        this.PTS = PTS;
    }
    public static final String type = "attacker";
    public float[] multi;
    public String[] replaced,created;
    public float PTS;
    @Override
    public String[] unitTypeReplaced() {
        return replaced;
    }

    @Override
    public String[] unitTypeCreated() {
        return created;
    }

    @Override
    public String type() {
        return type;
    }
    public float getPowerToSizeRaitio(){
        return PTS;
    }

    public boolean swap(GroundBattleIntel battle, GroundUnit unit){
        MarketAPI market = AIRetrofits_GroundBattleListiner2.getUnitsMarket(unit,battle);
        //AIRetrofit_Log.loging("looking for market",this,logs);
        if (market == null)return false;
        //AIRetrofit_Log.loging("market found",this,logs);
        return this.swap(battle,unit,getOddsOfRobot(market));
    }
    public boolean swap(GroundBattleIntel battle, GroundUnit unit,float odds){
        if (odds == 0) return false;
        //AIRetrofit_Log.loging("swap",this,logs);
        Object[] temp = getNewDef(unit.getUnitDefId());
        if (temp == null) return false;
        String newDefinition = (String) temp[0];
        float multi = (float) temp[1];
        double tempb = Math.random();
        AIRetrofit_Log.loging("dice, odds:"+tempb+", "+odds,this,logs);
        if (tempb < odds){
            AIRetrofit_Log.loging("attmpting to swap "+unit.getUnitDefId()+" with "+newDefinition,this,logs);
            AIRetrofit_Log.loging("old attack stat"+unit.getAttackStat().getModifiedValue(),this,logs);

            AIRetrofit_Log.loging("before",this,true);
            AIRetrofit_Log.push();
            displayMaps(unit);
            AIRetrofit_Log.pop();
            int sizeTemp = unit.getSize();
            //unit.setSize(0,false);
            unit.setUnitDef(newDefinition);
            AIRetrofit_Log.loging("AM I SETTING THIS RIGHT!?!?!?",this,true);
            AIRetrofit_Log.loging("new attack stat before size changes"+unit.getAttackStat().getModifiedValue(),this,logs);
            unit.setSize((int)(sizeTemp*multi),false);
            spliceMarines(unit.getPersonnelMap());
            AIRetrofit_Log.loging("after",this,true);
            AIRetrofit_Log.push();
            displayMaps(unit);
            AIRetrofit_Log.pop();
            AIRetrofit_Log.loging("new attack stat"+unit.getAttackStat().getModifiedValue(),this,logs);

            return true;
        }
        return false;
        /*put the rest of the 'replacement' code here.*/
    }
    public void spliceMarines(java.util.Map<String, Integer> map){
        map.remove("marines");
    }
    private void displayMaps(GroundUnit unit){
        AIRetrofit_Log.loging("personell map",this,true);
        AIRetrofit_Log.push();
        for (String key : unit.getPersonnelMap().keySet()){
            AIRetrofit_Log.loging("of key: "+key + " unit count: "+unit.getPersonnelMap().get(key),this,true);
        }
        AIRetrofit_Log.pop();
        AIRetrofit_Log.loging("equipment map",this,true);
        AIRetrofit_Log.push();
        for (String key : unit.getEquipmentMap().keySet()){
            AIRetrofit_Log.loging("of key: "+key + " unit count: "+unit.getEquipmentMap().get(key),this,true);
        }
        AIRetrofit_Log.pop();
    }
    public Object[] getNewDef(String oldDef){
        for (int a = 0; a < replaced.length; a++){
            if (replaced[a].equals(oldDef)){
                return new Object[]{created[a],multi[a]};
            }
        }
        return null;
    }
}
