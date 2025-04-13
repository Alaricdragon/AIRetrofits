package data.scripts.hullmods;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.Set;

public class AIRetrofit_PatchworkAIRetrofit extends AIRetrofit_BaseLugistic {
    private static final String CantChangeHullMod = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Patchwork_CantSwapText");
    private static final String CanChangeHullMod1 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_Patchwork_CanSwapText1");
    private static final float SUPPLY_USE_MULT = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_SUPPLY_USE_MULT");//1f;
    private static final float CREW_USE_MULT = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_CREW_USE_MULT");//0f;
    private static final float REPAIR_LOSE = Global.getSettings().getFloat("AIRetrofits_Patchwork_AIretrofit_REPAIR_LOSE");//0.5f;

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
    @Override
    public String getDescriptionParam(int index, ShipAPI.HullSize hullSize,MutableShipStatsAPI ship) {
        switch (index) {
            case 5:
                try {
                    Set<String> a = Global.getSector().getPlayerFaction().getKnownHullMods();
                    if (!a.contains(AIRetrofits_Constants_3.Hullmod_AIRetrofit) || !AIRetrofits_Constants_3.Hullmod_PatchworkAIRetrofit_CanSwap)
                        return "" + CantChangeHullMod;
                } catch (Exception e) {
                    AIRetrofit_Log.loging("failed to get a parm. error: " + e, this, true);
                    return "" + CantChangeHullMod;
                }
                return AIRetrofits_StringHelper.getSplitString(CanChangeHullMod1, Global.getSettings().getHullModSpec(AIRetrofits_Constants_3.Hullmod_AIRetrofit).getDisplayName());
        }
        return super.getDescriptionParam(index,hullSize,ship);
    }
    @Override
    public boolean isApplicableToShip(ShipAPI ship){
        if (incompatibleHullMods(ship) != null) return false;
        int currentMod = (int) ship.getMutableStats().getMinCrewMod().computeEffective(ship.getMutableStats().getVariant().getHullSpec().getMinCrew());
        if (currentMod <= 0 && !ship.getVariant().hasHullMod(getHullmodID())) return false;
        int max = Math.round(ship.getMutableStats().getDynamic().getMod(Stats.MAX_LOGISTICS_HULLMODS_MOD).computeEffective(MAX_MODS));
        int num = getNumLogisticsMods(ship);
        boolean has = spec != null && ship.getVariant().hasHullMod(spec.getId());
        if (has) num--;
        if (num >= max) return false;
        return true;
    }
}
