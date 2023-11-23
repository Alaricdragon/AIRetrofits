package data.scripts.AIWorldCode.industries.personalRobotForge;

public class AIRetrofit_salvageRobotManufactoryT2 extends AIRetrofit_salvageRobotManufactory{
    @Override
    protected int[] getNumbers() {
        int[] b = super.getNumbers();
        b[0] += Math.max(0,(market.getSize())-2);
        b[1] += Math.max(0,(market.getSize() /2));
        b[2] += Math.max(0,(market.getSize()/3));
        for(int a = 3; a < b.length; a++){
            b[a] += Math.max(0,market.getSize()-2);
        }
        return b;
    }
}
