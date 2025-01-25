package com.jespanag.crud_persona.repositorie;

import com.jespanag.crud_persona.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PersonaRepository extends JpaRepository<PersonaEntity, UUID> {
    Optional<PersonaEntity> findByCedula(String cedula);
    Optional<PersonaEntity> findByUsername(String username);
    Optional<PersonaEntity> findByEmail(String email);
}
