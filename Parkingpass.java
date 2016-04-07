public class Parkingpass{

        int ParkingPass;
        int noParkingPass;
    public Parkingpass()
    {
        int ParkingPass = 0;
        int noParkingPass = 0;
        
    }
    
        public void incrementParkingPass()
        {
            ParkingPass++;
        }
        
        public int getAmountParkingPass()
        {
            return ParkingPass;
        }
       
        public int getAmountNoParkingPass()
        {
            return noParkingPass;
        }
        
        public void incrementNoParkingPass()
        {
            noParkingPass++;
        }
}
