package data.scripts.AIWorldCode;
//AIRetrofit_AIRelations.AIRelation("factionID");
public class AIRetrofit_AIRelations {
    static String player = "player";
    static String[] dislikes = {};
    static String[] likes = {};
    public static int AIRelation(String factionID){
        for(String name: dislikes){
            if(name.equals(factionID)){
                return -1;
            }
        }
        for(String name: likes){
            if(name.equals(factionID)){
                return 1;
            }
        }
        return 0;
    }
}
