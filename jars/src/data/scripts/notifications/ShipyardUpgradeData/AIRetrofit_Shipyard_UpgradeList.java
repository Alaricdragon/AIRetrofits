package data.scripts.notifications.ShipyardUpgradeData;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.ResourceCostPanelAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Highlights;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofit_Log;
import data.scripts.notifications.AIRetrofit_ShipyardNotification;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.ArrayList;

import static data.scripts.startupData.AIRetrofits_Constants.ASIC_hullmods;

public class AIRetrofit_Shipyard_UpgradeList {
    public ArrayList<AIRetrofits_Shipyard_UpgradeTypes> Types = new ArrayList<>();
    public void addLocation(String market,AIRetrofit_Shipyard_UpgradeShips ships){
        if(ships == null || ships.ships.size() == 0){return;}
        while(Types.size() <= ships.type){
            Types.add(new AIRetrofits_Shipyard_UpgradeTypes(Types.size()));
        }
        Types.get(ships.type).addLocation(market,ships);
    }
    public void runNotification(){
        AIRetrofit_ShipyardNotification intel = new AIRetrofit_ShipyardNotification(this);

        Global.getSector().getCampaignUI().addMessage(intel);
    }
    public void applyCosts(){
        if(Types.size() == 0){
            return;
        }
        float cost = 0;
        float bonusXP = 0;
        for(int a2 = 0; a2 < Types.size(); a2++){
            AIRetrofits_Shipyard_UpgradeTypes a = Types.get(a2);
            //info.addPara("upgraded with " + AIRetrofits_Constants.ASIC_hullmods[a.type],10);
            float[] temp = a.getCost();
            cost += temp[0];
            bonusXP += temp[1];
        }

        if(cost != 0){
            Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(cost);
        }
        if(bonusXP != 0){
            long XpPerStory = Global.getSector().getPlayerStats().getBonusXPForSpendingStoryPointBeforeSpendingIt();
            bonusXP*=XpPerStory;
            TextPanelAPI a = new TempText();//info.addTextField(0,0);
            Global.getSector().getPlayerStats().addBonusXP((long)bonusXP,false,a,true);
        }
    }
    public void display(TooltipMakerAPI info){
        if(Types.size() == 0){
            return;
        }
        float pad = 5;
        float cost = 0;
        float bonusXP = 0;
        Color highlight = Misc.getHighlightColor();
        for(int a2 = 0; a2 < Types.size(); a2++){
            AIRetrofits_Shipyard_UpgradeTypes a = Types.get(a2);
            //info.addPara("upgraded with " + AIRetrofits_Constants.ASIC_hullmods[a.type],10);
            float[] temp = a.display(info);
            cost += temp[0];
            bonusXP += temp[1];
        }

        if(cost != 0){
            String[] exstra = new String[]{"" + cost};
            String text = AIRetrofits_Constants.ASIC_NotificationCredits;
            info.addPara(text,pad,highlight,exstra);
            //Global.getSector().getPlayerFleet().getCargo().getCredits().subtract(cost);
        }
        if(bonusXP != 0){
            long XpPerStory = Global.getSector().getPlayerStats().getBonusXPForSpendingStoryPointBeforeSpendingIt();
            bonusXP*=XpPerStory;
            highlight = Misc.getStoryOptionColor();
            AIRetrofit_Log.loging( bonusXP + " bonusXP from AIRetrofitShipyard",this,true);
            String[] exstra = new String[]{"" + (int)bonusXP};
            String text = AIRetrofits_Constants.ASIC_NotificationBonusXP;
            info.addPara(text,pad,highlight,exstra);
            //TextPanelAPI a = new TempText();//info.addTextField(0,0);
            //Global.getSector().getPlayerStats().addBonusXP((long)bonusXP,false,a,true);
        }
    }
}


class TempText implements TextPanelAPI{
    @Override
    public void setFontInsignia() {

    }

    @Override
    public void setFontOrbitron() {

    }

    @Override
    public void setFontVictor() {

    }

    @Override
    public void setFontSmallInsignia() {

    }

    @Override
    public LabelAPI addPara(String text) {
        return null;
    }

    @Override
    public LabelAPI addPara(String text, Color color) {
        return null;
    }

    @Override
    public LabelAPI addParagraph(String text) {
        return null;
    }

    @Override
    public LabelAPI addParagraph(String text, Color color) {
        return null;
    }

    @Override
    public void replaceLastParagraph(String text) {

    }

    @Override
    public void replaceLastParagraph(String text, Color color) {

    }

    @Override
    public void appendToLastParagraph(String text) {

    }

    @Override
    public void appendToLastParagraph(int charsToCut, String text) {

    }

    @Override
    public void highlightFirstInLastPara(String text, Color color) {

    }

    @Override
    public void highlightLastInLastPara(String text, Color color) {

    }

    @Override
    public void highlightInLastPara(Color color, String... strings) {

    }

    @Override
    public void highlightInLastPara(String... strings) {

    }

    @Override
    public void setHighlightColorsInLastPara(Color... colors) {

    }

    @Override
    public void clear() {

    }

    @Override
    public InteractionDialogAPI getDialog() {
        return null;
    }

    @Override
    public boolean isOrbitronMode() {
        return false;
    }

    @Override
    public void setOrbitronMode(boolean orbitronMode) {

    }

    @Override
    public ResourceCostPanelAPI addCostPanel(String title, float height, Color color, Color dark) {
        return null;
    }

    @Override
    public void setHighlightsInLastPara(Highlights h) {

    }

    @Override
    public LabelAPI addPara(String format, Color color, Color hl, String... highlights) {
        return null;
    }

    @Override
    public LabelAPI addPara(String format, Color hl, String... highlights) {
        return null;
    }

    @Override
    public void advance(float amount) {

    }

    @Override
    public TooltipMakerAPI beginTooltip() {
        return null;
    }

    @Override
    public void addTooltip() {

    }

    @Override
    public void updateSize() {

    }

    @Override
    public boolean addCostPanel(String title, Color color, Color dark, Object... params) {
        return false;
    }

    @Override
    public boolean addCostPanel(String title, Object... params) {
        return false;
    }

    @Override
    public void addSkillPanel(PersonAPI person, boolean admin) {

    }

    @Override
    public void setFontOrbitronUnnecessarilyLarge() {

    }

    @Override
    public void addImage(String category, String key) {

    }

    @Override
    public void addImage(String spriteName) {

    }
};
