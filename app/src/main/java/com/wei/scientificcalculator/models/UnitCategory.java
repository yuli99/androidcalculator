package com.wei.scientificcalculator.models;

import android.support.annotation.IntDef;

import java.util.List;


public class UnitCategory {

    public static final int LENGTH = 0;
    public static final int AREA = 1;
    public static final int VOLUME = 2;
    public static final int PRESSURE = 3;
    public static final int TEMPERATURE = 4;
    public static final int WEIGHT = 5;

    @IntDef({LENGTH, AREA, VOLUME, PRESSURE, TEMPERATURE, WEIGHT})
    public @interface CategoryIds{}

    private int id;
    private int labelResource;
    private List<Unit> units;

    // constructor
    public UnitCategory(@CategoryIds int id, int labelResource, List<Unit> units) {
        this.id = id;
        this.labelResource = labelResource;
        this.units = units;
    }

    @CategoryIds
    public int getId() {
        return id;
    }

    public int getLabelResource() {
        return labelResource;
    }

    public List<Unit> getUnits() {
        return units;
    }
}
