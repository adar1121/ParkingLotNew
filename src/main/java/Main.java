import java.time.Duration;
import java.time.Instant;

public class Main {

   static int truckCounter = 0;
    static int carCounter = 0;
    static int motorcycleCounter = 0;
    static int numberOfVehicleWhichSpentNight = 0;
    public static void main(String[] args) {
        String[][] logs = {
                {"86453", "CAR", "2020-01-01T04:24:00.000Z", "ENTRANCE"},
                {"11353", "TRUCK", "2020-01-02T05:00:00.000Z", "EXIT"},
                {"11353", "TRUCK", "2020-01-01T00:00:00.000Z", "ENTRANCE"},
                {"86453", "CAR", "2020-01-02T08:25:00.000Z", "EXIT"}
        };

        String vehicleID = "";
        String vehicleActionCoying = "";
        String enterTime = "";
        String exitTime = "";
        String vehicleType = "";
        int noExitFoundCounter = 0;
        int minuteDuration;
        int vehicleCurrentlyInParkingLot = 0;
        int dayDuration = 0;

        for (int counter = 0; counter < logs.length; counter++){
            noExitFoundCounter = 0;
            vehicleID = logs[counter][0];
            if (logs[counter][3] == "ENTRANCE") {
                enterTime = logs[counter][2];
                vehicleType = logs[counter][1];
                for (int insideCounter = 0; insideCounter < logs.length; insideCounter++) {
                    if (vehicleID == logs[insideCounter][0]) {
                        if(logs[insideCounter][3] == "EXIT"){
                            // calculate enter time and exit time
                            exitTime = logs[insideCounter][2];

                            dayDuration = dayCalculator(enterTime,exitTime);

                            if(dayDuration >= 1){
                                vehicleTypeSearchingForXDays(vehicleType);
                            }

                            else {
                                minuteDuration = timeCalculator(enterTime,exitTime);
                                vehicleTypeSearchingForXHours(vehicleType,minuteDuration);
                            }



                        }
                        else {
                            noExitFoundCounter++;
                        }
                    }
                    else {
                        noExitFoundCounter++;
                    }
                    if (noExitFoundCounter == logs.length){
                        vehicleCurrentlyInParkingLot++;
                        exitTime = Instant.now().toString();
                        dayDuration = dayCalculator(enterTime,exitTime);
                        if(dayDuration >= 1){
                            vehicleTypeSearchingForXDays(vehicleType);
                        }
                        else {
                            minuteDuration = timeCalculator(enterTime,exitTime);
                        }

                    }
                }
            }
        }
        System.out.println("Numbers of Truck: " + truckCounter);
        System.out.println("Number of Car: " + carCounter);
        System.out.println("Number of Motorcycle: " + motorcycleCounter);
        System.out.println("Number of vehicles that currently in the Parking Lot: " + vehicleCurrentlyInParkingLot);
        System.out.println("Number of vehicle who spent the night: " + numberOfVehicleWhichSpentNight);
    }
    static int timeCalculator(String enterTime, String exitTime){
        int durationCalculator = Integer.parseInt(String.valueOf(Duration.between(Instant.parse(enterTime),Instant.parse(exitTime)).toMinutes()));
        return durationCalculator;
    }
    static int dayCalculator(String enterTime, String exitTime){
        int dayCalculator = Integer.parseInt(String.valueOf(Duration.between(Instant.parse(enterTime),Instant.parse(exitTime)).toDays()));
        return dayCalculator;
    }
    static void vehicleTypeSearchingForXHours(String vehicleType, int duration){

        if(vehicleType == "TRUCK" && duration > 180){
            truckCounter++;
        }
        else if(vehicleType == "CAR" && duration > 120){
            carCounter++;
        }
        else if(vehicleType == "MOTORCYCLE" && duration > 60){
            motorcycleCounter++;
        }
    }


    static void vehicleTypeSearchingForXDays(String vehicleType){
        numberOfVehicleWhichSpentNight++;
        if(vehicleType == "TRUCK"){
            truckCounter++;
        }
        else if(vehicleType == "CAR"){
            carCounter++;
        }
        else if(vehicleType == "MOTORCYCLE"){
            motorcycleCounter++;
        }
    }
}