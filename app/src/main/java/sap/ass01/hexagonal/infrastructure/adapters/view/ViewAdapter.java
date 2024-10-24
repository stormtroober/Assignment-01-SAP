package sap.ass01.hexagonal.infrastructure.adapters.view;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import sap.ass01.hexagonal.application.ports.EBikeApplication;
import sap.ass01.hexagonal.application.ports.entities.EBikeDTO;
import sap.ass01.hexagonal.domain.model.EBikeState;
import sap.ass01.hexagonal.application.ports.entities.RideDTO;
import sap.ass01.hexagonal.application.ports.entities.UserDTO;
import sap.ass01.hexagonal.application.ports.AdminViewPort;
import sap.ass01.hexagonal.application.ports.LoginViewPort;
import sap.ass01.hexagonal.application.ports.UserViewPort;
import sap.ass01.hexagonal.infrastructure.presentation.PresentationController;
import sap.ass01.hexagonal.infrastructure.presentation.views.MainView;


import java.util.*;

public class ViewAdapter implements LoginViewPort, AdminViewPort, UserViewPort {

    EBikeApplication application;

    private final BehaviorSubject<Collection<EBikeDTO>> allBikesSubject = BehaviorSubject.createDefault(Collections.emptyList());
    private final BehaviorSubject<Collection<UserDTO>> allUsersSubject = BehaviorSubject.createDefault(Collections.emptyList());
    private final BehaviorSubject<Collection<EBikeDTO>> availableBikesSubject = BehaviorSubject.createDefault(Collections.emptyList());


    public ViewAdapter(EBikeApplication application) {
        this.application = application;

    }

    public void init() {
        PresentationController presentationController = new PresentationController(this);
        MainView mainView = new MainView(presentationController);
        mainView.display();
        emitAllBikes();
        emitAllUsers();
        emitAvailableBikes();
    }

    @Override
    public Completable signUp(String userId, boolean isAdmin) {
        return Completable.fromAction(() -> {
            if(!application.addUser(userId, isAdmin))
                throw new IllegalArgumentException("User already exists.");
            emitAllUsers();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Single<UserDTO> logIn(String userId) {
        return Single.fromCallable(() -> {
            Optional<UserDTO> userOpt = application.getUser(userId);
            if (userOpt.isEmpty()) {
                throw new IllegalArgumentException("User not found.");
            }
            return userOpt.get();

        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Single<EBikeDTO> createEBike(String bikeId, double x, double y) {
        return Single.fromCallable(() -> {
            Optional<EBikeDTO> bike = application.addEbike(bikeId, x, y);
            if (bike.isEmpty())
                throw new IllegalArgumentException("Bike ID already exists.");
            emitAllBikes();
            emitAvailableBikes();
            return bike.get();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<Collection<EBikeDTO>> observeAllBikes() {
        return allBikesSubject.hide().observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<Collection<UserDTO>> observeAllUsers() {
        return allUsersSubject.hide().observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Single<EBikeDTO> rechargeEBike(String bikeId) {
        return Single.fromCallable(() -> {

            Optional<EBikeDTO> bike = application.rechargeBike(bikeId);
            if (bike.isEmpty())
                throw new IllegalArgumentException("Bike ID not found.");

            emitAllBikes();
            emitAvailableBikes();
            return bike.get();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public void refreshAllBikes() {
        application.getBikes().forEach(bike -> allBikesSubject.onNext(Collections.singletonList(bike)));
        emitAllBikes();
        emitAvailableBikes();
    }

    @Override
    public Observable<Collection<EBikeDTO>> observeAvailableBikes() {
        return availableBikesSubject.hide().observeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<RideDTO> startRide(String userId, String rideId, String bikeId) {
        Observable<RideDTO> observable = application.startRide(rideId, userId, bikeId)
                .doOnNext(rideDTO -> {
                    emitAllBikes();
                    emitAvailableBikes();
                })
                .doOnError(throwable -> {
                    System.out.println("Error starting ride: " + throwable.getMessage());
                })
                .doOnComplete(() -> {

                });
        emitAvailableBikes();
        emitAllUsers();
        return observable;


    }


    @Override
    public Completable endRide(String userId, String rideId, String bikeId) {
        return Completable.create(emitter -> {
            Optional<RideDTO> ride = application.getRide(rideId);
            if(ride.isEmpty())
                emitter.onError(new IllegalArgumentException("Ride not found."));
            else if(!ride.get().user().id().equals(userId))
                emitter.onError(new IllegalArgumentException("Ride does not belong to the user."));
            else if(!ride.get().ebike().id().equals(bikeId))
                emitter.onError(new IllegalArgumentException("Ride does not belong to the bike."));
            else {
                application.endRide(ride.get());
                emitter.onComplete();
            }
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    @Override
    public Observable<RideDTO> observeRide(String userId, String rideId) {
        Optional<RideDTO> ride = application.getRide(rideId);
        if (ride.isEmpty()) {
            System.out.println("Ride not found.");
            return Observable.error(new IllegalArgumentException("Ride not found."));
        }

        if (!ride.get().user().id().equals(userId)) {
            return Observable.error(new IllegalArgumentException("Ride does not belong to the user."));
        }
        return application.getRideSimulationObservable(rideId)
                .doOnNext(rideDTO -> {
                    emitAllBikes();
                    emitAvailableBikes();
                })
                .doOnError(throwable -> {
                    // Handle error
                })
                .doFinally(() -> {
                    System.out.println("Ride completed.");
                    emitAllBikes();
                    emitAvailableBikes();
                    emitAllUsers();
                });

    }

    @Override
    public Single<UserDTO> rechargeCredit(String userId, int amount) {
        return Single.fromCallable(() -> {
            Optional<UserDTO> user = application.rechargeCredit(userId, amount);
            if (user.isEmpty())
                throw new IllegalArgumentException("User ID not found.");
            emitAllUsers();
            return user.get();
        }).subscribeOn(io.reactivex.rxjava3.schedulers.Schedulers.io());
    }

    // ------------------- Helper Methods ------------------

    private void emitAllBikes() {
        allBikesSubject.onNext(application.getBikes());
    }

    private void emitAvailableBikes() {
        System.out.println("Emitting available bikes");
        Collection<EBikeDTO> availableDTOs = new ArrayList<>();
        application.getBikes().stream()
                .filter(bike -> bike.state().equals("AVAILABLE"))
                .forEach(availableDTOs::add);
        System.out.println("Available bikes: " + availableDTOs);
        availableBikesSubject.onNext(availableDTOs);
    }

    private void emitAllUsers() {
        allUsersSubject.onNext(application.getUsers());
    }

}
