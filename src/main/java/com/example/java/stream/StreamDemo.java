package com.example.java.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDemo {

	public void filterDemo() {

		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT), new Dish("beef", false, 700,
				Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("rice", true, 350,
				Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 400,
				Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));
		List<Dish> vegetarianMenu = menu.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
		for (Dish dish : vegetarianMenu) {
			System.out.println(dish.name);
		}
	}

	public void distinctDemo() {

		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT), new Dish("beef", false, 700,
				Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("rice", true, 350,
				Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 400,
				Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));
		List<Dish> vegetarianMenu = menu.stream().distinct().collect(Collectors.toList());
		menu.stream().distinct().forEach(System.out::print);

		for (Dish dish : vegetarianMenu) {
			System.out.println(dish.name);
		}
	}

	public void limitDemo() {

		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT), new Dish("beef", false, 700,
				Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("rice", true, 350,
				Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 400,
				Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));
		List<Dish> vegetarianMenu = menu.stream().filter(d->d.getCalories()>300).limit(3).collect(Collectors.toList());
		/*
		 * menu.stream() .distinct() .forEach(System.out::print);
		 */

		for (Dish dish : vegetarianMenu) {
			System.out.println(dish.name);
		}
	}
	
	
	
	public void skipDemo() {

		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT), new Dish("beef", false, 700,
				Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("rice", true, 350,
				Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 400,
				Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));
		List<Dish> vegetarianMenu = menu.stream().filter(d->d.getCalories()>300).skip(2).collect(Collectors.toList());
		/*
		 * menu.stream() .distinct() .forEach(System.out::print);
		 */

		for (Dish dish : vegetarianMenu) {
			System.out.println(dish.name);
		}
	}
	
	
	public void mapDemo() {

		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT), new Dish("beef", false, 700,
				Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("rice", true, 350,
				Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 400,
				Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));
		List<Integer> vegetarianMenu = menu.stream().map(Dish::getName).map(String::length).collect(Collectors.toList());
		/*
		 * menu.stream() .distinct() .forEach(System.out::print);
		 */

		for (Integer dish : vegetarianMenu) {
			System.out.println(dish);
		}
	}
	
	
	public void flatMapDemo() {

		List<Dish> menu = Arrays.asList(new Dish("pork", false, 800, Dish.Type.MEAT), new Dish("beef", false, 700,
				Dish.Type.MEAT), new Dish("chicken", false, 400, Dish.Type.MEAT), new Dish("rice", true, 350,
				Dish.Type.OTHER), new Dish("pizza", true, 550, Dish.Type.OTHER), new Dish("prawns", false, 400,
				Dish.Type.FISH), new Dish("salmon", false, 450, Dish.Type.FISH));
		List<Integer> vegetarianMenu = menu.stream().map(Dish::getName).map(String::length).collect(Collectors.toList());
		/*
		 * menu.stream() .distinct() .forEach(System.out::print);
		 */

		for (Integer dish : vegetarianMenu) {
			System.out.println(dish);
		}
	}
	

	public static void main(String[] args) {
		StreamDemo streamDemo = new StreamDemo();
		streamDemo.mapDemo();
	}

	static class Dish {
		private String name;
		boolean vegetarian;
		private final int calories;
		private final Type type;

		public Dish(String name, boolean vegetarian, int calories, Type type) {
			this.name = name;
			this.vegetarian = vegetarian;
			this.calories = calories;
			this.type = type;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isVegetarian() {
			return vegetarian;
		}

		public void setVegetarian(boolean vegetarian) {
			this.vegetarian = vegetarian;
		}

		public int getCalories() {
			return calories;
		}

		public Type getType() {
			return type;
		}

		public enum Type {
			MEAT, FISH, OTHER
		}
	}

}
