package io.github.danilodantas.rest.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import io.github.danilodantas.domain.entity.Usuario;
import io.github.danilodantas.domain.repository.Usuarios;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	private Usuarios usuarios;
	
	private UsuarioController(Usuarios usuarios) {
		this.usuarios = usuarios;
	}
	
	//SALVAR USUARIO
	@PostMapping
	public void salvar(@RequestBody Usuario usuario) {
		usuarios.save(usuario);
	}
	
	//BUSCAR USUARIO
	@GetMapping("{id}")
	public Usuario buscar(@PathVariable Integer id ) {
		return usuarios.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "O usuário de id [" + id + "] não foi localizado.")); 
	}

	//DELETAR USUARIO
	@DeleteMapping("{id}")
	public String deletar(@PathVariable Integer id) {
		Optional<Usuario> usuarioStatus = usuarios.findById(id);
		if (usuarioStatus.isPresent()) {
			usuarios.deleteById(id);
			return "Usuário de id [" + id + "] deletado com SUCESSO!";
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "usuario não encontrado.");
		}
	}
	
	//ATUALIZAR USUARIO
	@PatchMapping("{id}")
	public Usuario atualizar(@PathVariable Integer id, @RequestBody Usuario usuario) {
		Optional<Usuario> usuarioStatus = usuarios.findById(id);
		if (usuarioStatus.isPresent()) {
			usuario.setId(id);
			usuarios.save(usuario);
			return usuario;
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario de id [" + id + "] não pode ser atualizado, pois não foi localizado.");
		}
	}
	
	//BUSCAR TODOS OS USUARIOS
	@GetMapping
	public List<Usuario> listarTodos() {
		return usuarios.findAll();
	}
	
	
	
}
