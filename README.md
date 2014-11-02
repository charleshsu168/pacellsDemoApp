Pacells App for Android
====================================

<h2>What is Palcells App</h2>
Palcells App is an demo app for Android to monitor Palcell battery pack usages: state of charge, state of health, current, voltages, temperature, 
power, time to full charge, time to empty, energy consumptions. 

It display those information in realtime using the data uploaded from Raspberry Pi to Xively's MQTT server feeds at the 
at  https://xively.com/feeds/577572561


<ul>
<li>Line Charts</li>
<li>Bar Charts</li>
</ul>

Tested on Android 1.6, 2.2, 2.3 and 3.0 (honeycomb, tablet).

<img src="https://github.com/jjoe64/GraphView/raw/master/GVLine.jpg" />
<img src="https://github.com/jjoe64/GraphView/raw/master/GVBar.png" />

<h2>Features</h2>

* Two chart types
Line Chart and Bar Chart.
* Draw multiple series of data
Let the diagram show more that one series in a graph. You can set a color and a description for every series.
* Show legend
A legend can be displayed inline the chart. You can set the width and the vertical align (top, middle, bottom).
* Custom labels
The labels for the x- and y-axis are generated automatically. But you can set your own labels, Strings are possible.
* Handle incomplete data
It's possible to give the data in different frequency.
* Viewport
You can limit the viewport so that only a part of the data will be displayed.
* Scrolling
You can scroll with a finger touch move gesture.
* Scaling / Zooming
Since Android 2.3! With two-fingers touch scale gesture (Multi-touch), the viewport can be changed.
* Background (line graph)
Optionally draws a light background under the diagram stroke.

<h2>How to use</h2>
<a href="http://android-graphview.org">View GraphView page http://android-graphview.org</a>

Very simple example:
<pre>
// init example series data
GraphViewSeries exampleSeries = new GraphViewSeries(new GraphViewData[] {
	      new GraphViewData(1, 2.0d)
	      , new GraphViewData(2, 1.5d)
	      , new GraphViewData(3, 2.5d)
	      , new GraphViewData(4, 1.0d)
});

GraphView graphView = new LineGraphView(
      this // context
      , "GraphViewDemo" // heading
);
graphView.addSeries(exampleSeries); // data

LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
layout.addView(graphView);
</pre>
