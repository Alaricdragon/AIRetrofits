package data.scripts.AIWorldCode.industries.specal;

import com.fs.starfarer.api.Global;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;
import data.scripts.AIWorldCode.industries.base.AIRetrofit_IndustryBase;

public class AIRetrofits_tempthing extends AIRetrofit_IndustryBase {
    public boolean isAvailableToBuild(){
        return Global.getSector().getClock().getCycle() <= 206;
    }
}
