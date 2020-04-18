package it.italiancoders.pizzashop.gateway.authentication;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SecurityException {
    private String method;
    private String urlRegex;

    public SecurityException(String method, String urlRegex) {
        this.method = method;
        this.urlRegex = urlRegex;
    }
}
