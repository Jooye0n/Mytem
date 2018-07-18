package com.example.user.mytem.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.user.mytem.R;

public class HomeDinamisFragment extends Fragment {

    private Context context;
    private TextView tvDetailFragment;
    private String detail;
    private ImageView imageView12345;
    private int image;
    private int position;

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_dinamis, container, false);
        imageView12345 = (ImageView)view.findViewById(R.id.imageView12345);
        context = getContext();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDetailFragment = (TextView) view.findViewById(R.id.text_view_detail_fragment);
        tvDetailFragment.setText(getDetail());

        imageView12345 = (ImageView)view.findViewById(R.id.imageView12345);
        imageView12345.setImageResource(getImageView());
    }

//    public void getImageView() {
//        return imageView;
//
//        Log.i("position",Integer.toString(position));
//
//        switch (position) {
//
//            case 0:
//                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.home1));
//                break;
//
//            case 1:
//                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.home1));
//                break;
//
//            case 2:
//                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.home1));
//                break;
//
//            case 3:
//                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.home1));
//                break;
//
//            case 4:
//                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.home1));
//                break;
//
//            default:
//                imageView.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.home1));
//                break;
//
//
// imageView.setImageResource(R.drawable.home1);
//    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public int getImageView() {
        setPosition(this.position);
        return image;
    }

    public void setPosition(int position) {
        this.position = position;
        Log.v("position",Integer.toString(position));

        switch (position) {
            case 0:
                this.image = R.drawable.home1;
                break;
            case 1:
                this.image = R.drawable.home2;
                break;
            case 2:
                this.image = R.drawable.home3;
                break;
            case 3:
                this.image = R.drawable.home1;
                break;
            case 4:
                this.image = R.drawable.home2;
                break;

        }
    }
}