package srvikram13.fitnfun.view;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import srvikram13.fitnfun.R;

public class GameActivity extends AppCompatActivity implements SensorEventListener, View.OnClickListener{

    private static final String TAG = "DEBUG: GameActivity";
    /**
     * Earth gravity is around 9.8 m/s^2 but user may not completely direct his/her hand vertical
     * during the exercise so we leave some room. Basically if the x-component of gravity, as
     * measured by the Gravity sensor, changes with a variation (delta) > GRAVITY_THRESHOLD,
     * we consider that a successful count.
     */
    private static final float GRAVITY_THRESHOLD = 0.3f;

    /** an up-down movement that takes more than this will not be registered as such **/
    private static final long TIME_THRESHOLD_NS = 2000000000; // in nanoseconds (= 2sec)

    private SensorManager mSensorManager;
    private Sensor gravitySensor, accelerometerSensor;
    private long mLastTime = 0;
    private boolean mUp = false;
    private int mJumpCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Button btnEndGame = (Button) findViewById(R.id.btnEndGame);
        btnEndGame.setOnClickListener(this);

        //mJumpCounter = Utils.getCounterFromPreference(this);
        mJumpCounter = 0;
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        gravitySensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorManager.registerListener(this, gravitySensor,
                SensorManager.SENSOR_DELAY_NORMAL)) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Successfully registered for the gravity sensor updates");
            }
        } else {
            Log.d(TAG, "Unable to register for the gravity sensor updates");
        }

        if (mSensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL)) {
            if (Log.isLoggable(TAG, Log.DEBUG)) {
                Log.d(TAG, "Successfully registered for the accelerometer sensor updates");
            }
        } else {
            Log.d(TAG, "Unable to register for the accelerometerS sensor updates");
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Unregistered for sensor events");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Sensor sensor = event.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            //detectShake(event.values[0], event.values[1], event.values[2], event.timestamp);
            detectShake(event);
        } else if (sensor.getType() == Sensor.TYPE_GRAVITY) {
            float adjust = 0.0f;    // adjustment factor
            float gravity = 9.8f;

            detectJump(event.values[0], event.values[1], event.values[2] - gravity + adjust, event.timestamp);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    ////////////////////////////////////////////////////////////////////////////
    //Reference for Shske detection: http://stackoverflow.com/questions/2317428/android-i-want-to-shake-it

    // Minimum acceleration needed to count as a shake movement
    private static final int MIN_SHAKE_ACCELERATION = 20;

    // Minimum number of movements to register a shake
    private static final int MIN_MOVEMENTS = 4;

    // Maximum time (in milliseconds) for the whole shake to occur
    private static final int MAX_SHAKE_DURATION = 1500;

    // Arrays to store gravity and linear acceleration values
    private float[] mGravity = { 0.0f, 0.0f, 0.0f };
    private float[] mLinearAcceleration = { 0.0f, 0.0f, 0.0f };

    // Start time for the shake detection
    long startTime = 0;

    // Counter for shake movements
    int moveCount = 0;

    private void detectShake(SensorEvent event) {
        // This method will be called when the accelerometer detects a change.

        // Call a helper method that wraps code from the Android developer site
        setCurrentAcceleration(event);

        // Get the max linear acceleration in any direction
        float maxLinearAcceleration = getMaxCurrentLinearAcceleration();
        Log.d(TAG, maxLinearAcceleration +" << Max acceleration");
        // Check if the acceleration is greater than our minimum threshold
        if (maxLinearAcceleration > MIN_SHAKE_ACCELERATION) {
            long now = System.currentTimeMillis();

            // Set the startTime if it was reset to zero
            if (startTime == 0) {
                startTime = now;
            }

            long elapsedTime = now - startTime;

            // Check if we're still in the shake window we defined
            if (elapsedTime > MAX_SHAKE_DURATION) {
                // Too much time has passed. Start over!
                resetShakeDetection();
                Log.d(TAG, "resetShakeDetection()");
            }
            else {
                // Keep track of all the movements
                moveCount++;
                Log.d(TAG, "moveCount: " + moveCount);

                // Check if enough movements have been made to qualify as a shake
                if (moveCount > MIN_MOVEMENTS) {
                    // It's a shake! Notify the listener.
                    Log.d(TAG, "SHAKE!!!, moveCount: " + moveCount);
                    Toast.makeText(this, "Shake Detected!!!", Toast.LENGTH_LONG).show();

                    // Reset for the next one!
                    resetShakeDetection();
                }
            }
        }
    }

    private void setCurrentAcceleration(SensorEvent event) {
        /*
         *  BEGIN SECTION from Android developer site. This code accounts for
         *  gravity using a high-pass filter
         */

        // alpha is calculated as t / (t + dT)
        // with t, the low-pass filter's time-constant
        // and dT, the event delivery rate

        final float alpha = 0.8f;

        // Gravity components of x, y, and z acceleration
        mGravity[0] = alpha * mGravity[0] + (1 - alpha) * event.values[0];
        mGravity[1] = alpha * mGravity[1] + (1 - alpha) * event.values[1];
        mGravity[2] = alpha * mGravity[2] + (1 - alpha) * event.values[2];

        // Linear acceleration along the x, y, and z axes (gravity effects removed)
        mLinearAcceleration[0] = event.values[0] - mGravity[0];
        mLinearAcceleration[1] = event.values[1] - mGravity[1];
        mLinearAcceleration[2] = event.values[2] - mGravity[2];

        /*
         *  END SECTION from Android developer site
         */
    }

    private float getMaxCurrentLinearAcceleration() {
        // Start by setting the value to the x value
        float maxLinearAcceleration = mLinearAcceleration[0];

        // Check if the y value is greater
        if (mLinearAcceleration[1] > maxLinearAcceleration) {
            maxLinearAcceleration = mLinearAcceleration[1];
        }

        // Check if the z value is greater
        if (mLinearAcceleration[2] > maxLinearAcceleration) {
            maxLinearAcceleration = mLinearAcceleration[2];
        }

        // Return the greatest value
        return maxLinearAcceleration;
    }

    private void resetShakeDetection() {
        startTime = 0;
        moveCount = 0;
    }


    //////////////////////////////////////////////////////////
    // Reference for jump detection

    /**
     * A simple algorithm to detect a successful up-down movement of hand(s). The algorithm is
     * based on the assumption that when a person is wearing the watch, the x-component of gravity
     * as measured by the Gravity Sensor is +9.8 when the hand is downward and -9.8 when the hand
     * is upward (signs are reversed if the watch is worn on the right hand). Since the upward or
     * downward may not be completely accurate, we leave some room and instead of 9.8, we use
     * GRAVITY_THRESHOLD. We also consider the up <-> down movement successful if it takes less than
     * TIME_THRESHOLD_NS.
     */
    private void detectJump(float xValue, float yValue, float zValue, long timestamp) {
        //if ((Math.abs(xValue) > GRAVITY_THRESHOLD) || (Math.abs(yValue) > GRAVITY_THRESHOLD) || (Math.abs(zValue) > GRAVITY_THRESHOLD)) {
        // Log.d(TAG, "detectJump(), xValue: " + xValue + "yValue: " + yValue + "zValue: " + zValue);
        //}
        float upThreshold = -9.0f;
        float downThreshold = 0.5f;

        if (!mUp) {
            // check for up
            if (zValue < upThreshold) {
                // going up
                Log.d(TAG, "Going UP");
                mLastTime = timestamp;
                mUp = true;
            }
        } else {
            // check for down
            if (timestamp - mLastTime > TIME_THRESHOLD_NS) {
                // reset - waited too long for down
                Log.d(TAG, "RESET - waited too long for down");
                mUp = false;
            }
            else if ((Math.abs(zValue) < downThreshold)) {
                // going down
                Log.d(TAG, "Going DOWN");
                mUp = false;
                Log.d(TAG, "JUMP!!!");
                Toast.makeText(this, "Jump Detected!!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View view) {
        Intent i;
        switch(view.getId()) {
            case R.id.btnEndGame:
                i = new Intent(this, ScoreCardActivity.class);
                startActivity(i);
                break;
        }
    }

}