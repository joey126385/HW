package com.example.demo;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CoindeskModel;
import com.example.demo.service.CoindeskService;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private CoindeskService coindeskService;
	
	public static String coindeskAPI="https://api.coindesk.com/v1/bpi/currentprice.json";
	public static String responseData="";
	static {
		RestTemplate restTemplate = new RestTemplate();
		// 設置請求標頭
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		// 建立請求實體
		HttpEntity<String> requestEntity = new HttpEntity<>(headers);
		// 發送GET請求並獲取響應
		ResponseEntity<String> response = restTemplate.exchange(coindeskAPI, HttpMethod.GET, requestEntity, String.class);
		// 解析JSON響應
		 responseData = response.getBody();
		
	}
	
	
	
	//1. 測試呼叫查詢幣別對應表資料API，並顯示其內容。
	@Test
	public void test1() {
		System.out.println("sss");
		
	}
	
	//2. 測試呼叫新增幣別對應表資料API。
	@Test
	public void test2() {
		JSONObject m=new JSONObject(responseData);
		Object bpi=m.get("bpi");	
		try {
			JSONObject ms=(JSONObject) bpi;
			for (Object codeName : ms.names()) {
				JSONObject coin_map=(JSONObject) ms.get(codeName+"");				
				String symbol=coin_map.getString("symbol");
				String code=coin_map.getString("code");
				String rate=coin_map.getString("rate");
				String description=coin_map.getString("description");
				Object rate_float=coin_map.get("rate_float");				
				coindeskService.saveUser(new CoindeskModel(code, symbol,rate, description, rate_float+""));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//3. 測試呼叫更新幣別對應表資料API，並顯示其內容。
	@Test
	public void test3() {
		System.out.println("sss");
		
	}
	
	//4. 測試呼叫刪除幣別對應表資料API。。
	@Test
	public void test4() {
		System.out.println("sss");
		
	}
	
	//5. 測試呼叫coindesk API，並顯示其內容。
	@Test
	public void test5() {
		System.out.println("sss");
		
	}	
	//6. 測試呼叫資料轉換的API，並顯示其內容。
	@Test
	public void test6() {
		System.out.println("sss");
		
	}
	
	
	
	

}
