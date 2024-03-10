package Transfortation.view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Transfortation.data.DataUtil;
import Transfortation.login.LoginFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ViewFrame extends JFrame {
	public static final String PLUS = "+";
	public static final String MINUS = "-";
	public static final String HIPEN = "-";

	private String userName;
	JLabel l1;
	DefaultTableModel model;
	JTable table;
	JScrollPane scrollPane;
	String[] columnNames = {"DATE", "D_STATION", "A_STATION", "WAY", "FARE", "DAY", "TOTAL"};
	int sum;
	JTextField t2;

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

		sum = calculateSum(userInfoArr); // 합계 계산

		model = new DefaultTableModel(userInfoArr, columnNames);
		table = new JTable(model);
		scrollPane = new JScrollPane(table);

		add(scrollPane, BorderLayout.CENTER);

		JPanel p2 = new JPanel(new FlowLayout());
		JButton b1 = new JButton("뒤로가기");
		JButton b2 = new JButton("수정하기");
		JLabel l2 = new JLabel("합계");
		t2 = new JTextField(sum + "엔");
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
		sum = calculateSum(userInfoArr); // 합계 갱신

		model = new DefaultTableModel(userInfoArr, columnNames);
		// new JTable로 갱신이 안되므로 setModel을 사용하여 갱신한다.
		table.setModel(model);
		t2 = new JTextField(sum + "엔");
	    t2.setEditable(false);
	    JPanel p2 = (JPanel) getContentPane().getComponent(2); // SOUTH 패널 가져오기
	    p2.getComponent(3).setVisible(false); // 기존 텍스트 필드 삭제
	    p2.remove(3);
	    p2.add(t2); // 새로운 텍스트 필드 추가
	    p2.revalidate(); // 레이아웃
	}

    private int calculateSum(String[][] userInfoArr) {
        int sum = 0;
        for(int i = 0; i < userInfoArr.length; i++) {
            sum += Integer.parseInt(userInfoArr[i][6]);
        }
        return sum;
    }

}


