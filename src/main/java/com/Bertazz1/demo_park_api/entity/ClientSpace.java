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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity(name = "ClientSpace")
@Table(name = "clients_has_spaces")
public class ClientSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receip_number", nullable = false, unique = true, length = 15)
    private String receipt;
    @Column(name = "license_plate", nullable = false, length = 8)
    private String licensePlate;
    @Column(name = "model", nullable = false, length = 45)
    private String model;
    @Column(name = "brand", nullable = false, length = 45)
    private String brand;
    @Column(name = "color", nullable = false, length = 20)
    private String color;

    @Column(name = "entry_date", nullable = false)
    private LocalDateTime entryTime;
    @Column(name = "exit_date")
    private LocalDateTime exitTime;

    @Column(name = "price", columnDefinition = "decimal(10,2)")
    private BigDecimal price;
    @Column(name = "discount", columnDefinition = "decimal(10,2)")
    private BigDecimal discount;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "parking_space_id", nullable = false)
    private ParkingSpace parkingSpace;

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
        ClientSpace that = (ClientSpace) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
