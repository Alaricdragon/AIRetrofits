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
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;

import java.util.List;

import static data.scripts.robot_forge.AIRetrofits_RobotForgeSecondary.*;

public class AIRetrofits_RobotForge extends BaseToggleAbility {
    public static final Color CONTRAIL_COLOR = new Color(255, 97, 27, 80);
    protected static final String String_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_0");
    protected static final String String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_1");
    protected static final String String_2 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_2");
    protected static final String String_3 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_3");
    protected static final String String_4 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_4");
    protected static final String String_5 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_5");
    protected static final String String_6 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_6");
    protected static final String String_7 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_7");
    protected static final String String_8 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_8");
    protected static final String String_9 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_9");
    protected static final String String_10 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_10");
    protected static final String String_11 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_11");
    protected static final String String_12 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_12");
    protected static final String String_13 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_13");
    protected static final String String_14 = AIRetrofits_StringGetterProtection.getString("AIRetrofits_RobotForge_Ability_14");



    static int a = -1;
    boolean startups = true;
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
            fleet.addFloatingText(String_0, Misc.setAlpha(entity.getIndicatorColor(), 255), 3.5f);
        }else if (this.entity.isPlayerFleet()) {
            CampaignFleetAPI fleet = this.getFleet();
            Global.getSector().getCampaignUI().showInteractionDialog(new AIRetrofits_RobotForgeDiologPlugin2(), fleet);
        }
    }

    @Override
    public boolean showActiveIndicator() { return isActive(); }

    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded) {
        //oldHERE (DONE) will need to be compleatly reworked. i dont understand mush of the code, but i can understand the commands.
        //Color gray = Misc.getGrayColor();
        Color highlight = Misc.getHighlightColor();

        String status = String_1;
        if (turnedOn) {
            status = String_2;
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
        CampaignFleetAPI fleet = getFleet();
        if (fleet == null) return;

        if(!isActive()) return;

        float days = Global.getSector().getClock().convertToDays(amount);
        float cost = days;
        float supply;// = fleet.getCargo().getCommodityQuantity(Commodities.SUPPLIES);
        boolean continueing = true;
        if(iCalculateBonus(getFleet()) == 0){
            continueing = false;
            fleet.addFloatingText(String_3, Misc.setAlpha(entity.getIndicatorColor(), 255), 3.5f);
            deactivate();
        }
        if(!AIRetrofits_ForgeList.items.get(a).canForge(fleet)) {
            continueing = false;
            AIRetrofits_ForgeList.items.get(a).getCantBuildPopup(fleet, entity);
            deactivate();
        }
        if(fleet.getCargo().getSpaceLeft() <= 0){
            continueing = false;
            fleet.addFloatingText(String_4, Misc.setAlpha(entity.getIndicatorColor(), 255), 3.5f);
            deactivate();
        }
        if(continueing) {
            float basedmodifier = iCalculateBonus(getFleet());
            AIRetrofits_ForgeList.items.get(a).runForge(fleet,cost * basedmodifier);
        }//DONEHERE

    }

    @Override
    public boolean isUsable() {
        //return isActive();
        return true;
    }

    static double[] hullsizemulti = {0.25,0.5,0.75,1,1.25};
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
        tooltip.addPara(String_5, pad);
        float iCoom = iCalculateBonus(getFleet());
        String canOrIs = isActive() ? String_6 : String_7;
        tooltip.addPara(AIRetrofits_StringHelper.getSplitString(String_8,canOrIs,AIRetrofits_ForgeList.items.get(a).getoutputAsString(iCoom)),pad,Misc.getTextColor());
        tooltip.addPara("%s", pad, highlight, getPara(0));
        tooltip.addPara("%s", pad, highlight, getPara(1));
        tooltip.addPara("%s", pad, highlight, getPara(2));
        //tooltip.addPara("%s %s", pad, highlight, Based, Based2);
        return tooltip;
    }
    private String getPara(int a){
        float b;
        float c;
        switch(a){
            case 0:
                float iCoom = iCalculateBonus(getFleet());
                return iCoom > 0 ? AIRetrofits_StringHelper.getSplitString(String_9,""+Misc.getRoundedValueOneAfterDecimalIfNotWhole(iCoom)) : String_10;
            case 1:
                b = getFleetsForgeModules(getFleet());
                return AIRetrofits_StringHelper.getSplitString(String_11,""+Misc.getRoundedValueOneAfterDecimalIfNotWhole(b));
            case 2:
                b = getFleetsForgeModules(getFleet());
                c = getFleetsNanoforgePower(getFleet());

                return AIRetrofits_StringHelper.getSplitString(String_12,""+Misc.getRoundedValueOneAfterDecimalIfNotWhole(Math.min(b,c)));
            case 3:
                return "";
        }
        return "";
    }
    protected TooltipMakerAPI inactaveDiolog(TooltipMakerAPI tooltip, boolean expanded,Color highlight,String status){
        LabelAPI title = tooltip.addTitle(spec.getName() + status);
        title.highlightLast(status);
        title.setHighlightColor(highlight);
        float pad = 10f;
        tooltip.addPara(String_13, pad);
        tooltip.addPara(String_14,pad,Misc.getTextColor());
        tooltip.addPara("%s", pad, highlight, getPara(0));
        tooltip.addPara("%s", pad, highlight, getPara(1));
        tooltip.addPara("%s", pad, highlight, getPara(2));
        //tooltip.addPara("%s %s", pad, highlight, Based, Based2);
        return tooltip;
    }
    public static void setForgeValue(int ItemNumberToForge){
        a = ItemNumberToForge;
    }
    public static void setInitData(){//HERE
    }
}