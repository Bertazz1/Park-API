package com.Bertazz1.demo_park_api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ParkingSpace")
@Table(name = "parking_spaces")
@EntityListeners(AuditingEntityListener.class)
public class ParkingSpace implements Serializable {

    private static final long serialVersionUID = 1L;


     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

    @Column(name = "code", nullable = false, unique = true, length = 4)
     private String code;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusParking status;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @CreatedBy
    @Column(name = "created_by")
    private String createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private String updatedBy;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ParkingSpace that = (ParkingSpace) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public enum StatusParking {
        AVAILABLE,
        OCCUPIED
    }
}
