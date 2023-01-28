package com.coder.soundPoolAndDialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.animation.Animation;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.coder.sample.R;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
public class MainActivity extends AppCompatActivity {

    Button callDialog;
    Dialog customDialog;
    SoundPool soundPool;
    private int clickSound;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        customDialog = new Dialog(MainActivity.this);
        customDialog.setCancelable(false);
        callDialog = (Button) findViewById(R.id.customDialogBtn);

        soundPoolLoader();
        loadAnimations();

        callDialog.setOnClickListener(v -> {
            soundPool.play(clickSound,1,1,0,0,1);
            callCustomDialog();
        });

    }


    private void loadAnimations() {
        YoYo.with(Techniques.Bounce)
                .duration(700)
                .repeat(Animation.INFINITE)
                .playOn(callDialog);
    }

    private void soundPoolLoader() {
        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder()
                .setMaxStreams(1)
                .setAudioAttributes(audioAttributes)
                .build();

        clickSound = soundPool.load(this,R.raw.click,1);

    }

    @Override
    protected void onStop() {
        customDialog.dismiss();
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        soundPool.play(clickSound,1,1,0,0,1);
        super.onBackPressed();
    }

    private void callCustomDialog() {
        customDialog.setContentView(R.layout.custom_dialog_ui);
        Button yesBtn = customDialog.findViewById(R.id.yesBtn);
        Button noBtn = customDialog.findViewById(R.id.noBtn);

        YoYo.with(Techniques.Pulse)
                .duration(700)
                .repeat(Animation.INFINITE)
                .playOn(noBtn);

        customDialog.show();

        yesBtn.setOnClickListener(v -> {
            soundPool.play(clickSound,1,1,0,0,1);
            finish();
        });

        noBtn.setOnClickListener(v -> {
            soundPool.play(clickSound,1,1,0,0,1);
            customDialog.dismiss();
        });

    }

}