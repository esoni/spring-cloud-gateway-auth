package it.italiancoders.pizzashop.gateway.repository;


import it.italiancoders.pizzashop.gateway.model.security.SessionUserDetails;
import org.springframework.data.repository.CrudRepository;

public interface SessionRepository extends CrudRepository<SessionUserDetails, String> {

}
