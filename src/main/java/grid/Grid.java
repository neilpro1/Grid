package grid;

public class Grid {
	
	private double currentPrice;
	private double distance;
	
	public Grid() {
		this.currentPrice = -1;
		this.distance = -1;
	}
	
	public Grid(double distance) {
		this.distance = distance;
		this.currentPrice = -1;
	}
	
	public double getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public boolean setPrice(double price) {
		if(this.currentPrice == -1) {
			this.currentPrice = price;
			return true;
		}else if(this.currentPrice-this.distance >= price) {
			this.currentPrice -= this.distance;
			return true;
		}else if(this.currentPrice+distance <= price){
			this.currentPrice += this.distance;
			return false;
		}
		return false;
	}

	@Override
	public String toString() {
		return "Grid [currentPrice=" + currentPrice + ", distance=" + distance + "]";
	}
}
