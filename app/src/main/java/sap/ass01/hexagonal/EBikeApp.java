package sap.ass01.hexagonal;

import sap.ass01.hexagonal.application.EBikeApplicationImpl;
import sap.ass01.hexagonal.application.ports.EBikeApplication;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.application.ports.RideRepository;
import sap.ass01.hexagonal.application.ports.UserRepository;

import sap.ass01.hexagonal.infrastructure.adapters.persistence.DatabaseType;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.EBikeRepositoryAdapter;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.RideRepositoryAdapter;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.UserRepositoryAdapter;
import sap.ass01.hexagonal.infrastructure.adapters.view.ViewAdapter;

public class EBikeApp {
    public static void main(String[] args) {
        EBikeRepository eBikeRepository = new EBikeRepositoryAdapter(DatabaseType.DISK);
        UserRepository userRepository = new UserRepositoryAdapter(DatabaseType.DISK);
        RideRepository rideRepository = new RideRepositoryAdapter(DatabaseType.DISK);

        EBikeApplication app = new EBikeApplicationImpl(eBikeRepository, userRepository, rideRepository);
        ViewAdapter adapter = new ViewAdapter(app);
        adapter.init();

    }
}
