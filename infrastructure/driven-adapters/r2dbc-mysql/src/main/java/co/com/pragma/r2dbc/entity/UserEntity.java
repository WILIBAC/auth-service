package co.com.pragma.r2dbc.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class UserEntity implements Persistable<String> {
    @Id
    @Column("user_id")
    private String userId;
    @Column("name")
    private String name;
    @Column("last_name")
    private String lastName;
    @Column("birth_date")
    private LocalDate birthDate;
    @Column("address")
    private String address;
    @Column("phone_number")
    private String phoneNumber;
    @Column("email")
    private String email;
    @Column("base_salary")
    private BigDecimal baseSalary;
    @Column("identity_document")
    private String identityDocument;
    // Store role id as string to match users.role_id
    @Column("role_id")
    private String roleId;

    @Transient
    @Builder.Default
    private boolean isNew = true;

    @Override
    public String getId() {
        return userId;
    }

    @Override
    public boolean isNew() {
        return isNew || this.userId == null;
    }

    // helper to mark entity as persisted
    public UserEntity markPersisted() {
        this.isNew = false;
        return this;
    }
}

