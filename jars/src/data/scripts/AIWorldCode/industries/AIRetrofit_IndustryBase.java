package data.scripts.AIWorldCode.industries;

import com.fs.starfarer.api.impl.campaign.econ.impl.BaseIndustry;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;

public class AIRetrofit_IndustryBase extends BaseIndustry {
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
        return "requires AI market.";
    }
}
