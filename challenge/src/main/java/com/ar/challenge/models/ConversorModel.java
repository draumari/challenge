package com.ar.challenge.models;

public class ConversorModel {
	
	private String monedaOrigen;
	private String monedaDestino;
	private Double conversion;
	private Double ultimoPrecio;
	
	
	public ConversorModel(String monedaOrigen) {
		super();
		this.monedaOrigen = monedaOrigen;
		monedaDestino = "USD";
	}	
	
	public String getMonedaOrigen() {
		return monedaOrigen;
	}
	public void setMonedaOrigen(String monedaOrigen) {
		this.monedaOrigen = monedaOrigen;
	}
	public String getMonedaDestino() {
		return monedaDestino;
	}
	public void setMonedaDestino(String monedaDestino) {
		this.monedaDestino = monedaDestino;
	}
	public Double getConversion() {
		return conversion;
	}
	public void setConversion(Double conversion) {
		this.conversion = conversion;
	}
	public Double getUltimoPrecio() {
		return ultimoPrecio;
	}
	public void setUltimoPrecio(Double ultimoPrecio) {
		this.ultimoPrecio = ultimoPrecio;
	}
	
}
