package com.jeffrpowell.aoc2018;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tests
{
    public static final List<String> DAY3 = new ArrayList<>();
    public static final List<Point2D> DAY6 = new ArrayList<>();
	public static final List<String> DAY7 = new ArrayList<>();
	public static final List<Integer> DAY8 = new ArrayList<>();
	public static final List<Integer> DAY9 = new ArrayList<>();
	public static final List<String> DAY10 = new ArrayList<>();
    
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
		DAY7.add("Step C must be finished before step A can begin.");
		DAY7.add("Step C must be finished before step F can begin.");
		DAY7.add("Step A must be finished before step B can begin.");
		DAY7.add("Step A must be finished before step D can begin.");
		DAY7.add("Step B must be finished before step E can begin.");
		DAY7.add("Step D must be finished before step E can begin.");
		DAY7.add("Step F must be finished before step E can begin.");
		String day8 = "2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2";
		Pattern number = Pattern.compile("(\\d+)");
		Matcher m = number.matcher(day8);
		while(m.find()) {
			DAY8.add(Integer.parseInt(m.group(1)));
		}
		String day9 = "9 players; last marble is worth 25 points";
//		String day9 = "10 players; last marble is worth 1618 points"; 
		Matcher m9 = number.matcher(day9);
		while(m9.find()) {
			DAY9.add(Integer.parseInt(m9.group(1)));
		}
        DAY10.add("position=< 9,  1> velocity=< 0,  2>");
        DAY10.add("position=< 7,  0> velocity=<-1,  0>");
        DAY10.add("position=< 3, -2> velocity=<-1,  1>");
        DAY10.add("position=< 6, 10> velocity=<-2, -1>");
        DAY10.add("position=< 2, -4> velocity=< 2,  2>");
        DAY10.add("position=<-6, 10> velocity=< 2, -2>");
        DAY10.add("position=< 1,  8> velocity=< 1, -1>");
        DAY10.add("position=< 1,  7> velocity=< 1,  0>");
        DAY10.add("position=<-3, 11> velocity=< 1, -2>");
        DAY10.add("position=< 7,  6> velocity=<-1, -1>");
        DAY10.add("position=<-2,  3> velocity=< 1,  0>");
        DAY10.add("position=<-4,  3> velocity=< 2,  0>");
        DAY10.add("position=<10, -3> velocity=<-1,  1>");
        DAY10.add("position=< 5, 11> velocity=< 1, -2>");
        DAY10.add("position=< 4,  7> velocity=< 0, -1>");
        DAY10.add("position=< 8, -2> velocity=< 0,  1>");
        DAY10.add("position=<15,  0> velocity=<-2,  0>");
        DAY10.add("position=< 1,  6> velocity=< 1,  0>");
        DAY10.add("position=< 8,  9> velocity=< 0, -1>");
        DAY10.add("position=< 3,  3> velocity=<-1,  1>");
        DAY10.add("position=< 0,  5> velocity=< 0, -1>");
        DAY10.add("position=<-2,  2> velocity=< 2,  0>");
        DAY10.add("position=< 5, -2> velocity=< 1,  2>");
        DAY10.add("position=< 1,  4> velocity=< 2,  1>");
        DAY10.add("position=<-2,  7> velocity=< 2, -2>");
        DAY10.add("position=< 3,  6> velocity=<-1, -1>");
        DAY10.add("position=< 5,  0> velocity=< 1,  0>");
        DAY10.add("position=<-6,  0> velocity=< 2,  0>");
        DAY10.add("position=< 5,  9> velocity=< 1, -2>");
        DAY10.add("position=<14,  7> velocity=<-2,  0>");
        DAY10.add("position=<-3,  6> velocity=< 2, -1>");
    }
}
