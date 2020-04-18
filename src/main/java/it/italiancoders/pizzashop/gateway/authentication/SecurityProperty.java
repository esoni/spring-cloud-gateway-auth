package it.italiancoders.pizzashop.gateway.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix="security-rules")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SecurityProperty {
    private Long maxInactiveInterval;
    private List<SecurityException> exceptions = new ArrayList<>();
    private String tokenHeaderName;
    private String principalHeaderName;

}
