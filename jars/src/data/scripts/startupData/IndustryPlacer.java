package data.scripts.startupData;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.SpecialItemData;
import com.fs.starfarer.api.campaign.econ.Industry;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.impl.campaign.procgen.SalvageEntityGenDataSpec;

import java.util.ArrayList;

public class IndustryPlacer {

    public IndustryPlacer(){
        apply();
    }
    public void apply(){
        /*
        base game worlds:
        ita = industry slots not filled.
        independent:
            nomios
                size 3
                ita  1
            agreus
                size 5
                ita  1
            nortia
                size 4
                ita  0
            asharu
                size 4
                ita  1
            baetis
                size 5
                ita  1
            crom_cruach
                size 4
                ita  2
            cethlenn
                size 4
                ita  1
            groombridge_habitat
                size 4
                ita  2
            new_maxios
                size 5
                ita  0
            orthrus
                size 4
                ita  1
            eldfell
                size 5
                ita  2
        tri-tech
            culann
                size 5
                ita  0
            eochu_bres
                size 6
                ita 2
            ogma
                size 5
                ita 3???
            tibicena
                size 5
                ita 1
            port_tse
                size 4
                ita  0
        heg
            arcadia_station
                size 4
                ita  0
            chicomoztoc
                size 8
                ita  0
            coatl
                size 5
                ita 2
            yama
                size 6
                ita 1
            nachiketa
                size 5
                ita  1
            tigra_city
                size 4
                ita  2
            eventide
                size 7
                ita  1
            sphinx
                size 5
                ita  1
        pers
            laicaille_habitat
                size 4
                ita  0
            olinadu
                size 4
                ita  1
            mairaath
                size 5
                ita  2
            kazeron
                size 7
                ita  1
        pirates
            umbra
                size 5
                ita  1
            donn
                size 4
                ita  1
            station_kapteyn
                size 4
                ita  0
            kanni
                size 4
                ita  0
            kantas_den
                size 4
                ita  0
            mairaath_abandoned_station2
                size 4
                ita  2
            thule_pirate_station
                size 4
                ita  1
        sind
            sindria
                size 7
                ita  0
            volturn
                size 6
                ita  1
            cruor
                size 5
                ita  2


        --after mush adjustment, I have determend that every market has enouth fucking worlds already. so, lets get started on market generation.
        --how this will work is simple: each vinila faction will have 'preferd worlds', but after the preferd worlds we just select one at random.


        */
        addShipyard();
        addAINodeProduction();

        addSaR_0();
        addCR_0();
        addSuR_0();

        addSaR_1();
        addCR_1();
        addSuR_1();

        //buildRandomFactions();
    }
    private void buildRandomFactions(){
        //this will be were I add in industrys for random factions that were here at game start. for better or worse.
    }

    private void addSaR_0(){
        addPreferdTarget("independent",new String[]{"asharu"},"AIRetrofit_salvageRobotManufactory");
        addPreferdTarget("sindrian_diktat",new String[]{"cruor","volturn","sindria"},"AIRetrofit_salvageRobotManufactory");
        addPreferdTarget("hegemony",new String[]{"sphinx"},"AIRetrofit_salvageRobotManufactory");
        //addPreferdTarget("pirates",new String[]{},"AIRetrofit_salvageRobotManufactory");
        addPreferdTarget("tritachyon",new String[]{"port_tse"},"AIRetrofit_salvageRobotManufactory","alpha_core");
        addPreferdTarget("persean",new String[]{"kazeron"},"AIRetrofit_salvageRobotManufactory");
    }
    private void addCR_0(){
        addPreferdTarget("independent",new String[]{"baetis"},"AIRetrofit_combatRobotManufactory");
        addPreferdTarget("sindrian_diktat",new String[]{"sindria","cruor","volturn"},"AIRetrofit_combatRobotManufactory");
        //addPreferdTarget("hegemony",new String[]{},"AIRetrofit_combatRobotManufactory");
        addPreferdTarget("pirates",new String[]{"kantas_den"},"AIRetrofit_combatRobotManufactory");
        addPreferdTarget("tritachyon",new String[]{"eochu_bres"},"AIRetrofit_combatRobotManufactory","alpha_core");
        addPreferdTarget("persean",new String[]{"laicaille_habitat"},"AIRetrofit_combatRobotManufactory");

    }
    private void addSuR_0(){
        addPreferdTarget("independent",new String[]{"groombridge_habitat"},"AIRetrofit_surveyRobotManufactory");
        //addPreferdTarget("sindrian_diktat",new String[]{},"AIRetrofit_surveyRobotManufactory");
        addPreferdTarget("hegemony",new String[]{"arcadia_station"},"AIRetrofit_surveyRobotManufactory");
        addPreferdTarget("pirates",new String[]{"mairaath_abandoned_station2"},"AIRetrofit_surveyRobotManufactory");
        addPreferdTarget("tritachyon",new String[]{"culann"},"AIRetrofit_surveyRobotManufactory","alpha_core");
        addPreferdTarget("persean",new String[]{"olinadu"},"AIRetrofit_surveyRobotManufactory");

    }

    private void addSaR_1(){
        addPreferdTarget("pirates",new String[]{"umbra","mairaath_abandoned_station2","thule_pirate_station"},"AIRetrofit_salvageRobotManufactory_V2");

    }
    private void addCR_1(){
        addPreferdTarget("hegemony",new String[]{"coatl","yama","nachiketa","tigra_city","eventide","sphinx"},"AIRetrofit_combatRobotManufactory_V2");
    }
    private void addSuR_1(){
        addPreferdTarget("independent",new String[]{"eldfell","orthrus","groombridge_habitat","cethlenn","crom_cruach","baetis","asharu","agreus","nomios"},"AIRetrofit_surveyRobotManufactory_V2");
    }

    private void addShipyard(){
        addPreferdTarget("tritachyon",new String[]{"culann"},"AIRetrofit_shipYard");

    }
    private void addAINodeProduction(){
        addPreferdTarget("tritachyon",new String[]{"ogma"},"AIRetrofit_AINodeProductionFacility");
        //addIndustry("tritachyon","AIRetrofit_AINodeProductionFacility");
    }

    private void addPreferdTarget(String factionID, String[] marketID, String industryID){
        addPreferdTarget(factionID,marketID,industryID,null,null);
    }
    private void addPreferdTarget(String factionID, String[] marketID, String industryID, String AICoreID){
        addPreferdTarget(factionID,marketID,industryID,AICoreID,null);
    }
    private void addPreferdTarget(String factionID, String[] marketID, String industryID, String AICoreID, String itemID){
        for (String a : marketID){
            MarketAPI market = Global.getSector().getEconomy().getMarket(a);
            if (market == null || !canAdd(a,industryID)) continue;
            addIndustry(a, industryID,AICoreID,itemID);
            return;
        }
        getRandomTarget(factionID, industryID, AICoreID, itemID);
    }
    private void getRandomTarget(String factionID, String industryID, String AICoreID, String itemID){
        //only keep this active if nexerlin is disabled maybe? mmmmm no wait, this is for same faction targets. should be fine.
        ArrayList<MarketAPI> targets = new ArrayList<>();
        for (MarketAPI a : Global.getSector().getEconomy().getMarketsCopy()) if (a.getFaction().getId().equals(factionID) && canAdd(a.getId(),industryID)) targets.add(a);
        if (targets.isEmpty()) return;
        MarketAPI target = targets.get((int) (Math.min(Math.random()*targets.size(),targets.size()-1)));
        addIndustry(target.getId(),industryID,AICoreID,itemID);
    }
    private boolean canAdd(String marketID, String industryID){
        MarketAPI market = Global.getSector().getEconomy().getMarket(marketID);
        if (market == null) return false;
        if (market.getIndustries().size() >= 12) return false;//max number of industrys reached.
        boolean isIndustry = Global.getSettings().getIndustrySpec(industryID).hasTag("industry");
        if (!isIndustry) return true;
        int max = Math.min(market.getSize() - 2,4);
        int count = 0;
        for (Industry a : market.getIndustries()) if (a.getSpec().hasTag("industry")) count++;
        if (count >= max) return false;
        return true;
    }
    private void addIndustry(String marketID, String industryID){
        addIndustry(marketID,industryID,null,null);
    }
    private void addIndustry(String marketID, String industryID, String AICoreID){
        addIndustry(marketID, industryID,AICoreID,null);
    }
    private void addIndustry(String marketID, String industryID, String AICoreID, String itemID){
        //notes:
        //if an AICoreID is null, no AI core is added.
        //if item id is null, no specal item is added.
        MarketAPI target = Global.getSector().getEconomy().getMarket(marketID);
        if (target == null) return;
        if (!target.hasIndustry(industryID)) target.addIndustry(industryID);
        if (AICoreID != null) target.getIndustry(industryID).setAICoreId(AICoreID);
        if (itemID != null) target.getIndustry(industryID).setSpecialItem(new SpecialItemData(itemID,null));
    }
}
