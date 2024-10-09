package sap.ass01.layered.persistence.repository.factory;

import sap.ass01.layered.persistence.ride.MongoRideRepository;
import sap.ass01.layered.persistence.ride.DiskRideRepository;
import sap.ass01.layered.persistence.ride.MySQLRideRepository;
import sap.ass01.layered.persistence.repository.DatabaseType;
import sap.ass01.layered.persistence.repository.RideRepository;

public class RideRepositoryFactory {
    public static RideRepository createRepository(DatabaseType dbType) {
        switch (dbType) {
            case IN_MEMORY:
                return new DiskRideRepository();
            case MONGODB:
                return new MongoRideRepository();
            case MYSQL:
                return new MySQLRideRepository();
            default:
                throw new UnsupportedOperationException("Database type not supported");
        }
    }
}
