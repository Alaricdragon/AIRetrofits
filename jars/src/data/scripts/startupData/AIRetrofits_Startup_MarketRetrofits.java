package data.scripts.startupData;

import data.scripts.AIWorldCode.supplyDemandClasses.AIRetrofit_CCSetMain;
import data.scripts.AIWorldCode.supplyDemandClasses.AIRetrofit_CCSetSecondary;
import data.scripts.supplyDemandLibary.changes.MarketRetrofit_CCSwapDemand;
import data.scripts.supplyDemandLibary.changes.MarketRetrofit_CCSwapSupply;

public class AIRetrofits_Startup_MarketRetrofits {
    public static void apply(){
        AISupplyDemandSet();
    }
    private static void AISupplyDemandSet(){
        AIRetrofit_CCSetMain a = new AIRetrofit_CCSetMain("AIRetrofits_Main");
        MarketRetrofit_CCSwapDemand d = new MarketRetrofit_CCSwapDemand("AIRetrofits_DCrew",0,"crew","AIretrofit_WorkerDrone");
        a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_Dmarines",0,"marines","AIretrofit_CombatDrone");
        a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_DFood",0,"food","AIretrofit_maintainsPacts");
        a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_Ddomestic_goods",0,"domestic_goods","AIretrofit_CommandRely");
        a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_Dluxury_goods",0,"luxury_goods","AIretrofit_humanInterfaceNode");
        a.addChange(d);
        d = new MarketRetrofit_CCSwapDemand("AIRetrofits_Ddrugs",0,"drugs","AIretrofit_SurveyDrone");
        a.addChange(d);
        MarketRetrofit_CCSwapSupply e = new MarketRetrofit_CCSwapSupply("AIRetrofits_SCrew",0,"crew","AIretrofit_WorkerDrone");
        a.addChange(e);
        e = new MarketRetrofit_CCSwapSupply("AIRetrofits_Smarines",0,"marines","AIretrofit_CombatDrone");
        a.addChange(e);
        //a.addChange();



        AIRetrofit_CCSetSecondary b = new AIRetrofit_CCSetSecondary("AIRetrofit_Second");
        e = new MarketRetrofit_CCSwapSupply("AIRetrofits_Spaceport_SCrew",0,"crew","AIretrofit_WorkerDrone");
        e.modifyMult("a",0);
        b.addChange(e);
        b.applyToIndustry("spaceport");
    }
}
