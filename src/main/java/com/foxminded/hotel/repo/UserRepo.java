package com.foxminded.hotel.repo;

import com.foxminded.hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);
}
