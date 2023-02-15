package cs301.birthdaycake;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;


public class CakeController implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    private CakeModel refModel;
    private CakeView cView;
    public CakeController(CakeView cakeView) {
        cView = cakeView;
        refModel = cakeView.getCakeModel();
    }

    public void onClick(View v) {
        Log.d("Debug","Click Successful");
        refModel.isLit = !refModel.isLit;
        cView.invalidate();
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Log.d("Debug","Click Successful");
        refModel.hasCandles = !refModel.hasCandles;
        cView.invalidate();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        refModel.candleCount = i;
        cView.invalidate();
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
