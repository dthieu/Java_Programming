package FirstPackage;

import java.util.Scanner;

public class Point {
	private int x;
	private int y;
	public Point() {
		this.x = 0;
		this.y = 0;
	}
	public Point(int _x, int _y) {
		this.x = _x;
		this.y = _y;
	}
	public float calcDistance(Point _p) {
		float reval = 0.0f;
		reval = (float)Math.sqrt((this.x - _p.x) * (this.x - _p.x) + (this.y - _p.y) * (this.y - _p.y));
		return reval;
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public void printPoint() {
		System.out.printf("(%d,%d)\n", this.x, this.y);
	}
	public void inputPoint() {
		int x, y;
		Scanner sc = new Scanner(System.in);
		System.out.print("Input x: ");
		x = Integer.parseInt(sc.nextLine());
		System.out.print("Input y: ");
		y = Integer.parseInt(sc.nextLine());
		this.x = x;
		this.y = y;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Point A = new Point();
		A.inputPoint();
		Point B = new Point(0, 5);
		System.out.print("This is pos of A: ");
		A.printPoint();
		System.out.print("This is pos of B: ");
		B.printPoint();
		System.out.println("The Distance between 2 Points : " + A.calcDistance(B));
	}
}
