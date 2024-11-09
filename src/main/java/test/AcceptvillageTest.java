package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import model.Location;
import model.LocationType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.LocationService;

public class AcceptvillageTest {

    private static LocationService locationService;

    @BeforeAll
    public static void setup() {
        locationService = new LocationService();
    }

    @Test
    public void testAcceptVillageAndReturnProvince() {
        // Step 1: Programmatically create a full hierarchy
        // Create a province
        Location province = locationService.createLocation("PR01", "Southern Province", LocationType.PROVINCE, null);
        
        // Create a district under the province
        Location district = locationService.createLocation("DT01", "Huye District", LocationType.DISTRICT, province);
        
        // Create a sector under the district
        Location sector = locationService.createLocation("SC01", "Ngoma Sector", LocationType.SECTOR, district);
        
        // Create a cell under the sector
        Location cell = locationService.createLocation("CL01", "Kigoma Cell", LocationType.CELL, sector);
        
        // Step 2: User only inputs the village details
        // Village under the cell (only village info is inputted by the user)
        System.out.println("Insert Village Code: ");
        String villageCode = new java.util.Scanner(System.in).nextLine();
        
        System.out.println("Insert Village Name: ");
        String villageName = new java.util.Scanner(System.in).nextLine();

        Location village = locationService.createLocation(villageCode, villageName, LocationType.VILLAGE, cell);

        // Step 3: Retrieve the province based on the village
        Location resultProvince = locationService.getProvinceByVillage(village);

        // Step 4: Assertions
        assertNotNull(resultProvince); // Ensure the province is found
        System.out.println("Province ID: " + resultProvince.getLocationId());
        System.out.println("Province Name: " + resultProvince.getLocationName());
        assertEquals("Southern Province", resultProvince.getLocationName()); // Ensure the correct province is returned
        assertEquals(LocationType.PROVINCE, resultProvince.getLocationType()); // Ensure the returned location is of type PROVINCE
    }

    @AfterAll
    public static void tearDown() {
        locationService.closeSession(); // Close the session after the tests
    }
}
