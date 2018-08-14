package com.example.user.mytem.viewholder;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.user.mytem.R;
import com.example.user.mytem.singleton.CUser;
import com.example.user.mytem.ui.ManagerWriteActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView name;
    private TextView email;
    private TextView phone;
    private Context context;
    private String dataRefKey;
    private CUser user;
    private CheckBox checkBox;
    private Toolbar toolbar;
    private ImageButton dropdownButton;
    private DatabaseReference mDatabase;
    private String postType;

    public CustomerPostViewHolder( View itemView ) {
        super(itemView);
        context = itemView.getContext();
        ImageView call = itemView.findViewById(R.id.customer_call_btn);
        ImageView mail = itemView.findViewById(R.id.customer_mail_btn);
        toolbar = itemView.findViewById(R.id.toolbar);

        phone = itemView.findViewById(R.id.customer_phone);
        name = itemView.findViewById(R.id.customer_name);
        email = itemView.findViewById(R.id.customer_email);
        checkBox = itemView.findViewById(R.id.checkBox);
        dropdownButton = itemView.findViewById(R.id.customer_dropdown_button);
        mDatabase = FirebaseDatabase.getInstance().getReference();

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

        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
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

        dropdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(context, dropdownButton);
                popup.getMenuInflater()
                        .inflate(R.menu.popup_customer, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.popup_delete) {//////////////////////////////////////////////////삭제
                            mDatabase.child(postType).child(dataRefKey).removeValue();//firebase에서 삭제
                            //아래 user는 cuser이다
                        } else if (item.getItemId() == R.id.popup_rewrite) {///////////////////////////////////////////등급변경
//                            Intent intent = new Intent(context, ManagerWriteActivity.class);
//                            intent.putExtra("POST_NAME", user.getMname());
//                            intent.putExtra("POST_PLACE", user.getMposition());
//                            intent.putExtra("CORRECT_POST_KEY", dataRefKey);
//                            intent.putExtra("POST_PHONE",user.getMphone());
//                            intent.putExtra("POST_EMAIL",user.getMemail());
//                            intent.putExtra("POST_PW",user.getMpassword());
//                            intent.putExtra("POST_REWRITE",true);
//                            context.startActivity(intent);
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });



        itemView.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
    }

    public void bindPostNoneCheck( final CUser cuser, String postKey, String postType) {
        this.user = cuser;
        name.setText(String.valueOf(cuser.getUserName()));
        email.setText(String.valueOf(cuser.getUemail()));
        phone.setText(String.valueOf(cuser.getUphone()));
        this.dataRefKey = postKey;
        this.postType = postType;
        ((Activity) context).overridePendingTransition(R.anim.slide_up_anim, R.anim.no_change);
    }

    public Boolean isCheckedCustomer() {
        return checkBox.isChecked();
    }

    public void bindPostCheck( final CUser cuser, String postKey, String postType) {
        this.user = cuser;
        name.setText(String.valueOf(cuser.getUserName()));
        email.setText(String.valueOf(cuser.getUemail()));
        phone.setText(String.valueOf(cuser.getUphone()));
        this.dataRefKey = postKey;
        this.postType = postType;
        checkBox.setVisibility(View.VISIBLE);

        ((Activity) context).overridePendingTransition(R.anim.slide_up_anim, R.anim.no_change);
    }
}
