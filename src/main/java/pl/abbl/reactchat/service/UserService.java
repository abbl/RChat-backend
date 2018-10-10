package pl.abbl.reactchat.service;

import pl.abbl.reactchat.callbacks.AbstractCallback;

import java.util.Map;

public interface UserService {
    AbstractCallback register(Map<String, String> userCredentials);
}
