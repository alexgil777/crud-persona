package com.jespanag.crud_persona.controller;

import com.jespanag.crud_persona.entity.PersonaEntity;
import com.jespanag.crud_persona.repositorie.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/persona/")
public class PersonaController {

    @Autowired
    private PersonaRepository personaRepository;

    @PostMapping
    public ResponseEntity<String> createPersona(@RequestBody PersonaEntity personaEntity) {
        if (personaRepository.findByCedula(personaEntity.getCedula()).isPresent()) {
            return ResponseEntity.badRequest().body("La cedula que estas ingrsando ya esta registrada");
        }
        if (personaRepository.findByUsername(personaEntity.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("El usuario que estas ingresando ya esta registrado");
        }
        if (personaRepository.findByEmail(personaEntity.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("El email ya se encuentra registrado");
        }
        personaRepository.save(personaEntity);
        return ResponseEntity.ok("Usuario registrado correctamente");
    }


    @GetMapping("/{id}")
    public ResponseEntity<PersonaEntity> getPersonaId(@PathVariable UUID id) {
        Optional<PersonaEntity> personaEntity = personaRepository.findById(id);
        return personaEntity.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/buscarPersonaCedula/{cedula}")
    public ResponseEntity<PersonaEntity> getPersonaCedula(@PathVariable String cedula) {
        Optional<PersonaEntity> personaEntity = personaRepository.findByCedula(cedula);
        return personaEntity != null ? (ResponseEntity<PersonaEntity>) ResponseEntity.ok() : ResponseEntity.noContent().build();
    }

    @GetMapping("/buscarPersonaEmail/{email}")
    public ResponseEntity<PersonaEntity> getPersonaEmail(@PathVariable String email) {
        Optional<PersonaEntity> personaEntity = personaRepository.findByEmail(email);
        return personaEntity != null ? (ResponseEntity<PersonaEntity>) ResponseEntity.ok() : ResponseEntity.noContent().build();
    }

    @GetMapping("/allPersons")
    public List<PersonaEntity> getAllPersonas() {
        return personaRepository.findAll();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaEntity> updatePersonaId(@PathVariable UUID id, @RequestBody PersonaEntity personaEntity) {
        if (!personaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        personaEntity.setNombre(personaEntity.getNombre());
        personaEntity.setApellido(personaEntity.getApellido());
        personaEntity.setCedula(personaEntity.getCedula());
        personaEntity.setEmail(personaEntity.getEmail());
        personaEntity.setCiudad(personaEntity.getCiudad());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PersonaEntity> deletePersona(@PathVariable UUID id) {
        if (!personaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        personaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/eliminarPersonaCedula/{cedula}")
    public ResponseEntity<Void> deletePersonaCedula(@PathVariable String cedula) {
        Optional<PersonaEntity> personaEntity = personaRepository.findByCedula(cedula);
        if (personaEntity == null) {
            return ResponseEntity.notFound().build();
        }
        personaRepository.delete((PersonaEntity) personaRepository);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletePersonaUsername/{username}")
    public ResponseEntity<Void> deletePersonaUsername(@PathVariable String username) {
        Optional<PersonaEntity> personaEntity = personaRepository.findByUsername(username);
        if (personaEntity == null) {
            return ResponseEntity.notFound().build();
        }
        personaRepository.delete((PersonaEntity) personaRepository);
        return ResponseEntity.noContent().build();
    }
}
