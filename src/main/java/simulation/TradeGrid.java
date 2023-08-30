package simulation;

import java.util.List;

import grid.Grid;

import java.util.ArrayList;

class History {
	private double qty;
	private double priceBuy;
	private double priceSell;

	public History(double qty, double priceToBuy, double priceToSell) {
		this.qty = qty;
		this.priceBuy = priceToBuy;
		this.priceSell = priceToSell;
	}

	public double getQty() {
		return this.qty;
	}

	public double getPriceToBuy() {
		return this.priceBuy;
	}

	public double getPriceToSell() {
		return this.priceSell;
	}

	public double getValueBuy() {
		return this.qty * this.priceBuy;
	}

	public double getValueSell() {
		return this.qty * this.priceSell;
	}
}

public class TradeGrid {

	private List<History> historyOfTrade;
	private double balance, initialBalance, value, qty;
	private Grid grid;

	public TradeGrid(double balance, double distance) {
		this.historyOfTrade = new ArrayList<>();
		this.balance = balance;
		this.initialBalance = balance;
		this.grid = new Grid(distance);
	}

	public boolean setPrice(double price, double value) {
		boolean result = this.grid.setPrice(price);
		boolean resultToReturn = false;
		while (result && this.balance > value) {
			double qty = value / price;
			double priceBuy = this.grid.getCurrentPrice();
			this.balance -= qty * priceBuy;
			this.value += value;
			this.qty += qty;
			double priceToSell = priceBuy + this.grid.getDistance();
			this.historyOfTrade.add(new History(qty, priceBuy, priceToSell));
			resultToReturn = true;
			result = this.grid.setPrice(price);
		}

		if (!this.historyOfTrade.isEmpty()) {
			for (int i = this.historyOfTrade.size() - 1; i > -1; i--) {
				History history = this.historyOfTrade.get(i);
				if (history.getPriceToSell() <= price) {
					this.balance += history.getQty() * history.getPriceToSell();
					this.value -= value;
					this.qty -= history.getQty();
					if (this.value < 0 || this.qty < 0) {
						this.value = 0;
						this.qty = 0;
					}
					resultToReturn = false;
					this.historyOfTrade.remove(i);
				} else
					break;
			}
		}
		return resultToReturn;
	}

	public double getBalance() {
		return this.balance;
	}

	public int getSizeOfHistory() {
		return this.historyOfTrade.size();
	}

	public double getMinPriceToSell() {
		if (!this.historyOfTrade.isEmpty())
			return this.historyOfTrade.get(this.historyOfTrade.size() - 1).getPriceToSell();
		else
			return 0;
	}

	public double getMaxPriceToSell() {
		if (!this.historyOfTrade.isEmpty())
			return this.historyOfTrade.get(0).getPriceToSell();
		else
			return 0;
	}

	public double getTotalValue() {
		return value;
	}

	public double getTotalQty() {
		return this.qty;
	}

	public double getProfit() {
		return this.getTotalValue() + this.balance - this.initialBalance;
	}

	public double getCurrentPrice() {
		return this.grid.getCurrentPrice();
	}
}
