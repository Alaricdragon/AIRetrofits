package data.scripts.memory;

import com.fs.starfarer.api.Global;
import data.scripts.crewReplacer_Crew;
import data.scripts.crewReplacer_Main;

import java.util.ArrayList;

public class AIRetrofit_ItemFoundMemory{
    public static ArrayList<AIRetrofit_ItemFoundMemory> memory = new ArrayList<>();
    public static void onGameLoad(){
        for (AIRetrofit_ItemFoundMemory a : memory){
            a.onGL();
        }
    }
    public static void changeMemory(){
        for (AIRetrofit_ItemFoundMemory a : memory){
            a.changeMemoryIfItemAvailable();
        }
    }
    public String key,item,job;
    public crewReplacer_Crew Crew;
    public AIRetrofit_ItemFoundMemory(String key, String item, String job, crewReplacer_Crew Crew) {
        this.key=key;
        this.item=item;
        this.job=job;
        this.Crew=Crew;
        memory.add(this);
    }
    public void onGL(){
        hideItem();
        showItemIfCan();
    }
    public void showItemIfCan() {
        if (Global.getSector().getMemory().getBoolean(key)){
            showItem();
        }
    }
    public void changeMemoryIfItemAvailable(){
        if (Global.getSector().getMemory().getBoolean(key)) return;
        boolean output = false;
        try {
            output = Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(item) != 0;
        }catch (Exception e){
            //AIRetrofit_Log.loging("failed to get memory"+output,this,true);
        }
        if (output) {
            Global.getSector().getMemory().set(key, output);
            showItem();
        }
        //AIRetrofit_Log.loging("got memory as: "+output,this,true);

    }
    public void showItem(){
        crewReplacer_Main.getJob(job).addCrew(Crew);
        crewReplacer_Main.getJob(job).organizePriority();
    }
    public void hideItem(){
        crewReplacer_Main.getJob(job).removeCrew(Crew.name);
        crewReplacer_Main.getJob(job).organizePriority();
    }
}
