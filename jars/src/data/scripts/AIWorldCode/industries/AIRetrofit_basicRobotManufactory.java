package data.scripts.AIWorldCode.industries;

import data.scripts.AIWorldCode.industries.personalRobotForge.AIRetrofit_PersonalRobotManufactoryBase;

public class AIRetrofit_basicRobotManufactory extends AIRetrofit_PersonalRobotManufactoryBase {
    @Override
    public boolean isAvailableToBuild(){
        return false;
    }

}
