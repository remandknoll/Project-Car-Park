/**
 * Description of the class CarparkMain
 * This class is the main class of the whole project and runs the new simulator
 * 
 * @author ProjectGroup of ITV1C (Jesse Tijsma, Dennis Vrieling, Mark Dissel, Remand Knol)
 * @version 0.1
 */
public class CarparkMain
{
    /**
     * The starting point for the car park simulation
     * @param arg Program Arguments
     */    
    public static void main(String[] args)
    {
        Simulator simulator = new Simulator();
        simulator.run();
    }    
}