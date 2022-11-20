 # 1:1 건강검진 파트너 깐부 

<br>

## 1. 프로젝트 개요

### 🏆 **프로젝트 목표**

### 복잡한 건강검진 절차를 자율주행 기반의 로봇이 수검자가 건강검진을 완료할 수 있도록 1:1 안내를 진행하는 IoT 프로젝트입니다. 

> 안내 기능뿐 아니라 검진 대기 중 다양한 컨텐츠를 제공하여 건강 검진을 보다 지루하지 않게 받을 수 있게 구성하였습니다.

1. SLAM 기반의 자율주행 
2. 1:1 안내를 위해 OpenCV을 이용한 수검자 Tracking 기능
3.  STT(Speech-to-Text), TTS(Text-to-Speech)를 이용한 자동 문진 작성 기능 및 KWS(Keyword Spotting) 기능

4. Pose Estimation을 이용한 스트레칭 게임

<br>

### 📅 **전체 일정**

### 2022.10.11. ~ 2022.11.21. (6주)

|             기 간             | 내 용                                                        |
| :---------------------------: | :----------------------------------------------------------- |
| 2022. 10. 11. ~ 2022. 10. 14. | 아이디어 선정                                                |
| 2022. 10. 17. ~ 2022. 10. 21. | 요구사항 분석 및 기능명세서 작성 / 프로토타입 / ERD / REST API 설계 |
| 2022. 10. 24. ~ 2022. 10. 28. | TurtleBot 설계 / 개발환경 설정 / 개발 시작                   |
| 2022. 10. 31. ~ 2022. 11. 4.  | Gazebo 시뮬레이션 추가 / 서비스 개발 및 1차 배포             |
| 2022. 11. 7. ~ 2022. 11. 11.  | 서비스 개발 및 유지보수                                      |
|  2022.11. 14. ~ 2022.11. 18.  | [QA](https://www.notion.so/QA-ac30a4b221f64e2e9da168ebdc59fc64) 및 프로젝트 마무리 |

<br>

### 👥 **구성원**

**싸브란스 병원 팀**         

![image-20221120145450499](https://user-images.githubusercontent.com/111056719/202899286-d143f7b8-2f88-43b0-9394-d7de72662d3d.png)

<br>

## 2. 프로젝트 설계

### 🎨 Figma[(link)](https://www.figma.com/file/foFbHtvnSpE8jtfhXfQECp/%EC%9E%90%EC%9C%A8-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8?node-id=92%3A2&t=RGdrNQyRFcgt1GgR-1) / 📖 **기능명세서 [(link)](https://docs.google.com/spreadsheets/d/1iZGnNJgl5VaMxyMT8uY7smfvJN1GXhAH3OsUwPxjn48/edit#gid=0)** / :page_with_curl:API [(link)]( https://miniature-heat-dc4.notion.site/API-2772e82f29e547e2af3340e263524e6d)

### <br>

### 🛠 개발환경 [(link)](https://miniature-heat-dc4.notion.site/Tool-1cff91a246c0417e94f70e035c10236e)

| Backend                 | Frontend  | IoT        | CI/CD            | 협업툴     |
| ----------------------- | --------- | ---------- | ---------------- | ---------- |
| Java Open-JDK zulu 17   | Vue 2     | ROS        | AWS EC2          | Mattermost |
| SpringBoot Gradle 2.7.2 | Vuex      | Gazebo     | Ubuntu 20.04 LTS | Webex      |
| Spring Data JPA         | axios     | Roslib     | Docker 20.10.18  | Notion     |
| Lombok                  | BootStrap | rosbridge  | Jenkins          |            |
| Swagger 2.9.2           | node.js   | TurtleBot  | Nginx            |            |
| MySQL 8.0.29            | SockJS    | JetsonNano |                  |            |
| Fast API                |           |            |                  |            |
| Azure                   |           |            |                  |            |
| OpenCV                  |           |            |                  |            |

<br>

### 📊 아키텍처

 ![Web App Reference Architecture (3)](https://user-images.githubusercontent.com/111056719/202900144-517ae773-35cf-4fa8-a960-c212b2897652.png)

<br>

### 🛢 ERD

  ![image-20221120143054890](https://user-images.githubusercontent.com/111056719/202900166-565569d5-f2ba-490e-83f4-c1689d34ef99.png)  

<br>

## 4. 프로젝트 소개

|                      의료진 접수처 화면                      |                      의료진 검진실 화면                      |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![ec2 로그인](https://user-images.githubusercontent.com/111056719/202899306-89b79aae-18df-4b7a-b434-dbde869c1d04.gif) | ![image](https://https://lab.ssafy.com/s07-final/S07P31B309/-/blob/develop/exec/image/ec2%20%EA%B2%80%EC%A7%84.gif) |

### 의료진 접수처 화면

- 의료진 각자의 계정으로 로그인.
- 접수처 화면과 검진실 화면을 따로 구성하여 각자의 기능을 수행함.
- 접수처 화면은 환자 등록과 검진 시작을 통해 수검자 고유의 QR을 문자로 발급.

### 의료진 검진실 화면

- 수검자 고유의 QR을 이용하여 검진을 시작 및 종료을 수행.
- 시작, 종료 수행을 통해 수검자 검진 상태를 SockJS와 Roslib, RosBridge을 이용하여 통신.

<br>

---

|                        절차 안내 화면                        |                     QR 연결 및 자동 문진                     | Tracking 연결 및 안내 시작                                   |
| :----------------------------------------------------------: | :----------------------------------------------------------: | ------------------------------------------------------------ |
| ![깐부 설명](https://user-images.githubusercontent.com/111056719/202899437-090e642d-3d6a-464f-a056-9bdb1821272b.gif) | ![깐부 문진](https://user-images.githubusercontent.com/111056719/202899493-d46fd7cb-0485-45e9-bfe9-22067f00ffa1.gif) | ![깐부 안내2](https://user-images.githubusercontent.com/111056719/202899460-677aa2d0-3137-49a3-8543-f2563b7d2ac2.gif) |

### 절차 안내 화면

- 각각의 검진 절차에 대한 자세한 설명을 제공함.

### QR 연결 및 자동 문진

- `건강검진 시작하기` 버튼을 통해 QR을 이용하여 수검자와 로봇 1:1 연결 수행.
- 연결을 완료하고 STT, TTS을 이용하여 자동 문진 작성 기능을 수행.

### Tracking 연결 및 안내 시작

- `안내시작` 버튼을 통해 OpenCV을 이용하여 로봇과 수검자 사이의 1:1 Tracking 연결.
- 연결을 완료한 후 이동 중 화면으로 이동.
- 이동 중 STT을 이용한 KWS(Keyword Spotting) 기능을 이용하여 `깐부`를 불렀을시 안내를 잠시 멈출 수 있음.

---

|              Pose Estimation를 이용한 스트레칭               |                          Card Game                           |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| ![image](https://user-images.githubusercontent.com/79126498/202899561-95e96a33-14d4-438d-aa22-dd0b5a990ba4.png) | ![카드](https://user-images.githubusercontent.com/111056719/202899518-f8f217e0-cf45-4f87-98a6-b90cf8689b22.gif) |

### Pose Estimation를 이용한 스트레칭

- `Google Teachable Machine`의 `Pose Estimation`을 이용하여 오랜 대기 시간에 지친 몸을 풀 수 있도록 구성.
- 학습시킨 모델의 동작과 웹캠의 수검자 동작의 정확도를 수치로 표현.

### Card Game

- 긴 대기시간에 다양한 컨텐츠를 제공하기 위해 구성.



## 5. 포팅 매뉴얼

### EC2-Backend

1. git clone https://lab.ssafy.com/s07-final/S07P31B309.git
2. cd  ec2-back
3. 경로 : ec2-back/gganbu/src/main/resources/**env.properties 파일 추가**

```yaml
USER_NAME = {db.username}
PASSWORD = {db.pw}
COOLSMS_API_KEY = {coolsms api key}
COOLSMS_SECRET = {coolsms secret key}
COOLSMS_PHONE = {coolsms에서 인증받은 번호}
BACKEND_ADDRESS = k7b309.p.ssafy.io:8081
```

4. Build Project

5. Run 'GganbuApplication'

<br>

### EC2-Frontend

1. cd ec2-front
2. 경로: ec2-front/**.env.local**파일 추가

```yaml
VUE_APP_API_SERVER="https://k7b309.p.ssafy.io/api”
VUE_APP_WEATHER_API_KEY={인증받은 weather api key}
```

3. Project build : `npm install`

4. Project run : `npm run serve`

<br>

### gganbu-Back

1. cd gganbu-back
2. pip `pip install -r requirements.txt`
3. Project run : `uvicorn main:app --reload`

<br>

### gganbu-Front

1. cd gganbu-front
2. 경로: gganbu-front/**.env**파일 추가

```yaml
VUE_APP_WEATHER_API_KEY={인증받은 weather api key}
```

3. Project build : `npm install`

4. Project run : `npm run serve`

## 6. 프로젝트 산출물

- [UCC](https://www.youtube.com/watch?v=Pm1MJZ-jmqU)
- [배포 매뉴얼](https://docs.google.com/document/d/1KU1Ff0xTkcKssYkKuD7X87qqtQCjS9LwLu8ULNQl_bo/edit)

<br>
