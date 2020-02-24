package com.gusta.banco.contas;

public class ContaCorrente {
	private static int _lastNumeroDeConta = 9999;
	
	private String _titular;
	private String _numeroDaConta;
	private double _saldo = 0.00;
	
	public ContaCorrente(String nomeTitular) {
		setTitular(nomeTitular);
		geraNumeroDaConta();
	}
	
	private void setTitular(String nomeTitular) {
		if (nomeTitular == null || nomeTitular.isBlank()) throw new IllegalArgumentException("O nome fornecido � invalido");
		_titular = nomeTitular;
	}

	public String getNumeroDaConta() {
		return _numeroDaConta;
	}
	
	private void geraNumeroDaConta() {
		int numero = ++_lastNumeroDeConta;
		_numeroDaConta = String.valueOf(numero);
	}

	public double getSaldo() {
		return _saldo;
	}

	public void deposita(double valorDeposito) {
		_saldo += valorDeposito;		
	}
}
