package data.scripts.robot_forge;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.InteractionDialogAPI;
import com.fs.starfarer.api.campaign.SectorEntityToken;
import com.fs.starfarer.api.campaign.TextPanelAPI;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import data.scripts.AIRetrofits_StringHelper;
import data.scripts.jsonDataReader.AIRetrofits_StringGetterProtection;

import java.util.ArrayList;
/*
an class that will be used for the robot_forge. to make things just a little easier to edit.
(because what i have right now is just a mess of arrays.)
 */
public class AIRetrofits_ForgeItem {
    public boolean active = true;
    public String name = "";
    public String description = "";
    public float speed = 1;
    ArrayList<String> outputItems = new ArrayList<>();//ID of returned items
    ArrayList<String> inputItems = new ArrayList<>();//ID of required items.

    ArrayList<Float> outputNumbers = new ArrayList<>();//number of outputted items per output time
    ArrayList<Float> inputNumbers = new ArrayList<>();//number of required items per output time

    protected static final String String_0 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_Item_0");
    protected static final String String_1 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_Item_1");
    protected static final String String_2 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_Item_2");
    protected static final String String_3 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_Item_3");
    protected static final String String_4 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_Item_4");
    protected static final String String_5 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_Item_5");
    protected static final String String_6 = AIRetrofits_StringGetterProtection.getString("AIRetrofit_RobotForge_Item_6");

    public AIRetrofits_ForgeItem(String nameTemp, String descriptionTemp, float speedTemp){
        name = nameTemp;
        description = descriptionTemp;
        speed = speedTemp;
    }
    public void addRequiredItem(String commoditieID,float quantity){
        inputItems.add(commoditieID);
        inputNumbers.add(quantity);
    }
    public void addOutputItem(String commoditieID,float quantity){
        outputItems.add(commoditieID);
        outputNumbers.add(quantity);
    }
    public boolean canForge(CampaignFleetAPI fleet){
        boolean out = true;
        for(int a = 0; a < inputItems.size(); a++){
            if(fleet.getCargo().getCommodityQuantity(inputItems.get(a)) <= 0){
                out = false;
                break;
            }
        }
        return out;
    }
    public void runForge(CampaignFleetAPI fleet, float power){
        for(int a = 0; a < outputItems.size(); a++){
            fleet.getCargo().addCommodity(outputItems.get(a),getForgeSpeed(power) * outputNumbers.get(a));

        }
        for(int a = 0; a < inputItems.size(); a++){
            fleet.getCargo().removeCommodity(inputItems.get(a),getForgeSpeed(power) * inputNumbers.get(a));
        }
    }

    public float getForgeSpeed(float power){
        return power * speed;
    }
    public void getDescription(TextPanelAPI text, float power){
        //out += description;
        float ForgeSpeed = getForgeSpeed(power);
        text.addPara(description);
        text.addPara("");
        text.addPara(String_0);
        for(int a = 0; a < outputItems.size(); a++){
            float numbers = outputNumbers.get(a) * ForgeSpeed;
            displayItem(text,numbers,outputItems.get(a));
            //text.addPara(Misc.getRoundedValueMaxOneAfterDecimal(numbers) + " " + Global.getSector().getEconomy().getCommoditySpec(outputItems.get(a)).getName());
        }
        text.addPara(String_1);
        for(int a = 0; a < inputItems.size(); a++){
            float numbers = inputNumbers.get(a) * ForgeSpeed;
            displayItem(text,numbers,inputItems.get(a));

            //text.addPara(Misc.getRoundedValueMaxOneAfterDecimal(numbers) + " " + Global.getSector().getEconomy().getCommoditySpec(inputItems.get(a)).getName());
        }

        //return out;
    }
    public String getoutputAsString(float power){
        //out += description;
        float ForgeSpeed = getForgeSpeed(power);
        String out = "";
        boolean last = false;
        for(int a = 0; a < outputItems.size(); a++){
            if(!last){
                out += String_2;
            }
            last = true;
            float numbers = outputNumbers.get(a) * ForgeSpeed;
            out += Misc.getRoundedValueMaxOneAfterDecimal(numbers) + " " + Global.getSector().getEconomy().getCommoditySpec(outputItems.get(a)).getName();
        }
        out += String_3;
        last = false;
        for(int a = 0; a < inputItems.size(); a++){
            if(!last){
                out += String_4;
            }
            last = true;
            float numbers = inputNumbers.get(a) * ForgeSpeed;
            out += Misc.getRoundedValueMaxOneAfterDecimal(numbers) + " " + Global.getSector().getEconomy().getCommoditySpec(inputItems.get(a)).getName();
        }

        return out;
    }
    public void getCantBuildPopup(CampaignFleetAPI fleet,SectorEntityToken entity){
        //String text_main = "cant forge " + name + ". out of ";
        String text = "";
        boolean last = false;
        for(int a = 0; a < inputItems.size(); a++){
            if(fleet.getCargo().getCommodityQuantity(inputItems.get(a)) <= 0){
                if(last){
                    text += String_6;
                }
                text += Global.getSector().getEconomy().getCommoditySpec(inputItems.get(a)).getName();
                last = true;
            }
        }
        fleet.addFloatingText(AIRetrofits_StringHelper.getSplitString(String_5,name,text), Misc.setAlpha(entity.getIndicatorColor(), 255), 3.5f);
    }
    private void displayItem(TextPanelAPI text, float numberOfItems,String itemName){
        CommoditySpecAPI spec = Global.getSector().getEconomy().getCommoditySpec(itemName);
        String displayName = spec.getName();

        TooltipMakerAPI tt = text.beginTooltip();
        TooltipMakerAPI iwt = tt.beginImageWithText(spec.getIconName(), 24);
        String numberStr = numberOfItems + "";
        LabelAPI label = iwt.addPara(numberStr + " " + displayName, 0, Misc.getHighlightColor(), numberStr);
        tt.addImageWithText(0);
        text.addTooltip();
    }
    public boolean activateDialog(InteractionDialogAPI dialog){
        return false;
    }
}
