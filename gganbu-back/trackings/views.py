from django.shortcuts import render
import os
import cv2
import imutils
from matplotlib import pyplot as plt
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response

@api_view(['GET'])
def tracking(request):
    
    casacade = cv2.CascadeClassifier("data/haarcascade_upperbody.xml")

    video_capture = cv2.VideoCapture(0)

    # video_width = video_capture.get(3)
    # video_height = video_capture.get(4)
    img_counter = 0
    while True:
        ret, frame = video_capture.read()

        # frame = imutils.resize(frame, width=1000) # resize original video for better viewing performance
        frame = imutils.resize(frame, width=1000)
        gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) # convert video to grayscale
        rgb = cv2.cvtColor(frame, cv2.COLOR_BGR2RGB)

        box = casacade.detectMultiScale(
            gray,
            # rgb,
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

    # 캡쳐한 사진 삭제 함수
    # def DeleteFiles(filePath):
    #     if os.path.exists(filePath):
    #         for file in os.scandir(filePath):
    #             os.remove(file.path)
    #         return 'remove_files'
    #     else:
    #         return 'Directory Not Found'

    # DeleteFiles('frame_data')

    # 여기부터는 이미 캡쳐한거에서 상반신 박스그리기
    # Opening image
    img = cv2.imread("frame_data/opencv_frame_0.png")
    
    img_gray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
    img_rgb = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    
    found = casacade.detectMultiScale(
            # gray,
            rgb,
            scaleFactor = 1.1,
            minNeighbors = 3,
            minSize = (200, 200), # Min size for valid detection, changes according to video size or body size in the video.
            flags = cv2.CASCADE_SCALE_IMAGE
        )
    
    amount_found = len(found)
    
    if amount_found != 0:
        found_cnt = 0
        for (x, y, width, height) in found:
            found_cnt += 1
            cv2.rectangle(img_rgb, (x, y), (x + height, y + width), (0, 255, 0), 1)
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

    # 트랙커 객체 생성자 함수 리스트 ---①
    trackers = [
                # cv2.legacy.TrackerBoosting_create,
                # cv2.legacy.TrackerMIL_create,
                cv2.legacy.TrackerKCF_create, # 가장 성능이 좋다고 판단하여 이 모델만 사용
                # cv2.legacy.TrackerTLD_create,
                # cv2.legacy.TrackerMedianFlow_create,
                # cv2.legacy.TrackerCSRT_create,
                # cv2.legacy.TrackerMOSSE_create
                ]
    trackerIdx = 0  # 트랙커 생성자 함수 선택 인덱스(우리한테는 필요가 없다.)
    tracker = None
    isFirst = True

    video_src = 0 # 비디오 파일과 카메라 선택 ---②
    cap = cv2.VideoCapture(video_src)
    fps = cap.get(cv2.CAP_PROP_FPS) # 프레임 수 구하기
    delay = int(1000/fps)
    win_name = 'Tracking APIs'
    returnState = 0 # 장고 리턴값을 유형별로 분기해서 나누어 주어야 한다.(while문이 끝나고 각각의 상태별로 리턴값을 보내줘야한다. ex)1-트래킹중 대상물체 인식실패가 3초 넘은 경우 )
    failCount = 0 
    while cap.isOpened():
        ret, frame = cap.read()
        frame = imutils.resize(frame, width=1000)
        if not ret:
            print('Cannot read video file')
            break
        img_draw = frame.copy()
        if tracker is None: # 트랙커 생성 안된 경우
            tracker = trackers[trackerIdx]()
        else:
            ok, bbox = tracker.update(frame)   # 새로운 프레임에서 추적 위치 찾기 ---③
            (x,y,w,h) = bbox
            if ok: # 추적 성공
                cv2.rectangle(img_draw, (int(x), int(y)), (int(x + w), int(y + h)), \
                            (0,255,0), 2, 1)
                print("추적성공")
                failCount = 0
            else : # 추적 실패
                cv2.putText(img_draw, "Tracking fail.", (100,80), \
                            cv2.FONT_HERSHEY_SIMPLEX, 0.75,(0,0,255),2,cv2.LINE_AA)
                print("추적 실패")
                failCount += 1
                if(failCount >= 60):
                    returnState = 1
                    break
        trackerName = tracker.__class__.__name__
        cv2.putText(img_draw, str(trackerIdx) + ":"+trackerName , (100,20), \
                    cv2.FONT_HERSHEY_SIMPLEX, 0.75, (0,255,0),2,cv2.LINE_AA)
        cv2.imshow(win_name, img_draw)
        
        key = cv2.waitKey(delay) & 0xff
        if isFirst: 
            isFirst = False
            roi = boxPos  # 초기 객체 위치 설정
            # 이 부분에 ROI를 상반신 탐지한 박스로 넣어줘야한다.
            if roi[2] and roi[3]:         # 위치 설정 값 있는 경우
                tracker = trackers[trackerIdx]()    #트랙커 객체 생성 ---⑤
                isInit = tracker.init(frame, roi)
        elif key in range(48, 56): # 0~7 숫자 입력   ---⑥
            trackerIdx = key-48     # 선택한 숫자로 트랙커 인덱스 수정
            if bbox is not None:
                tracker = trackers[trackerIdx]() # 선택한 숫자의 트랙커 객체 생성 ---⑦
                isInit = tracker.init(frame, bbox) # 이전 추적 위치로 추적 위치 초기화
        elif key == 27 : 
            break
    else:
        print( "Could not open video")
    cap.release()
    cv2.destroyAllWindows()
    if(returnState == 1):
        return Response(False)