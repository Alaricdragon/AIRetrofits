package data.scripts.DropGroupSupport;

import com.fs.starfarer.api.Global;
import data.scripts.jsonDataReader.AIRetrofits_CSV_Reader;
import lombok.SneakyThrows;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AIRetrofit_DropGroupSupport {
    public static HashMap<String,AIRetrofit_SingleDropGroup> map = new HashMap<>();
    @SneakyThrows
    public AIRetrofit_DropGroupSupport(){
        String path = "data/lords/lordGenerator.csv";
        JSONArray jsons = Global.getSettings().loadCSV(path, true);
        HashMap<String, JSONObject> list = AIRetrofits_CSV_Reader.computeFile(jsons);
        //ArrayList<Double> order = new ArrayList<>();
        ArrayList<AIRetrofit_SingleDropGroup> data = new ArrayList<>();
        //ArrayList<String> ids = new ArrayList<>();
        map = new HashMap<>();
        for (String a : list.keySet()){
            JSONObject json = list.get(a);
            map.put(json.getString("id"),new AIRetrofit_SingleDropGroup(json));
            //data.add(new AIRetrofit_SingleDropGroup(json));
            //ids.add(id);
            //order.add(orderT);
        }
        for (String a : map.keySet()){
            map.get(a).preformInitialPreperation();
        }
    }
}
