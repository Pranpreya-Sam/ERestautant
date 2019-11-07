package com.example.e_restautant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.List;

/**
 * Created by Belal on 1/23/2018.
 */

public class Fragment_Home extends Fragment {

    ViewFlipper flipper;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View temp = inflater.inflate(R.layout.fragment_home, null);

        int image[]= {R.drawable.promo1, R.drawable.promo2, R.drawable.promo3};
        flipper = (ViewFlipper)temp.findViewById(R.id.flipper);

        for(int i=0; i<image.length; i++) {
            flipperImages(image[i]);
        }
        return temp;
    }

    public void flipperImages(int image){
        ImageView img = new ImageView(getContext());
        img.setBackgroundResource(image);
        flipper.addView(img);
        flipper.setFlipInterval(4000);
        flipper.setAutoStart(true);
    }
}