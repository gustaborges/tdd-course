package com.gusta.banco.atm;

import java.text.DecimalFormat;

import com.gusta.banco.contas.ContaCorrente;
import com.gusta.banco.contas.IServicoRemoto;
import com.gusta.banco.contas.SaqueMaiorQueSaldoException;

public class CaixaEletronico {
	private IHardware _hardware;
	private IServicoRemoto _servicoRemotoContas;
	
	private String _numeroDaConta;
	
	public CaixaEletronico(IHardware hardware, IServicoRemoto servicoRemotoConta) {
		_hardware = hardware;
		_servicoRemotoContas = servicoRemotoConta;
	}
	
	public String logar() {
		try {
			_numeroDaConta = _hardware.pegarNumeroDaContaCartao();
			return "Usu�rio Autenticado";
		} catch (FalhaNaLeituraDoCartaoException ex ) {
			return "N�o foi poss�vel autenticar o usu�rio";
		}
	}

	public String saldo() {
		double saldo = _servicoRemotoContas.recuperarConta(_numeroDaConta).getSaldo();
		DecimalFormat formatoMonetario = new DecimalFormat();
		formatoMonetario.applyPattern("R$ ###,###,##0.00");
		return "O saldo � " + formatoMonetario.format(saldo);
	}
	

	public String depositar() throws FalhaNoLeitorDeEnvelopeException {
		try {
			double valorDeposito = _hardware.lerEnvelope();
			ContaCorrente conta = _servicoRemotoContas.recuperarConta(_numeroDaConta);
			conta.deposita(valorDeposito);
			_servicoRemotoContas.persistirConta(conta);
			return "Dep�sito recebido com sucesso";
		}
		catch (FalhaNoLeitorDeEnvelopeException ex) {
			throw ex;
		}
		
	}

	public String sacar(double valorSaque) throws FalhaNaEmissaoDasNotasException  {
		try {
			ContaCorrente conta = _servicoRemotoContas.recuperarConta(_numeroDaConta);
			if (conta.temSaldoParaSaque(valorSaque) == false)
				throw new SaqueMaiorQueSaldoException("Saldo insuficiente para o valor de saque solicitado");
			
			_hardware.entregarDinheiro(valorSaque);
			conta.saca(valorSaque);
			_servicoRemotoContas.persistirConta(conta);
			return "Retire seu dinheiro";
		}
		catch(SaqueMaiorQueSaldoException ex) {
			return "Saldo Insuficiente";
		}
		catch(FalhaNaEmissaoDasNotasException ex) {
			throw ex;
		}
	}


}
