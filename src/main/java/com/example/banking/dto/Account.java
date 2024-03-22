package com.example.banking.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "account1")
@DynamicInsert
@DynamicUpdate

public class Account {
    @Id
    private String account;

    private int no;

    private String branchCode;

    private long amount;

    private String userId;
}
