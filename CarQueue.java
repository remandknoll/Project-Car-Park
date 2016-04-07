import java.util.LinkedList;
import java.util.Queue;
/**
 * Description of the class CarQueue
 * This Class contains the LinkedList queue, which is for the storage of cars in the CarQueue
 * 
 * @author ProjectGroup of ITV1C (Jesse Tijsma, Dennis Vrieling, Mark Dissel, Remand Knol)
 * @version 0.1
 */

public class CarQueue {
    // instance variable
    private Queue<Car> queue = new LinkedList<>();

    /**
     * This is the addCar method; this adds a car given by the parameters to the queue
     * @param adds the car given by user input in the parameters 
     * @return car added or not (true or false)
     */
    public boolean addCar(Car car) {
        return queue.add(car);
    }
    
    /**
     *This is the removeCar method; this removes the car out of the queue
     * @return car from the queue
     */
    public Car removeCar() {
        return queue.poll();
    }

}

