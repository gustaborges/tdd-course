package com.gusta.banco.atm;

import com.gusta.banco.contas.ContaCorrente;
import com.gusta.banco.contas.FalhaAoRecuperarContaException;
import com.gusta.banco.contas.IServicoRemoto;

public class CaixaEletronico {
	private IHardware _hardware;
	private IServicoRemoto _servicoRemotoContas;
	
	private ContaCorrente _conta;
	
	public CaixaEletronico(IHardware hardware, IServicoRemoto servicoRemotoConta) {
		_hardware = hardware;
		_servicoRemotoContas = servicoRemotoConta;
	}
	
	public String logar() {
		try {
			String numeroDaConta = _hardware.pegarNumeroDaContaCartao();
			this.setConta(_servicoRemotoContas.recuperarConta(numeroDaConta));
			return "Usu�rio Autenticado";
		} catch (FalhaNaLeituraDoCartaoException | FalhaAoRecuperarContaException ex ) {
			return "N�o foi poss�vel autenticar o usu�rio";
		}
	}

	public ContaCorrente getConta() {
		return _conta;
	}

	public void setConta(ContaCorrente conta) {
		_conta = conta;
	}
}
