package com.gusta.banco.atm;

public class MockHardware implements IHardware {

	private String _numeroDaContaCartao;
	public enum FalhasHW { LEITURA_DO_CARTAO }
	private FalhasHW _erroForcado;
	
	public MockHardware() {	}

	public void setNumeroDaConta(String numeroDaConta) {
		this._numeroDaContaCartao = numeroDaConta;
	}
	
	public void setFalha(FalhasHW falha) {
		this._erroForcado = falha;
	}

	
	@Override
	public String pegarNumeroDaContaCartao() throws FalhaNaLeituraDoCartaoException {
		if (this._erroForcado == FalhasHW.LEITURA_DO_CARTAO)
			throw new FalhaNaLeituraDoCartaoException("Erro for�ado pelo mock na leitura do cart�o.");
		
		return this._numeroDaContaCartao;
	}

	@Override
	public void entregarDinheiro() {
		// TODO Auto-generated method stub

	}

	@Override
	public void lerEnvelope() {
		// TODO Auto-generated method stub

	}


}
