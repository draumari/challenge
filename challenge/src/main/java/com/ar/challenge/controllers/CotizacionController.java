package com.ar.challenge.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ar.challenge.models.CotizacionModel;
import com.ar.challenge.services.CotizacionService;

@Controller
public class CotizacionController {

	@Autowired
	CotizacionService service;
	
	public void guardarCotizacion(Iterable<CotizacionModel> entities) {
		service.guardar(entities);	
	}
}
