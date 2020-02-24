package com.gusta.banco.atm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gusta.banco.atm.MockHardware.FalhasHW;

class CaixaEletronicoTest {
	
	@BeforeEach
	void setUp() {
		mockHardware = new MockHardware();
		mockServicoRemoto = new MockServicoRemoto();
		caixaATM = new CaixaEletronico(mockHardware,mockServicoRemoto);
	}
	
	@Test
	void whenAutenticacaoBemSucedidaEntaoDevolveMensagemExito() {
		mockHardware.setNumeroDaConta("10000");			
		assertEquals("Usu�rio Autenticado", caixaATM.logar());
	}

	@Test
	void whenTentoAutenticarELeituraDoCartaoFalhaEntaoDevolveMensagemFalha() {	
		mockHardware.setFalha(FalhasHW.LEITURA_DO_CARTAO);
		assertEquals("N�o foi poss�vel autenticar o usu�rio", caixaATM.logar());
	}
	

	@Test
	void whenNenhumDepositoEConsultoSaldoEntaoSaldoEhZero() {		
		caixaATM.logar();
		assertEquals("O saldo � R$ 0,00", caixaATM.saldo());
	}
	
	
	@Test
	void whenDeposito100EntaoSaldoEh100() throws FalhaNoLeitorDeEnvelopeException {		
		caixaATM.logar();
		mockHardware.setValorASerLidoDoEnvelope(100.00);
		assertEquals("Dep�sito recebido com sucesso", caixaATM.depositar());
		assertEquals("O saldo � R$ 100,00", caixaATM.saldo());
	}
	
	@Test
	void whenDepositoELeitorDoEnvelopeFalhaEntaoDevolvoFalhaNoLeitorDeEnvelopeException() {		
		caixaATM.logar();
		mockHardware.setValorASerLidoDoEnvelope(1050.00);
		mockHardware.setFalha(FalhasHW.LEITOR_DE_ENVELOPE);
		
		try {
			caixaATM.depositar();
		}
		catch(FalhaNoLeitorDeEnvelopeException ex) { }
		
		assertEquals("O saldo � R$ 0,00", caixaATM.saldo());
	}
	
	
	private MockHardware mockHardware;
	private MockServicoRemoto mockServicoRemoto;	
	private CaixaEletronico caixaATM;
}
