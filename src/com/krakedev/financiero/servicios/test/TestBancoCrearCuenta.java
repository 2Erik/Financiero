package com.krakedev.financiero.servicios.test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class TestBancoCrearCuenta {
	public static void main(String[] args) {
		
		Banco banco = new Banco();
		
		Cliente cliente1 = new Cliente("1111111111", "Pepe", "Paredes");
		Cliente cliente2 = new Cliente("222222222", "Juan", "Mena");
		
		Cuenta cuenta1 = banco.crearCuenta(cliente1);
		Cuenta cuenta2 = banco.crearCuenta(cliente2);
		
		cuenta1.imprimir();
		System.out.println("---------------------------");
		cuenta2.imprimir();
		
	}
}
