package com.ar.challenge.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

@Document(collection = "cotizacion")
public class CotizacionModel {

	@Id
	private String id;
	private Double precio;
	private String moneda;
	private String monedaRef;
	@DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
	private Date fecha;

	public CotizacionModel(String id,Double precio, String moneda, String monedaRef, Date fecha) {
		super();
		this.id = id;
		this.precio = precio;
		this.moneda = moneda;
		this.monedaRef = monedaRef;
		this.fecha = fecha;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	public String getMoneda() {
		return moneda;
	}
	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}
	public String getMonedaRef() {
		return monedaRef;
	}
	public void setMonedaRef(String monedaRef) {
		this.monedaRef = monedaRef;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "CotizacionModel [id=" + id + ", precio=" + precio + ", moneda=" + moneda + ", monedaRef=" + monedaRef
				+ ", fecha=" + fecha + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((moneda == null) ? 0 : moneda.hashCode());
		result = prime * result + ((monedaRef == null) ? 0 : monedaRef.hashCode());
		result = prime * result + ((precio == null) ? 0 : precio.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		
		if (obj == null)
			return false;
		
		if (getClass() != obj.getClass())
			return false;
		
		CotizacionModel other = (CotizacionModel) obj;
		
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (moneda == null) {
			if (other.moneda != null)
				return false;
		} else if (!moneda.equals(other.moneda))
			return false;
		if (monedaRef == null) {
			if (other.monedaRef != null)
				return false;
		} else if (!monedaRef.equals(other.monedaRef))
			return false;
		if (precio == null) {
			if (other.precio != null)
				return false;
		} else if (!precio.equals(other.precio))
			return false;
		return true;
	}


}
