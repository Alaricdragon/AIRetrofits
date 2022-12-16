package data.scripts.AIWorldCode.supplyDemandClasses.old;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandSet;

import java.util.ArrayList;

public class AIRetrofit_SuplyDemandSet extends crewReplacer_SupplyDemandSet {
    private static boolean can = Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    public AIRetrofit_SuplyDemandSet(String nametemp) {
        super(nametemp);
    }

    @Override
    public boolean active(MarketAPI market){
        return can && market.hasCondition("AIRetrofit_AIPop");
    }
}
