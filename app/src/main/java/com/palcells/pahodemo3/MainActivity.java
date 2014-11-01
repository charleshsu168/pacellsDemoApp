package com.palcells.pahodemo3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class MainActivity extends ActionBarActivity implements MqttCallback, IMqttActionListener{
    MemoryPersistence mPer;
    MqttAndroidClient client=null;
    //MqttAndroidClient mosquitto = null;
    JSONObject jsonResponse;
    static final String CLIENT = "CLIENT";
    static final String POWER = "POWER";
    static final String CURRENT = "CURRENT";
    static final String SOC = "SOC";
    static final String TEMPERATURE = "TEMPERATURE";
    static final String VOLTAGE = "VOLTAGE";
	MqttConnectOptions options;
	String action;
	Context mainContext;
    ViewPager mViewPager;
    private final Handler mHandler = new Handler();
    //private Runnable mTimer1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 		Toast toast = Toast.makeText(this.getApplicationContext(), "created", Toast.LENGTH_LONG);
 		toast.show();
        }

    /*
    protected void onStart(Bundle savedInstanceState) {
        super.onStart();

    	createClient();
        	try {
				subscribeToXively(client);
			} catch (MqttException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     		Toast toast = Toast.makeText(this.getApplicationContext(), "started", Toast.LENGTH_LONG);
     		toast.show();
    }*/
        
    public void startLineGraph(View view)
    {
        Intent intent = new Intent(this, GraphViewActivity.class);
        startActivity(intent);
    }
    public void startRealTime(View view)
    {
        Intent intent = new Intent(this, RealTime.class);
        startActivity(intent);
    }

    public void createClient()
    {
        options = new MqttConnectOptions();
    	options.setConnectionTimeout(0);
    	options.setKeepAliveInterval(0);
		options.setUserName("yP6mirzrHR6fGiLEeZ26dqOk1EiQUpBDjoV2BKahAJSXqNbY");
		options.setCleanSession(false);
        mPer= new MemoryPersistence();
        //int duration = Toast.LENGTH_LONG;
        mainContext=this.getApplicationContext();
		client = new MqttAndroidClient(this.getApplicationContext(), "tcp://api.xively.com:1883", MqttAsyncClient.generateClientId(), mPer );
		client.setCallback((MqttCallback)this);
    	
    }
    public void PublishXively(   final MqttAndroidClient client,   final String data) throws MqttException {
    	//final MqttMessage publishmessage = new MqttMessage(data.getBytes());
    	//XivelyPublisher publisher = new XivelyPublisher(client, data, this, options);
    	//publisher.publish();
    	
    	//final IMqttActionListener callback = (IMqttActionListener)this;
    	try{
    		client.connect(options, mainContext, new  IMqttActionListener (){
    			@Override
    			public void onFailure(IMqttToken arg0, Throwable arg1) {}			
    			@Override
    			public void onSuccess(IMqttToken arg0) {
    			
    				try {
				 		Toast toast = Toast.makeText(mainContext, "publishing", Toast.LENGTH_LONG);
				 		toast.show();
						client.publish("yP6mirzrHR6fGiLEeZ26dqOk1EiQUpBDjoV2BKahAJSXqNbY/v2/feeds/577572561/datastreams/Switch.csv", data.getBytes(), 0, 
								true, mainContext, new  IMqttActionListener (){
							@Override
							public void onFailure(IMqttToken arg0, Throwable arg1) {
						 		Toast toast = Toast.makeText(mainContext, "failed", Toast.LENGTH_LONG);
						 		toast.show();
							}			
							@Override
							public void onSuccess(IMqttToken arg0) {
						 		Toast toast = Toast.makeText(mainContext, "published", Toast.LENGTH_LONG);
						 		toast.show();}
						});
					} catch (MqttPersistenceException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MqttException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			}});
    	}
    	catch (MqttException e)
    	{
    		
			e.printStackTrace();

    	}
    } 

    public void subscribeToXively( final MqttAndroidClient client) throws MqttException {
    	
    	try{
    		client.connect(options, mainContext, new  IMqttActionListener (){
    			@Override
    			public void onFailure(IMqttToken arg0, Throwable arg1) {}			
    			@Override
    			public void onSuccess(IMqttToken arg0) {
    			
    		    	try{

    		        	client.subscribe("yP6mirzrHR6fGiLEeZ26dqOk1EiQUpBDjoV2BKahAJSXqNbY/v2/feeds/577572561", 0, mainContext, new  IMqttActionListener (){
    		    			@Override
    		    			public void onFailure(IMqttToken arg0, Throwable arg1) {
    		    				// TODO Auto-generated method stub
    		    			}
    		    			@Override
    		    			public void onSuccess(IMqttToken arg0) {
    		    				// TODO Auto-generated method stub

    		    		     		Toast toast = Toast.makeText(mainContext, "subscribed", Toast.LENGTH_LONG);
    		    		     		toast.show();
    		    			}});}
    		        	catch( MqttException e)
    		        	{
    		    			e.printStackTrace();
    		        	}
    			}}
    		);
    	}
    	catch (MqttException e)
    	{
    		
			e.printStackTrace();

    	}
    	
    	

    }
    @Override
    public void onStart() {
    	super.onStart();
 		Toast toast = Toast.makeText(this.getApplicationContext(), "started", Toast.LENGTH_LONG);
 		toast.show();

    }
    
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        createClient();
    	try {
    		subscribeToXively(client);
    	} catch (MqttException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
 		Toast toast = Toast.makeText(this.getApplicationContext(), "resumed", Toast.LENGTH_LONG);
 		toast.show();


    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub
		//int duration = Toast.LENGTH_LONG;
 		Toast toast = Toast.makeText(mainContext, "published", Toast.LENGTH_LONG);
 		toast.show();
		
	}
	@Override
	public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
		// TODO Auto-generated method stub
		Context context = getApplicationContext();
		//CharSequence text = arg1.toString();
		//int duration = Toast.LENGTH_LONG;
		
		//Toast toast = Toast.makeText(this.getApplicationContext(), "messeage arrived from xively"+arg1.toString(), Toast.LENGTH_LONG);
		//toast.show();
		
		/*Toast toast = Toast.makeText(context, text, duration);
		toast.show();
		System.out.println(arg0+arg1.toString());
		Log.i("Xively", arg1.toString() );*/
		readData(arg1);
		//Log.i("Xively", arg1.toString() );		
	}
	public void onToggleClick(View view) throws MqttPersistenceException, MqttException {
	    if(((ToggleButton) view).isChecked()) {
	    	TimeZone tz = TimeZone.getTimeZone("UTC");
	    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
	    	df.setTimeZone(tz);
	    	String nowAsISO = df.format(new Date());
	        // handle toggle on
	    	//String data ="{\"id\": \"Switch\",  \"tags\": [    \"on or off\"  ],  \"unit\": {\"type\": null,\"symbol\": null,    \"label\": null},  \"current_value\": \"Offfqwrqfaqeeadeadf45\"}";
			String data1 ="2014-10-15T21:23:03.629335Z, 1";
			String data2 = nowAsISO+",1";
			PublishXively(client, data2);

	    } else {
	       // handle toggle off
	    	TimeZone tz1 = TimeZone.getTimeZone("UTC");
	    	DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
	    	df1.setTimeZone(tz1);
	    	String nowAsISO1 = df1.format(new Date());
	    	String data3= nowAsISO1+",0";
	    	String offdata ="{\"id\": \"Switch\",  \"tags\": [    \"on or off\"  ],  \"unit\": {\"type\": null,\"symbol\": null,    \"label\": null},  \"current_value\": \"Offffaaweadawdwd45\"}";
			String data5 ="2014-10-15T21:23:03.629335Z, 0";
			
			PublishXively(client, data3);

	    }
	}    

	public void readData(MqttMessage arg1 )
	{
        JSONObject jsonResponse;
		//int duration = Toast.LENGTH_LONG;
		//Toast toast = Toast.makeText(this.getApplicationContext(), "reading xively data"+ arg1.toString(), duration);
		//toast.show();
        try {
              
             /****** Creates a new JSONObject with name/value mappings from the JSON string. ********/
             jsonResponse = new JSONObject(arg1.toString());
              
             /***** Returns the value mapped by name if it exists and is a JSONArray. ***/
             /*******  Returns null otherwise.  *******/
             JSONArray jsonMainNode = jsonResponse.optJSONArray("datastreams");
              
             /*********** Process each JSON Node ************/

             int lengthJsonArr = jsonMainNode.length();  

             for(int i=0; i < lengthJsonArr; i++) 
             {
                 /****** Get Object for each JSON node.***********/
                 JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                  
                 /******* Fetch node values *********
                 if ((jsonChildNode.optString("id").toString().equals("Current")))
                 {		
             		//Toast toast1 = Toast.makeText(this.getApplicationContext(), "setting data"+jsonChildNode.optString("current_value"), Toast.LENGTH_LONG);
             		//toast1.show();
                    TextView currentTextView =(TextView) findViewById(R.id.currentTextView);
                	 currentTextView.setText(jsonChildNode.optString("current_value"));
                	 //String song_name   = jsonChildNode.optString("song_name").toString();
                	 //String artist_name = jsonChildNode.optString("artist_name").toString();

                 }                 
                 if (jsonChildNode.optString("id").toString().equals("Power"))
                 {   TextView powerTextView =(TextView) findViewById(R.id.powerTextView);
                	 powerTextView.setText(jsonChildNode.optString("current_value"));
                	 //String song_name   = jsonChildNode.optString("song_name").toString();
                	 //String artist_name = jsonChildNode.optString("artist_name").toString();
                 }*/
                 if (jsonChildNode.optString("id").toString().equals("SOC"))
                 {
                     TextView socTextView = (TextView) findViewById(R.id.socTextView1);
                	 socTextView.setText(jsonChildNode.optString("current_value")+'%');
                	 //String song_name   = jsonChildNode.optString("song_name").toString();
                	 //String artist_name = jsonChildNode.optString("artist_name").toString();
                 }/*
                 if (jsonChildNode.optString("id").toString().equals("Temperature"))
                 {
                	 TextView tempTextView =(TextView) findViewById(R.id.tempTextView);
                	 tempTextView.setText(jsonChildNode.optString("current_value"));
                	 //String song_name   = jsonChildNode.optString("song_name").toString();
                	 //String artist_name = jsonChildNode.optString("artist_name").toString();
                 }
                 if (jsonChildNode.optString("id").toString().equals("Voltage"))
                 {
                	 TextView voltageTextView =(TextView) findViewById(R.id.voltageTextView);
                	 voltageTextView.setText(jsonChildNode.optString("current_value"));
                	 //String song_name   = jsonChildNode.optString("song_name").toString();
                	 //String artist_name = jsonChildNode.optString("artist_name").toString();
                 }
                 if (jsonChildNode.optString("id").toString().equals("Switch"))
                 {
                	 ToggleButton batteryToggleButton =(ToggleButton) findViewById(R.id.batteryToggleButton);
                	 if(jsonChildNode.optString("current_value").equals("1"))
                	 {
                	 batteryToggleButton.setChecked(true);
                	 }
                	 else
                	 {
                    	 batteryToggleButton.setChecked(false);

                	 }
                	 //String song_name   = jsonChildNode.optString("song_name").toString();
                	 //String artist_name = jsonChildNode.optString("artist_name").toString();
                 }*/

                  

            }
              
             /************ Show Output on screen/activity **********/

              
         } catch (JSONException e) {
  
             e.printStackTrace();
         }

     }



	@Override
	public void onFailure(IMqttToken arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onSuccess(IMqttToken arg0) {
		// TODO Auto-generated method stub
 		//Toast toast = Toast.makeText(mainContext, "published", Toast.LENGTH_LONG);
 		//toast.show();
		
		
	}

		
}

