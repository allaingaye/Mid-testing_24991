package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import model.Location;
import model.LocationType;
import service.LocationService;

public class LocationServiceTest {

    private static LocationService locationService;
    private static Scanner scanner;

    @BeforeAll
    public static void setup() {
        locationService = new LocationService();
        scanner = new Scanner(System.in); // Initialize the scanner for user input
    }

    // Method to take user input and create a location
    private Location createLocationByUserInput(Location parentLocation) {
        System.out.println("Enter the location code:");
        String code = scanner.nextLine();
        System.out.println("Enter the location name:");
        String name = scanner.nextLine();
        System.out.println("Enter the location type (PROVINCE, DISTRICT, SECTOR, CELL, VILLAGE):");
        String typeStr = scanner.nextLine().toUpperCase();
        
        LocationType type = LocationType.valueOf(typeStr); // Parse the type from the user's input
        
        return locationService.createLocation(code, name, type, parentLocation); // Create and return the location
    }

    @Test
    public void testCreateProvinceByUser() {
        System.out.println("Please insert a Province:");
        Location province = createLocationByUserInput(null); // Province has no parent

        assertNotNull(province.getLocationId());
        System.out.println("Province created with ID: " + province.getLocationId());
        assertEquals(LocationType.PROVINCE, province.getLocationType());
    }

    @Test
    public void testCreateDistrictUnderProvinceByUser() {
        System.out.println("Please insert a Province:");
        Location province = createLocationByUserInput(null); // First, the user creates a Province

        System.out.println("Please insert a District under the Province:");
        Location district = createLocationByUserInput(province); // Then, the user creates a District under that Province

        assertNotNull(district.getLocationId());
        System.out.println("District created with ID: " + district.getLocationId());
        assertEquals(province.getLocationId(), district.getParentLocation().getLocationId());
        assertEquals(LocationType.DISTRICT, district.getLocationType());
    }

    @Test
    public void testCreateFullHierarchyByUser() {
        System.out.println("Please insert a Province:");
        Location province = createLocationByUserInput(null); // Province

        System.out.println("Please insert a District under the Province:");
        Location district = createLocationByUserInput(province); // District

        System.out.println("Please insert a Sector under the District:");
        Location sector = createLocationByUserInput(district); // Sector

        System.out.println("Please insert a Cell under the Sector:");
        Location cell = createLocationByUserInput(sector); // Cell

        System.out.println("Please insert a Village under the Cell:");
        Location village = createLocationByUserInput(cell); // Village

        // Assertions
        assertNotNull(village.getLocationId());
        System.out.println("Village created with ID: " + village.getLocationId());
        assertEquals(LocationType.VILLAGE, village.getLocationType());
        assertEquals(cell.getLocationId(), village.getParentLocation().getLocationId());
    }

    @AfterAll
    public static void tearDown() {
        scanner.close(); // Close the scanner after all tests are done
        locationService.closeSession();
    }
}

