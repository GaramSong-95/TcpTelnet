package net.kccistc.tcptelnet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.KeyEvent;

import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;








public class CctvActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cctv);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);


            return insets;
        });

        // WebView 초기화
        webView = findViewById(R.id.webView); // 웹뷰를 XML에서 설정한 ID로 찾기

        // WebView 설정
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true); // JavaScript 허용

        // 네이버 페이지 로드
        webView.loadUrl("http://10.10.141.133:8080");
    }

    @Override
    public void onBackPressed() {
        if (webView != null && webView.canGoBack()) {
            // WebView에서 뒤로 갈 수 있으면 뒤로 가기
            webView.goBack();
        } else {
            // 더 이상 뒤로 갈 수 없으면 MenuActivity로 이동
            super.onBackPressed();
            Intent intent = new Intent(CctvActivity.this, MenuActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);  // MenuActivity 시작
            finish();  // 현재 Activity 종료
        }
    }

}