package com.ar.challenge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ar.challenge.controllers.CotizacionController;
import com.ar.challenge.models.CotizacionModel;


@EnableScheduling
@Component
public class Consumo {

	@Autowired
	CotizacionController controller;
	@Value("${zona.horaria}")
	int tz;

	@Scheduled(fixedRateString = "${tiempo.ejecucion}")
	public void getCotizacion() {

		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

		//ws ETH
		ResponseEntity<String> response = restTemplate.exchange("https://cex.io/api/last_price/ETH/USD", HttpMethod.GET,entity,String.class);

		//ws BTC
		ResponseEntity<String> response2 = restTemplate.exchange("https://cex.io/api/last_price/BTC/USD", HttpMethod.GET,entity,String.class);        

		//Guardar en BD
		JSONObject jsonObjETH = new JSONObject(response.getBody());
		Date fecha = new Date(response.getHeaders().getDate());

		CotizacionModel cotETH = new CotizacionModel("ETH_"+fecha.getTime(),jsonObjETH.getDouble("lprice"), jsonObjETH.getString("curr1"), jsonObjETH.getString("curr2"), new Date(fecha.getTime()+(3600000*tz)));

		JSONObject jsonObjBTC = new JSONObject(response2.getBody());
		fecha = new Date(response2.getHeaders().getDate());
		CotizacionModel cotBTC = new CotizacionModel("BTC_"+fecha.getTime(),jsonObjBTC.getDouble("lprice"), jsonObjBTC.getString("curr1"), jsonObjBTC.getString("curr2"), new Date(fecha.getTime()+(3600000*tz)));


		List<CotizacionModel> entities = new ArrayList<CotizacionModel>();
		entities.add(cotETH);
		entities.add(cotBTC);        

		controller.guardarCotizacion(entities);

	}



}
