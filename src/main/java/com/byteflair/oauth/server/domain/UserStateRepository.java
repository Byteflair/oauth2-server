package com.byteflair.oauth.server.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user-states", path = "user-states")
public interface UserStateRepository extends PagingAndSortingRepository<UserState, Integer> {
    UserState findByDescription(@Param("description") String description);
}
