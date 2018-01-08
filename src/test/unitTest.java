package power.management;

import java.text.DecimalFormat;
import org.junit.Test;
import static org.junit.Assert.*;

public class unitTest {
    
    public unitTest() {
    }
    
    /*
        helper functions to create objects for user, car and battery
    */
    public User createUser(int numberOfCars, int[] cellsPerBattery) {
        User u = new User();
        for(int iter=0;iter<numberOfCars;iter++) {
            Car c = createCar(cellsPerBattery[iter]);
            u.addCars(c);
        }
        return u;
    }
    
    public Car createCar(int cellCapacity) {
        Car c = new Car();
        Battery b = createBattery(cellCapacity);
        c.setBattery(b);
        return c;
    }
    
    public Battery createBattery(int cellCapacity) {
        Battery b = new Battery(cellCapacity);        
        return b;
    }
    
    /*
        This test determines if an object for the Cell class can be created
    */
    @Test
    public void cellTest() {
        double cellCapacity = (double)15;
        Cell testCell = new Cell(cellCapacity);
        assertNotNull(testCell);
    }
    
    /*
        This test checks if the return value from the member function 'getInitialCapacity()' of Cell class is correct.
    */
    @Test
    public void cellInitialCapacityTest() {
        double cellCapcity = (double)35;
        Cell testCell = new Cell(cellCapcity);                
        assertEquals("initial capacity test unsuccessfull",(double)0.035, testCell.getInitialCapacity(), 0);
    }
    
    /*
        This test checks if the return value from the member function 'getCurrentCapacity()' of Cell class is correct.
    */
    @Test
    public void cellCurrentCapacityTest() {
        double cellCapacity = (double)40;
        Cell testCell = new Cell(cellCapacity);
        assertEquals("current capacity test unsuccessfull",(double)0.040, testCell.getCurrentCapacity(), 0);
    }
    
    /*
        This test determines if an object for the Battery class can be created.
    */
    @Test
    public void batteryTest() {
        int cells = 10;
        Battery testBattery = new Battery(cells);
        assertNotNull(testBattery);
    }
    
    /*
        This test checks if the factory test baselines are set correctly.
    */
    @Test
    public void batteryFactoryBaselineTest() {
        int cells = 10;
        Battery testBattery = new Battery(cells);
        Baseline testBaseline = testBattery.getBaseLine();
        assertEquals((float)70, testBaseline.getBaseOne(), 0);
        assertEquals((float)85, testBaseline.getBaseTwo(), 0);
        assertEquals((float)100, testBaseline.getBaseThree(), 0);
        assertEquals((float)120, testBaseline.getBaseFour(), 0);
    }
    
    /*
        This test checks if a negative value for 'noOfCells' member of the Battery, can be assigned.
    */
    @Test
    public void batteryNumberOfCellsTest() {
        int cells = -232;
        cells *= -1; //tesing for negative number
        Battery testBattery = new Battery(cells);
        assertEquals("number of cells test unsuccessfull", cells, testBattery.getNumberOfCells());
    }
    
    /*
        This test checks if the totalBatteryCapacity stored in the Battery class is equal to the sum of the capacity of
        all the cells present in that battery.
    */
    @Test
    public void batteryTotalCapacityTest() {
        DecimalFormat df = new DecimalFormat(".##");
        int cells = 7000;
        Battery testBattery = new Battery(cells);
        double totalBatteryCapacity = testBattery.getTotalCapacity();
        Cell[] batteryCells = testBattery.getCells();
        double cellCapacitySum = 0;
        for(int index=0;index<cells;index++) {
            cellCapacitySum += batteryCells[index].getInitialCapacity();
        }        
        cellCapacitySum = Double.parseDouble(df.format(cellCapacitySum));
        assertEquals("total battery capacity not equal to sum of cell capacity", totalBatteryCapacity, cellCapacitySum, 0);    
    }
    
    /*
        This test checks if the 'updateDischarging' member function of Battery class throws an error when it reaches a crictal level.
    */
    @Test
    public void batteryDischargingLowBattery() {
        int cells = 7250;
        Battery testBattery = new Battery(cells);
        double totalBatteryCapacity = testBattery.getTotalCapacity();
        String status = "";
        //initial charge
        System.out.println("Current Charge : "+testBattery.getCurrentCharge());
        do {
            status = testBattery.updateDischarging(1);
        } while(!status.equalsIgnoreCase("LOW BATTERY"));
        //after discharing
        System.out.println("Current Charge : "+testBattery.getCurrentCharge());
        double currentBatteryCharge = (testBattery.getCurrentCharge()*totalBatteryCapacity)/100;
        assertEquals("low battery check for 1 passenger",(totalBatteryCapacity*20/100), currentBatteryCharge,0.01);
    }
    
    /*
        This test checks if a discharged battery charges up.
    */
    @Test
    public void batteryChargingTest() {
        int cells = 7500;
        Battery testBattery = new Battery(cells); 
        String status = "";
        do {
            status = testBattery.updateDischarging(1);
        } while(!status.equalsIgnoreCase("LOW BATTERY"));
        for(int iter=0;iter<200;iter++) {
            testBattery.updateCharging();
        }
        assertEquals("battery charging test",100.0,testBattery.getCurrentCharge(),0.1);
    }
    
    /*
        This test checks if the number of battery cycles counted is correct.
    */
    @Test
    public void batteryCyclesCompletedTest() {
        int cells = 6750;
        Battery testBattery = new Battery(cells);
        String status = "";
        //discharing
        do {
            status = testBattery.updateDischarging(1);
        }while(!status.equalsIgnoreCase("LOW BATTERY"));   
        System.out.println("Current Charge : "+testBattery.getCurrentCharge());
        //charging
        for(int iter=0;iter<50;iter++) {
            testBattery.updateCharging();
        }
        System.out.println("Current Charge : "+testBattery.getCurrentCharge());
        //discharging
        do {
            status = testBattery.updateDischarging(1);
        }while(!status.equalsIgnoreCase("LOW BATTERY"));
        System.out.println("Current Charge : "+testBattery.getCurrentCharge());
        System.out.println("Cycles Completed : "+testBattery.getCyclesCount());
        assertEquals("battery cycles completed",1,testBattery.getCyclesCount(),0);
    }
    
    /*
        This test determines if an object for the Car class can be created.
    */
    @Test
    public void carTest() {
        Car testCar = new Car();
        assertNotNull(testCar);
    }
    
    /*
        This determines if an object for Battery class can be created and assigned to that car.
    */
    @Test
    public void carBatteryTest() {
        Car testCar = new Car();
        int cells = 3000;
        testCar.setBattery(createBattery(cells));
        Battery testCarBattery = testCar.getBattery();
        assertNotNull(testCarBattery);
    }
    
    /*
        This test checks if the baseline for every 100KM driven by a user is updated.
        Running average is used.
    */
    @Test
    public void carUpdateBaselineTest() {
        Car testCar = new Car();
        int cells = 5000;
        Battery testCarBattery = createBattery(cells);
        testCar.setBattery(testCarBattery);
        
        Baseline testBatteryBaseline = testCarBattery.getBaseLine();
        double onePassFactoryBase = testBatteryBaseline.getBaseOne();
        double twoPassFactoryBase = testBatteryBaseline.getBaseTwo();
        double threePassFactoryBase = testBatteryBaseline.getBaseThree();
        double fourPassFactoryBase = testBatteryBaseline.getBaseFour();
        
        for(int iter=0;iter<1200;iter++) {
            testCar.updateTrip(1);
            testCar.updateTrip(2);
            testCar.updateTrip(3);
            testCar.updateTrip(4);
        }
        
        double onePassNewBase = testBatteryBaseline.getBaseOne();
        double twoPassNewBase = testBatteryBaseline.getBaseTwo();
        double threePassNewBase = testBatteryBaseline.getBaseThree();
        double fourPassNewBase = testBatteryBaseline.getBaseFour();
        
        assertNotEquals("updating battery baseline for every 100KM driven", onePassNewBase, onePassFactoryBase, 0);
        assertNotEquals("updating battery baseline for every 100KM driven", twoPassNewBase, twoPassFactoryBase, 0);
        assertNotEquals("updating battery baseline for every 100KM driven", threePassNewBase, threePassFactoryBase, 0);
        assertNotEquals("updating battery baseline for every 100KM driven", fourPassNewBase, fourPassFactoryBase, 0);
        
        System.out.println("Passenger 1 : "+onePassFactoryBase+","+onePassNewBase);
        System.out.println("Passenger 2 : "+twoPassFactoryBase+","+twoPassNewBase);
        System.out.println("Passenger 3 : "+threePassFactoryBase+","+threePassNewBase);
        System.out.println("Passenger 4 : "+fourPassFactoryBase+","+fourPassNewBase);
    }
    
    /*
        This test determines if an object for the User class can be created.
    */
    @Test
    public void userTest() {
        int noOfCars = 1;
        int[] cellsPerBattery = {6345};
        User testUser = createUser(noOfCars, cellsPerBattery);
        assertNotNull(testUser);
    }
    
    /*
        This test determines if the total number of cars owned by the user is correct.
    */
    @Test
    public void userCarTest() {
        User testUser = new User();
        assertEquals(0,testUser.getTotalCars());
    } 
    
}
