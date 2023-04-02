package data.scripts.AIWorldCode;

import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.RepLevel;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
//NOTHING here is implemented yet. yet...
public class AIRetrofit_TransferMarket {
    public static void changeData(MarketAPI market, FactionAPI lastOwner, FactionAPI newOwner){
        //RepLevel cats ;
        //newOwner.adjustRelationship(lastOwner.getId(),loveHateAIWorlds(lastOwner),);

    }
    public static String[] factions = {
            "faction names here"
    };
    public static int[] loveHateAIWorlds = {
            -1//said factions love for AI worlds here. default is -1.
    };
    //-1 is hate, 0 is nertural, 1 is love.
    //if less then 0, i want to purge the AI world. more then 0, i want it to live.
    public static int loveHateAIWorlds(FactionAPI faction){
        for(int a = 0; a < factions.length; a++){
            if(factions[a].equals(faction.getId())){
                return loveHateAIWorlds[a];
            }
        }
        return -1;
    }
}
