#-*- coding: utf-8 -*-
#-*- coding: euc-kr -*-

import os
import cv2
import imutils
from matplotlib import pyplot as plt

# haar_upper_body_cascade = cv2.CascadeClassifier("data/haarcascade_upperbody.xml")
casacade = cv2.CascadeClassifier("data/haarcascade_upperbody.xml")
# casacade = cv2.CascadeClassifier("data/haarcascade_frontalface_default.xml")

# Uncomment this for real-time webcam detection
# If you have more than one webcam & your 1st/original webcam is occupied,
# you may increase the parameter to 1 or respectively to detect with other webcams, depending on which one you wanna use.

video_capture = cv2.VideoCapture(0)

# For real-time sample video detection
# video_capture = cv2.VideoCapture("subway.mp4")
video_width = video_capture.get(3)
video_height = video_capture.get(4)
img_counter = 0
while True:
    ret, frame = video_capture.read()

    # frame = imutils.resize(frame, width=1000) # resize original video for better viewing performance
    frame = imutils.resize(frame, width=1000)
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) # convert video to grayscale
    rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

    box = casacade.detectMultiScale(
        # gray,
        rgb,
        scaleFactor = 1.1,
        minNeighbors = 3,
        minSize = (200, 200), # Min size for valid detection, changes according to video size or body size in the video.
        flags = cv2.CASCADE_SCALE_IMAGE
    )

    # Draw a rectangle around the upper bodies
    for (x, y, w, h) in box:
        cv2.rectangle(frame, (x, y), (x + w, y + h), (0, 255, 0), 1) # creates green color rectangle with a thickness size of 1
        cv2.putText(frame, "Object Detected", (x + 5, y + 15), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (0, 255, 0), 2) # creates green color text with text size of 0.5 & thickness size of 2
        print(x, y, w, h)
        img_name = 'frame_data/opencv_frame_{}.png'.format(img_counter)
        cv2.imwrite(img_name, frame)
        img_counter += 1

    if img_counter > 0:
        break

    cv2.imshow('Video', frame) # Display video
    # stop script when "q" key is pressed
    if cv2.waitKey(1) & 0xFF == ord('q'):
        break

# Release capture
video_capture.release()
cv2.destroyAllWindows()

def DeleteFiles(filePath):
    if os.path.exists(filePath):
        for file in os.scandir(filePath):
            os.remove(file.path)
        return 'removw_files'
    else:
        return 'Directory Not Found'

# DeleteFiles('frame_data')

# 여기부터는 이미 캡쳐한거에서 상반신 박스그리기

# Opening image
img = cv2.imread("frame_data/opencv_frame_0.png")
  
# OpenCV opens images as BRG 
# but we want it as RGB We'll 
# also need a grayscale version
img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
  
  
# Use minSize because for not 
# bothering with extra-small 
# dots that would look like STOP signs

# stop_data = cv2.CascadeClassifier('stop_data.xml')
stop_data = cv2.CascadeClassifier("data/haarcascade_upperbody.xml")
# casacade = cv2.CascadeClassifier("data/haarcascade_upperbody.xml")
  
# found = stop_data.detectMultiScale(img_rgb, 
#                                    minSize =(20, 20))

found = casacade.detectMultiScale(
        # gray,
        rgb,
        scaleFactor = 1.1,
        minNeighbors = 3,
        minSize = (200, 200), # Min size for valid detection, changes according to video size or body size in the video.
        flags = cv2.CASCADE_SCALE_IMAGE
    )
  
# Don't do anything if there's 
# no sign
amount_found = len(found)
  
if amount_found != 0:
      
    # There may be more than one
    # sign in the image
    found_cnt = 0
    for (x, y, width, height) in found:
        found_cnt += 1
        # We draw a green rectangle around
        # every recognized sign
        cv2.rectangle(img_rgb, (x, y), 
                      (x + height, y + width), 
                      (0, 255, 0), 5)
        
        #  첫번째 박스만 가져오기
        #  이부분에서 여러 박스가 있을때 어떤걸 선택해야하는지 고민해봐야 한다.
        if found_cnt > 1:
            break
        else:
            boxPos = (x, y, width, height)
            break

else:
    # 여기에서 roi를 못찾았다면 다시 화면 캡쳐하는 곳으로 돌아가기
    # 
    # 
    print('ROI 못찾겠다')

# Creates the environment of 
# the picture and shows it
# plt.subplot(1, 1, 1)
# plt.imshow(img_rgb)
# plt.show()


# 트랙커 객체 생성자 함수 리스트 ---①
trackers = [cv2.Tracker_create('TLD'),
            cv2.Tracker_create('BOOSTING'),            
            cv2.Tracker_create('MIL'),
            cv2.Tracker_create('KCF'),
            cv2.Tracker_create('MEDIANFLOW'),
            cv2.Tracker_create('GOTURN'),
            cv2.Tracker_create('MOSSE'),
            ]
trackerIdx = 0  # 트랙커 생성자 함수 선택 인덱스
tracker = None
isFirst = True

video_src = 0 # 비디오 파일과 카메라 선택 ---②
cap = cv2.VideoCapture(video_src)
# print(cap.isOpened())
fps = cap.get(cv2.CAP_PROP_FPS) # 프레임 수 구하기 - fps를 구할 수 없어서 임의로 지연시간 설정해줬음
# print(fps)
# delay = int(1000/fps)
win_name = 'Tracking APIs'
while cap.isOpened():
    ret, frame = cap.read()
    frame = imutils.resize(frame, width=1000)
    if not ret:
        print('Cannot read video file')
        break
    img_draw = frame.copy()
    if tracker is None: # 트랙커 생성 안된 경우
        # cv2.putText(img_draw, "Press the Space to set ROI!!", \
        #     (100,80), cv2.FONT_HERSHEY_SIMPLEX, 0.75,(0,0,255),2,cv2.LINE_AA)
        print("멸망")
        tracker = trackers[trackerIdx]
    else:
        
        ok, bbox = tracker.update(frame)   # 새로운 프레임에서 추적 위치 찾기 ---③
        (x,y,w,h) = bbox
        if ok: # 추적 성공
            cv2.rectangle(img_draw, (int(x), int(y)), (int(x + w), int(y + h)), \
                          (0,255,0), 2, 1)
            print("success")
        else : # 추적 실패
            cv2.putText(img_draw, "Tracking fail.", (100,80), \
                        cv2.FONT_HERSHEY_SIMPLEX, 0.75,(0,0,255),2,cv2.LINE_AA)
            print("fail")
    trackerNames = ['TLD',
            'BOOSTING',
            'MIL',
            'KCF',
            'MEDIANFLOW',
            'CSRT', 
            'MOSSE',
            ]
    trackerName = trackerNames[trackerIdx]
    cv2.putText(img_draw, str(trackerIdx) + ":"+trackerName , (100,20), \
                 cv2.FONT_HERSHEY_SIMPLEX, 0.75, (0,255,0),2,cv2.LINE_AA)

    cv2.imshow(win_name, img_draw)
    key = cv2.waitKey(60) & 0xff
    # 스페이스 바 또는 비디오 파일 최초 실행 ---④
    # if key == ord(' ') or (video_src != 0 and isFirst): 
    if isFirst: 

        isFirst = False
        roi = boxPos  # 초기 객체 위치 설정
        # 이 부분에 ROI를 상반신 탐지한 박스로 넣어줘야한다.
        if roi[2] and roi[3]:         # 위치 설정 값 있는 경우
            tracker = trackers[trackerIdx]    #트랙커 객체 생성 ---⑤
            isInit = tracker.init(frame, roi)
    elif key in range(48, 56): # 0~7 숫자 입력   ---⑥
        trackerIdx = key-48     # 선택한 숫자로 트랙커 인덱스 수정
        if bbox is not None:
            tracker = trackers[trackerIdx] # 선택한 숫자의 트랙커 객체 생성 ---⑦
            isInit = tracker.init(frame, bbox) # 이전 추적 위치로 추적 위치 초기화
    elif key == 27 : 
        break
else:
    print( "Could not open video")
cap.release()
cv2.destroyAllWindows()