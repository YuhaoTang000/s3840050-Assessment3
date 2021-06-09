package vehicles.maintenance;

import vehicles.jobs.Job;

public class VehicleMaintenance {

	public VehicleMaintenance(int odometer, int odometerWhenLastServiced, int odometerServiceInterval) {
		this.odometer = odometer;
		this.odometerWhenLastServiced = odometerWhenLastServiced;
		this.odometerServiceInterval = odometerServiceInterval;
	}

	private int odometer;
	private int odometerWhenLastServiced;
	private final int odometerServiceInterval;
	
	public int getOdometer() {
		return odometer;
	}
	
	public int getOdometerWhenLastServiced() {
		return odometerWhenLastServiced;
	}
	
	public int getOdometerServiceInterval() {
		return odometerServiceInterval;
	}

	public int getDistanceRemainingBeforeNextService() {
		int distance = odometerServiceInterval - odometer + odometerWhenLastServiced;
		return distance;
	}

	public boolean canTravel(Job job) {
		if (job.getDistance() >= getDistanceRemainingBeforeNextService())
			return false;
		return true;
	}

	public void travel(Job job) {
		if (!canTravel(job))
			System.out.println();
		odometer += job.getDistance();
	}

	public void service() {
		odometerWhenLastServiced = odometer;
	}
}
