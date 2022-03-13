package com.letscode.starwar.repository;

import com.letscode.starwar.entity.Rebelde;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RebeldeRepository extends JpaRepository<Rebelde, Long> {
    //Rebelde findByNome(Long id);
}
