package com.boletos.apirest.resources;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boletos.apirest.entity.Usuario;
import com.boletos.apirest.exception.ServiceException;
import com.boletos.apirest.models.BoletoEmitido;
import com.boletos.apirest.models.BoletoSolicitado;
import com.boletos.apirest.services.BoletoService;
import com.boletos.apirest.bankly.models.Account;
import com.boletos.apirest.bankly.models.BankSlip;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api")
@Api(value = "API REST Boletos")
@CrossOrigin(origins = "*")
public class BoletoResource {
	
	private BoletoService service;
	
	public BoletoResource() {
		this.service = new BoletoService();
	}
	
	@PostMapping("/boleto")
	@ApiOperation(value = "Emissão de boleto")
	public BankSlip emissao(@RequestBody BoletoSolicitado boleto) {
		try {
			Usuario user = service.buscar(boleto.getUser()); 
			if (user.getId() > 0) {
				boleto.setUser(user);
				return service.emitir(boleto);
			}
			else {
				return new BankSlip("Usuário/Senha inválido.");
			}
		} catch (ServiceException e) {
			return new BankSlip(e.getMessage());
		}
	}
	
	@DeleteMapping("/boleto")
	@ApiOperation(value = "Cancelamento de boleto")
	public BankSlip cancelar(@RequestBody BoletoEmitido boleto) {
		try {
			Usuario user = service.buscar(boleto.getUser()); 
			if (user.getId() > 0) {
				boleto.setUser(user);
				return service.cancelar(boleto);
			}
			else {
				return new BankSlip("Usuário/Senha inválido.");
			}
		} catch (ServiceException e) {
			return new BankSlip(e.getMessage());
		}
	}
	
	@PutMapping("/boleto")
	@ApiOperation(value = "Baixa de boleto")
	public BankSlip finalizar(@RequestBody BoletoEmitido boleto) {
		try {
			Usuario user = service.buscar(boleto.getUser()); 
			if (user.getId() > 0) {
				boleto.setUser(user);
				return service.finalizar(boleto);
			}
			else {
				return new BankSlip("Usuário/Senha inválido.");
			}
		} catch (ServiceException e) {
			return new BankSlip(e.getMessage());
		}
	}

	@GetMapping("/boleto/{username}/{password}/{branch}/{number}/{authenticationCode}")
	@ApiOperation(value = "Busca um boleto")
	public BankSlip buscar(
			@PathVariable(value = "username") String username,
			@PathVariable(value = "password") String password,
			@PathVariable(value = "branch") String branch,
			@PathVariable(value = "number") String number,
			@PathVariable(value = "authenticationCode") String authenticationCode) {
		
		BoletoEmitido boleto = new BoletoEmitido();
		boleto.setUser(new Usuario(username, password));
		boleto.setAccount(new Account(branch, number));
		boleto.setAuthenticationCode(authenticationCode);

		try {
			Usuario user = service.buscar(boleto.getUser()); 
			if (user.getId() > 0) {
				boleto.setUser(user);
				return service.buscar(boleto);
			}
			else {
				return new BankSlip("Usuário/Senha inválido.");
			}
		} catch (ServiceException e) {
			return new BankSlip(e.getMessage());
		}
	}
	
}
