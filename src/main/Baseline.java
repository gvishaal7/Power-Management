package power.management;

public class Baseline {
    
    private double onePassSum;
    private double twoPassSum;
    private double threePassSum;
    private double fourPassSum;    
    
    private int onePassCount;
    private int twoPassCount;
    private int threePassCount;
    private int fourPassCount;
    
    public Baseline() {
        this.onePassSum = 0;
        this.onePassCount = 0;
        this.twoPassSum = 0;
        this.twoPassCount = 0;
        this.threePassCount = 0;
        this.threePassSum = 0;
    }
    
    /*
        Returns the running averages.
    */
    public float getBaseOne() {        
        return ((float)this.onePassSum/this.onePassCount);
    }
    public float getBaseTwo() {
        return ((float)this.twoPassSum/this.twoPassCount);
    }
    public float getBaseThree() {
        return ((float)this.threePassSum/this.threePassCount);
    }
    public float getBaseFour() {
        return ((float)this.fourPassSum/this.fourPassCount);
    }       
    
    public void setOnePass(double val) {
        onePassSum += val;
        onePassCount++;
    }
    
    public void setTwoPass(double val) {
        twoPassSum += val;
        twoPassCount++;
    }
    
    public void setThreePass(double val) {
        threePassSum += val;
        threePassCount++;
    }
        
    public void setFourPass(double val) {
        fourPassSum += val;
        fourPassCount++;
    }
        
}
