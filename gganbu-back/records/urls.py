from records import views
from django.urls import path

app_name='records'

urlpatterns=[
    path('',views.recordSave),
    path('save/', views.recognize_from_microphone),
    path('mail', views.sendMail)
]