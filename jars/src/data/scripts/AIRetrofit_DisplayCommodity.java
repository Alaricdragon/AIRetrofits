package data.scripts;

import com.fs.starfarer.api.campaign.CargoStackAPI;
import com.fs.starfarer.api.campaign.listeners.CommodityTooltipModifier;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class AIRetrofit_DisplayCommodity implements CommodityTooltipModifier {
    @Override
    public void addSectionAfterPrice(TooltipMakerAPI info, float width, boolean expanded, CargoStackAPI stack) {

    }
}
