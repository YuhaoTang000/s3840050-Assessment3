package vehicles;

import vehicles.jobs.Job;
import vehicles.maintenance.VehicleMaintenance;

public class Van extends AbstractVehicle {

	public Van(String rego, int year, String make, String model, VehicleMaintenance maintenance) {
		super(rego, year, make, model, maintenance);
	}

	public double calculateJobCost(Job job) {
		return (200 + 0.85 * job.getDistance());
	}

}
