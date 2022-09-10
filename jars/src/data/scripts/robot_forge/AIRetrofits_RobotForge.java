package data.scripts.robot_forge;

import java.awt.Color;

import com.fs.starfarer.api.campaign.*;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI.CargoItemType;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.abilities.BaseToggleAbility;
import com.fs.starfarer.api.impl.campaign.ids.Items;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.util.List;

import static data.scripts.robot_forge.AIRetrofits_RobotForgeSecondary.getFleetsForgeModules;
import static data.scripts.robot_forge.AIRetrofits_RobotForgeSecondary.iCalculateBonus;

public class AIRetrofits_RobotForge extends BaseToggleAbility {
    public static final Color CONTRAIL_COLOR = new Color(255, 97, 27, 80);

    //public float getSupplyPerMetal() {
        //int a = 0;
        //(Global.getSettings().getFloat("Automated_Retrofits_MetalConversionRate")
        //        * (Global.getSector().getEconomy().getCommoditySpec(Commodities.HEAVY_MACHINERY).getBasePrice() +
        //        5*Global.getSector().getEconomy().getCommoditySpec(Commodities.METALS).getBasePrice())
        //        / Global.getSector().getEconomy().getCommoditySpec(Commodities.SUPPLIES).getBasePrice())
    //    return (Global.getSettings().getFloat("Automated_Retrofits_MetalConversionRate")
    //            * (Global.getSector().getEconomy().getCommoditySpec(Commodities.HEAVY_MACHINERY).getBasePrice() + 5*Global.getSector().getEconomy().getCommoditySpec(Commodities.METALS).getBasePrice())
    //            / Global.getSector().getEconomy().getCommoditySpec(Commodities.SUPPLIES).getBasePrice())
    //            ;
    //}
    //public int MoreCoom() {
    //    return Global.getSettings().getInt("UseExtraCommodities");
    //}
    //float MetalCost = Global.getSettings().getFloat("Automated_Retrofits_MetalCost");
    //float HeavyMachineryCost = Global.getSettings().getFloat("Automated_Retrofits_HeavyMachineryCost");
    static int a = -1;
    boolean startups = true;
    /*static float CorruptedMetalMultiplier = Global.getSettings().getFloat("AIRetrofits_CorruptedMetal");
    static float PristineMetalMultiplier = Global.getSettings().getFloat("AIRetrofits_PristineMetal");
    static float SalvageModifier = Global.getSettings().getFloat("AIRetrofits_SalvageGantry");
    static float ForgePowerMulti = Global.getSettings().getFloat("AIRetrofits_RobotForgeMulti");*/
    //boolean affectInput = Global.getSettings().getBoolean("Automated_Retrofits_Input");
    //boolean affectOutput = Global.getSettings().getBoolean("Automated_Retrofits_Output");


    //static String[][] produce;
    //static String[][] requirements;
    //static float[][] produceNumbers;
    //static float[][] requirementsNumbers;
    @Override
    protected String getActivationText() {
        /*        if (Commodities.HEAVY_MACHINERY != null
        && Commodities.METALS != null
        && getFleet() != null
        || (getFleet().getCargo().getCommodityQuantity(Commodities.METALS) <= 0
        || getFleet().getCargo().getCommodityQuantity(Commodities.HEAVY_MACHINERY) <= 0
        || getFleet().getCargo().getSupplies() >= getFleet().getCargo().getMaxCapacity())) {
        return null;
        } else */return null;
    }
    protected TextPanelAPI text;
    protected InteractionDialogAPI dialog;
    @Override
    protected void activateImpl() {
        //FireBest.fire(null, dialog, memoryMap, "DialogOptionSelected");
        //text = dialog.getTextPanel();
        //text.
        //dialog.addOptionSelectedText();
        a = 0;
        startups = true;
        if(iCalculateBonus(getFleet()) == 0){
            CampaignFleetAPI fleet = getFleet();
            deactivate();
            fleet.addFloatingText("No Automated Drone Factory's installed on ships in fleet. cannot produce anything", Misc.setAlpha(entity.getIndicatorColor(), 255), 3.5f);
        }else if (this.entity.isPlayerFleet()) {
            CampaignFleetAPI fleet = this.getFleet();
            Global.getSector().getCampaignUI().showInteractionDialog(new AIRetrofits_RobotForgeDiologPlugin(fleet), fleet);
        }
    }

    @Override
    public boolean showActiveIndicator() { return isActive(); }

    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded) {
        //oldHERE (DONE) will need to be compleatly reworked. i dont understand mush of the code, but i can understand the commands.
        //Color gray = Misc.getGrayColor();
        Color highlight = Misc.getHighlightColor();

        String status = " (off)";
        if (turnedOn) {
            status = " (on)";
        }else{
            a = -1;
        }
        if(a == -1){
            tooltip = inactaveDiolog(tooltip, expanded, highlight, status);
        }else {
            tooltip = actavateDiolog(tooltip, expanded, highlight, status);
        }

        addIncompatibleToTooltip(tooltip, expanded);
    }

    @Override
    public boolean hasTooltip() { return true; }

    @Override
    protected void applyEffect(float amount, float level) {

        if(a == -1 || a >= AIRetrofits_ForgeList.items.size() || iCalculateBonus(getFleet()) == 0){
            deactivate();
            return;
        }
        if(startups){
            startups = false;
            return;
        }
        //oldHERE (DONE!). most work will need to go here
        CampaignFleetAPI fleet = getFleet();
        if (fleet == null) return;

        if(!isActive()) return;

        //fleet.getStats().getDetectedRangeMod().modifyPercent(getModId(), data.scripts.AIRetrofits_RobotForgeSecondary.SENSOR_PROFILE_INCREASE_PERCENT, "Supply Forging");

        float days = Global.getSector().getClock().convertToDays(amount);
        float cost = days;
        float supply;// = fleet.getCargo().getCommodityQuantity(Commodities.SUPPLIES);
        boolean continueing = true;
        //oldHERE however is relevent to my build. gets when im out of res or space to build things. might remove out of space though.
        if(iCalculateBonus(getFleet()) == 0){
            continueing = false;
            fleet.addFloatingText("No Automated Drone Factory's installed on ships in fleet. cannot produce anything", Misc.setAlpha(entity.getIndicatorColor(), 255), 3.5f);
            deactivate();
        }
        if(!AIRetrofits_ForgeList.items.get(a).canForge(fleet)) {
            continueing = false;
            AIRetrofits_ForgeList.items.get(a).getCantBuildPopup(fleet, entity);
            //getFleet().addFloatingText("" + a, Misc.setAlpha(entity.getIndicatorColor(), 255), 1f);
            deactivate();
        }
        /*for(int b = 0; b < requirements[a].length; b++){
            if(fleet.getCargo().getCommodityQuantity(requirements[a][b]) <= 0){
                continueing = false;
                fleet.addFloatingText("Out of " + requirements[a][b], Misc.setAlpha(entity.getIndicatorColor(), 255), 0.5f);
                deactivate();
                break;
            }
        }*///DONEHERE
        if(fleet.getCargo().getSpaceLeft() <= 0){
            continueing = false;
            fleet.addFloatingText("RobotForge deactivated. out of cargo space", Misc.setAlpha(entity.getIndicatorColor(), 255), 1f);
            deactivate();
        }
        /*for (int b = 0; b < produce[a].length; b++) {
            supply = fleet.getCargo().getCommodityQuantity(produce[a][b]);
            if(supply >= fleet.getCargo().getMaxCapacity()){
                continueing = false;
                fleet.addFloatingText("Full of " + produce[a][b], Misc.setAlpha(entity.getIndicatorColor(), 255), 0.5f);
                deactivate();
                break;
            }
        }*///DONEHERE
        //}
        if(continueing) {
            float basedmodifier = iCalculateBonus(getFleet());
            AIRetrofits_ForgeList.items.get(a).runForge(fleet,cost * basedmodifier);
            /*for (int b = 0; b < requirements[a].length; b++) {
                fleet.getCargo().removeCommodity(requirements[a][b], cost * requirementsNumbers[a][b] * basedmodifier);
            }


            for (int b = 0; b < produce[a].length; b++) {
                fleet.getCargo().addCommodity(produce[a][b], cost*produceNumbers[a][b]*basedmodifier);
            }*/
            //for (FleetMemberViewAPI view : getFleet().getViews()) {//oldHERE think this changes what color my fleet is as it makes things
            //    view.getContrailColor().shift("timidhavenoidea", CONTRAIL_COLOR, getActivationDays(), 2, 1f);
            //    view.getContrailWidthMult().shift("timidhavenoidea", 6, getActivationDays(), 2, 1f);
            //}
        }//DONEHERE
        /*if(continueing){

            //oldHERE affect Input/Output is not needed. it just says werether or not to add/remove items. that math will need to be changed though
            if (affectInput) {fleet.getCargo().removeCommodity(Commodities.METALS, cost*MetalCost*basedmodifier);fleet.getCargo().removeCommodity(Commodities.HEAVY_MACHINERY, cost*HeavyMachineryCost*basedmodifier);} else {fleet.getCargo().removeCommodity(Commodities.METALS, cost*MetalCost);fleet.getCargo().removeCommodity(Commodities.HEAVY_MACHINERY, cost*HeavyMachineryCost);}
            if (affectOutput) {fleet.getCargo().addCommodity(Commodities.SUPPLIES, cost*getSupplyPerMetal()*basedmodifier);} else {fleet.getCargo().addCommodity(Commodities.SUPPLIES, cost*getSupplyPerMetal());}
            for (FleetMemberViewAPI view : getFleet().getViews()) {
                view.getContrailColor().shift("timidhavenoidea", CONTRAIL_COLOR, getActivationDays(), 2, 1f);
                view.getContrailWidthMult().shift("timidhavenoidea", 6, getActivationDays(), 2, 1f);
            }
        }*/
        /*if(fleet.getCargo().getCommodityQuantity(Commodities.METALS) <= 0 || fleet.getCargo().getCommodityQuantity(Commodities.HEAVY_MACHINERY) <= 0) {
            //fleet.addFloatingText("Out of Metals or Heavy Machinery", Misc.setAlpha(entity.getIndicatorColor(), 255), 0.5f);
            //deactivate();
        } else if(false) {
            //fleet.addFloatingText("Full of Supplies", Misc.setAlpha(entity.getIndicatorColor(), 255), 0.5f);
            //deactivate();
        } else {
            float basedmodifier = iCalculateBonus();
            //oldHERE affect Input/Output is not needed. it just says werether or not to add/remove items. that math will need to be changed though
            if (affectInput) {fleet.getCargo().removeCommodity(Commodities.METALS, cost*MetalCost*basedmodifier);fleet.getCargo().removeCommodity(Commodities.HEAVY_MACHINERY, cost*HeavyMachineryCost*basedmodifier);} else {fleet.getCargo().removeCommodity(Commodities.METALS, cost*MetalCost);fleet.getCargo().removeCommodity(Commodities.HEAVY_MACHINERY, cost*HeavyMachineryCost);}
            if (affectOutput) {fleet.getCargo().addCommodity(Commodities.SUPPLIES, cost*getSupplyPerMetal()*basedmodifier);} else {fleet.getCargo().addCommodity(Commodities.SUPPLIES, cost*getSupplyPerMetal());}
            for (FleetMemberViewAPI view : getFleet().getViews()) {
                view.getContrailColor().shift("timidhavenoidea", CONTRAIL_COLOR, getActivationDays(), 2, 1f);
                view.getContrailWidthMult().shift("timidhavenoidea", 6, getActivationDays(), 2, 1f);
            }
        }*/
    }

    @Override
    public boolean isUsable() {
        //return isActive();
        return true;
    }

    static double[] hullsizemulti = {0.25,0.5,0.75,1,1.25};
    /*public float iCalculateBonus() {
        //oldHERE. mush to do
        float iCorrupted = getFleet().getCargo().getQuantity(CargoItemType.SPECIAL, new SpecialItemData(Items.CORRUPTED_NANOFORGE, null));
        float iPristine = getFleet().getCargo().getQuantity(CargoItemType.SPECIAL, new SpecialItemData(Items.PRISTINE_NANOFORGE, null));
        float iSalvageCoomer = 0f;
        List<FleetMemberAPI> playerFleetList = Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy();
        int iShipSize = playerFleetList.size();
        iSalvageCoomer = getFleetsForgeModules();
        //float iMaxBonus = PristineMetalMultiplier*iShipSize+SalvageModifier*iSalvageCoomer;
        float iMaxBonus = PristineMetalMultiplier*iSalvageCoomer+SalvageModifier*iSalvageCoomer;
        if (iCorrupted > iSalvageCoomer) {
            iCorrupted = iSalvageCoomer;
        };
        //float iBonus = CorruptedMetalMultiplier*iCorrupted+PristineMetalMultiplier*iPristine+SalvageModifier*iSalvageCoomer;
        float iBonus = CorruptedMetalMultiplier*iCorrupted+PristineMetalMultiplier*iPristine+SalvageModifier*iSalvageCoomer;
        if (iBonus > iMaxBonus) {
            iBonus = iMaxBonus;
        };
        return iBonus
    }*/
    @Override
    protected void deactivateImpl() {
        a = -1;
        cleanupImpl();
    }

    @Override
    protected void cleanupImpl() {
        CampaignFleetAPI fleet = getFleet();
        if (fleet == null) return;

        fleet.getStats().getDetectedRangeMod().unmodify(getModId());
    }
    protected TooltipMakerAPI actavateDiolog(TooltipMakerAPI tooltip, boolean expanded,Color highlight,String status){
        if(a == -1){
            return tooltip;
        }
        LabelAPI title = tooltip.addTitle(spec.getName() + status);
        title.highlightLast(status);
        title.setHighlightColor(highlight);
        float pad = 10f;
        tooltip.addPara("convert items into drones and other contraptions.", pad);
        //String Supply = Misc.getRoundedValueMaxOneAfterDecimal(getSupplyPerMetal());
        float iCoom = iCalculateBonus(getFleet());
        //if (iCoom > 1) {Supply = Misc.getRoundedValueMaxOneAfterDecimal(getSupplyPerMetal()*iCoom);}
        String canOrIs = isActive() ? "are building" : "can build";
        String Based = iCoom > 1 ? "Nanoforges in your inventory and ships with Automated Drone Factory's installed are improving the process of building robotic workers by": "You do not possess a nanoforge or a ship with an Automated Drone Factory that can hasten the process.";
        String Based2 = iCoom > 1 ? Misc.getRoundedValue((iCoom-1)*100) + "%." : "";
        String building = "";
        String consuming = "";
        String temp;
        /*for(int b = 0; b < produce[a].length; b++){//DONEHERE
            temp = Misc.getRoundedValueMaxOneAfterDecimal(produceNumbers[a][b]*iCoom);
            building += temp + " units of " + produce[a][b];
            if(b < produce[a].length - 2){
                building += ", ";
            }else if(b < produce[a].length - 1){
                building += ", and ";
            }
        }
        for(int b = 0; b < requirements[a].length; b++){//DONEHERE
            temp = Misc.getRoundedValueMaxOneAfterDecimal(requirementsNumbers[a][b]*iCoom);
            consuming += temp + " units of " + requirements[a][b];
            if(b < requirements[a].length - 2){
                consuming += ", ";
            }else if(b < requirements[a].length - 1){
                consuming += ", and ";
            }
        }*/
        tooltip.addPara("Your fleet's autoforges " + canOrIs + " producing " + AIRetrofits_ForgeList.items.get(a).getoutputAsString(iCoom) + " on a daily basis.",pad,Misc.getTextColor());
        //tooltip.addPara("Your fleet's autoforges " + canOrIs + " %s units of Metal with %s units of Heavy Machinery to create %s Supplies on a daily basis.",
        //        pad, Misc.getTextColor(), Misc.getRoundedValueMaxOneAfterDecimal(MetalCost*iCoom), Misc.getRoundedValueMaxOneAfterDecimal(HeavyMachineryCost*iCoom), Supply);



        /*if (MoreCoom() > 0) {
            for (int i = 0; i < MoreCoom(); i++) {
                tooltip.addPara("Additionally using %s " + Global.getSettings().getCommoditySpec(Global.getSettings().getString("ExtraCommodities" + i)).getName() + ".",
                        pad*0.2f, Misc.getTextColor(), Misc.getRoundedValueMaxOneAfterDecimal((Global.getSettings().getFloat("ExtraCommoditiesCost" + i))*iCoom));
            }
        };*/
        tooltip.addPara("%s %s", pad, highlight, Based, Based2);
        //tooltip.addPara("Increases the range at which the fleet can be detected by %s.",
        //        pad, Misc.getNegativeHighlightColor(), (int) data.scripts.AIRetrofits_RobotForgeSecondary.SENSOR_PROFILE_INCREASE_PERCENT + "%");


        return tooltip;
    }
    protected TooltipMakerAPI inactaveDiolog(TooltipMakerAPI tooltip, boolean expanded,Color highlight,String status){
        LabelAPI title = tooltip.addTitle(spec.getName() + status);
        title.highlightLast(status);
        title.setHighlightColor(highlight);
        float pad = 10f;
        tooltip.addPara("Smelt items into drones and other contraptions.", pad);
        //String Supply = Misc.getRoundedValueMaxOneAfterDecimal(getSupplyPerMetal());
        float iCoom = iCalculateBonus(getFleet());
        //if (iCoom > 1) {Supply = Misc.getRoundedValueMaxOneAfterDecimal(getSupplyPerMetal()*iCoom);}
        //String canOrIs = isActive() ? "are smelting" : "can smelt";
        String Based = iCoom > 0 ? "Nanoforges in your inventory and ships with Automated Drone Factory's installed are improving the process of forging supplies by": "You do not possess a nanoforge or a ship with an Automated Drone Factory that can hasten the process.";
        String Based2 = iCoom > 0 ? Misc.getRoundedValue((iCoom-1)*100) + "%." : "";
        String building = "";
        String consuming = "";
        String temp;
        /*for(int b = 0; b < produce[a].length; b++){
            temp = Misc.getRoundedValueMaxOneAfterDecimal(produceNumbers[a][b]*iCoom);
            building += temp + " units of " + produce[a][b];
            if(b < produce[a].length - 2){
                building += ", ";
            }else if(b < produce[a].length - 1){
                building += ", and ";
            }
        }
        for(int b = 0; b < requirements[a].length; b++){
            temp = Misc.getRoundedValueMaxOneAfterDecimal(requirementsNumbers[a][b]*iCoom);
            consuming += temp + " units of " + requirements[a][b];
            if(b < requirements[a].length - 2){
                consuming += ", ";
            }else if(b < requirements[a].length - 1){
                consuming += ", and ";
            }
        }*/
        int thing = AIRetrofits_ForgeList.items.size();
        //tooltip.addPara("total size of array: " + produce.length,pad,Misc.getTextColor());
        //tooltip.addPara("total size of array: " + thing,pad,Misc.getTextColor());
        tooltip.addPara("your fleet's autoforges are currently offline, but can be activated at any time.",pad,Misc.getTextColor());
        //tooltip.addPara("Your fleet's autoforges " + canOrIs + " " + consuming + " to create " + building + " on a daily basis.",pad,Misc.getTextColor());
        //tooltip.addPara("Your fleet's autoforges " + canOrIs + " %s units of Metal with %s units of Heavy Machinery to create %s Supplies on a daily basis.",
        //        pad, Misc.getTextColor(), Misc.getRoundedValueMaxOneAfterDecimal(MetalCost*iCoom), Misc.getRoundedValueMaxOneAfterDecimal(HeavyMachineryCost*iCoom), Supply);



        /*if (MoreCoom() > 0) {
            for (int i = 0; i < MoreCoom(); i++) {
                tooltip.addPara("Additionally using %s " + Global.getSettings().getCommoditySpec(Global.getSettings().getString("ExtraCommodities" + i)).getName() + ".",
                        pad*0.2f, Misc.getTextColor(), Misc.getRoundedValueMaxOneAfterDecimal((Global.getSettings().getFloat("ExtraCommoditiesCost" + i))*iCoom));
            }
        };*/
        tooltip.addPara("%s %s", pad, highlight, Based, Based2);
        return tooltip;
    }
    public static void setForgeValue(int ItemNumberToForge){
        a = ItemNumberToForge;
    }
    public static void setInitData(){//HERE
        /*produce = new String[][]{
                {
                        "AIretrofit_WorkerDrone"
                },
                {
                        "AIretrofit_SurveyDrone"
                },
                {
                        "supplies"
                }
        };
        requirements = new String[][]{
                {
                        "heavy_machinery"
                },
                {
                        "supplies"
                },
                {

                }
        };
        produceNumbers = new float[][]{
                {
                        10
                },
                {
                        40
                },
                {
                        100
                }
        };
        requirementsNumbers = new float[][]{
                {
                        1
                },
                {
                        1
                },
                {
                        1
                }
        };*/
    }
    /*
    protected float getFleetsForgeModules(){
        float iSalvageCoomer = 0f;
        List<FleetMemberAPI> playerFleetList = Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy();
        for (FleetMemberAPI member : playerFleetList) {
            if (member.isMothballed()) continue;
            if (member.getVariant().hasHullMod("AIretrofit_AutomatedCrewReplacementDrones")) {//oldHERE gets the ID of the hullmode that lets one make things

                float MinCrew = member.getVariant().getHullSpec().getMinCrew();
                float MaxCrew = member.getVariant().getHullSpec().getMaxCrew();
                iSalvageCoomer += (MaxCrew - MinCrew) * 0.01;//ForgePowerMulti;
                //stats.getDynamic().getMod(Stats.getSurveyCostReductionId(Commodities.CREW)).modifyFlat(id,(MaxCrew - MinCrew) / DronePerCrew);
                //ReplacedCrew = (MaxCrew - MinCrew) * 0.01;
                //iSalvageCoomer += hullsizemulti[bounus];
            }
        }
        return iSalvageCoomer * ForgePowerMulti;
    }*/
}