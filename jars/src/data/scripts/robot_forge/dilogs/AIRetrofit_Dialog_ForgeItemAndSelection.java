package data.scripts.robot_forge.dilogs;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.OptionPanelAPI;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.rules.MemoryAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.api.util.Pair;
import data.scripts.robot_forge.AIRetrofits_ForgeList;
import data.scripts.robot_forge.AIRetrofits_RobotForge;
import data.scripts.robot_forge.AIRetrofits_RobotForgeSecondary;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AIRetrofit_Dialog_ForgeItemAndSelection extends AIRetrofits_DialogBase {
    protected InteractionDialogAPI dialog;
    protected TextPanelAPI text;
    protected OptionPanelAPI options;
    //protected AIRetrofits_RobotForgeDiologPlugin.Menu lastSelectedMenu;
    //protected AIRetrofits_RobotForgeDiologPlugin.Items lastSelectedItems;
    private String[] lastSelectedMenu = {"Menu", "", "", "", "", "", "", ""};
    protected int depth = 0;
    protected int itemSellected = 0;
    protected List<Pair<String, Object>> optionsList = new ArrayList();
    protected int currentPage = 1;
    //protected PersonAPI selectedOfficer;
    public static final int ENTRIES_PER_PAGE = 6;
    protected int maxOptions = 5;
    protected int optionSellected;
    //List<PersonAPI> captiveOfficers;
    CampaignFleetAPI fleet;
    public AIRetrofit_Dialog_ForgeItemAndSelection(CampaignFleetAPI theFleet) {
        this.fleet = theFleet;
        initDataThing();
    }

    protected void showPaginatedMenu() {
        if (this.optionsList.isEmpty()) {
            this.addBackOption();
        } else {
            this.options.clearOptions();
            int offset = (this.currentPage - 1) * 6;
            int max = Math.min(offset + 6, this.optionsList.size());
            int numPages = 1 + (this.optionsList.size() - 1) / 6;
            if (this.currentPage > numPages) {
                this.currentPage = numPages;
                offset = (this.currentPage - 1) * 6;
            }

            for (int x = offset; x < max; ++x) {
                Pair<String, Object> entry = (Pair) this.optionsList.get(x);
                this.options.addOption((String) entry.one, entry.two);
            }

            if (this.currentPage > 1) {
                this.options.addOption("Previous","PREVIOUS");// AIRetrofits_RobotForgeDiologPlugin.Menu.PREVIOUS);
                this.options.setShortcut("PREVIOUS", 203, false, false, false, true);//AIRetrofits_RobotForgeDiologPlugin.Menu.PREVIOUS, 203, false, false, false, true);
            }

            if (this.currentPage < numPages) {
                this.options.addOption("Next","NEXT");// AIRetrofits_RobotForgeDiologPlugin.Menu.NEXT);
                this.options.setShortcut("NEXT", 205, false, false, false, true);//AIRetrofits_RobotForgeDiologPlugin.Menu.NEXT, 205, false, false, false, true);
            }

            this.addBackOption();
        }
    }

    protected void addBackOption() {
        this.options.addOption("Back","BACK");// AIRetrofits_RobotForgeDiologPlugin.Menu.BACK);
        this.options.setShortcut("BACK", 1, false, false, false, true);//AIRetrofits_RobotForgeDiologPlugin.Menu.BACK, 1, false, false, false, true);
    }

    protected void populateOptions() {
        this.options.clearOptions();
        //this.addBackOption();
        //if (this.lastSelectedMenu == AIRetrofits_RobotForgeDiologPlugin.Menu.CREW) {
        //    this.populateCrewOptions();
        //} else if (this.lastSelectedMenu == AIRetrofits_RobotForgeDiologPlugin.Menu.OFFICERS) {
        //    this.populateOfficersOptions();
        //} else if (this.lastSelectedMenu == AIRetrofits_RobotForgeDiologPlugin.Menu.OFFICER) {
        //    this.populateOfficerOptions();
        //} else if (this.lastSelectedMenu != AIRetrofits_RobotForgeDiologPlugin.Menu.BREAK_CREW && this.lastSelectedMenu != AIRetrofits_RobotForgeDiologPlugin.Menu.BREAKO_FFICER && this.lastSelectedMenu != AIRetrofits_RobotForgeDiologPlugin.Menu.RANSOM_OFFICER && this.lastSelectedMenu != AIRetrofits_RobotForgeDiologPlugin.Menu.PROCESS_CREW && this.lastSelectedMenu != AIRetrofits_RobotForgeDiologPlugin.Menu.PROCESS_OFFICER && this.lastSelectedMenu != AIRetrofits_RobotForgeDiologPlugin.Menu.RANSOM_ALL && this.lastSelectedMenu != AIRetrofits_RobotForgeDiologPlugin.Menu.BRIBE_OFFICER && this.lastSelectedMenu != AIRetrofits_RobotForgeDiologPlugin.Menu.REPATRIATE_OFFICER) {
        //    this.populateMainMenuOptions();
        //} else {

        //if(getLast().equals(Menu)){
        //    this.mainMenu();
        //}
        //text.addParagraph("last was: " + getLast() + " at position " + depth);
        switch (getLast()) {
            case "Menu":
                this.mainMenu();
                break;
            //case "aaa":
            //    break;
            default:
                //this.mainMenu();
                //if(getLast().equals("salvageRobots")){
                //ItemPage(0);
                //}
                //removeLast();
                boolean continueing = true;
                /**/for(int a = 0; a < Items.length; a++){
                if(getLast().equals(Items[a])){//optionData == item){
                    //run relevent code here
                    //itemSellected = a;
                    //addLast(Items[a]);
                    ItemPage(a);
                    continueing = false;
                    //text.addParagraph("YOU DIDIDDD IT!!!! " + (a));
                    //fleet.getAbility("AIretrofit_robot_drone_forge");
                    //robot_forge.setForgeValue(a);
                    break;
                }
            }
                if(continueing){
                    //this.mainMenu();
                }
                //removeLast();
                break;/**/
        }

        //this.populateConfirmCancel();
        //}

    }
    protected void populateConfirmCancel() {
        this.options.clearOptions();
        this.options.addOption("Confirm", "CONFIRM");//Menu.CONFIRM);
        this.addBackOption();
    }
    /*
        protected void populateMainMenuOptions() {
            this.options.clearOptions();
            if (this.fleet.getCargo().getCommodityQuantity("capturedcrew") > 0.0F) {
                this.options.addOption("Captive crew", AIRetrofits_RobotForgeDiologPlugin.Menu.CREW);
            }

            if (this.captiveOfficers.size() > 0) {
                this.options.addOption("Captive officers", AIRetrofits_RobotForgeDiologPlugin.Menu.OFFICERS);
                this.options.addOption("Ransom all officers", AIRetrofits_RobotForgeDiologPlugin.Menu.RANSOM_ALL);
            }

            this.addBackOption();
        }
    */
    @Override
    public void advance(float arg0) {
    }
    @Override
    public void backFromEngagement(EngagementResultAPI arg0) {
    }
    @Override
    public Object getContext() {
        return null;
    }
    @Override
    public Map<String, MemoryAPI> getMemoryMap() {
        return null;
    }
    @Override
    public void init(InteractionDialogAPI dialog) {
        this.dialog = dialog;
        this.options = dialog.getOptionPanel();
        this.text = dialog.getTextPanel();
        dialog.getVisualPanel().setVisualFade(0.25F, 0.25F);
        //Iterator var3 = Global.getSector().getScripts().iterator();

        /*while (var3.hasNext()) {
            EveryFrameScript script = (EveryFrameScript) var3.next();
            //if (script.getClass() == LootAddScript.class) {
            //    if (((LootAddScript)script).captiveOfficers == null) {
            //        ((LootAddScript)script).captiveOfficers = new ArrayList();
            //    }

            //    this.captiveOfficers = ((LootAddScript)script).captiveOfficers;
            //}
        }*/

        //this.text.addParagraph("You currently have " + (int) this.fleet.getCargo().getCommodityQuantity("capturedcrew") + " captive crew and " + this.captiveOfficers.size() + " captive officers.");
        this.text.addParagraph("You cafullys consider what you can produce with the knowlage you have...");
        //this.lastSelectedMenu = null;
        //this.lastSelectedItems = null;
        this.populateOptions();
        dialog.setPromptText(Misc.ucFirst("Options"));
    }
    @Override
    public void optionMousedOver(String arg0, Object arg1) {

    }
    @Override
    public void optionSelected(String optionText, Object optionData) {
        if (optionText != null) {
            this.text.addParagraph(optionText, Global.getSettings().getColor("buttonText"));
        }

        try {
            //optionData.equals("strings");
            if(optionData.equals("Menu")){
                this.mainMenu();
            }
            if(optionData.equals("CONFIRM")){
                text.addParagraph("item sending == " + itemSellected);
                AIRetrofits_RobotForge.setForgeValue(itemSellected);
                itemSellected = 0;
                resetLast();
                this.dialog.dismiss();
            }
            if (optionData.equals("NEXT")){//optionData == AIRetrofits_RobotForgeDiologPlugin.Menu.NEXT) {
                ++this.currentPage;
                this.showPaginatedMenu();
                return;
            }

            if (optionData.equals("PREVIOUS")){//optionData == AIRetrofits_RobotForgeDiologPlugin.Menu.PREVIOUS) {
                --this.currentPage;
                this.showPaginatedMenu();
                return;
            }

            this.currentPage = 1;
            if (optionData.equals("BACK")){//optionData == AIRetrofits_RobotForgeDiologPlugin.Menu.BACK){
                if (depth == 0) {
                    resetLast();
                    AIRetrofits_RobotForge.setForgeValue(-1);
                    this.dialog.dismiss();
                } else {
                    //this.lastSelectedMenu = null;
                    //optionData = getLast();
                    removeLast();
                    //this.populateOptions();
                    //optionSelected(optionText,optionData);
                }
            }else{
                //int a = 0;
                //for(Items item : Items.values()){
                for(int a = 0; a < Items.length; a++){
                    if(optionData.equals(Items[a])){//optionData == item){
                        //run relevent code here
                        itemSellected = a;
                        text.addParagraph("item sellected == " + itemSellected);
                        addLast(Items[a]);
                        //ItemPage(a);
                        //text.addParagraph("YOU DIDIDDD IT!!!! " + (a));
                        //fleet.getAbility("AIretrofit_robot_drone_forge");
                        //robot_forge.setForgeValue(a);
                    }
                }
            }
        } catch (Exception var7) {
            this.text.addPara(var7.toString());
            this.addBackOption();
        }
        //text.addParagraph(getLast());
        this.populateOptions();
    }

    protected void mainMenu(){
        this.options.clearOptions();
        /*int a = 0;
        for (Items item : Items.values()) {
            this.options.addOption(DiologOptions[a], item);
            a++;
        }*/
        for(int a = 0; a < Items.length; a++) {
            //for(String thing : Global.getSector().getPlayerFaction().getKnownHullMods()){
            if(AIRetrofits_ForgeList.items.get(a).active){//thing.equals(RequiredHullmods[a])){//DONEHERE
                this.options.addOption(AIRetrofits_ForgeList.items.get(a).name, Items[a]);//DONEHERE
                //break;
            }
            //}
        }
        this.addBackOption();
    }
    protected void ItemPage(int item){
        this.options.clearOptions();
        //for(int a = 0; a < Items.length; a++){
        //this.options.//.addOption(DiologOptions[a], Items[a]);
        //}
        //this.text.addParagraph(ItemDescriptions[item], Global.getSettings().getColor("buttonText"));
        //AIRetrofits_ForgeList.items.get(item).getDescription(this.text);
        AIRetrofits_ForgeList.items.get(item).getDescription(this.text, AIRetrofits_RobotForgeSecondary.iCalculateBonus(Global.getSector().getPlayerFleet()));//DONEHERE
        //this.text.addParagraph(AIRetrofits_ForgeList.items.get(item).description, Global.getSettings().getColor("buttonText"));//DONEHERE
        if(!AIRetrofits_ForgeList.items.get(item).activateDialog(dialog)) {
            this.populateConfirmCancel();
        }else{
            resetLast();
            AIRetrofits_RobotForge.setForgeValue(-1);
        }
    }
    protected String getLast(){
        return lastSelectedMenu[depth];
    }
    protected void addLast(String lastLocation){
        //text.addParagraph("added to last: " + lastLocation);
        lastSelectedMenu[depth + 1] = lastLocation;
        depth++;
    }
    protected void removeLast(){
        if(depth != 0) {
            //text.addParagraph("removed from last: " + lastSelectedMenu[depth]);
            lastSelectedMenu[depth] = "";
            depth--;
        }
    }
    protected void resetLast(){
        for(depth = depth; depth >= 1; depth--){
            lastSelectedMenu[depth] = "";
        }
    }
    /*protected static enum Menu {
        ITEM,
        BACK,
        NEXT,
        PREVIOUS,
        CONFIRM;
        private Menu() {
        }
    }
    protected static enum Items {
        o1,
        o2,
        o3,
        o4;

        private Items() {
        }
    }*/
    protected void initDataThing(){
        Items = new String[AIRetrofits_ForgeList.items.size()];
        for(int a = 0; a < Items.length; a++){
            Items[a] = "o" + (a + 1);
        }
    }
    protected static String[] Menu =  {
            "ITEM",
            "BACK",
            "NEXT",
            "PREVIOUS",
            "CONFIRM"
    };
    //HERE
    protected static String[] Items;
}
