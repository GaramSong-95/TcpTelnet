package net.kccistc.tcptelnet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.view.KeyEvent;
import android.content.Intent;



public class MainActivity extends AppCompatActivity {
    static MainHandler mainHandler;
    ClientThread clientTread;
    ToggleButton toggleButtonConnect;
    Button buttonSend,buttonGo,buttonHome,buttonSpray;
    TextView textViewRecv;
    ScrollView scrollViewRecv;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yy MM dd HH:mm:ss");
    EditText editTextSend,editTextId;
    ScrollView scrollViewBottom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        editTextSend = findViewById(R.id.editTextSend);
        scrollViewRecv = findViewById(R.id.scrollViewRecv);
        textViewRecv = findViewById(R.id.textViewRecv);
        textViewRecv.setMovementMethod(new ScrollingMovementMethod());
        //EditText editTextIp = findViewById(R.id.editTextIp);
        //EditText editTextPort = findViewById(R.id.editTextPort);
        //EditText editTextId = findViewById(R.id.editTextId);

        //editTextIp.setText(ClientThread.serverIp);
       // editTextPort.setText(String.valueOf(ClientThread.serverPort));
        //editTextId.setText(ClientThread.clientId);

        scrollViewBottom = findViewById(R.id.scrollViewBottom);

        buttonSend =findViewById(R.id.buttonSend);
        buttonSend.setEnabled(false);
        toggleButtonConnect = findViewById(R.id.toggleButtonConnect);

        toggleButtonConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(toggleButtonConnect.isChecked()) {
                   // String Ip = editTextIp.getText().toString();
                    // int Port = Integer.parseInt(editTextPort.getText().toString());
                    //String Id = editTextId.getText().toString();

                    clientTread = new ClientThread();
                    clientTread.start();
                    buttonSend.setEnabled(true);
                }
                else{
                    clientTread.stopClient();
                    buttonSend.setEnabled(false);
                }
            }
        });
        Button buttonSend = findViewById(R.id.buttonSend);
        buttonSend.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strSend = editTextSend.getText().toString();
                clientTread.sendData(strSend);
                editTextSend.setText("");

            }
        });

        buttonHome = findViewById(R.id.buttonHome);
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strHome = "[ROS]GOHOME";
                clientTread.sendData(strHome);

            }
        });

        buttonGo = findViewById(R.id.buttonGo);

        buttonGo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strGo = "[ROS]GO@x좌표@y좌표@각도";
                editTextSend.setText(strGo);

            }
        });

        buttonSpray = findViewById(R.id.buttonSpray);

        buttonSpray.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strSpray = "[ROS]SPRAYIC";
                clientTread.sendData(strSpray);
            }
        });

        editTextSend.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    scrollViewBottom.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            scrollViewBottom.smoothScrollTo(0, editTextSend.getBottom());
                        }
                    }, 200);
                }
            }
        });




        mainHandler = new MainHandler();

    }

    @Override
    public void onBackPressed() {
        // MenuActivity로 돌아가기 위한 인텐트 생성
        super.onBackPressed();
        Intent intent = new Intent(MainActivity.this, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);  // MenuActivity 시작
        finish();  // SecondActivity 종료
    }

    class MainHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String data = bundle.getString("msg");
            Log.d("MainHandler",data);

            Date date = new Date();
            String strDate = dateFormat.format(date);
            strDate = strDate+" "+data+"\n";


            textViewRecv.append(strDate);

            scrollViewRecv.post(new Runnable() {
                @Override
                public void run() {
                    scrollViewRecv.fullScroll(View.FOCUS_DOWN);
                    // 🔹 또는 다음 코드도 사용 가능
                    scrollViewRecv.smoothScrollTo(0, textViewRecv.getBottom());
                }
            });
        }

    }

}