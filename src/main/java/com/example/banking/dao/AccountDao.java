package com.example.banking.dao;

import com.example.banking.dto.AccountInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountInfoDao extends JpaRepository<AccountInfo, String> {
}
