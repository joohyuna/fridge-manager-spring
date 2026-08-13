package com.lineacademy.fridgemanagerspring.repository;

import com.lineacademy.fridgemanagerspring.domain.fridge.Fridge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FridgeRepository extends JpaRepository<Fridge, Long> {
}
