package ir.sajjadyosefi.tubeless.radyab.slavetubeless.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ir.sajjadyosefi.tubeless.radyab.slavetubeless.R;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.databaseLayout.DatabaseUtils;
import ir.sajjadyosefi.tubeless.radyab.slavetubeless.classes.networkLayout.asyncTask.replyAsyncTask;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mContext = this;

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        (findViewById(R.id.buttonReset)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseUtils.clearMasterSetting(mContext);
                Toast.makeText(mContext,"OK" ,Toast.LENGTH_LONG).show();
                Intent intent = new Intent(mContext,waitForRegister.class);
                mContext.startActivity(intent);
                finish();
            }
        });
    }

}

