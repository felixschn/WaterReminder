package com.example.waterreminder2.util;

import androidx.recyclerview.widget.RecyclerView;

public class VerticalSpacingItemDecorator extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public VerticalSpacingItemDecorator(int verticalSpaceHeight){
        this.verticalSpaceHeight = verticalSpaceHeight;
    }
}
