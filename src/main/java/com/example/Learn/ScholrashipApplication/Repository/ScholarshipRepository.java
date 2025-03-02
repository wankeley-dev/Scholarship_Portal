package com.example.Learn.ScholrashipApplication.Repository;

import com.example.Learn.ScholrashipApplication.Entity.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScholarshipRepository extends JpaRepository<Scholarship, Long> {

        Optional<Scholarship> findByName(String name); // Find scholarship by name (optional feature)
}
