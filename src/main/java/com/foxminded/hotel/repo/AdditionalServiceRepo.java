package com.foxminded.hotel.repo;

import com.foxminded.hotel.model.AdditionalService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdditionalServiceRepo
        extends JpaRepository<AdditionalService, Long> {
}
