package com.krakedev.financiero.servicios;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;

public class Banco {
	private int ultimoCodigo = 1000;
	
	public Banco() {}

	public int getUltimoCodigo() {
		return ultimoCodigo;
	}

	public void setUltimoCodigo(int ultimoCodigo) {
		this.ultimoCodigo = ultimoCodigo;
	}
	
	public Cuenta crearCuenta(Cliente cliente) {
		
		String codigoStr = ultimoCodigo+"";
		ultimoCodigo++;
		Cuenta cuenta = new Cuenta(codigoStr);
		cuenta.setPropietario(cliente);
		
		return cuenta;
		
	}
	
	public boolean depositar(double monto, Cuenta cuenta) {
		if(monto > 0) {
			cuenta.setSaldoActual(cuenta.getSaldoActual() + monto);
			return true;
		}
		return false;
	}
	
	public boolean retirar(double monto, Cuenta cuenta) {
		if(monto > 0 && monto <= cuenta.getSaldoActual()) {
			cuenta.setSaldoActual(cuenta.getSaldoActual()-monto);
			return true;
		}
		return false;
	}
	
	public boolean transferir(Cuenta origen, Cuenta destino, double monto) {
		
		if(monto>0 && origen.getSaldoActual() >= monto && origen.getId() != destino.getId()) {
			return retirar(monto, origen) && depositar(monto, destino);
		}
		
		return false;
	}
}
