package com.edu.proyect.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Usuario {

	@Id
	@GeneratedValue
	private Long id;
	private String nombre;
	private String clave;
	private String email;
	private Boolean estado;
	private String fotoPath;
	@Transient
	private String fotourl;
}