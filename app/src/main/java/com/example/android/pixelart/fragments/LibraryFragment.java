package com.example.android.pixelart.fragments;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.pixelart.R;
import com.example.android.pixelart.adapters.DrawingCursorAdapter;
import com.example.android.pixelart.interfaces.DrawingInterface;
import com.example.android.pixelart.persistency.PixelArtDBContract;

public class LibraryFragment extends Fragment {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_library, container, false);

        ListView listView = v.findViewById(R.id.list);

        View emptyView = v.findViewById(R.id.empty_view);
        listView.setEmptyView(emptyView);

        listView.setAdapter(((DrawingInterface) getActivity()).getmCursorAdapter());

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Uri currentDrawingUri = ContentUris.withAppendedId(PixelArtDBContract.DrawingEntry.CONTENT_URI, id);
                ((DrawingInterface) getActivity()).setCurrentDrawingUri(currentDrawingUri);
                ((DrawingInterface) getActivity()).setShowDrawing(true);
                ((DrawingInterface) getActivity()).showDrawingFragment();
            }
        });
        return v;
    }
}
