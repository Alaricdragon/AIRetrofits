package data.scripts.AIWorldCode.growth;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.MarketConditionAPI;
import com.fs.starfarer.api.campaign.econ.MarketImmigrationModifier;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.impl.campaign.econ.FreeMarket;
import com.fs.starfarer.api.impl.campaign.population.PopulationComposition;
import data.scripts.AIRetrofit_Log;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.ArrayList;
//import data.scripts.CrewReplacer_Log;

public class AIRetrofits_RemoveUnwantedGrowth {
    static final private AIRetrofits_RemoveUnwantedGrowth logClass = new AIRetrofits_RemoveUnwantedGrowth();
    static final private String info = "report bug to AIRetrofits";
    static final private String[] activeGrowth = AIRetrofits_Constants.Market_WhiteListedGrowthMods;
    public static void removeKnownImmigration(MarketAPI market, PopulationComposition incoming){
        removeKnownImmigration(market,incoming,new ArrayList<String>());
    }
    public static void removeKnownImmigration(MarketAPI market, PopulationComposition incoming,ArrayList<String> gone){
        PopulationComposition negitiveIn = new PopulationComposition();
        for(Industry a : market.getIndustries()){
            if (market.hasIndustry(a.getSpec().getId()) && market.getIndustry(a.getSpec().getId()).isFunctional()){
                try {
                    if(a.getSpec() instanceof MarketImmigrationModifier) {
                        MarketImmigrationModifier b = (MarketImmigrationModifier) market.getIndustry(a.getSpec().getId());
                        b.modifyIncoming(market, negitiveIn);
                        AIRetrofit_Log.loging("got industry of id:" + a.getSpec().getId() + " and am removing it relitive growth.",logClass,AIRetrofits_Constants.Market_EnableLogs);
                    }else{
                        AIRetrofit_Log.loging("got industry of id: " + a.getSpec().getId() + "and have nothing to remove here",logClass,AIRetrofits_Constants.Market_EnableLogs);
                    }
                }catch (Exception e){
                    AIRetrofit_Log.loging("ERROR: failed to ad to remove mod named: " + a.getSpec().getId(),logClass,true);
                }
            }
        }
        for(MarketConditionAPI a : market.getConditions()){
            if (market.hasCondition(a.getSpec().getId()) && !a.getSpec().getId().equals(AIRetrofits_Constants.Market_Condition)){
                try{
                    if(a.getPlugin() instanceof MarketImmigrationModifier){
                        MarketImmigrationModifier b = (MarketImmigrationModifier) a.getPlugin();
                        b.modifyIncoming(market, negitiveIn);
                        AIRetrofit_Log.loging("got conditions of id:" + a.getSpec().getId() + " and am removing it relitive growth.",logClass,AIRetrofits_Constants.Market_EnableLogs);
                    }else{
                        AIRetrofit_Log.loging("got conditions of id: " + a.getSpec().getId() + "and have nothing to remove here",logClass,AIRetrofits_Constants.Market_EnableLogs);
                    }
                }catch (Exception e){
                    AIRetrofit_Log.loging("ERROR: failed to ad to remove mod named: " + a.getSpec().getId(),logClass,true);
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
            for(String b:gone){
                if(b.equals(mods[a].toString())){
                    AIRetrofit_Log.loging("avoiding removing " + b + " because it was removed elsewere.",logClass,AIRetrofits_Constants.Market_EnableLogs);
                    noOut = true;
                    break;
                }
            }
            if(!noOut) {
                AIRetrofit_Log.loging("adding negative growth for industry/conditional growth named: " + mods[a].toString(),logClass,AIRetrofits_Constants.Market_EnableLogs);
                MutableStat.StatMod temp2 = negitiveIn.getWeight().getFlatMods().get(mods[a].toString());
                AIRetrofit_Log.loging("adding negative mod: " + temp2, logClass,AIRetrofits_Constants.Market_EnableLogs);
                String a0 = "AIRetrofit_Remove_" + temp2.getSource();
                AIRetrofit_Log.loging("negative growth named:" + a0,logClass,AIRetrofits_Constants.Market_EnableLogs);
                float b = -temp2.getValue();
                incoming.getWeight().modifyFlat(a0, b, info);
            }
        }

    }
    public static ArrayList<String> removeGrowthOther(MarketAPI market, PopulationComposition incoming){
        AIRetrofit_Log.loging("removeing growth from market named: " + market.getName(),logClass,AIRetrofits_Constants.Market_EnableLogs);
        ArrayList<String> removedTemp = new ArrayList<>();
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
                AIRetrofit_Log.loging("Removed growth named: " + incoming.getWeight().getFlatMods().keySet().toArray()[a].toString(),logClass,AIRetrofits_Constants.Market_EnableLogs);
                removedTemp.add(incoming.getWeight().getFlatMods().keySet().toArray()[a].toString());
                incoming.getWeight().unmodify(incoming.getWeight().getFlatMods().keySet().toArray()[a].toString());
            }
        }
        return removedTemp;
    }
}
