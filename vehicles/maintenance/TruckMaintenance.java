package vehicles.maintenance;

public class TruckMaintenance extends VehicleMaintenance{
	
	public TruckMaintenance(int odometer, int odometerWhenLastServiced, int odometerServiceInterval, int jobsSinceLastService) {
		super(odometer, odometerWhenLastServiced, odometerServiceInterval);
	}

}
