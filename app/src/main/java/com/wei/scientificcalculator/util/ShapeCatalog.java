package com.wei.scientificcalculator.util;

import com.wei.scientificcalculator.R;
import com.wei.scientificcalculator.models.Shape;

import java.util.HashMap;
import java.util.Map;


public class ShapeCatalog {

    // use singleton Pattern
    private static ShapeCatalog catalogInstance = null;
    private Map<Integer, Shape> shapesMap = new HashMap<>();

    public static ShapeCatalog getInstance() {
        if (catalogInstance == null) {
            catalogInstance = new ShapeCatalog();
        }

        return catalogInstance;
    }

    // constructor
    private ShapeCatalog() {

        addShape(Shape.SPHERE, new Shape(Shape.SPHERE, R.string.sphere, R.drawable.sphere));
        addShape(Shape.CUBE, new Shape(Shape.CUBE, R.string.cubeShape, R.drawable.cube));
        addShape(Shape.CONE, new Shape(Shape.CONE, R.string.cone, R.drawable.cone));
        addShape(Shape.CYLINDER, new Shape(Shape.CYLINDER, R.string.cylinder, R.drawable.cylinder));
        addShape(Shape.CAPSULE, new Shape(Shape.CAPSULE, R.string.capsule, R.drawable.capsule));
        addShape(Shape.ELLIPSOID, new Shape(Shape.ELLIPSOID, R.string.ellipsoid, R.drawable.ellipsoid));
    }

    private void addShape(@Shape.ShapeIds int id, Shape shape) {
        shapesMap.put(id, shape);
    }

    public Shape getShapeById(@Shape.ShapeIds int id) {
        return shapesMap.get(id);
    }
}
