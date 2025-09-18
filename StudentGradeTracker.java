package com.saravanan;

import java.util.ArrayList;
import java.util.Scanner;

class Student {
	private String name;
	private int score;

	public Student(String name, int score) {
		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}
}

public class StudentGradeTracker {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		ArrayList<Student> students = new ArrayList<>();

		System.out.print("Enter number of students: ");
		int n = scanner.nextInt();
		scanner.nextLine();

		for (int i = 0; i < n; i++) {
			System.out.print("Enter student's name: ");
			String name = scanner.nextLine();
			System.out.print("Enter student's score: ");
			int score = scanner.nextInt();
			scanner.nextLine();
			students.add(new Student(name, score));
		}
		int total = 0;
		int highest = Integer.MIN_VALUE;
		int lowest = Integer.MAX_VALUE;
		String highestStudent = "", lowestStudent = "";

		for (Student s : students) {
			int sc = s.getScore();
			total += sc;
			if (sc > highest) {
				highest = sc;
				highestStudent = s.getName();
			}
			if (sc < lowest) {
				lowest = sc;
				lowestStudent = s.getName();
			}
		}
		double average = (double) total / n;

		System.out.println("\n--- Summary Report ---");
		for (Student s : students) {
			System.out.println("Student: " + s.getName() + ", Score: " + s.getScore());
		}
		System.out.println("Average Score: " + average);
		System.out.println("Highest Score: " + highest + " (by " + highestStudent + ")");
		System.out.println("Lowest Score: " + lowest + " (by " + lowestStudent + ")");
	}
}
