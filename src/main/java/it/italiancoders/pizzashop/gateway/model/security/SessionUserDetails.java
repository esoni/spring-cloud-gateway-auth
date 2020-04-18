package it.italiancoders.pizzashop.gateway.model.security;

import it.italiancoders.pizzashop.gateway.model.dto.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

import javax.persistence.Id;

@RedisHash(value = "session")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SessionUserDetails {
    @Id
    String id;
    User currentUser;
    @TimeToLive
    Long timeout;
}
