package data.scripts.AIWorldCode.growth;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.impl.campaign.ids.Factions;
import com.fs.starfarer.api.impl.campaign.ids.Strings;
import com.fs.starfarer.api.impl.campaign.population.CoreImmigrationPluginImpl;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.campaign.econ.Market;
import data.scripts.CrewReplacer_Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AIRetrofit_RemoveBaseImrgration {
    /*removed because this is now oppsoleet. see removeUnwantedGrowth for new version of this*/
    /*
    private static String[] negativeNames = {
            "AIRetrofit_Negative_a",
            "AIRetrofit_Negative_b",
            "AIRetrofit_Negative_c",
            "AIRetrofit_Negative_d",
            "AIRetrofit_Negative_e",
            "AIRetrofit_Negative_f",
            "AIRetrofit_Negative_g",
            "AIRetrofit_Negative_h",

    };
    static final private String info = "report bug to AIRetrofits";
    public static void apply(MarketAPI market, PopulationComposition incoming){
        //bouth boolean uiUpdateOnly, float f are not used here.
        PopulationComposition temp = computeIncoming(false,0,market,incoming);
        Object[] mods = temp.getWeight().getFlatMods().keySet().toArray();
        //HashMap<String, MutableStat.StatMod> mods = temp.getWeight().getFlatMods().values().toArray();
        for(int a = 0;a < mods.length; a++){
            //mods.keySet().toArray()
            //String m = incoming.getWeight().getFlatMods().keySet().toArray()[a].toString();
            MutableStat.StatMod temp2 = temp.getWeight().getFlatMods().get(mods[a].toString());
            CrewReplacer_Log.loging("adding negitive mod: " + temp2,new AIRetrofit_RemoveBaseImrgration(),true);
            //String c = temp2.getDesc();
            String a0 = temp2.getSource();
            float b = 0;//-temp2.getValue();
            incoming.getWeight().modifyFlat(a0,b,info);
        }
    }
    public static void unApply(PopulationComposition incoming){
        for(String a: negativeNames){
            incoming.getWeight().unmodifyFlat(a);
        }
    }


    private static PopulationComposition computeIncoming(boolean uiUpdateOnly, float f,MarketAPI market,PopulationComposition inc) {
        //PopulationComposition inc = new PopulationComposition();
        float stability = market.getStabilityValue();

//		if (stability > 0) {
//			inc.getWeight().modifyFlat("inc_st", stability, "Stability");
//		} else {
//			inc.getWeight().modifyFlat("inc_st", ZERO_STABILITY_PENALTY, "Stability");
//		}
        if (stability < 5) {
            inc.getWeight().modifyFlat(negativeNames[0], stability - 5, "Instability");
        }

        int numInd = Misc.getNumIndustries(market);
        if (numInd <= 0 && CoreImmigrationPluginImpl.GROWTH_NO_INDUSTRIES != 0 && market.getSize() > 3) {
            float weight = CoreImmigrationPluginImpl.getWeightForMarketSizeStatic(market.getSize());
            float penalty = -Math.round(weight * CoreImmigrationPluginImpl.GROWTH_NO_INDUSTRIES);
            inc.getWeight().modifyFlat(negativeNames[1], penalty, "No industries");
        }


        //inc.getWeight().modifyFlat("inc_size", -market.getSize(), "Colony size");

        float a = Math.round(market.getAccessibilityMod().computeEffective(0f) * 100f) / 100f;
        int accessibilityMod = (int) (a / Misc.PER_UNIT_SHIPPING);
        inc.getWeight().modifyFlat(negativeNames[2], accessibilityMod, "Accessibility");


        float hazMod = getImmigrationHazardPenalty(market);
        if (hazMod != 0) {
            float hazardSizeMult = getImmigrationHazardPenaltySizeMult(market);
            inc.getWeight().modifyFlat(negativeNames[3], hazMod,
                    "Hazard rating (" + Strings.X + Misc.getRoundedValueMaxOneAfterDecimal(hazardSizeMult) +
                            " based on colony size)");
        }

        MarketAPI biggestInSystem = null;
        List<MarketAPI> inReach = Global.getSector().getEconomy().getMarketsWithSameGroup(market);
        //Global.getSettings().profilerEnd();
        for (MarketAPI curr : inReach) {
            if (curr == market) continue;

            if (curr.getFaction().isHostileTo(market.getFaction())) continue;

            if (Misc.getDistanceLY(curr.getLocationInHyperspace(), market.getLocationInHyperspace()) <= 0) {
                if (biggestInSystem == null || curr.getSize() > biggestInSystem.getSize()) {
                    biggestInSystem = curr;
                }
            }
        }
        if (biggestInSystem != null) {
            float sDiff = biggestInSystem.getSize() - market.getSize();
            sDiff *= 2;
            if (sDiff > 0) {
                inc.getWeight().modifyFlat(negativeNames[4], sDiff, "Larger non-hostile colony in same system");
            } else if (sDiff < 0) {
                //inc.getWeight().modifyFlat("inc_insys", sDiff, "Smaller non-hostile market in same system");
            }
        }

        //HERE. this is population compasition stuff. not market growth.
        /*
        float numIndustries = market.getIndustries().size();
        inc.add(Factions.PIRATES, 1f * numIndustries);
        inc.add(Factions.POOR, 1f * numIndustries);

        String bulkFaction = Factions.INDEPENDENT;
        if (market.getFaction().isHostileTo(bulkFaction)) {
            bulkFaction = market.getFactionId();
        }
        //inc.add(bulkFaction, 10f * numIndustries);
        *//*
        applyIncentives(inc, uiUpdateOnly, f,market);

        //HERE internal calculation. not needed here.
        /*
        for (MarketImmigrationModifier mod : market.getAllImmigrationModifiers()) {
            mod.modifyIncoming(market, inc);
        }*/

        //HERE removed for not doing growth things.
        /*
        for (String fid : new ArrayList<String>(inc.getComp().keySet())) {
            if (Global.getSector().getFaction(fid) == null) {
                inc.getComp().remove(fid);
            }
        }

        inc.normalizeToPositive();*/
/*
        return inc;
    }

    private static float getImmigrationHazardPenalty(MarketAPI market) {
        float hazMod = Math.round((market.getHazardValue() - 1f) / CoreImmigrationPluginImpl.IMMIGRATION_PER_HAZARD);
        if (hazMod < 0) hazMod = 0;
        float hazardSizeMult = getImmigrationHazardPenaltySizeMult(market);
        return -hazMod * hazardSizeMult;
    }

    private static float getImmigrationHazardPenaltySizeMult(MarketAPI market) {
        float hazardSizeMult = 1f + (market.getSize() - 3f) * CoreImmigrationPluginImpl.HAZARD_SIZE_MULT;
        return hazardSizeMult;
    }

    private static void applyIncentives(PopulationComposition inc, boolean uiUpdateOnly, float f, MarketAPI market) {
//		if (market.getName().equals("Jangala")) {
//			System.out.println("ewfwfew");
//		}
        if (!market.isImmigrationIncentivesOn()) return;

        /*if (market.getSize() >= Misc.MAX_COLONY_SIZE) {
            market.setImmigrationIncentivesOn(false);
            return;
        }*//*


        float points = -getImmigrationHazardPenalty(market) + CoreImmigrationPluginImpl.INCENTIVE_POINTS_EXTRA;
        //float cost = INCENTIVE_CREDITS_PER_POINT * points * f;
        //float cost = market.getImmigrationIncentivesCost() * f;

        if (points > 0) {
            inc.getWeight().modifyFlat(negativeNames[5], points, "Hazard pay");
            /*if (!uiUpdateOnly) {
                market.setIncentiveCredits(market.getIncentiveCredits() + cost);
            }*/
        //}

    //}

}
