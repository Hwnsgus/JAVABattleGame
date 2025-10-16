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
        frame = new JFrame("격투게임");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 첫 번째 화면: 이름 입력 받기

        panel1 = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel1.setLayout(gridBagLayout);

        JLabel label1 = new JLabel("이름을 입력하세요:");
        label1.setPreferredSize(new Dimension(120, 100));
        nameInput = new JTextField();
        nameInput.setColumns(15);
        JButton nextButton = new JButton("다음");

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
                startBackgroundMusicThread("C:/JavaLecture/객지프로젝트/src/images/pokemon-battle.wav");
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

        // 두 번째 화면: 캐릭터 이미지 및 버튼
        panel2 = new ImagePanel("C:/JavaLecture/객지프로젝트/src/images/background.png");
        panel2.setLayout(new GridBagLayout());
        panel2.setPreferredSize(new Dimension(1040, 780));

        healthBar = new JProgressBar();
        healthBar.setMinimum(0); // 최소 체력 설정
        healthBar.setMaximum(100); // 최대 체력 설정
        healthBar.setValue(100); // 초기 체력 설정
        healthBar.setStringPainted(true); 
        healthBar.setForeground(Color.GREEN);

        playerHealthBar = new JProgressBar();
        playerHealthBar.setMinimum(0); // 최소 체력 설정
        playerHealthBar.setMaximum(100); // 최대 체력 설정
        playerHealthBar.setValue(100); 
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setForeground(Color.GREEN);

        // 플레이어 이미지와 적 이미지를 넣을 라벨
        JLabel playerLabel = new JLabel(new ImageIcon("C:/JavaLecture/객지프로젝트/src/images/player.png")); 
        enemyLabel = new JLabel(new ImageIcon("C:/JavaLecture/객지프로젝트/src/images/buu.png")); 

        // 공격 버튼 추가
        JButton punchButton = new JButton("Punch");
        JButton kickButton = new JButton("Kick");
        JButton skillButton = new JButton("Skill");

        // 전투 정보를 표시할 JTextArea
        combatInfoArea = new JTextArea(10, 30);
        combatInfoArea.setFont(new Font("Ariel", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(combatInfoArea);

        GridBagConstraints gbc = new GridBagConstraints();

        exitButton = new JButton("게임 종료");
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

        // 플레이어 이미지 위치 설정 (왼쪽 아래)
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.LINE_START; // 왼쪽 정렬
        gbc.insets = new Insets(0, 10, 0, 0);
        panel2.add(playerLabel, gbc);

        // 적 이미지 위치 설정 (오른쪽 상단)
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END; // 오른쪽 정렬
        panel2.add(enemyLabel, gbc);

        // 버튼 패널 위치 설정
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10)); 
        buttonPanel.add(punchButton);
        buttonPanel.add(kickButton);
        buttonPanel.add(skillButton);

        gbc.gridx = 3; // 오른쪽에 배치하고자 하는 위치로 설정
        gbc.gridy = 2; // 원래 플레이어 이미지 아래에 배치하고자 함
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.insets = new Insets(0, 0, 0, 0); 
        panel2.add(buttonPanel, gbc);
        
        // 전투 정보창 위치 설정
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1; // 1로 변경하여 전투 정보창의 가로 크기를 1로 설정
        gbc.weightx = 1.0; // X 방향으로 확장 가능한 공간 설정
        gbc.weighty = 1.0; // Y 방향으로 확장 가능한 공간 설정
        gbc.fill = GridBagConstraints.BOTH; // 두 방향으로 확장하도록 설정
        gbc.anchor = GridBagConstraints.CENTER;
        panel2.add(scrollPane, gbc);

        // healthBar와 playerHealthBar를 panel2에 추가
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1; // 1로 변경하여 체력바의 가로 크기를 1로 설정
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; // 수평으로 채우기
        gbc.insets = new Insets(10, 0, 10, 0); 
        panel2.add(healthBar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1; // 1로 변경하여 플레이어 체력바의 가로 크기를 1로 설정
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.insets = new Insets(10, 10, 10, 10); 
        panel2.add(playerHealthBar, gbc);

        frame.getContentPane().add(panel2);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 공격 버튼에 대한 ActionListener 추가
        punchButton.addActionListener(e -> {
            if (punchCount > 0 && playerHealth > 0) { // 플레이어 체력이 0보다 클 때만 공격 동작 수행
                int defenseChance = (int) (Math.random() * 10);
                if (defenseChance < 2) {
                    displayCombatInfo("상대가 공격을 방어했습니다!\n");
                    startShieldEffectThread("C:/JavaLecture/객지프로젝트/src/images/shield.wav");
                    enemyAttack();
                } else {
                   startPunchEffectThread("C:/JavaLecture/객지프로젝트/src/images/light_punch1.wav");
                    displayCombatInfo("상대가 10데미지를 받았습니다");
                    decreaseEnemyHealth(10);
                }
                punchCount--; // 펀치 사용 횟수 감소
                if (punchCount == 0) {
                    punchButton.setEnabled(false); // 펀치 버튼 비활성화
                }
            }
        });

        kickButton.addActionListener(e -> {
            if (kickCount > 0 && playerHealth > 0) {
                // 플레이어 체력이 0보다 클 때만 공격 동작 수행
                int defenseChance = (int) (Math.random() * 10);
                if (defenseChance < 2) {
                    displayCombatInfo("상대가 공격을 방어했습니다!\n");
                    startShieldEffectThread("C:/JavaLecture/객지프로젝트/src/images/shield.wav");
                    enemyAttack();
                } else {
                    displayCombatInfo("상대가 15데미지를 받았습니다");
                    startKickEffectThread("C:/JavaLecture/객지프로젝트/src/images/kick.wav");
                    decreaseEnemyHealth(15);
                }
                kickCount--; // 킥 사용 횟수 감소
                if (kickCount == 0) {
                    kickButton.setEnabled(false); // 킥 버튼 비활성화
                }
            }
        });

        skillButton.addActionListener(e -> {
            if (skillCount > 0 && playerHealth > 0) { // 플레이어 체력이 0보다 클 때만 공격 동작 수행
                int defenseChance = (int) (Math.random() * 10);
                if (defenseChance < 2) {
                    displayCombatInfo("상대가 공격을 방어했습니다!\n");
                    startShieldEffectThread("C:/JavaLecture/객지프로젝트/src/images/shield.wav");
                    enemyAttack();
                } else {
                    displayCombatInfo("상대가 20데미지를 받았습니다");
                    startSkillEffectThread("C:/JavaLecture/객지프로젝트/src/images/skill.wav");
                    decreaseEnemyHealth(20);
                }
                skillCount--; // 스킬 사용 횟수 감소
                if (skillCount == 0) {
                    skillButton.setEnabled(false); // 스킬 버튼 비활성화
                }
            }
        });
    }
    
    private void startShieldEffectThread(String filePath) {
        // 백그라운드 스레드에서 효과음 재생하는 방식으로 변경
        Thread soundThread = new Thread(() -> {
            soundEffectPlayer = new SoundEffectPlayer(filePath);
            soundEffectPlayer.playHitSound("C:/JavaLecture/객지프로젝트/src/images/shield.wav");
        });
        soundThread.start();
    }

    private void startPunchEffectThread(String filePath) {
        Thread soundThread = new Thread(() -> {
            soundEffectPlayer = new SoundEffectPlayer(filePath);
            soundEffectPlayer.playHitSound("C:/JavaLecture/객지프로젝트/src/images/light_punch1.wav");
        });
        soundThread.start();
    }
    
    private void startKickEffectThread(String filePath) {
        Thread soundThread = new Thread(() -> {
            soundEffectPlayer = new SoundEffectPlayer(filePath);
            soundEffectPlayer.playHitSound("C:/JavaLecture/객지프로젝트/src/images/kick.wav");
        });
        soundThread.start();
    }
    
    private void startSkillEffectThread(String filePath) {
        Thread soundThread = new Thread(() -> {
            soundEffectPlayer = new SoundEffectPlayer(filePath);
            soundEffectPlayer.playHitSound("C:/JavaLecture/객지프로젝트/src/images/skill.wav");
        });
        soundThread.start();
    }

    private void decreaseEnemyHealth(int damage) {
        defendEnemy(damage);
        enemyAttack();

        ImageIcon newImageIcon = new ImageIcon("C:/JavaLecture/객지프로젝트/src/images/hit_buu.png");
        enemyLabel.setIcon(newImageIcon); // 새로운 이미지로 변경

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                // 원래 이미지로 복원
                ImageIcon originalImageIcon = new ImageIcon("C:/JavaLecture/객지프로젝트/src/images/buu.png");
                enemyLabel.setIcon(originalImageIcon); // 원래 이미지로 변경
                panel2.revalidate(); // 패널을 다시 유효화하여 변경 사항을 적용
                panel2.repaint(); // 패널을 다시 그려서 변경 사항을 적용
            }
        }, 500);

        if (enemyHealth <= 0) {
            displayCombatInfo("전투에서 승리했습니다! 5초 후에 다음 스테이지로 이동합니다");

            Timer timer1 = new Timer();
            timer1.schedule(new TimerTask() {
                @Override
                public void run() {
                    moveToNextStage(); // 다음 스테이지로 이동하는 메서드 호출
                }
            }, 5000); // 5초 후에 실행
        }

    }

    private void defendEnemy(int damage) {

        // 적이 플레이어의 공격을 받는 경우
        enemyHealth -= damage; // 적의 체력 감소
        if (enemyHealth < 0) {
            enemyHealth = 0; // 적의 체력이 0 미만이 되지 않도록 보정
        }
        updateEnemyHealth(); // 적의 체력 갱신

    }

    private void enemyAttack() {
        if (enemyHealth > 0) {
            Timer timer = new Timer();
            TimerTask enemyAttackTask = new TimerTask() {
                @Override
                public void run() {
                    int damage = getRandomDamage(); // 10, 15, 20 중 랜덤한 데미지를 얻는 함수 호출

                    // 플레이어 공격에 대한 텍스트 표시

                    // 플레이어 체력 감소 및 업데이트
                    decreasePlayerHealth(damage);
                }
            };

            timer.schedule(enemyAttackTask, 1000);
        } // 1초 뒤에 공격 태스크 실행
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
            displayCombatInfo("게임 종료: 플레이어가 패배했습니다.");

        }
    }

    private void defendPlayer(int damage) {
        int defenseChance = (int) (Math.random() * 10);

        if (defenseChance < 2) { // 0, 1 : 20%의 확률
            displayCombatInfo(playerName + " 이(가)공격을 방어했습니다!\n");
            startShieldEffectThread("C:/JavaLecture/객지프로젝트/src/images/shield.wav");
        } else {
            // 플레이어가 공격을 받는 경우
            playerHealth -= damage; // 적의 체력 감소
            if (playerHealth < 0) {
                playerHealth = 0; // 적의 체력이 0 미만이 되지 않도록 보정
            }
            displayCombatInfo("상대가 공격했습니다! " + damage + "의 데미지를 입었습니다.");
            updatePlayerHealth(); 
        }

    }

    private void updateEnemyHealth() {
        combatInfoArea.append("적의 체력: " + enemyHealth + "\n\n");
        healthBar.setValue(enemyHealth); 
        int percentage = (int) ((double) enemyHealth / healthBar.getMaximum() * 100);

        if (percentage <= 20) {
            healthBar.setForeground(Color.RED); // 적의 체력이 20% 미만일 때 빨간색으로 변경
        } else if (percentage <= 50) {
            healthBar.setForeground(Color.YELLOW); // 적의 체력이 50% 미만일 때 노란색으로 변경
        } else {
            healthBar.setForeground(Color.GREEN);
        }

    }

    private void updatePlayerHealth() {
        // 플레이어 체력을 combatInfoArea(전투 정보 영역)에 출력할 수 있음
        combatInfoArea.append(playerName + "의 체력: " + playerHealth + "\n\n");
        playerHealthBar.setValue(playerHealth); // 플레이어 체력바 갱신
        int percentage = (int) ((double) playerHealth / playerHealthBar.getMaximum() * 100);

        if (percentage <= 20) {
            playerHealthBar.setForeground(Color.RED); // 플레이어의 체력이 20% 미만일 때 빨간색으로 변경
        } else if (percentage <= 50) {
            playerHealthBar.setForeground(Color.YELLOW); // 플레이어의 체력이 50% 미만일 때 노란색으로 변경
        } else {
            playerHealthBar.setForeground(Color.GREEN);
        }

    }

    private void moveToNextStage() {
        JFrame newFrame = new JFrame("다음 스테이지");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //(다음 스테이지 설정)

        newFrame.pack();
        newFrame.setLocationRelativeTo(null); // 화면을 중앙에 배치
        newFrame.setVisible(true);

        // 현재 프레임 숨기기
        frame.setVisible(false);

    }

    private void displayCombatInfo(String message) {
        combatInfoArea.append(message + "\n");
        combatInfoArea.setCaretPosition(combatInfoArea.getDocument().getLength());
    }

}