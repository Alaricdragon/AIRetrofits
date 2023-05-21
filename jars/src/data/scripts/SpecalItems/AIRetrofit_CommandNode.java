package data.scripts.SpecalItems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.impl.items.BaseSpecialItemPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.loading.specs.FactionDoctrine;
import data.scripts.AIRetrofit_Log;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;

public class AIRetrofit_CommandNode extends BaseSpecialItemPlugin {
    public PersonAPI person;
    public String personType = "";
    public int quality;
    public FactionDoctrineAPI doctrine = Global.getSector().getPlayerFaction().getDoctrine();
    private final static String nameText = "a command node with the designation of %s. they are a %s of level %s";
    private final static String officerText = "officer";
    private final static String adminText = "admin";
    private final static float maxRandomPower0 = 7;
    private final static float minRandomPower0 = 1;
    private final static float maxRandomPower1 = 3;
    private final static float minRandomPower1 = 1;
    @Override
    public void init(CargoStackAPI stack) {
        super.init(stack);
        AIRetrofit_Log.loging("loading data",this,true);
        if (stack.getSpecialDataIfSpecial() instanceof AIRetrofit_CommandNode_SpecalItemData) {
            AIRetrofit_Log.loging("trying to load data from specaldata",this,true);
            person = ((AIRetrofit_CommandNode_SpecalItemData) stack.getSpecialDataIfSpecial()).getPerson();
            findPersonType();
        }else{
            //cant  fix stack here. need to fix it somewere else.
            fixStack();
        }
        AIRetrofit_Log.loging("DONE loading / setting data for specal item",this,true);
    }
    public void fixStack(){
        if (stack.getSpecialDataIfSpecial() instanceof AIRetrofit_CommandNode_SpecalItemData) {return;}
        AIRetrofit_Log.loging("createing new data for specal data",this,true);
        AIRetrofit_Log.push();
        int b = 0;
        //while(b < (int)stack.getSize()) {
        try {
            AIRetrofit_Log.loging("created person", this, true);
            createPerson();
            AIRetrofit_Log.loging("creating specal data", this, true);
            AIRetrofit_CommandNode_SpecalItemData a = new AIRetrofit_CommandNode_SpecalItemData(AIRetrofits_Constants.SpecalItemID_CommandNode, null, this.person);
            AIRetrofit_Log.loging("do we have a cargo bay?", this, true);
            stack.getCargo().addCommodity("crew",50);
            AIRetrofit_Log.loging("adding new specal item to cargo bay", this, true);
            stack.getCargo().addSpecial(a, 1);
        } catch (Exception e) {
            AIRetrofit_Log.loging("exeption found. type: " + e, this, true);
        }
        b++;
        //}
        try {
            AIRetrofit_Log.loging("removing this stack from cargo bay", this, true);
            stack.getCargo().removeStack(stack);
        }catch (Exception e){
            AIRetrofit_Log.loging("failed to remove bad core. exeption type: "+e,this,true);
        }
        AIRetrofit_Log.pop();
    }
    /*
    @Override
    public void render(float x, float y, float w, float h, float alphaMult,
                       float glowMult, SpecialItemRendererAPI renderer) {
    }*/

    @Override
    public String getId(){
        //fixStack();
        return super.getId();
    }

    @Override
    public boolean hasRightClickAction(){
        return true;
    }
    @Override
    public boolean shouldRemoveOnRightClickAction(){
        return true;
    }
    @Override
    public boolean isTooltipExpandable(){
        return true;
    }
    @Override
    public void performRightClickAction(){
        //super.performRightClickAction();
        switch (personType){
            case AIRetrofits_Constants.PersonTypes_Officer:
                Global.getSector().getPlayerFleet().getFleetData().addOfficer(person);
                break;
            case AIRetrofits_Constants.PersonTypes_Admin:
                Global.getSector().getCharacterData().addAdmin(person);
                break;
        }
    }
    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource) {
        super.createTooltip(tooltip, expanded, transferHandler, stackSource, false);
        //fixStack();
        if(person == null){
            this.createPerson();
        }
        float pad = 3f;
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        String type = "error";
        switch (personType){
            case AIRetrofits_Constants.PersonTypes_Officer:
                type = officerText;
                break;
            case AIRetrofits_Constants.PersonTypes_Admin:
                type = adminText;
                break;
        }
        TooltipMakerAPI text = tooltip.beginImageWithText(person.getPortraitSprite(), 48);
        text.addPara(nameText,pad,highlight,person.getNameString(),type,""+person.getStats().getLevel());
        //text.addPara(person.getNameString() + " is an official faction representative.", pad);
        //text.addPara("Treat them well, or face the consequences.", pad);
        tooltip.addImageWithText(opad);
        //tooltip.addPara(nameText,pad,highlight,person.getNameString(),type,""+person.getStats().getLevel());
        if(expanded){
            tooltip.addSkillPanel(person,pad);
        }
    }
    @Override
    public int getPrice(MarketAPI market, SubmarketAPI submarket) {
        //this.doctrine = market.getFaction().getDoctrine();
        return super.getPrice(market, submarket);
    }
    @Override
    protected float	getItemPriceMult() {
        return super.getItemPriceMult();
    }

    public static String getPersonTypeByWeight(){
        String[] persons = {
                AIRetrofits_Constants.PersonTypes_Officer,
                AIRetrofits_Constants.PersonTypes_Admin
        };
        float[] weight = {
                AIRetrofits_Constants.PersonWeight_Officer,
                AIRetrofits_Constants.PersonWeight_Admin
        };
        float totalWeight = 0;
        for(float a : weight){
            totalWeight+=a;
        }
        float random = (float) (Math.random()*totalWeight);
        AIRetrofit_Log.loging("got a weight of: "+random,AIRetrofit_CommandNode.class,true);
        for(int a = 0; a < weight.length; a++){
            if(random <= weight[a]){
                return persons[a];
            }
            random -= weight[a];
        }
        return "ERROR";
    }
    public void createPerson(){
        createPerson(getPersonTypeByWeight());
    }
    public void findPersonType(){
        if(person.getTags().contains(officerText)) personType = AIRetrofits_Constants.PersonTypes_Officer;
        if(person.getTags().contains(adminText)) personType = AIRetrofits_Constants.PersonTypes_Admin;
    }

    public void createPerson(String type){
        float a = minRandomPower0 + Math.round(Math.random() * (maxRandomPower0) - minRandomPower0);
        float b = minRandomPower1 + Math.round(Math.random() * (maxRandomPower1) - minRandomPower1);
        createPerson((int)a,(int)b,type);
    }
    public void createPerson(int power,int power2, String type){
        AIRetrofit_Log.loging("got a power,power,type of: "+power+", "+power2+", "+type,this,true);
        switch (type){
            case AIRetrofits_Constants.PersonTypes_Officer:
                this.personType = type;
                person = AIRetrofits_CreatePeople.createOfficer(personalityMix(this.doctrine),power,power2);
                break;
            case AIRetrofits_Constants.PersonTypes_Admin:
                this.personType = type;
                person = AIRetrofits_CreatePeople.createAdmen(power);
                break;
        }
    }
    private int personalityMix(FactionDoctrineAPI doctrine){
        float maxChange = 10;
        int aggression = doctrine.getAggression();
        int max = 6;
        int min = 0;
        float changeChance = 0.1f;
        float changes = 0;
        while(maxChange < changes && Math.random() > 1 - changeChance){
            int change = -1;
            if((Math.random() > 0.5f && aggression < max) || aggression <= min) change = 1;
            aggression += change;
            changes++;
        }
        AIRetrofit_Log.loging("got aggression level of: "+aggression,this,true);
        return aggression;
    }

}
