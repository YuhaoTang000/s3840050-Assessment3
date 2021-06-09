package vehicles;

import vehicles.jobs.Job;
import vehicles.maintenance.TruckMaintenance;

public class Truck extends AbstractVehicle{
	
	public Truck(String rego, int year, String make, String model, double weightLimit, TruckMaintenance maintenance) {
		super(rego, year, make, model, maintenance);
		this.weightLimit = weightLimit;
	}
	
	private final double weightLimit;
	
	public double getWeightLimit() {
		return weightLimit;
	}

	public TruckMaintenance getMaintenance() {
		return (TruckMaintenance) super.getMaintenance();
	}
	
	public double calculateJobCost(Job job) {
		return (400 + 50*weightLimit + 1.15* job.getDistance());
	}

	public String toString() {
		return (super.toString() + ";" + weightLimit + ";" + getMaintenance().getJobsSinceLastService());
	}
}
