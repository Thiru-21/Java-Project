package currencyConverter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Scanner;
import java.util.HashMap;

public class CurrencyConverter {
	private static Map<Integer, String> m = new HashMap<>();
	static {
		m.put(1, "USD");
		m.put(2, "EUR");
		m.put(3, "INR");
		m.put(4, "GBP");
		m.put(5, "JPY");
		m.put(6, "AUD");
		m.put(7, "CAD");
		m.put(8, "CHF");
		m.put(9, "CNY");
		m.put(10, "NZD");
		m.put(11, "SGD");
	}

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);

		System.out.println("Select country (From):");
		for (Map.Entry<Integer, String> entry : m.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue());
		
		System.out.print("Enter a Country (as Number) to Conver From: ");
		int keyFrom=s.nextInt();
		if(m.get(keyFrom)==null) { System.out.println("Enter valid Country number"); return; }
		
		System.out.println("Select country (To):");
		for (Map.Entry<Integer, String> entry : m.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue());
		
		System.out.print("Enter a Country (as Number) to Conver To: ");
		int keyTo=s.nextInt();
		if(m.get(keyTo)==null) { System.out.println("Enter valid Country number"); return; }
		
		System.out.printf("Enter the amount to Convert (%s): ",m.get(keyFrom));
		long amount=s.nextLong();
		
		String str=CurrencyConverting(m.get(keyFrom),m.get(keyTo),amount);
		
		if(str!=null) {
			int start=str.indexOf("result\":")+8,end=str.length();
			System.out.printf("After Convvertion (%s) : ",m.get(keyTo));
			System.out.println(str.substring(start,end-1));
		}
		else {
			System.out.println("Error Fetching Convertion");
		}
	}

	private static String CurrencyConverting(String From, String To, long amount) {
		String apiurl="API_website_URL";
		String apiKey="API_Key";
		String urlAddress=apiurl + "?access_key=" + apiKey + "&from=" + From + "&to=" + To + "&amount=" + amount;
		try {
			@SuppressWarnings("deprecation")
			URL url=new URL(urlAddress);
			HttpURLConnection con=(HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");
			
			Scanner s=new Scanner(con.getInputStream());
			StringBuffer sb=new StringBuffer();
			while(s.hasNextLine()) {
				sb.append(s.nextLine());
			}
			s.close();
			return sb.toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.printf("Error Accessing URL ");
		}
		return null;
	}
}
