package data.scripts.AIWorldCode.growth;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.impl.campaign.econ.FreeMarket;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import data.scripts.CrewReplacer_Log;

public class AIRetrofits_RemoveUnwantedGrowth {
    private static final String T1FactoryName = "LocalRobotFactory's";
    private static final String T2FactoryName = "FactionWideRobotFactory's";
    private static final String hazzardPayName = "Hazard pay robot factory's";

    static final String[] industrys = {

    };
    static final String[] conditions = {

    };
    static final private String info = "report bug to AIRetrofits";
    static final private String[] activeGrowth = {//HERE <-- this needs to be swapped with data from config
            "population_AIRetrofit_0",
            "population_AIRetrofit_1",
            "population_AIRetrofit_2",
            "population_AIRetrofit_3",
            "AIRetrofits_BasicDroneFactory_0",
            "AIRetrofits_AdvancedDroneFactory_0",
            T1FactoryName,
            T2FactoryName,
            "AIRetrofits_RobotFactoryGrowthMod"
    };
    public static void removeKnownImmigration(MarketAPI market, PopulationComposition incoming){
        PopulationComposition negitiveIn = new PopulationComposition();
        for(Industry a : market.getIndustries()){
            if (market.hasIndustry(a.getSpec().getId()) && market.getIndustry(a.getSpec().getId()).isFunctional()){
                try {
                    MarketImmigrationModifier b = (MarketImmigrationModifier) market.getIndustry(a.getSpec().getId());
                    b.modifyIncoming(market, negitiveIn);
                }catch (Exception e){}
            }
        }
        for(MarketConditionAPI a : market.getConditions()){
            if (market.hasCondition(a.getSpec().getId())){
                CrewReplacer_Log.loging("got conditions of id:" + a.getSpec().getId(),new AIRetrofit_RemoveBaseImrgration(),true);
                try{
                    MarketImmigrationModifier b = (MarketImmigrationModifier) market.getCondition(a.getSpec().getId());
                    b.modifyIncoming(market, negitiveIn);
                }catch (Exception e){
                    CrewReplacer_Log.loging("ERROR: failed to ad to remove mod named: " + a.getSpec().getId(),new AIRetrofit_RemoveBaseImrgration(),true);
                }
            }
        }
        Object[] mods = negitiveIn.getWeight().getFlatMods().keySet().toArray();
        //HashMap<String, MutableStat.StatMod> mods = temp.getWeight().getFlatMods().values().toArray();
        boolean noOut;
        for(int a = 0;a < mods.length; a++){
            noOut = false;
            for(String b:activeGrowth){
                if(b.equals(mods[a].toString())){
                    noOut = true;
                    break;
                }
            }
            if(!noOut) {
                CrewReplacer_Log.loging("adding negative growth for industry/conditional growth named: " + mods[a].toString(),new AIRetrofits_RemoveUnwantedGrowth(),true);
                MutableStat.StatMod temp2 = negitiveIn.getWeight().getFlatMods().get(mods[a].toString());
                CrewReplacer_Log.loging("adding negative mod: " + temp2, new AIRetrofit_RemoveBaseImrgration(), true);
                String a0 = "AIRetrofit_Remove_" + temp2.getSource();
                CrewReplacer_Log.loging("negative growth named:" + a0,new AIRetrofits_RemoveUnwantedGrowth(),true);
                float b = -temp2.getValue();
                incoming.getWeight().modifyFlat(a0, b, info);
            }
        }

    }
    public static void removeGrowthOther(MarketAPI market, PopulationComposition incoming){
        //PopulationComposition incoming = market.getIncoming();
        String[] activeGrowth = {//HERE <-- this needs to be swapped with data from config
                "population_AIRetrofit_0",
                "population_AIRetrofit_1",
                "population_AIRetrofit_2",
                "population_AIRetrofit_3",
                "AIRetrofits_BasicDroneFactory_0",
                "AIRetrofits_AdvancedDroneFactory_0",
                T1FactoryName,
                T2FactoryName,
                hazzardPayName,
                "AIRetrofits_RobotFactoryGrowthMod"
        };
        CrewReplacer_Log.loging("removeing growth from market named: " + market.getName(),new AIRetrofits_RemoveUnwantedGrowth(),true);
        int a = 0;
        while(incoming.getWeight().getFlatMods().keySet().size() != 0 && a < incoming.getWeight().getFlatMods().keySet().size()){
            boolean out = true;
            String m = incoming.getWeight().getFlatMods().keySet().toArray()[a].toString();
            for (String s : activeGrowth) {
                if (m.equals(s)) {
                    a++;
                    out = false;
                    //incoming.getWeight().getFlatStatMod(s).getDesc()
                    break;
                }
            }
            if(out) {
                CrewReplacer_Log.loging("Removed growth named: " + incoming.getWeight().getFlatMods().keySet().toArray()[a].toString(),new AIRetrofits_RemoveUnwantedGrowth(),true);
                incoming.getWeight().unmodify(incoming.getWeight().getFlatMods().keySet().toArray()[a].toString());
            }
        }
    }
}
