package com.example.demo.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.CoindeskModel;
import com.example.demo.service.CoindeskService;

@Controller
public class CoindeskController {
	
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
	
	//新增  幣別
	@GetMapping("/add")//http://localhost:8080/
	//@ResponseBody
	public String add(Model model) {
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
		model.addAttribute("message", "新增成功");
		model.addAttribute("message", "新增成功");
		return "index";
	}
	//測試呼叫查詢幣別對應表資料API，並顯示其內容。
	@GetMapping("/seach")
	public String seach() {
		String code="USD";//幣別
//	List<CoindeskModel> a=	coindeskService.listCoindesks();
	CoindeskModel coindeskModel=coindeskService.getCoindeskModel(code);
	
	System.out.println("code-->"+coindeskModel.getCode());
	System.out.println("description-->"+coindeskModel.getDescription());
	System.out.println("rate-->"+coindeskModel.getRate());
	System.out.println("rate_float-->"+coindeskModel.getRate_float());
	System.out.println("symbol-->"+coindeskModel.getSymbol());
	
	
		return "index";
	}

	@GetMapping("/delete")
	public String delete() {
		String code="USD";//幣別
		coindeskService.deleteCoindeskModel(code);
		return "index";
	}
	
	@GetMapping("/update")
	public String update() {
		String code="USD";
		String des="testDes";
		
		coindeskService.updateCoindesk(des, code);
		return "index";
	}
	//5. 測試呼叫coindesk API，並顯示其內容。
	@GetMapping("/callApi")
	public String callApi() {
		JSONObject m=new JSONObject(responseData);
		System.out.println(m);
		return "index";
	}
	//6. 測試呼叫資料轉換的API，並顯示其內容。
	@GetMapping("/callApiUpdate")
	public String callApiUpdate() {
		JSONObject m=new JSONObject(responseData);
		JSONObject time=(JSONObject) m.get("time");	
		//{"updateduk":"Apr 14, 2024 at 14:31 BST","updatedISO":"2024-04-14T13:31:45+00:00","updated":"Apr 14, 2024 13:31:45 UTC"}
		
		//1990/01/01 00:00:00）
		
		String updateduk=(String) time.get("updateduk");
		String updatedISO=(String) time.get("updatedISO");
		String updated=(String) time.get("updated");
		//Apr 14, 2024 13:31:45 UTC
		//MMM dd, yyyy HH:mm:ss z
        DateTimeFormatter originalFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy HH:mm:ss zzzz");
        LocalDateTime dateTime = LocalDateTime.parse(updated, originalFormatter);

        DateTimeFormatter targetFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedDateTime = dateTime.format(targetFormatter);
		
		
		
		
		System.out.println(formattedDateTime);
		return "index";
	}
}
