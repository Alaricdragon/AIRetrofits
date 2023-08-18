package data.scripts.robot_forge.createItemSupport.CommandNodeTypes;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.CargoTransferHandlerAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.impl.campaign.events.OfficerManagerEvent;
import com.fs.starfarer.api.impl.campaign.ids.Ranks;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.Misc;
import com.fs.starfarer.rpg.Person;
import data.scripts.SpecalItems.AIRetrofit_CommandNode;
import data.scripts.robot_forge.createItemSupport.AIRetrofits_CreatePeople;
import exerelin.campaign.intel.agents.AgentIntel;

import java.awt.*;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AIRetrofit_CommandNodeType_NexerlinOperative extends AIRetorfit_CommandNodeTypesBase{
    public static final float CostPerLevel = Global.getSettings().getFloat("AIRetrofit_CreatePerson_Nexerlin_Opertive_baseLevelCost");
    public static final float CostMultiIncreasePerLevel = Global.getSettings().getFloat("AIRetrofit_CreatePerson_Nexerlin_Opertive_LEvelCostMulti");
    public static final String CommandNodeType = Global.getSettings().getString("AIRetrofit_CommandNode_Nexerlin_Operative_Text");
    public static final String CommandNodeType2 = Global.getSettings().getString("AIRetrofit_CommandNode_Nexerlin_Operative_Text2");
    public static final int maxLevel = (int) Global.getSettings().getFloat("AIRetrofit_CreatePerson_Nexerlin_Opertive_MaxLevel");
    public AIRetrofit_CommandNodeType_NexerlinOperative(String name, float weight) {
        super(name, weight);
    }

    @Override
    public void commandNodeTooltip(TooltipMakerAPI tooltip, boolean expanded, CargoTransferHandlerAPI transferHandler, Object stackSource, AIRetrofit_CommandNode commandNode) {
        PersonAPI person = commandNode.person;
        float pad = 3f;
        float opad = 10f;
        Color highlight = Misc.getHighlightColor();
        int level = person.getStats().getLevel();
        String type = getType(person).getName();
        TooltipMakerAPI text = tooltip.beginImageWithText(person.getPortraitSprite(), 48);
        text.addPara(CommandNodeType2,pad,highlight,person.getNameString(),""+level,CommandNodeType,type);
        tooltip.addImageWithText(opad);
        if(expanded){
            //put what the operative can do here?
        }
    }

    @Override
    public PersonAPI createPersonForNode(CargoAPI cargo, int power, int personality) {
        int level=1;
        float powerUsed = CostPerLevel;
        float powerMulti = CostMultiIncreasePerLevel;
        while(level < maxLevel && powerUsed + (CostPerLevel * powerMulti) <= power){
            powerUsed+=(CostPerLevel * powerMulti);
            powerMulti*=CostMultiIncreasePerLevel;
            level++;
        }
        PersonAPI person = Global.getSector().getPlayerFaction().createRandomPerson();//;//OfficerManagerEvent.createAdmin(Global.getSector().getPlayerFaction(),tier,new Random());
        person.setRankId(Ranks.AGENT);
        person.setPostId(Ranks.POST_AGENT);

        /*AgentIntel intel = new AgentIntel(agent, Global.getSector().getPlayerFaction(), level);

        if (tmp.length > 2) {
            AgentIntel.Specialization spec = AgentIntel.Specialization.valueOf();
            intel.addSpecialization(spec);
        }*/
        String type = AgentIntel.Specialization.pickRandomSpecialization().getName();
        person.getStats().setLevel(level);
        person.addTag(type);


        AIRetrofits_CreatePeople.setPerson(person);
        this.setAmountOfPowerUsed((int)powerUsed);
        return person;
    }

    @Override
    public void performRightClickAction(PersonAPI person) {
        AgentIntel.Specialization type = getType(person);
        int level = person.getStats().getLevel();
        AgentIntel intel = new AgentIntel(person, Global.getSector().getPlayerFaction(), level);
        intel.addSpecialization(type);
        intel.setMarket(getClosestMarket());
        intel.init();
    }
    public static AgentIntel.Specialization getType(PersonAPI person){
        AgentIntel.Specialization type = AgentIntel.Specialization.HYBRID;
        if(person.hasTag(AgentIntel.Specialization.HYBRID.getName())){
            type = AgentIntel.Specialization.HYBRID;
        }
        if(person.hasTag(AgentIntel.Specialization.NEGOTIATOR.getName())){
            type = AgentIntel.Specialization.NEGOTIATOR;
        }
        if(person.hasTag(AgentIntel.Specialization.SABOTEUR.getName())){
            type = AgentIntel.Specialization.SABOTEUR;
        }
        return type;
    }
    public static MarketAPI getClosestMarket(){
        MarketAPI market = Global.getSector().getPlayerFleet().getMarket();
        if(market != null){
            return market;
        }
        /*market = Global.getSector().getPlayerFleet().getInteractionTarget().getMarket();
        if(market != null){
            return market;
        }*/
        float hyperDistance = 99999999999f;
        float distince = 999999999999f;
        float[] playerLocationHyper = {
                Global.getSector().getPlayerFleet().getLocationInHyperspace().getX(),
                Global.getSector().getPlayerFleet().getLocationInHyperspace().getY()
        };
        float[] playerLocation = {
                Global.getSector().getPlayerFleet().getLocation().getX(),
                Global.getSector().getPlayerFleet().getLocation().getY()
        };
        List<MarketAPI> temp = Global.getSector().getEconomy().getMarketsCopy();
        for(int a = 0; a < temp.size(); a++){
            float[] hyperSpace = {
                    temp.get(a).getLocationInHyperspace().getX(),
                    temp.get(a).getLocationInHyperspace().getY()
            };
            float[] localSpace = {
                    temp.get(a).getLocation().getX(),
                    temp.get(a).getLocation().getY()
            };
            float b1 = hyperSpace[0] - playerLocationHyper[0];
            float b2 = hyperSpace[1] - playerLocationHyper[1];
            float c1 = Math.max(b1,-b1)+Math.max(b2,-b2);
            float b3 = localSpace[0] - playerLocation[0];
            float b4 = localSpace[1] - playerLocation[1];
            float c2 = Math.max(b3,-b3)+Math.max(b4,-b4);
            if (c1 < hyperDistance || (c1 == hyperDistance && c2 < distince)){
                hyperDistance = c1;
                distince = c2;
                market = temp.get(a);
            }
        }
        return market;
    }
    public void temp() {
        /*PersonAPI agent = Global.getSector().getPlayerFaction().createRandomPerson();
        agent.setRankId(Ranks.AGENT);
        agent.setPostId(Ranks.POST_AGENT);
        AgentIntel intel = new AgentIntel(agent, Global.getSector().getPlayerFaction(), level);

        if (tmp.length > 2) {
            AgentIntel.Specialization spec = AgentIntel.Specialization.valueOf(tmp[2].toUpperCase(Locale.ENGLISH));
            intel.addSpecialization(spec);
        }

        intel.setMarket(market);
        intel.init();
        Console.showMessage("Added level " + level + " agent " + agent.getNameString() + " to market " + market.getName());
        */
    }
}
