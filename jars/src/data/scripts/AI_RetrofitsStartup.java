package data.scripts;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import data.scripts.AIWorldCode.FoundAMarket.AIRetrofit_MarketRetrofit_CustomMarketFounder;
import data.scripts.AIWorldCode.market_listiners.AIRetrofit_MakretListener;
import data.scripts.AIWorldCode.market_listiners.AIRetrofit_econUpdateListiner;
import data.scripts.AIWorldCode.supplyDemandClasses.*;
import data.scripts.AIWorldCode.Fleet.BaseCampainPlugin.AIRetrofit_FleetPlugin;
import data.scripts.AIWorldCode.Fleet.listiner.AIRetrofit_FleetListener;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.combatabilityPatches.AIRetrofits_InitCombatabilityPatches;
import data.scripts.notifications.AIRetrofit_ShipyardNotification;
import data.scripts.startupData.AIRetrofits_Startup_CreatePeople;
import data.scripts.startupData.AIRetrofits_Startup_CrewReplacer;
import data.scripts.startupData.AIRetrofits_Startup_MarketRetrofits;
import data.scripts.startupData.AIRetrofits_Startup_RobotForge;
import data.scripts.supplyDemandLibary.changes.MarketRetrofit_CCSwapDemand;
import data.scripts.supplyDemandLibary.changes.MarketRetrofit_CCSwapSupply;

public class AI_RetrofitsStartup extends BaseModPlugin {
    public void onNewGame(){
    }
    @Override
    public void onApplicationLoad() {
        /*
        XStream x = new XStream();
        x.alias("PLStatMarines", PLStatMarines.class);*/
        //Global.getSector().getEconomy().getCommoditySpec().
        //crew_replacer.addCrewType("AIretrofit_WorkerDrone");
        AIRetrofits_Startup_RobotForge.apply();
        setDataLists.init();
        AIRetrofits_Startup_CreatePeople.apply();
        AIRetrofits_Startup_MarketRetrofits.apply();

        AIRetrofits_Startup_CrewReplacer.apply();
        AIRetrofits_InitCombatabilityPatches.onApplicationLoad();
    }
    @Override
    public void onGameLoad(boolean newGame) {
        //something wrong with crewreplacer addCrewType. causes out of bounds
        //crew_replacer_start_outdated();
        super.onGameLoad(newGame);
        AIMarketModSet();
        descriptions();
        AIRetrofits_AbilityAndHullmodAdding.addAIRetrofits();
        AIRetrofits_InitCombatabilityPatches.onGameLoad(newGame);
    }
    private void AIMarketModSet(){
        //new AIRetrofit_FleetListener(false);//like this?
        Global.getSector().addTransientListener(new AIRetrofit_FleetListener(false));
        Global.getSector().registerPlugin(new AIRetrofit_FleetPlugin());
        //HERE! this is here until marketRetrofits is online and working =)
        AIRetrofit_econUpdateListiner a = new AIRetrofit_econUpdateListiner();
        Global.getSector().getEconomy().addUpdateListener(a);

        Global.getSector().addTransientListener(new AIRetrofit_MakretListener(false));

        AIRetrofit_MarketRetrofit_CustomMarketFounder.setMarketFounder();
    }
    private void descriptions(){
        //Global.getSector().getEconomy().getCommoditySpec("")
        //Global.getSector().addTransientListener(new AIRetrofit_RobotDescriptions());
        //Global.getSector().getListenerManager().addListener(new AIRetrofit_RobotDescriptions(),true);
        //Global.getSector().getListenerManager().addListener(new AIRetrofit_RobotDescriptions().getClass(),true);
        //if(!Global.getSector().getListenerManager().hasListener(AIRetrofit_RobotDescriptions.getCommodityTooltipModifier())){
        Global.getSector().getListenerManager().addListener(AIRetrofit_RobotDescriptions.getCommodityTooltipModifier(),true);
        //}

    }
}