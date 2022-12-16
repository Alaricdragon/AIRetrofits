package data.scripts.AIWorldCode.supplyDemandClasses.old;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.util.Pair;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandChange;
public class AIRetrofit_SupplyDemand_Combat extends crewReplacer_SupplyDemandChange {
    private static int requiredCombat = 3;
    public AIRetrofit_SupplyDemand_Combat(String nametemp, boolean supplytemp) {
        super(nametemp, supplytemp);
    }
    @Override
    public void applyExtraData(Industry industry, String ID){
        if(!industry.getSpec().getId().equals("population")){
            return;
        }
        industry.getDemand("marines").getQuantity().modifyFlat(ID,requiredCombat,"required to combat hostile conditions.");
    }
    @Override
    public void undoExtraData(Industry industry, String ID){
        industry.getDemand("marines").getQuantity().unmodify(ID);
    }

}
