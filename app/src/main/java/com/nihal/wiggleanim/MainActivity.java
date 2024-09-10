package com.nihal.wiggledemo;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nihal.wiggle.WiggleEffect;
import com.nihal.wiggleanim.R;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnDragListener {

    private static final String TAG = "MainActivity";
    private WiggleEffect wiggleAnimation;
    private com.nihal.wiggledemo.HomeScreenAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        // Sample list of drawable resources for the grid items
        List<Integer> itemList = Arrays.asList(
                R.drawable.simple_shape, R.drawable.simple_shape,
                R.drawable.simple_shape, R.drawable.simple_shape,
                R.drawable.simple_shape, R.drawable.simple_shape,
                R.drawable.simple_shape, R.drawable.simple_shape // 2 more rows
        );

        // Set up RecyclerView with 3 columns and item spacing
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new HomeScreenAdapter(this, itemList);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 16, true));  // 16dp spacing, 3 columns

        // Start the wiggle animation when edit mode is entered
        adapter.setEditMode(true);

        // Set up drag-and-drop listeners
        recyclerView.setOnDragListener(this);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        Log.d(TAG, "onDrag event: " + event.toString());
        int action = event.getAction();
        View draggedView = (View) event.getLocalState();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                if (wiggleAnimation == null) {
                    wiggleAnimation = new WiggleEffect(draggedView);
                }
                wiggleAnimation.start();
                return true;
            case DragEvent.ACTION_DROP:
                ViewGroup owner = (ViewGroup) draggedView.getParent();
                owner.removeView(draggedView);
                ((RecyclerView) v).addView(draggedView);
                draggedView.setVisibility(View.VISIBLE);
                return true;
            case DragEvent.ACTION_DRAG_ENDED:
                if (wiggleAnimation != null) {
                    wiggleAnimation.stop();
                }
                draggedView.setVisibility(View.VISIBLE);
                return true;
            default:
                break;
        }
        return false;
    }
}
