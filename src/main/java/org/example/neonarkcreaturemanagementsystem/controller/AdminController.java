package org.example.neonarkcreaturemanagementsystem.controller;

import org.example.neonarkcreaturemanagementsystem.dto.AdminUserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping("/users")
    public ResponseEntity<List<AdminUserResponse>> getAllUsers() {

        List<Object[]> results = entityManager.createNativeQuery("""
            SELECT u.full_name, u.email, u.phone, r.name
            FROM users u
            JOIN user_roles ur ON u.id = ur.user_id
            JOIN roles r ON ur.role_id = r.id
        """).getResultList();

        List<AdminUserResponse> response = results.stream()
                .map(row -> new AdminUserResponse(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}