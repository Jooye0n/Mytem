package com.example.user.mytem.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.mytem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginDialogFragment extends DialogFragment {

    private EditText id;//입력 아이디 및 확인하고자하는 아이디
    private EditText pw;
    private Button login;
    private Button create;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    //private CUser user;

    private static final String TAG = "LoginActivity";



    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.fragment_login_dialog);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login_dialog,null);

        mAuth = FirebaseAuth.getInstance();


        id = view.findViewById(R.id.id);
        pw = view.findViewById(R.id.passwordM);
        login = view.findViewById(R.id.btn_createM);
        create = view.findViewById(R.id.btn_request);


        login.bringToFront();
        create.bringToFront();
        id.bringToFront();
        pw.bringToFront();


        /*
        <<<<<<<<<<<< 로그인 >>>>>>>>>>>>>>>>>>>>>>>
         */


        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(getActivity(), CreateActicity.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                if(isTextInputError())
                    return;
                if (!isValidEmail(id.getText().toString())) {
                    Toast.makeText(getActivity(), "이메일을 다시 확인해주세요",
                            Toast.LENGTH_SHORT).show();
                    return;//이메일 제대로 입력하고 비밀번호 두자리수 입력했는데 이거뜬다 고치기
                }
                if(!isValidPasswd(id.getText().toString())) {
                    return;
                }

                showProgressDialog();

                mAuth.signInWithEmailAndPassword(id.getText().toString(), pw.getText().toString())
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    //nav의 text 업데이트
                                    //            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                                    //            user.getEmail(), user.isEmailVerified()));
                                    //            mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    Toast.makeText(getActivity(), user.getEmail()+"님이 로그인 되었습니다.", Toast.LENGTH_SHORT).show();
                                    updateUI(user);//로그인 성공 후의 UI업데이트
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //로그인 실패시 textview 비우고 다시 입력받는다
                                    pw.setText("");
                                    id.setText("");
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "입력 사항을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
                                    Toast.makeText(getActivity(), "로그인 실패",
                                            Toast.LENGTH_SHORT).show();
                                    updateUI(null);//로그인 실패시의 UI업데이트
                                }
                            }
                        });
            }
        });
        return new AlertDialog.Builder(getActivity()).setView( view ).create();
    }//oncreate

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (user != null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        showProgressDialog();
        updateUI(currentUser);

    }

    private boolean isTextInputError() {//입력 안했을 시
        if (TextUtils.isEmpty(id.getText().toString())) {
            id.requestFocus();
            id.setError("이메일을 입력해주세요\n");
            return true;
        } else if (TextUtils.isEmpty(pw.getText().toString())) {
            pw.setError("비밀번호를 입력해주세요\n");
            pw.requestFocus();
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
                Toast.makeText(getActivity(), "비밀번호를 6자리 이상으로 입력해주세요",
                        Toast.LENGTH_SHORT).show();
                return false;
        }
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {//로그인 성공 후의 UI업데이트

            dismiss();//로그인 다이얼로그 사라지기

            //private FirebaseAuth mAuth;
            mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();

            TextView navHeaderTextView = getActivity().findViewById(R.id.nav_sub_header333);
            navHeaderTextView.setText(currentUser.getEmail());//네비게이션 바의 헤더 부분 로그인 완료 시 UIupdate
            TextView navHead = getActivity().findViewById(R.id.nav_sub_header2);
            navHead.setVisibility(View.VISIBLE);
            navHead.setText(currentUser.getDisplayName()+"님");//이메일 이름 업데이트

            navHead.setTextColor(Color.parseColor("#072972"));
            navHead.setPaintFlags(navHead.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);//밑줄긋기(링크)

            FloatingActionButton noticeFloatingActionButton = getActivity().findViewById(R.id.notice_floating);
            TextView noticeCount = getActivity().findViewById(R.id.notice_count);
            noticeFloatingActionButton.setVisibility(View.VISIBLE);
            noticeCount.setVisibility(View.VISIBLE);//로그인이 안된 상태면

        } else {//로그인 실패시의 UI업데이트
//            mStatusTextView.setText(R.string.signed_out);
//            mDetailTextView.setText(null);

        }
    }

    private void showProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading..");
        progressDialog.show();
    }

    private void hideProgressDialog() {
            progressDialog.dismiss();
    }

}
