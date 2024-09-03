package data.scripts.hullmods;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofit_Log;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.awt.*;
import java.util.Set;

public class AIRetrofit_PatchworkAIRetrofit extends BaseLogisticsHullMod {
    private static final String[] IncombatableReasons = {
            AIRetrofits_StringGetterProtection.getString("AIRetrofits_Patchwork_AIRetrofit_NA_combatable"),
            AIRetrofits_StringGetterProtection.getString("AIRetrofits_Patchwork_AIRetrofit_NA_MinCrew"),
            AIRetrofits_StringGetterProtection.getString("AIRetrofits_Patchwork_AIRetrofit_NA_OppCost"),
    };

    private static final int[] Base_OP_COST = {5,10,15,25};
    private static final float SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_REPAIR_LOSE");//0.5f;
    private float[] CrewPerCostPerSize = {
            Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_C-OP-Other"),
            Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_C-OP-Frigate"),
            Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_C-OP-Destroyer"),
            Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_C-OP-Cruiser"),
            Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_C-OP-Capital_ship")
    };
    //private int[] CrewPerCostPerSize = {1,5,10,20,40};
    //private float[] CrewPerCostPerSize = {1f,0.2f,0.01f,0.05f,0.025f};
    private int[] parm = new int[7];

    private static final float CR_DOWNGRADE = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_CR_LOSE");
    //private static final float MALFUNCTION_CHANCE = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_MALFUNCTION_CHANCE");
    //private static final float MALFUNCTION_CHANCE2 = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_MALFUNCTION_CHANCE2");

    private static final String CanChangeHullMod1 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Patchwork_CanSwapText1");
    private static final String CanChangeHullMod2 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Patchwork_CanSwapText2");
    private static final String CantChangeHullMod = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Patchwork_CantSwapText");
    @Override
    public void applyEffectsBeforeShipCreation(ShipAPI.HullSize hullSize, MutableShipStatsAPI stats, String id/*, MutableCharacterStatsAPI c*/) {
        //a.0)
        float MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        float SupplyIncrease = stats.getSuppliesPerMonth().getBaseValue() * SUPPLY_USE_MULT;
        //stats.getSuppliesPerMonth().getBaseValue()
        stats.getSuppliesPerMonth().modifyFlat(id,SupplyIncrease);
        //stats.getSuppliesPerMonth().modifyMult(id, SUPPLY_USE_MULT);
        stats.getMinCrewMod().modifyMult(id,CREW_USE_MULT);
        stats.getMaxCrewMod().modifyFlat(id,MinCrew * -1);
        stats.getCombatEngineRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);
        stats.getCombatWeaponRepairTimeMult().modifyMult(id,1 + REPAIR_LOSE);

        stats.getMaxCombatReadiness().modifyFlat(id,CR_DOWNGRADE);
        //stats.getCriticalMalfunctionChance().modifyFlat(id,MALFUNCTION_CHANCE);
        //stats.getCriticalMalfunctionChance().modifyFlat(id, MALFUNCTION_CHANCE);
        //stats.getWeaponMalfunctionChance().modifyFlat(id,MALFUNCTION_CHANCE2);
        //isAutomated(stats);
        //int temp = stats.getVariant().computeHullModOPCost();
        //a.1)

        int exstra_cost = GetExstraOpCost(MinCrew,hullSize);
        //a.2)
        boolean temp = stats.getVariant().getSMods().contains(AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit);
        if(!temp) {
            addExstraOpCost(exstra_cost,stats);
            //stats.getVariant().addMod("mymod_temp0");
        }


        MinCrew = stats.getVariant().getHullSpec().getMinCrew();
        ShipAPI.HullSize hullsize = stats.getVariant().getHullSpec().getHullSize();
        int cost = GetExstraOpCost(MinCrew,hullsize);
        int Base_cost= 0;/*
		parm[0] = cost;
		parm[1] = (cost + Base_cost);
		parm[2] = 100;
		parm[3] = (int) (REPAIR_LOSE * 100);
		parm[4] = (int) MinCrew;
		parm[5] = (int) (MinCrew * CREW_USE_MULT);*/

    }
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize) {
        //b.0)//no idea if this works or how it works
        //return "cats";
        switch(index) {
            case 0:
                return "" + reqCrew(CrewPerCostPerSize[1]) + "/" + reqCrew(CrewPerCostPerSize[2]) + "/" + reqCrew(CrewPerCostPerSize[3]) + "/" + reqCrew(CrewPerCostPerSize[4]);
            case 1:
                return "" + parm[0];
            case 2:
                return "" + parm[1];
            case 3:
                return "" + parm[2] + "%";//%
            case 4:
                return "" + parm[3] + "%";//%
            case 5:
                return "" + parm[4];
            case 6:
                return "" + parm[5] + "%";
            case 7:
                return "" + parm[6];
            case 8:
                try {
                    Set<String> a = Global.getSector().getPlayerFaction().getKnownHullMods();
                    if (!a.contains(AIRetrofits_Constants_3.Hullmod_AIRetrofit) || !AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit_CanSwap)
                        return "" + CantChangeHullMod;
                }catch (Exception e){
                    AIRetrofit_Log.loging("failed to get a parm. error: " + e,this,true);
                    return "" + CantChangeHullMod;
                }
                return "" + CanChangeHullMod1  + Global.getSettings().getHullModSpec(AIRetrofits_Constants_3.Hullmod_AIRetrofit).getDisplayName() + CanChangeHullMod2;
        }
        return null;
    }
    @Override
    public boolean isApplicableToShip(ShipAPI ship/*, MutableCharacterStatsAPI wat*/){
        //c.0)
        int unusedOP = ship.getVariant().getUnusedOP(Global.getSector().getCharacterData().getPerson().getFleetCommanderStats());//only works for player fleets
        //int unusedOP = ship.getVariant().getUnusedOP(ship.getFleetMember().getFleetCommanderForStats().getFleetCommanderStats());//might work for all fleets
        float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
        ShipAPI.HullSize hullsize = ship.getVariant().getHullSize();
        int cost = GetExstraOpCost(MinCrew,hullsize);
        //c.2)
        //return ship != null;
        //int exstra_cost = GetExstraOpCost(MinCrew,ship.getHullSize());
        //a.2)
        int Base_cost = this.spec.getCostFor(hullsize);
        //setDisplayValues(ship);
        //a.3)
        boolean hasMinCrew = true;
        try {
            if (ship.getFleetMember().getStats().getMinCrewMod().computeEffective(ship.getVariant().getHullSpec().getMinCrew()) <= 0 && !ship.getVariant().hasHullMod(AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit)) {
                hasMinCrew = false;
            }
        }catch (Exception E){
            AIRetrofit_Log.loging("Error: failed to get min crew in AIRetrofit hullmod. what were you even doing?",this);
        }
        return ship != null && (cost + Base_cost <= unusedOP || ship.getVariant().hasHullMod(AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit)) && incompatibleHullMods(ship) == null && super.isApplicableToShip(ship) && hasMinCrew;
    }
    private void addExstraOpCost(int exstra_cost,MutableShipStatsAPI stats){
        //example of adding a hullmod
        //stats.getVariant().addMod("mymod_temp0");
        //d.0)
        int b;
        String temp;
        //d.1)
        for(int a = 4096; a >= 1 && exstra_cost > 0; a = a / 2){
            //d.2)
            //d.3)
            //d.3.1)
            if(a <= exstra_cost) {//if extra cost is >= to this number it will need to be added anyways.
                exstra_cost -= a;
                temp = "AIretrofit_AIretrofit_opadd" + a;
                stats.getVariant().addMod(temp);
            }
        }
    }
    private void addExstraOpCostOld(int exstra_cost,MutableShipStatsAPI stats){
        //example of adding a hullmod
        //stats.getVariant().addMod("mymod_temp0");
        //d.0)
        int b;
        String temp;
        //d.1)
        /**/for(int a = 100; a >= 1; a = a / 10){
            //d.2)
            b = 0;
            //d.3)
            while(exstra_cost >= a && b < 9){//TEMP (change 2 to 10 when done)
                //d.3.0)
                b++;
                exstra_cost = exstra_cost - a;
            }
            //d.3.1)
            if(b != 0) {
                temp = "AIretrofit_AIretrofit_opadd" + (a * b);
                stats.getVariant().addMod(temp);
            }
        }/**/
    }

    private int GetExstraOpCost(float crew, ShipAPI.HullSize hullSize){
		/*if(hullSize == HullSize.FIGHTER || hullSize == HullSize.DEFAULT){
			//crew = CrewPerCostPerSize[0];//1 cost per
		}else */if(hullSize == ShipAPI.HullSize.FRIGATE){
            //crew = crew / CrewPerCostPerSize[1];
            return (int) (crew * CrewPerCostPerSize[1]);
        }else if(hullSize == ShipAPI.HullSize.DESTROYER){
            //crew = crew / CrewPerCostPerSize[2];
            return (int) (crew * CrewPerCostPerSize[2]);
        }else if(hullSize == ShipAPI.HullSize.CRUISER){
            //crew = crew / CrewPerCostPerSize[3];
            return (int) (crew * CrewPerCostPerSize[3]);
        }else if(hullSize == ShipAPI.HullSize.CAPITAL_SHIP){
            return (int) (crew * CrewPerCostPerSize[4]);
        }
        return (int) (crew * CrewPerCostPerSize[0]);
    }
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, ShipAPI.HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
        //this is how im trying to highlight thigns. dose not work right. no idea why
        //setDisplayValues(ship);


        if (Global.getSettings().getCurrentState() == GameState.TITLE) return;
        Color h = Misc.getHighlightColor();
        tooltip.addPara("",
                0, h,
                ""
        );
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        try {
            String hullmods = incompatibleHullMods(ship);
            if (hullmods != null) {
                return IncombatableReasons[0] + hullmods;//"not compatible with: " + hullmods;
            }
            if (ship.getFleetMember().getStats().getMinCrewMod().computeEffective(ship.getVariant().getHullSpec().getMinCrew()) <= 0 && !ship.getVariant().hasHullMod("AIretrofit_airetrofit")) {
                return IncombatableReasons[1];//"cannot be installed on a drone ship, or a ship that otherwise has no crew requirements";
            }
            int unusedOP = ship.getVariant().getUnusedOP(Global.getSector().getCharacterData().getPerson().getFleetCommanderStats());//only works for player fleets
            //int unusedOP = ship.getVariant().getUnusedOP(ship.getFleetMember().getFleetCommanderForStats().getFleetCommanderStats());//might work for all fleets
            float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
            ShipAPI.HullSize hullsize = ship.getVariant().getHullSize();
            int cost = GetExstraOpCost(MinCrew, hullsize);
            int Base_cost = this.spec.getCostFor(hullsize);
            if (!(cost + Base_cost <= unusedOP || ship.getVariant().hasHullMod(this.spec.getId()))) {
                return IncombatableReasons[2] + (cost + Base_cost);//"op cost: " + (cost + Base_cost);
            }
        }catch (Exception E){
            AIRetrofit_Log.loging("Error: failed to run getUnapplicableReason. wonder why?",this,true);
        }
        return super.getUnapplicableReason(ship);
    }
    @Override
    public boolean canBeAddedOrRemovedNow(ShipAPI ship, MarketAPI marketOrNull, CampaignUIAPI.CoreUITradeMode mode){
        setDisplayValues(ship);
        return super.canBeAddedOrRemovedNow(ship,marketOrNull,mode);
    }
    private int reqCrew(float in){
        if(in == 0){
            return 0;
        }
        return (int)(1 / in);
    }
    private String incompatibleHullMods(ShipAPI ship){
        final String[] compatible = {
                "AIRetrofit_ShipyardBase",
                "AIRetrofit_ShipyardGamma",
                "AIRetrofit_ShipyardBeta",
                "AIRetrofit_ShipyardAlpha",
                "AIRetrofit_ShipyardOmega",
                AIRetrofits_Constants_3.Hullmod_AIRetrofit
        };
        final String[] names = {
                Global.getSettings().getHullModSpec(compatible[0]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[1]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[2]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[3]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[4]).getDisplayName(),
                Global.getSettings().getHullModSpec(compatible[5]).getDisplayName(),
        };
        for(int a = 0; a < compatible.length; a++){
            if(ship.getVariant().hasHullMod(compatible[a])){
                return names[a];
            }
        }
        return null;
    }

    private void setDisplayValues(ShipAPI ship){
        if(ship == null){
            return;
        }
        float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
        ShipAPI.HullSize hullsize = ship.getVariant().getHullSize();
        int cost = GetExstraOpCost(MinCrew,hullsize);
        int Base_cost = this.spec.getCostFor(hullsize);
        parm[0] = cost;
        parm[1] = (cost + Base_cost);
        parm[2] = (int)(100 * SUPPLY_USE_MULT);
        parm[3] = (int) (REPAIR_LOSE * 100);
        parm[4] = (int) MinCrew;
        parm[5] = (int) (CR_DOWNGRADE*-100);
        parm[6] = (int) (MinCrew * CREW_USE_MULT);
    }
}
