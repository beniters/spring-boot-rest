package com.ninjaone.backendinterviewproject.repository;

import com.ninjaone.backendinterviewproject.domain.Sample;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SampleRepository extends CrudRepository<Sample, String> {}
