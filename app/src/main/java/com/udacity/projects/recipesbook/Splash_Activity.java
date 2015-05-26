package com.udacity.projects.recipesbook;


import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Splash_Activity extends Activity {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        window.setFormat(PixelFormat.RGBA_8888);
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_splash);
     
        StartAnimations();
        StartRotate();
        StartTranslate();

        /****** Create Thread that will sleep for 3 seconds *************/
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 3 seconds
                    sleep(3*1000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),Main_Activity.class);
                    startActivity(i);

                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();

    }

    /*********************Animation**********************/
    private void StartAnimations() {
      Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        LinearLayout l=(LinearLayout) findViewById(R.id.lin_lay);
        l.clearAnimation();
        l.startAnimation(anim);




        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        findViewById(R.id.ic).startAnimation(rotate);

        rotate.reset();
        rotate.start();



    }

    /************ Translation of the text***********************/
    public  void StartTranslate(){

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        TextView ivt = (TextView) findViewById(R.id.textNameapp);
        ivt.clearAnimation();
        ivt.startAnimation(anim);

    }
    /********************Rotation of icon*********************/
    public  void StartRotate(){

        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        findViewById(R.id.ic).startAnimation(rotate);
        rotate.reset();
        rotate.start();

    }

}