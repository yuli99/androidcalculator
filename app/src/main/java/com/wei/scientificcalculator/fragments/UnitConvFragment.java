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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;

import com.wei.scientificcalculator.R;
import com.wei.scientificcalculator.models.Unit;
import com.wei.scientificcalculator.models.UnitCategory;
import com.wei.scientificcalculator.util.UnitCatalog;
import com.wei.scientificcalculator.util.UnitConverter;

import java.text.DecimalFormat;


public class UnitConvFragment extends Fragment
        implements RadioGroup.OnCheckedChangeListener {

    private static final String ARGS_UNIT_CATEGORY_ID = "unit_category_id";

    private RadioGroup groupFrom, groupTo;
    private EditText inputText, resultText;
    private int unitCategoryId;

    public static UnitConvFragment newInstance(@UnitCategory.CategoryIds int id) {
        UnitConvFragment f = new UnitConvFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_UNIT_CATEGORY_ID, id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
        unitCategoryId = getArguments().getInt(ARGS_UNIT_CATEGORY_ID, UnitCategory.LENGTH);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_units, container, false);

        inputText = (EditText) v.findViewById(R.id.editInput);
        if (savedInstanceState == null) {
            inputText.setText("");
            inputText.setSelection(inputText.getText().length());
        }

        inputText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL
                | InputType.TYPE_NUMBER_FLAG_SIGNED);

        inputText.setOnLongClickListener(new View.OnLongClickListener() {
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
                        inputText.setText(dataStr);
                    }
                }

                return true;
            }
        });


        resultText = (EditText) v.findViewById(R.id.editResult);
        resultText.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // Copy text to clipboard
                ClipboardManager clipboardManager =
                        (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("Unit Conversion Result", ((EditText) v).getText().toString());
                clipboardManager.setPrimaryClip(clipData);

                Toast.makeText(getActivity(), R.string.toast_copied_clipboard, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        groupFrom = (RadioGroup) v.findViewById(R.id.radio_group_from);
        groupTo = (RadioGroup) v.findViewById(R.id.radio_group_to);
        addUnitsToView();

        ObservableScrollView scrollView =  (ObservableScrollView) v.findViewById(R.id.unit_list_container);
        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
        fab.attachToScrollView(scrollView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConversionResult(convertUnit());
            }
        });

        return v;
    }

    private void addUnitsToView() {
        UnitCategory currCategory = UnitCatalog.getInstance().getCategoryById(unitCategoryId);
        RadioGroup.LayoutParams layoutParams =
                new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i < currCategory.getUnits().size(); i++) {
            Unit unit = currCategory.getUnits().get(i);
            boolean checkedFrom = false;
            boolean checkedTo = false;

            // set default checked units
            if (i == 0) checkedFrom = true;
            if (i == 1) checkedTo = true;

            groupFrom.addView(getRadioButton(unit, checkedFrom, groupFrom), layoutParams);
            groupTo.addView(getRadioButton(unit, checkedTo, groupTo), layoutParams);
        }
    }

    private RadioButton getRadioButton(Unit u, boolean isChecked, ViewGroup parent) {

        RadioButton btn = (RadioButton) LayoutInflater.from(getActivity()).inflate(R.layout.radio_button_units, parent, false);
        btn.setId(u.getId());
        btn.setTag(u);
        btn.setText(u.getLabelResource());
        btn.setChecked(isChecked);
        return btn;
    }

    private double convertUnit() {

        String inputStr = inputText.getText().toString();
        double value = 0;
        double res;

        try {
            value = Double.parseDouble(inputStr);

        } catch (NumberFormatException exc) {
            // do nothing
        }

        Unit unitFrom = getCheckedUnit(groupFrom);
        Unit unitTo = getCheckedUnit(groupTo);
        UnitConverter unitConverter = new UnitConverter(unitFrom, unitTo, value);

        if (unitCategoryId == UnitCategory.TEMPERATURE) {
            res = unitConverter.convertTemperature();
        }
        else {
            res = unitConverter.convertOthers();
        }

        return res;
    }

    private Unit getCheckedUnit(RadioGroup radioGroup) {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        RadioButton btn = (RadioButton) radioGroup.findViewById(checkedId);
        return (Unit) btn.getTag();
    }

    private void showConversionResult(double result) {
        String resultStr;
        if (unitCategoryId == UnitCategory.TEMPERATURE) {
            resultStr = setDecimalFormat(2).format(result);
        }
        else {
            resultStr = setDecimalFormat(6).format(result);
        }

        resultText.setText(resultStr);
    }

    private DecimalFormat setDecimalFormat(int n) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(n);
        return decimalFormat;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        // handle changing checked button in radio group
        radioGroup.clearCheck();
        radioGroup.check(checkedId);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_clear:
                inputText.setText("");
                resultText.setText("");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
