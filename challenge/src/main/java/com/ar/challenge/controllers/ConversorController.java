package com.ar.challenge.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ar.challenge.models.ConversorModel;
import com.ar.challenge.models.CotizacionModel;
import com.ar.challenge.services.ConversorService;

@RestController
@RequestMapping("/PARTE 2")
public class ConversorController {

	@Autowired
	ConversorService service;

	@RequestMapping(value = "convertirMoneda", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<ConversorModel> convertirMoneda(String moneda, String monto) {

		try {

			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
			JSONObject request = new JSONObject();
			request.put("amnt", monto);
			HttpEntity<String> entity = new HttpEntity<String>(request.toString(),headers);

			URI uri = new URI("https://cex.io/api/convert/" + moneda + "/USD");		
			ConversorModel conversor = new ConversorModel(moneda);

			//ws Conversor
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST,entity,String.class);
			JSONObject jsonObj = new JSONObject(response.getBody());

			if(response.getStatusCodeValue() == 200 && !response.getBody().contains("error")) {
				CotizacionModel ultCotizacion= service.getUltimoDato(moneda);
				conversor.setConversion(jsonObj.getDouble("amnt"));
				conversor.setUltimoPrecio(ultCotizacion.getPrecio());

				return ResponseEntity.ok(conversor);	

			}else {
				System.out.println("ERROR EN EL WS " + response.getBody());
				return ResponseEntity.noContent().build();
			}

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.internalServerError().build();
		}

	}


}
