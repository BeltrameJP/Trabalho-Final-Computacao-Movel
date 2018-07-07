package br.ufop.beltramejp.meubebeconforto;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;

public class InUterusSound extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ImageButton playButton, pauseButton, replayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_uterus_sound);

        mediaPlayer = MediaPlayer.create(this, R.raw.utero_sound);

        playButton = findViewById(R.id.imageButtonPlay);
        pauseButton = findViewById(R.id.imageButtonPause);
        replayButton = findViewById(R.id.imageButtonReplay);

    }
}
