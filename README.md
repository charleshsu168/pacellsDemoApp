Pacells App for Android
====================================

<h2>What is Palcells App</h2>
Palcells App is a demo app for Android to monitor Palcell battery pack usages including: state of charge,  current, voltages, temperature, 
power. 

Raspberry Pi's battery script, xbmslog.py, publishes realtime data to Xively MQTT cloud server. 
https://xively.com/feeds/577572561

The Palcells app subscribe to feed at the Xively's MQTT publishing server and display those information in the app. 

To run the battery script, at RPI commandline type: sudo xbmslog 

Future implementations may include: 
Calculate realtime data for state of health, time to full charge, time to empty, and energy consumptions. Draw the realtime data in the graphs and charts, which are currently randomly generated. Implement the app to support multile android devices and screen orientations. 


Tested on Android 4.4 KitKat Nexus 5
<h2>Installation</h2>

Clone this project into the desktop and then import it into Android Studio and build.

You can also just download the pre-compiled android package app-debug.apk file at https://github.com/charleshsu168/pacellsDemoApp/tree/master/app/build/outputs/apk and emailing the file to your android device as attachment to install it. Click accept when it ask permission to install.   


<h2>How to use</h2>

<img src="https://raw.githubusercontent.com/charleshsu168/pacellsDemoApp/master/screenshots/screen1.png" />


<img src="https://raw.githubusercontent.com/charleshsu168/pacellsDemoApp/master/screenshots/screen2.png" />

<img src="https://raw.githubusercontent.com/charleshsu168/pacellsDemoApp/master/screenshots/screen3.png" />



