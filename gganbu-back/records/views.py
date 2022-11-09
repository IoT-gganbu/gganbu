from django.shortcuts import render
from rest_framework import status
from rest_framework.decorators import api_view
from rest_framework.response import Response
import os
import azure.cognitiveservices.speech as speechsdk
import smtplib
from email.message import EmailMessage
# Create your views here.
@api_view(['GET'])
def recordSave(request) :
    print("hihi")
    data = "hihi"
    return Response(data)

@api_view(['GET'])
def recognize_from_microphone(request):
    # This example requires environment variables named "SPEECH_KEY" and "SPEECH_REGION"
    speech_config = speechsdk.SpeechConfig(subscription=os.environ.get('SPEECH_KEY'), region=os.environ.get('SPEECH_REGION'))
    speech_config.speech_recognition_language="ko-KR"

    audio_config = speechsdk.audio.AudioConfig(use_default_microphone=True)
    speech_recognizer = speechsdk.SpeechRecognizer(speech_config=speech_config, audio_config=audio_config)

    print("Speak into your microphone.")
    speech_recognition_result = speech_recognizer.recognize_once_async().get()

    if speech_recognition_result.reason == speechsdk.ResultReason.RecognizedSpeech:
        print("Recognized: {}".format(speech_recognition_result.text))
        return Response(speech_recognition_result.text)
    elif speech_recognition_result.reason == speechsdk.ResultReason.NoMatch:
        print("No speech could be recognized: {}".format(speech_recognition_result.no_match_details))
        return Response("No speech could be recognized")
    elif speech_recognition_result.reason == speechsdk.ResultReason.Canceled:
        cancellation_details = speech_recognition_result.cancellation_details
        print("Speech Recognition canceled: {}".format(cancellation_details.reason))
        
        if cancellation_details.reason == speechsdk.CancellationReason.Error:
            print("Error details: {}".format(cancellation_details.error_details))
            print("Did you set the speech resource key and region values?")
            return Response("Error details: {}".format(cancellation_details.error_details))
    return Response("Speech Recognition canceled: {}".format(cancellation_details.reason))

@api_view(['POST'])
def sendMail(request) :
    # STMP 서버의 url과 port 번호
    SMTP_SERVER = 'smtp.gmail.com'
    SMTP_PORT = 465

    # 1. SMTP 서버 연결
    smtp = smtplib.SMTP_SSL(SMTP_SERVER, SMTP_PORT)

    EMAIL_ADDR = 'ssabeulans@gmail.com'
    EMAIL_PASSWORD = 'ymqqpiidgyefkfjq'

    # 2. SMTP 서버에 로그인
    smtp.login(EMAIL_ADDR, EMAIL_PASSWORD)

    contents = request.data.getlist('data[]')
    print(len(contents))
    print(contents[0]+" "+ contents[1])
    # 3. MIME 형태의 이메일 메세지 작성
    message = EmailMessage()
    message.set_content('인플루엔자 예방접종을 매년 하십니까?\n'+contents[0]+"\n지금까지 평생 다섯갑 이상의 담배를 피운 적이 있습니까?\n"
                        + contents[1]+"\n한 달에 몇번 음주를 하십니까?\n"+ contents[2]+ "\n부모, 형제, 자매 중에 당뇨 질환을 앓은 경우가 있습니까?\n"+contents[3])
    message["Subject"] = "싸브란스 병원 문진 결과"
    message["From"] = EMAIL_ADDR  #보내는 사람의 이메일 계정
    message["To"] = 'chann585862@naver.com'

    # 4. 서버로 메일 보내기
    smtp.send_message(message)

    # 5. 메일을 보내면 서버와의 연결 끊기
    smtp.quit()
    
    return Response("SUCCESS")

            
    
