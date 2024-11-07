import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class RockPaperScisors {

    private static int winCount = 0;
    private static int loseCount = 0;
    private static int tieCount = 0;

    public static void main(String[] args) {
        JFrame f = new JFrame("Rock Paper Scissors");
        f.setSize(600, 500);

        final JTextField tf = new JTextField();
        tf.setBounds(100, 50, 400, 40);
        tf.setFont(new Font("Arial", Font.PLAIN, 16));
        tf.setText("Welcome to Rock Paper Scissors game!!");
        tf.setHorizontalAlignment(SwingConstants.CENTER);

        JButton rock = new JButton("Rock 🪨");
        JButton paper = new JButton("Paper 📄");
        JButton scissors = new JButton("Scissors ✂️");
        JButton playAgain = new JButton("Play Again");

        rock.setBounds(200, 150, 150, 40);
        paper.setBounds(200, 210, 150, 40);
        scissors.setBounds(200, 270, 150, 40);
        playAgain.setBounds(200, 330, 150, 40);
        playAgain.setEnabled(false);

        Font buttonFont = new Font("Arial", Font.PLAIN, 16);
        rock.setFont(buttonFont);
        paper.setFont(buttonFont);
        scissors.setFont(buttonFont);
        playAgain.setFont(buttonFont);

        final JTextField winCounter = new JTextField("Wins: 0");
        final JTextField loseCounter = new JTextField("Losses: 0");
        final JTextField tieCounter = new JTextField("Ties: 0");

        winCounter.setBounds(470, 150, 100, 30);
        loseCounter.setBounds(470, 210, 100, 30);
        tieCounter.setBounds(470, 270, 100, 30);

        Font counterFont = new Font("Arial", Font.PLAIN, 14);
        winCounter.setFont(counterFont);
        loseCounter.setFont(counterFont);
        tieCounter.setFont(counterFont);

        winCounter.setEditable(false);
        loseCounter.setEditable(false);
        tieCounter.setEditable(false);

        // Track the chosen button to change its color based on the result
        final JButton[] selectedButton = new JButton[1];

        ActionListener gameAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userChoice = e.getActionCommand();
                JButton button = (JButton) e.getSource();
                selectedButton[0] = button;

                // Change chosen button color to light blue
                button.setBackground(Color.CYAN);
                button.setForeground(Color.BLACK); // Set text color for better contrast

                // Disable buttons after choice
                rock.setEnabled(false);
                paper.setEnabled(false);
                scissors.setEnabled(false);

                String[] choices = {"Rock 🪨", "Paper 📄", "Scissors ✂️"};
                Timer thinkingTimer = new Timer(100, null);
                thinkingTimer.addActionListener(new ActionListener() {
                    int index = 0;
                    int elapsed = 0;

                    @Override
                    public void actionPerformed(ActionEvent event) {
                        if (elapsed < 3000) {
                            tf.setText("Opponent is thinking... " + choices[index]);
                            index = (index + 1) % 3;
                            elapsed += 100;
                        } else {
                            thinkingTimer.stop();
                            int opponent = (int) (Math.random() * 3);
                            String opponentChoice = choices[opponent];
                            String result;

                            if (userChoice.equals(opponentChoice)) {
                                result = "It's a tie!";
                                tieCount++;
                                tieCounter.setText("Ties: " + tieCount);
                                button.setBackground(Color.YELLOW);  // Tie color
                            } else if ((userChoice.equals("Rock 🪨") && opponentChoice.equals("Scissors ✂️")) ||
                                    (userChoice.equals("Paper 📄") && opponentChoice.equals("Rock 🪨")) ||
                                    (userChoice.equals("Scissors ✂️") && opponentChoice.equals("Paper 📄"))) {
                                result = "You win! 🥇";
                                winCount++;
                                winCounter.setText("Wins: " + winCount);
                                button.setBackground(Color.GREEN);  // Win color
                            } else {
                                result = "You lose! ❌";
                                loseCount++;
                                loseCounter.setText("Losses: " + loseCount);
                                button.setBackground(Color.RED);  // Lose color
                            }

                            tf.setText("Opponent chose " + opponentChoice + ". " + result);
                            playAgain.setEnabled(true);
                        }
                    }
                });
                thinkingTimer.start();
            }
        };

        playAgain.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tf.setText("Make your choice!");
                rock.setEnabled(true);
                paper.setEnabled(true);
                scissors.setEnabled(true);
                playAgain.setEnabled(false);

                // Reset color of the selected button to default
                if (selectedButton[0] != null) {
                    selectedButton[0].setBackground(null); // Set to default color
                    selectedButton[0].setForeground(null); // Reset text color
                    selectedButton[0] = null;
                }
            }
        });

        rock.addActionListener(gameAction);
        paper.addActionListener(gameAction);
        scissors.addActionListener(gameAction);

        f.add(tf);
        f.add(rock);
        f.add(paper);
        f.add(scissors);
        f.add(playAgain);
        f.add(winCounter);
        f.add(loseCounter);
        f.add(tieCounter);

        f.setLayout(null);
        f.setVisible(true);
    }
}
