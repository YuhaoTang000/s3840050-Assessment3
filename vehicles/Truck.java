package vehicles;

import vehicles.jobs.Job;
import vehicles.maintenance.TruckMaintenance;

public class Truck extends AbstractVehicle{
	
	public Truck(String rego, int year, String make, String model, double weightLimit, TruckMaintenance maintenance) {
		super(rego, year, make, model, maintenance);
	}

	
	public double calculateJobCost(Job job) {
		return 0;
	}

}
