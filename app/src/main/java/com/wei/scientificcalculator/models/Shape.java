package com.wei.scientificcalculator.models;

import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;


public class Shape {

    public static final int SPHERE = 0;
    public static final int CUBE = 1;
    public static final int CONE = 2;
    public static final int CYLINDER = 3;
    public static final int CAPSULE = 4;
    public static final int ELLIPSOID = 5;

    @IntDef({SPHERE, CUBE, CONE, CYLINDER, CAPSULE, ELLIPSOID})
    public @interface ShapeIds{}

    private int id;
    private int labelResource;
    private int imageResource;

    // constructor
    public Shape(@ShapeIds int id, int labelResource, int imageResource) {
        this.id = id;
        this.labelResource = labelResource;
        this.imageResource = imageResource;
    }

    @ShapeIds
    public int getId() {
        return id;
    }

    public int getLabelResource() {
        return labelResource;
    }

    public int getImageResource() {
        return imageResource;
    }
}

