package vehicles;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;

import vehicles.maintenance.TruckMaintenance;
import vehicles.maintenance.VehicleMaintenance;

public class VehicleSystem {

	public VehicleSystem() {
		
	}
	
	private Vehicle[] vehicles = new Vehicle[0];
	
	public Vehicle[] getVehicles() {
		return vehicles;
	}
	
	public void loadVehicles(String fileName) throws IOException {
		BufferedReader objReader = new BufferedReader(new FileReader(fileName));
		String strCurrentLine;
		while ((strCurrentLine = objReader.readLine()) != null) {
			//split(;)
			Vehicle v;
			StringTokenizer st = new StringTokenizer(strCurrentLine, ";");
			
				String rego = st.nextToken();
				int year = Integer.parseInt(st.nextToken());
				String make = st.nextToken();
				String model = st.nextToken();
				int odometer = Integer.parseInt(st.nextToken());
				int odometerWhenLastServiced = Integer.parseInt(st.nextToken());
				int odometerServiceInterval = Integer.parseInt(st.nextToken());
				
				if (st.hasMoreTokens()) {
					double weightLimit = Double.parseDouble(st.nextToken());
					int jobsSinceLastService = Integer.parseInt(st.nextToken());
					v = new Truck(rego, year, make, model, weightLimit, new TruckMaintenance(odometer, odometerWhenLastServiced, odometerServiceInterval, jobsSinceLastService));
				}
				else
					v = new Van(rego, year, make, model, new VehicleMaintenance(odometer, odometerWhenLastServiced, odometerServiceInterval));
				
			addVehicle(v);
		}
		objReader.close();
	}
	
	public void saveVehicles(String fileName) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(fileName)));
		for (int i = 0; i<vehicles.length; i++) {
			bw.write(vehicles[i].toString());
			bw.write("\n");
		}
		bw.close();
	}
	
	public Vehicle getVehicle(String rego) {
		for (int i = 0; i<vehicles.length; i++) {
			if (vehicles[i].getRego().equalsIgnoreCase(rego))
				return vehicles[i];
		}
		return null;
	}
	
	public boolean hasVehicle(String rego) {
		for (int i = 0; i<vehicles.length; i++) {
			if (vehicles[i].getRego() == rego)
				return true;
		}
		return false;
	}
	
	public boolean addVehicle(Vehicle vehicle) {
		for (int i = 0; i<vehicles.length; i++) {
			if (vehicles[i] == vehicle)
				return false;
		}
		
		Vehicle[] proxyarray = new Vehicle[vehicles.length+1];
		for (int i = 0; i < vehicles.length; ++i) {
			proxyarray[i] = vehicles[i];
		}
		proxyarray[vehicles.length] = vehicle;
		vehicles = proxyarray;
		
		return true;
	}
	
	public boolean removeVehicle(String rego) {
		for (int i = 0; i < vehicles.length; i++) {
			if (vehicles[i].getRego().equalsIgnoreCase(rego)) {

				Vehicle[] proxyarray = new Vehicle[vehicles.length - 1];
				int proxyloop = 0;
				for (int j = 0; j < vehicles.length; ++j) {
					if (!rego.equalsIgnoreCase(vehicles[j].getRego())) {
						proxyarray[proxyloop] = vehicles[j];
						proxyloop += 1;
					}

				}
				vehicles = proxyarray;
				return true;
			}	
		}
		return false;
	}
}
