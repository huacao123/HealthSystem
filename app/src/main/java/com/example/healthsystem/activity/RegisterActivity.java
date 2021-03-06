package com.example.healthsystem.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.healthsystem.R;
import com.example.healthsystem.sqlite.MyDBHelper;

/**
 * Created by Administrator on 2018/3/13.
 */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName;
    private EditText password1;
    private EditText password2;
    private Button backButton;
    private Button registerButton;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase database;
    private RadioGroup rgClasses;
    private RadioButton rbClasses;
    private String classes = "doctor";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.userName);
        password1 = findViewById(R.id.password1);
        password2 = findViewById(R.id.password2);
        backButton = findViewById(R.id.backButton);
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

        backButton.setTag(2);
        backButton.setOnClickListener(this);
        registerButton.setTag(1);
        registerButton.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        int tag = (int) v.getTag();
        switch (tag) {
            case 1:
                insertData();
                break;

            case 2:
                finish();
                break;

            default:
                Toast.makeText(RegisterActivity.this, "注册失败，请再试一次", Toast.LENGTH_LONG).show();
        }

    }

    public void createDatabase() {
        myDBHelper = new MyDBHelper(this, "APP_Login.db", null, 1);
        database = myDBHelper.getWritableDatabase();

    }

    public void insertData() {
        if (pwdCorrect() && checkData()) {
            createDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("classes", classes);
            contentValues.put("username", userName.getText().toString());
            contentValues.put("password", password1.getText().toString());
            database.insert("person", null, contentValues);
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
        }

    }

    public boolean pwdCorrect() {
        if (password1.getText().toString().equals(password2.getText().toString())) {
            return true;
        }
        Toast.makeText(RegisterActivity.this, "两次密码输入不一致，请重新输入", Toast.LENGTH_LONG).show();
        return false;
    }

    private boolean checkData() {
        if (userName.getText().toString().trim().isEmpty()) {  //TextUtils.isEmpty(userName.getText().toString())
            Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password1.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}
