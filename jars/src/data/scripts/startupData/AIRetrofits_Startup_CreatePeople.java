package data.scripts.startupData;

import com.fs.starfarer.api.Global;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_Admin;
import data.scripts.robot_forge.createItemSupport.CommandNodeTypes.AIRetrofit_CommandNodeType_Officer;

public class AIRetrofits_Startup_CreatePeople {

    public static final float PersonWeight_Officer = Global.getSettings().getFloat("AIRetrofit_CommandNode_PersonalityWeight_officer");
    public static final float PersonWeight_Admin = Global.getSettings().getFloat("AIRetrofit_CommandNode_PersonalityWeight_admin");
    public static void apply(){
        new AIRetrofit_CommandNodeType_Officer("officer",PersonWeight_Officer);
        new AIRetrofit_CommandNodeType_Admin("admin",PersonWeight_Admin);
    }
}
