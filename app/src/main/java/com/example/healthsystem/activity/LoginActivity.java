package com.example.healthsystem.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthsystem.R;
import com.example.healthsystem.sqlite.MyDBHelper;
import com.example.healthsystem.tools.ExitApplication;

/**
 * Created by Administrator on 2018/3/14.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText userName;
    private EditText password;
    private Button loginButton;
    private Button registerButton;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase database;
    private RadioGroup rgClasses;
    private RadioButton rbClasses;
    private String classes = "doctor";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        startActivity(new Intent(this, StartupActivity.class));
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        registerButton = findViewById(R.id.registerButton);
        rgClasses = findViewById(R.id.rg_classes);
        rgClasses.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                rbClasses = findViewById(checkedId);
                if (rbClasses.getText().toString().equals("医生")) {
                    classes = "doctor";
                } else {
                    classes = "sick";
                }
            }
        });


        loginButton.setTag(1);
        loginButton.setOnClickListener(this);
        registerButton.setTag(2);
        registerButton.setOnClickListener(this);

        ExitApplication.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        int tag = (int) v.getTag();
        switch (tag) {
            case 1:
                Intent intentLogin = new Intent(this, MainActivity.class);
                //intentLogin.putExtras("username",userName.getText().toString());
                //intentLogin.putExtras("password",password.getText().toString());
                if (isCorrect()) {
                    startActivity(intentLogin);
                }
                break;
            case 2:
                Intent intentRegiter = new Intent(this, RegisterActivity.class);
                startActivity(intentRegiter);
                break;
        }
    }

    public boolean isCorrect() {
        String dbUserName;
        String dbPassword;
        String dbClasses;
        myDBHelper = new MyDBHelper(this, "APP_Login.db", null, 1);
        database = myDBHelper.getWritableDatabase();

        Cursor cursor = database.query("person", null, null, null, null, null, null);
        Log.d("wenfang", "dbClasses" + classes);
        if (cursor.moveToFirst()) {
            do {
                dbUserName = cursor.getString(cursor.getColumnIndex("username"));
                dbPassword = cursor.getString(cursor.getColumnIndex("password"));
                dbClasses = cursor.getString(cursor.getColumnIndex("classes"));
                Log.d("wenfang", "dbClasses" + dbClasses);

                if (dbUserName.equals(userName.getText().toString()) &&
                        dbPassword.equals(password.getText().toString()) &&
                        dbClasses.equals(classes)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        Toast.makeText(this, "账号或密码错误，请重新输入", Toast.LENGTH_LONG).show();
        return false;
    }

}
