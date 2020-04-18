package it.italiancoders.pizzashop.gateway.authentication.provider.impl;

import it.italiancoders.pizzashop.gateway.authentication.SecurityProperty;
import it.italiancoders.pizzashop.gateway.authentication.provider.AuthenticationProvider;
import it.italiancoders.pizzashop.gateway.model.security.SessionUserDetails;
import it.italiancoders.pizzashop.gateway.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
public class RedisAuthenticationProviderImpl implements AuthenticationProvider {

    private SessionRepository sessionRepository;

    private SecurityProperty securityProperty;

    @Autowired
    public RedisAuthenticationProviderImpl( SessionRepository sessionRepository,
                                            SecurityProperty securityProperty) {
        this.sessionRepository = sessionRepository;
        this.securityProperty = securityProperty;
    }

    @Override
    public Optional<SessionUserDetails> getSession(String token) {
        if (StringUtils.isEmpty(token)) {
            return Optional.empty();
        }
        return sessionRepository.findById(token);
    }

    @Override
    public Mono<Void> refreshSession(SessionUserDetails session) {
        return Mono.fromRunnable(() -> {
            session.setTimeout(securityProperty.getMaxInactiveInterval());
            sessionRepository.save(session);
        });
    }

    @Override
    public boolean isMandatoryAuthentication(String method, String path) {
        return securityProperty.getExceptions().stream()
                .noneMatch((v) -> path.matches(v.getUrlRegex()) && method.equals(v.getMethod()));
    }
}
