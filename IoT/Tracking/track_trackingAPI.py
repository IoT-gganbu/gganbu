# Tracker APIs (track_trackingAPI.py)

import cv2
import imutils
# 트랙커 객체 생성자 함수 리스트 ---①
trackers = [cv2.legacy.TrackerBoosting_create,
#             cv2.TrackerMIL_create,
            cv2.TrackerKCF_create,
            cv2.legacy.TrackerTLD_create,
            cv2.legacy.TrackerMedianFlow_create,
#             cv2.TrackerCSRT_create,
#             cv2.TrackerGOTURN_create,
            cv2.legacy.TrackerMOSSE_create]
trackerIdx = 0  # 트랙커 생성자 함수 선택 인덱스
tracker = None
isFirst = True

video_src = 0 # 비디오 파일과 카메라 선택 ---②
# video_src = "../img/highway.mp4"
cap = cv2.VideoCapture(video_src)
fps = cap.get(cv2.CAP_PROP_FPS) # 프레임 수 구하기
delay = int(1000/fps)
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
    else:
        
        ok, bbox = tracker.update(frame)   # 새로운 프레임에서 추적 위치 찾기 ---③
        (x,y,w,h) = bbox
        if ok: # 추적 성공
            cv2.rectangle(img_draw, (int(x), int(y)), (int(x + w), int(y + h)), \
                          (0,255,0), 2, 1)
        else : # 추적 실패
            cv2.putText(img_draw, "Tracking fail.", (100,80), \
                        cv2.FONT_HERSHEY_SIMPLEX, 0.75,(0,0,255),2,cv2.LINE_AA)
    trackerName = tracker.__class__.__name__
    # cv2.putText(img_draw, str(trackerIdx) + ":"+trackerName , (100,20), \
    #              cv2.FONT_HERSHEY_SIMPLEX, 0.75, (0,255,0),2,cv2.LINE_AA)

    # cv2.imshow(win_name, img_draw)
    key = cv2.waitKey(delay) & 0xff
    # 스페이스 바 또는 비디오 파일 최초 실행 ---④
    # if key == ord(' ') or (video_src != 0 and isFirst): 
    if isFirst: 

        isFirst = False
        roi = cv2.selectROI()  # 초기 객체 위치 설정
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