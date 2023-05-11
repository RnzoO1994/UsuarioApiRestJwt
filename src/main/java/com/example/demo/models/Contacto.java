package com.example.demo.models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Contacto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idecontacto")
	private Integer id;
	private String nombre;
	@Column(name = "fechanac")
	private LocalDate fechaNacimiento;
	private String celular;
	private String email;

}
