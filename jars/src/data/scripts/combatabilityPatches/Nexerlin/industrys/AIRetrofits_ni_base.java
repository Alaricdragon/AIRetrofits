package data.scripts.combatabilityPatches.Nexerlin.industrys;

import com.fs.starfarer.api.campaign.econ.MarketAPI;
import exerelin.campaign.alliances.Alliance;
import exerelin.utilities.NexConfig;
import exerelin.utilities.NexFactionConfig;
import exerelin.world.ExerelinProcGen;
import exerelin.world.industry.IndustryClassGen;
import starlords.util.NexerlinUtilitys;

public abstract class AIRetrofits_ni_base extends IndustryClassGen {
    protected String[] countsAs;
    public AIRetrofits_ni_base(String[] countsAs, String... industrys){
        super(industrys);
    }
    @Override
    public boolean canApply(ExerelinProcGen.ProcGenEntity entity) {
        MarketAPI market = entity.market;
        if (market != null){
            NexConfig.getFactionConfig(market.getFaction().getId()).getAlignments().get(Alliance.Alignment.TECHNOCRATIC);
            if (NexConfig.getFactionConfig(market.getFaction().getId()).diplomacyTraits.contains("hates_ai")) return false;
            //dislikes_ai
        }
        return super.canApply(entity);
    }

    @Override
    public float getWeight(ExerelinProcGen.ProcGenEntity entity) {
        MarketAPI market = entity.market;
        if (market != null) {
            NexFactionConfig config = NexConfig.getFactionConfig(market.getFaction().getId());
            float a = config.getAlignmentValues().get(Alliance.Alignment.TECHNOCRATIC);
            float multi = ((a+1f)/4)+1;//a should go between -1 and +1. +2 makes it between 0 and 2. /4 is 0 and 0.25 +
            if (config.diplomacyTraits.contains("dislikes_ai")) multi *= 0.5f;
            if (config.diplomacyTraits.contains("likes_ai")) multi += 0.5f;
            if (config.diplomacyTraits.contains("loves_ai")) multi += 1f;
            return super.getWeight(entity) * multi * getWeightMulti(entity);
        }
        return super.getWeight(entity)*getWeightMulti(entity);
    }
    /// for custom alignments OTHER then AI based ones.
    public abstract float getWeightMulti(ExerelinProcGen.ProcGenEntity entity);
}
