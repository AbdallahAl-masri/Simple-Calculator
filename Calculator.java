import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private JTextField displayField;
    private String numStr1 = "";
    private String numStr2 = "";
    private char op;
    private boolean firstInput = true, secondInput = false;

    public Calculator() {
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        displayField = new JTextField();
        displayField.setEditable(false);
        add(displayField, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4));

        String[] buttonLabels = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", "C", "=", "+"
        };

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.addActionListener(this);
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null); // Center the window
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculator calculator = new Calculator();
            calculator.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton source = (JButton) e.getSource();
        char ch = source.getText().charAt(0);

        String result="";

        try {
            if (Character.isDigit(ch)) {
                if (firstInput) {
                    numStr1 += ch;
                    displayField.setText(String.valueOf(ch));
                } else {
                    numStr2 += ch;
                    displayField.setText(displayField.getText() + " " + String.valueOf(ch));
                }
            } else if (ch == '+' || ch == '-' || ch == '*' || ch == '/') {
                displayField.setText(displayField.getText() + " " + ch);
                if (firstInput) {
                    firstInput = false;
                } else if (!numStr1.isEmpty() && !numStr2.isEmpty()){
                    String OP = displayField.getText().split(" ")[1];
                    op = OP.charAt(0);
                    result = evaluate();
                    numStr1 = result;
                    numStr2 = "";
                    displayField.setText(numStr1 + " " + ch);
                }
                op = ch;
            } else if (ch == '=') {
                if (!numStr1.isEmpty() && !numStr2.isEmpty()) {
                    result = evaluate();
                    displayField.setText(result);
                    numStr1 = result;
                    numStr2 = "";
                    op = '\0';
                    firstInput = true;
                } else {
                    displayField.setText("Error: Incomplete expression");
                }
            } else if (ch == 'C') {
                numStr1 = "";
                numStr2 = "";
                op = '\0';
                displayField.setText("");
                firstInput = true;
            } else {
                displayField.setText("Invalid input");
            }
        } catch (Exception ex) {
            displayField.setText("Error: " + ex.getMessage());
        }
    }

    private String evaluate() throws Exception {
        if (numStr1.isEmpty() || numStr2.isEmpty()) {
            throw new Exception("Incomplete expression");
        }

        double num1 = Double.parseDouble(numStr1);
        double num2 = Double.parseDouble(numStr2);
        double result = 0;

        switch (op) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 == 0) {
                    throw new Exception("Division by zero");
                }
                result = num1 / num2;
                break;
            default:
                throw new Exception("Invalid operator");
        }

        return String.valueOf(result);
    }
}
