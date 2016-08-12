package com.wei.scientificcalculator.util;

import com.wei.scientificcalculator.R;
import com.wei.scientificcalculator.models.Unit;
import com.wei.scientificcalculator.models.UnitCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UnitCatalog {

    // use singleton Pattern
    private static UnitCatalog catalogInstance = null;
    private Map<Integer, UnitCategory> unitsMap = new HashMap<>();

    public static UnitCatalog getInstance() {
        if (catalogInstance == null) {
            catalogInstance = new UnitCatalog();
        }

        return catalogInstance;
    }

    // constructor
    private UnitCatalog() {
        addLengthConversions();
        addAreaConversions();
        addVolumeConversions();
        addPressureConversions();
        addTemperatureConversions();
        addWeightConversions();
    }

    // choose Metre as base unit for length
    private void addLengthConversions() {
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(Unit.INCH, R.string.inch, 0.0254, 39.3700787401574803));
        units.add(new Unit(Unit.FOOT, R.string.foot, 0.3048, 3.28083989501312336));
        units.add(new Unit(Unit.YARD, R.string.yard, 0.9144, 1.09361329833770779));
        units.add(new Unit(Unit.MILE, R.string.mile, 1609.344, 0.00062137119223733397));
        units.add(new Unit(Unit.MILLIMETRE, R.string.millimetre, 0.001, 1000.0));
        units.add(new Unit(Unit.CENTIMETRE, R.string.centimetre, 0.01, 100.0));
        units.add(new Unit(Unit.METRE, R.string.metre, 1.0, 1.0));
        units.add(new Unit(Unit.KILOMETRE, R.string.kilometre, 1000.0, 0.001));

        addConversion(UnitCategory.LENGTH, new UnitCategory(UnitCategory.LENGTH, R.string.length, units));
    }

    private void addConversion(@UnitCategory.CategoryIds int id, UnitCategory category) {
        unitsMap.put(id, category);
    }

    // choose Square Metre as base unit for area
    private void addAreaConversions() {
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(Unit.SQUARE_INCH, R.string.sq_inch, 0.00064516, 1550.00310000620001));
        units.add(new Unit(Unit.SQUARE_FOOT, R.string.sq_foot, 0.09290304, 10.7639104167097223));
        units.add(new Unit(Unit.SQUARE_YARD, R.string.sq_yard, 0.83612736, 1.19599004630108026));
        units.add(new Unit(Unit.SQUARE_MILE, R.string.sq_mile, 2589988.11, 0.0000003861));
        units.add(new Unit(Unit.SQUARE_CENTIMETRE, R.string.sq_centimetre, 0.0001, 10000.0));
        units.add(new Unit(Unit.SQUARE_METRE, R.string.sq_metre, 1.0, 1.0));
        units.add(new Unit(Unit.SQUARE_KILOMETRE, R.string.sq_kilometre, 1000000.0, 0.000001));
        units.add(new Unit(Unit.HECTARE, R.string.hectare, 10000.0, 0.0001));
        units.add(new Unit(Unit.ACRE, R.string.acre, 4046.8564224, 0.000247105381467165342));

        addConversion(UnitCategory.AREA, new UnitCategory(UnitCategory.AREA, R.string.area, units));
    }

    // choose Cubic Metre as base unit for Volume
    private void addVolumeConversions() {
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(Unit.CUBIC_INCH, R.string.cb_inch, 0.000016387064, 61023.744094732284));
        units.add(new Unit(Unit.CUBIC_FOOT, R.string.cb_foot, 0.028316846592, 35.3146667214885903));
        units.add(new Unit(Unit.CUBIC_CENTIMETRE, R.string.cb_centimetre, 0.000001, 1000000.0));
        units.add(new Unit(Unit.CUBIC_METRE, R.string.cb_metre, 1.0, 0.1));
        units.add(new Unit(Unit.MILLILITRE, R.string.millilitre, 0.000001, 1000000.0));
        units.add(new Unit(Unit.LITRE, R.string.litre, 0.001, 1000.0));
        units.add(new Unit(Unit.US_FLUID_OUNCE, R.string.fluid_ounce, 0.0000295735295625, 33814.0227018429972));
        units.add(new Unit(Unit.US_PINT, R.string.pint, 0.000473176473, 2113.37641886518732));
        units.add(new Unit(Unit.US_QUART, R.string.quart, 0.000946352946, 1056.68820943259366));
        units.add(new Unit(Unit.US_GALLON, R.string.gallon, 0.003785411784, 264.172052358148415));
        units.add(new Unit(Unit.US_BARREL, R.string.barrel, 0.119240471196, 8.38641436057614017079));

        addConversion(UnitCategory.VOLUME, new UnitCategory(UnitCategory.VOLUME, R.string.volume, units));
    }

    // choose Pascal as base unit for pressure
    private void addPressureConversions() {
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(Unit.ATMOSPHERE, R.string.atmosphere, 101325.0, 0.0000098692326671601283));
        units.add(new Unit(Unit.PASCAL, R.string.pascal, 1.0, 1.0));
        units.add(new Unit(Unit.BAR, R.string.bar, 100000.0, 0.00001));
        units.add(new Unit(Unit.PSI, R.string.psi, 6894.757293168361, 0.000145037737730209222));
        units.add(new Unit(Unit.TORR, R.string.torr, 133.3223684210526315789, 0.00750061682704169751));

        addConversion(UnitCategory.PRESSURE, new UnitCategory(UnitCategory.PRESSURE, R.string.pressure, units));
    }

    private void addTemperatureConversions() {
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(Unit.CELSIUS, R.string.celsius));
        units.add(new Unit(Unit.FAHRENHEIT, R.string.fahrenheit));
        units.add(new Unit(Unit.KELVIN, R.string.kelvin));

        addConversion(UnitCategory.TEMPERATURE, new UnitCategory(UnitCategory.TEMPERATURE, R.string.temperature, units));
    }

    // choose Kilogram as base unit for Weight
    private void addWeightConversions() {
        List<Unit> units = new ArrayList<>();
        units.add(new Unit(Unit.MILLIGRAM, R.string.milligram, 0.000001, 1000000.0));
        units.add(new Unit(Unit.GRAM, R.string.gram, 0.001, 1000.0));
        units.add(new Unit(Unit.KILOGRAM, R.string.kilogram, 1.0, 1.0));
        units.add(new Unit(Unit.OUNCE, R.string.ounce, 0.028349523125, 35.27396194958041291568));
        units.add(new Unit(Unit.POUND, R.string.pound, 0.45359237, 2.20462262184877581));
        units.add(new Unit(Unit.STONE, R.string.stone, 6.35029318, 0.15747304441777));
        units.add(new Unit(Unit.US_TON, R.string.us_ton, 907.18474, 0.0011023113109243879));

        addConversion(UnitCategory.WEIGHT, new UnitCategory(UnitCategory.WEIGHT, R.string.weight, units));
    }

    public UnitCategory getCategoryById(@UnitCategory.CategoryIds int id) {
        return unitsMap.get(id);
    }

}
