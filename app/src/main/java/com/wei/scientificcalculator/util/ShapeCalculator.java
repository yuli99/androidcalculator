package com.wei.scientificcalculator.util;

import com.wei.scientificcalculator.models.Shape;


public class ShapeCalculator {

    private Shape shape;
    private double radius;
    private double height;

    // constructor
    public ShapeCalculator(Shape shape, double radius, double height) {
        this.shape = shape;
        this.radius = radius;
        this.height = height;
    }

    public double getShapeVolume() {
        double vol = 0;

        switch (shape.getId()) {
            case Shape.SPHERE:
                vol = 4 * Math.PI * Math.pow(radius, 3.0) / 3;
                break;
            case Shape.CUBE:
                vol = Math.pow(height, 3.0);
                break;
            case Shape.CONE:
                vol = Math.PI * Math.pow(radius, 2.0) * height / 3;
                break;
            case  Shape.CYLINDER:
                vol = Math.PI * Math.pow(radius, 2.0) * height;
                break;
            case Shape.CAPSULE:
                vol = Math.PI * Math.pow(radius, 2.0) * (height + 4 / 3 * radius);
                break;
            case Shape.ELLIPSOID:
                vol = 4 * Math.PI * Math.pow(radius, 2.0) * height / 3;
                break;
        }

        return vol;
    }

    public double getShapeSurfaceArea() {
        double surArea = 0;

        switch (shape.getId()) {
            case Shape.SPHERE:
                surArea = 4 * Math.PI * Math.pow(radius, 2.0);
                break;
            case Shape.CUBE:
                surArea = 6 * Math.pow(height, 2.0);
                break;
            case Shape.CONE:
                double temp = Math.sqrt(radius * radius + height * height);
                surArea = Math.PI * radius * (radius + temp);
                break;
            case  Shape.CYLINDER:
                surArea = 2 * Math.PI * radius * (radius + height);
                break;
            case Shape.CAPSULE:
                surArea = 2 * Math.PI * radius * (2 * radius + height);
                break;
            case Shape.ELLIPSOID:
                double temp2 = Math.pow(radius, 3.2) / 3 + 2 * Math.pow(radius * height, 1.6) /3;
                surArea = 4 * Math.PI * Math.pow(temp2, 1 / 1.6);
                break;
        }

        return surArea;
    }

}
