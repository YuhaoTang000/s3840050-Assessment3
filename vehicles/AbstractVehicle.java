package vehicles;

import vehicles.maintenance.VehicleMaintenance;

public abstract class AbstractVehicle implements Vehicle{
	
	public AbstractVehicle(String rego, int year, String make, String model, VehicleMaintenance maintenance) {
		this.rego = rego;
		this.year = year;
		this.make = make;
		this.model = model;
		this.maintenance = maintenance;
	}
	
	private final String rego;
	private final int year;
	private final String make;
	private final String model;
	private final VehicleMaintenance maintenance;

	public static void isValidRego(String rego) {
		
	}
}
