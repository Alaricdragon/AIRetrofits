package data.scripts.robot_forge;

import java.util.ArrayList;

public class AIRetrofits_ForgeList {
    static public ArrayList<AIRetrofits_ForgeItem> items = new ArrayList<>();

    static public void addItem(AIRetrofits_ForgeItem forgeItem){
        items.add(forgeItem);
    }
}
