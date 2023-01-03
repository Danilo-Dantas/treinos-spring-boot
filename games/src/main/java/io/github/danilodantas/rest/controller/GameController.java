package io.github.danilodantas.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.danilodantas.domain.entity.Game;
import io.github.danilodantas.domain.repository.Games;

@RestController
@RequestMapping("/api/games")
public class GameController {

	private Games games;

	private GameController(Games games) {
		this.games = games;
	}

	// CADASTRAR GAME
	@PostMapping
	public void save(@RequestBody Game game) {
		games.save(game);
	}

	// BUSCAR GAME
	@GetMapping("{id}")
	public Game buscar(@PathVariable Integer id) {
		return games.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Nenhum Game possui o ID informado na requisição"));
	}

	// BUSCAR TODOS OS GAMES
	@GetMapping
	public List<Game> buscarTodos() {
		return games.findAll();
	}

	// DELETAR GAME
	@DeleteMapping("{id}")
	public String deletar(@PathVariable Integer id) {
		Optional<Game> statusGame = games.findById(id);

		if (statusGame.isPresent()) {
			games.deleteById(id);
			return "O game de id [" + id + "] foi deletado com SUCESSO!";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum Game possui o ID informado na requisição");
		}
	}

	// ATUALIZAR GAME
	@PatchMapping("{id}")
	public Game atualizar(@PathVariable Integer id, @RequestBody Game game) {
		Optional<Game> statusGame = games.findById(id);
		if (statusGame.isPresent()) {
			game.setId(id);
			if (game.getTitulo() == null || game.getTitulo().isEmpty()) {
				game.setTitulo(games.findById(id).get().getTitulo());
			}
			if (game.getPreco() == null) {
				game.setPreco(games.findById(id).get().getPreco());
			}
			games.saveAndFlush(game);
			return game;
			
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum Game possui o ID informado na requisição");
		}
	}

}
