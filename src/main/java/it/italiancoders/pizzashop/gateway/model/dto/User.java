package it.italiancoders.pizzashop.gateway.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder(builderMethodName = "newBuilder")
public class User {

    @NotNull
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotNull
    private String address;

    @NotNull
    private List<UserRole> roles;
}
