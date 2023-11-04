//Numberguessing Game
import java.util.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;

class NumberGame {
    int target;
    int maxAttempts = 3;
    int attemptsLeft;
    int rounds = 1;
    int score = 0;

    JLabel msg;
    JTextField input;
    JButton guess;
    JButton newgame;
    JLabel sc;
    JLabel result;

    NumberGame() {
        target = generateRandomNumber();
        attemptsLeft = maxAttempts;

        JFrame f = new JFrame("Number Game");
        msg = new JLabel("Guess The Number");
        msg.setBounds(10, 20, 200, 30);

        input = new JTextField(10);
        input.setBounds(200, 20, 200, 30);

        guess = new JButton("Check");
        guess.setBounds(10, 70, 100, 30);

        newgame = new JButton("Play Again");
        newgame.setBounds(130, 70, 100, 30);

        sc = new JLabel("Score " + score);
        sc.setBounds(10, 100, 200, 30);

        result = new JLabel(" ");
        result.setBounds(10, 160, 500, 30);
        // add
        f.add(msg);
        f.add(input);
        f.add(guess);
        f.add(newgame);
        f.add(sc);
        f.add(result);
        // p.add(f);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 500);
        f.setLayout(null);
        f.setVisible(true);

        guess.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        newgame.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                startAgain();
            }
        });

    }

    public int generateRandomNumber() {
        Random r = new Random();
        return r.nextInt(100) + 1;
    }

    public void checkAnswer() {
        try {
            int in = Integer.parseInt(input.getText());

            if (in == target) {
                result.setText("Congratulations! You guessed the number!");
                score += attemptsLeft;
                sc.setText("Score: " + score);
                newgame.setEnabled(true);
            } else {
                if (in > 100 & in < 1) {
                    result.setText("Enter number between 1 to 100");
                }
                attemptsLeft--;
                if (attemptsLeft > 0) {
                    result.setText("Wrong guess. Attempts left: " + attemptsLeft);
                    input.setText("");
                } else {
                    result.setText("You've run out of attempts. The correct number was: " + target);
                    newgame.setEnabled(true);
                    input.setText("");
                }
            }
        } catch (NumberFormatException e) {
            result.setText("Enter Valid Number...");
            return;
        }

    }

    public void startAgain() {
        rounds++;
        target = new Random().nextInt(100) + 1;
        attemptsLeft = maxAttempts;
        result.setText("Guess the number between 1 and 100:");
        input.setText("");
        newgame.setEnabled(false);
    }

    public static void main(String[] args) {
        NumberGame m = new NumberGame();
    }
}