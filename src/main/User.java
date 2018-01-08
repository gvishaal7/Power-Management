package power.management;

import java.util.ArrayList;
import java.util.UUID;

public class User {
    
    private final String userID;
    private ArrayList<Car> carList;
    
    public User() {
        userID = UUID.randomUUID().toString();
        carList = new ArrayList<>();
    }  
    
    public String getUser() {
        return this.userID;
    }
    /*
        Returns the total number of cars owned by this user.
    */
    public int getTotalCars() {
        return this.carList.size();
    }
    public ArrayList<Car> getCarList() {
        return this.carList;
    }
    
    public void addCars(Car newCar) {
        this.carList.add(newCar);            
    }
}
