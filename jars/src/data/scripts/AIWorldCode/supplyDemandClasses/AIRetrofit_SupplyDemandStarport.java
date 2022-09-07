package data.scripts.AIWorldCode.supplyDemandClasses;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.util.Pair;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandChange;

public class AIRetrofit_SupplyDemandStarport extends crewReplacer_SupplyDemandChange {
    public AIRetrofit_SupplyDemandStarport(String nametemp, boolean supplytemp) {
        super(nametemp, supplytemp);
    }
    @Override
    public void applyExtraData(Industry industry, String ID){
        if(!(industry.getSpec().getId().equals("spaceport") || industry.getSpec().getId().equals("megaport"))){
            return;
        }
        industry.getSupply("AIretrofit_WorkerDrone").getQuantity().modifyMult(ID,0);

    }
    @Override
    public void undoExtraData(Industry industry, String ID){
        industry.getSupply("AIretrofit_WorkerDrone").getQuantity().unmodify(ID);
    }
}
