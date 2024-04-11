package com.example.demo.controller;

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
	
	@GetMapping("/seach")
	public String seach(@RequestParam(value="insert") String insert,Model model) {
		coindeskService.listCoindesks();
		model.addAttribute("message", 	coindeskService.listCoindesks().size());
		return "index";
	}

	@GetMapping("/delete")
	public String delete() {
		System.out.println(13);
		return "";
	}
	
	@GetMapping("/update")
	public String update() {
		System.out.println(13);
		return "";
	}
	
}
