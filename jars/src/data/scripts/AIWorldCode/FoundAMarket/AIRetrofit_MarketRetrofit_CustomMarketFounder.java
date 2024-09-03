package data.scripts.AIWorldCode.FoundAMarket;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.impl.campaign.ids.Commodities;
import data.scripts.AIWorldCode.AIRetrofits_ChangePeople;
import data.scripts.AIWorldCode.SupportCode.AIretrofit_canBuild;
import data.scripts.customMarketFounding.MarketRetrofits_MarketFounder;
import data.scripts.customMarketFounding.MarketRetrofits_MarketFounderMasterList;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;
import data.scripts.startupData.AIRetrofits_Constants_3;

import java.util.LinkedHashMap;
import java.util.Map;

public class AIRetrofit_MarketRetrofit_CustomMarketFounder extends MarketRetrofits_MarketFounder {
    public static void setMarketFounder(){
        String ID = "AIRetrofits_RoboticWorld";
        String name = AIRetrofits_StringGetterProtection.getString("AIRetrofit_MarketFounder_Name");
        float order = Global.getSettings().getFloat("AIRetrofit_MarketFounder_Order");
        new AIRetrofit_MarketRetrofit_CustomMarketFounder(ID,name,order);
    }
    public AIRetrofit_MarketRetrofit_CustomMarketFounder(String ID, String name,float order) {
        super(ID, name,order);
        setData();
        MarketRetrofits_MarketFounderMasterList.addOrReplaceMarketFounder(this);
    }
    public void setData(){
        this.canFoundWithoutJumpPonits = Global.getSettings().getBoolean("AIRetrofit_MarketFounder_CanFoundInCutOffSystem");
        this.canFoundWithHostileActivity = Global.getSettings().getBoolean("AIRetrofit_MarketFounder_CanFoundWellUnderThreat");
        this.MarketFounderDescription = AIRetrofits_StringGetterProtection.getString("AIRetrofit_MarketFounder_Description");
        this.MarketFounderHasDescription = true;
    }

    int reqAICore = (int) AIRetrofits_Constants_3.FoundAMarket_reqAICore;
    int reqWorker = (int) AIRetrofits_Constants_3.FoundAMarket_reqWorker;
    int reqSupply = (int) AIRetrofits_Constants_3.FoundAMarket_reqSupply;
    int reqMachinery = (int) AIRetrofits_Constants_3.FoundAMarket_reqMachinery;
    @Override
    public Map<String, Integer> getOutpostConsumed(SectorEntityToken planet) {
        Map<String, Integer> result = new LinkedHashMap<String, Integer>();
        result.put(AIRetrofits_Constants_3.Commodity_T0_WorkerDrone, reqWorker);
        result.put(Commodities.HEAVY_MACHINERY, reqMachinery);
        result.put(Commodities.SUPPLIES, reqSupply);
        result.put(AIRetrofits_Constants_3.Commodity_SubCommandNode,reqAICore);
        return result;
    }

    @Override
    public void runCodeAfterFoundingMarket(SectorEntityToken planet) {
        super.runCodeAfterFoundingMarket(planet);
        AIRetrofits_ChangePeople.changePeopleMarket(planet.getMarket());
        planet.getMarket().addCondition(AIRetrofits_Constants_3.Market_Condition);
    }

    @Override
    public boolean showOption(SectorEntityToken planet, boolean hostileActivity, boolean cutOffFromHyperspace) {
        return super.showOption(planet, hostileActivity, cutOffFromHyperspace) && hasReqStuffToCreateMarket();
    }

    @Override
    public boolean canEstablishAMarketHere(SectorEntityToken planet, boolean hostileActivity, boolean cutOffFromHyperspace) {
        return hasReqStuffToCreateMarket() && super.canEstablishAMarketHere(planet, hostileActivity, cutOffFromHyperspace);
    }
    private static boolean can = AIRetrofits_Constants_3.Market_EnableMarketFetures;
    public static boolean hasReqStuffToCreateMarket(){
        return AIretrofit_canBuild.canFoundMarket();
    }
}
