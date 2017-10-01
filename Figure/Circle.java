package FirstPackage;

import java.util.Scanner;

public class Circle {
	private float R; // Radius
	public static int NumberFigure = 0;
	
	// Compute perimeter
	public float getPerimeter() {
		return (float)(2 * Math.PI * this.R);
	}
	// Compute area
	public float getArea() {
		return (float)(Math.PI * this.R * this.R);
	}
	// Input / output
	public void InputInfo() {
		Scanner sc = new Scanner(System.in);
		System.out.print("Input R: ");
		this.R = Float.parseFloat(sc.nextLine());
	}
	public void OuputInfo() {
		System.out.printf("Circle with R = %.1f\n", this.R);
	}

	// Constructor
	public Circle(float r) {
		super();
		NumberFigure++;
		R = r;
	}
	public Circle() {
		super();
		NumberFigure++;
		R = 0;
	}
	
	// Get/set method
	public float getR() {
		return R;
	}
	public void setR(float r) {
		R = r;
	}

	// Main
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Circle A = new Circle(3);
		A.InputInfo();
		System.out.print("the info of this circle: ");
		A.OuputInfo();
		System.out.printf("P : %.2f\n", A.getPerimeter());
		System.out.printf("S : %.2f\n", A.getArea());
	}
}
