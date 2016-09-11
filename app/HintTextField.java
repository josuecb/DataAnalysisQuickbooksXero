package app;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Josue on 8/20/2016.
 */
public class HintTextField extends JTextField implements FocusListener {
    private boolean simulatePassword = false;
    private String passwordValue = "";
    private final String hint;
    private boolean showingHint;

    public HintTextField(final String hint) {
        super(hint);
        this.hint = hint;
        super.setForeground(new Color(180, 180, 180));
        this.showingHint = true;
        super.addFocusListener(this);
    }

    public void simulatePasswordField() {
        this.simulatePassword = true;

        this.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
//                System.out.println("Pressed");
                if (e.getKeyCode() == 8) {
                    try {
                        passwordValue = passwordValue.substring(0, passwordValue.length() - 2).trim();
                    } catch (Exception ex) {
                        passwordValue = "";
                    }
                } else {
                    if (e.getKeyCode() > 44 && e.getKeyCode() < 112) {
                        passwordValue += e.getKeyChar();
//                            System.out.println(passwordValue);
                    }
                }
                replaceString(getText());

            }

            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
    }

    public String getPasswordValue() {
        return this.passwordValue;
    }

    private void replaceString(String text) {
        String newText = "";
        if (text.length() > 0) {
            for (int i = 0; i < text.length(); i++) {
                newText += "*";
            }
        }

        setText(newText);
    }

    @Override
    public void focusGained(FocusEvent e) {
        super.setForeground(new Color(60, 60, 60));
        if (this.getText().isEmpty()) {
            super.setText("");
            showingHint = false;
        } else {
            if (this.simulatePassword) {
                passwordValue = this.getText();
            }
        }

    }

    @Override
    public void focusLost(FocusEvent e) {
        if (this.getText().isEmpty()) {
            super.setText(hint);
            super.setForeground(new Color(180, 180, 180));
            showingHint = true;
        }
    }

    @Override
    public synchronized void addKeyListener(KeyListener l) {
            super.addKeyListener(l);
    }


    @Override
    public String getText() {
        if (hint.equals(super.getText())) {
            return "";
        } else {
            return super.getText();
        }
    }
}
