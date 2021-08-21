package com.ar.challenge.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ar.challenge.models.CotizacionModel;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DBObject;

@DataMongoTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ChallengeTest{

	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private  List<CotizacionModel> listCotizacion;
	private  List<CotizacionModel> listETH;
	private  List<CotizacionModel> listBTC;

	@Autowired 
	MongoTemplate mongoTemplate;

	@BeforeEach
	void iniciarVariables() throws ParseException {
		// given
		DBObject objectETH1 = BasicDBObjectBuilder
				.start()
				.add("_id", "ETH_1629248228000")
				.add("precio", 3016.41)
				.add("moneda", "ETH")
				.add("monedaRef", "USD")
				.add("fecha", dateformat.parse("2021-08-17 21:57:08"))
				.get();        
		mongoTemplate.save(objectETH1, "cotizacion");
		
		DBObject objectBTC1 = BasicDBObjectBuilder
				.start()
				.add("_id", "BTC_1629248228000")
				.add("precio", 44778.9)
				.add("moneda", "BTC")
				.add("monedaRef", "USD")
				.add("fecha", dateformat.parse("2021-08-17 21:57:08"))
				.get();        
		mongoTemplate.save(objectBTC1, "cotizacion");

		DBObject objectETH2 = BasicDBObjectBuilder
				.start()
				.add("_id", "ETH_1629248238000")
				.add("precio", 3017.41)
				.add("moneda", "ETH")
				.add("monedaRef", "USD")
				.add("fecha", dateformat.parse("2021-08-17 21:57:18"))
				.get();
		mongoTemplate.save(objectETH2, "cotizacion");
		
		DBObject objectBTC2 = BasicDBObjectBuilder
				.start()
				.add("_id", "BTC_1629248238000")
				.add("precio", 44779.9)
				.add("moneda", "BTC")
				.add("monedaRef", "USD")
				.add("fecha", dateformat.parse("2021-08-17 21:57:18"))
				.get();        
		mongoTemplate.save(objectBTC2, "cotizacion");		

		DBObject objectETH3 = BasicDBObjectBuilder
				.start()
				.add("_id", "ETH_1629248248000")
				.add("precio", 3018.41)
				.add("moneda", "ETH")
				.add("monedaRef", "USD")
				.add("fecha", dateformat.parse("2021-08-17 21:57:28"))
				.get();
		mongoTemplate.save(objectETH3, "cotizacion");

		DBObject objectBTC3 = BasicDBObjectBuilder
				.start()
				.add("_id", "BTC_1629248248000")
				.add("precio", 44780.9)
				.add("moneda", "BTC")
				.add("monedaRef", "USD")
				.add("fecha", dateformat.parse("2021-08-17 21:57:28"))
				.get();        
		mongoTemplate.save(objectBTC3, "cotizacion");


		//List local
		listCotizacion = new ArrayList<CotizacionModel>();
		listCotizacion.add(new CotizacionModel("ETH_1629248228000",3016.41,"ETH","USD",dateformat.parse("2021-08-17 21:57:08")));
		listCotizacion.add(new CotizacionModel("BTC_1629248228000",44778.9,"BTC","USD",dateformat.parse("2021-08-17 21:57:08")));
		listCotizacion.add(new CotizacionModel("ETH_1629248238000",3017.41,"ETH","USD",dateformat.parse("2021-08-17 21:57:18")));
		listCotizacion.add(new CotizacionModel("BTC_1629248238000",44779.9,"BTC","USD",dateformat.parse("2021-08-17 21:57:18")));
		listCotizacion.add(new CotizacionModel("ETH_1629248248000",3018.41,"ETH","USD",dateformat.parse("2021-08-17 21:57:28")));
		listCotizacion.add(new CotizacionModel("BTC_1629248248000",44780.9,"BTC","USD",dateformat.parse("2021-08-17 21:57:28")));

		listarPorMoneda();
	}

	@Test
	public void obtenerCotizaETH() throws ParseException {

		String moneda = "ETH";
		Long fecha = 1629248228000L;
		CotizacionModel actual = mongoTemplate.findById(moneda+"_"+fecha, CotizacionModel.class, "cotizacion");        
		CotizacionModel esperado = new CotizacionModel("ETH_1629248228000", 3016.41, "ETH", "USD", dateformat.parse("2021-08-17 21:57:08"));

		Assertions.assertEquals(esperado , actual);
	}

	@Test
	public void obtenerCotizaBTC() throws ParseException {

		String moneda = "BTC";
		Long fecha = 1629248228000L;
		CotizacionModel actual = mongoTemplate.findById(moneda+"_"+fecha, CotizacionModel.class, "cotizacion");        
		CotizacionModel esperado = new CotizacionModel("BTC_1629248228000", 44778.9, "BTC", "USD", dateformat.parse("2021-08-17 21:57:08"));

		Assertions.assertEquals(esperado , actual);
	}

	@Test
	public void obtenerCotizacionesPaginada1de3(){

		int pageNum = 1, pageSize = 3;        

		List<CotizacionModel> lista = new ArrayList<CotizacionModel>();
		lista.add(listCotizacion.get(0));
		lista.add(listCotizacion.get(1));
		lista.add(listCotizacion.get(2));
		
		Query query = new Query();
		query.skip((pageNum - 1) * pageSize);
	    query.limit(pageSize);
	    List<CotizacionModel> actual = mongoTemplate.find(query,CotizacionModel.class, "cotizacion");
	    
	    
		Assertions.assertIterableEquals(lista, actual);
	}
	
	@Test
	public void obtenerCotizacionesPaginada2de3(){

		int pageNum = 2, pageSize = 3;        

		List<CotizacionModel> lista = new ArrayList<CotizacionModel>();
		lista.add(listCotizacion.get(3));
		lista.add(listCotizacion.get(4));
		lista.add(listCotizacion.get(5));
		
		Query query = new Query();
		query.skip((pageNum - 1) * pageSize);
	    query.limit(pageSize);
	    List<CotizacionModel> actual = mongoTemplate.find(query,CotizacionModel.class, "cotizacion");
	    
	    
		Assertions.assertIterableEquals(lista, actual);
	}

	@Test
	public void obtenerCotizacionEntreFechasETH() throws ParseException{

		String moneda = "ETH";
		Date fechaDesde = dateformat.parse("2021-08-17 21:57:08");
		Date fechaHasta = dateformat.parse("2021-08-17 21:57:38");

		Criteria publishedDateCriteria = Criteria.where("fecha").gte(fechaDesde).lte(fechaHasta).and("moneda").is(moneda);
		Query query = new Query(publishedDateCriteria);
		List<CotizacionModel> actual = mongoTemplate.find(query,CotizacionModel.class, "cotizacion");

		Assertions.assertEquals(listETH , actual);
	}
	
	@Test
	public void obtenerCotizacionEntreFechasBTC() throws ParseException{

		String moneda = "BTC";
		Date fechaDesde = dateformat.parse("2021-08-17 21:57:08");
		Date fechaHasta = dateformat.parse("2021-08-17 21:57:38");

		Criteria publishedDateCriteria = Criteria.where("fecha").gte(fechaDesde).lte(fechaHasta).and("moneda").is(moneda);
		Query query = new Query(publishedDateCriteria);
		List<CotizacionModel> actual = mongoTemplate.find(query,CotizacionModel.class, "cotizacion");

		Assertions.assertEquals(listBTC , actual);
	}
	
	@Test
	public void obtenerCotizacionesPorFechaUnoPorPagina() throws ParseException{
		
		Date fecha = dateformat.parse("2021-08-17 21:57:08");
		int pageNum = 1, pageSize = 1;  
		
		List<CotizacionModel>lista = new ArrayList<CotizacionModel>();
		lista.add(listCotizacion.get(0));
		
		Criteria publishedDateCriteria = Criteria.where("fecha").is(fecha);
		Query query = new Query(publishedDateCriteria);
		query.skip((pageNum - 1) * pageSize);
	    query.limit(pageSize);
		List<CotizacionModel> actual = mongoTemplate.find(query,CotizacionModel.class, "cotizacion");     

		Assertions.assertIterableEquals(lista , actual);
	}
	
	@Test
	public void obtenerCotizacionesPorFechaDosPorPagina() throws ParseException{
		
		Date fecha = dateformat.parse("2021-08-17 21:57:08");
		int pageNum = 1, pageSize = 2;  
		
		List<CotizacionModel>lista = new ArrayList<CotizacionModel>();
		lista.add(listCotizacion.get(0));
		lista.add(listCotizacion.get(1));
		
		Criteria publishedDateCriteria = Criteria.where("fecha").is(fecha);
		Query query = new Query(publishedDateCriteria);
		query.skip((pageNum - 1) * pageSize);
	    query.limit(pageSize);
		List<CotizacionModel> actual = mongoTemplate.find(query,CotizacionModel.class, "cotizacion");     

		Assertions.assertIterableEquals(lista , actual);
	}
	
	@Test
	public void getUltimoDatoETH() throws ParseException {

		String moneda = "ETH";
		Criteria publishedDateCriteria = Criteria.where("moneda").is(moneda);
		Query query = new Query(publishedDateCriteria);
		query.with(Sort.by(Sort.Order.desc("fecha")));
		CotizacionModel actual = mongoTemplate.findOne(query,CotizacionModel.class, "cotizacion");        
		CotizacionModel esperado = new CotizacionModel(listCotizacion.get(4).getId(),
				listCotizacion.get(4).getPrecio(),
				listCotizacion.get(4).getMoneda(),
				listCotizacion.get(4).getMonedaRef(),
				listCotizacion.get(4).getFecha());

		Assertions.assertEquals(esperado , actual);
	}
	
	@Test
	public void getUltimoDatoBTC() throws ParseException {

		String moneda = "BTC";
		Criteria publishedDateCriteria = Criteria.where("moneda").is(moneda);
		Query query = new Query(publishedDateCriteria);
		query.with(Sort.by(Sort.Order.desc("fecha")));
		CotizacionModel actual = mongoTemplate.findOne(query,CotizacionModel.class, "cotizacion");        
		CotizacionModel esperado = new CotizacionModel(listCotizacion.get(5).getId(),
				listCotizacion.get(5).getPrecio(),
				listCotizacion.get(5).getMoneda(),
				listCotizacion.get(5).getMonedaRef(),
				listCotizacion.get(5).getFecha());

		Assertions.assertEquals(esperado , actual);
	}

	private void listarPorMoneda(){

		listBTC = new ArrayList<CotizacionModel>();
		listETH = new ArrayList<CotizacionModel>();
		
		for (CotizacionModel cotizacionModel : listCotizacion) {
			if(cotizacionModel.getMoneda().equalsIgnoreCase("ETH")) {
				listETH.add(cotizacionModel);
			}else {
				listBTC.add(cotizacionModel);
			}
		}

	}
	
	
}

