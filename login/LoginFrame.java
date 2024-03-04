package Transfortation.login;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Transfortation.signup.SignUpFrame;
import Transfortation.data.DataUtil;
import Transfortation.entity.UserInfoEntity;
import Transfortation.main.MainFrame;

public class LoginFrame extends JFrame {
	static public String userId; // 로그인 세션
	JButton b1 = new JButton("로그인");
	JButton b2 = new JButton("회원가입");
	JTextField t1 = new JTextField(10);
	JPasswordField t2 = new JPasswordField(10);
	boolean loginSuccessFlag = false;
	String userName = ""; // 유저 이름을 저장할 변수


	public LoginFrame() {
		setTitle("교통비정산 시스템 - 로그인");
		setSize(300,200);
		setLocationRelativeTo(null);

		setLayout(new GridLayout(2,1));

		// 1번판낼
		JPanel panel1 = new JPanel(new GridLayout(2, 2));
		JPanel p1 = new JPanel();
		JLabel l1 = new JLabel("ID");
		p1.add(l1);
		p1.add(t1);
		JPanel p2 = new JPanel();
		JLabel l2 = new JLabel("PW");
		p2.add(l2);
		p2.add(t2);
		panel1.add(p1);
		panel1.add(p2);

		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				checkLogin();
			}
		});

		JPanel panel2 = new JPanel();

		panel2.add(b1);
		panel2.add(b2);

		b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new SignUpFrame();
			}
		});

		add(panel1);
		add(panel2);

		setVisible(true);
	}

	public static void main(String[] args) {
		new LoginFrame();
	}
	 public void checkLogin() {
	        DataUtil dataUtil = new DataUtil();
	        List<UserInfoEntity> userInfoList = dataUtil.loadAllData();

	        String pw = String.valueOf(t2.getPassword());

	        for(UserInfoEntity userInfo: userInfoList) {
	            if(userInfo.getId().equals(t1.getText()) && userInfo.getPw().equals(pw)) {
	                userId = userInfo.getId();
	            	loginSuccessFlag = true;
	                userName = userInfo.getName(); // 유저 이름 저장
	                break; // 찾았으므로 더 이상 반복할 필요 없음
	            }
	        }

	        if(loginSuccessFlag) {
	            JOptionPane.showMessageDialog(null, "로그인 완료");
	            // 로그인 성공 시 MainFrame으로 화면 전환
	            setVisible(false); // 현재 창 숨기기
	            new MainFrame(userName); // MainFrame 생성 및 유저 이름 전달
	        } else {
	            JOptionPane.showMessageDialog(null, "아이디 패스워드가 존재하지 않습니다.");
	        }
	    }

}
