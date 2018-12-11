package com.jeffrpowell.aoc2018;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day11 implements Solution<Integer>
{
    private static final int GRID_SIZE = 300;
    private int serial;
    
    @Override
    public String part1(List<Integer> input) {
        serial = input.get(0);
        List<List<Integer>> powerLevels = IntStream.rangeClosed(1, GRID_SIZE).mapToObj(x -> 
            IntStream.rangeClosed(1, GRID_SIZE).mapToObj(y -> 
                new Point2D.Double(x, y)
            ).map(this::getPowerLevel).collect(Collectors.toList())
        ).collect(Collectors.toList());
        Map<Point2D, Integer> sums = new HashMap<>();
        Iterator<List<Integer>> powerLevelsIterator = powerLevels.iterator();
        List<Integer> firstRow;
        List<Integer> secondRow = powerLevelsIterator.next();
        List<Integer> thirdRow = powerLevelsIterator.next();
        for (int y = 0; y < GRID_SIZE - 2; y++) {
            firstRow = secondRow;
            secondRow = thirdRow;
            thirdRow = powerLevelsIterator.next();
            
            Iterator<Integer> powerLevelFirstRowIterator = firstRow.iterator();
            Iterator<Integer> powerLevelSecondRowIterator = secondRow.iterator();
            Iterator<Integer> powerLevelThirdRowIterator = thirdRow.iterator();
            Integer first_1;
            Integer first_2 = powerLevelFirstRowIterator.next();
            Integer first_3 = powerLevelFirstRowIterator.next();
            Integer second_1;
            Integer second_2 = powerLevelSecondRowIterator.next();
            Integer second_3 = powerLevelSecondRowIterator.next();
            Integer third_1;
            Integer third_2 = powerLevelThirdRowIterator.next();
            Integer third_3 = powerLevelThirdRowIterator.next();
            for(int x = 0; x < GRID_SIZE - 2; x++) {
                first_1 = first_2;
                first_2 = first_3;
                first_3 = powerLevelFirstRowIterator.next();
                second_1 = second_2;
                second_2 = second_3;
                second_3 = powerLevelSecondRowIterator.next();
                third_1 = third_2;
                third_2 = third_3;
                third_3 = powerLevelThirdRowIterator.next();
                sums.put(new Point2D.Double(x, y), first_1 + first_2 + first_3 + second_1 + second_2 + second_3 + third_1 + third_2 + third_3);
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
        return pt.getX();
    }

    @Override
    public String part2(List<Integer> input) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
