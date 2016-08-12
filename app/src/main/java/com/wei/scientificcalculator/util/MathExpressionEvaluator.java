package com.wei.scientificcalculator.util;

import android.util.Log;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import net.objecthunter.exp4j.function.Function;
import net.objecthunter.exp4j.operator.Operator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MathExpressionEvaluator {
    private String exprStr;
    private boolean isDegree;

    // constructor
    public MathExpressionEvaluator(String exprStr, boolean isDegree) {
        this.exprStr = exprStr;
        this.isDegree = isDegree;
    }

    // main evaluation method, return string result
    public String evaluate() {
        String resStr;
        Log.i("FULL EXPRESSION", exprStr);

        if(exprStr.equals("")) {
            return "";
        }

        // Create a custom operator "!" to calculate the factorial
        Operator factorial;

        // built-in trig functions only work for radians
        // rewrite them to handle both degrees and radians
        Function arcSinFunc;
        Function arcCosFunc;
        Function arcTanFunc;
        Function cosFunc;
        Function tanFunc;
        Function sinFunc;

        try {
            factorial = new Operator("!", 1, true, Operator.PRECEDENCE_POWER + 1) {
                @Override
                public double apply(double... args) {
                    final int arg = (int) args[0];
                    if ((double) arg != args[0] || arg < 0) {
                        throw new IllegalArgumentException("Operand for factorial has to be an non-negative integer");
                    }

                    double fact = 1;
                    for (int i = 1; i <= arg; i++) {
                        fact *= i;
                    }
                    return fact;
                }
            };

            sinFunc = new Function("sin") {
                @Override
                public double apply(double... args) {
                    if (isDegree) {
                        return Math.sin(Math.toRadians(args[0]));
                    }
                    else {
                        return Math.sin(args[0]);
                    }
                }
            };

            cosFunc = new Function("cos") {
                @Override
                public double apply(double... args) {
                    if (isDegree) {
                        return Math.cos(Math.toRadians(args[0]));
                    }
                    else {
                        return Math.cos(args[0]);
                    }
                }
            };

            tanFunc = new Function("tan") {
                @Override
                public double apply(double... args) {
                    if (isDegree) {
                        return Math.tan(Math.toRadians(args[0]));
                    }
                    else {
                        return Math.tan(args[0]);
                    }
                }
            };

            arcSinFunc = new Function("asin") {
                @Override
                public double apply(double... args) {
                    if (isDegree) {
                        return Math.toDegrees(Math.asin(args[0]));
                    }
                    else {
                        return Math.asin(args[0]);
                    }
                }
            };

            arcCosFunc = new Function("acos") {
                @Override
                public double apply(double... args) {
                    if (isDegree) {
                        return Math.toDegrees(Math.acos(args[0]));
                    }
                    else {
                        return Math.acos(args[0]);
                    }
                }
            };

            arcTanFunc = new Function("atan") {
                @Override
                public double apply(double... args) {
                    if (isDegree) {
                        return Math.toDegrees(Math.atan(args[0]));
                    }
                    else {
                        return Math.atan(args[0]);
                    }
                }
            };

            List<Function> customFuncs = new ArrayList<>();
            customFuncs.add(sinFunc);
            customFuncs.add(cosFunc);
            customFuncs.add(tanFunc);
            customFuncs.add(arcSinFunc);
            customFuncs.add(arcCosFunc);
            customFuncs.add(arcTanFunc);

            Expression expr = new ExpressionBuilder(exprStr).operator(factorial)
                    .functions(customFuncs).build();
            double res = expr.evaluate();

            Log.i("Full Result", String.valueOf(res));
            resStr = setDecimalFormat(6).format(res);
        } catch (Exception exc) {
            resStr = "Syntax Error";
            exc.printStackTrace();
        }

        return resStr;
    }

    private DecimalFormat setDecimalFormat(int n) {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMaximumFractionDigits(n);
        return decimalFormat;
    }
}