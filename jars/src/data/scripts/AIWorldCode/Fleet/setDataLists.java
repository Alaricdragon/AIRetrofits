package data.scripts.AIWorldCode.Fleet;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;
import data.scripts.startupData.AIRetrofits_Constants;

import java.util.ArrayList;

public class setDataLists {
    private static boolean can = AIRetrofits_Constants.Market_EnableMarketFetures;
    static public ArrayList<String> CaptionFirstNames;
    static public ArrayList<String> CaptionLastNames;
    static public ArrayList<String> CaptionPortraits;
    static public ArrayList<String> AINodeProductionFacility_numCores;
    static public ArrayList<Float> AINodeProductionFacility_numCoreThresholds;
    static public ArrayList<String> AINodeProductionFacility_powerCores;
    static public ArrayList<Float> AINodeProductionFacility_powerCoreThresholds;
    static private String CapFirstName = "AIRetofit_AIOfficerFirstName";
    static private String CapLastName = "AIRetofit_AIOfficerLastName";
    static private String CapPort = "AIRetofit_AIOfficerPortrait";
    static private String numCoresName = "AIRetrofit_AINodeProductionFacility_numCores_";
    static private String numThresholdsName = "AIRetrofit_AINodeProductionFacility_numCoreThresholds_";
    static private String powerCoresName = "AIRetrofit_AINodeProductionFacility_powerCores_";
    static private String powerCoreThresholdsName = "AIRetrofit_AINodeProductionFacility_powerCoreThresholds_";
    static String Condition = AIRetrofits_Constants.Market_Condition;
    public static boolean fleetMod(CampaignFleetAPI fleet){
        MarketAPI market = Global.getSector().getEconomy().getMarket(fleet.getMemory().getString(MemFlags.MEMORY_KEY_SOURCE_MARKET));
        return (market != null && market.hasCondition(Condition) && market.getFaction().getId().equals(fleet.getFaction().getId()) && can);
    }
    public static void init(){
        CaptionFirstNames = SetArrayString(CapFirstName);
        CaptionLastNames = SetArrayString(CapLastName);
        CaptionPortraits = SetArrayString(CapPort);
        AINodeProductionFacility_numCores = SetArrayString(numCoresName);
        AINodeProductionFacility_numCoreThresholds = SetArrayFloat(numThresholdsName);
        AINodeProductionFacility_powerCores = SetArrayString(powerCoresName);
        AINodeProductionFacility_powerCoreThresholds = SetArrayFloat(powerCoreThresholdsName);
    }
    public static String getRandom(int type){
        switch (type){
            case 0:
                return getRandom(CaptionFirstNames);
            case 1:
                return getRandom(CaptionLastNames);
            case 2:
                return getRandom(CaptionPortraits);
        }
        return "";
    }
    public static boolean matches(int type,String input){
        switch (type){
            case 0:
                return matches(CaptionFirstNames,input);
            case 1:
                return matches(CaptionLastNames,input);
            case 2:
                return matches(CaptionPortraits,input);
        }
        return false;
    }
    private static boolean matches(ArrayList<String> list,String input){
        for(int b = 0; b < list.size(); b++){
            String a = list.get(b);
            if(a.equals(input)){
                return true;
            }
        }
        return false;
    }
    private static String getRandom(ArrayList<String> list){
        return list.get((int)(Math.random() * list.size()));
    }

    private static ArrayList<Float> SetArrayFloat(String name){
        ArrayList<Float> out = new ArrayList<Float>();
        int a = 0;
        while(true){
            try{
                out.add(Global.getSettings().getFloat(name + a));
                a++;
            }catch (Exception e){
                break;
            }
        }
        return out;
    }
    private static ArrayList<String> SetArrayString(String name){
        ArrayList<String> out = new ArrayList<String>();
        int a = 0;
        while(true){
            try{
                out.add(Global.getSettings().getString(name + a));
                a++;
            }catch (Exception e){
                break;
            }
        }
        return out;
    }
}
