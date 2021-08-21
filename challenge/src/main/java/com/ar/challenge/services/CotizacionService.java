package com.ar.challenge.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ar.challenge.models.CotizacionModel;
import com.ar.challenge.repositories.CotizacionRepository;

@Service
public class CotizacionService {
	
	@Autowired
	CotizacionRepository repository;
			
	public void guardar(Iterable<CotizacionModel> entities) {		
 		repository.saveAll(entities);
	}

	public Optional<CotizacionModel> obtenerCotiza(String moneda, long fecha) {
		return repository.findById(moneda+"_"+fecha);
	}
	
	public List<CotizacionModel> obtenerCotizacionEntreFechas(String moneda, Date fechaDesde, Date fechaHasta) {
		return repository.findAll(moneda,fechaDesde,fechaHasta);
	}

	public Page<CotizacionModel> obtenerCotizaciones(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public Page<CotizacionModel> obtenerCotizaciones(Pageable pageable, Date fecha) {
		return repository.findByFecha(fecha,pageable);
	}
}
