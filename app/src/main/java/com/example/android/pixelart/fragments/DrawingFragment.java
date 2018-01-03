package com.example.android.pixelart.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.pixelart.R;
import com.example.android.pixelart.interfaces.DrawingInterface;
import com.example.android.pixelart.models.Grid;
import com.example.android.pixelart.utils.CanvasView;

import org.w3c.dom.Text;

public class DrawingFragment extends Fragment {

    private CanvasView canvasView;
    private Grid grid;
    private int color;
    private String drawStyle;
    private ImageView colorAanduiding;
    private TextView styleDrawing;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_drawing, container, false);

        canvasView = (CanvasView) v.findViewById(R.id.canvas);

        v.findViewById(R.id.toolboxBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawingInterface) getActivity()).showToolboxFragment();
            }
        });
        v.findViewById(R.id.cameraBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DrawingInterface) getActivity()).dispatchTakePictureIntent();
            }
        });
        v.findViewById(R.id.clearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canvasView.clearCanvasDrawing();
            }
        });
        v.findViewById(R.id.eraseBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawStyle = "erase";
                styleDrawing.setText(drawStyle);
                canvasView.setDrawStyle(drawStyle);
            }
        });

        colorAanduiding = (ImageView) v.findViewById(R.id.colorDrawing);
        styleDrawing = (TextView) v.findViewById(R.id.styleDrawing);
        updateView();
        return v;
    }

    public void updateView() {
        this.color = ((DrawingInterface) getActivity()).getColor();
        this.drawStyle = ((DrawingInterface) getActivity()).getDrawStyle();
        this.grid = ((DrawingInterface) getActivity()).getGrid();

        canvasView.setGrid(this.grid);
        canvasView.setColor(this.color);
        canvasView.setDrawStyle(this.drawStyle);

        colorAanduiding.setColorFilter(this.color);
        styleDrawing.setText(this.drawStyle);
    }

    public void drawPhoto(Bitmap bitmap) {
        canvasView.drawPhoto(bitmap);
    }

    public void drawSavedImage(Grid grid) {
        canvasView.drawSavedImage(grid);
    }
}
