package com.byteflair.oauth.server.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by rpr on 22/03/16.
 */
@RepositoryRestResource(collectionResourceRel = "user-details", path = "user-details")
public interface UserDetailsRepository extends PagingAndSortingRepository<UserDetail, Integer> {
}
