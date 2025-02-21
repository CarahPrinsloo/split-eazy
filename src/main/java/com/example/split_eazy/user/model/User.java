package com.example.split_eazy.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {
    @Id
    private Integer id;

    private UUID uuid;

    private String name;

    @Column("email_address")
    private String emailAddress;

    private String password;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @CreatedDate
    @Column("updated_at")
    private Instant updatedAt;
}
