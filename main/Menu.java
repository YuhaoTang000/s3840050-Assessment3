package main;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import vehicles.AbstractVehicle;
import vehicles.Truck;
import vehicles.Vehicle;
import vehicles.VehicleSystem;
import vehicles.jobs.Job;
import vehicles.maintenance.TruckMaintenance;
import vehicles.maintenance.VehicleMaintenance;

public class Menu {
	private static final String VEHICLES_FILENAME = "lib/vehicles.txt";

	private static final int DISPLAY_VEHICLES = 1;
	private static final int PERFORM_JOB = 2;
	private static final int SERVICE_VEHICLE = 3;
	private static final int REMOVE_VEHICLE = 4;
	private static final int SAVE_AND_QUIT = 8;
	private static final int QUIT = 9;

	private final VehicleSystem vehicleSystem = new VehicleSystem();
	private final Scanner scanner = new Scanner(System.in);

	public void run() {
		// The menu only runs if loading the vehicles is successful.
		if (loadVehicles())
			runMenu();

		System.out.println("Program ending.");
	}

	private void printMenu() {
		// Location #1.
		System.out.println("");
		System.out.println("--- Options ---");
		System.out.println("1\tDisplay Vehicles");
		System.out.println("2\tPerform Job");
		System.out.println("3\tService Vehicle");
		System.out.println("4\tRemove Vehicle");
		System.out.println("8\tSave and Quit");
		System.out.println("9\tQuit");
		System.out.println("");

	}

	private void runMenu() {
		// Scanner sc= new Scanner(System.in);
		boolean runMenu = true;
		while (runMenu) {
			printMenu();
			System.out.print("Select an option: ");

			// Location #2.

			String input = scanner.nextLine(); // sc.nextLine();
			switch (input) {
			case "1":
				displayVehicles();
				break;
			case "2":
				performJob();
				break;
			case "3":
				serviceVehicle();
				break;
			case "4":
				removeVehicle();
				break;
			case "8":
				saveVehicles();
				break;
			case "9":
				runMenu = false;
				break;
			default:
				printErrorMessage();
			}

		}

	}

	private Integer getPositiveIntegerInput(String prompt) {
		int integerInput = 0;
		while (integerInput < 1) {
			System.out.print(prompt);

			// Location #3.

			String input = scanner.nextLine();
			try {
				Integer.parseInt(input);
			} catch (Exception e) {
				continue;
			}
			if (input.isEmpty()) {

				return null;
			}

			else if (Integer.parseInt(input) > 0) {

				integerInput = Integer.parseInt(input);
			} else {
				printErrorMessage();
			}

		}

		return integerInput;
	}

	private void displayVehicles() {
		final String FORMAT = "%-8s%-8s%-8s%-10s%-10s%-10s%-20s%-20s";

		Vehicle[] vehicles = vehicleSystem.getVehicles();
		if (vehicles.length == 0) {
			System.out.println("No vehicles present.");
			return;
		}

		System.out.println(String.format(FORMAT, "Type", "Rego", "Year", "Make", "Model", "Odometer",
				"Distance Remaining", "Jobs Remaining"));

		for (int i = 0; i < vehicles.length; i++) {
			Vehicle vehicle = vehicles[i];
			VehicleMaintenance maintenance = vehicle.getMaintenance();

			String distanceRemaining = String.valueOf(maintenance.getDistanceRemainingBeforeNextService()) + " km";
			String jobsRemaining = vehicle instanceof Truck
					? String.valueOf(((Truck) vehicle).getMaintenance().getJobsRemainingBeforeNextService())
					: "n/a";

			System.out.println(String.format(FORMAT, vehicle.getClass().getSimpleName(), vehicle.getRego(),
					String.valueOf(vehicle.getYear()), vehicle.getMake(), vehicle.getModel(),
					String.valueOf(maintenance.getOdometer()) + " km", distanceRemaining, jobsRemaining));
		}
	}

	private void performJob() {
		Vehicle vehicle = getVehicle();

		// Return to main menu.
		if (vehicle == null)
			return;

		System.out.println(String.format("Vehicle %s can travel %d km.", vehicle.getRego(),
				vehicle.getMaintenance().getDistanceRemainingBeforeNextService()));

		// Location #4.
		if (vehicle instanceof Truck) {
			System.out.println(
					"And has " + ((TruckMaintenance) vehicle.getMaintenance()).getJobsRemainingBeforeNextService()
							+ " jobs remaining.");
		}

		Integer distance = getPositiveIntegerInput("Enter distance: ");

		// Return to main menu.
		if (distance == null)
			return;

		Job job = new Job(distance);

		if (!vehicle.canPerformJob(job)) {
			printErrorMessage(String.format("Vehicle %s unable to perform job.", vehicle.getRego()));
			return;
		}

		double cost = vehicle.performJob(job);

		System.out.println(String.format("Job booked for %d km, the cost is: $%.2f", distance, cost));
	}

	private void serviceVehicle() {
		Vehicle vehicle = getVehicle();

		// Return to main menu.
		if (vehicle == null)
			return;

		// Location #5.
		if (vehicle instanceof Truck)
			((TruckMaintenance) vehicle.getMaintenance()).service();
		else
			vehicle.getMaintenance().service();

		System.out.println(String.format("Successfully serviced vehicle with rego %s.", vehicle.getRego()));
	}

	private void removeVehicle() {
		Vehicle vehicle = getVehicle();

		// Return to main menu.
		if (vehicle == null)
			return;

		String rego = vehicle.getRego();
		if (vehicleSystem.removeVehicle(rego))
			System.out.println(String.format("Successfully removed vehicle with rego %s.", rego));
		else
			printErrorMessage(String.format("Unable to remove vehicle with rego %s.", rego));
	}

	private Vehicle getVehicle() {
		System.out.print("Enter rego: ");
		String rego = scanner.nextLine();

		if (rego.isEmpty())
			return null;

		Vehicle vehicle = null;
		try {
			// Validate rego.
			AbstractVehicle.isValidRego(rego);

			vehicle = vehicleSystem.getVehicle(rego);
			if (vehicle == null)
				printErrorMessage(String.format("No vehicle with rego %s found.", rego));
		} catch (Exception e) {
			printErrorMessage();
		}

		return vehicle;
	}

	private boolean loadVehicles() {
		// Location #6.
		try {
			vehicleSystem.loadVehicles(VEHICLES_FILENAME);
		} catch (IOException e) {
			System.out.print("Could not open vehicles.txt");
			return false;
		}
		return true;
	}

	private void saveVehicles() {
		try {
			System.out.print("Saving vehicles... ");
			vehicleSystem.saveVehicles(VEHICLES_FILENAME);
			System.out.println("complete.");
		} catch (IOException e) {
			printErrorMessage(e);
		}
	}

	private static void printErrorMessage() {
		printErrorMessage("Invalid, please try again.");
	}

	private static void printErrorMessage(Exception e) {
		printErrorMessage(String.format("Failed due to: %s", e.getMessage()));
	}

	private static void printErrorMessage(String message) {
		flushAndSleep(System.out);
		System.err.println(message);
		flushAndSleep(System.err);
	}

	private static void flushAndSleep(PrintStream stream) {
		// 50ms sleep.
		final int DEFAULT_SLEEP = 50;

		// The flush and sleep below is only present to help the Eclipse console
		// on Windows to output System.out and System.err streams in the
		// expected order.
		stream.flush();
		try {
			Thread.sleep(DEFAULT_SLEEP);
		} catch (InterruptedException e) {
		}
	}
}