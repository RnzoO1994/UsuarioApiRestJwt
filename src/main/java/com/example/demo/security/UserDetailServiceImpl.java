package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.models.Usuario;
import com.example.demo.repository.UsuarioRepository;
public class UserDetailServiceImpl implements UserDetailsService {
	@Autowired
	private UsuarioRepository usuarioRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
		Usuario usuario = usuarioRepository
				.findOneByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario con id " +email + "no existe"));
		return new UserDetailsImpl(usuario);
	}
}
