package com.example.banking.dao;

import com.example.banking.dto.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInfoDao extends JpaRepository<UserInfo, String> {
    UserInfo findByIdAndPassword(String id, String password);
}

