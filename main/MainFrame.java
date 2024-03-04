package Transfortation.main;

import Transfortation.login.LoginFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
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
        JButton printButton = new JButton("출력하기");
        JButton saveButton = new JButton("저장하기");
        JButton backButton = new JButton("돌아가기");

        panel3.add(printButton);
        panel3.add(saveButton);
        panel3.add(backButton);

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement printing functionality
                JOptionPane.showMessageDialog(MainFrame.this, "출력하기");
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Implement saving functionality
                inputSaving();

                JOptionPane.showMessageDialog(MainFrame.this, "좋은 하루 되세요");
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
        String fileName = "C:\\Users\\차승석\\Desktop\\Coding\\study11\\swing\\src\\Transfortation\\data\\March\\transfortationInfo_" + userName + ".txt";

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            fileWriter.write("오늘의 날짜: " + getCurrentDate() + "\n");
            fileWriter.write("출발지: " + startField.getText() + "\n");
            fileWriter.write("도착지: " + destinationField.getText() + "\n");

            String travelType = r1.isSelected() ? "왕복" : "편도";
            fileWriter.write("여행 유형: " + travelType + "\n");

            fileWriter.write("왕복비용/편도비용: " + costField.getText() + "\n");
            fileWriter.write("청구일수: " + billingPeriodField.getText() + "\n");

            // 총액 계산
            double cost = Double.parseDouble(costField.getText());
            int billingPeriod = Integer.parseInt(billingPeriodField.getText());
            double totalAmount = cost * billingPeriod;
            fileWriter.write("총 액: " + totalAmount + "\n");

            JOptionPane.showMessageDialog(MainFrame.this, "저장이 완료되었습니다.");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(MainFrame.this, "저장에 실패했습니다.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame("John"); // Replace "John" with the actual user name
            }
        });
    }
}
