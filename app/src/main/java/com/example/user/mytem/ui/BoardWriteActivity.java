package com.example.user.mytem.ui;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.user.mytem.R;
import com.example.user.mytem.model.PostModel;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class BoardWriteActivity extends AppCompatActivity {

    //firebase
    private FirebaseAuth mAuth;
    private FirebaseStorage storage;

    //UI
    private EditText titleEditText;
    private EditText contentsEditText;
    private Spinner boardCategory;
    private Spinner boardDelivery;
    private EditText numEditText;
    private EditText priceEditText;//1
    private EditText price2EditText;//2
    private EditText priceAEditText;//3
    private EditText priceBEditText;//4
    private EditText detailEditText;
    private int currentPosition;
    private boolean rewrite;
    private String postKey;
    private PostModel postModel;
    private EditText brand;
    private EditText production;
    private EditText origin;
    private EditText delivery2;
    private ActionBar actionBar;
    private TextView toolbarText;

    private String spinnerItem;
    private String spinnerItemDetail;

    //사진 -1
    private ImageView inputimg;
    private ImageView inputimgDetail;
    private int flag=0;
    private Uri imgUri=null;
    private Uri photoURI=null;//메인사진
    private Uri photoURI2=null;//상세이미지
    private String mCurrentPhotoPath;
    private static final int FROM_CAMERA = 0;
    private static final int FROM_ALBUM = 1;
    private Boolean isMainImg = false;

    //카메라켜기
    boolean photo = false;
    private StorageReference storageRef;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board_write);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        //StorageReference storageReference = storage.getReferenceFromUrl("gs://mytem-c93ac.appspot.com").child("albumImages");

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
        inputimgDetail = findViewById(R.id.inputimg_detail);

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
        boardCategory = (Spinner) findViewById(R.id.board_spinner);
        boardDelivery = findViewById(R.id.board_delivery_spinner);
        brand = findViewById(R.id.board_brand);
        production = findViewById(R.id.board_production);
        origin = findViewById(R.id.board_origin);
        delivery2 = findViewById(R.id.board_delivery);



        boardCategory.post(new Runnable() {
            @Override
            public void run() {
                boardCategory.setSelection(currentPosition);
            }
        });
        spinnerItem = (String) boardCategory.getSelectedItem();//spinnerItem 아직 어디에 쓸지 모르겠다

        ArrayAdapter<CharSequence> boardadapter = ArrayAdapter.createFromResource(BoardWriteActivity.this, R.array.board_spinner, android.R.layout.simple_spinner_item);
        boardadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardCategory.setAdapter(boardadapter);




        spinnerItemDetail = (String) boardDelivery.getSelectedItem();//spinnerItem 아직 어디에 쓸지 모르겠다
        ArrayAdapter<CharSequence> boardadapter2 = ArrayAdapter.createFromResource(BoardWriteActivity.this, R.array.board_delivery_spinner, android.R.layout.simple_spinner_item);
        boardadapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        boardDelivery.setAdapter(boardadapter2);

        postModel = new PostModel();

        if (reWriteContents != null && reWriteTitle != null) { //수정시
            titleEditText.setText(reWriteTitle);
            contentsEditText.setText(reWriteContents);
            detailEditText.setText(reWriteDetail);
            priceEditText.setText(String.valueOf(reWritePrice));
            price2EditText.setText(String.valueOf(reWritePrice2));
            priceAEditText.setText(String.valueOf(reWritePriceA));
            priceBEditText.setText(String.valueOf(reWritePriceB));
            numEditText.setText(String.valueOf(reWriteNum));
            rewrite = true;

            StorageReference mStorageRef;
            mStorageRef = FirebaseStorage.getInstance().getReference().child("albumImages/" +contentsEditText.getText().toString()+ ".jpg");
            Log.v("로그",contentsEditText.getText().toString());//정상출력됨
            Glide.with(this).using(new FirebaseImageLoader()).load(mStorageRef).diskCacheStrategy(DiskCacheStrategy.ALL).into(inputimg);//원래의 이미지 로드

        }



        writeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTextInputError())
                    return;
                if(photoURI!=null && photoURI2!=null){ //사진을 가져왔으면
                    photo = true;
                }
                else{
                    photo = false;
                }
                sendPost();
                makeConfirmDialog();//수정시 업로드는 성공했음
                //수정 완료 시 recyclerview 새로고침 해야한다.
            }
        });


        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    public void onClickButton(View view ) {
        if(view==inputimg)
            isMainImg = true;
        if(view==inputimgDetail)
            isMainImg = false;

        Log.v("알림", "사진 추가 버튼 누름");
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

        Intent intent = new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA);
        startActivityForResult(intent, FROM_CAMERA);
    }

    //앨범 선택 클릭
    public void selectAlbum() {
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

        if(isMainImg==true)//메인 이미지인 경우
            resultPhoto(inputimg, requestCode, data);
        else//상세 이미지인 경우
            resultPhoto(inputimgDetail,requestCode, data);

    }

    public void resultPhoto(ImageView view,int requestCode, Intent data) {
        switch (requestCode){
            case FROM_ALBUM : {
                //앨범에서 가져오기
                if(data.getData()!=null){
                    try{
                        Bitmap bitmap;
                        if(isMainImg == true) {
                            photoURI = data.getData();
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                        } else //(view.getId() == R.id.inputimg_detail)
                        {
                            photoURI2 = data.getData();
                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI2);
                        }
                        view.setImageBitmap(bitmap);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case FROM_CAMERA : {
                //카메라 촬영z
                try{
                    Log.v("알림", "FROM_CAMERA 처리");
                    galleryAddPic();
                    view.setImageURI(imgUri);
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
    }


    public void galleryAddPic() {
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

                        //1. 사진을 storage에 저장하고 그 url을 알아내야함
                        String filename = contentsEditText.getText().toString();
                        StorageReference storageRef;
                        StorageReference storageRef2;

                        storageRef = storage.getReferenceFromUrl("gs://mytem-c93ac.appspot.com").child("albumImages/" + filename+".jpg");
                        storageRef2 = storage.getReferenceFromUrl("gs://mytem-c93ac.appspot.com").child("albumImages/" + filename+"_detail"+".jpg");

                        UploadTask uploadTask;
                        UploadTask uploadTask2;

                        Uri file = null;
                        Uri file2 = null;

                        if(flag ==0){
                            //사진촬영
//                            file = Uri.fromFile(new File(mCurrentPhotoPath));
                            file = photoURI;
                            file2 = photoURI2;
                        }else if(flag==1){
                            //앨범선택
                            file = photoURI;
                            file2 = photoURI2;
                        }

                        uploadTask = storageRef.putFile(file);
                        uploadTask2 = storageRef2.putFile(file2);
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

                            }
                        });


                        uploadTask2.addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                // Handle unsuccessful uploads
                                Log.v("알림", "사진 업로드 실패");
                                exception.printStackTrace();
                                finish();
                                Toast.makeText(getApplicationContext(), "작성 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                                String downloadUrl = taskSnapshot.getStorage().getDownloadUrl().toString();
                                Log.v("알림", "사진 업로드 성공 " + downloadUrl);
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
        if (rewrite) {//게시글 수정->사진수정 추가해야한다.
            postModel.correctPost(setPriceRange(Integer.parseInt(priceEditText.getText().toString())),"url", titleEditText.getText().toString(), contentsEditText.getText().toString(),
                    postKey, Integer.parseInt(numEditText.getText().toString()),
                    Integer.parseInt(priceEditText.getText().toString()), Integer.parseInt(price2EditText.getText().toString()),Integer.parseInt(priceAEditText.getText().toString()),
                    Integer.parseInt(priceBEditText.getText().toString()),detailEditText.getText().toString(),
                    (String) boardCategory.getSelectedItem(), production.getText().toString(), origin.getText().toString(), brand.getText().toString(),
                    (String) boardDelivery.getSelectedItem(),delivery2.getText().toString()
                    );
            //post.getCategory(), post.getProduction(), post.getOrigin(), post.getBrand(), post.getDelivery1(), post.getDelivery2()

        } else//게시글 등록
            postModel.writePost(setPriceRange(Integer.parseInt(priceEditText.getText().toString())), "url",titleEditText.getText().toString(),
                    contentsEditText.getText().toString(), Integer.parseInt(numEditText.getText().toString()),
                    Integer.parseInt(priceEditText.getText().toString()), Integer.parseInt(price2EditText.getText().toString()),
                    Integer.parseInt(priceAEditText.getText().toString()),Integer.parseInt(priceBEditText.getText().toString()),detailEditText.getText().toString(),
                    (String) boardCategory.getSelectedItem(), production.getText().toString(), origin.getText().toString(), brand.getText().toString(),
                    (String) boardDelivery.getSelectedItem(),delivery2.getText().toString());
    }

}
