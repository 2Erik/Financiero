package com.krakedev.financiero.servicios.test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class TestBanco {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Banco banco = new Banco();
		
		Cliente cl1 = new Cliente("111111111", "Pepe", "Paredes");
		Cliente cl2 = new Cliente("2222222222", "Mari", "Tere");
		
		Cuenta c1 = banco.crearCuenta(cl1);
		Cuenta c2 = banco.crearCuenta(cl2);
		
		System.out.println("-----CUENTA 1-----");
		c1.imprimir();
		
		System.out.println("-----CUENTA 2-----");
		c2.imprimir();
		
		System.out.println("-----DEPOSITO CUENTA 1-----");
		banco.depositar(700, c1);
		c1.imprimir();
		
		System.out.println("-----DEPOSITO CUENTA 2-----");
		banco.depositar(1852.69, c2);
		c2.imprimir();
		
		System.out.println("-----RETIRO CUENTA 1-----");
		banco.retirar(2000, c1);
		c1.imprimir();
		
		System.out.println("-----RETIRO CUENTA 2-----");
		banco.retirar(500, c2);
		c2.imprimir();
		
		System.out.println("-----TRANSFERIR A CUENTA 1");
		banco.transferir(c2, c1, 100);
		c1.imprimir();
		System.out.println("--------------------------");
		c2.imprimir();

	}

}
