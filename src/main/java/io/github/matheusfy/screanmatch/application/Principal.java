package io.github.matheusfy.screanmatch.application;

import io.github.matheusfy.screanmatch.application.menu.MenuFilme;
import io.github.matheusfy.screanmatch.application.menu.MenuSerie;
import io.github.matheusfy.screanmatch.model.api.ConsumoApi;
import io.github.matheusfy.screanmatch.model.repository.EpisodioRepository;

import io.github.matheusfy.screanmatch.model.repository.FilmeRepository;
import io.github.matheusfy.screanmatch.model.repository.SerieRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class Principal {

	private final Scanner cmd;
	private final ConsumoApi api = new ConsumoApi();

	private final MenuSerie menuSerie;
	private final MenuFilme menuFilme;

	private static String API_KEY = "&apikey=%s";

	public Principal(SerieRepository serieRepository, EpisodioRepository episodioRepository,
			FilmeRepository filmeRepository) {
		this.cmd = new Scanner(System.in);
		API_KEY = API_KEY.formatted(System.getenv("OMDB_APIKEY"));
		this.menuSerie = new MenuSerie(serieRepository, episodioRepository, cmd, api);
		this.menuFilme = new MenuFilme(filmeRepository, cmd, api);
	}

	public void exibeMenu() {

		String opcao = "-1";
		while (!opcao.equals("0")) {

			System.out.println("1 - série \n2 - filme \n0 - Sair");
			opcao = cmd.nextLine();

			switch (opcao) {

				case "1" -> menuSerie.exibeMenuSerie();
				case "2" -> menuFilme.exibeMenu();

				case "0" -> System.out.println("Saindo do menu principal");
				default -> throw new IllegalStateException("Unexpected value: " + opcao);
			}
		}
	}

	// FUNÇÕES UTILIDADES
	public static String buildUri(String nomePrograma) {
		// Trata os espaços digitados pelo usuario
		String OMDB_URI = "http://www.omdbapi.com/?";
		return OMDB_URI + "t=" + nomePrograma.replace(" ", "+") + API_KEY;
	}

	private void limpaConsole() {
		// TODO: Fazer funcionar testando aaa
		System.out.println("não esta funcionando");
		System.out.print("\033[H\033[2J");
	}
}