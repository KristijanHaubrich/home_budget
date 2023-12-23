package com.example.home_budget.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private String date;
    private String description;
    private Double amount;
}
