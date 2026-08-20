package data.scripts.DropGroupSupport;

import lombok.SneakyThrows;
import org.json.JSONObject;

public class AIRetrofit_SingleDropGroup {
    private String[] linkedTo;
    private String[] linkedFrom;
    private double[] multi;
    private String type;
    private AIRetrofit_SingleDropGroup[] LinkedGroups;
    @SneakyThrows
    public AIRetrofit_SingleDropGroup(JSONObject json){
        /*todo: I have compleatly changed this down to its roots. This is mostly for, well....
            its mostly about adding things to specific ships (probes, survey ships, and motherships need there survey robots).
            its also about adding in command nodes to the drop list.
         */
        linkedTo = getMultiStrings(json.getString("linkedTo"));
        linkedFrom = getMultiStrings(json.getString("linkedFrom"));
        type = json.getString("type");
        String[] multi = getMultiStrings(json.getString("multiplyer"));
        this.multi = new double[multi.length];
        for (int a = 0; a < multi.length; a++){
            this.multi[a] = Double.parseDouble(multi[a]);
        }
    }
    private String[] getMultiStrings(String input){
        return input.split('\n'+"");
    }
    public boolean shouldApply(){
        //done know how to handle that yet
        //DropGroup
        //LinkedGroup
        //SalvageType
        return false;
    }
    public void apply(double multi){

    }
    public void attemptToApply(){
        for (int a = 0; a < linkedTo.length; a++){
            for (int b = 0; b < linkedFrom.length; b++){
                if (shouldApply()){
                    apply(multi.length > a ? multi[a] : multi[0]);
                    return;
                }
            }
        }
    }
    public void preformInitialPreperation(){
        if (!type.equals("LinkedGroup")) return;
        LinkedGroups = new AIRetrofit_SingleDropGroup[linkedFrom.length];
        for (int c = 0; c < linkedFrom.length; c++){
            String a = linkedFrom[c];
            if (!AIRetrofit_DropGroupSupport.map.containsKey(a)) continue;
            AIRetrofit_SingleDropGroup b = AIRetrofit_DropGroupSupport.map.get(a);
            LinkedGroups[c] = b;
        }
    }
}
