package Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;

public class FightingGame {
    private JFrame frame;
    private JPanel panel1;
    private JPanel panel2;
    private JTextField nameInput;
    private JTextArea combatInfoArea;
    private int enemyHealth = 100;
    private int playerHealth = 100;
    private JProgressBar healthBar;
    private JProgressBar playerHealthBar;
    private JButton exitButton;
    private JLabel enemyLabel;
    private BackgroundMusicPlayer bgmPlayer;
    private SoundEffectPlayer soundEffectPlayer;
    private String playerName;
    private int punchCount = 6;
    private int kickCount = 4;
    private int skillCount = 2;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FightingGame game = new FightingGame();
            game.start();
        });
    }

    private void start() {
        frame = new JFrame("��������");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ù ��° ȭ��: �̸� �Է� �ޱ�

        panel1 = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel1.setLayout(gridBagLayout);

        JLabel label1 = new JLabel("�̸��� �Է��ϼ���:");
        label1.setPreferredSize(new Dimension(120, 100));
        nameInput = new JTextField();
        nameInput.setColumns(15);
        JButton nextButton = new JButton("����");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); 

        panel1.add(label1, gbc);

        gbc.gridy = 2;
        panel1.add(nameInput, gbc);

        gbc.gridy = 2;
        panel1.add(nextButton, gbc);

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerName = nameInput.getText();
                moveToNextPanel(playerName);
                startBackgroundMusicThread("C:/JavaLecture/����������Ʈ/src/images/pokemon-battle.wav");
            }

            private void startBackgroundMusicThread(String filePath) {
                bgmPlayer = new BackgroundMusicPlayer(filePath);
                Thread musicThread = new Thread(bgmPlayer);
                musicThread.start();
            }

        });

        panel1.add(label1);
        panel1.add(nameInput);
        panel1.add(nextButton);

        frame.getContentPane().add(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    class ImagePanel extends JPanel {
        private Image backgroundImage;

        public ImagePanel(String imagePath) {
            backgroundImage = new ImageIcon(imagePath).getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void moveToNextPanel(String playerName) {
        frame.getContentPane().remove(panel1);

        // �� ��° ȭ��: ĳ���� �̹��� �� ��ư
        panel2 = new ImagePanel("C:/JavaLecture/����������Ʈ/src/images/background.png");
        panel2.setLayout(new GridBagLayout());
        panel2.setPreferredSize(new Dimension(1040, 780));

        healthBar = new JProgressBar();
        healthBar.setMinimum(0); // �ּ� ü�� ����
        healthBar.setMaximum(100); // �ִ� ü�� ����
        healthBar.setValue(100); // �ʱ� ü�� ����
        healthBar.setStringPainted(true); 
        healthBar.setForeground(Color.GREEN);

        playerHealthBar = new JProgressBar();
        playerHealthBar.setMinimum(0); // �ּ� ü�� ����
        playerHealthBar.setMaximum(100); // �ִ� ü�� ����
        playerHealthBar.setValue(100); 
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setForeground(Color.GREEN);

        // �÷��̾� �̹����� �� �̹����� ���� ��
        JLabel playerLabel = new JLabel(new ImageIcon("C:/JavaLecture/����������Ʈ/src/images/player.png")); 
        enemyLabel = new JLabel(new ImageIcon("C:/JavaLecture/����������Ʈ/src/images/buu.png")); 

        // ���� ��ư �߰�
        JButton punchButton = new JButton("Punch");
        JButton kickButton = new JButton("Kick");
        JButton skillButton = new JButton("Skill");

        // ���� ������ ǥ���� JTextArea
        combatInfoArea = new JTextArea(10, 30);
        combatInfoArea.setFont(new Font("Ariel", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(combatInfoArea);

        GridBagConstraints gbc = new GridBagConstraints();

        exitButton = new JButton("���� ����");
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               System.exit(0);
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel2.add(exitButton, gbc);

        // �÷��̾� �̹��� ��ġ ���� (���� �Ʒ�)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START; // ���� ����
        gbc.insets = new Insets(0, 10, 0, 0);
        panel2.add(playerLabel, gbc);

        // �� �̹��� ��ġ ���� (������ ���)
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END; // ������ ����
        panel2.add(enemyLabel, gbc);

        // ��ư �г� ��ġ ����
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); 
        buttonPanel.add(punchButton);
        buttonPanel.add(kickButton);
        buttonPanel.add(skillButton);

        gbc.gridx = 3; // �����ʿ� ��ġ�ϰ��� �ϴ� ��ġ�� ����
        gbc.gridy = 2; // ���� �÷��̾� �̹��� �Ʒ��� ��ġ�ϰ��� ��
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 0, 0); 
        panel2.add(buttonPanel, gbc);
        
        // ���� ����â ��ġ ����
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 1�� �����Ͽ� ���� ����â�� ���� ũ�⸦ 1�� ����
        gbc.weightx = 1.0; // X �������� Ȯ�� ������ ���� ����
        gbc.weighty = 1.0; // Y �������� Ȯ�� ������ ���� ����
        gbc.fill = GridBagConstraints.BOTH; // �� �������� Ȯ���ϵ��� ����
        gbc.anchor = GridBagConstraints.CENTER;
        panel2.add(scrollPane, gbc);

        // healthBar�� playerHealthBar�� panel2�� �߰�
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 1�� �����Ͽ� ü�¹��� ���� ũ�⸦ 1�� ����
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // �������� ä���
        gbc.insets = new Insets(10, 0, 10, 0); 
        panel2.add(healthBar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 1�� �����Ͽ� �÷��̾� ü�¹��� ���� ũ�⸦ 1�� ����
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(10, 10, 10, 10); 
        panel2.add(playerHealthBar, gbc);

        frame.getContentPane().add(panel2);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // ���� ��ư�� ���� ActionListener �߰�
        punchButton.addActionListener(e -> {
            if (punchCount > 0 && playerHealth > 0) { // �÷��̾� ü���� 0���� Ŭ ���� ���� ���� ����
                int defenseChance = (int) (Math.random() * 10);
                if (defenseChance < 2) {
                    displayCombatInfo("��밡 ������ ����߽��ϴ�!\n");
                    startShieldEffectThread("C:/JavaLecture/����������Ʈ/src/images/shield.wav");
                    enemyAttack();
                } else {
                   startPunchEffectThread("C:/JavaLecture/����������Ʈ/src/images/light_punch1.wav");
                    displayCombatInfo("��밡 10�������� �޾ҽ��ϴ�");
                    decreaseEnemyHealth(10);
                }
                punchCount--; // ��ġ ��� Ƚ�� ����
                if (punchCount == 0) {
                    punchButton.setEnabled(false); // ��ġ ��ư ��Ȱ��ȭ
                }
            }
        });

        kickButton.addActionListener(e -> {
            if (kickCount > 0 && playerHealth > 0) {
                // �÷��̾� ü���� 0���� Ŭ ���� ���� ���� ����
                int defenseChance = (int) (Math.random() * 10);
                if (defenseChance < 2) {
                    displayCombatInfo("��밡 ������ ����߽��ϴ�!\n");
                    startShieldEffectThread("C:/JavaLecture/����������Ʈ/src/images/shield.wav");
                    enemyAttack();
                } else {
                    displayCombatInfo("��밡 15�������� �޾ҽ��ϴ�");
                    startKickEffectThread("C:/JavaLecture/����������Ʈ/src/images/kick.wav");
                    decreaseEnemyHealth(15);
                }
                kickCount--; // ű ��� Ƚ�� ����
                if (kickCount == 0) {
                    kickButton.setEnabled(false); // ű ��ư ��Ȱ��ȭ
                }
            }
        });

        skillButton.addActionListener(e -> {
            if (skillCount > 0 && playerHealth > 0) { // �÷��̾� ü���� 0���� Ŭ ���� ���� ���� ����
                int defenseChance = (int) (Math.random() * 10);
                if (defenseChance < 2) {
                    displayCombatInfo("��밡 ������ ����߽��ϴ�!\n");
                    startShieldEffectThread("C:/JavaLecture/����������Ʈ/src/images/shield.wav");
                    enemyAttack();
                } else {
                    displayCombatInfo("��밡 20�������� �޾ҽ��ϴ�");
                    startSkillEffectThread("C:/JavaLecture/����������Ʈ/src/images/skill.wav");
                    decreaseEnemyHealth(20);
                }
                skillCount--; // ��ų ��� Ƚ�� ����
                if (skillCount == 0) {
                    skillButton.setEnabled(false); // ��ų ��ư ��Ȱ��ȭ
                }
            }
        });
    }
    
    private void startShieldEffectThread(String filePath) {
        // ��׶��� �����忡�� ȿ���� ����ϴ� ������� ����
        Thread soundThread = new Thread(() -> {
            soundEffectPlayer = new SoundEffectPlayer(filePath);
            soundEffectPlayer.playHitSound("C:/JavaLecture/����������Ʈ/src/images/shield.wav");
        });
        soundThread.start();
    }

    private void startPunchEffectThread(String filePath) {
        Thread soundThread = new Thread(() -> {
            soundEffectPlayer = new SoundEffectPlayer(filePath);
            soundEffectPlayer.playHitSound("C:/JavaLecture/����������Ʈ/src/images/light_punch1.wav");
        });
        soundThread.start();
    }
    
    private void startKickEffectThread(String filePath) {
        Thread soundThread = new Thread(() -> {
            soundEffectPlayer = new SoundEffectPlayer(filePath);
            soundEffectPlayer.playHitSound("C:/JavaLecture/����������Ʈ/src/images/kick.wav");
        });
        soundThread.start();
    }
    
    private void startSkillEffectThread(String filePath) {
        Thread soundThread = new Thread(() -> {
            soundEffectPlayer = new SoundEffectPlayer(filePath);
            soundEffectPlayer.playHitSound("C:/JavaLecture/����������Ʈ/src/images/skill.wav");
        });
        soundThread.start();
    }

    private void decreaseEnemyHealth(int damage) {
        defendEnemy(damage);
        enemyAttack();

        ImageIcon newImageIcon = new ImageIcon("C:/JavaLecture/����������Ʈ/src/images/hit_buu.png");
        enemyLabel.setIcon(newImageIcon); // ���ο� �̹����� ����

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // ���� �̹����� ����
                ImageIcon originalImageIcon = new ImageIcon("C:/JavaLecture/����������Ʈ/src/images/buu.png");
                enemyLabel.setIcon(originalImageIcon); // ���� �̹����� ����
                panel2.revalidate(); // �г��� �ٽ� ��ȿȭ�Ͽ� ���� ������ ����
                panel2.repaint(); // �г��� �ٽ� �׷��� ���� ������ ����
            }
        }, 500);

        if (enemyHealth <= 0) {
            displayCombatInfo("�������� �¸��߽��ϴ�! 5�� �Ŀ� ���� ���������� �̵��մϴ�");

            Timer timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    moveToNextStage(); // ���� ���������� �̵��ϴ� �޼��� ȣ��
                }
            }, 5000); // 5�� �Ŀ� ����
        }

    }

    private void defendEnemy(int damage) {

        // ���� �÷��̾��� ������ �޴� ���
        enemyHealth -= damage; // ���� ü�� ����
        if (enemyHealth < 0) {
            enemyHealth = 0; // ���� ü���� 0 �̸��� ���� �ʵ��� ����
        }
        updateEnemyHealth(); // ���� ü�� ����

    }

    private void enemyAttack() {
        if (enemyHealth > 0) {
            Timer timer = new Timer();
            TimerTask enemyAttackTask = new TimerTask() {
                @Override
                public void run() {
                    int damage = getRandomDamage(); // 10, 15, 20 �� ������ �������� ��� �Լ� ȣ��

                    // �÷��̾� ���ݿ� ���� �ؽ�Ʈ ǥ��

                    // �÷��̾� ü�� ���� �� ������Ʈ
                    decreasePlayerHealth(damage);
                }
            };

            timer.schedule(enemyAttackTask, 1000);
        } // 1�� �ڿ� ���� �½�ũ ����
    }

    private int getRandomDamage() {
        Random random = new Random();
        int[] damages = { 10, 15, 20 };
        return damages[random.nextInt(damages.length)];
    }

    private void decreasePlayerHealth(int damage) {
        defendPlayer(damage);

        if (playerHealth <= 0) {
            playerHealth = 0;
            displayCombatInfo("���� ����: �÷��̾ �й��߽��ϴ�.");

        }
    }

    private void defendPlayer(int damage) {
        int defenseChance = (int) (Math.random() * 10);

        if (defenseChance < 2) { // 0, 1 : 20%�� Ȯ��
            displayCombatInfo(playerName + " ��(��)������ ����߽��ϴ�!\n");
            startShieldEffectThread("C:/JavaLecture/����������Ʈ/src/images/shield.wav");
        } else {
            // �÷��̾ ������ �޴� ���
            playerHealth -= damage; // ���� ü�� ����
            if (playerHealth < 0) {
                playerHealth = 0; // ���� ü���� 0 �̸��� ���� �ʵ��� ����
            }
            displayCombatInfo("��밡 �����߽��ϴ�! " + damage + "�� �������� �Ծ����ϴ�.");
            updatePlayerHealth(); 
        }

    }

    private void updateEnemyHealth() {
        combatInfoArea.append("���� ü��: " + enemyHealth + "\n\n");
        healthBar.setValue(enemyHealth); 
        int percentage = (int) ((double) enemyHealth / healthBar.getMaximum() * 100);

        if (percentage <= 20) {
            healthBar.setForeground(Color.RED); // ���� ü���� 20% �̸��� �� ���������� ����
        } else if (percentage <= 50) {
            healthBar.setForeground(Color.YELLOW); // ���� ü���� 50% �̸��� �� ��������� ����
        } else {
            healthBar.setForeground(Color.GREEN);
        }

    }

    private void updatePlayerHealth() {
        // �÷��̾� ü���� combatInfoArea(���� ���� ����)�� ����� �� ����
        combatInfoArea.append(playerName + "�� ü��: " + playerHealth + "\n\n");
        playerHealthBar.setValue(playerHealth); // �÷��̾� ü�¹� ����
        int percentage = (int) ((double) playerHealth / playerHealthBar.getMaximum() * 100);

        if (percentage <= 20) {
            playerHealthBar.setForeground(Color.RED); // �÷��̾��� ü���� 20% �̸��� �� ���������� ����
        } else if (percentage <= 50) {
            playerHealthBar.setForeground(Color.YELLOW); // �÷��̾��� ü���� 50% �̸��� �� ��������� ����
        } else {
            playerHealthBar.setForeground(Color.GREEN);
        }

    }

    private void moveToNextStage() {
        JFrame newFrame = new JFrame("���� ��������");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //(���� �������� ����)

        newFrame.pack();
        newFrame.setLocationRelativeTo(null); // ȭ���� �߾ӿ� ��ġ
        newFrame.setVisible(true);

        // ���� ������ �����
        frame.setVisible(false);

    }

    private void displayCombatInfo(String message) {
        combatInfoArea.append(message + "\n");
        combatInfoArea.setCaretPosition(combatInfoArea.getDocument().getLength());
    }

}