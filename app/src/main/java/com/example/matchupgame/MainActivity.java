package com.example.matchupgame;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    ViewFlipper[] viewFlippers = new ViewFlipper[12];
    boolean[] isFrontVisible = new boolean[12];
    boolean[] isAllVisible = new boolean[12];
    int clickcount = 0;

    int firstFliiper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        @SuppressLint("ResourceType") Animation fliptofront = AnimationUtils.loadAnimation(this, R.anim.frontanim);
        @SuppressLint("ResourceType") Animation fliptoback = AnimationUtils.loadAnimation(this, R.anim.backanim);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 12; i++){
                    viewFlippers[i].setInAnimation(fliptoback);
                    viewFlippers[i].showNext();
                }
            }
        },2500);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 12; i++){
                    viewFlippers[i].setInAnimation(fliptofront);
                    viewFlippers[i].showNext();
                }
            }
        },1000);



        for (int i = 0; i < 12; i++) {
            isFrontVisible[i] = true;
            isAllVisible[i] = false;
        }
        viewFlippers[0] = findViewById(R.id.viewflipper1);
        viewFlippers[1] = findViewById(R.id.viewflipper2);
        viewFlippers[2] = findViewById(R.id.viewflipper3);
        viewFlippers[3] = findViewById(R.id.viewflipper4);
        viewFlippers[4] = findViewById(R.id.viewflipper5);
        viewFlippers[5] = findViewById(R.id.viewflipper6);
        viewFlippers[6] = findViewById(R.id.viewflipper7);
        viewFlippers[7] = findViewById(R.id.viewflipper8);
        viewFlippers[8] = findViewById(R.id.viewflipper9);
        viewFlippers[9] = findViewById(R.id.viewflipper10);
        viewFlippers[10] = findViewById(R.id.viewflipper11);
        viewFlippers[11] = findViewById(R.id.viewflipper12);

        for (int i = 0; i < 12; i++) {
            int finalI = i;

            viewFlippers[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flipCard(finalI);
                }
            });
        }
    }


    private void flipCard(int index) {
        @SuppressLint("ResourceType") Animation fliptofront = AnimationUtils.loadAnimation(this, R.anim.frontanim);
        @SuppressLint("ResourceType") Animation fliptoback = AnimationUtils.loadAnimation(this, R.anim.backanim);
        clickcount++;
       //Toast.makeText(this, "index " + index, Toast.LENGTH_SHORT).show();
        if (isFrontVisible[index]) {
            viewFlippers[index].setInAnimation(fliptoback);
            viewFlippers[index].showNext();
            isFrontVisible[index] = false;

            if(clickcount == 1){
                firstFliiper = index;

               //Toast.makeText(this, "firstflipper " + firstFliiper, Toast.LENGTH_SHORT).show();
                //Toast.makeText(this, "index " + index, Toast.LENGTH_SHORT).show();
            }else if(clickcount == 2){
               // Toast.makeText(this, "2:  " + isFrontVisible[2], Toast.LENGTH_SHORT).show();
                 //Toast.makeText(this, "9:  " + isFrontVisible[9], Toast.LENGTH_SHORT).show();
                    if((isFrontVisible[0] == false && isFrontVisible[7] == false) ||
                            (isFrontVisible[1] == false && isFrontVisible[10] == false) ||
                            (isFrontVisible[2] == false && isFrontVisible[6] == false) ||
                            (isFrontVisible[3] == false && isFrontVisible[8] == false) ||
                            (isFrontVisible[4] == false && isFrontVisible[9] == false) ||
                            (isFrontVisible[5] == false && isFrontVisible[11] == false)){
                        isFrontVisible[index] = true;
                        isFrontVisible[firstFliiper] = true;
                        viewFlippers[index].setOnClickListener(null);
                        viewFlippers[firstFliiper].setOnClickListener(null);
                        isAllVisible[index] = true;
                        isAllVisible[firstFliiper]=true;
                        if (allCardsFlipped()) {
                            congrats();
                        }
                        clickcount=0;
                    } else{
                       // Toast.makeText(this, "index: " + index, Toast.LENGTH_SHORT).show();

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                viewFlippers[index].setInAnimation(fliptofront);
                                viewFlippers[index].showPrevious();

                                viewFlippers[firstFliiper].setInAnimation(fliptofront);
                                viewFlippers[firstFliiper].showPrevious();

                                isFrontVisible[index] = true;
                                isFrontVisible[firstFliiper] = true;
                            }
                        }, 1000);
                        clickcount=0;
                    }

             }
        }

    }

    private boolean allCardsFlipped() {
        for (boolean visible : isAllVisible) {
            if (!visible) {
                return false;
            }
        }
        return true;
    }

    private void congrats() {
        new AlertDialog.Builder(this)
                .setTitle("Congratulations!")
                .setMessage("All cards are flipped to the back!")
                .setPositiveButton("OK", null)
                .show();
    }

/*
    public void flipcard(int i) {
        @SuppressLint("ResourceType") Animation fliptofront = AnimationUtils.loadAnimation(this, R.anim.frontanim);
        @SuppressLint("ResourceType") Animation fliptoback = AnimationUtils.loadAnimation(this, R.anim.backanim);

        if (frontvisile) {
            viewFlipper.setInAnimation(fliptoback);
           // viewFlipper.setOutAnimation(fliptofront);
            viewFlipper.showNext();

        } else {
            viewFlipper.setInAnimation(fliptofront);
           // viewFlipper.setOutAnimation(fliptoback);
            viewFlipper.showPrevious();
        }
        frontvisile = !frontvisile;
    }
 */

}
