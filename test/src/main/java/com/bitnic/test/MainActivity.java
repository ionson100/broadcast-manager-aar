package com.bitnic.test;

import static android.widget.Toast.LENGTH_SHORT;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bitnic.broadcast_manager.BroadcastManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        BroadcastManager.broadcast_scope="com.bitnic.test";
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BroadcastManager.add(this,"B1",str -> {
            Toast.makeText(this,str,LENGTH_SHORT).show();
        });
        BroadcastManager.add(this,"B1",str -> {
            Toast.makeText(this,str,LENGTH_SHORT).show();
        });
        BroadcastManager.addParcelable(this,"B2",parcelable -> {
            if (parcelable instanceof TestParcelable){
                TestParcelable tp= (TestParcelable) parcelable;
                Toast.makeText(this,tp.toString(),LENGTH_SHORT).show();
            }
        });
        this.findViewById(R.id.bt_1).setOnClickListener(v -> {
            BroadcastManager.sendMessage(this,"B1","test22");
        });

        this.findViewById(R.id.bt_2).setOnClickListener(v -> {
            TestParcelable parcelable=new TestParcelable();
            parcelable.name="name";
            parcelable.age=33;
            BroadcastManager.sendMessage(this,"B2",parcelable);
        });
        this.findViewById(R.id.bt_3).setOnClickListener(v -> {
            BroadcastManager.dispose(this);

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BroadcastManager.dispose(this);
    }
}