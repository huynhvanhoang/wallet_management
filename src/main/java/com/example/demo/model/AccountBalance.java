package com.example.demo.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "balance_history")
public class AccountBalance implements Serializable {
    private static final long serialVersionUID = -297553281792804396L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private double current_balance;

    private double amount;

    private long created_at;

    public void setCreatedAt(Date createdAt){
        this.created_at = createdAt.getTime();
    }
}
