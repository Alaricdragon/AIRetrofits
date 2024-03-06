package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.AIRetrofits_GroundBattleListiner2;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.interfaces.AIRetrofits_GroundCombatTypeReplacement;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

import java.util.List;

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
        MarketAPI market = getUnitsMarket(unit,battle);
        //AIRetrofit_Log.loging("looking for market",this,logs);
        if (market == null)return false;
        //AIRetrofit_Log.loging("market found",this,logs);
        return this.swap(battle,unit,getOddsOfRobot(market));
    }
    //private static int count = 0;
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
            //if (true) return true;
            //count++;
            //AIRetrofit_Log.loging("CHANGING A UNIT NAMED: "+ unit.getUnitDefId()+". from side "+unit.isAttacker()+". a total of "+count+" units have been changed. battlesize is: "+battle.getSide(true).getUnits().size()+", "+battle.getSide(false).getUnits().size(),this,true);
            AIRetrofit_Log.loging("attmpting to swap "+unit.getUnitDefId()+" with "+newDefinition,this,logs);
            AIRetrofit_Log.loging("old attack stat"+unit.getAttackStat().getModifiedValue(),this,logs);

            AIRetrofit_Log.loging("before",this,logs);
            AIRetrofit_Log.push();
            displayMaps(unit);
            AIRetrofit_Log.pop();
            int sizeTemp = unit.getSize();
            //unit.setSize(0,false);
            unit.setUnitDef(newDefinition);
            AIRetrofit_Log.loging("new attack stat before size changes"+unit.getAttackStat().getModifiedValue(),this,logs);
            spliceMarines(unit.getPersonnelMap());
            unit.setSize((int)(sizeTemp*multi),false);
            AIRetrofit_Log.loging("after",this,logs);
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
        AIRetrofit_Log.loging("personell map",this,logs);
        AIRetrofit_Log.push();
        for (String key : unit.getPersonnelMap().keySet()){
            AIRetrofit_Log.loging("of key: "+key + " unit count: "+unit.getPersonnelMap().get(key),this,logs);
        }
        AIRetrofit_Log.pop();
        AIRetrofit_Log.loging("equipment map",this,logs);
        AIRetrofit_Log.push();
        for (String key : unit.getEquipmentMap().keySet()){
            AIRetrofit_Log.loging("of key: "+key + " unit count: "+unit.getEquipmentMap().get(key),this,logs);
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

    public MarketAPI getUnitsMarket(GroundUnit b, GroundBattleIntel battle){
        MarketAPI market;
        if (b.getFleet() == null){
            if (b.isAttacker()){
                AIRetrofit_Log.loging("unit, fleet"+b.getUnitDefId()+", "+b.getFleet(),new AIRetrofit_Log(),logs);
                String faction = b.getFaction().getId();
                List<MarketAPI> markets = Global.getSector().getEconomy().getMarketsCopy();
                for (int a = 0; a < markets.size(); a++){
                    if (markets.get(a).getFactionId().equals(faction)){
                        AIRetrofit_Log.loging("getUnitsMarket: "+0,new AIRetrofit_Log(),logs);
                        return markets.get(a);
                    }
                }
                AIRetrofit_Log.loging("getUnitsMarket: "+1,new AIRetrofit_Log(),logs);
                return null;
            }else{
                AIRetrofit_Log.loging("getUnitsMarket: "+2,new AIRetrofit_Log(),logs);
                return battle.getMarket();
            }
        }else{
            market = Global.getSector().getEconomy().getMarket(b.getFleet().getMemory().getString(MemFlags.MEMORY_KEY_SOURCE_MARKET));
            if (market == null){
                if (b.isAttacker()){
                    AIRetrofit_Log.loging("getUnitsMarket: "+3,new AIRetrofit_Log(),logs);
                    return null;
                }else{
                    AIRetrofit_Log.loging("getUnitsMarket: "+4,new AIRetrofit_Log(),logs);
                    return battle.getMarket();
                }
            }
            AIRetrofit_Log.loging("getUnitsMarket: "+5,new AIRetrofit_Log(),logs);
            return market;
        }
    }
}
