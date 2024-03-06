package Transfortation.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Transfortation.data.DataUtil;
import Transfortation.login.LoginFrame;
import Transfortation.main.MainFrame;

import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ViewFrame extends JFrame {
    private String userName;
    private YearMonth currentYearMonth;
    private DefaultTableModel model;
    private JTextField totalTextField;

    public ViewFrame(String userName) {
        this.userName = userName;
        setTitle(userName + "님의 교통비정산 리스트");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JButton a1 = new JButton("<");
        JButton a2 = new JButton(">");

        JPanel p1 = new JPanel();
        // 현재 날짜를 가져오기
        LocalDate currentDate = LocalDate.now();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

        String formattedDate = currentDate.format(formatter);

        JLabel l1 = new JLabel(formattedDate);
        p1.add(a1);
        p1.add(l1);
        p1.add(a2);
        add(p1, BorderLayout.NORTH);

        String[] columnNames = {"DATE", "D_STATION", "A_STATION", "WAY", "FRICE", "TIME", "FARE"};
        String yearMonth = getCurrentYearMonth(); // 현재 년도와 월을 가져옵니다.
        String fileName = MainFrame.DEFAULT_DIRECTORY_PATH + yearMonth + "\\transfortationInfo_" + LoginFrame.userId + ".txt"; // 파일 경로를 정의합니다.

        DataUtil dataUtil = new DataUtil();
        String[][] userInfoArr = dataUtil.loadUserTransInfo(fileName); // 파일 경로를 인자로 전달합니다.

        int sum = 0;
        for(int i = 0; i < userInfoArr.length; i++) { // 오류 수정: 조건을 userInfoArr.length로 변경
            sum += Integer.parseInt(userInfoArr[i][6]); // 오류 수정: 요금(fare)의 인덱스는 6입니다.
        }


        DefaultTableModel model = new DefaultTableModel(userInfoArr, columnNames);

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane, BorderLayout.CENTER);

        JPanel p2 = new JPanel(new FlowLayout());
        JButton b2 = new JButton("수정하기");
        JButton b1 = new JButton("뒤로가기");
        JLabel l2 = new JLabel("합계");
        JTextField t2 = new JTextField(sum + "엔"); // 수정: 합계를 sum으로 출력
        t2.setEditable(false);
        p2.add(b2);
        p2.add(b1);
        p2.add(l2);
        p2.add(t2);

        add(p2, BorderLayout.SOUTH);

        setVisible(true);
    }


    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    private String getCurrentYearMonth() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
        return year + "-" + month;
    }

    public static void main(String[] args) {
        new ViewFrame("John");
    }
}

// 틀린 코드
//String[] columnNames = {"DATE", "D_STATION", "A_STATION", "WAY", "FRICE", "TIME", "FARE"};
//String yearMonth = getCurrentYearMonth(); // 현재 년도와 월을 가져옵니다.
//String fileName = MainFrame.DEFAULT_DIRECTORY_PATH + yearMonth + "\\transfortationInfo_" + LoginFrame.userId + ".txt"; // 파일 경로를 정의합니다.
//
//DataUtil dataUtil = new DataUtil();
//String[][] userInfoArr = dataUtil.loadUserTransInfo(fileName); // 파일 경로를 인자로 전달합니다.
//
//int sum = 0;
//for(int i = 0; i < userInfoArr.length - 1; i++) {
//	sum += Integer.parseInt(userInfoArr[i][6]);
//}
