import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * Description of the class SimulatorView
 * This Class generates the graphic view of the simulator
 * 
 * @author ProjectGroup of ITV1C (Jesse Tijsma, Dennis Vrieling, Mark Dissel, Remand Knol)
 * @version 0.1
 */
public class SimulatorView extends JFrame {
    // instance variables
    private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    private Car[][][] cars;

    /**
     * Constructor of the class SimulatorView
     * @param int numberOfFloors that the simulator needs to make
     * @param int numberOfRows that the simulator needs to make
     * @param int numberOfPlaces that the simulator needs to make
     */
    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        carParkView = new CarParkView();

        Container contentPane = getContentPane();
        //contentPane.add(stepLabel, BorderLayout.NORTH);
        contentPane.add(carParkView, BorderLayout.CENTER);
        //contentPane.add(population, BorderLayout.SOUTH);
        pack();
        setVisible(true);
 
        updateView();
    }
    
    /**
     * This is the updateView method; this method clears and updates the view
     */
    public void updateView() {
        carParkView.updateView();
    }
    
    /**
     * This is the getNumberOfFloors method; this method gives the amount of floors within the car park
     * @return numberOfFloors of the car park
     */
     public int getNumberOfFloors() {
            return numberOfFloors;
        }
        
    /**
     * This is the getNumberOfRows method; this method gives the amount of rows within the car park
     * @return numberOfRows of the car park
     */
        public int getNumberOfRows() {
            return numberOfRows;
        }
    
    /**
     * This is the getNumberOFPlaces method; this method gives the amount of pleaces within the car park
     * @return numberOfPlaces of the car park
     */
        public int getNumberOfPlaces() {
            return numberOfPlaces;
        }
    
    /**
     * This is the getCarAt method; this method gives the location of the car
     * @param  Location location of the car
     * @return cars location in floor, row and place
     */
        public Car getCarAt(Location location) {
            if (!locationIsValid(location)) {
                return null;
            }
            return cars[location.getFloor()][location.getRow()][location.getPlace()];
        }
    
    /**
     * This is the setCarAt method; this method sets the location of a specific car inputted by parameters
     * @param  Location location and Car car where the specific car needs to be placed
     * @return set succesfully or not (true or false)
     */
        public boolean setCarAt(Location location, Car car) {
            if (!locationIsValid(location)) {
                return false;
            }
            Car oldCar = getCarAt(location);
            if (oldCar == null) {
                cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
                car.setLocation(location);
                return true;
            }
            return false;
        }
    
    /**
     * This is the removeCarAt method; removes the car from a specific location entered by parameters
     * @param Location location where the car has to be removed from
     * @return car if succesfully removed from position else return null 
     */
        public Car removeCarAt(Location location) {
            if (!locationIsValid(location)) {
                return null;
            }
            Car car = getCarAt(location);
            if (car == null) {
                return null;
            }
            cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
            car.setLocation(null);
            return car;
        }
    
    /**
     * This is the getFirstFreeLocation method; this method gives the first free location availab
     * @return location of the free place, if there isn't return null
     */
        public Location getFirstFreeLocation() {
            for (int floor = 0; floor < getNumberOfFloors(); floor++) {
                for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        if (getCarAt(location) == null) {
                            return location;
                        }
                    }
                }
            }
            return null;
        }
    
    /**
     * This is the getFirstLeavingCar method; this method gives the first leaving car
     * @return the first leaving Car object
     */
        public Car getFirstLeavingCar() {
            for (int floor = 0; floor < getNumberOfFloors(); floor++) {
                for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                            return car;
                        }
                    }
                }
            }
            return null;
        }
    
    /**
     * This is the tick method; this method runs a tick of the simulation for all cars
     */
        public void tick() {
            for (int floor = 0; floor < getNumberOfFloors(); floor++) {
                for (int row = 0; row < getNumberOfRows(); row++) {
                    for (int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        if (car != null) {
                            car.tick();
                        }
                    }
                }
            }
        }
    
    /**
     * This is the locationIsValid method; this method checks if the location is valid
     * 
     * @param Location location to check if valid
     * @return if the location is valid or not (true or false)
     */
        private boolean locationIsValid(Location location) {
            int floor = location.getFloor();
            int row = location.getRow();
            int place = location.getPlace();
            if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
                return false;
            }
            return true;
        }
    
    private class CarParkView extends JPanel {
        
        private Dimension size;
        private Image carParkImage;    
    
        /**
         * Constructor for objects of class CarPark
         */
        public CarParkView() {
            size = new Dimension(0, 0);
        }
    
        /**
         * Overridden. Tell the GUI manager how big we would like to be.
         */
        public Dimension getPreferredSize() {
            return new Dimension(800, 500);
        }
    
        /**
         * Overriden. The car park view component needs to be redisplayed. Copy the
         * internal image to screen.
         */
        public void paintComponent(Graphics g) {
            if (carParkImage == null) {
                return;
            }
    
            Dimension currentSize = getSize();
            if (size.equals(currentSize)) {
                g.drawImage(carParkImage, 0, 0, null);
            }
            else {
                // Rescale the previous image.
                g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
            }
        }
    
        public void updateView() {
            // Create a new car park image if the size has changed.
            if (!size.equals(getSize())) {
                size = getSize();
                carParkImage = createImage(size.width, size.height);
            }
            Graphics graphics = carParkImage.getGraphics();
            for(int floor = 0; floor < getNumberOfFloors(); floor++) {
                for(int row = 0; row < getNumberOfRows(); row++) {
                    for(int place = 0; place < getNumberOfPlaces(); place++) {
                        Location location = new Location(floor, row, place);
                        Car car = getCarAt(location);
                        Color color = car == null ? Color.white : Color.red;
                        drawPlace(graphics, location, color);
                    }
                }
            }
            repaint();
        }
    
        /**
         * Paint a place on this car park view in a given color.
         */
        private void drawPlace(Graphics graphics, Location location, Color color) {
            graphics.setColor(color);
            graphics.fillRect(
                    location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                    60 + location.getPlace() * 10,
                    20 - 1,
                    10 - 1); // TODO use dynamic size or constants
        }
    }

}
