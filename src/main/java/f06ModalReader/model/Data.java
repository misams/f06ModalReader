package f06ModalReader.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Model class for Modal Data
 *
 * @author Michael Sams
 */
public class Data {

    private final IntegerProperty mode;
	private final DoubleProperty frequency;
	private final DoubleProperty x;
	private final DoubleProperty y;
	private final DoubleProperty z;
	private final DoubleProperty rx;
	private final DoubleProperty ry;
	private final DoubleProperty rz;


    /**
     * Default constructor.
     */

    /**
     * Default constructor.
     */
    public Data() {
        this(0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
    }
    
    public Data( int m, double f, double x, double y, double z, double rx, double ry, double rz ) {
    	this.mode = new SimpleIntegerProperty(m);
    	this.frequency = new SimpleDoubleProperty(f);
    	this.x = new SimpleDoubleProperty(x);
    	this.y = new SimpleDoubleProperty(y);
    	this.z = new SimpleDoubleProperty(z);
    	this.rx = new SimpleDoubleProperty(rx);
    	this.ry = new SimpleDoubleProperty(ry);
    	this.rz = new SimpleDoubleProperty(rz);
    }

    // mode
    public int getMode() {
        return mode.get();
    }
    public void setMode(int mode) {
    	this.mode.set(mode);
    }
    public IntegerProperty modeProperty() {
    	return mode;
    }

    // frequency
    public double getFrequency() {
        return frequency.get();
    }
    public void setFrequency(double frequency) {
    	this.frequency.set(frequency);
    }
    public DoubleProperty frequencyProperty() {
    	return frequency;
    }
    
    // x
    public double getX() {
        return x.get();
    }
    public void setX(double x) {
    	this.x.set(x);
    }
    public DoubleProperty xProperty() {
    	return x;
    }
    
    // y
    public double getY() {
        return y.get();
    }
    public void setY(double y) {
    	this.y.set(y);
    }
    public DoubleProperty yProperty() {
    	return y;
    }
    
    // z
    public double getZ() {
        return z.get();
    }
    public void setZ(double z) {
    	this.z.set(z);
    }
    public DoubleProperty zProperty() {
    	return z;
    }
    
    // rx
    public double getRX() {
        return rx.get();
    }
    public void setRX(double rx) {
    	this.rx.set(rx);
    }
    public DoubleProperty rxProperty() {
    	return rx;
    }
    
    // ry
    public double getRY() {
        return ry.get();
    }
    public void setRY(double ry) {
    	this.ry.set(ry);
    }
    public DoubleProperty ryProperty() {
    	return ry;
    }
    
    // rz
    public double getRZ() {
        return rz.get();
    }
    public void setRZ(double rz) {
    	this.rz.set(rz);
    }
    public DoubleProperty rzProperty() {
    	return rz;
    }

}