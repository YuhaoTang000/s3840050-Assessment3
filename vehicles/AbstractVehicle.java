package vehicles;

import vehicles.exceptions.IllegalRegistrationException;
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
		if (Character.isDigit(rego.charAt(0)) || Character.isDigit(rego.charAt(4)) || Character.isDigit(rego.charAt(5)) || !Character.isUpperCase(rego.charAt(0)) || !Character.isUpperCase(rego.charAt(4)) || !Character.isUpperCase(rego.charAt(5))) {
			new IllegalRegistrationException("Uppercase character expected.");
		}
		else if (!Character.isDigit(rego.charAt(1)) || !Character.isDigit(rego.charAt(2)) || !Character.isDigit(rego.charAt(3))) {
			new IllegalRegistrationException("Numerical character expected.");
		}
		else if (!(rego.length()==6)) {
			new IllegalRegistrationException("Rego must be 6 characters.");
		}
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
