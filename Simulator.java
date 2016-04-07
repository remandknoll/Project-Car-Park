import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
/**
 * Description of the class Location
 * This Class contains the details about the parking spots (floors, rows, places)
 * 
 * @author ProjectGroup of ITV1C (Jesse Tijsma, Dennis Vrieling, Mark Dissel, Remand Knol)
 * @version 0.1
 */
public class Simulator {
    // instance variables
    private CarQueue entranceCarQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;

    private int day = 0; 
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals= 50; // average number of arriving cars per hour
    int weekendArrivals = 90; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 10; // number of cars that can pay per minute
    int exitSpeed = 9; // number of cars that can leave per minute
    int parkingPassProbability = 6;
    
    private Parkingpass parkingPass;
    public int totalRevenue;
    private int totalMinutes;
    private GUI gui;

    /**
     * Constructor of the class: Simulator; creates the simulation
     */
    public Simulator() {
        entranceCarQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30);
        parkingPass = new Parkingpass();
        totalRevenue = 0;
        totalMinutes = 0;
        gui = new GUI();
        gui.openGUI();
    }

    /**
     * This is the run method; runs the simulation for a duration
     */
    public void run() {
        for (int i = 0; i < 10000; i++) {
            tick();
            gui.textArea2.setText("Aantal auto's weggegaan met Parking Pass: " + Integer.toString(parkingPass.getAmountParkingPass()));
            gui.textArea4.setText("Aantal auto's weggegaan zonder Parking Pass: " + Integer.toString(parkingPass.getAmountNoParkingPass()));
            int totalMoney = totalRevenue / 100;
            gui.textArea6.setText("Totale Omzet Prognose: €" + Integer.toString(totalMoney));

        }
    }

    /**
     * This is the tick method; calculates the times in minutes, hours and days
     */
    private void tick() {
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
            ? weekDayArrivals
            : weekendArrivals;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.1;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        int numberOfCarsPerMinute = (int)Math.round(numberOfCarsPerHour / 60);

        // Add the cars to the back of the queue.
        for (int i = 0; i < numberOfCarsPerMinute; i++) {
            int parkingPassChance = random.nextInt(parkingPassProbability);
            if(parkingPassChance == 0)
            {
                Car car = new ParkingPassCar();
                entranceCarQueue.addCar(car);
            }
            else {
                Car car = new AdHocCar();
                entranceCarQueue.addCar(car);
            }
            
        }

        // Remove car from the front of the queue and assign to a parking space.
        for (int i = 0; i < enterSpeed; i++) {
            Car car = entranceCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Find a space for this car.
            Location freeLocation = simulatorView.getFirstFreeLocation();
            if (freeLocation != null) {
                simulatorView.setCarAt(freeLocation, car);
                int stayMinutes = (int) (15 + random.nextFloat() * 10 * 60);
                if(car instanceof AdHocCar)
                {
                    if(stayMinutes >= 400)
                    {
                        totalRevenue += 2000;
                    }
                    else
                    {
                        totalRevenue += stayMinutes * 5;
                    }
                }
                car.setMinutesLeft(stayMinutes);
            }
        }

        // Perform car park tick.
        simulatorView.tick();

        // Add leaving cars to the exit queue.
        while (true) {
            Car car = simulatorView.getFirstLeavingCar();
            if (car == null) {
                break;
            }
            
            if(car instanceof ParkingPassCar)
            {
                exitCarQueue.addCar(car);
                parkingPass.incrementParkingPass();
            }
            else {
                paymentCarQueue.addCar(car);
                parkingPass.incrementNoParkingPass();
            }
            car.setIsPaying(true);
        }

        // Let cars pay.
        for (int i = 0; i < paymentSpeed; i++) {
            Car car = paymentCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // TODO Handle payment.
            simulatorView.removeCarAt(car.getLocation());
            exitCarQueue.addCar(car);
        }

        // Let cars leave.
        for (int i = 0; i < exitSpeed; i++) {
            Car car = exitCarQueue.removeCar();
            if (car == null) {
                break;
            }
            // Bye!
        }

        // Update the car park view.
        simulatorView.updateView();

        // Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
