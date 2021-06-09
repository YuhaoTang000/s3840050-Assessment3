package vehicles.maintenance;

import vehicles.jobs.Job;

public class TruckMaintenance extends VehicleMaintenance{
	
	public TruckMaintenance(int odometer, int odometerWhenLastServiced, int odometerServiceInterval, int jobsSinceLastService) {
		super(odometer, odometerWhenLastServiced, odometerServiceInterval);
		this.jobsSinceLastService = jobsSinceLastService;
	}
	
	private int jobsSinceLastService;
	
	public int getJobsSinceLastService() {
		return jobsSinceLastService;
	}
	
	public int getJobsRemainingBeforeNextService() {
		int jobs = 5 - jobsSinceLastService;
		return jobs;
	}

	public boolean canTravel(Job job) {
		if (super.canTravel(job)&& getJobsRemainingBeforeNextService() > 0)
			return true;
		else
			return false;
	}
	
	public void travel(Job job) {
		super.canTravel(job);
		jobsSinceLastService += 1;
	}
	
	public void service(Job job) {
		super.service();
		jobsSinceLastService = 0;
	}
}
