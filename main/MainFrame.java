package Transfortation.main;

import Transfortation.login.LoginFrame;
import Transfortation.view.ViewFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

public class MainFrame extends JFrame {

    private String userName;
    private JTextField startField;
    private JTextField destinationField;
    private JRadioButton r1;
    private JRadioButton r2;
    private JTextField costField;
    private JTextField billingPeriodField;
    private JTextField totalAmountField;

    public MainFrame(String userName) {
        this.userName = userName;

        setTitle("교통비정산 시스템 - Main");
        setSize(400, 300);
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

        JLabel dateLabel = new JLabel("오늘의 날짜");
        JTextField dateField = new JTextField();
        dateField.setEditable(false);
        dateField.setText(getCurrentDate());

        JLabel startLabel = new JLabel("출발지");
        startField = new JTextField();

        JLabel destinationLabel = new JLabel("도착지");
        destinationField = new JTextField();

        JPanel radio = new JPanel((LayoutManager) new FlowLayout(FlowLayout.LEFT));
        r1 = new JRadioButton("왕복");
        r2 = new JRadioButton("편도");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(r1);
        buttonGroup.add(r2);
        radio.add(r1);
        radio.add(r2);
        costField = new JTextField();

        JLabel billingPeriodLabel = new JLabel("청구일수");
        billingPeriodField = new JTextField();

        JLabel totalAmountLabel = new JLabel("총 액");
        totalAmountField = new JTextField();
        totalAmountField.setEditable(false);

        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(startLabel);
        inputPanel.add(startField);
        inputPanel.add(destinationLabel);
        inputPanel.add(destinationField);
        inputPanel.add(radio);
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
                LoginFrame loginFrame = new LoginFrame();
                new ViewFrame(loginFrame.userId);
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

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + month + "-" + day;
    }

    public void inputSaving() {
        String fileName = "C:\\Users\\차승석\\Desktop\\Coding\\study11\\swing\\src\\Transfortation\\data\\March\\transfortationInfo_" + LoginFrame.userId + ".txt";

        File file = new File(fileName);
        boolean fileExists = file.exists();

        // 파일이 이미 존재하는 경우
        if (fileExists) {
            int option = JOptionPane.showConfirmDialog(this,
                    "이미 아래의 정보가 등록되어 있습니다:\n" +
                            getCurrentDate() + "," + startField.getText() + "," + destinationField.getText() + "," +
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
            out.println(getCurrentDate() + "," + startField.getText() + "," + destinationField.getText() + "," +
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
            writer.println(getCurrentDate() + "," + startField.getText() + "," + destinationField.getText() + "," +
                    (r1.isSelected() ? "왕복" : "편도") + "," + costField.getText() + "," + billingPeriodField.getText() + "," +
                    totalAmount);
            JOptionPane.showMessageDialog(this, "저장이 완료되었습니다.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "저장에 실패했습니다.");
        }
    }
}
