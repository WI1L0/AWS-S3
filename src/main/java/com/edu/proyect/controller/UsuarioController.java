package com.edu.proyect.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.edu.proyect.model.Usuario;
import com.edu.proyect.services.UsuarioServicesImpl;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioServicesImpl Services;
	
	@PostMapping
	public  ResponseEntity<?> create (@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED).body(Services.save(usuario));
	}
	
	@GetMapping("/{id}")
	public  ResponseEntity<?> read (@PathVariable(value = "id") Long usuarioId){
		Optional<Usuario> User = Services.findById(usuarioId);
		
		if (!User.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(User);
		}
	}
	
	@PutMapping("/{id}")
	public  ResponseEntity<?> update (@RequestBody Usuario usuario, @PathVariable(value = "id") Long usuarioId){
		Optional<Usuario> User = Services.findById(usuarioId);
		
		if (!User.isPresent()) {
			return ResponseEntity.notFound().build();
		}
			User.get().setNombre(usuario.getNombre());
			User.get().setEmail(usuario.getEmail());
			User.get().setClave(usuario.getClave());
			User.get().setEstado(usuario.getEstado());
			
			return ResponseEntity.status(HttpStatus.CREATED).body(Services.save(User.get()));
			
		
	}
	
	@DeleteMapping("/{id}")
	public  ResponseEntity<?> delete (@PathVariable(value = "id") Long usuarioId){
		
		if (!Services.findById(usuarioId).isPresent()) {
			return ResponseEntity.notFound().build();
		}
		Services.deleteByid(usuarioId);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping
	public List<Usuario> readAll(){
		List<Usuario> usuarios = StreamSupport
				.stream(Services.findAll().spliterator(), false).collect(Collectors.toList());
		return usuarios;
	}
}
