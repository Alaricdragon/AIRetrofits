package data.scripts.AIWorldCode.industries;
//package com.fs.starfarer.api.impl.campaign.econ.impl;

import com.fs.starfarer.api.impl.campaign.econ.impl.PopulationAndInfrastructure;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionDoctrineAPI;
import com.fs.starfarer.api.characters.MarketConditionSpecAPI;
import com.fs.starfarer.api.impl.campaign.econ.impl.ConstructionQueue.ConstructionQueueItem;
import com.fs.starfarer.api.impl.campaign.fleets.FleetFactoryV3;
import com.fs.starfarer.api.impl.campaign.ids.*;
import com.fs.starfarer.api.loading.IndustrySpecAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

//exstending PopulationAndInfrastructure dose not work for my porpusses.
/*option: market condition that dose what i want?
then overwrite PopulationAndInfrastructure, and give it two states.
state 0: default state. working like normal
state 1: AI market state.
    -replace description,name,and image of this industry.
    -replace imports of crew, marine, food, luxury, drugs, organics and domestic good with other commodities.
    -replace exports of crew and marines with other commodities.
    -add on the AIRetrofit mod, the caption names, and the caption images to all ships that call this world home.
    -and anything else i can think of.

    -change grouth calculation:
        -set base growth to zero
            -remove the growth boost from freeport? (having to keep robotic works more secrete)
            -remove the growth - for hazards
        -add new growth factors:
            -growth will slowly go down when stability is below 5. going down to very negative at 0, at somewhat negative at 4
            -growth will only go up when 'hazard pay' is on. change the name 'hazard pay' to 'manufacture robotic drones'
            -when 'manufacture robotic drones' is ON have increased output and input on population and infastructor?
    (will ask programing tomorrow if its possible to interface with faction fleets, or hopefully world fleet, from industry.)

* */
public class AIRetrofit_Population extends PopulationAndInfrastructure {//BaseIndustry implements MarketImmigrationModifier {
    private final static String C1 = "AIretrofit_maintainsPacts";//replaces food
    private final static String C2 = "AIretrofit_CommandRely";//replaces DOMESTIC_GOODS
    private final static String C3 = "AIretrofit_humanInterfaceNode";//replaces LUXURY_GOODS
    private final static String C4 = "AIretrofit_SurveyDrone";//replaces DRUGS
    private final static String C5 = "AIretrofit_roboticReplacementParts";//replaces ORGANS
    private final static String C6 = "AIretrofit_SubCommandNode";//replaces ORGANICS
    private final static String C7 = "AIretrofit_WorkerDrone";

    @Override
    public void apply() {
        //market.getPlanetEntity().getSpec().getCoronaSize()
        if(AIretrofit_canBuild.isAI(market)){
            applyNew();
            //market.getCondition("AIRetrofit_AIPop").getPlugin().apply("AIRetrofit_AIPop");
        }else{
            super.apply();
            //applyOld();
        }
    }

    @Override
    public String getCurrentImage() {
        if(AIretrofit_canBuild.isAI(market)){
            return getCurrentImageNew();//Global.getSettings().getSpriteName("industry", "pop_high");
        }else{
            return super.getCurrentImage();//getCurrentImageOld();
        }
    }
    @Override
    protected String getDescriptionOverride() {
        if(AIretrofit_canBuild.isAI(market)){
            return getDescriptionOverrideNew();
        }else{
            return super.getDescriptionOverride();//getDescriptionOverrideOld();
        }
    }
    protected String getDescriptionOverrideNew() {
        String[] peoples = {
                "an handful",
                "tens",
                "hundreds",
                "thousands",
                "tens of thousands",
                "hundreds of thousands",
                "millions",
                "tens of millions",
                "hundreds of millions",
                "billions"
        };
        int size = market.getSize();
        String cid = null;
        if (size >= 1 && size <= 9) {
            cid = "population_" + size;
            MarketConditionSpecAPI mcs = Global.getSettings().getMarketConditionSpec(cid);
            String marketAIDescription = Global.getSettings().getString("AIRetrofits_PopulationDescription");//"this world is full of automated robots, and not people. a text description";
            //String marketSizeDescription = Global.getSettings().getString("AIRetrofits_PopulationSizeDescription");
            //String[] exstra = {"" + peoples[size]};
            //marketAIDescription
            if (mcs != null) {
                return marketAIDescription + "\n\n" + "there are " + peoples[size] + " of robotic workers active on this world";//mcs.getDesc();
            }
        }
        return super.getDescriptionOverride();
    }
    private void applyNew(){
        modifyStability(this, market, getModId(3));

//		if (market.getId().equals("chicomoztoc")) {
//		System.out.println("wefwefwe");
//	}
        /*if(market.isImmigrationIncentivesOn()){
            demand("metals",market.getSize());
            demand("rare_metals",market.getSize());
            demand("heavy_machinery",market.getSize());
            //getDemand("metals").getQuantity().modifyFlat(id3,industry.getMarket().getSize()," from hazard pay building drones");
            //industry.getDemand("rare_metals").getQuantity().modifyFlat(id3,industry.getMarket().getSize()," from hazard pay building drones");
            //industry.getDemand("heavy_machinery").getQuantity().modifyFlat(id3,industry.getMarket().getSize()," from hazard pay building drones");

            Pair<String, Integer> deficit = getMaxDeficit("metals", "rare_metals", "heavy_machinery");
            if(deficit.two != 0) {
                float loss = 1 - (market.getSize() / deficit.two);
                market.getIncoming().getWeight().modifyFlat(id2, loss * ((market.getSize() * 8) - 10), " missing resources for hazard pay's building drones");
            }
        }*/
        super.apply(true);
        int size = market.getSize();

        demand(C1, size);

        /*if (!market.hasCondition(Conditions.HABITABLE)) {
            demand(Commodities.ORGANICS, size - 1);
        }*/
        //market.getIndustry().getDemand()
        //market.getIndustry().getSupply().getQuantity();
        //market.
        int luxuryThreshold = 3;
        //HERE
        demand(C2, size - 1);
        demand(C3, size - luxuryThreshold);
        demand(C4, size - 2);
        demand(C5, size - 3);
        demand(C6, size - 2);

        demand(C7,size);

        demand(Commodities.SUPPLIES, Math.min(size, 3));

        supply(Commodities.CREW, size - 3);
        supply(C4, size - 4);
        supply(C5, size - 5);

        Pair<String, Integer> deficit = getMaxDeficit(C2);//com rellay
        if (deficit.two <= 0) {
            market.getStability().modifyFlat(getModId(0), 1, Global.getSector().getEconomy().getCommoditySpec(C2).getName() + " demand met");
        } else {
            if(size >= 4) {
                market.getStability().modifyFlat(getModId(0), -1, Global.getSector().getEconomy().getCommoditySpec(C2).getName() + " demand not met");
            }else{
                market.getStability().unmodifyFlat(getModId(0));
            }
        }

        deficit = getMaxDeficit(C3);//humon interface node
        if (deficit.two <= 0) {
            if(size > luxuryThreshold) {
                market.getStability().modifyFlat(getModId(1), 1, Global.getSector().getEconomy().getCommoditySpec(C3).getName() + " demand met");
            }else{
                market.getStability().unmodifyFlat(getModId(1));
            }
        } else {
            market.getStability().modifyFlat(getModId(1), -1, Global.getSector().getEconomy().getCommoditySpec(C3).getName() + " demand not met");

            //market.getStability().unmodifyFlat(getModId(1));
        }

        deficit = getMaxDeficit(C1);//matnace parts
        /*if (!market.hasCondition(Conditions.HABITABLE)) {
            deficit = getMaxDeficit(Commodities.FOOD, Commodities.ORGANICS);
        }*/
        if (deficit.two <= 0) {
            market.getStability().modifyFlat(getModId(2), 1, Global.getSector().getEconomy().getCommoditySpec(C1).getName() + " demand met");
        } else {
            market.getStability().modifyFlat(getModId(2), -1, Global.getSector().getEconomy().getCommoditySpec(C1).getName() + " demand not met");
            //market.getStability().unmodifyFlat(getModId(2));
        }

        deficit = getMaxDeficit(C6);//sub command node
        if (deficit.two <= 0) {
            market.getStability().modifyFlat(getModId(3), 1, Global.getSector().getEconomy().getCommoditySpec(C6).getName() + " demand met");
        } else {
            if(size >= 5) {
                market.getStability().modifyFlat(getModId(3), -1, Global.getSector().getEconomy().getCommoditySpec(C6).getName() + " demand not met");
            }else{
                market.getStability().unmodifyFlat(getModId(3));
            }
        }

        //C5,C1 for growth defects
        /*deficit = getMaxDeficit(C5);
        if (deficit.two > 0) {
            market.getIncoming().getWeight().modifyFlat(id1,deficit.two * -1,Global.getSector().getEconomy().getCommoditySpec(C5).getName() + " demand met");
        } else {
            market.getIncoming().getWeight().unmodify(id1);
        }*/
        boolean spaceportFirstInQueue = false;
        for (ConstructionQueueItem item : market.getConstructionQueue().getItems()) {
            IndustrySpecAPI spec = Global.getSettings().getIndustrySpec(item.id);
            if (spec.hasTag(Industries.TAG_SPACEPORT)) {
                spaceportFirstInQueue = true;
            }
            break;
        }
        if (spaceportFirstInQueue && Misc.getCurrentlyBeingConstructed(market) != null) {
            spaceportFirstInQueue = false;
        }
        if (!market.hasSpaceport() && !spaceportFirstInQueue) {
            float accessibilityNoSpaceport = Global.getSettings().getFloat("accessibilityNoSpaceport");
            market.getAccessibilityMod().modifyFlat(getModId(0), accessibilityNoSpaceport, "No spaceport");
        }

        float sizeBonus = getAccessibilityBonus(size);
        if (sizeBonus > 0) {
            market.getAccessibilityMod().modifyFlat(getModId(1), sizeBonus, "Colony size");
        }




        float stability = market.getPrevStability();
        float stabilityQualityMod = FleetFactoryV3.getShipQualityModForStability(stability);
        float doctrineQualityMod = market.getFaction().getDoctrine().getShipQualityContribution();

        market.getStats().getDynamic().getMod(Stats.FLEET_QUALITY_MOD).modifyFlatAlways(getModId(0), stabilityQualityMod,
                "Stability");

        market.getStats().getDynamic().getMod(Stats.FLEET_QUALITY_MOD).modifyFlatAlways(getModId(1), doctrineQualityMod,
                Misc.ucFirst(market.getFaction().getEntityNamePrefix()) + " fleet doctrine");

        //float stabilityDefenseMult = 0.5f + stability / 10f;
        float stabilityDefenseMult = 0.25f + stability / 10f * 0.75f;
        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMultAlways(getModId(),
                stabilityDefenseMult, "Stability");

        float baseDef = getBaseGroundDefenses(market.getSize());
        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyFlatAlways(getModId(),
                baseDef, "Base value for a size " + market.getSize() + " colony");


        //if (market.getHazardValue() > 1f) {
        if (HAZARD_INCREASES_DEFENSE) {
            market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMultAlways(getModId(1),
                    Math.max(market.getHazardValue(), 1f), "Colony hazard rating");
        }
        //}

        market.getStats().getDynamic().getMod(Stats.MAX_INDUSTRIES).modifyFlat(getModId(), getMaxIndustries(), null);

//		if (market.isPlayerOwned()) {
//			System.out.println("wfwefwef");
//		}
        FactionDoctrineAPI doctrine = market.getFaction().getDoctrine();
        float doctrineShipsMult = FleetFactoryV3.getDoctrineNumShipsMult(doctrine.getNumShips());
        float marketSizeShipsMult = FleetFactoryV3.getNumShipsMultForMarketSize(market.getSize());
        float deficitShipsMult = FleetFactoryV3.getShipDeficitFleetSizeMult(market);
        float stabilityShipsMult = FleetFactoryV3.getNumShipsMultForStability(stability);

        market.getStats().getDynamic().getMod(Stats.COMBAT_FLEET_SIZE_MULT).modifyFlatAlways(getModId(0), marketSizeShipsMult,
                "Colony size");

        market.getStats().getDynamic().getMod(Stats.COMBAT_FLEET_SIZE_MULT).modifyMultAlways(getModId(1), doctrineShipsMult,
                Misc.ucFirst(market.getFaction().getEntityNamePrefix()) + " fleet doctrine");

        if (deficitShipsMult != 1f) {
            market.getStats().getDynamic().getMod(Stats.COMBAT_FLEET_SIZE_MULT).modifyMult(getModId(2), deficitShipsMult,
                    getDeficitText(Commodities.SHIPS));
        } else {
            market.getStats().getDynamic().getMod(Stats.COMBAT_FLEET_SIZE_MULT).modifyMultAlways(getModId(2), deficitShipsMult,
                    getDeficitText(Commodities.SHIPS).replaceAll("shortage", "demand met"));
        }

        market.getStats().getDynamic().getMod(Stats.COMBAT_FLEET_SIZE_MULT).modifyMultAlways(getModId(3), stabilityShipsMult,
                "Stability");


        // chance of spawning officers and admins; some industries further modify this
        market.getStats().getDynamic().getMod(Stats.OFFICER_PROB_MOD).modifyFlat(getModId(0), OFFICER_BASE_PROB);
        market.getStats().getDynamic().getMod(Stats.OFFICER_PROB_MOD).modifyFlat(getModId(1),
                OFFICER_PROB_PER_SIZE * Math.max(0, market.getSize() - 3));

        market.getStats().getDynamic().getMod(Stats.OFFICER_ADDITIONAL_PROB_MULT_MOD).modifyFlat(getModId(0), OFFICER_ADDITIONAL_BASE_PROB);
        market.getStats().getDynamic().getMod(Stats.OFFICER_IS_MERC_PROB_MOD).modifyFlat(getModId(0), OFFICER_BASE_MERC_PROB);

        market.getStats().getDynamic().getMod(Stats.ADMIN_PROB_MOD).modifyFlat(getModId(0), ADMIN_BASE_PROB);
        market.getStats().getDynamic().getMod(Stats.ADMIN_PROB_MOD).modifyFlat(getModId(1),
                ADMIN_PROB_PER_SIZE * Math.max(0, market.getSize() - 3));

        modifyStability2(this, market, getModId(3));

        market.addTransientImmigrationModifier(this);



//		// if there's no queued spaceport, setHasSpaceport() is called by Spaceport (if it's present at the market)
//		boolean spaceportFirstInQueue = false;
//		for (ConstructionQueueItem item : market.getConstructionQueue().getItems()) {
//			IndustrySpecAPI spec = Global.getSettings().getIndustrySpec(item.id);
//			if (spec.hasTag(Industries.TAG_SPACEPORT)) {
//				market.setHasSpaceport(true);
//				market.getMemoryWithoutUpdate().set("$hadQueuedSpaceport", true);
//				spaceportFirstInQueue = true;
//			}
//			break;
//		}
//		if (!spaceportFirstInQueue && market.hasSpaceport() && market.getMemoryWithoutUpdate().is("$hadQueuedSpaceport", true)) {
//			market.getMemoryWithoutUpdate().unset("$hadQueuedSpaceport");
//			boolean hasSpaceport = false;
//			for (Industry ind : market.getIndustries()) {
//				if (ind.getSpec().hasTag(Industries.TAG_SPACEPORT)) {
//					hasSpaceport = true;
//					break;
//				}
//			}
//			if (!hasSpaceport) {
//				market.setHasSpaceport(false);
//			}
//		}

    }
    private String getCurrentImageNew() {
        float size = market.getSize();
        if (size <= SIZE_FOR_SMALL_IMAGE) {
            return Global.getSettings().getSpriteName("industry", "AIRetorfit_AIpop_low");
        }
        if(size >= SIZE_FOR_LARGE_IMAGE + 3){
            return Global.getSettings().getSpriteName("industry", "AIRetorfit_AIpop_veryHigh");
        }
        return Global.getSettings().getSpriteName("industry", "AIRetorfit_AIpop_high");
    }
}







