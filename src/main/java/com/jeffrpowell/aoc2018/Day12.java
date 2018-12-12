package com.jeffrpowell.aoc2018;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Day12 implements Solution<String>{
	private static final Pattern REGEX = Pattern.compile("(\\.|#)(\\.|#)(\\.|#)(\\.|#)(\\.|#) => (\\.|#)");
	private static List<Pot> pots;
	private static Set<Rule> rules;
	private static int frontIndex;
	private static int lastIndex;
	
	@Override
	public String part1(List<String> input)
	{
		String state = input.remove(0);
		pots = IntStream.range(0, state.length()).mapToObj(i -> new Pot(i, state.charAt(i))).collect(Collectors.toList());
		rules = input.stream().map(REGEX::matcher).map(Rule::new).collect(Collectors.toSet());
		frontIndex = 0;
		lastIndex = pots.size() - 1;
		IntStream.range(0, 20).forEach(i -> nextGen());
		return evaluateResult();
	}
	
	private static void nextGen() {
		padPotList();
//		SlidingWindowSpliterator.windowed(pots, 5)
	}
	
	private static void padPotList() {
		long plantsInFront = IntStream.range(0, 2).mapToObj(pots::get).map(Pot::hasAPlant).filter(b -> b).count();
		long plantsInBack = IntStream.range(pots.size() - 3, pots.size()).mapToObj(pots::get).map(Pot::hasAPlant).filter(b -> b).count();
		if (plantsInFront > 0) {
			frontIndex -= plantsInFront;
			LongStream.range(frontIndex, plantsInFront).forEach(i -> pots.add(new Pot(Long.valueOf(i).intValue(), false)));
		}
		if (plantsInBack > 0) {
			LongStream.range(lastIndex + 1, lastIndex + 1 + plantsInBack).forEach(i -> pots.add(new Pot(Long.valueOf(i).intValue(), false)));
			lastIndex += plantsInBack;
		}
	}
	
	private static String evaluateResult() {
		return "";
	}

	@Override
	public String part2(List<String> input)
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	private static boolean hasPlant(String s) {
		return s.equals("#");
	}

	private static boolean hasPlant(char c) {
		return c == '#';
	}
	
	private static class Pot {
		int id;
		boolean hasPlant;

		public Pot(int id, char hasPlantC)
		{
			this.id = id;
			this.hasPlant = hasPlant(hasPlantC);
		}

		public Pot(int id, boolean hasPlant)
		{
			this.id = id;
			this.hasPlant = hasPlant;
		}

		public int getId()
		{
			return id;
		}

		public boolean hasAPlant()
		{
			return hasPlant;
		}

		public void nextGen(boolean hasPlant)
		{
			this.hasPlant = hasPlant;
		}
		
	}
	
	private static class Rule {
		List<Boolean> plantArrangement;
		boolean result;
		
		public Rule(Matcher m) {
			this.plantArrangement = new ArrayList<>();
			plantArrangement.add(hasPlant(m.group(1)));
			plantArrangement.add(hasPlant(m.group(2)));
			plantArrangement.add(hasPlant(m.group(3)));
			plantArrangement.add(hasPlant(m.group(4)));
			plantArrangement.add(hasPlant(m.group(5)));
			result = hasPlant(m.group(6));
		}

		public boolean isResult()
		{
			return result;
		}
		
		public static int hash(List<Boolean> arrangement) {
			int hash = 7;
			hash = 41 * hash + Objects.hashCode(arrangement);
			return hash;
		}

		@Override
		public int hashCode()
		{
			return hash(plantArrangement);
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
			final Rule other = (Rule) obj;
			return Objects.equals(this.plantArrangement, other.plantArrangement);
		}
		
	}
}
