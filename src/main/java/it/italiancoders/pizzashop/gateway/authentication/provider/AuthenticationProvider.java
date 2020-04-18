package it.italiancoders.pizzashop.gateway.authentication.provider;

import it.italiancoders.pizzashop.gateway.model.security.SessionUserDetails;
import reactor.core.publisher.Mono;

import java.util.Optional;

public interface AuthenticationProvider {
    Optional<SessionUserDetails> getSession(String token);
    Mono<Void> refreshSession(SessionUserDetails session);
    boolean isMandatoryAuthentication(String method, String path);
}
