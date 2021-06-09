package vehicles;

import vehicles.jobs.Job;
import vehicles.maintenance.VehicleMaintenance;

public interface Vehicle {
	
	public String getRego();
	public int getYear();
	public String getMake();
	public String getModel();
	public VehicleMaintenance getMaintenance();
	public boolean canPerformJob(Job job);
	public double performJob(Job job);
	public double calculateJobCost(Job job);
}
