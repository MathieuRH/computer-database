package com.excilys.cdb.api;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.exceptions.InputException;
import com.excilys.cdb.model.AuthenticationRequest;
import com.excilys.cdb.model.AuthenticationResponse;
import com.excilys.cdb.util.UtilJwt;

@RestController
public class IdentificationRestController {
	
	private AuthenticationManager authenticationManager;
	private UserDetailsService userDetailsService;
	private UtilJwt jwtTokenUtil;
	
	public IdentificationRestController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UtilJwt jwtTokenUtil) {
		this.authenticationManager=authenticationManager;
		this.userDetailsService=userDetailsService;
		this.jwtTokenUtil=jwtTokenUtil;
	}
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws InputException {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
			
		} catch (BadCredentialsException e) {
			throw new InputException("Incorrect username or password");
		}
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		String jwt = jwtTokenUtil.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
