package data.scripts;

import com.fs.starfarer.api.Global;

import java.util.Arrays;

public class AIRetrofits_StringHelper {
    public static String modString = "AIRetrofitString";
    public static String getString(String className, String function, int lineID){
        try {
            return Global.getSettings().getString(modString + "_" + className + "_" + function + "_" + lineID);
        }catch (Exception e){
            AIRetrofit_Log.loging("failed to get string of ID: "+modString + "_" + className + "_" + function + "_" + lineID ,new AIRetrofits_StringHelper(),true);
            return "";
        }
    }
    public static String getString(String className, String function, int lineID,String... splits){
        return getSplitString(getString(className, function, lineID),splits);
    }
    protected static String getSplitString(String primary,String[] secondary){
        StringBuilder output = new StringBuilder();
        String[] a = primary.split("%s");
        try {
            for (int b = 0; b < a.length - 1; b++) {
                output.append(a[b]).append(secondary[b]);
            }
            output.append(a[a.length - 1]);
        }catch (Exception e){
            AIRetrofit_Log.loging("failed to get split string: "+primary+" , "+ Arrays.toString(secondary),new AIRetrofits_StringHelper(),true);
            return "";
        }
        return output.toString();
    }
}
