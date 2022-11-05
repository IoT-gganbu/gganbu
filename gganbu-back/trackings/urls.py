from trackings import views
from django.urls import path

app_name='trackings'

urlpatterns=[
    path('',views.tracking),
    
]