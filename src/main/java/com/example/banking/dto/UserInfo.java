package com.example.banking.dto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class UserInfo {

    @Id
    private String id;
    private String name;
    private String password;
    private String gender;
    private String reg_date;
    private String status;
}
