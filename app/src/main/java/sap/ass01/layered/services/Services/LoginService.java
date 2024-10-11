package sap.ass01.layered.services.Services;

import sap.ass01.layered.services.dto.UserDTO;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface LoginService {
    /**
     * Registers a new user.
     * @param name Username.
     * @param isAdmin True if the user is an admin, false otherwise.
     * @return Completable indicating success or failure.
     */
    Completable signUp(String name, boolean isAdmin);

    /**
     * Authenticates a user by name.
     * @param name Username.
     * @return Single emitting UserDTO on success or an error.
     */
    Single<UserDTO> logIn(String name);
}