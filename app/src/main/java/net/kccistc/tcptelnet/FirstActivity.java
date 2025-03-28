package net.kccistc.tcptelnet;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;



public class FirstActivity extends AppCompatActivity {

    private static final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first); // 스플래시 레이아웃 설정

        Log.d(TAG, "FirstActivity 실행됨"); // 로그 확인
        // 3초 후 메인 액티비티로 이동
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(FirstActivity.this, MenuActivity.class);
            startActivity(intent);
            finish(); // 현재 액티비티 종료
        }, 3000);
    }
}