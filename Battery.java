package power.management;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

public class Battery {
    
    private final String batteryID;
    private final int noOfCells;
    public Cell[] cells;
    private char temperature;
    private double discharge;
    private double currentCharge;
    private int cyclesCompleted;
    private double totalBatteryCapacity;
    private Baseline baselines;
    private double[] tripCharge; 
            
    public Battery(int noOfCells) {
        
        Random rand = new Random();        
        if(noOfCells < 0) { //always the absolute value(positive) is taken for number of cells in a battery
            noOfCells *= -1;
        }
        
        this.noOfCells = noOfCells;
        this.cells = new Cell[noOfCells];
        this.totalBatteryCapacity = 0;
        this.cyclesCompleted = 0;
        this.temperature = 'l';
        this.baselines = setFactoryBaseline();
        this.batteryID = UUID.randomUUID().toString();
        this.discharge = 0;
        this.tripCharge = new double[4];
        
        for(int index=0;index<noOfCells;index++) {
            float initialCellCapacity = (float)((rand.nextInt(6)+11)); //Capacities between 11W and 16 W are assigned for each cell.
            Cell newCell = new Cell(initialCellCapacity);
            this.totalBatteryCapacity += newCell.getInitialCapacity();
            this.cells[index] = newCell;
        }
    }
    
    public double getDischarge() {
        return this.discharge;
    }
    public Cell[] getCells() {
        return this.cells;
    }
    public String getBatteryID() {
        return this.batteryID;
    }
    public Baseline getBaseLine() {
        return this.baselines;
    }
    public char getBatteryTemperature() {
        return this.temperature;
    }
    public int getNumberOfCells() {
        return this.noOfCells;
    }
    /*
        Fucntion that returns the current battery charge in %
    */
    public double getCurrentCharge() {
        double currentChargeSum = 0;
        for (Cell c : this.cells) {
            currentChargeSum += c.getCurrentCapacity();
        }
        this.currentCharge = (currentChargeSum/this.totalBatteryCapacity)*100;        
        return this.currentCharge;
    }
    /*
        Function returns the total capacity of the battery with upto 2 decimal places.
    */
    public double getTotalCapacity() {
        DecimalFormat df = new DecimalFormat(".##");
        return Double.parseDouble(df.format(this.totalBatteryCapacity));
    }
    public int getCyclesCount() {
        return this.cyclesCompleted;
    }
    public double getTripCharge(int passengers) {
        return this.tripCharge[passengers-1];
    }
            
    public void setBaselines(Baseline b) {
        this.baselines = b;
    }
    public void updateBatteryTemperature(char temp) {
        this.temperature = temp;
    }
    public void updateTripCharge(int passengers) {
        this.tripCharge[passengers-1] = 0;
    }
    /*
        function that sets the factory baseline for every 100KM based number of passengers
    */
    private Baseline setFactoryBaseline() {
        Baseline factoryBaseline = new Baseline();
        factoryBaseline.setOnePass(70);
        factoryBaseline.setTwoPass(85);
        factoryBaseline.setThreePass(100);
        factoryBaseline.setFourPass(120);
        return factoryBaseline;
    }
    
    /*
        function that charges the battery. each call to this function charges each cell to 1% of their initial capacity.
        excess charging is discarded aka the cells' capacities are not updated once they are 100% charged even if the function is called.
    */
    public void updateCharging() {  
        if(getCurrentCharge() < 100){
            for (Cell c : this.cells) {
                double currentCellCharge = c.getCurrentCapacity();
                double initialCapacity = c.getInitialCapacity();
                if(currentCellCharge < initialCapacity) { 
                    double chargeAmt = initialCapacity/100;
                    currentCellCharge += chargeAmt;
                    if(currentCellCharge > initialCapacity) {
                        currentCellCharge = initialCapacity;
                    }
                    c.setCurrentCapacity(currentCellCharge*1000);
                }
            }
        }
    }
    /*
        function that updates the current charge of the battery for every 0.25KM driven.
        this function also calculates the number of cycles completed
        the decay of the battery's cells depending on the temperature of the battery and the number of passengers
    */
    public String updateDischarging(int passengers) {
        String status = "";
        Random rand = new Random();
        double currentBatteryCapacity = 0;
        for (Cell c : this.cells) {
            double currentCellCapacity = c.getCurrentCapacity();
            if(currentCellCapacity > 0) {
                double decay;
                switch(this.temperature) {
                    case 'h':
                        decay = (float)(rand.nextInt(3)+8)/1000;
                        break;
                    case 'n':
                        decay = (float)(rand.nextInt(4)+4)/1000;
                        break;
                    default:
                        decay = (float)(rand.nextInt(4))/1000;
                        break;
                }
                switch(passengers) {
                    case 1:
                        decay += 0.0025;
                        break;
                    case 2:
                        decay += 0.005;
                        break;
                    case 3:
                        decay += 0.0075;
                        break;
                    default:
                        decay += 0.01;
                        break;
                }
                double decayAmt = (c.getInitialCapacity() * decay)/100; 
                if(currentCellCapacity - decayAmt < 0) {
                        decayAmt = currentCellCapacity;
                    currentCellCapacity = 0;
                }
                else {
                    currentCellCapacity -= decayAmt;
                }
                c.setCurrentCapacity(currentCellCapacity*1000); //current capacity of the cell is updated with the new capacity
                this.discharge += decayAmt; //computes the amount a given cell has decayed
                currentBatteryCapacity += decayAmt;     
            }         
        }
        //System.out.println(currentBatteryCapacity);
        this.tripCharge[passengers-1] += currentBatteryCapacity; //updates the energy consumed for every 0.25KM
        if(((this.discharge/this.totalBatteryCapacity)*100) >= 100) { //condition to check if one battery cycle has been completed
            this.discharge =0;
            this.cyclesCompleted++;
        }
        double charge = getCurrentCharge();
            if(charge <= 20) {
                status = "LOW BATTERY";
            }
            if(charge <= 3) {
                status = "NO CHARGE";
            }
        return status;
    }    
}
