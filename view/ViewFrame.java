package Transfortation.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Transfortation.data.DataUtil;
import Transfortation.login.LoginFrame;
import Transfortation.main.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class ViewFrame extends JFrame {
	public static final String PLUS = "+";
	public static final String MINUS = "-";
	public static final String HIPEN = "-";

	private String userName;
	JLabel l1;
	DefaultTableModel model;
	JTable table;
	JScrollPane scrollPane;
	String[] columnNames = {"DATE", "D_STATION", "A_STATION", "PARE"};

    public ViewFrame(String userName) {
        this.userName = userName;
        setTitle(userName + "님의 교통비정산 리스트");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JButton a1 = new JButton("<");
        JButton a2 = new JButton(">");

        a1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reloadData(MINUS);
			}
		});

		a2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				reloadData(PLUS);
			}
		});



		JPanel p1 = new JPanel();
		// 현재 날짜를 가져오기
		LocalDate currentDate = LocalDate.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

		String formattedDate = currentDate.format(formatter);

		l1 = new JLabel(formattedDate);
		p1.add(a1);
		p1.add(l1);
		p1.add(a2);
		add(p1, BorderLayout.NORTH);

		DataUtil du = new DataUtil();
		String[][] userInfoArr = du.loadUserTransInfo(formattedDate, LoginFrame.userId);

		int sum = 0;
		for(int i = 0; i<userInfoArr.length; i++) {
			sum+=Integer.parseInt(userInfoArr[i][3]);
		}

		model = new DefaultTableModel(userInfoArr, columnNames);
		table = new JTable(model);
		scrollPane = new JScrollPane(table);

		add(scrollPane, BorderLayout.CENTER);

		JPanel p2 = new JPanel(new FlowLayout());
		JButton b1 = new JButton("뒤로가기");
		JButton b2 = new JButton("수정하기");
		JLabel l2 = new JLabel("합계");
		JTextField t2 = new JTextField(sum + "엔");
		t2.setEditable(false);
		p2.add(b1);
		p2.add(b2);
		p2.add(l2);
		p2.add(t2);

		add(p2, BorderLayout.SOUTH);

		setVisible(true);
	}
    public void reloadData(String direction) {
		String now = l1.getText();

		String year = now.substring(0,4);
		String month = now.substring(5,7);

		// 뒤로가기를 눌렀을때는 현재 표시되는 날짜에서 -1개월을 한다.
		if(direction.equals(MINUS)) {

			if(month.equals("01")) {
				year = String.valueOf(Integer.parseInt(year) - 1);
				month = "12";
			} else {
				month = String.valueOf(Integer.parseInt(month) - 1);
			}
			// 다음달이 눌렸을때
		} else {
			if(month.equals("12")) {
				year = String.valueOf(Integer.parseInt(year) + 1);
				month = "01";
			} else {
				month = String.valueOf(Integer.parseInt(month) + 1);
			}
		}

		month = String.format("%02d", Integer.parseInt(month));

		l1.setText(year + HIPEN + month);

		DataUtil du = new DataUtil();
		String[][] userInfoArr = du.loadUserTransInfo(l1.getText(), LoginFrame.userId);

		model = new DefaultTableModel(userInfoArr, columnNames);
		// new JTable로 갱신이 안되므로 setModel을 사용하여 갱신한다.
		table.setModel(model);
	}

    public static void main(String[] args) {
        new ViewFrame("John");
    }

}


//private void loadUserData(YearMonth currentYearMonth) {
//// 해당 년월의 경로를 가져옵니다.
//String yearMonth = currentYearMonth.format(DateTimeFormatter.ofPattern("yyyy-MM"));
//String filePath = MainFrame.DEFAULT_DIRECTORY_PATH + yearMonth + "\\transfortationInfo_" + LoginFrame.userId + ".txt";
//
//// 해당 경로가 존재하는지 확인합니다.
//Path path = Paths.get(filePath);
//if (!Files.exists(path)) {
//   JOptionPane.showMessageDialog(this, "조회된 데이터가 없습니다.", "알림", JOptionPane.INFORMATION_MESSAGE);
//   return;
//}
//
//// 파일에서 데이터를 로드하여 테이블에 표시합니다.
//DataUtil dataUtil = new DataUtil();
//String[][] userInfoArr = dataUtil.loadUserTransInfo(filePath);
//model.setDataVector(userInfoArr, new String[]{"DATE", "D_STATION", "A_STATION", "WAY", "FRICE", "TIME", "FARE"});
//}

//DefaultTableModel model = new DefaultTableModel(userInfoArr, columnNames);
//
//JTable table = new JTable(model);
//
//JScrollPane scrollPane = new JScrollPane(table);
//
//add(scrollPane, BorderLayout.CENTER);
//
//JPanel p2 = new JPanel(new FlowLayout());
//JButton b2 = new JButton("수정하기");
//JButton b1 = new JButton("뒤로가기");
//JLabel l2 = new JLabel("합계");
//JTextField t2 = new JTextField(sum + "엔"); // 수정: 합계를 sum으로 출력
//t2.setEditable(false);
//p2.add(b2);
//p2.add(b1);
//p2.add(l2);
//p2.add(t2);
//
//add(p2, BorderLayout.SOUTH);
//
//setVisible(true);
//}

//private void updateMonthLabel() {
//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
//monthLabel.setText(currentYearMonth.format(formatter)); // 현재 날짜를 레이블에 표시합니다.
//}
//
//private String getCurrentDate() {
//Calendar calendar = Calendar.getInstance();
//int year = calendar.get(Calendar.YEAR);
//int month = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
//int day = calendar.get(Calendar.DAY_OF_MONTH);
//return year + "-" + month + "-" + day;
//}
//
//private String getCurrentYearMonth() {
//Calendar calendar = Calendar.getInstance();
//int year = calendar.get(Calendar.YEAR);
//int month = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
//return year + "-" + month;
//}


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
