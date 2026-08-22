package data.scripts.combatabilityPatches.Nexerlin.industrys;

import exerelin.world.ExerelinProcGen;
import exerelin.world.industry.IndustryClassGen;

public class AIShipyard extends IndustryClassGen {
    public AIShipyard(){
        //todo: put industry ids into here. this helps promass.
        super("");
    }

    @Override
    public boolean canApply(ExerelinProcGen.ProcGenEntity entity) {
        return super.canApply(entity);
    }

    @Override
    public float getWeight(ExerelinProcGen.ProcGenEntity entity) {
        return super.getWeight(entity);
    }

/*    @Override
    public void apply(ExerelinProcGen.ProcGenEntity entity, boolean instant) {
        //contents of 'super.apply'. the reason I would run this, the only real reason, is to apply AI cores on game start.
        String id = industryIds.toArray(new String[0])[0];
        addIndustry(entity.market, id, instant);
        entity.numProductiveIndustries += 1;
    }*/
}
