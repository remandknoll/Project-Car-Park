/**
 * Description of the class Car
 * This Class contains all details about a Car in progress.
 * 
 * @author ProjectGroup of ITV1C (Jesse Tijsma, Dennis Vrieling, Mark Dissel, Remand Knol)
 * @version 0.1
 */
public abstract class Car {
    // instance variables
    private Location location;
    private int minutesLeft;
    private boolean isPaying;

    /**
     * Constructor for objects of class Car
     */
    public Car() {

    }
    
    /**
     * This is the Location method; shows the location of the car
     * @return location
     */
    public Location getLocation() {
        return location;
    }
    
    /**
     * This is the setLocation method; this method sets the location of the car with the user-input of the param
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }
    
    /**
     * This is the getMinutesLeft method; returns the minutes left on the parking ticket
     * @return minutesLeft
     */
    public int getMinutesLeft() {
        return minutesLeft;
    }
    
    /**
     * This is the setMinutesLeft method; sets the minutes how long the parking ticket is valid from 
     * user-input parameters
     * @param  int minutesLeft
     */
    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    /**
     * This is the getIsPaying method; returns if paid true or false
     * @return boolean isPaying (true or false)
     */     
    public boolean getIsPaying() {
        return isPaying;
    }
   
    /**
     * The is the setIsPaying method; sets the boolean of isPaying, if paid for ticket or not.
     * @param  boolean isPaying (true or false)
     */
    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }
    
    /**
     * This is the tick method; this method calculates the minutesLeft minus each minut. 
     */
    public void tick() {
        minutesLeft--;
    }

}