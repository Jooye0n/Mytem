package com.example.user.mytem.ui;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.mytem.R;
import com.example.user.mytem.model.SUserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class ManagerWriteActivity extends AppCompatActivity {

    private static final String TAG = "ManagerWriteActivity";

    private EditText editTextName;
    private EditText editTextPostion;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextPasswordCk;
    private EditText editTextPhone;
    private Button create;
    private Button btnDoubleCk;
    private String postKey;
    private boolean rewrite = false;

    private ActionBar actionBar;

    private SUserModel sUserModel;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_manager_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        try {
            rewrite = getIntent().getExtras().getBoolean("POST_REWRITE");
        }  catch (Exception e) {
            e.printStackTrace();
        }

        editTextName = findViewById(R.id.nameM);
        editTextPostion = findViewById(R.id.levelM);
        editTextEmail = findViewById(R.id.emailM);
        editTextPassword = findViewById(R.id.passwordM);
        editTextPasswordCk = findViewById(R.id.passwordM_ck);
        editTextPhone = findViewById(R.id.phoneM);
        create = findViewById(R.id.btn_createM);
        btnDoubleCk = findViewById(R.id.btn_doubleckM);

        sUserModel = new SUserModel();
        mAuth = FirebaseAuth.getInstance();


        if (rewrite) {//수정하는경우
            editTextName.setText(getIntent().getExtras().getString("POST_NAME"));
            editTextPostion.setText(getIntent().getExtras().getString("POST_PLACE"));
            editTextEmail.setText(getIntent().getExtras().getString("POST_EMAIL"));
            editTextPhone.setText(getIntent().getExtras().getString("POST_PHONE"));
            editTextPassword.setText(getIntent().getExtras().getString("POST_PW"));
            editTextPasswordCk.setText(getIntent().getExtras().getString("POST_PW"));
            postKey = getIntent().getExtras().getString("CORRECT_POST_KEY");

            editTextEmail.setTextColor(Color.parseColor("#a6a6a6"));
            editTextEmail.setFocusable(false);
            editTextEmail.setClickable(false);//이메일은 변경 불가하다.

            editTextPassword.setTextColor(Color.parseColor("#a6a6a6"));
            editTextPassword.setFocusable(false);
            editTextPassword.setClickable(false);
            editTextPasswordCk.setTextColor(Color.parseColor("#a6a6a6"));
            editTextPasswordCk.setFocusable(false);
            editTextPasswordCk.setClickable(false);//비밀번호도 변경 불가하다.

            btnDoubleCk.setVisibility(View.INVISIBLE);

        }

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if(isTextInputError())
                    return;
                sendCreate();
                //sendPost();
                finish();
            }
        });

        btnDoubleCk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {

                if (!TextUtils.isEmpty(editTextEmail.getText().toString())) {
                    final ProgressDialog progressDialog = new ProgressDialog(ManagerWriteActivity.this, R.style.AlertDialogCustom);
                    progressDialog.setMessage("확인중...");
                    progressDialog.show();

                    //다시
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progressDialog.dismiss();
                            Toast.makeText(ManagerWriteActivity.this, "사용가능한 아이디입니다.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }, 3000); // 3000 milliseconds delay


                }  else {
                    editTextEmail.setError("아이디를 입력해주세요\n");
                    editTextEmail.requestFocus();
                }
            }
        });

        editTextPasswordCk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String password = editTextPassword.getText().toString();
                String confirm = editTextPasswordCk.getText().toString();

                if( password.equals(confirm) ) {
                    editTextPassword.setTextColor(Color.BLACK);
                    editTextPasswordCk.setTextColor(Color.BLACK);
                } else {
                    editTextPassword.setTextColor(Color.parseColor("#b42424"));
                    editTextPasswordCk.setTextColor(Color.parseColor("#b42424"));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI( FirebaseUser user) {
//        if (user != null) {
////            login.setVisibility(View.VISIBLE);
////            create.setVisibility(View.VISIBLE);
//
//            Toast.makeText(ManagerWriteActivity.this,
//                    "기입 사항을 다시 확인하세요",
//                    Toast.LENGTH_LONG).show();
//        } else {
//
////            login.setVisibility(View.VISIBLE);
////            create.setVisibility(View.VISIBLE);
//        }
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {//toolbar의 back키 눌렀을 때 동작
        switch (item.getItemId()){
            case android.R.id.home:{
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendPost() {
        if (rewrite) {//게시글 수정->사진수정 추가해야한다.
            sUserModel.correctPost(editTextName.getText().toString(), editTextPostion.getText().toString(), editTextEmail.getText().toString(), editTextPhone.getText().toString()
                    , editTextPassword.getText().toString(), postKey);

        } else//게시글 등록
            sUserModel.writePost(editTextName.getText().toString(), editTextPostion.getText().toString(), editTextEmail.getText().toString(), editTextPhone.getText().toString(),editTextPassword.getText().toString());
    }


    public void sendCreate() {//firebase등록
        Log.v("로그", "샌드");

        if (isValidEmail(editTextEmail.getText().toString()) && isValidPasswd(editTextPassword.getText().toString())) {
            sendPost();


            mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete( @NonNull Task<AuthResult> task ) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "회원가입성공");
                                FirebaseUser user = mAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(editTextName.getText().toString()).build();
                                user.updateProfile(profileUpdates);//이름추가
                                Toast.makeText(ManagerWriteActivity.this, "회원가입 성공.",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                if(rewrite) {
                                    Log.d(TAG, "회원정보변경 성공");
                                    Toast.makeText(ManagerWriteActivity.this, "회원정보변경 성공.",
                                            Toast.LENGTH_SHORT).show();
                                }else {
                                    Log.d(TAG, "회원가입 실패");
                                    Toast.makeText(ManagerWriteActivity.this, "회원가입 실패.",
                                            Toast.LENGTH_SHORT).show();
                                }
                                //보통 이메일이 이미 존재하거나, 이메일 형식이아니거나, 비밀번호가 6자리 이상이 아닐 때 발생 
                            }
                        }
                    });

            // Toast.makeText(getApplicationContext(), "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ManagerWriteActivity.this,
                    "정보 사항을 다시 확인하세요",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean isTextInputError() {//입력 안했을 시
        if (TextUtils.isEmpty(editTextName.getText().toString())) {
            editTextName.requestFocus();
            editTextName.setError("이름을 입력해주세요\n");
            return true;
        } else if (TextUtils.isEmpty(editTextPostion.getText().toString())) {
            editTextPostion.setError("직급을 입력해주세요\n");
            editTextPostion.requestFocus();
            return true;//editTextRRN
        } else if (TextUtils.isEmpty(editTextPhone.getText().toString())) {
            editTextPhone.setError("핸드폰번호를 입력해주세요\n");
            editTextPhone.requestFocus();
            return true;
        }

        return false;
    }

    private boolean isValidEmail(String str){//이메일 형식과 다를 시
        if(str == null || TextUtils.isEmpty(str)){
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(str).matches();
        }
    }

    boolean isValidPasswd(String str){//6자리 미만입력시 오류
        if(str == null || TextUtils.isEmpty(str)){
            return false;
        } else {
            if(str.length() >= 6)
                return true;
            else
                return false;
        }
    }

    @Override
    protected void onActivityResult( int requestCode, int resultCode, Intent data ) {
        super.onActivityResult(requestCode, resultCode, data);

        mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPassword.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(ManagerWriteActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
}
