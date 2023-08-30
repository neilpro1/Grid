package exe;

import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import data.Data;
import simulation.TradeGrid;
import struct.DiskList;

public class SimulationGrid {
	private static final String ROOT = String.valueOf(Paths.get("").toAbsolutePath().getParent());
	private static final String SYMBOL = "BTCUSDT";
	private static final String INTERVAL = "1s";
	private static final long TIME_INCREASE = CompilerTime.getMilisec(INTERVAL);
	private static final double BALANCE = 20000;
	private static final double BASE_VALUE = 200;
	private static final double BREAK_ACCOUNT = 84600 * 30;

	public static void main(String[] args) {
		Data data = new Data(ROOT, SYMBOL, INTERVAL);
		DiskList<Map<String, String>> disk = data.getData();
		TradeGrid trade = new TradeGrid(BALANCE, 4);
		List<Double> listOfProfits = new ArrayList<>(3600);

		for (int i = 0; i < disk.size(); i++) {
			Map<String, String> map = disk.get(i);
			long openTime = Long.parseLong(map.get("open time"));
			double openPrice = Double.parseDouble(map.get("open price"));
			LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(openTime), ZoneId.systemDefault());
			
			boolean result = trade.setPrice(openPrice, BASE_VALUE);
			
			double day = TIME_INCREASE * i / 1000 / 60 / 1440; 
			if(result) {
				printStatus("---------------\nBUY\n-------------------", start, openPrice, trade, day, listOfProfits);
			} else {
				printStatus("---------------\nSELL\n------------------", start, openPrice, trade, day, listOfProfits);
			}
			
			if(i % BREAK_ACCOUNT == 0 && i != 0) {
				listOfProfits.add(trade.getProfit());
			}
		}
	}

	private static void printStatus(String status, LocalDateTime date, double price, TradeGrid trade, double day, List<Double> listOfProfits) {
		println(status);
		println("Date: " + date);
		println("Price: " + price);
		println("CurrentPrice on Grid: " + trade.getCurrentPrice());
		println("Balance: " + trade.getBalance());
		println("Number of partition: " + trade.getSizeOfHistory());
		println("Min price to sell: " + trade.getMinPriceToSell());
		println("Max price to sell: " + trade.getMaxPriceToSell());
		println("profit: " + trade.getProfit());
		println("total invested: " + trade.getTotalValue());
		println("qty: " + trade.getTotalQty());
		println("day: " + day);
		
		println("");
		if(!listOfProfits.isEmpty()) {
			for(int i = 0; i < listOfProfits.size(); i++) {
				if(i == 0 || i % 4 == 0) println("");
				
				print("Mounth [" + i +"]: " +  listOfProfits.get(i) + " | ");

			}
		}
		
	}
	
	private static void println(Object obj) {
		System.out.println(obj);
	}
	
	private static void print(Object obj) {
		System.out.println(obj);
	}
}
