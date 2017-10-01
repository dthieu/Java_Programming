package FirstPackage;

import java.util.Scanner;

public class Rectangle {
	private float width;
	private float height;
	
	public static int NumberFigure = 0;
	
	// Compute perimeter
	public float getPerimeter() {
		return (this.width + this.height) * 2;
	}
	// Compute area
	public float getArea() {
		return (this.width * this.height);
	}
	// Input / output
	public void InputInfo() {
		Scanner sc = new Scanner(System.in);
		float tmp1;
		float tmp2;
		
		while ( this.width == 0  ||
				this.width < 0   ||
				this.height == 0 ||
				this.height < 0  ||
				this.width == this.height)
		{
			System.out.print("Input width : ");
			tmp1 = Float.parseFloat(sc.nextLine());
			System.out.print("Input height: ");
			tmp2 = Float.parseFloat(sc.nextLine());
			// width > height
			this.width  = (tmp1 > tmp2) ? tmp1 : tmp2;
			this.height = (tmp1 < tmp2) ? tmp1 : tmp2;
		}
	}
	public void OuputInfo() {
		System.out.printf("Rectangle with: width = %.1f, height = %.1f\n", this.width, this.height);
	}

	// Constructor
	public Rectangle(float width, float height) {
		super();
		NumberFigure++;
		this.width = width;
		this.height = height;
	}
	public Rectangle() {
		super();
		NumberFigure++;
		this.width = 0.0f;
		this.height = 0.0f;
	}
	
	// Get/set method
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	
	// Main
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			Rectangle A = new Rectangle();
			A.InputInfo();
			A.OuputInfo();
			System.out.printf("Perimeter: %.1f\n", A.getPerimeter());
			System.out.printf("Area     : %.1f\n", A.getArea());
			Rectangle B = new Rectangle(12, 10);
			System.out.printf("Perimeter: %.1f\n", B.getPerimeter());
			System.out.printf("Area     : %.1f\n", B.getArea());
			System.out.print("Number of Object: " + B.NumberFigure);
		}
	
}
