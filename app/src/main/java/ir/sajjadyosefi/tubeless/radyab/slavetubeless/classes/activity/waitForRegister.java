package ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.utility.RandomUtil;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;

public class waitForRegister extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait_for_register);

        RandomUtil random = new RandomUtil();
        Long generateCode = random.generateRandom(5);

        TextView textView = (TextView) findViewById(R.id.textViewRandomCode);
        textView.setText(generateCode.toString());

    }
}
