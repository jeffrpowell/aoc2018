package com.jeffrpowell.aoc2018;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;

public class Day11 implements Solution<Integer>
{
    private static final int GRID_SIZE = 300;
    private int serial;
    
    @Override
    public String part1(List<Integer> input) {
        serial = input.get(0);
        List<List<Integer>> powerLevels = new ArrayList<>();
        for (int x = 1; x <= GRID_SIZE; x++) {
            List<Integer> row = new ArrayList<>();
            for (int y = 1; y <= GRID_SIZE; y++) {
                row.add(getPowerLevel(new Point2D.Double(x, y)));
            }
            powerLevels.add(row);
        }
        Map<Point2D, Integer> sums = new HashMap<>();
        
        for (int x = 0; x < GRID_SIZE - 2; x++) {
            for (int y = 0; y < GRID_SIZE - 2; y++) {
                int sum = powerLevels.get(x).get(y) + 
                          powerLevels.get(x+1).get(y) + 
                          powerLevels.get(x+2).get(y) + 
                          powerLevels.get(x).get(y+1) + 
                          powerLevels.get(x+1).get(y+1) + 
                          powerLevels.get(x+2).get(y+1) + 
                          powerLevels.get(x).get(y+2) + 
                          powerLevels.get(x+1).get(y+2) + 
                          powerLevels.get(x+2).get(y+2);
                sums.put(new Point2D.Double(x + 1, y + 1), sum);
            }
        }
        Point2D highestSumPt = sums.entrySet().stream().reduce(BinaryOperator.maxBy(Comparator.comparing(Map.Entry::getValue))).get().getKey();
        return highestSumPt.getX() + "," + highestSumPt.getY();
    }
    
    private int getPowerLevel(Point2D pt) {
        double rackId = getRackId(pt);
        double powerLevelCipher = rackId * pt.getY();
        powerLevelCipher += serial;
        powerLevelCipher *= rackId;
        String powerLevelCipherText = Integer.toString(Double.valueOf(powerLevelCipher).intValue());
        int powerLevelCipherTextLength = powerLevelCipherText.length();
        int powerLevel;
        if (powerLevelCipherTextLength < 3) {
            powerLevel = 0;
        }
        else {
            powerLevel = Integer.parseInt(powerLevelCipherText.substring(powerLevelCipherTextLength - 3, powerLevelCipherTextLength - 2));
        }
        return powerLevel - 5;
    }
    
    private double getRackId(Point2D pt) {
        return pt.getX() + 10;
    }

    @Override
    public String part2(List<Integer> input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
