package net.alexfabian.swarmview.dao;

import net.alexfabian.swarmview.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by alexfabian on 2/19/17.
 */

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RoleRepository extends MongoRepository<Role, String>{
    Role findByName(@Param("name") String name);
}
