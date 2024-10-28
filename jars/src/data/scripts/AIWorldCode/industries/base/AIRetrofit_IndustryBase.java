package data.scripts.AIWorldCode.industries.base;

import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import exerelin.world.industry.bonus.AICore;

public class AIRetrofit_IndustryBase extends BaseIndustry {
    public static final String AIRetrofit_IndustryBase_addOmegaCoreDescription_String_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addOmegaCoreDescription_String_0");
    public static final String AIRetrofit_IndustryBase_addOmegaCoreDescription_String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addOmegaCoreDescription_String_1");

    public static final String AIRetrofit_IndustryBase_addAlphaCoreDescription_String_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addAlphaCoreDescription_String_0");
    public static final String AIRetrofit_IndustryBase_addAlphaCoreDescription_String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addAlphaCoreDescription_String_1");
    //public static final String AIRetrofit_IndustryBase_addAlphaCoreDescription_String_2 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addAlphaCoreDescription_String_2");

    public static final String AIRetrofit_IndustryBase_addBetaCoreDescription_String_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addBetaCoreDescription_String_0");
    public static final String AIRetrofit_IndustryBase_addBetaCoreDescription_String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addBetaCoreDescription_String_1");

    public static final String AIRetrofit_IndustryBase_addGammaCoreDescription_String_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addGammaCoreDescription_String_0");
    public static final String AIRetrofit_IndustryBase_addGammaCoreDescription_String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addGammaCoreDescription_String_1");



    public static final String AIRetrofit_IndustryBase_getUnavailableReason_String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_getUnavailableReason_String_1");

    public static final String AIRetrofit_IndustryBase_addImproveDesc_String_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addImproveDesc_String_0");
    public static final String AIRetrofit_IndustryBase_addImproveDesc_String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_IndustryBase_addImproveDesc_String_1");
    @Override
    public void apply() {

    }
    @Override
    public boolean showWhenUnavailable() {
        return false;
    }
    @Override
    public boolean isAvailableToBuild(){
        return AIretrofit_canBuild.isAI(market);
    }
    @Override
    public String getUnavailableReason() {
        if (!super.isAvailableToBuild()) return super.getUnavailableReason();
        return AIRetrofit_IndustryBase_getUnavailableReason_String_1;
    }

    public String getOmegaCoreString(Industry.AICoreDescriptionMode mode){
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            return AIRetrofit_IndustryBase_addOmegaCoreDescription_String_1;
        }
        return AIRetrofit_IndustryBase_addOmegaCoreDescription_String_0;
    }
    public String getAlphaCoreString(Industry.AICoreDescriptionMode mode){
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            return AIRetrofit_IndustryBase_addAlphaCoreDescription_String_1;
        }
        return AIRetrofit_IndustryBase_addAlphaCoreDescription_String_0;
    }
    public String getBetaCoreString(Industry.AICoreDescriptionMode mode){
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            return AIRetrofit_IndustryBase_addBetaCoreDescription_String_1;
        }
        return AIRetrofit_IndustryBase_addBetaCoreDescription_String_0;
    }
    public String getGammaCoreString(Industry.AICoreDescriptionMode mode){
        if (mode == AICoreDescriptionMode.MANAGE_CORE_DIALOG_LIST || mode == AICoreDescriptionMode.INDUSTRY_TOOLTIP) {
            return AIRetrofit_IndustryBase_addGammaCoreDescription_String_1;
        }
        return AIRetrofit_IndustryBase_addGammaCoreDescription_String_0;
    }
    public void applyStoryText(TooltipMakerAPI info, ImprovementDescriptionMode mode){
        float initPad = 0f;
        float opad = 10f;
        info.addSpacer(opad);
        if (mode != ImprovementDescriptionMode.INDUSTRY_TOOLTIP) {
            String highlight = AIRetrofit_IndustryBase_addImproveDesc_String_1;
            info.addPara(AIRetrofits_StringHelper.getSplitString(AIRetrofit_IndustryBase_addImproveDesc_String_0,highlight), initPad,
                    Misc.getStoryOptionColor(), highlight);
        }
    }

    @Override
    protected void applyAICoreModifiers() {
        if (aiCoreId != null && aiCoreId.equals(Commodities.OMEGA_CORE)) {
            applyOmegaCoreModifiers();
            return;
        }
        super.applyAICoreModifiers();
    }
    public void addAICoreSection(TooltipMakerAPI tooltip, String coreId, AICoreDescriptionMode mode) {
        if (aiCoreId != null && aiCoreId.equals(Commodities.OMEGA_CORE)) {
            addOmegaCoreDescription(tooltip, mode);
            return;
        }
        super.addAICoreSection(tooltip, coreId, mode);
    }
    protected void applyOmegaCoreModifiers() {

    }
    protected void	addOmegaCoreDescription(TooltipMakerAPI tooltip, Industry.AICoreDescriptionMode mode){

    }
}
