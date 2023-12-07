package data.scripts.AIWorldCode.industries.personalRobotForge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class AIRetrofit_combatRobotManufactory extends AIRetrofit_PersonalRobotManufactoryBase {
    private final static String C1 = "metals";
    private final static String C2 = "rare_metals";
    private final static String C3 = "hand_weapons";
    private final static String S1 = "AIretrofit_CombatDrone";
    private final static String S2 = "AIretrofit_Advanced_CombatDrone";
    private final static String S3 = "AIretrofit_Omega_CombatDrone";

    private final static int OS1Min = Global.getSettings().getInt("AIRetrofit_robotManufactury_combat_OS1Min");
    private final static int OS1Max = Global.getSettings().getInt("AIRetrofit_robotManufactury_combat_OS1Max");

    private final static int OS2Min = Global.getSettings().getInt("AIRetrofit_robotManufactury_combat_OS2Min");
    private final static int OS2Max = Global.getSettings().getInt("AIRetrofit_robotManufactury_combat_OS2Max");

    private final static int OS3Min = Global.getSettings().getInt("AIRetrofit_robotManufactury_combat_OS3Min");
    private final static int OS3Max = Global.getSettings().getInt("AIRetrofit_robotManufactury_combat_OS3Max");

    protected final static float BetaDefenceMulti = Global.getSettings().getFloat("AIRetrofit_robotManufactury_combat_Mod");//1.1f;

    private final static String groundDefenceText = Global.getSettings().getString("AIRetrofit_robotManufactury_combat_exstaText");//"from combat robot factory";
    private final static String BetaText = Global.getSettings().getString("AIRetrofit_robotManufactury_combat_betaText");//"use produced combat robots to boost ground defences by %s";
    @Override
    protected String[] getItems(){
        return new String[] {C1,C2,C3,S1,S2,S3};
    }
    @Override
    protected int[] getNumbers(){
        return new int[]{3,2,1,3,2,1};
    }
    @Override
    protected Object[] getOutput(){
        return new Object[]{S1,getRandomBetween(OS1Min,OS1Max)};
    }
    @Override
    protected Object[] getAlphaOutput(){
        return new Object[]{S2,getRandomBetween(OS2Min,OS2Max)};
    }
    @Override
    protected Object[] getOmegaOutput(){
        return new Object[]{S3,getRandomBetween(OS3Min,OS3Max)};
    }
    @Override
    public void applyBetaCoreModifiers(){
        if (!isFunctional()) {
            return;
        }
        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).modifyMult(id,getCombatMulti(),groundDefenceText);
    }
    public float getCombatMulti(){
        float bonus = BetaDefenceMulti;
        if (this.isImproved()){
            //bonus *= 2;
        }
        String[] itemsTemp = this.getItems();
        if (market != null && market.getIndustry(this.getSpec().getId()) != null) {
            bonus *= (int)market.getIndustry(this.getSpec().getId()).getSupply(itemsTemp[3]).getQuantity().getModifiedValue();
        }
        bonus+=1;
        return bonus;
    }
    @Override
    protected void removeBetaMods(){
        market.getStats().getDynamic().getMod(Stats.GROUND_DEFENSES_MOD).unmodifyMult(id);
    }
    @Override
    protected boolean canImproveToIncreaseProduction() {
        return true;
    }
    @Override
    public void unapply() {
        super.unapply();
    }
    @Override
    protected void exstraBetaDescription(String pre, TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){
        float pad = 5;
        Color highlight = Misc.getHighlightColor();
        float bonus = getCombatMulti();
        String[] exstra = {"" + ((bonus - 1) * 100) + "%"};
        tooltip.addPara(pre + BetaText,pad,highlight,exstra);
    }


}