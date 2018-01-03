package com.example.android.pixelart.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.pixelart.R;
import com.example.android.pixelart.activities.MainActivity;
import com.example.android.pixelart.activities.ToolboxActivity;
import com.example.android.pixelart.interfaces.DrawingInterface;
import com.example.android.pixelart.models.Grid;
import com.example.android.pixelart.utils.CanvasView;

public class ToolboxFragment extends Fragment {

    private int color;
    private String drawStyle;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_toolbox, container, false);

        v.findViewById(R.id.greenBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = getResources().getColor(android.R.color.holo_green_dark);
                ((DrawingInterface) getActivity()).setColor(color);
            }
        });
        v.findViewById(R.id.blueBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = getResources().getColor(android.R.color.holo_blue_dark);
                ((DrawingInterface) getActivity()).setColor(color);
            }
        });
        v.findViewById(R.id.redBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = getResources().getColor(android.R.color.holo_red_dark);
                ((DrawingInterface) getActivity()).setColor(color);
            }
        });
        v.findViewById(R.id.purpleBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                color = getResources().getColor(android.R.color.holo_purple);
                ((DrawingInterface) getActivity()).setColor(color);
            }
        });
        v.findViewById(R.id.squareBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "square";
                ((DrawingInterface) getActivity()).setDrawStyle(drawStyle);
            }
        });
        v.findViewById(R.id.lineBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "line";
                ((DrawingInterface) getActivity()).setDrawStyle(drawStyle);
            }
        });
        v.findViewById(R.id.freeBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "free";
                ((DrawingInterface) getActivity()).setDrawStyle(drawStyle);
            }
        });

        return v;
    }

}
