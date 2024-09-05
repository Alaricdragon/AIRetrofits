package data.scripts.jsonDataReader;

import com.fs.starfarer.api.Global;
import data.scripts.AIRetrofit_Log;
import data.scripts.startupData.AIRetrofits_Constants_2;
import org.json.JSONException;
import org.json.JSONObject;

public class AIRetrofits_JsonReaderBase {
    private static boolean started = false;
    private static AIRetrofits_JsonReaderBase[] readers;
    public static final String STRING_FILE_NAME = "data/config/AIRetrofits/strings.json";
    public String file,mod;
    protected JSONObject CoreObject;
    protected JSONObject[] Object;
    protected String[] ObjectName;
    protected JSONObject[] Array;
    public static void startup() throws JSONException {
        started = true;
        readers = new AIRetrofits_JsonReaderBase[]{
            new AIRetrofits_JsonReaderBase(STRING_FILE_NAME, AIRetrofits_Constants_2.ModID)
        };
        //command for swaping all getStrings with this command hopefully.
        //s/getSettings().getString(/AIRetrofits_JsonReaderBase.getReader(AIRetrofits_JsonReaderBase.STRING_FILE_NAME).getString("Strings",/g
        //s/getSettings().getString(/AIRetrofits_JsonReaderBase.getReader(AIRetrofits_JsonReaderBase.STRING_FILE_NAME).getString(/g
        readers[0].prepare_JSONObjects();//"Strings");
        /*AIRetrofits_JsonReaderBase b = AIRetrofits_JsonReaderBase.getReader(AIRetrofits_JsonReaderBase.STRING_FILE_NAME);
        String c = b.getString("Strings","cat");
        AIRetrofit_Log.loging("the cats name is: "+ c,readers[0],true);*/
    }
    public AIRetrofits_JsonReaderBase(String file, String mod){
        this.file = file;
        this.mod = mod;
    }
    public void prepare_JSONObjects(String... name){
        try {
            JSONObject nameConfig = Global.getSettings().getMergedJSONForMod(file, mod);
            CoreObject = nameConfig;
            Object = new JSONObject[name.length];
            ObjectName = new String[name.length];
            for (int a = 0; a < name.length; a++){
                Object[a] = nameConfig.getJSONObject(name[a]);
                ObjectName[a] = name[a];
            }
        }catch (Exception e){
            AIRetrofit_Log.loging("ERROR: failed to get json data. mod, data file, parameters"+this.mod+", "+this.file+", "+name.toString(),this,true);
        }
    }
    public static void ifnullstartup() {
        try {
            if (!started) startup();
        }catch (Exception e){
            AIRetrofit_Log.loging("ERROR: "+e,new AIRetrofit_Log(),true);
        }
    }
    public static AIRetrofits_JsonReaderBase getReader(String name){
        ifnullstartup();
        for (int a = 0; a < readers.length; a++){
            //AIRetrofit_Log.loging("getting name, check as: "+readers[a].file+", "+name,readers[a],true);
            if (readers[a].file.equals(name)) {
                AIRetrofit_Log.loging("got data named"+name,new AIRetrofit_Log(),true);
                return readers[a];
            }
        }
        AIRetrofit_Log.loging("failed to get data"+name,new AIRetrofit_Log(),true);
        return null;
    }
    public String getString(String name) throws JSONException {
        AIRetrofit_Log.loging("attempting to get string named: "+name,this,true);
        return CoreObject.getString(name);
    }
    public String getString(String set,String name) throws JSONException {
        JSONObject set0 = getSet(set);
        return set0.getString(name);
    }
    public int getInt(String set,String name) throws JSONException {
        JSONObject set0 = getSet(set);
        return set0.getInt(name);
    }
    public int getInt(String name) throws JSONException {
        return CoreObject.getInt(name);
    }
    public double getDoable(String set,String name) throws JSONException {
        JSONObject set0 = getSet(set);
        return set0.getDouble(name);
    }
    public double getDoable(String name) throws JSONException {
        return CoreObject.getDouble(name);
    }
    public boolean getBoolean(String set,String name) throws JSONException {
        JSONObject set0 = getSet(set);
        return set0.getBoolean(name);
    }
    public boolean getBoolean(String name) throws JSONException {
        return CoreObject.getBoolean(name);
    }
    protected JSONObject getSet(String set){
        for (int a = 0; a < this.Object.length; a++){
            if (set.equals(ObjectName[a])) return this.Object[a];
        }
        return null;
    }
    public void prepare_JSONArrays(String... name){

    }
}
