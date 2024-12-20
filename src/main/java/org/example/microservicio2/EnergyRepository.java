package org.example.microservicio2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnergyRepository extends JpaRepository<Energy, UUID> {}
