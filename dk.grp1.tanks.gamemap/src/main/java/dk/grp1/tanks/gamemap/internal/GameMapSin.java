package dk.grp1.tanks.gamemap.internal;

import dk.grp1.tanks.common.data.IGameMapFunction;
import dk.grp1.tanks.common.utils.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class GameMapSin implements IGameMapFunction {
    private double amplitude;
    private double angularFrequency;
    private float phaseShift;
    private float shiftConstant;

    private float startX;
    private float endX;

    public GameMapSin(float startX, float endX) {
        this(1,1,0,0,startX,endX);
    }

    public GameMapSin(double amplitude, double angularFrequency, float phaseShift, float shiftConstant, float startX, float endX) {
        this.amplitude = amplitude;
        this.angularFrequency = angularFrequency;
        this.phaseShift = phaseShift;
        this.shiftConstant = shiftConstant;
        this.startX = startX;
        this.endX = endX;
    }

    /**
     * Create a sin function based on the sucessor. This ensures a continuous function
     * @param amplitude
     * @param angularFrequency
     * @param phaseShift
     * @param sucessorFn
     * @param startX
     * @param endX
     */
    public GameMapSin(double amplitude,double angularFrequency, float phaseShift, IGameMapFunction sucessorFn, float startX, float endX){
        this(amplitude,angularFrequency,phaseShift,0,startX,endX);
        this.shiftConstant = (float) (-amplitude*Math.sin(angularFrequency*sucessorFn.getEndX()+phaseShift)+sucessorFn.getYValue(sucessorFn.getEndX()));
        }
    @Override
    public float getStartX() {
        return startX;
    }

    @Override
    public float getYValue(float xValue) {
        return (float) (amplitude * Math.sin((angularFrequency * xValue + phaseShift)) + shiftConstant);
    }

    @Override
    public float getEndX() {
        return endX;
    }

    @Override
    public List<Float> getYValues(List<Float> xValues) {
        List<Float> yValues = new ArrayList<>();
        for (Float xValue : xValues) {
            yValues.add(getYValue(xValue));
        }
        return yValues;
    }

    @Override
    public boolean isWithin(float x) {
        if(this.startX <= x && this.endX > x){
            return true;
        }
        return false;
    }

    @Override
    public void setEndX(float value) {
        this.endX = value;
    }

    @Override
    public void setStartX(float value) {
        this.startX = value;
    }

    @Override
    public boolean existsOnlyWithinRange(float startX, float endX) {
        return (this.startX > startX && this.endX < endX);
    }

    @Override
    public List<IGameMapFunction> splitInTwoWithNewRanges(float rangeOneStartX, float rangeOneEndX, float rangeTwoStartX, float rangeTwoEndX) {
        List<IGameMapFunction> splitGameMapFunctions = new ArrayList<>();
        splitGameMapFunctions.add(new GameMapSin(this.amplitude,this.angularFrequency,this.phaseShift,this.shiftConstant,rangeOneStartX,rangeOneEndX));
        splitGameMapFunctions.add(new GameMapSin(this.amplitude,this.angularFrequency,this.phaseShift,this.shiftConstant,rangeTwoStartX,rangeTwoEndX));
        return splitGameMapFunctions;
    }

}
