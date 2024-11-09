package service;

import model.Location;
import model.LocationType;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

public class LocationService {

    private Session session;

    public LocationService() {
        session = HibernateUtil.getSession().openSession();
    }

    public Location createLocation(String code, String name, LocationType type, Location parentLocation) {
        Transaction transaction = null;
        Location location = null;
        try {
            transaction = session.beginTransaction();
            location = new Location();
            location.setLocationCode(code);
            location.setLocationName(name);
            location.setLocationType(type);
            location.setParentLocation(parentLocation);
            session.save(location); // Save the location object to the database
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return location;
    }

    // Method to get the province based on a given village
    public Location getProvinceByVillage(Location village) {
        Location currentLocation = village;

        // Traverse up the hierarchy until we find the province
        while (currentLocation != null && currentLocation.getLocationType() != LocationType.PROVINCE) {
            currentLocation = currentLocation.getParentLocation();
        }

        return currentLocation; // Will return the province or null if not found
    }

    public void closeSession() {
        if (session != null && session.isOpen()) {
            session.close(); // Close the session when done
        }
    }
}
