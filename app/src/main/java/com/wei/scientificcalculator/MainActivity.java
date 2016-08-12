package com.wei.scientificcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wei.scientificcalculator.util.MathExpressionEvaluator;


public class MainActivity extends AppCompatActivity {

    private TextView mainInputView;
    private TextView historyView;
    private TextView angleUnitView;
    private TextView secFuncView;
    private String mainDisplay = "0";
    private String historyDisplay = "Ans = 0";
    private String angleUnitDisplay = "DEG";
    private String secFuncDisplay = "";

    private String lastAns = "0";
    private boolean isDegree = true;
    private boolean is2ndFunc = false;
    private boolean justCalculated = false;
    private int openParCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainInputView = (TextView) findViewById(R.id.mainInput);
        mainInputView.setText(mainDisplay);
        historyView = (TextView) findViewById(R.id.history);
        historyView.setText(historyDisplay);
        angleUnitView = (TextView) findViewById(R.id.angleUnit);
        angleUnitView.setText(angleUnitDisplay);
        secFuncView = (TextView) findViewById(R.id.secondFunc);
        secFuncView.setText(secFuncDisplay);
    }

    public void onClickUnits(View v) {
        Intent intent = new Intent(this, UnitsActivity.class);
        startActivity(intent);
    }

    public void onClickShapes(View v) {
        Intent intent = new Intent(this, ShapesActivity.class);
        startActivity(intent);
    }

    // Button "CLEAN"
    public void onClickClean(View v) {
        cleanScreen();
        updateScreen();
        v.setPadding(0, 0, 0, 0);
        ((Button) v).setGravity(Gravity.CENTER);
    }

    private void cleanScreen() {
        mainDisplay = "0";
        historyDisplay = "Ans = 0";
        angleUnitDisplay = "DEG";
        secFuncDisplay = "";
        lastAns = "0";
        isDegree = true;
        is2ndFunc = false;
        justCalculated = false;
        openParCount = 0;
    }

    private void updateScreen() {
        mainInputView.setText(mainDisplay);
        historyView.setText(historyDisplay);
        angleUnitView.setText(angleUnitDisplay);
        secFuncView.setText(secFuncDisplay);
    }

    // Button "2nd"
    public void onClickFunctionSwitch(View v) {
        if (is2ndFunc) {
            secFuncDisplay = "";
            is2ndFunc = false;
        }
        else {
            secFuncDisplay = "2nd";
            is2ndFunc = true;
        }

        secFuncView.setText(secFuncDisplay);
        v.setPadding(0, 0, 0, 0);
        ((Button) v).setGravity(Gravity.CENTER);
    }

    // Button "deg/rad"
    public void onClickAngleUnitSwitch(View v) {
        if (isDegree) {
            angleUnitDisplay = "RAD";
            isDegree = false;
        }
        else {
            angleUnitDisplay = "DEG";
            isDegree = true;
        }

        angleUnitView.setText(angleUnitDisplay);
        v.setPadding(0, 0, 0, 0);
        ((Button) v).setGravity(Gravity.CENTER);
    }

    // Button "DEL"
    public void onClickDelete(View v) {
        checkCalStateAndClean();

        int displayLen = mainDisplay.length();

        // remove 4-letter functions such as asin and sinh etc.
        if (mainDisplay.matches(".*[a-z]{4}[(]$")) {
            mainDisplay = mainDisplay.substring(0, displayLen - 5);
            openParCount--;
        }
        // remove 3-letter functions such as abs and sin etc.
        else if (mainDisplay.matches(".*[a-z]{3}[(]$")){
            mainDisplay = mainDisplay.substring(0, displayLen - 4);
            openParCount--;
        }
        // remove function ln
        else if (mainDisplay.endsWith("ln(")) {
            mainDisplay = mainDisplay.substring(0, displayLen - 3);
            openParCount--;
        }
        // remove function Ans
        else if (mainDisplay.endsWith("Ans")) {
            mainDisplay = mainDisplay.substring(0, displayLen - 3);
        }
        // remove others
        else {
            if (mainDisplay.endsWith("(")) {
                openParCount--;
            }
            mainDisplay = mainDisplay.substring(0, displayLen - 1);
        }

        mainDisplay = mainDisplay.trim();

        if (mainDisplay.isEmpty()) {
            mainDisplay = "0";
        }

        updateMainScreen();
        updateHistoryScreen();
        v.setPadding(0, 0, 0, 0);
        ((Button) v).setGravity(Gravity.CENTER);
    }

    private void checkCalStateAndClean() {
        if (justCalculated) {
            historyDisplay = "Ans = " + lastAns;
            mainDisplay = "0";
            justCalculated = false;
            openParCount = 0;
        }
    }

    private void updateMainScreen() {
        mainInputView.setText(mainDisplay);
    }

    private void updateHistoryScreen() {
        historyView.setText(historyDisplay);
    }

    // Number Buttons 0 - 9
    public void onClickNumber(View v) {
        checkCalStateAndClean();

        Button btn = (Button) v;
        String numStr = btn.getText().toString();
        char end = mainDisplay.charAt(mainDisplay.length() - 1);

        if (mainDisplay.equals("0")) {
            mainDisplay = numStr;
        }
        else if (isConstFactAns(end) || end == ')') {
            mainDisplay += " × " + numStr;
        }
        else if (isOperator(end)) {
            mainDisplay += " " + numStr;
        }
        else {
            mainDisplay += numStr;
        }

        updateMainScreen();
        updateHistoryScreen();
        btn.setPadding(0, 0, 0, 0);
        btn.setGravity(Gravity.CENTER);
    }

    private boolean isOperator(char c) {
        switch (c){
            case '+':
            case '-':
            case '×':
            case '÷':
            case '%':
            case '^':
                return true;
            default:
                return false;
        }
    }

    private boolean isConstFactAns(char c) {
        switch (c){
            case 'e':
            case 'π':
            case '!':
            case 's':
                return true;
            default:
                return false;
        }
    }

    // Operator Buttons + - × ÷ ^ % and Buttons . ( )
    public void onClickOperator(View v) {
        checkCalState();

        Button btn = (Button) v;
        String opStr = btn.getText().toString();
        char end = mainDisplay.charAt(mainDisplay.length() - 1);

        switch(opStr) {
            case "-":
                if (mainDisplay.equals("0")) {
                    mainDisplay = "-";
                }
                else if (end == '-') {
                    return;
                }
                else if (end == '+' || end =='%') {
                    mainDisplay = mainDisplay.substring(0, mainDisplay.length() - 1) + "-";
                }
                else {
                    mainDisplay += " -";
                }
                break;
            case "^":
            case "+":
            case "×":
            case "÷":
            case "%":
                if (mainDisplay.equals("-") || end == '(') {
                    return;
                }
                else if (isOperator(end)) {
                    mainDisplay = mainDisplay.substring(0, mainDisplay.length() - 1) + opStr;
                }
                else {
                    mainDisplay += " " + opStr;
                }
                break;
            case ".":
                if (end == '.') {
                    return;
                }
                else if (isConstFactAns(end) || end == ')') {
                    mainDisplay += " × .";
                }
                else if (isOperator(end)) {
                    mainDisplay += " .";
                }
                else {
                    mainDisplay += ".";
                }
                break;
            case "(":
                if (mainDisplay.equals("0")) {
                    mainDisplay = "(";
                }
                else if (isOperator(end)) {
                    mainDisplay += " (";
                }
                else {
                    mainDisplay += "(";
                }
                openParCount++;
                break;
            case ")":
                if (openParCount < 1 || isOperator(end)) {
                    return;
                }
                else {
                    mainDisplay += ")";
                    openParCount--;
                }
                break;
            default:
                return;
        }

        updateMainScreen();
        updateHistoryScreen();
        btn.setPadding(0, 0, 0, 0);
        btn.setGravity(Gravity.CENTER);
    }

    private void checkCalState() {
        if (justCalculated) {
            if (mainDisplay.equalsIgnoreCase("Syntax Error")) {
                mainDisplay = "0";
            }

            historyDisplay = "Ans = " + lastAns;
            justCalculated = false;
            openParCount = 0;
        }
    }

    // Constant Buttons π e
    public void onClickConstant(View v) {

        String cst = is2ndFunc ? "e" : "π";
        char end =mainDisplay.charAt(mainDisplay.length() - 1);

        if (mainDisplay.equals("0")) {
            mainDisplay = cst;
        }
        else if (isConstFactAns(end) || end == ')') {
            mainDisplay += " × " + cst;
        }
        else if (isOperator(end)) {
            mainDisplay += " " + cst;
        }
        else {
            mainDisplay += cst;
        }

        updateMainScreen();
        v.setPadding(0, 0, 0, 0);
        ((Button) v).setGravity(Gravity.CENTER);
    }

    // Trig Func Buttons sin cos tan asin acos atan sinh cosh
    public void onClickTrigFunction(View v) {
        checkCalState();

        Button btn = (Button) v;
        String func = btn.getText().toString();
        char end = mainDisplay.charAt(mainDisplay.length() - 1);

        if (!is2ndFunc) {
            if (mainDisplay.equals("0")) {
                mainDisplay = func + "(";
            }
            else if (end == '(') {
                mainDisplay += func + "(";
            }
            else if (end == 's'){
                mainDisplay += " × " + func + "(";
            }
            else {
                mainDisplay += " " + func + "(";
            }
        }
        else {
            switch(func) {
                case "sin":
                case "cos":
                case "tan":
                    if (mainDisplay.equals("0")) {
                        mainDisplay = "a" + func + "(";
                    }
                    else if (end == '(') {
                        mainDisplay += "a" + func + "(";
                    }
                    else if (end == 's') {
                        mainDisplay += " × a" + func + "(";
                    }
                    else {
                        mainDisplay += " a" + func + "(";
                    }
                    break;
                case "sinh":
                    if (mainDisplay.equals("0")) {
                        mainDisplay = "cosh(";
                    }
                    else if (end == '(') {
                        mainDisplay += "cosh(";
                    }
                    else if (end == 's'){
                        mainDisplay += " × cosh(";
                    }
                    else {
                        mainDisplay += " cosh(";
                    }
                    break;
                default:
                    return;
            }
        }

        openParCount++;
        updateMainScreen();
        updateHistoryScreen();
        btn.setPadding(0, 0, 0, 0);
        btn.setGravity(Gravity.CENTER);
    }

    // Other Func Buttons
    public void onClickOtherFunction(View v) {
        checkCalState();

        Button btn = (Button) v;
        String func = btn.getText().toString();
        char end = mainDisplay.charAt(mainDisplay.length() - 1);

        if (func.equalsIgnoreCase("abs")) {
            if (mainDisplay.equals("0")) {
                mainDisplay = "abs(";
            }
            else if (end == '(') {
                mainDisplay += "abs(";
            }
            else if (end == 's'){
                mainDisplay += " × abs(";
            }
            else {
                mainDisplay += " abs(";
            }

            openParCount++;
            updateMainScreen();
            updateHistoryScreen();
            btn.setPadding(0, 0, 0, 0);
            btn.setGravity(Gravity.CENTER);
            return;
        }

        if (func.equalsIgnoreCase("Ans")) {
            if (mainDisplay.equals("0")) {
                mainDisplay = "Ans";
            }
            else if (end == '(') {
                mainDisplay += "Ans";
            }
            else if (isConstFactAns(end) || end == ')') {
                mainDisplay += " × Ans";
            }
            else {
                mainDisplay += " Ans";
            }

            updateMainScreen();
            updateHistoryScreen();
            btn.setPadding(0, 0, 0, 0);
            btn.setGravity(Gravity.CENTER);
            return;
        }

        if (!is2ndFunc) {
            switch(func) {
                case "n!":
                    if (mainDisplay.equals("-") || end == '(') {
                        return;
                    }
                    else if (isOperator(end)) {
                        mainDisplay = mainDisplay.substring(0, mainDisplay.length() - 2) + "!";
                    }
                    else {
                        mainDisplay += "!";
                    }
                    break;
                case "x^2":
                case "x^3":
                    if (mainDisplay.equals("-") || end == '(') {
                        return;
                    }
                    else if (isOperator(end)) {
                        mainDisplay = mainDisplay.substring(0, mainDisplay.length() - 1) + func.substring(1);
                    }
                    else {
                        mainDisplay +=  " " + func.substring(1);
                    }
                    break;
                case "e^x":
                    if (mainDisplay.equals("0")) {
                        mainDisplay = "e^";
                    }
                    else if (isConstFactAns(end) || end ==')')  {
                        mainDisplay += " × e^";
                    }
                    else if (isOperator(end)) {
                        mainDisplay += " e^";
                    }
                    else {
                        mainDisplay += "e^";
                    }
                    break;
                case "10^x":
                    if (mainDisplay.equals("0")) {
                        mainDisplay = "10^";
                    }
                    else if (end =='(') {
                        mainDisplay += "10^";
                    }
                    else if (isOperator(end)) {
                        mainDisplay += " 10^";
                    }
                    else {
                        mainDisplay += " × 10^";
                    }
                    break;
                default:
                    return;
            }
        }
        else {
            switch(func) {
                case "n!":
                    if (mainDisplay.equals("-") || end == '(') {
                        return;
                    }
                    else if (isOperator(end)) {
                        mainDisplay = mainDisplay.substring(0, mainDisplay.length() - 1) + "^(-1)";
                    }
                    else {
                        mainDisplay += " ^(-1)";
                    }
                    break;
                case "x^2":
                    if (mainDisplay.equals("0")) {
                        mainDisplay = "√(";
                    }
                    else if (end == '(') {
                        mainDisplay += "√(";
                    }
                    else if (isOperator(end)){
                        mainDisplay += " √(";
                    }
                    else {
                        mainDisplay += " × √(";
                    }
                    openParCount++;
                    break;
                case "x^3":
                    if (mainDisplay.equals("0")) {
                        mainDisplay = "3√(";
                    }
                    else if (end == '(') {
                        mainDisplay += "3√(";
                    }
                    else if (isOperator(end)){
                        mainDisplay += " 3√(";
                    }
                    else {
                        mainDisplay += " × 3√(";
                    }
                    openParCount++;
                    break;
                case "e^x":
                    if (mainDisplay.equals("0")) {
                        mainDisplay = "ln(";
                    }
                    else if (end == '(') {
                        mainDisplay += "ln(";
                    }
                    else if (end == 's' || end == ')'){
                        mainDisplay += " × ln(";
                    }
                    else {
                        mainDisplay += " ln(";
                    }
                    openParCount++;
                    break;
                case "10^x":
                    if (mainDisplay.equals("0")) {
                        mainDisplay = "log(";
                    }
                    else if (end == '(') {
                        mainDisplay += "log(";
                    }
                    else if (end == 's' || end == ')'){
                        mainDisplay += " × log(";
                    }
                    else {
                        mainDisplay += " log(";
                    }
                    openParCount++;
                    break;
                default:
                    return;
            }
        }

        updateMainScreen();
        updateHistoryScreen();
        btn.setPadding(0, 0, 0, 0);
        btn.setGravity(Gravity.CENTER);
    }

    // Equal Button =
    public void onClickEqual(View v) {
        // match close parentheses to open parentheses
        while (openParCount > 0) {
            mainDisplay += ")";
            openParCount--;
        }
        historyDisplay = mainDisplay + " = ";

        // replace our symbols to the corresponding built-in operators and functions in exp4j lib
        String exprStr = convertToExprStr(mainDisplay);

        // Evaluator includes factorial operator and custom trig functions to handle degrees and radians
        MathExpressionEvaluator exprEval = new MathExpressionEvaluator(exprStr, isDegree);
        mainDisplay = exprEval.evaluate();

        // update lastAns value
        if (mainDisplay.equalsIgnoreCase("Syntax Error")) {
            lastAns = "0";
        }
        else {
            lastAns = mainDisplay;
        }

        updateMainScreen();
        updateHistoryScreen();
        v.setPadding(0, 0, 0, 0);
        ((Button) v).setGravity(Gravity.CENTER);
        justCalculated = true;
    }

    private String convertToExprStr(String str) {
        StringBuilder exprStr = new StringBuilder();
        int len = str.length();

        for (int i = 0; i < len; i++) {
            switch (str.charAt(i)) {
                case '×':
                    exprStr.append('*');
                    break;
                case '÷':
                    exprStr.append('/');
                    break;
                case '√':
                    if (i > 0 && str.charAt(i - 1) == '3') {
                        exprStr.deleteCharAt(exprStr.length() - 1);
                        exprStr.append("cbrt");
                    }
                    else {
                        exprStr.append("sqrt");
                    }
                    break;
                case 'l':
                    if (i < len - 1 && str.charAt(i + 1) == 'n') {
                        exprStr.append("log");
                        i++;
                    }
                    else {
                        exprStr.append("log10");
                        i += 2;
                    }
                    break;
                case 'A':
                    exprStr.append(lastAns);
                    i += 2;
                    break;
                default:
                   exprStr.append(str.charAt(i));
            }
        }

        return exprStr.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return item.getItemId() == R.id.menu_settings || super.onOptionsItemSelected(item);
    }
}
