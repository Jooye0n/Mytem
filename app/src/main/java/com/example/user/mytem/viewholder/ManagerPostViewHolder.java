package com.example.user.mytem.viewholder;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mytem.R;
import com.example.user.mytem.singleton.SUser;
import com.example.user.mytem.ui.ManagerWriteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ManagerPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView call;
    private ImageView mail;
    private ImageButton dropdownButton;
    private TextView name;
    private TextView email;
    private TextView phone;
    private TextView place;
    private Context context;
    private DatabaseReference mDatabase;
    private SUser user;
    private String dataRefKey;
    private String postType = "SUser";

    public ManagerPostViewHolder( View itemView ) {
        super(itemView);
        context = itemView.getContext();

        call = itemView.findViewById(R.id.call);
        mail = itemView.findViewById(R.id.mail);
        dropdownButton = itemView.findViewById(R.id.dropdown_button2);
        name = itemView.findViewById(R.id.postManName);
        email = itemView.findViewById(R.id.postManEmail);
        phone = itemView.findViewById(R.id.postManPhone);
        place = itemView.findViewById(R.id.postManPlace);
        phone = itemView.findViewById(R.id.postManPhone);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, dropdownButton);
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_delete) {//////////////////////////////////////////////////삭제
                            mDatabase.child(postType).child(dataRefKey).removeValue();//firebase에서 삭제


                        } else if (item.getItemId() == R.id.popup_rewrite) {///////////////////////////////////////////수정
                            Intent intent = new Intent(context, ManagerWriteActivity.class);
                            intent.putExtra("POST_NAME", user.getMname());
                            intent.putExtra("POST_PLACE", user.getMposition());
                            intent.putExtra("CORRECT_POST_KEY", dataRefKey);
                            intent.putExtra("POST_PHONE",user.getMphone());
                            intent.putExtra("POST_EMAIL",user.getMemail());
                            intent.putExtra("POST_PW",user.getMpassword());
                            intent.putExtra("POST_REWRITE",true);
                            context.startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone.getText().toString()));
                Log.i("전화1",""+phone.getText().toString());

                try {
                    context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //UserRecord userRecord = FirebaseAuth.getInstance().getUserByPhoneNumber(phoneNumber);
        //// See the UserRecord reference doc for the contents of userRecord.
        //System.out.println("Successfully fetched user data: " + userRecord.getPhoneNumber());

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                FirebaseUser currentUser = mAuth.getCurrentUser();
//                Log.v("currentUser", currentUser.getDisplayName());
                try {
                    Intent intent = new Intent (Intent.ACTION_SEND);
                    intent.setType("message/rfc822");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email.getText().toString()});
                    intent.putExtra(Intent.EXTRA_SUBJECT, name.getText().toString()+"에게 보내는 메일");
                    intent.setPackage("com.google.android.gm");
                    if (intent.resolveActivity(context.getPackageManager())!=null)
                       context.startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        itemView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {//////등록된 상품 하나를 선택한 경우->할게없다
//        android.app.FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
//
//        Bundle arguments = new Bundle();
//        arguments.putString("POST_DATAIL", detail);
//        arguments.putString("POST_TITLE",post.getTitle());
//
//        PostDetailDialogFragment dialog = new PostDetailDialogFragment();
//        dialog.setArguments(arguments);
//
//        dialog.show(fragmentManager, "POST_DATAIL");
//        ((Activity) context).overridePendingTransition(R.anim.slide_up_anim, R.anim.no_change);
    }

    public void bindPost( final SUser user, String postKey) {
        this.user = user;
        name.setText(String.valueOf(user.getMname()));
        place.setText(String.valueOf(user.getMposition()));
        phone.setText(String.valueOf(user.getMphone()));
        email.setText(String.valueOf(user.getMemail()));
        this.dataRefKey = postKey;

//        int visibility = Objects.equals(user.getAuthorUid(), "관리자") ?
//                View.VISIBLE : View.GONE;//댓글 단 사람이 삭제하려는 사람과 같으면 보인다->지우기

//        dropdownButton.setVisibility(visibility);
    }
}
