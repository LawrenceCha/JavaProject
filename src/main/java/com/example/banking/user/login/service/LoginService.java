package com.example.banking.user.login.service;

import com.example.banking.dao.UserInfoDao;
import com.example.banking.dto.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    // Lombok을 통한 간결한 의존성 주입 방식
    // @RequiredArgsConstructor과 함께 사용함
    private final UserInfoDao userInfodao;

    public UserInfo login(String id, String password) {

        UserInfo userInfo = userInfodao.findByIdAndPassword(id, password);

//        if (userInfo != null) {
//            return true;
//        } else  {
//            return false;
//        }
        return userInfo;
    }

}
