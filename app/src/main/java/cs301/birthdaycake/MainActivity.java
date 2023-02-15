package cs301.birthdaycake;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.util.Log;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main);

        CakeView cakeView = findViewById(R.id.cakeview);
        CakeController cControl = new CakeController(cakeView);
        Switch candlesSwitch = findViewById(R.id.switch3);
        Button blowOut = findViewById(R.id.button);
        SeekBar candleCount = findViewById(R.id.seekBar2);

        candlesSwitch.setOnCheckedChangeListener(cControl);
        blowOut.setOnClickListener(cControl);
        candleCount.setOnSeekBarChangeListener(cControl);
    }


    public void goodbye(View button) {
        Log.i("button","Goodbye");
    }
}
