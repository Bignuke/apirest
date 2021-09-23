package com.boletos.apirest.bankly.models;

public enum CodigoHTTP {

	CODE200(200, "Ok", "Sucesso", true),
	CODE201(201, "Created", "Recurso criado", true),
	CODE202(202, "Accepted", "Requisição aceita, ação será processada de forma assíncrona", true),
	CODE204(204, "No Content", "Requisição aceita e processada, retorno sem conteúdo", true),
	CODE400(400, "Bad Request", "Requisição inválida, recurso obrigatório enviado incorretamente e/ou não enviado", false),
	CODE401(401, "Unauthorized", "Falha de autenticação", false),
	CODE403(403, "Forbidden", "Sua aplicação não está autorizada a acessar esse ambiente", false),
	CODE404(404, "Not found", "Requisição válida e processada, item pesquisado não encontrado", false),
	CODE409(409, "Conflict", "Requisição aceita, porém conflitou com alguma regra de negócio", false),
	CODE500(500, "Internal Server Error", "Ocorreu um erro interno", false);
	
	private CodigoHTTP(int code, String status, String interpretacao, boolean flag) {
		this.code = code;
		this.status = status;
		this.interpretacao = interpretacao;
		this.flag = flag;
	}
	
	private int code;
	private String status;
	private String interpretacao;
	private boolean flag;
	
	
	public int getCode() {
		return code;
	}
	public String getStatus() {
		return status;
	}
	public String getInterpretacao() {
		return interpretacao;
	}
	public boolean isFlag() {
		return flag;
	}
	
	
	public static CodigoHTTP get(int code) {
		for (CodigoHTTP bean : CodigoHTTP.values()) {
			if (bean.getCode()==code)
				return bean;
		}
		return null;
	}
}
