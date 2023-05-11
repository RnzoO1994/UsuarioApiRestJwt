package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Contacto;
import com.example.demo.repository.ContactoRepository;

@RestController
@RequestMapping("/usuario")
public class ContactoController {
	 private  ContactoRepository contactoRepository;
	 @GetMapping
	 public List<Contacto> listContacto(){
		 return contactoRepository.findAll();
	 }
}
