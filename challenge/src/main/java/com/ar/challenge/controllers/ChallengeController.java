package com.ar.challenge.controllers;

import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ar.challenge.models.CalculosModel;
import com.ar.challenge.models.CotizacionModel;
import com.ar.challenge.services.CotizacionService;

@RestController
@RequestMapping("/PARTE 1")
public class ChallengeController {
	
	@Autowired
	CotizacionService service;	
	
	@Value("${zona.horaria}")
	int tz;
	
	@RequestMapping(value = "consultarPorFecha", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<CotizacionModel> getCotizacion(@RequestParam(defaultValue = "ETH o BTC") String moneda, 
			@RequestParam(defaultValue = "2021-08-17 21:57:09") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fecha ){

		Optional<CotizacionModel> opCotiza = service.obtenerCotiza(moneda, fecha.getTime());
		if(opCotiza.isPresent()) {
			return ResponseEntity.ok(opCotiza.get());
		}else {
			return ResponseEntity.noContent().build();
		}
		 
	}
	
	@RequestMapping(value = "consultarEntreFechas", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<CalculosModel> getCalculosEntreFechas(@RequestParam(defaultValue = "ETH o BTC") String moneda, 
			@RequestParam(defaultValue = "2021-08-17 21:57:09") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fechaDesde, 
			@RequestParam(defaultValue = "2021-08-17 21:57:09") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fechaHasta ){

		fechaDesde = new Date(fechaDesde.getTime()+(3600000*tz));
		fechaHasta = new Date(fechaHasta.getTime()+(3600000*tz));		
		List<CotizacionModel> cotizacionList = service.obtenerCotizacionEntreFechas(moneda, fechaDesde, fechaHasta);
		
		if(!cotizacionList.isEmpty()) {
			CalculosModel calculos = new CalculosModel();
			Double sumatoria = 0D;
			calculos.setPrecioMax(0D);
			
			for (CotizacionModel cotizacion : cotizacionList) {
				sumatoria += cotizacion.getPrecio();
				
				if(cotizacion.getPrecio()>calculos.getPrecioMax()) {
					calculos.setPrecioMax(cotizacion.getPrecio());
				}
				
			}
			
			calculos.setPromedio(sumatoria/cotizacionList.size());
			calculos.setPorcentaje((calculos.getPromedio()-calculos.getPrecioMax())/calculos.getPromedio());
			
			NumberFormat formatoPorcentaje = NumberFormat.getPercentInstance();
			formatoPorcentaje.setMinimumFractionDigits(2);
			calculos.setPorcentajeStr(formatoPorcentaje.format(calculos.getPorcentaje()));
			
			return ResponseEntity.ok(calculos);
		}else {
			return ResponseEntity.noContent().build();
		}	 
	}
	
	@RequestMapping(value = "consultarPaginados", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Page<CotizacionModel>> getPaginados(@RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "3") int size, @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date fecha){
		
		Pageable pageable = PageRequest.of(page, size, Sort.by(Order.asc("fecha")));
		
		Page<CotizacionModel> cotizacionList;
		if(fecha!=null) {
			fecha = new Date(fecha.getTime()+(3600000*tz));
			cotizacionList = service.obtenerCotizaciones(pageable,fecha);
		}else {
			cotizacionList = service.obtenerCotizaciones(pageable);
		}
		
		if(!cotizacionList.isEmpty()) {
			return ResponseEntity.ok(cotizacionList);
		}else {
			return ResponseEntity.noContent().build();
		}
		 
	}

}
