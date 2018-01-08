package power.management;

import java.util.UUID;


public class Cell {
    
    private final String cellID;
    private double initialCellCapacity; //max capacity of each cell.
    private double currentCellCapacity;
    
    public Cell(double initialCapacity) {
        this.cellID = UUID.randomUUID().toString();
        this.initialCellCapacity = initialCapacity;
        this.currentCellCapacity = initialCapacity;
    }
    
    public double getInitialCapacity() {        
        return (this.initialCellCapacity/1000); //returns the value in kW
    }
    public double getCurrentCapacity() {
        return (this.currentCellCapacity/1000);
    }
    public String getCellID() {
        return this.cellID;
    }
    
    public void setInitialCapacity(double initialCapacity) {
        this.initialCellCapacity = initialCapacity;
    } 
    public void setCurrentCapacity(double currentCapacity) {
        this.currentCellCapacity = currentCapacity;
    }
}
