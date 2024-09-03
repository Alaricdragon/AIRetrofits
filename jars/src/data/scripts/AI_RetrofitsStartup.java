package data.scripts;
import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.thoughtworks.xstream.XStream;
import data.scripts.AIWorldCode.FoundAMarket.AIRetrofit_MarketRetrofit_CustomMarketFounder;
import data.scripts.AIWorldCode.market_listiners.AIRetrofit_MakretListener;
import data.scripts.AIWorldCode.market_listiners.AIRetrofit_econUpdateListiner;
import data.scripts.AIWorldCode.Fleet.BaseCampainPlugin.AIRetrofit_FleetPlugin;
import data.scripts.AIWorldCode.Fleet.listiner.AIRetrofit_FleetListener;
import data.scripts.AIWorldCode.Fleet.setDataLists;
import data.scripts.combatabilityPatches.AIRetrofits_InitCombatabilityPatches;
import data.scripts.jsonDataReader.AIRetrofits_JsonReaderBase;
import data.scripts.memory.AIRetrofit_ItemFoundMemory;
import data.scripts.startupData.*;
import org.json.JSONException;

import java.io.IOException;

public class AI_RetrofitsStartup extends BaseModPlugin {
    public void onNewGame(){
    }

    @Override
    public void configureXStream(XStream x) {
        super.configureXStream(x);
    }

    @Override
    public void onApplicationLoad() throws JSONException, IOException {
        load0();
        load1();
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
        AIRetrofit_ItemFoundMemory.onGameLoad();
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
    private void getTestingData(){
        /*
        IntelManagerAPI manager = Global.getSector().getIntelManager();
        List<IntelInfoPlugin> intell = manager.getIntel();
        //intell.get(0).getSmallDescriptionTitle();
        //intell.get(0).getMapLocation();
        CustomPanelAPI panel= null;
        intell.get(0).createLargeDescription(panel,0,0);*/
    }
    private void load0() throws IOException {
        AIRetrofit_Log.loging("attempting to preform base startup protocols",this,true);
        try {
            AIRetrofits_JsonReaderBase.startup();
            AIRetrofits_Constants_3.apply();
        } catch (JSONException e) {
            AIRetrofit_Log.loging("got an error of: "+e.toString(),this,true);
            e.printStackTrace();
        } catch (Exception e){
            AIRetrofit_Log.loging("got an error of: "+e.toString(),this,true);
            e.printStackTrace();
        }
    }
    private void load1(){
        AIRetrofit_Log.loging("attempting to preform secondary startup protocols",this,true);
        AIRetrofits_Startup_RobotForge.apply();
        //setDataLists.init();
        AIRetrofits_Startup_CreatePeople.apply();
        AIRetrofits_Startup_MarketRetrofits.apply();
        AIRetrofits_Startup_RobotTypesCalculater.apply();
        AIRetrofits_Startup_CrewReplacer.apply();
        AIRetrofits_InitCombatabilityPatches.onApplicationLoad();
    }
    private void load2(){

    }
}