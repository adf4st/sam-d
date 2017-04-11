package net.alexfabian.swarmview.dao;

import net.alexfabian.swarmview.model.Setting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by alexfabian on 6/13/16.
 */

@RepositoryRestResource(collectionResourceRel = "settings", path = "settings")
public interface SettingRepository extends MongoRepository<Setting, String> {
    public Setting findByActive(@Param("active") Boolean active);
}
