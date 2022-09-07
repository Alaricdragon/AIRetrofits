package data.scripts.AIWorldCode.Fleet;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.fleet.FleetAPI;
import com.fs.starfarer.api.impl.campaign.ids.MemFlags;

import java.util.ArrayList;
import java.util.Set;

public class setDataLists {
    private static boolean can = Global.getSettings().getBoolean("AIRetrofits_EnableColonyFeatures");
    static public ArrayList<String> CaptionFirstNames;
    static public ArrayList<String> CaptionLastNames;
    static public ArrayList<String> CaptionPortraits;
    static private String CapFirstName = "AIRetofit_AIOfficerFirstName";
    static private String CapLastName = "AIRetofit_AIOfficerLastName";
    static private String CapPort = "AIRetofit_AIOfficerPortrait";
    static String Condition = "AIRetrofit_AIPop";
    public static boolean fleetMod(CampaignFleetAPI fleet){
        MarketAPI market = Global.getSector().getEconomy().getMarket(fleet.getMemory().getString(MemFlags.MEMORY_KEY_SOURCE_MARKET));
        return (market != null && market.hasCondition(Condition) && market.getFaction().getId().equals(fleet.getFaction().getId()) && can);
    }
    public static void init(){
        CaptionFirstNames = SetArray(CapFirstName);
        CaptionLastNames = SetArray(CapLastName);
        CaptionPortraits = SetArray(CapPort);
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
    private static String getRandom(ArrayList<String> list){
        return list.get((int)(Math.random() * list.size()));
    }
    private static ArrayList<String> SetArray(String name){
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
