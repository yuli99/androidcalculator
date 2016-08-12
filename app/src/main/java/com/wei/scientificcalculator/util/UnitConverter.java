package com.wei.scientificcalculator.util;

import com.wei.scientificcalculator.models.Unit;

import java.math.BigDecimal;


public class UnitConverter {

    private Unit from;
    private Unit to;
    double inputValue;

    // constructor
    public UnitConverter(Unit from, Unit to, double inputValue) {
        this.from = from;
        this.to = to;
        this.inputValue = inputValue;
    }

    public double convertTemperature() {

        if (to.getId() == from.getId()) {
            return inputValue;
        }

        if (to.getId() == Unit.CELSIUS) {
            return convertToCelsius();
        }
        else if (to.getId() == Unit.FAHRENHEIT) {
            return convertToFahrenheit();
        }
        else if (to.getId() == Unit.KELVIN) {
                return convertToKelvin();
        }
        else {
            return inputValue;
        }
    }

    private double convertToCelsius() {

        if (from.getId() == Unit.FAHRENHEIT) {
            return (inputValue - 32) * 5 / 9;  // from F to C
        }
        else if (from.getId() == Unit.KELVIN) {
            return inputValue - 273.15;   // from K to C
        }
        else {
            return inputValue;
        }
    }

    private double convertToFahrenheit() {

        if (from.getId() == Unit.CELSIUS) {
            return inputValue * 9 / 5 + 32;  // from C to F
        }
        else if (from.getId() == Unit.KELVIN) {
            return inputValue * 9 / 5 - 459.67;  // from K to F
        }
        else {
            return inputValue;
        }
    }

    private double convertToKelvin() {

        if (from.getId() == Unit.CELSIUS) {
            return inputValue + 273.15;    // from C to K
        }
        else if (from.getId() == Unit.FAHRENHEIT) {
            return (inputValue + 459.67) * 5 / 9;  // from F to K
        }
        else {
            return inputValue;
        }
    }

    public double convertOthers() {

        if (from.getId() != to.getId()) {
            BigDecimal multiplier = new BigDecimal(from.getConversionToBaseUnit())
                    .multiply(new BigDecimal(to.getConversionFromBaseUnit()));
            BigDecimal resBD = new BigDecimal(inputValue).multiply(multiplier);

            return resBD.doubleValue();
        }
        else {
            return inputValue;
        }
    }
}
