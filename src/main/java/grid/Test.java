package grid;

public class Test {
	public static void main(String[] args) {
		
		Grid grid = new Grid(10);
		
		System.out.println(grid.setPrice(100) + " " + grid.toString());
		System.out.println(grid.setPrice(89) + " " + grid.toString());

	}
}
