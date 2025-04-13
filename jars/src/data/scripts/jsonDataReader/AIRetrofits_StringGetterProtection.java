package data.scripts.jsonDataReader;

import data.scripts.AIRetrofit_Log;
import org.json.JSONException;

public class AIRetrofits_StringGetterProtection {
    //this exsists for the sole reason of me making a mistake with the implementation of this, and needing something to 'catch' my issues
    public static String getString(String name){
        //s/getSettings().getString(/AIRetrofits_JsonReaderBase.getReader(AIRetrofits_JsonReaderBase.STRING_FILE_NAME).getString(/g
        //s/getSettings().getString(/AIRetrofits_StringGetterProtection.getString(/g
        try {
            AIRetrofit_Log.loging("attempting to get a string named"+name,new AIRetrofit_Log(),true);
            return AIRetrofits_JsonReaderBase.getReader(AIRetrofits_JsonReaderBase.STRING_FILE_NAME).getString(name);
        } catch (JSONException e) {
            AIRetrofit_Log.loging("failed to get da string. error of type: "+e.getMessage(),new AIRetrofit_Log(),true);
            e.printStackTrace();
        }
        return null;

    }
}
