package sap.ass01.hexagonal;

import sap.ass01.hexagonal.application.EBikeApplicationImpl;
import sap.ass01.hexagonal.application.ports.EBikeApplication;
import sap.ass01.hexagonal.application.ports.EBikeRepository;
import sap.ass01.hexagonal.application.ports.RideRepository;
import sap.ass01.hexagonal.application.ports.UserRepository;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DiskEBikeRepositoryImpl;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DiskRideRepositoryImpl;
import sap.ass01.hexagonal.infrastructure.adapters.persistence.DiskUserRepositoryImpl;
import sap.ass01.hexagonal.infrastructure.adapters.view.ViewAdapter;

public class EBikeApp {
    public static void main(String[] args) {
        EBikeRepository eBikeRepository = new DiskEBikeRepositoryImpl();
        UserRepository userRepository = new DiskUserRepositoryImpl();
        RideRepository rideRepository = new DiskRideRepositoryImpl();

        EBikeApplication app = new EBikeApplicationImpl(eBikeRepository, userRepository, rideRepository);
        ViewAdapter adapter = new ViewAdapter(app);
        adapter.init();
    }
}
