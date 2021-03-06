package com.gust.placar;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.gust.armazenamento.Armazenamento;
import com.gust.armazenamento.FalhaArmazenamentoException;
import com.gust.user.UserNotFoundException;

public class Placar {

	private Armazenamento armazenamento;

	public Placar(Armazenamento armazenamento) {
		setArmazenamento(armazenamento);
	}

	public void registraPonto(String username, TipoPonto tipoPonto, int quantidade)
			throws UserNotFoundException, FalhaArmazenamentoException {
		if (username == null)
			throw new IllegalArgumentException("Usu�rio inv�lido.");
		if (tipoPonto == null)
			throw new IllegalArgumentException("Argumento nulo.");
		if (quantidade <= 0)
			throw new IllegalArgumentException("Quantidade de pontos deve ser maior que zero.");

		armazenamento.addPontos(username, tipoPonto, quantidade);
	}

	public Map<TipoPonto, Integer> consultaPontos(String username)
			throws UserNotFoundException, FalhaArmazenamentoException {
		if (username == null)
			throw new IllegalArgumentException("Usu�rio inv�lido.");

		HashMap<TipoPonto, Integer> relacaoDePontos = new HashMap<TipoPonto, Integer>();
		for (TipoPonto tipo : armazenamento.getTiposDePontosJaRegistrados(username)) {
			int totalPontos = armazenamento.getPontos(username, tipo);
			if (totalPontos > 0)
				relacaoDePontos.put(tipo, totalPontos);
		}
		return relacaoDePontos;
	}

	public ArrayList<ItemRanking> consultaRanking(TipoPonto tipo) throws FalhaArmazenamentoException {
		if (tipo == null)
			throw new IllegalArgumentException("Argumento nulo.");

			var ranking = new ArrayList<ItemRanking>();

			for (String user : armazenamento.getUsuariosPorTipoPonto(tipo)) {
				try {
					int pontos = armazenamento.getPontos(user, tipo);
					ranking.add(new ItemRanking(user, pontos));
				}
				catch(UserNotFoundException e) {}
			}
			ranking.sort(Comparator.naturalOrder());
			return ranking;
	}
	
	// Getters e Setters
	private void setArmazenamento(Armazenamento armazenamento) {
		this.armazenamento = armazenamento;
	}

}
