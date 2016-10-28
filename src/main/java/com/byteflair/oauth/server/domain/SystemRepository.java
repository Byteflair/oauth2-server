package com.byteflair.oauth.server.domain;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "systems", path = "systems")
public interface SystemRepository extends PagingAndSortingRepository<System, Integer> {
    List<System> findByName(@Param("name") String name);
}
