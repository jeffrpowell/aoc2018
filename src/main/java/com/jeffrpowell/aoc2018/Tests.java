package com.jeffrpowell.aoc2018;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Tests
{
    public static final List<String> DAY3 = new ArrayList<>();
    public static final List<Point2D> DAY6 = new ArrayList<>();
    
    static {
        DAY3.add("#1 @ 1,3: 4x4");
        DAY3.add("#2 @ 3,1: 4x4");
        DAY3.add("#3 @ 5,5: 2x2");
        DAY6.add(new Point2D.Double(1, 1));
        DAY6.add(new Point2D.Double(1, 6));
        DAY6.add(new Point2D.Double(8, 3));
        DAY6.add(new Point2D.Double(3, 4));
        DAY6.add(new Point2D.Double(5, 5));
        DAY6.add(new Point2D.Double(8, 9));
    }
}
