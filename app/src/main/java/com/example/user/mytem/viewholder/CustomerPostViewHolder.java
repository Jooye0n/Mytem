package com.example.user.mytem.viewholder;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.mytem.R;
import com.example.user.mytem.singleton.CUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CustomerPostViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private TextView name;
    private TextView email;
    private TextView phone;
    private Context context;
    private String dataRefKey;
    private CUser user;

    public CustomerPostViewHolder( View itemView ) {
        super(itemView);
        context = itemView.getContext();
        ImageView call = itemView.findViewById(R.id.customer_call_btn);
        ImageView mail = itemView.findViewById(R.id.customer_mail_btn);

        phone = itemView.findViewById(R.id.customer_phone);
        name = itemView.findViewById(R.id.customer_name);
        email = itemView.findViewById(R.id.customer_email);

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

    public void bindPost( final CUser cuser, String postKey) {
        this.user = cuser;
        name.setText(String.valueOf(cuser.getUserName()));
        email.setText(String.valueOf(cuser.getUemail()));
        phone.setText(String.valueOf(cuser.getUphone()));
        this.dataRefKey = postKey;

    }
}
