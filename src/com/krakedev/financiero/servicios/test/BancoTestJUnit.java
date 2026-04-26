package com.krakedev.financiero.servicios.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.krakedev.financiero.entidades.Cliente;
import com.krakedev.financiero.entidades.Cuenta;
import com.krakedev.financiero.servicios.Banco;

public class BancoTestJUnit {

	private Banco banco;
	private Cliente cliente;
	private Cuenta cuenta;

	/**
	 * Se ejecuta antes de cada prueba. Inicializa los objetos necesarios.
	 */
	@BeforeEach
	public void setUp() {
		banco = new Banco();
		cliente = new Cliente("1723456789", "Erik", "Rodriguez");
		cuenta = banco.crearCuenta(cliente);
	}

	// =========================================================
	// PRUEBAS DEL MÉTODO crearCuenta
	// =========================================================

	@Test
	@DisplayName("Debe crear una cuenta correctamente")
	public void testCrearCuenta() {
		assertNotNull(cuenta, "La cuenta creada no debe ser nula");

		assertEquals("1000", cuenta.getId(), "El primer código debe ser 1000");

		assertEquals(cliente, cuenta.getPropietario(), "El propietario debe ser el cliente enviado");

		assertEquals(0, cuenta.getSaldoActual(), "Una cuenta nueva debe iniciar con saldo cero");
	}

	@Test
	@DisplayName("Debe incrementar el código al crear varias cuentas")
	public void testCrearMultiplesCuentas() {
		Cuenta cuenta2 = banco.crearCuenta(cliente);

		assertEquals("1001", cuenta2.getId(), "El segundo código debe ser 1001");

		assertEquals(1002, banco.getUltimoCodigo(), "El último código debe incrementarse correctamente");
	}

	// =========================================================
	// PRUEBAS DEL MÉTODO depositar
	// =========================================================

	@Test
	@DisplayName("Debe depositar correctamente un monto válido")
	public void testDepositarMontoValido() {
		boolean resultado = banco.depositar(500, cuenta);

		assertTrue(resultado, "El depósito debe realizarse correctamente");

		assertEquals(500, cuenta.getSaldoActual(), "El saldo debe incrementarse");
	}

	@Test
	@DisplayName("No debe permitir depositar cero")
	public void testDepositarCero() {
		boolean resultado = banco.depositar(0, cuenta);

		assertFalse(resultado, "No debe aceptar depósitos de cero");

		assertEquals(0, cuenta.getSaldoActual(), "El saldo debe permanecer igual");
	}

	@Test
	@DisplayName("No debe permitir depositar valores negativos")
	public void testDepositarNegativo() {
		boolean resultado = banco.depositar(-100, cuenta);

		assertFalse(resultado, "No debe aceptar montos negativos");

		assertEquals(0, cuenta.getSaldoActual(), "El saldo no debe modificarse");
	}

	// =========================================================
	// PRUEBAS DEL MÉTODO retirar
	// =========================================================

	@Test
	@DisplayName("Debe retirar correctamente cuando hay saldo suficiente")
	public void testRetirarExitoso() {
		banco.depositar(1000, cuenta);

		boolean resultado = banco.retirar(400, cuenta);

		assertTrue(resultado, "El retiro debe realizarse");

		assertEquals(600, cuenta.getSaldoActual(), "El saldo debe disminuir correctamente");
	}

	@Test
	@DisplayName("No debe retirar más dinero del disponible")
	public void testRetirarSaldoInsuficiente() {
		banco.depositar(300, cuenta);

		boolean resultado = banco.retirar(500, cuenta);

		assertFalse(resultado, "No debe permitir retirar más del saldo");

		assertEquals(300, cuenta.getSaldoActual(), "El saldo debe permanecer igual");
	}

	@Test
	@DisplayName("No debe permitir retirar cero")
	public void testRetirarCero() {
		banco.depositar(300, cuenta);

		boolean resultado = banco.retirar(0, cuenta);

		assertFalse(resultado, "No debe permitir retirar cero");

		assertEquals(300, cuenta.getSaldoActual(), "El saldo debe permanecer igual");
	}

	@Test
	@DisplayName("No debe permitir retirar montos negativos")
	public void testRetirarNegativo() {
		banco.depositar(300, cuenta);

		boolean resultado = banco.retirar(-50, cuenta);

		assertFalse(resultado, "No debe permitir montos negativos");

		assertEquals(300, cuenta.getSaldoActual(), "El saldo debe permanecer igual");
	}

	// =========================================================
	// PRUEBAS DEL MÉTODO transferir
	// =========================================================

	@Test
	@DisplayName("Debe transferir correctamente entre cuentas")
	public void testTransferirExitoso() {
		Cuenta destino = banco.crearCuenta(new Cliente("0102030405", "Ana", "Lopez"));

		banco.depositar(1000, cuenta);

		boolean resultado = banco.transferir(cuenta, destino, 300);

		assertTrue(resultado, "La transferencia debe completarse");

		assertEquals(700, cuenta.getSaldoActual(), "El saldo origen debe disminuir");

		assertEquals(300, destino.getSaldoActual(), "El saldo destino debe aumentar");
	}

	@Test
	@DisplayName("No debe transferir con saldo insuficiente")
	public void testTransferirSaldoInsuficiente() {
		Cuenta destino = banco.crearCuenta(new Cliente("0102030405", "Ana", "Lopez"));

		banco.depositar(200, cuenta);

		boolean resultado = banco.transferir(cuenta, destino, 500);

		assertFalse(resultado, "La transferencia debe fallar");

		assertEquals(200, cuenta.getSaldoActual(), "El saldo origen no debe cambiar");

		assertEquals(0, destino.getSaldoActual(), "El saldo destino no debe cambiar");
	}

	@Test
	@DisplayName("No debe transferir montos negativos o cero")
	public void testTransferirMontoInvalido() {
		Cuenta destino = banco.crearCuenta(new Cliente("0102030405", "Ana", "Lopez"));

		banco.depositar(500, cuenta);

		assertFalse(banco.transferir(cuenta, destino, 0));
		assertFalse(banco.transferir(cuenta, destino, -100));

		assertEquals(500, cuenta.getSaldoActual());
		assertEquals(0, destino.getSaldoActual());
	}

	@Test
	@DisplayName("No debe transferir a la misma cuenta")
	public void testTransferirMismaCuenta() {
		banco.depositar(500, cuenta);

		boolean resultado = banco.transferir(cuenta, cuenta, 100);

		assertFalse(resultado, "No debe permitir transferir a la misma cuenta");

		assertEquals(500, cuenta.getSaldoActual(), "El saldo debe permanecer igual");
	}

}
