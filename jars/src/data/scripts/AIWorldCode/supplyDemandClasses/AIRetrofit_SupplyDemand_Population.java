package data.scripts.AIWorldCode.supplyDemandClasses;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.MarketConditionSpecAPI;
import com.fs.starfarer.api.util.Pair;
import data.scripts.supplyDemandLibary.crewReplacer_SupplyDemandChange;

public class AIRetrofit_SupplyDemand_Population extends crewReplacer_SupplyDemandChange {
    static String C1 = "AIretrofit_maintainsPacts";//replaces food
    static String C2 = "AIretrofit_CommandRely";//replaces DOMESTIC_GOODS
    static String C3 = "AIretrofit_humanInterfaceNode";//replaces LUXURY_GOODS
    static String C4 = "AIretrofit_SurveyDrone";//replaces DRUGS
    static String C5 = "AIretrofit_roboticReplacementParts";//replaces ORGANS
    static String C6 = "AIretrofit_SubCommandNode";//replaces ORGANICS

    static String id0 = "population_AIRetrofit_0";
    static String id1 = "population_AIRetrofit_1";
    static String id2 = "population_AIRetrofit_2";
    //static String id3 = "population_AIRetrofit_2";
    public AIRetrofit_SupplyDemand_Population(String nametemp, boolean supplytemp) {
        super(nametemp, supplytemp);
    }
    @Override
    public void applyExtraData(Industry industry, String ID){
        if(!industry.getSpec().getId().equals("population")){
            return;
        }
        Pair<String, Integer> deficit = industry.getMaxDeficit(C1);
        if (deficit.two > 0) {
            industry.getMarket().getIncoming().getWeight().modifyFlat(id0,deficit.two * -1,Global.getSector().getEconomy().getCommoditySpec(C1).getName() + " demand not met");
        } else {
            industry.getMarket().getIncoming().getWeight().unmodify(id0);
        }
        deficit = industry.getMaxDeficit(C5);
        if (deficit.two > 0) {
            industry.getMarket().getIncoming().getWeight().modifyFlat(id1,deficit.two * -1,Global.getSector().getEconomy().getCommoditySpec(C5).getName() + " demand not met");
        } else {
            industry.getMarket().getIncoming().getWeight().unmodify(id1);
        }

        industry.getMarket().getIncoming().getWeight().modifyFlat(id2,industry.getMarket().getSize(), " base robot production");
    }
    @Override
    public void undoExtraData(Industry industry, String ID){
        industry.getMarket().getIncoming().getWeight().unmodify(id0);
        industry.getMarket().getIncoming().getWeight().unmodify(id1);
        industry.getMarket().getIncoming().getWeight().unmodify(id2);
    }

    public void thing(){

    }
}
