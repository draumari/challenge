package com.ar.challenge.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ar.challenge.models.CotizacionModel;
import com.ar.challenge.repositories.CotizacionRepository;

@Service
public class ConversorService {

	@Autowired
	CotizacionRepository repository;

	public CotizacionModel getUltimoDato(String moneda) {
		return repository.findTopByMonedaOrderByFechaDesc(moneda);
	}


}
