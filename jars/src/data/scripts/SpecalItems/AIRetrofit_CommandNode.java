package data.scripts.SpecalItems;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.CargoTransferHandlerAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.campaign.impl.items.BaseSpecialItemPlugin;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;

import java.awt.*;

public class AIRetrofit_CommandNode extends BaseSpecialItemPlugin {
    public PersonAPI person;
    public boolean officer;
    public int quality;
    @Override
    public void init(CargoStackAPI stack) {
        super.init(stack);
    }

    /*
    @Override
    public void render(float x, float y, float w, float h, float alphaMult,
                       float glowMult, SpecialItemRendererAPI renderer) {
    }*/
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
        if(officer){
            Global.getSector().getPlayerFleet().getFleetData().addOfficer(person);
        }else{
            Global.getSector().getCharacterData().addAdmin(person);
        }
    }
    private final static String nameText = "a command node with the designation of %s. they are a %s of level %s";
    private final static String officerText = "officer";
    private final static String adminText = "admin";
    @Override
    public void createTooltip(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource) {
        super.createTooltip(tooltip, expanded, transferHandler, stackSource, false);
        float pad = 3f;
        Color highlight = Misc.getHighlightColor();
        String type = adminText;
        if(officer) type = officerText;
        tooltip.addPara(nameText,pad,highlight,person.getNameString(),type,""+person.getStats().getLevel());
        if(expanded){
            tooltip.addSkillPanel(person,pad);
        }
    }
    @Override
    public int getPrice(MarketAPI market, SubmarketAPI submarket) {
        return super.getPrice(market, submarket);
    }

}
