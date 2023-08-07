package grid;

import data.Data;

public class SimulateGrid {

	private Struct grid;
	private double balance;
	private Data data;
	private boolean donwload;
	
	public SimulateGrid() {
		this.balance = 0;
		this.donwload = true;
	}

	public void config(String symbol, String interval, long time, long pause, double balance,
			double min, double max, double space) {
		this.grid = new Struct(balance, min, max, space);
		this.balance = balance;
		new Thread(() -> {
			this.data = new Data(symbol, interval);
			this.data.donwload(symbol, interval, time, pause);
			donwload = false;
		} ).start();
	}
	
	public void start(double value) {
		int size = this.data.getData().size();
		for(int i = 0 ; i < size; i++) {
			String openPrice = this.data.getData().get(i).get("open price");
			String closePrice = this.data.getData().get(i).get("close price");
			this.grid.setPrice(Double.parseDouble(openPrice), value);
			this.grid.setPrice(Double.parseDouble(closePrice), value);
			if(donwload) {
				size = this.data.getData().size();
				try {
					Thread.sleep(1);
				}catch(Exception e) {}
			}
			
		}
	}
}
