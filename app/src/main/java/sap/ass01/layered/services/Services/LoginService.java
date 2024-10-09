package sap.ass01.layered.services.Services;

import sap.ass01.layered.services.dto.UserDTO;

import java.util.concurrent.CompletableFuture;

public interface LoginService {
    CompletableFuture<Void> signIn(String name, Boolean admin);
    CompletableFuture<UserDTO> logIn(String name);
}
