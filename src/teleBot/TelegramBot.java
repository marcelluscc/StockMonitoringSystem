package teleBot;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

public class TelegramBot extends TelegramLongPollingBot{

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return "StockMonitoringBot";
	}
	
	@Override
	public void onUpdateReceived(Update arg0) {
		try {
			handleMessage(arg0);
		} catch (TelegramApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void handleMessage(Update arg0) throws TelegramApiException {
		SendMessage message = new SendMessage().setChatId(arg0.getMessage().getChatId());
		if(arg0.hasMessage()){
			String command = arg0.getMessage().getText();
			ArrayList<String> commandArr = new ArrayList<String>();
			for (String comm : command.split(" ")){
				commandArr.add(comm);
			}
			if (commandArr.get(0).equalsIgnoreCase("/quote")){
				if(commandArr.size()>1){
					message.setText(getQuote(commandArr.get(1)));
					sendMessage(message);
				}
				else{
					message.setText("stock code not found.");
					sendMessage(message);
				}
			}
		}
		// TODO Auto-generated method stub
		
	}

	private String getQuote(String string) {
		// TODO Auto-generated method stub
		try {
			Stock stock = YahooFinance.get(string);
			BigDecimal price = stock.getQuote().getPrice();
			return price.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		return "stock code not found";
		
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return "392246727:AAHnkCBc_3WzUZa7cTEV3gj8zKQI63mM-mU";
	}
	
	/*
	 * list of command
	 * quote - get the last price of a stock
	 */

}
