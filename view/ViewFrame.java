package Transfortation.view;

import javax.swing.*;

import Transfortation.main.MainFrame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ViewFrame extends JFrame {

    public ViewFrame(String userName) {
        setTitle(userName + "님의 교통정산 조회");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        String fileName = "C:\\Users\\차승석\\Desktop\\Coding\\study11\\swing\\src\\Transfortation\\data\\March\\transfortationInfo_" + userName + ".txt";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            boolean fileIsEmpty = true;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                textArea.append("=======================\n");
                textArea.append("날짜: " + parts[0] + "\n");
                textArea.append("출발지: " + parts[1] + "\n");
                textArea.append("도착지: " + parts[2] + "\n");
                textArea.append("운행정보: " + parts[3] + "\n");
                textArea.append("운행요금: " + parts[4] + "\n");
                textArea.append("청구일수: " + parts[5] + "\n");
                // 총 청구액 계산
                int cost = Integer.parseInt(parts[4]);
                int billingPeriod = Integer.parseInt(parts[5]);
                int totalAmount = cost * billingPeriod;
                textArea.append("총 청구액: " + totalAmount + "\n");
                fileIsEmpty = false;
            }
            reader.close();

            if (fileIsEmpty) {
                textArea.setText("해당하는 정보가 없습니다.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            textArea.setText("파일을 찾을 수 없습니다.");
        }

        JButton closeButton = new JButton("돌아가기");
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(closeButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 창 닫기
                // MainFrame으로 전환
                new MainFrame(userName);
            }
        });

        add(mainPanel);
        setVisible(true);
    }
}
