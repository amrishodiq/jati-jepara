package me.shodiq.jatijepara.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import me.shodiq.jatijepara.R;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initComponents();
    }

    private void initComponents() {
        findViewById(R.id.debug).setOnClickListener(onClickListener);
        findViewById(R.id.info).setOnClickListener(onClickListener);
        findViewById(R.id.error).setOnClickListener(onClickListener);
        findViewById(R.id.error_exception).setOnClickListener(onClickListener);
        findViewById(R.id.error_unhandled).setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.debug:
                    Timber.d("Try to simmulate debug.");
                    break;
                case R.id.info:
                    Timber.i("Try to simmulate info.");
                    break;
                case R.id.error:
                    Timber.e("Try to simmulate error.");
                    break;
                case R.id.error_exception:
                    Timber.e(new NullPointerException("A component has not been initialized"),
                            "Try to simmulate error with exception.");
                    break;
                case R.id.error_unhandled:
                    throw new RuntimeException("This will trigger unhandled exception! Beware!!");
            }
        }
    };
}
