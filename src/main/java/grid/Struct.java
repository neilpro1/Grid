package grid;

import struct.DiskMap;
import struct.DiskList;

class Grid {
	private final String NAME_VAR = "grid";

	private DiskMap<Integer, Double> grid;
	private int counter;
	private double min, max, space;

	public Grid(double min, double max, double space) {
		this.grid = new DiskMap<>(Grid.class, this.NAME_VAR);
		this.counter = 0;
		this.min = min;
		this.max = max;
		this.space = space;
		this.createGrid();
	}

	public Grid(String name, double min, double max, double space) {
		this.grid = new DiskMap<>(Grid.class, this.NAME_VAR + name);
		this.counter = 0;
		this.min = min;
		this.max = max;
		this.space = space;
		this.createGrid();
	}

	public DiskMap<Integer, Double> getGrid() {
		return this.grid;
	}

	public void extendGrid(double max) {
		do {
			this.grid.put(this.counter++, this.min);
			this.min += this.space;
		} while (this.min <= max);
	}

	private void createGrid() {
		do {
			this.grid.put(this.counter++, this.min);
			this.min += this.space;
		} while (this.min <= this.max);
	}
}

class History {
	private double qty;
	private double price;

	public History(double qty, double price) {
		this.qty = qty;
		this.price = price;
	}

	public double getQty() {
		return this.qty;
	}

	public double getPrice() {
		return this.price;
	}

	public void setQty(double qty) {
		this.qty = qty;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getValue() {
		return this.price * this.qty;
	}
}

class HistoryTrade {
	private final String NAME_VAR_BUY = "buy";
	private final String NAME_VAR_SELL = "sell";

	private DiskList<History> buy;
	private DiskList<History> sell;

	public HistoryTrade() {
		this.buy = new DiskList<History>(HistoryTrade.class, this.NAME_VAR_BUY);
		this.sell = new DiskList<History>(HistoryTrade.class, this.NAME_VAR_SELL);
	}

	public HistoryTrade(String name) {
		this.buy = new DiskList<History>(HistoryTrade.class, this.NAME_VAR_BUY + name);
		this.sell = new DiskList<History>(HistoryTrade.class, this.NAME_VAR_SELL + name);
	}

	public void buy(double qty, double price) {
		this.buy.add(new History(qty, price));
	}

	public void sell(int index) {
		History h = this.buy.get(index);
		this.buy.delete(index);
		this.sell.add(h);
	}

	public DiskList<History> getBuy() {
		return this.buy;
	}

	public DiskList<History> getSell() {
		return this.sell;
	}

	public double getDiff() {
		double diff = 0;
		for (int i = 0; i < this.buy.size(); i++) {
			diff -= this.buy.get(i).getValue();
		}

		for (int i = 0; i < this.sell.size(); i++) {
			diff += this.sell.get(i).getValue();
		}
		return diff;
	}
}

public class Struct {

	private Grid grid;
	private HistoryTrade history;
	private String name;
	private int index;
	private double balance;

	public Struct(double balance, String name, double min, double max, double space) {
		this.name = name;
		this.grid = new Grid(name, min, max, space);
		this.history = new HistoryTrade(name);
		this.index = 0;
		this.balance = balance;
	}

	public Struct(String name, double min, double max, double space) {
		this.name = name;
		this.grid = new Grid(name, min, max, space);
		this.history = new HistoryTrade(name);
		this.index = 0;
		this.balance = -1;
	}

	public Struct(double balance, double min, double max, double space) {
		this.name = "";
		this.grid = new Grid(min, max, space);
		this.history = new HistoryTrade();
		this.index = 0;
		this.balance = balance;
	}

	public Struct(double min, double max, double space) {
		this.name = "";
		this.grid = new Grid(min, max, space);
		this.history = new HistoryTrade();
		this.index = 0;
		this.balance = -1;
	}

	public String getName() {
		return this.name;
	}
	
	public double getBalance() {
		return this.balance;
	}
	
	public void setPrice(double price, double value) {

		double qty = value / price;
		double lastPrice = this.grid.getGrid().get(index);

		if (lastPrice > price) {// buy
			for (int i = index - 1; i > -1; i--) {
				double priceGrid = this.grid.getGrid().get(i);
				if (priceGrid > 0 && priceGrid > price) {
					if (this.balance == -1 || balance >= value) {
						this.history.buy(qty, priceGrid);
						this.balance -= value;
						index = i;
					}
				}
			}
		} else {// sell
			int numberOfQty = this.history.getBuy().size();
			for(int i = numberOfQty; i > -1; i++) {
				double priceToSell = this.grid.getGrid().get(index + 1);
				History history = this.history.getBuy().get(i);
				if(priceToSell < price && priceToSell > history.getPrice()) {
					this.history.sell(i);
					index++;
				}
				
			}
		}
	}
}
