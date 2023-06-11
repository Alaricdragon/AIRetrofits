package data.scripts.notifications;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.comm.IntelInfoPlugin;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.ids.Tags;
import com.fs.starfarer.api.impl.campaign.intel.BaseIntelPlugin;
import com.fs.starfarer.api.impl.campaign.intel.misc.FleetLogIntel;
import com.fs.starfarer.api.impl.campaign.intel.misc.ProductionReportIntel;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.SectorMapAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import data.scripts.AIRetrofit_Log;
import data.scripts.AIWorldCode.market_listiners.AIRetrofit_MakretListener;
import data.scripts.notifications.support.shipyard.AIRetrofit_ShipYard_UpgradeList;

import java.util.Set;

public class AIRetrofit_ShipyardNotification extends FleetLogIntel {
    protected static String name = "Automated Ship Installation Center Production Report";
    public AIRetrofit_ShipYard_UpgradeList upgradeData;
    public void setUpgradeData(AIRetrofit_ShipYard_UpgradeList upgradeData){
        this.upgradeData = upgradeData;
    }
    @Override
    public java.util.Set<java.lang.String> getIntelTags(SectorMapAPI map){
        Set<String> a = super.getIntelTags(map);
        a.add(Tags.INTEL_PRODUCTION);
        //a.add(Tags.INTEL_CONTACTS);
        return a;
    }
    @Override
    public String getName(){
        return name;
    }
    @Override
    public String getSortString() {
        return "Production";
    }
    @Override
    public String getSmallDescriptionTitle(){
        return name;
    }/*
    @Override
    public boolean shouldRemoveIntel(){
        return true;
    }*/
    @Override
    public void createIntelInfo(TooltipMakerAPI info, IntelInfoPlugin.ListInfoMode mode){
        //info.showShips(cargo.getMothballedShips().getMembersListCopy(), 20, true, opad);
        info.addPara(name,5);
    }
    @Override
    public void createSmallDescription(TooltipMakerAPI info, float width, float height){
        displayAIRetrofit_ShipYardNotification(info,upgradeData);
    }
    @Override
    public void createLargeDescription(CustomPanelAPI panel,
                                       float width,
                                       float height){
        displayAIRetrofit_ShipYardNotification(panel.createUIElement(5,5,true),upgradeData);
    }
    @Override
    public boolean shouldRemoveIntel() {
        if (isImportant()) return false;
        if (getDaysSincePlayerVisible() < 30) return false;
        return true;//super.shouldRemoveIntel();
    }
    @Override
    public String getIcon(){
        return Global.getSector().getEconomy().getCommoditySpec("AIretrofit_SubCommandNode").getIconName();
    }
    public static void displayAIRetrofit_ShipYardNotification(TooltipMakerAPI info,AIRetrofit_ShipYard_UpgradeList upgrades){
        //
        try {
            upgrades.display(info);
        }catch(Exception e){
            AIRetrofit_Log.loging("failed to displayAIRetrofit_ShipyardNotification. error: "+e,new AIRetrofit_Log(),true);
        }
    }
}
