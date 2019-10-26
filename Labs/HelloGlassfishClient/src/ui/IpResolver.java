package ui;

import bl.ResolveIpBl;

import javax.swing.*;

public class IpResolver {
    private JTextField textField1;
    private JButton button1;
    private JLabel label1;
    private JPanel jPanel;
    private ResolveIpBl resolveIpBl = new ResolveIpBl();

    public void showForm() {
        createUIComponents();
        JFrame jFrame = new JFrame("IpResolver");
        jFrame.setContentPane(this.jPanel);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }

    private void createUIComponents() {
        button1.addActionListener(e -> onButtonClick());
    }

    private void onButtonClick() {
        String cityName = resolveIpBl.getCity(textField1.getText());
        label1.setText(cityName);
        System.out.println(cityName);
    }
}
