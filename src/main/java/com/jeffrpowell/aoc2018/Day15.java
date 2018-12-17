package com.jeffrpowell.aoc2018;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day15 implements Solution<String>{

	private static final Comparator<Point2D> PT_COMPARATOR = (a, b) -> {
		double ax = a.getX();
		double ay = a.getY();
		double bx = b.getX();
		double by = b.getY();
		if (ay < by) {
			return -1;
		}
		if (ay > by) {
			return 1;
		}
		if (ax < bx) {
			return -1;
		}
		if (ax > bx) {
			return 1;
		}
		return 0;
	};
	private static final Comparator<Unit.PathNode> PATH_COMPARATOR = (a, b) -> {
		return Double.compare(a.getScore(), b.getScore());
	};
	private Map<Point2D, CellType> map;
	private List<Unit> goblins;
	private List<Unit> elves;
	private List<Unit> allUnits;
	
	@Override
	public String part1(List<String> input)
	{
		map = new HashMap<>();
		goblins = new ArrayList<>();
		elves = new ArrayList<>();
		allUnits = new ArrayList<>();
		for (int y = 0; y < input.size(); y++)
		{
			String row = input.get(y);
			for (int x = 0; x < row.length(); x++)
			{
				char c = row.charAt(x);
				Point2D pt = new Point2D.Double(x, y);
				map.put(pt, CellType.parse(row.charAt(x)));
				if (c == 'G') {
					Unit g = new Unit(pt, false);
					goblins.add(g);
					allUnits.add(g);
				}
				else if (c == 'E') {
					Unit e = new Unit(pt, true);
					elves.add(e);
					allUnits.add(e);
				}
			}
		}
		int turns = -1;
		boolean complete = false;
//		printGrid();
		while(!complete) {
			turns++;
			complete = evalRound();
		}
		return Integer.toString(allUnits.stream().map(Unit::getHp).reduce(0, Math::addExact) * turns);
	}

	@Override
	public String part2(List<String> input)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
	
	private boolean evalRound() {
		allUnits.sort(null);
		Set<Unit> eliminatedUnits = new HashSet<>();
		boolean combatEnded = false;
		int unitsWithTurnThisRound = 0;
		for (Unit unit : allUnits)
		{
			if (elves.isEmpty() || goblins.isEmpty()) {
				combatEnded = true;
				break;
			}
			unitsWithTurnThisRound++;
			if (eliminatedUnits.contains(unit)) {
				continue;
			}
			List<Unit> enemies = unit.isElf() ? goblins : elves;
			unit.tryMove(enemies);
			Optional<Unit> attackedUnit = unit.tryAttack(enemies);
			if (attackedUnit.isPresent()) {
				boolean dead = attackedUnit.get().takeDamage(3);
				if (dead) {
					printGrid();
					eliminatedUnits.add(attackedUnit.get());
					enemies.remove(attackedUnit.get());
					if (enemies.isEmpty()){
						if (unitsWithTurnThisRound < allUnits.size()) { //if the last unit is killed by the last unit in the rotation, count the round
							combatEnded = true;
						}
						break;
					}
				}
			}
//			printGrid();
		}
		allUnits.removeAll(eliminatedUnits);
		return combatEnded;
	}
	
	private void printGrid() {
		StringBuilder builder = new StringBuilder();
		for (int y = 0; y < 32; y++)
		{
			for (int x = 0; x < 32; x++)
			{
				Point2D pt = new Point2D.Double(x, y);
				CellType type = map.get(pt);
				if (type == CellType.WALL) {
					builder.append('#');
				}
				else {
					Optional<Unit> goblin = goblins.stream().filter(u -> u.getLocation().equals(pt)).findAny();
					if (goblin.isPresent()) {
						builder.append('G');
					}
					else {
						Optional<Unit> elf = elves.stream().filter(u -> u.getLocation().equals(pt)).findAny();
						if (elf.isPresent()) {
							builder.append('E');
						}
						else {
							builder.append('.');
						}
					}
				}
			}
			builder.append("\n");
		}
		System.out.println(builder.toString());
	}

	private static enum CellType {
		BLANK, WALL;
		
		public static CellType parse(char c) {
			if (c == '#') {
				return WALL;
			}
			else {
				return BLANK;
			}
		}
	}
	
	private class Unit implements Comparable<Unit> {
		private final boolean elf;
		private final PriorityQueue<PathNode> pathingQueue;
		private Point2D location;
		private int hp;

		public Unit(Point2D location, boolean elf)
		{
			this.elf = elf;
			this.pathingQueue = new PriorityQueue(PATH_COMPARATOR);
			this.location = location;
			this.hp = 200;
		}

		public Point2D getLocation()
		{
			return location;
		}

		public void setLocation(Point2D location)
		{
			this.location = location;
		}

		public boolean isElf()
		{
			return elf;
		}

		public int getHp()
		{
			return hp;
		}
		
		public boolean takeDamage(int damage) {
			hp -= damage;
			return hp <= 0;
		}
		
		public Set<Point2D> getOpenAdjacentPts() {
			return getOpenAdjacentPts(location);
		}
		
		public Set<Point2D> getOpenAdjacentPts(Point2D point) {
			return getAdjacentPts(point).stream()
				.filter(pt -> map.containsKey(pt))
				.filter(pt -> map.get(pt) == CellType.BLANK)
				.filter(pt -> pt.equals(location) || !allUnits.stream().map(Unit::getLocation).collect(Collectors.toSet()).contains(pt))
				.collect(Collectors.toSet());
		}
		
		public Set<Point2D> getAdjacentPts(Point2D point) {
			double x = point.getX();
			double y = point.getY();
			return Stream.of(new Point2D.Double(x, y - 1),
				new Point2D.Double(x - 1, y),
				new Point2D.Double(x + 1, y),
				new Point2D.Double(x, y + 1))
				.collect(Collectors.toSet());
		}
		
		public Optional<Unit> tryAttack(List<Unit> enemies) {
			Set<Point2D> adjacentPts = getAdjacentPts(location);
			return enemies.stream()
				.filter(e -> adjacentPts.contains(e.getLocation()))
				.sorted((e1, e2) -> {
					if (e1.getHp() < e2.getHp()) {
						return -1;
					}
					if (e2.getHp() < e1.getHp()) {
						return 1;
					}
					return PT_COMPARATOR.compare(e1.getLocation(), e2.getLocation());
				})
				.findFirst();
		}
		
		public void tryMove(List<Unit> enemies) {
			Set<Point2D> adjacentPts = getAdjacentPts(location);
			if (enemies.stream().map(Unit::getLocation).anyMatch(adjacentPts::contains)) {
				return;
			}
			Optional<List<Point2D>> targetPts = enemies.stream()
				.map(Unit::getOpenAdjacentPts)
				.flatMap(Set::stream)
				.map(this::getPathToPt)
				.filter(path -> !path.isEmpty())
				.sorted(Comparator.comparing(path -> path.size()))
				.reduce(BinaryOperator.minBy((path1, path2) -> {
					if (path1.size() != path2.size()) {
						return Integer.compare(path1.size(), path2.size());
					}
					else {
						return PT_COMPARATOR.compare(path1.get(0), path2.get(0)); //Might be a bug; may need to establish reading order per target first, then do global distance
					}
				}));
			targetPts.ifPresent(path -> location = path.get(0));
		}
		
		private List<Point2D> getPathToPt(Point2D targetPt) {
			pathingQueue.clear();
			Set<Point2D> visitedPts = new HashSet<>();
			List<List<Point2D>> possibleAnswers = new ArrayList<>();
			//should be faster to search in reverse direction, due to frequent crowding around targets
			pathingQueue.add(new PathNode(null, 0, targetPt, location));
			while(!pathingQueue.isEmpty()) {
				final PathNode node = pathingQueue.poll();
				if (!possibleAnswers.isEmpty() && node.distance > possibleAnswers.get(0).size()) {
					if (!node.pt.equals(location)) {
						visitedPts.add(node.pt);
					}
					continue;
				}
				if (node.pt.equals(location)) {
					List<Point2D> answer = new ArrayList<>();
					PathNode searchNode = node;
					//don't add first point to answer; it's the location that the unit is already at
					while(searchNode.previous != null) {
						searchNode = searchNode.previous;
						answer.add(searchNode.pt);
					}
					if (answer.size() < possibleAnswers.size()) {
						possibleAnswers.clear();
					}
					possibleAnswers.add(answer);
					continue;
				}
				visitedPts.add(node.pt);
				getOpenAdjacentPts(node.pt).stream()
					.filter(p -> !visitedPts.contains(p))
					.forEach(p -> pathingQueue.offer(new PathNode(node, node.distance + 1, p, location)));
			}
			return possibleAnswers.stream().sorted((a1, a2) -> PT_COMPARATOR.compare(a1.get(0), a2.get(0))).findFirst().orElse(Collections.emptyList());
		}

		@Override
		public int compareTo(Unit o)
		{
			return PT_COMPARATOR.compare(location, o.getLocation());
		}

		@Override
		public int hashCode()
		{
			int hash = 3;
			hash = 43 * hash + Objects.hashCode(this.location);
			hash = 43 * hash + (this.elf ? 1 : 0);
			return hash;
		}

		@Override
		public boolean equals(Object obj)
		{
			if (this == obj)
			{
				return true;
			}
			if (obj == null)
			{
				return false;
			}
			if (getClass() != obj.getClass())
			{
				return false;
			}
			final Unit other = (Unit) obj;
			if (this.elf != other.elf)
			{
				return false;
			}
			return Objects.equals(this.location, other.location);
		}

		@Override
		public String toString()
		{
			return (elf ? "E" : "G") + hp + " ("+location.getX()+","+location.getY()+")";
		}
		
		public class PathNode {
			PathNode previous;
			int distance;
			Point2D pt;
			Point2D target;

			public PathNode(PathNode previous, int distance, Point2D pt, Point2D target)
			{
				this.previous = previous;
				this.distance = distance;
				this.pt = pt;
				this.target = target;
			}

			public double getScore() {
				return distance + pt.distance(target);
			}
			
			@Override
			public String toString() {
				return distance + " -> " + pt.getX() + "," + pt.getY();
			}
		}
	}
	
}
