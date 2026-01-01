import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorApp extends JFrame implements ActionListener {

    private JTextField screen;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    private final Color BG_COLOR = new Color(33, 33, 33);
    private final Color SCREEN_BG = new Color(48, 48, 48);
    private final Color BTN_NUM_BG = new Color(66, 66, 66);
    private final Color BTN_OP_BG = new Color(255, 149, 0);
    private final Color BTN_FUNC_BG = new Color(165, 165, 165);
    private final Color TEXT_COLOR = Color.WHITE;

    public CalculatorApp() {
        setTitle("Calculator");
        setSize(350, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(BG_COLOR);

        screen = new JTextField();
        screen.setFont(new Font("Segoe UI", Font.BOLD, 40));
        screen.setEditable(false);
        screen.setHorizontalAlignment(JTextField.RIGHT);
        screen.setBackground(SCREEN_BG);
        screen.setForeground(TEXT_COLOR);
        screen.setBorder(new EmptyBorder(20, 15, 20, 15));
        add(screen, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(4, 3, 8, 8));
        centerPanel.setBackground(BG_COLOR);
        centerPanel.setBorder(new EmptyBorder(0, 10, 10, 0));

        String[] numButtons = {
                "1", "2", "3",
                "4", "5", "6",
                "7", "8", "9",
                ".", "0", "="
        };

        for (String text : numButtons) {
            JButton btn = createStyledButton(text, BTN_NUM_BG);
            if (text.equals("=")) btn.setBackground(new Color(0, 122, 255));
            centerPanel.add(btn);
        }

        JPanel eastPanel = new JPanel(new GridLayout(5, 1, 8, 8));
        eastPanel.setBackground(BG_COLOR);
        eastPanel.setBorder(new EmptyBorder(0, 5, 10, 10));

        String[] opButtons = {"C", "+", "-", "*", "/"};
        for (String text : opButtons) {
            Color color = text.equals("C") ? BTN_FUNC_BG : BTN_OP_BG;
            JButton btn = createStyledButton(text, color);
            if(text.equals("C")) btn.setForeground(Color.BLACK);
            eastPanel.add(btn);
        }

        add(centerPanel, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);

        setVisible(true);
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        btn.setBackground(bg);
        btn.setForeground(TEXT_COLOR);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
            screen.setText(screen.getText() + command);
        }
        else if (command.equals(".")) {
            if (!screen.getText().contains(".")) {
                if (screen.getText().isEmpty()) {
                    screen.setText("0.");
                } else {
                    screen.setText(screen.getText() + ".");
                }
            }
        }
        else if (command.equals("C")) {
            screen.setText("");
            num1 = 0;
            num2 = 0;
            result = 0;
        }
        else if (command.equals("=")) {
            if (!screen.getText().isEmpty()) {
                try {
                    num2 = Double.parseDouble(screen.getText());

                    switch (operator) {
                        case '+': result = num1 + num2; break;
                        case '-': result = num1 - num2; break;
                        case '*': result = num1 * num2; break;
                        case '/':
                            if (num2 != 0) result = num1 / num2;
                            else result = 0;
                            break;
                    }

                    if (result == (long) result) {
                        screen.setText(String.format("%d", (long) result));
                    } else {
                        screen.setText(String.valueOf(result));
                    }

                    num1 = result;
                } catch (Exception ex) {
                    screen.setText("Error");
                }
            }
        }
        else {
            if (!screen.getText().isEmpty()) {
                num1 = Double.parseDouble(screen.getText());
                operator = command.charAt(0);
                screen.setText("");
            }
        }
    }

    public static void main(String[] args) {

        new CalculatorApp();
    }
}
