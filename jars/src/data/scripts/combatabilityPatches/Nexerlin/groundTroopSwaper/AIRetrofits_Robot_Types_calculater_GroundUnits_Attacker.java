package data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.Robot_Percentage_Calculater.AIRetrofits_Robot_Types_calculater_2;
import data.scripts.combatabilityPatches.Nexerlin.AIRetrofits_GroundBattleListiner;
import data.scripts.combatabilityPatches.Nexerlin.groundTroopSwaper.interfaces.AIRetrofits_GroundCombatTypeReplacement;
import exerelin.campaign.intel.groundbattle.GroundBattleIntel;
import exerelin.campaign.intel.groundbattle.GroundUnit;

import java.util.ArrayList;

public class AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker extends AIRetrofits_Robot_Types_calculater_2 implements AIRetrofits_GroundCombatTypeReplacement {
    public AIRetrofits_Robot_Types_calculater_GroundUnits_Attacker(String ID, String[] replaced, String[] created,float[] multi) {
        super(ID);
        this.replaced = replaced;
        this.created = created;
        this.multi = multi;
    }
    public static final String type = "attacker";
    public float[] multi;
    public String[] replaced,created;
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

    public boolean swap(GroundBattleIntel battle, GroundUnit unit){
        MarketAPI market = AIRetrofits_GroundBattleListiner.getUnitsMarket(unit,battle);
        //AIRetrofit_Log.loging("looking for market",this,true);
        if (market == null)return false;
        //AIRetrofit_Log.loging("market found",this,true);
        return this.swap(battle,unit,getOddsOfRobot(market));
    }
    public boolean swap(GroundBattleIntel battle, GroundUnit unit,float odds){
        if (odds == 0) return false;
        //AIRetrofit_Log.loging("swap",this,true);
        Object[] temp = getNewDef(unit.getUnitDefId());
        if (temp == null) return false;
        String newDefinition = (String) temp[0];
        float multi = (float) temp[1];
        double tempb = Math.random();
        AIRetrofit_Log.loging("dice, odds:"+tempb+", "+odds,this,true);
        if (tempb < odds){
            FactionAPI faction = unit.getFaction();
            /*unit.getFleet();
            unit.getLocation();
            unit.getDestination();*/
            GroundUnit NewUnit = battle.getSide(unit.isAttacker()).createUnit(newDefinition,faction, (int) (unit.getSize()*multi));
            NewUnit.setDestination(unit.getDestination());
            NewUnit.setLocation(unit.getLocation());
            //NewUnit.setSize(,false);
            //NewUnit.set
            //battle.getSide(unit.isAttacker()).getUnits().remove(unit);
            AIRetrofit_Log.loging("creating a new unit of unit definition: '"+newDefinition+"'. it also has definition for sure of: '"+NewUnit.getUnitDefId()+"'",this,true);
            AIRetrofit_Log.push();
            AIRetrofit_Log.loging("oldOnit: definition,destination,location,size,faction: "+unit.getUnitDefId()+","+unit.getDestination()+","+unit.getLocation()+","+unit.getSize()+","+unit.getFaction(),this,true);
            AIRetrofit_Log.loging("newUnit: definition,destination,location,size,faction: "+NewUnit.getUnitDefId()+","+NewUnit.getDestination()+","+NewUnit.getLocation()+","+NewUnit.getSize()+","+NewUnit.getFaction(),this,true);
            AIRetrofit_Log.pop();
            unit.removeUnit(false);
            return true;
        }
        return false;
        /*put the rest of the 'replacement' code here.*/
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
