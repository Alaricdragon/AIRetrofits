package data.scripts.memory;

import com.fs.starfarer.api.Global;
import data.scripts.AIRetrofit_Log;

import java.util.ArrayList;

public class AIRetrofits_ItemInCargoMemory {
    protected static ArrayList<AIRetrofits_ItemInCargoMemory> list = new ArrayList<>();
    public static void runall(){
        for (AIRetrofits_ItemInCargoMemory a : list){
            a.checkMemory();
            //a.activeCodeRunner();
        }
    }
    public AIRetrofits_ItemInCargoMemory(String key, String item){
        this.key=key;
        this.item=item;
        list.add(this);
    }
    /*public void activeCodeRunner(){
        //Global.getSector().getPlayerMemoryWithoutUpdate().get();
    }*/
    protected String key;
    protected String item;
    public void checkMemory(){
        boolean output = false;
        try {
            output = Global.getSector().getPlayerFleet().getCargo().getCommodityQuantity(item) != 0;
        }catch (Exception e){
            //AIRetrofit_Log.loging("failed to get memory"+output,this,true);
        }
        //AIRetrofit_Log.loging("got memory as: "+output,this,true);
        Global.getSector().getPlayerMemoryWithoutUpdate().set(key,output);
        //AIRetrofit_Log.loging("memory saved and gotten as: "+ Global.getSector().getPlayerMemoryWithoutUpdate().get(key).toString(),this,true);
    }
}
