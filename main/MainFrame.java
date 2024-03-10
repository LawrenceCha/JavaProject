package Transfortation.main;

import Transfortation.data.DataUtil;
import Transfortation.login.LoginFrame;
import Transfortation.view.ViewFrame;
import Transfortation.main.MainFrame;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainFrame extends JFrame {

    private String userName;
    private JTextField startField;
    private JTextField destinationField;
    private JRadioButton r1;
    private JRadioButton r2;
    private JTextField costField;
    private JTextField billingPeriodField;
    private JTextField totalAmountField;
    private JTextField yearMonthField;

    public MainFrame(String userName) {
    	this.userName = userName;

        setTitle(userName + "님의 교통비정산 입력창");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panel 1: Welcome Message
        JPanel panel1 = new JPanel();
        JLabel welcomeLabel = new JLabel("환영합니다, " + userName + "님");
        panel1.add(welcomeLabel);
        mainPanel.add(panel1, BorderLayout.NORTH);

        // Panel 2: Input Fields
        JPanel panel2 = new JPanel(new BorderLayout());
        panel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));

        // 해당 년월에 따라 일수가 바뀌게끔 수정 필요
        JLabel dateLabel = new JLabel("입력할 날짜");
        JPanel dateInputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        yearMonthField = new JTextField(10);
        dateInputPanel.add(yearMonthField);

        JLabel startLabel = new JLabel("출발지");
        startField = new JTextField();

        JLabel destinationLabel = new JLabel("도착지");
        destinationField = new JTextField();

        JPanel billing = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); // FlowLayout을 사용하고, 여백을 조절하여 정렬

        JLabel billingLabel = new JLabel("청구금액");
        JPanel radio = new JPanel(new FlowLayout(FlowLayout.LEFT)); // radio 패널도 FlowLayout으로 설정

        r1 = new JRadioButton("왕복");
        r2 = new JRadioButton("편도");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(r1);
        buttonGroup.add(r2);
        radio.add(r1);
        radio.add(r2);

        costField = new JTextField();

        billing.add(billingLabel);
        billing.add(radio);
        billing.add(costField);



        JLabel billingPeriodLabel = new JLabel("청구일수");
        billingPeriodField = new JTextField();
        billingPeriodField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateTotalAmount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateTotalAmount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateTotalAmount();
            }
        });

        JLabel totalAmountLabel = new JLabel("총 액");
        totalAmountField = new JTextField();
        totalAmountField.setEditable(false);


        inputPanel.add(dateLabel);
        inputPanel.add(dateInputPanel);
        inputPanel.add(startLabel);
        inputPanel.add(startField);
        inputPanel.add(destinationLabel);
        inputPanel.add(destinationField);
        inputPanel.add(billing);
        inputPanel.add(costField);
        inputPanel.add(billingPeriodLabel);
        inputPanel.add(billingPeriodField);
        inputPanel.add(totalAmountLabel);
        inputPanel.add(totalAmountField);

        panel2.add(inputPanel, BorderLayout.CENTER);

        // Panel 3: Buttons
        JPanel panel3 = new JPanel();
        JButton viewButton = new JButton("조회하기");
        JButton saveButton = new JButton("저장하기");
        JButton backButton = new JButton("돌아가기");
        panel3.add(viewButton);
        panel3.add(saveButton);
        panel3.add(backButton);

        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ViewFrame(LoginFrame.userId);
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement saving functionality
                inputSaving();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                new LoginFrame();
            }
        });

        panel2.add(panel3, BorderLayout.SOUTH);
        mainPanel.add(panel2, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }
    private void updateTotalAmount() {
        try {
            int cost = Integer.parseInt(costField.getText());
            int billingPeriod = Integer.parseInt(billingPeriodField.getText());
            int totalAmount = cost * billingPeriod;
            totalAmountField.setText(String.valueOf(totalAmount));
        } catch (NumberFormatException ex) {
            totalAmountField.setText("");
        }
    }

    public String[][] inputSaving() {
    	String yearMonth = yearMonthField.getText(); // 입력 필드에서 날짜 가져오기
    	yearMonth = yearMonth.substring(0, 7);
        String directoryPath = "src\\Transfortation\\data\\" + yearMonth; // 기본 경로와 직접 입력한 날짜를 조합하여 경로 생성

        File directory = new File(directoryPath);
        if (!directory.exists()) {
            directory.mkdirs(); // 폴더가 없으면 생성
        }

        String fileName = directoryPath + "\\transfortationInfo_" + LoginFrame.userId + ".txt";

        File file = new File(fileName);
        boolean fileExists = file.exists();

        // 파일이 이미 존재하는 경우
        if (fileExists) {
            int option = JOptionPane.showConfirmDialog(this,
                    "이미 아래의 정보가 등록되어 있습니다:\n" +
                            yearMonth + "," + startField.getText() + "," + destinationField.getText() + "," +
                            (r1.isSelected() ? "왕복" : "편도") + "," + costField.getText() + "," + billingPeriodField.getText() + "," +
                            totalAmountField.getText() + "\n" +
                            "추가 하시겠습니까?",
                    "알림",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (option == JOptionPane.YES_OPTION) {
                // 추가
                appendToFile(fileName);
            } else if (option == JOptionPane.NO_OPTION) {
                // 덮어쓰기
                writeToFile(fileName);
            }
            // 취소일 경우는 아무 작업도 하지 않음
        } else {
            // 파일이 존재하지 않는 경우 새로운 파일에 쓰기
            writeToFile(fileName);
        }

        // 파일에서 데이터 읽어오기
        DataUtil dataUtil = new DataUtil();
        return dataUtil.loadUserTransInfo(yearMonth, LoginFrame.userId); // loadUserTransInfo 메서드 호출에 매개변수 추가
    }


    // 파일에 내용 추가
    private void appendToFile(String fileName) {
        try (FileWriter fileWriter = new FileWriter(fileName, true);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
             PrintWriter out = new PrintWriter(bufferedWriter)) {
            // totalAmountField의 값을 계산하여 저장
            int cost = Integer.parseInt(costField.getText());
            int billingPeriod = Integer.parseInt(billingPeriodField.getText());
            int totalAmount = cost * billingPeriod;
            out.println(yearMonthField.getText() + "," + startField.getText() + "," + destinationField.getText() + "," +
                    (r1.isSelected() ? "왕복" : "편도") + "," + costField.getText() + "," + billingPeriodField.getText() + "," +
                    totalAmount);
            JOptionPane.showMessageDialog(this, "추가되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "추가에 실패했습니다.");
        }
    }

    // 파일에 내용 덮어쓰기
    private void writeToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            // totalAmountField의 값을 계산하여 저장
            int cost = Integer.parseInt(costField.getText());
            int billingPeriod = Integer.parseInt(billingPeriodField.getText());
            int totalAmount = cost * billingPeriod;
            writer.println(yearMonthField.getText() + "," + startField.getText() + "," + destinationField.getText() + "," +
                    (r1.isSelected() ? "왕복" : "편도") + "," + costField.getText() + "," + billingPeriodField.getText() + "," +
                    totalAmount);
            JOptionPane.showMessageDialog(this, "저장이 완료되었습니다.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "저장에 실패했습니다.");
        }
    }
}
