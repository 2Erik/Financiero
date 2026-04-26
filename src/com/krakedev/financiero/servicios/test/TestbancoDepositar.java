package com.krakedev.financiero.servicios.test;

import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;


public class TestbancoDepositar {
	public static void main(String[] args) {
		
		Banco banco = new Banco();
		
		Cuenta cuenta =  new Cuenta("12121");
		
		boolean respuesta1 = banco.depositar(500, cuenta);
		
		System.out.println(respuesta1);
		System.out.println(cuenta.getSaldoActual());
		
		banco.retirar(400, cuenta);
		System.out.println(cuenta.getSaldoActual());
	}
	
}
