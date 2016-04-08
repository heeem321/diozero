package com.diozero.sandpit;

/*
 * #%L
 * Device I/O Zero - Core
 * %%
 * Copyright (C) 2016 mattjlewis
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * #L%
 */


import com.diozero.api.AnalogInputDevice;
import com.diozero.internal.spi.AnalogInputDeviceFactoryInterface;
import com.diozero.util.DeviceFactoryHelper;

/**
 * Supports taking readings from a <a href="https://en.wikipedia.org/wiki/Potentiometer">potentiometer</a>.
 */
public class Potentiometer extends AnalogInputDevice {
	private float vRef;
	private float r1;
	
	/**
	 * @param pinNumber Pin to which the potentiometer is connected.
	 * @param vRef Reference voltage.
	 * @param r1 Resistor between the potentiometer and ground.
	 */
	public Potentiometer(int pinNumber, float vRef, float r1) {
		this(DeviceFactoryHelper.getNativeDeviceFactory(), pinNumber, vRef, r1);
	}
	
	public Potentiometer(AnalogInputDeviceFactoryInterface deviceFactory, int pinNumber, float vRef, float r1) {
		super(deviceFactory, pinNumber, vRef);
		
		this.vRef = vRef;
		this.r1 = r1;
	}
	
	@Override
	public float getScaledValue() {
		return getResistance();
	}
	
	/**
	 * Read the current resistance setting for the potentiometer.
	 * @return Resistance (Ohm).
	 */
	public float getResistance() {
		float v_pot = super.getScaledValue();
		float r_pot = r1 / (vRef / v_pot - 1);
		
		return r_pot;
	}
}