package org.example.neonarkcreaturemanagementsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.neonarkcreaturemanagementsystem.dto.AdminUserResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<AdminUserResponse> getAllUsers() {
        List<Object[]> results = entityManager.createNativeQuery("""
            SELECT u.full_name, u.email, u.phone, r.name
            FROM users u
            JOIN user_roles ur ON u.id = ur.user_id
            JOIN roles r ON ur.role_id = r.id
            ORDER BY u.full_name
        """).getResultList();

        return results.stream()
                .map(row -> new AdminUserResponse(
                        (String) row[0],
                        (String) row[1],
                        (String) row[2],
                        (String) row[3]
                ))
                .collect(Collectors.toList());
    }
}