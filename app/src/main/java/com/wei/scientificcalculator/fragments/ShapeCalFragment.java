package com.wei.scientificcalculator.fragments;

import android.app.Fragment;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.wei.scientificcalculator.R;
import com.wei.scientificcalculator.models.Shape;
import com.wei.scientificcalculator.util.ShapeCalculator;
import com.wei.scientificcalculator.util.ShapeCatalog;

import java.text.DecimalFormat;


public class ShapeCalFragment extends Fragment {

    private static final String ARGS_SHAPE_ID = "shape_id";

    private EditText inputTextR, inputTextH;
    private EditText resultTextS, resultTextV;
    private int shapeId;

    public static ShapeCalFragment newInstance(@Shape.ShapeIds int id) {
        ShapeCalFragment f = new ShapeCalFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_SHAPE_ID, id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        shapeId = getArguments().getInt(ARGS_SHAPE_ID, Shape.SPHERE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_shapes, container, false);

        inputTextR = (EditText) v.findViewById(R.id.editRadius);
        inputTextH = (EditText) v.findViewById(R.id.editHeight);

        if (savedInstanceState == null) {
            inputTextR.setText("");
            inputTextR.setSelection(inputTextR.getText().length());
            inputTextH.setText("");
            inputTextH.setSelection(inputTextH.getText().length());
        }

        inputTextR.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        inputTextH.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        inputTextR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // past text from clipboard
                ClipboardManager clipboardManager =
                        (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                if (clipboardManager.hasPrimaryClip()) {
                    ClipDescription description = clipboardManager.getPrimaryClipDescription();
                    ClipData clipData = clipboardManager.getPrimaryClip();

                    if (clipData != null && description != null
                            && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                        String dataStr = String.valueOf(clipData.getItemAt(0).getText());
                        inputTextR.setText(dataStr);
                    }
                }

                return true;
            }
        });

        inputTextH.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // past text from clipboard
                ClipboardManager clipboardManager =
                        (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);

                if (clipboardManager.hasPrimaryClip()) {
                    ClipDescription description = clipboardManager.getPrimaryClipDescription();
                    ClipData clipData = clipboardManager.getPrimaryClip();

                    if (clipData != null && description != null
                            && description.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {

                        String dataStr = String.valueOf(clipData.getItemAt(0).getText());
                        inputTextH.setText(dataStr);
                    }
                }

                return true;
            }
        });


        resultTextS = (EditText) v.findViewById(R.id.editSurface);
        resultTextV = (EditText) v.findViewById(R.id.editVolume);

        resultTextS.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Copy text to clipboard
                ClipboardManager clipboardManager =
                        (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Shape Surface Area", ((EditText) v).getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(getActivity(), R.string.toast_copied_clipboard, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        resultTextV.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Copy text to clipboard
                ClipboardManager clipboardManager =
                        (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Shape Volume", ((EditText) v).getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(getActivity(), R.string.toast_copied_clipboard, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ImageView imageView = (ImageView) v.findViewById(R.id.imageView);
        Shape shape = ShapeCatalog.getInstance().getShapeById(shapeId);
        imageView.setImageResource(shape.getImageResource());

        ObservableScrollView scrollView =  (ObservableScrollView) v.findViewById(R.id.shape_value_container);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.attachToScrollView(scrollView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showResults(calculateVolumeAndSurfaceArea());
            }
        });

        return v;
    }

    private double[] calculateVolumeAndSurfaceArea() {
        String radiusStr = inputTextR.getText().toString();
        String heightStr = inputTextH.getText().toString();
        double[] inputValues = new double[] {0, 0};
        double[] results = new double[2];

        try {
            inputValues[0] = Double.parseDouble(radiusStr);
            inputValues[1] = Double.parseDouble(heightStr);

        } catch (NumberFormatException exc) {
            // do nothing
        }

        Shape shape = ShapeCatalog.getInstance().getShapeById(shapeId);
        ShapeCalculator shapeCalculator = new ShapeCalculator(shape, inputValues[0], inputValues[1]);

        results[0] = shapeCalculator.getShapeVolume();
        results[1] = shapeCalculator.getShapeSurfaceArea();

        return results;
    }

    private void showResults(double[] results) {

        resultTextV.setText(setDecimalFormat(6).format(results[0]));
        resultTextS.setText(setDecimalFormat(6).format(results[1]));
    }

    private DecimalFormat setDecimalFormat(int n) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(n);
        return decimalFormat;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                inputTextR.setText("");
                inputTextH.setText("");
                resultTextS.setText("");
                resultTextV.setText("");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
