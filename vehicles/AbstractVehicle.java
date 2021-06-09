package vehicles;

import vehicles.jobs.Job;
import vehicles.maintenance.VehicleMaintenance;

public abstract class AbstractVehicle implements Vehicle{
	
	public AbstractVehicle(String rego, int year, String make, String model, VehicleMaintenance maintenance) {
		this.rego = rego;
		this.year = year;
		this.make = make;
		this.model = model;
		this.maintenance = maintenance;
		isValidRego(rego);
	}
	
	private final String rego;
	private final int year;
	private final String make;
	private final String model;
	private final VehicleMaintenance maintenance;

	public static void isValidRego(String rego) {
		
	}
	
	public String getRego() {
		return rego;
	}
	
	public int getYear() {
		return year;
	}
	
	public String getMake() {
		return make;
	}
	
	public String getModel() {
		return model;
	}
	
	public VehicleMaintenance getMaintenance() {
		return maintenance;
	}
	
	public boolean canPerformJob(Job job) {
		return maintenance.canTravel(job);
	}
	
	public double performJob(Job job) {
		maintenance.travel(job);
		return calculateJobCost(job);
	}
	
	public String toString() {
		return (rego + ";" + year + ";" + make + ";" + model + ";" + maintenance.getOdometer() + ";" + maintenance.getOdometerWhenLastServiced() + ";" + maintenance.getOdometerServiceInterval() + ";");
	}
	
	public abstract double calculateJobCost(Job job);
}
