package data.scripts.hullmods;

import com.fs.starfarer.api.Global;
import data.scripts.startupData.AIRetrofits_Constants_3;

public class AIRetrofit_AIretrofit extends AIRetrofit_BaseLugistic {

	private static final float SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_AIretrofit_SUPPLY_USE_MULT");//1f;
	private static final float CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_AIretrofit_CREW_USE_MULT");//0f;
	private static final float REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_AIretrofit_REPAIR_LOSE");//0.5f;

	@Override
	public float getSupplyCostMulti() {
		return SUPPLY_USE_MULT;
	}
	@Override
	public float getCrewReductionMulti() {
		return CREW_USE_MULT;
	}
	@Override
	public float getRepairTimeMulti() {
		return REPAIR_LOSE;
	}
}
