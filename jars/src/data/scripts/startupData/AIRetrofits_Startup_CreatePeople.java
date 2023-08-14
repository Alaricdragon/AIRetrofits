package data.scripts.startupData;

import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_Admin;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_Officer;

public class AIRetrofits_Startup_CreatePeople {
    public static void apply(){
        new AIRetrofit_CommandNodeType_Officer("officer",AIRetrofits_Constants.PersonWeight_List[0]);
        new AIRetrofit_CommandNodeType_Admin("admin",AIRetrofits_Constants.PersonWeight_List[1]);
    }
}
