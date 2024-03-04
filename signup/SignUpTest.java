package Transfortation.signup;

import javax.swing.*;

import Transfortation.login.LoginFrame;

import java.awt.*;

public class SignUpTest extends JFrame {
    JTextField idField, pwField, confirmPwField, nameField;
    JRadioButton maleRadioButton, femaleRadioButton;

    SignUpTest() {
        setTitle("SignUpPage");
        setSize(300, 500);
        setLocationRelativeTo(null);

     // 전체 판넬 설정
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 2, 5, 5)); // 6행 2열 그리드 레이아웃 사용

        String[] labels = {"ID", "PW", "PW 확인", "이름"};

        for (String label : labels) {
            JLabel lbl = new JLabel(label);
            mainPanel.add(lbl);

            // 텍스트 필드 설정
            JTextField textField = new JTextField(10);
            mainPanel.add(textField);

            if (label.equals("성별")) {
                // 성별 라디오 버튼 추가
                maleRadioButton = new JRadioButton("남자");
                femaleRadioButton = new JRadioButton("여자");
                ButtonGroup genderGroup = new ButtonGroup();
                genderGroup.add(maleRadioButton);
                genderGroup.add(femaleRadioButton);

                // 성별 라디오 버튼을 판넬에 추가
                JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                genderPanel.add(maleRadioButton);
                genderPanel.add(femaleRadioButton);
                mainPanel.add(genderPanel);
            }
        }

        JPanel panel2 = new JPanel(new FlowLayout());
        JButton registerButton = new JButton("등록");
        JButton backButton = new JButton("뒤로가기");

        // 등록 버튼 클릭 이벤트 설정
        registerButton.addActionListener(e -> {
            String id = idField.getText();
            String pw = pwField.getText();
            String confirmPw = confirmPwField.getText();
            String name = nameField.getText();
            String gender = maleRadioButton.isSelected() ? "남자" : "여자";

            if (pw.equals(confirmPw)) {
                JOptionPane.showMessageDialog(null, "등록 완료!");
                // 여기에 등록 완료 시 처리할 코드를 작성하면 됩니다.
            } else {
                JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 뒤로가기 버튼 클릭 이벤트 설정
        backButton.addActionListener(e -> {
            dispose(); // 현재 창 닫기
            new LoginFrame(); // LoginFrame 열기
        });

        panel2.add(registerButton);
        panel2.add(backButton);

        // 전체 판넬을 프레임에 추가
        add(mainPanel, BorderLayout.CENTER);
        add(panel2, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new SignUpTest();
    }
}

