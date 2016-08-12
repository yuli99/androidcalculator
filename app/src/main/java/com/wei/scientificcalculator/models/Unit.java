package com.wei.scientificcalculator.models;

import android.support.annotation.IntDef;


public class Unit {

    // units of length
    public static final int INCH = 0;
    public static final int FOOT = 1;
    public static final int YARD = 2;
    public static final int MILE = 3;
    public static final int MILLIMETRE = 4;
    public static final int CENTIMETRE = 5;
    public static final int METRE = 6;
    public static final int KILOMETRE = 7;

    // units of area
    public static final int SQUARE_INCH = 100;
    public static final int SQUARE_FOOT = 101;
    public static final int SQUARE_YARD = 102;
    public static final int SQUARE_MILE = 103;
    public static final int SQUARE_CENTIMETRE = 104;
    public static final int SQUARE_METRE = 105;
    public static final int SQUARE_KILOMETRE = 106;
    public static final int HECTARE = 107;
    public static final int ACRE = 108;

    // units of volume
    public static final int CUBIC_INCH = 200;
    public static final int CUBIC_FOOT = 201;
    public static final int CUBIC_CENTIMETRE = 202;
    public static final int CUBIC_METRE = 203;
    public static final int MILLILITRE = 204;
    public static final int LITRE = 205;
    public static final int US_FLUID_OUNCE = 206;
    public static final int US_PINT = 207;
    public static final int US_QUART = 208;
    public static final int US_GALLON = 209;
    public static final int US_BARREL = 210;

    // units of pressure
    public static final int ATMOSPHERE = 300;
    public static final int PASCAL = 301;
    public static final int BAR = 302;
    public static final int PSI = 303;
    public static final int TORR = 304;

    // units of temperature
    public static final int CELSIUS = 400;
    public static final int FAHRENHEIT = 401;
    public static final int KELVIN = 402;

    // units of weight
    public static final int MILLIGRAM = 500;
    public static final int GRAM = 501;
    public static final int KILOGRAM = 502;
    public static final int OUNCE = 503;
    public static final int POUND = 504;
    public static final int STONE = 505;
    public static final int US_TON = 506;

    @IntDef({ // length
            INCH, FOOT, YARD, MILE, MILLIMETRE, CENTIMETRE, METRE, KILOMETRE,
            // area
            SQUARE_INCH, SQUARE_FOOT, SQUARE_YARD, SQUARE_MILE, SQUARE_CENTIMETRE,
            SQUARE_METRE, SQUARE_KILOMETRE, HECTARE, ACRE,
            // volume
            CUBIC_INCH, CUBIC_FOOT, CUBIC_CENTIMETRE, CUBIC_METRE, MILLILITRE, LITRE,
            US_FLUID_OUNCE, US_PINT, US_QUART, US_GALLON, US_BARREL,
            // pressure
            ATMOSPHERE, PASCAL, BAR, PSI, TORR,
            // temperature
            CELSIUS, FAHRENHEIT, KELVIN,
            // weight
            MILLIGRAM, GRAM, KILOGRAM, OUNCE, POUND, STONE, US_TON
    })
    public @interface UnitIds {}

    private int id;
    private int labelResource;
    private double toBaseUnit;
    private double fromBaseUnit;

    // constructor for temperature-related units
    public Unit(@UnitIds int id, int labelResource) {
        this.id = id;
        this.labelResource = labelResource;
        this.toBaseUnit = 0.0;
        this.fromBaseUnit = 0.0;
    }

    // constructor for other units
    public Unit(@UnitIds int id, int labelResource, double toBaseUnit, double fromBaseUnit) {
        this.id = id;
        this.labelResource = labelResource;
        this.toBaseUnit = toBaseUnit;
        this.fromBaseUnit = fromBaseUnit;
    }

    @UnitIds
    public int getId() {
        return id;
    }

    public int getLabelResource() {
        return labelResource;
    }

    public double getConversionToBaseUnit() {
        return toBaseUnit;
    }

    public double getConversionFromBaseUnit() {
        return fromBaseUnit;
    }
}
