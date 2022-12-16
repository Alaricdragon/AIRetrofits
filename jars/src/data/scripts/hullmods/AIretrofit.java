package data.scripts.hullmods;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.impl.hullmods.BaseLogisticsHullMod;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class AIretrofit extends BaseLogisticsHullMod {
//getCrew ()
/*
i want to:
	1: (done) lower max crew by MinCrew
	2: when NOT built in, add hullmods to alture cost of this based on max crew. here is how it will work:
		-(done)all following hullmods are not viable at any time ever.
		-(done)get 9 1 cost hullmods, 9 10 cost hullmods, and 9 100 cost hullmods.
		-(?done)have them named something like -"AIretrofit_" + hullmod_cost + "_" + hullmodID;
		-(done)get minimm crew requirement. add the given hull mods untill the combined cost is equal to the cost
		 of however mush i want the crew cost modafiyer to be.
		-(done)when this hull mod is removed or built-in, remove the dummy hull mods
		-(done)change this to only have at most 3 hull mods with a cost of: a * 100, b * 10, c * 1
	3: setup the descriptions
		(done)say how mush opp cost, min crew, and suplys this will cost in description
		-show the required opp cost were it says opp cost
		(partly)set the inportant values to be highlighted. no idea how to do so though
	4: balance edits
		(done)slow in combat repair speed by 50%

*/
/*
	init data:
		private static final float SUPPLY_USE_MULT:		the supply cost multaplyer of this hullmod
		private static final float CREW_USE_MULT:		the crew cost multaplyer of this hullmod
		private int[] CrewPerCostPerSize:				the number of crew per exstra op (for eatch shipsize) that this hullmod requires

	public void applyEffectsBeforeShipCreation()
		description:
			applys all hullmod effects to ship.
		input:
			HullSize hullSize:
			MutableShipStatsAPI stats:
			String id:
		a.0) sets the new supply & crew cost based on SUPPLY/CREW_USE_MULT
		a.1) calculats the exsta cost that this hull mod will cost with GetExstraOpCost
		a.2) applys that exsta cost, provided this hullmod is not instaled with a story ponit

	public String getDescriptionParam()
		description:
			changes description of ship
		input:
			int index			something that gets something to do with ship descriptions?
			HullSize hullSize	gets ship hullSize
		b.0) modafys fleet description to match new supply/crew costs

	public boolean isApplicableToShip()
		description:
			finds out if ship can even install hullmod
		input:
			ShipAPI ship:	shipAPI?
		NOTES:
			still need to find out what my ships free op is. is shockingly hard to do
		c.0) gets total op cost for this hullmod
		c.1) NOT DONE: gets ship free op
		c.2) sets description peramiters
		c.3) if this ship is real and i have enuther free opp, return true

	private void addExstraOpCost(){
		description:
			adds pre built invisable hullmods to ship.
		input:
			int exstra_cost
			MutableShipStatsAPI stats
		how it works:
			3 sets of 9 hull mods
			set A cost 1 op per hullmod,
			set B costs 10 op per hullmod,
			set C costs 100 op per hullmod
			add hullmods untill the number of hullmods i have added is squal to the
			cost that i want this hull mod to be.
			example:
			cost: 152
			1 100 cost hullmod, 5 10 cost hullmods, 2 1 cost hullmods.
		d.0)
			int temp varubles, int b, String temp
		d.1) runs 3 times. {a:100,a:10,a:1}
		d.2) resets b
		d.3) creates new hullmods with an increaseing b stat, till the remaining cost is less then a
			d.3.0) increase b, and suptract my exstra_cost by a (exstra_cost is my cost left to add now. a is 100 / 10 pow 1 to 3)
		d.3.1) get name of the hidden hullmod im adding to ship, then add it to ship >=) (hullmode name is a * b, and has the same cost)
	GetExstraOpCost()
		description:
			calculates the exstra opp cost, based on hullsize
		int crew,
		HullSize hullSize
 */
	/*
	ShipVariantAPI variant = Global.getSettings().getVariant(variantId).clone();
	member = Global.getFactory().createFleetMember(FleetMemberType.SHIP, variant);
	assignShipName(member, Factions.INDEPENDENT);

	 */
	private static final int[] Base_OP_COST = {5,10,15,25};
	private static final float SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_AIretrofit_SUPPLY_USE_MULT");//1f;
	private static final float CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_AIretrofit_CREW_USE_MULT");//0f;
	private static final float REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_AIretrofit_REPAIR_LOSE");//0.5f;
	private float[] CrewPerCostPerSize = {
			Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Other"),
			Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Frigate"),
			Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Destroyer"),
			Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Cruiser"),
			Global.getSettings().getFloat("AIRetrofits_AIretrofit_C-OP-Capital_ship")
	};
	//private int[] CrewPerCostPerSize = {1,5,10,20,40};
	//private float[] CrewPerCostPerSize = {1f,0.2f,0.01f,0.05f,0.025f};
	private int[] parm = new int[6];
	@Override
	public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id/*, MutableCharacterStatsAPI c*/) {
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
		//isAutomated(stats);
		//int temp = stats.getVariant().computeHullModOPCost();
		//a.1)

		int exstra_cost = GetExstraOpCost(MinCrew,hullSize);
		//a.2)
		boolean temp = stats.getVariant().getSMods().contains("AIretrofit_airetrofit");
		if(!temp) {
			addExstraOpCost(exstra_cost,stats);
			//stats.getVariant().addMod("mymod_temp0");
		}


		MinCrew = stats.getVariant().getHullSpec().getMinCrew();
		HullSize hullsize = stats.getVariant().getHullSpec().getHullSize();
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
	public String getDescriptionParam(int index, HullSize hullSize) {
		//b.0)//no idea if this works or how it works
		//return "cats";
		switch(index) {
			case 0:
				return "" + reqCrew(CrewPerCostPerSize[1]);
			case 1:
				return "" + reqCrew(CrewPerCostPerSize[2]);
			case 2:
				return "" + reqCrew(CrewPerCostPerSize[3]);
			case 3:
				return "" + reqCrew(CrewPerCostPerSize[4]);
			case 4:
				return "" + parm[0];
			case 5:
				return "" + parm[1];
			case 6:
				return "" + parm[2] + "%";//%
			case 7:
				return "" + parm[3] + "%";//%
			case 8:
				return "" + parm[4];
			case 9:
				return "" + parm[5];
		}
		return null;
	}
	@Override
	public boolean isApplicableToShip(ShipAPI ship/*, MutableCharacterStatsAPI wat*/){
		//c.0)
		int unusedOP = ship.getVariant().getUnusedOP(Global.getSector().getCharacterData().getPerson().getFleetCommanderStats());//only works for player fleets
		//int unusedOP = ship.getVariant().getUnusedOP(ship.getFleetMember().getFleetCommanderForStats().getFleetCommanderStats());//might work for all fleets
		float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
		HullSize hullsize = ship.getVariant().getHullSize();
		int cost = GetExstraOpCost(MinCrew,hullsize);
		//c.2)
		//return ship != null;
		//int exstra_cost = GetExstraOpCost(MinCrew,ship.getHullSize());
		//a.2)
		int Base_cost = this.spec.getCostFor(hullsize);
		//setDisplayValues(ship);
		//a.3)
		return ship != null && (cost + Base_cost <= unusedOP || ship.getVariant().hasHullMod("AIretrofit_airetrofit")) && incompatibleHullMods(ship) == null && super.isApplicableToShip(ship);
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

	private int GetExstraOpCost(float crew,HullSize hullSize){
		/*if(hullSize == HullSize.FIGHTER || hullSize == HullSize.DEFAULT){
			//crew = CrewPerCostPerSize[0];//1 cost per
		}else */if(hullSize == HullSize.FRIGATE){
			//crew = crew / CrewPerCostPerSize[1];
			return (int) (crew * CrewPerCostPerSize[1]);
		}else if(hullSize == HullSize.DESTROYER){
			//crew = crew / CrewPerCostPerSize[2];
			return (int) (crew * CrewPerCostPerSize[2]);
		}else if(hullSize == HullSize.CRUISER){
			//crew = crew / CrewPerCostPerSize[3];
			return (int) (crew * CrewPerCostPerSize[3]);
		}else if(hullSize == HullSize.CAPITAL_SHIP){
			return (int) (crew * CrewPerCostPerSize[4]);
		}
		return (int) (crew * CrewPerCostPerSize[0]);
	}
	@Override
	public void addPostDescriptionSection(TooltipMakerAPI tooltip, HullSize hullSize, ShipAPI ship, float width, boolean isForModSpec) {
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
		String hullmods = incompatibleHullMods(ship);
		if(hullmods != null){
			return "not compatible with: " + hullmods;
		}
		int unusedOP = ship.getVariant().getUnusedOP(Global.getSector().getCharacterData().getPerson().getFleetCommanderStats());//only works for player fleets
		//int unusedOP = ship.getVariant().getUnusedOP(ship.getFleetMember().getFleetCommanderForStats().getFleetCommanderStats());//might work for all fleets
		float MinCrew = ship.getVariant().getHullSpec().getMinCrew();
		HullSize hullsize = ship.getVariant().getHullSize();
		int cost = GetExstraOpCost(MinCrew,hullsize);
		int Base_cost = this.spec.getCostFor(hullsize);
		if(!(cost + Base_cost <= unusedOP || ship.getVariant().hasHullMod("AIretrofit_airetrofit"))){
			return "op cost: " + (cost + Base_cost);
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
				"AIRetrofit_ShipyardOmega"
		};
		final String[] names = {
				Global.getSettings().getHullModSpec(compatible[0]).getDisplayName(),
				Global.getSettings().getHullModSpec(compatible[1]).getDisplayName(),
				Global.getSettings().getHullModSpec(compatible[2]).getDisplayName(),
				Global.getSettings().getHullModSpec(compatible[3]).getDisplayName(),
				Global.getSettings().getHullModSpec(compatible[4]).getDisplayName(),
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
		HullSize hullsize = ship.getVariant().getHullSize();
		int cost = GetExstraOpCost(MinCrew,hullsize);
		int Base_cost = this.spec.getCostFor(hullsize);
		parm[0] = cost;
		parm[1] = (cost + Base_cost);
		parm[2] = 100;
		parm[3] = (int) (REPAIR_LOSE * 100);
		parm[4] = (int) MinCrew;
		parm[5] = (int) (MinCrew * CREW_USE_MULT);
	}
}
