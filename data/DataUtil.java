package Transfortation.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Transfortation.entity.UserInfoEntity;

public class DataUtil {
	String path = "C:\\Users\\차승석\\Desktop\\Coding\\study11\\swing\\src\\Transfortation\\data\\userInfo.txt";


	public List<UserInfoEntity> loadAllData() {

		List<UserInfoEntity> userInfoList = new ArrayList<UserInfoEntity>();
		UserInfoEntity userInfo = new UserInfoEntity();
		try (
			BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            // 한 줄씩 읽어오기
            boolean existLine = true;

            while (existLine) {
            	line = reader.readLine();
            	if(line != null) {
            		userInfo = new UserInfoEntity();
            		String[] splitArr = line.split(",");
            		userInfo.setId(splitArr[0]);
            		userInfo.setPw(splitArr[1]);
            		userInfo.setName(splitArr[2]);
            		userInfo.setGender(splitArr[3]);
            		userInfoList.add(userInfo);
            	} else {
            		existLine = false;
            	}
            }
//            while ((line = reader.readLine()) != null) {
//            	userInfo = new UserInfoEntity();
//            	String[] splitArr =line.split(",");
//        		userInfo.setId(splitArr[0]);
//        		userInfo.setPw(splitArr[1]);
//        		userInfo.setName(splitArr[2]);
//        		userInfo.setGender(splitArr[3]);
//        		userInfoList.add(userInfo);
//            }

        } catch (IOException e) {
            System.out.println("파일을 읽어오는 동안 오류가 발생했습니다.");
            e.printStackTrace();
        }

		return userInfoList;
	}
}
