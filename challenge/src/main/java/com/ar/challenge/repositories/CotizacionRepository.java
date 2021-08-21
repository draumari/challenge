package com.ar.challenge.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.ar.challenge.models.CotizacionModel;

@Repository
public interface CotizacionRepository extends MongoRepository<CotizacionModel, String> {
	
	 @Query("{'moneda' : ?0 , 'fecha' : { $lte:?2, $gte:?1 }}")
	 public List<CotizacionModel> findAll(String moneda, Date desde, Date hasta);

	 public Page<CotizacionModel> findByFecha(Date fecha, Pageable pageable);

	 public CotizacionModel findTopByMonedaOrderByFechaDesc(String moneda);

}
