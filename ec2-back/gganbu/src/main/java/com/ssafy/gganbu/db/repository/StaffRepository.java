package com.ssafy.gganbu.db.repository;

import com.ssafy.gganbu.db.entity.Staffs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StaffRepository extends JpaRepository<Staffs, Long> {
    public Optional<Staffs> findByIdAndPassword(String id, String password);
}
