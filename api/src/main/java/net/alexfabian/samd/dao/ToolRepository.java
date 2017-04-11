package net.alexfabian.samd.dao;

import net.alexfabian.samd.model.Tool;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

/**
 * Created by alexfabian on 6/13/16.
 */

@CrossOrigin
@RepositoryRestResource(collectionResourceRel = "tools", path = "tools")
public interface ToolRepository extends MongoRepository<Tool, String> {

}
