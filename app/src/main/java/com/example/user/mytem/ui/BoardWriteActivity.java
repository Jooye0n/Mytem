package com.example.user.mytem.ui;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.example.user.mytem.R;
import com.example.user.mytem.model.PostModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

import me.iwf.photopicker.PhotoPicker;

public class BoardWriteActivity extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;

    //UI
    private EditText titleEditText;
    private EditText contentsEditText;
    private Spinner board_spinner;
    private EditText numEditText;
    private EditText priceEditText;
    private EditText price2EditText;
    private EditText priceAEditText;
    private EditText priceBEditText;
    private EditText detailEditText;
    private int currentPosition;
    private boolean rewrite;
    private String postKey;
    private PostModel postModel;
    private ActionBar actionBar;
    private TextView toolbarText;
    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String change;

    private String spinnerItem;

    //사진
    private ImageView inputimg;
    private int flag=0;
    private Uri imgUri=null;
    private Uri photoURI=null;
    private String mCurrentPhotoPath;
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;

    //카메라켜기
    boolean photo = false;
//    public static String select ="";
//    private String absoultePath;
//    private FirebaseDatabase database = FirebaseDatabase.getInstance();
//    private DatabaseReference databaseReference = database.getReference();
    private StorageReference storageRef;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        //StorageReference storageReference = storage.getReferenceFromUrl("gs://mytem-c93ac.appspot.com").child("albumImages");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//뒤로가기 추가

        toolbarText = findViewById(R.id.toolbartext);
        final Toolbar toolbartext = findViewById(R.id.toolbar);
        setSupportActionBar(toolbartext);//툴바 t변경

        actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);

        currentPosition = getIntent().getExtras().getInt("CURRENT_BOARD_TAB");
        String reWriteTitle = getIntent().getExtras().getString("BOARD_TITLE");
        String reWriteContents = getIntent().getExtras().getString("BOARD_CONTENTS");
        postKey = getIntent().getExtras().getString("CORRECT_POST_KEY");
        String reWriteDetail = getIntent().getExtras().getString("BOARD_DETAIL");
        int reWriteNum = getIntent().getExtras().getInt("BOARD_NUM");
        int reWritePrice = getIntent().getExtras().getInt("BOARD_PRICE");
        int reWritePrice2 = getIntent().getExtras().getInt("BOARD_PRICE2");
        int reWritePriceA = getIntent().getExtras().getInt("BOARD_PRICEA");
        int reWritePriceB = getIntent().getExtras().getInt("BOARD_PRICEB");

        inputimg = findViewById(R.id.inputimg);

        titleEditText = (EditText) findViewById(R.id.board_write_title_edit_text);
        contentsEditText = (EditText) findViewById(R.id.board_write_content_edit_text);
        numEditText = (EditText) findViewById(R.id.board_write_num_edit_text);
        priceEditText = (EditText) findViewById(R.id.board_write_price_edit_text);
        price2EditText = findViewById(R.id.board_write_price2_edit_text);
        priceAEditText = findViewById(R.id.board_write_priceA_edit_text);
        priceBEditText = findViewById(R.id.board_write_priceB_edit_text);
        detailEditText = (EditText) findViewById(R.id.board_write_detail_edit_text);
        ImageButton writeButton = (ImageButton) findViewById(R.id.board_write_finish);
        ImageButton closeButton = (ImageButton) findViewById(R.id.board_write_close_button);
        board_spinner = (Spinner) findViewById(R.id.board_spinner);

        board_spinner.post(new Runnable() {
            @Override
            public void run() {
                board_spinner.setSelection(currentPosition);
            }
        });
        spinnerItem = (String)board_spinner.getSelectedItem();//spinnerItem 아직 어디에 쓸지 모르겠다

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BoardWriteActivity.this, R.array.board_spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        board_spinner.setAdapter(adapter);

        postModel = new PostModel((String) board_spinner.getSelectedItem());


        if (reWriteContents != null && reWriteTitle != null) {
            titleEditText.setText(reWriteTitle);
            contentsEditText.setText(reWriteContents);
            board_spinner.setVisibility(View.GONE);
            detailEditText.setText(reWriteDetail);
            priceEditText.setText(String.valueOf(reWritePrice));
            price2EditText.setText(String.valueOf(reWritePrice2));
            priceAEditText.setText(String.valueOf(reWritePriceA));
            priceBEditText.setText(String.valueOf(reWritePriceB));
            numEditText.setText(String.valueOf(reWriteNum));
            rewrite = true;

        }



        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextInputError())
                    return;
                UploadTask uploadTask;
                if(photoURI!=null){

                    StorageReference riversRef = storageRef.child("albumImages/"+photoURI.getLastPathSegment());
                    photo = true;
                    uploadTask = riversRef.putFile(photoURI);
                    // temp_uri = uploadTask.getSnapshot().getDownloadUrl();
                    // Register observers to listen for when the download is done or if it fails
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                            //temp_uri = taskSnapshot.getDownloadUrl();
                            //Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        }
                    });
                }
                else{
                    photo = false;
                }
                sendPost();
                makeConfirmDialog();
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if(!TextUtils.isEmpty(charSequence.toString()) && !charSequence.toString().equals(change)){
                change = decimalFormat.format(Double.parseDouble(charSequence.toString().replaceAll(",","")));
                priceEditText.setText(change);
                priceEditText.setSelection(change.length());
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public void onClickButton(View view ) {
        Log.v("알림", "다이얼로그 > 앨범선택 선택");
        makeDialog();
    }

    private void makeDialog(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(BoardWriteActivity.this,R.style.AlertDialogCustom);
        alt_bld.setTitle("사진 업로드").setIcon(R.drawable.ic_menu_gallery).setCancelable(
                false).setPositiveButton("사진촬영",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v("알림", "다이얼로그 > 사진촬영 선택");
                        // 사진 촬영 클릭
                        flag = 0;
                        takePhoto();
                    }
                }).setNeutralButton("앨범선택",
                new DialogInterface.OnClickListener() {
                    public void onClick( DialogInterface dialogInterface, int id) {
                        Log.v("알림", "다이얼로그 > 앨범선택 선택");
                        //앨범에서 선택
                        flag = 1;
                        selectAlbum();
                    }
                }).setNegativeButton("취소   ",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.v("알림", "다이얼로그 > 취소 선택");
                        // 취소 클릭. dialog 닫기.
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }

    //사진 찍기 클릭
    public void takePhoto(){
//        // 촬영 후 이미지 가져옴
//        String state = Environment.getExternalStorageState();
//
//        if(Environment.MEDIA_MOUNTED.equals(state)){
//            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if(intent.resolveActivity(getPackageManager())!=null){
//                File photoFile = null;
//                try{
//                    photoFile = createImageFile();
//                }catch (IOException e){
//                    e.printStackTrace();
//                }
//                if(photoFile!=null){
//                    Uri providerURI = FileProvider.getUriForFile(this,getPackageName(),photoFile);
//                    imgUri = providerURI;
//                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, providerURI);
//                    startActivityForResult(intent, FROM_CAMERA);
//                }
//            }
//        }else{
//            Log.v("알림", "저장공간에 접근 불가능");
//            return;
//        }
        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + "jpg";//바꿔야될듯 -> 확인하기(파일명 시간아니다)
        photoURI = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(intent, FROM_CAMERA);
    }

    //앨범 선택 클릭
    public void selectAlbum(){
        //앨범에서 이미지 가져옴
        //앨범 열기
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        intent.setType("image/*");
        startActivityForResult(intent, FROM_ALBUM);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_OK){
            return;
        }
        switch (requestCode){
            case FROM_ALBUM : {
                //앨범에서 가져오기
                if(data.getData()!=null){
                    try{
                        photoURI = data.getData();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                        inputimg.setImageBitmap(bitmap);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case FROM_CAMERA : {
                //카메라 촬영
                try{
                    Log.v("알림", "FROM_CAMERA 처리");
                    galleryAddPic();
                    inputimg.setImageURI(imgUri);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }


//    public File createImageFile() throws IOException{
//        String imgFileName = System.currentTimeMillis() + ".jpg";
//        File imageFile= null;
//        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures", "ireh");
//
//        if(!storageDir.exists()){
//            //없으면 만들기
//            Log.v("알림","storageDir 존재 x " + storageDir.toString());
//            storageDir.mkdirs();
//        }
//        Log.v("알림","storageDir 존재함 " + storageDir.toString());
//        imageFile = new File(storageDir,imgFileName);
//        mCurrentPhotoPath = imageFile.getAbsolutePath();
//
//        return imageFile;
//    }

    public void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Toast.makeText(this,"사진이 저장되었습니다",Toast.LENGTH_SHORT).show();
    }

    public void makeConfirmDialog() {
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(BoardWriteActivity.this, R.style.AlertDialogCustom);
        alt_bld.setTitle("작성 완료").setIcon(R.drawable.send).setMessage("글을 게시하시겠습니까?").setCancelable(
                false).setPositiveButton("네",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        //DB에 등록
                        final String cu = mAuth.getUid();
                        //1. 사진을 storage에 저장하고 그 url을 알아내야함
                        String filename = titleEditText.getText().toString();
                        StorageReference storageRef = storage.getReferenceFromUrl("gs://mytem-c93ac.appspot.com").child("albumImages/" + filename+".jpg");

                        UploadTask uploadTask;

                        Uri file = null;
                        if(flag ==0){
                            //사진촬영
                            file = Uri.fromFile(new File(mCurrentPhotoPath));
                        }else if(flag==1){
                            //앨범선택
                            file = photoURI;
                        }
                        uploadTask = storageRef.putFile(file);
                        final ProgressDialog progressDialog = new ProgressDialog(BoardWriteActivity.this,R.style.AlertDialogCustom);
                        progressDialog.setMessage("업로드중...");
                        progressDialog.show();

                        // Register observers to listen for when the download is done or if it fails
                        uploadTask.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Log.v("알림", "사진 업로드 실패");
                                exception.printStackTrace();
                                progressDialog.dismiss();
                                finish();
                                Toast.makeText(getApplicationContext(), "작성 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                                String downloadUrl = taskSnapshot.getStorage().getDownloadUrl().toString();
                                Log.v("알림", "사진 업로드 성공 " + downloadUrl);
                                progressDialog.dismiss();
                                finish();
                                Toast.makeText(getApplicationContext(), "작성이 완료되었습니다.", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }).setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 아니오 클릭. dialog 닫기.
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        alert.show();
    }




    @Override
    public boolean onOptionsItemSelected( MenuItem item ) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                this.finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }


    private boolean isTextInputError() {
        if (TextUtils.isEmpty(titleEditText.getText().toString())) {
            titleEditText.requestFocus();
            titleEditText.setError("제목을 입력해주세요\n");
            return true;
        } else if (TextUtils.isEmpty(contentsEditText.getText().toString())) {
            contentsEditText.setError("내용을 입력해주세요\n");
            contentsEditText.requestFocus();
            return true;
        }
        return false;
    }

    public String setPriceRange(int originalprice) { //가격별로 posttype나누는 함수
        String posttype = null;

       if(originalprice<10000) {
           posttype = "만원미만";
       }else if(originalprice>=10000 && originalprice<30000) {
           posttype = "3만원미만";
       }else if(originalprice>=30000 && originalprice<50000) {
           posttype = "5만원미만";
       }else if(originalprice>=50000 && originalprice<100000) {
           posttype = "10만원미만";
       }else if(originalprice>=100000) {
           posttype = "10만원이상";
       }
       return posttype;

    }



    public void sendPost() {
        if (rewrite) {//게시글 수정
            postModel.correctPost(setPriceRange(Integer.parseInt(priceEditText.getText().toString())),"url", titleEditText.getText().toString(), contentsEditText.getText().toString(),
                    postKey, Integer.parseInt(numEditText.getText().toString()),
                    Integer.parseInt(priceEditText.getText().toString()), Integer.parseInt(price2EditText.getText().toString()),Integer.parseInt(priceAEditText.getText().toString()),Integer.parseInt(priceBEditText.getText().toString()),detailEditText.getText().toString());

        } else//게시글 등록
            postModel.writePost(setPriceRange(Integer.parseInt(priceEditText.getText().toString())), "url",titleEditText.getText().toString(),
                    contentsEditText.getText().toString(), Integer.parseInt(numEditText.getText().toString()),
                    Integer.parseInt(priceEditText.getText().toString()), Integer.parseInt(price2EditText.getText().toString()),Integer.parseInt(priceAEditText.getText().toString()),Integer.parseInt(priceBEditText.getText().toString()),detailEditText.getText().toString());
    }

}
