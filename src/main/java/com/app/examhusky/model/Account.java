package com.app.examhusky.model;


import com.app.examhusky.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
    @SequenceGenerator(name = "account_generator", sequenceName = "account_seq")
    private Integer id;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String username;

    @NotBlank
    @Size(max = 60)
    private String firstName;

    @NotBlank
    @Size(max = 20)
    private String lastName;

    @Transient
    private String fullName;

    @NotNull
    @NotEmpty
    @Size(max = 100)
    private String email;

    @Size(max = 128)
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!*()]).{8,}$",
            message = "Password must be 8 characters long and combination of " +
                    "uppercase letters, lowercase letters, numbers, special characters.")
    private String password;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    private Boolean disabled;

    private Boolean deleted;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}