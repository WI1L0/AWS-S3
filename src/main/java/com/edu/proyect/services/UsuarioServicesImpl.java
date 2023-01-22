package com.edu.proyect.services;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.edu.proyect.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edu.proyect.model.Usuario;
import com.edu.proyect.repository.IUsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioServicesImpl implements IUsuarioServicesImpl{

	@Autowired
	private IUsuarioRepository repository;

	@Autowired
	private S3Service s3Service;

	@Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll().stream().peek(Usuario -> Usuario.setFotourl(s3Service.getObjectUrl(Usuario.getFotoPath()))).collect(Collectors.toList());
		//return repository.findAll();
	}

	@Override
	@Transactional
	public Optional<Usuario> findById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	@Transactional
	@PostMapping
	public Usuario save(@RequestBody Usuario usuario) {
		// TODO Auto-generated method stub
		repository.save(usuario);
		usuario.setFotourl(s3Service.getObjectUrl(usuario.getFotoPath()));
		return usuario;
	}

	@Override
	public void deleteByid(Long id) {
		// TODO Auto-generated method stub
		repository.deleteById(id);
	}

	@Override
	public Page<Usuario> findAll(org.springframework.data.domain.Pageable pageable) {
		// TODO Auto-generated method stub
		return repository.findAll(pageable);
	}
}