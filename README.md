# 🥊 JavaFightingGame

자바(Java) Swing/AWT를 기반으로 제작된 **GUI 격투 게임**입니다.  
플레이어는 서로 공격/방어를 수행하며 체력이 0이 될 때까지 전투를 진행합니다. 
전투 중에는 **배경음악(BackgroundMusicPlayer)** 과 **효과음(SoundEffectPlayer)** 이 함께 동작하여 몰입감을 더합니다.

---

## 📸 In-Game Screenshots

게임의 주요 플레이 화면입니다.

| **1. 게임 대기 화면** |
| :---: |
| <img src="./src/images/javabattlegame screen1.png" width="100%" alt="게임 시작 화면"> |
| **게임 시작 시 플레이어와 적 캐릭터가 등장하며, 하단 메뉴를 통해 행동을 선택합니다.** |

<br>

| **2. 전투 진행 & 로그** |
| :---: |
| <img src="./src/images/javabattlegame screen2.png" width="100%" alt="전투 진행 화면"> |
| **공격 및 방어 성공 여부가 중앙 로그 창에 실시간으로 출력되며 체력바가 감소합니다.** |

<br>

| **3. 승리 및 종료** |
| :---: |
| <img src="./src/images/javabattlegame screen3.png" width="100%" alt="승리 화면"> |
| **적의 체력을 0으로 만들면 승리 메시지와 함께 게임이 종료됩니다.** |

---

## 🏗️ 프로젝트 구조

| 클래스명 | 역할 및 설명 |
| :--- | :--- |
| **FightingGame.java** | 메인 클래스. 캐릭터 생성, GUI 렌더링, 공격/방어 로직, 승패 판정을 담당합니다. |
| **BackgroundMusicPlayer.java** | 게임의 분위기를 살려주는 배경음악을 재생/중지/반복(Loop) 처리합니다. |
| **SoundEffectPlayer.java** | 펀치, 킥, 스킬 사용 및 피격 시 상황에 맞는 효과음을 재생합니다. |
| **Player.java** | (구조 예정) 플레이어의 이름, 체력, 상태 값을 관리하는 객체입니다. |

---

## 📐 클래스 다이어그램

![Class Diagram](./src/images/diagram.png)

> **설계 의도:** > `FightingGame` 클래스가 중심 컨트롤러(Controller) 역할을 수행하며, 
> 사용자 입력을 받아 `BackgroundMusicPlayer`와 `SoundEffectPlayer` 등 각 기능을 제어하는 구조로 설계하였습니다.

---

## 👨‍💻 만든 사람

**황준현, 임상규**
