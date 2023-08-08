package data.scripts.SpecalItems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.*;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.impl.items.BaseSpecialItemPlugin;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.ui.UIPanelAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofit_Log;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import data.scripts.startupData.AIRetrofits_Constants;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AIRetrofit_CommandNode extends BaseSpecialItemPlugin {
    //addSpecial AIRetrofit_CommandNode
    public PersonAPI person = null;
    public String personType = "";
    private final static String officerText = Global.getSettings().getString("AIRetrofit_CommandNode_officerText");//"officer";
    private final static String officerText2 = Global.getSettings().getString("AIRetrofit_CommandNode_officerText2");//"a command node with the designation of %s. they are a %s of level %s with a personality of %s";
    private final static String adminText = Global.getSettings().getString("AIRetrofit_CommandNode_adminText");//"admin";
    private final static String adminText2 = Global.getSettings().getString("AIRetrofit_CommandNode_adminText2");//"a command node with the designation of %s. they are a %s";
    private final static String errorText = Global.getSettings().getString("AIRetrofit_CommandNode_defaultTExt");//"error";
    private final static String errorText2 = Global.getSettings().getString("AIRetrofit_CommandNode_defaultText2");//"a command node with the designation of %s. they are a %s";
    @Override
    public void init(CargoStackAPI stack) {
        super.init(stack);
        //AIRetrofit_Log.loging("loading data",this,true);
        //AIRetrofit_Log.push();
        if (stack.getSpecialDataIfSpecial() instanceof AIRetrofit_CommandNode_SpecalItemData) {
            //AIRetrofit_Log.loging("trying to load data from specaldata",this);
            person = ((AIRetrofit_CommandNode_SpecalItemData) stack.getSpecialDataIfSpecial()).getPerson();
            personType = ((AIRetrofit_CommandNode_SpecalItemData) stack.getSpecialDataIfSpecial()).getPersonType();//AIRetrofit_Log.loging("returned type: "+((AIRetrofit_CommandNode_SpecalItemData) stack.getSpecialDataIfSpecial()).getPersonType(),this,true);
            //findPersonType();
        }else{
        }
        //AIRetrofit_Log.loging("DONE loading / setting data for specal item",this);
        //AIRetrofit_Log.pop();
    }
    public void fixStack(){
        if (stack.getSpecialDataIfSpecial() instanceof AIRetrofit_CommandNode_SpecalItemData) {return;}
        //AIRetrofit_Log.loging("createing new data for specal data",this,true);
        //AIRetrofit_Log.push();
        //int b = 0;
        //while(b < (int)stack.getSize()) {
        CargoAPI cargo = stack.getCargo();
        try {
            //AIRetrofit_Log.loging("removing this stack from cargo bay", this, true);
            //AIRetrofit_Log.loging("stake : "+stack.toString(),this,true);
            //AIRetrofit_Log.loging("stake.cetCargo : "+stack.getCargo().toString(),this,true);
            stack.getCargo().removeStack(stack);
            stack.setSize(0);
            stack = null;
            //stack.getCargo().removeStack(stack);
        }catch (Exception e){
            //AIRetrofit_Log.loging("failed to remove bad core. exeption type: "+e,this,true);
            //AIRetrofit_Log.loging("returning to avoid issues...",this,true);
            //AIRetrofit_Log.pop();
            return;
        }
        try {
            //AIRetrofit_Log.loging("created person", this, true);
            //AIRetrofit_Log.loging("creating specal data", this, true);
            AIRetrofit_CommandNode_SpecalItemData a = new AIRetrofit_CommandNode_SpecalItemData(AIRetrofits_Constants.SpecalItemID_CommandNodes[0], null);
            //AIRetrofit_Log.loging("do we have a cargo bay?", this, true);
            //AIRetrofit_Log.loging("adding new specal item to cargo bay", this, true);
            cargo.addSpecial(a, 1);
            if(cargo.getFleetData().getFleet().isPlayerFleet()){
                //AIRetrofit_Log.loging("is a player fleet",this,true);
            }else{
                //AIRetrofit_Log.loging("is NOT a player fleet",this,true);
            }
        } catch (Exception e) {
            //AIRetrofit_Log.loging("exeption found. type: " + e, this, true);
            //AIRetrofit_Log.pop();
        }
        //b++;
        //}
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
    protected int timesTriedToAddPerson = 0;
    @Override
    public void performRightClickAction(){
        //super.performRightClickAction();
        if(timesTriedToAddPerson > 1) return;
        switch (personType){
            case AIRetrofits_Constants.PersonTypes_Officer:
                Global.getSector().getPlayerFleet().getFleetData().addOfficer(person);
                break;
            case AIRetrofits_Constants.PersonTypes_Admin:
                Global.getSector().getCharacterData().addAdmin(person);
                break;
            default:
                if(person == null){
                    person = AIRetrofits_CreatePeople.createPerson();
                    this.findPersonType();
                    timesTriedToAddPerson++;
                    this.performRightClickAction();
                }
                break;
        }
    }
    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource) {
        //AIRetrofit_Log.loging("personType: "+personType,this,true);
        super.createTooltip(tooltip, expanded, transferHandler, stackSource, false);
        switch (personType){
            case AIRetrofits_Constants.PersonTypes_Officer:
                toolTipOfficer(tooltip,expanded,transferHandler,stackSource);
                break;
            case AIRetrofits_Constants.PersonTypes_Admin:
                toolTipAdmin(tooltip,expanded,transferHandler,stackSource);
                break;
            default:
                toolTipNull(tooltip,expanded,transferHandler,stackSource);
                break;
        }
    }
    public void toolTipOfficer(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource){
        float pad = 3f;
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        String type = officerText;
        TooltipMakerAPI text = tooltip.beginImageWithText(person.getPortraitSprite(), 48);
        text.addPara(officerText2,pad,highlight,person.getNameString(),type,""+person.getStats().getLevel(),person.getPersonalityAPI().getDisplayName());
        tooltip.addImageWithText(opad);
        if(expanded){
            tooltip.addSkillPanel(person,pad);
        }
    }
    public void toolTipAdmin(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource){
        float pad = 3f;
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        List<MutableCharacterStatsAPI.SkillLevelAPI> skills = person.getStats().getSkillsCopy();
        int level = 0;
        for(int a = 0; a < skills.size(); a++){
            if(skills.get(a).getSkill().isAdminSkill()){
                level++;
            }
        }
        String type = adminText;
        TooltipMakerAPI text = tooltip.beginImageWithText(person.getPortraitSprite(), 48);
        text.addPara(adminText2,pad,highlight,person.getNameString(),type,""+level);
        tooltip.addImageWithText(opad);
        if(expanded){
            //TextPanelAPI.addSkillPanel(person, true);
            //text.;
            //tooltip.addIconGroup(5);
            //ArrayList<String> skillsTemp = new ArrayList<>();
            List<MutableCharacterStatsAPI.SkillLevelAPI> skillsCopy = person.getStats().getSkillsCopy();
            for(int a2 = 0; a2 < skillsCopy.size(); a2++) {
                MutableCharacterStatsAPI.SkillLevelAPI a = skillsCopy.get(a2);
                if (/*true||*/a.getSkill().isAdminSkill()) {
                    //skillsTemp.add(a.getSkill().getSpriteName());
                    //tooltip.addImage();
                    //tooltip.addPara(a.getSkill().getName(),5);
                    TooltipMakerAPI text3 = tooltip.beginImageWithText(a.getSkill().getSpriteName(),30);
                    text.addPara(a.getSkill().getName(), opad);
                    tooltip.addImageWithText(opad);
                }
            }
            /*
            String[] temp = new String[skillsTemp.size()];
            for(int a = 0; a < skillsTemp.size(); a++){
                temp[a] = skillsTemp.get(a);
            }
            tooltip.addImages(20,20,5,5,temp);*/
            //tooltip.addSkillPanelOneColumn(person,pad);
            //tooltip.addSkillPanel(person,pad);
        }
    }
    public void toolTipNull(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource){
        float pad = 3f;
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        String type = "error";

        TooltipMakerAPI text = tooltip.beginImageWithText(person.getPortraitSprite(), 48);
        text.addPara(errorText2,pad,highlight,errorText);

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


    public void findPersonType(){
        if(person.getTags().contains(officerText)) personType = AIRetrofits_Constants.PersonTypes_Officer;
        if(person.getTags().contains(adminText)) personType = AIRetrofits_Constants.PersonTypes_Admin;
        if(person.hasTag(officerText)) personType = AIRetrofits_Constants.PersonTypes_Officer;
        if(person.hasTag(adminText)) personType = AIRetrofits_Constants.PersonTypes_Admin;
    }


}
