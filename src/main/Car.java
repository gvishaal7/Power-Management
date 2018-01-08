package power.management;

import java.util.UUID;

public class Car {
    
    private final String model;
    private double totalDistanceTravelled;        
    private Battery carBattery;
    private float[] currentTrip;
    
    public Car() {
        this.model = UUID.randomUUID().toString();
        this.totalDistanceTravelled = 0;
        this.currentTrip = new float[4];
    }
    
    public Battery getBattery() {
        return this.carBattery;
    }
    public String getModel() {
        return this.model;
    }
    public double getTotalDistance() {
        return this.totalDistanceTravelled;
    }
    
    public void setBattery(Battery b) {
        this.carBattery = b;
    }
    
    /*
        updates the user's baseline for every 100KM driven based on number of passengers
        and updates the car's current and total distance travelled for every 0.25KM
         currentTrip value is reset everytime the car covers 100KM.
    */
    public void updateTrip(int passengers) {
        this.currentTrip[passengers-1] += 0.25;
        Battery b = getBattery();
        String status = b.updateDischarging(passengers);
        if(!status.equalsIgnoreCase("NO CHARGE")) {
            if(currentTrip[passengers-1] >= 100) {
                currentTrip[passengers-1] = 0;
                double tripCharge = b.getTripCharge(passengers);
                Baseline base = b.getBaseLine();
                switch(passengers){
                    case 1:
                        base.setOnePass(tripCharge);
                        break;
                    case 2:
                        base.setTwoPass(tripCharge);
                        break;
                    case 3:
                        base.setThreePass(tripCharge);
                        break;
                    default:
                        base.setFourPass(tripCharge);
                        break;
                }
                b.updateTripCharge(passengers);
            }
            this.totalDistanceTravelled += 0.25;
        }
    }       
}
