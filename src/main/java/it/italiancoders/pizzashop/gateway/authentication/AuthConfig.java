package it.italiancoders.pizzashop.gateway.authentication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.italiancoders.pizzashop.gateway.authentication.provider.AuthenticationProvider;
import it.italiancoders.pizzashop.gateway.model.security.SessionUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

@Configuration
@Slf4j
public class AuthConfig {
    private SecurityProperty securityProperty;
    private AuthenticationProvider authenticationProvider;
    private ObjectMapper objectMapper;

    @Autowired
    public AuthConfig(SecurityProperty securityProperty,
                      AuthenticationProvider authenticationProvider,
                      ObjectMapper objectMapper) {
        this.securityProperty = securityProperty;
        this.authenticationProvider = authenticationProvider;
        this.objectMapper = objectMapper;
    }

    @Bean
    @Order(0)
    public GlobalFilter authenticationFilter() {
        return (exchange, chain) -> {
            //PATH
            String path = exchange.getRequest().getURI().getPath();
            String method = exchange.getRequest().getMethod().name();
            boolean isMandatoryAuthentication = authenticationProvider.isMandatoryAuthentication(method, path);

            if (isMandatoryAuthentication) {
                String token = exchange.getRequest().getHeaders().get(securityProperty.getTokenHeaderName()).get(0);
                if (StringUtils.isEmpty(token)) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                // check security
                SessionUserDetails sessionUserDetails = authenticationProvider.getSession(token).orElse(null);
                if (sessionUserDetails == null) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }
                // async refresh session in order to not block the chain
                authenticationProvider.refreshSession(sessionUserDetails).subscribe();
                String principalJson = null;
                try {
                    principalJson = objectMapper.writeValueAsString(sessionUserDetails);
                } catch (JsonProcessingException e) {
                    log.error("Unable to stringify session [{}]", sessionUserDetails);
                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    return exchange.getResponse().setComplete();
                }
                ServerHttpRequest newRequest =  exchange.getRequest()
                        .mutate()
                        .header(securityProperty.getPrincipalHeaderName(), principalJson)
                        .build();

                exchange.mutate().request(newRequest).build();

            }
            return chain.filter(exchange);
        };
    }

}
