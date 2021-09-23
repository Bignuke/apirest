package com.boletos.apirest.bankly;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.boletos.apirest.bankly.models.Account;
import com.boletos.apirest.bankly.models.Address;
import com.boletos.apirest.bankly.models.Amount;
import com.boletos.apirest.bankly.models.Authentication;
import com.boletos.apirest.bankly.models.BankSlip;
import com.boletos.apirest.bankly.models.CodigoHTTP;
import com.boletos.apirest.bankly.models.Payment;
import com.boletos.apirest.bankly.models.Person;
import com.boletos.apirest.exception.APIException;
import com.boletos.apirest.models.BoletoEmitido;
import com.boletos.apirest.utils.DateUtils;
import com.boletos.apirest.utils.StringUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class BanklyAPI {

	public static final String API_VERSION = "1.0";
	public String URL_API;
	public String URL_AUTH;

	public BanklyAPI(String urlAuth, String urlApi) {
		this.URL_AUTH = urlAuth;
		this.URL_API = urlApi;
	}

	public Authentication getAuth(String grantType, String clientId, String clientSecret) throws APIException {
		StringBuilder content = new StringBuilder();
		content.append("grant_type=").append(grantType);
		content.append("&client_id=").append(clientId);
		content.append("&client_secret=").append(clientSecret);
		
		try {
			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
			RequestBody body = RequestBody.create(content.toString(), mediaType);
			Request request = new Request.Builder()
					.url(URL_AUTH + "/connect/token")
					.post(body)
					.addHeader("Accept", "application/json")
					.addHeader("Content-Type", "application/x-www-form-urlencoded")
					.build();

			Response response = client.newCall(request).execute();
			CodigoHTTP codigo = CodigoHTTP.get(response.code());
			if (!codigo.isFlag()) {
				throw new APIException(codigo.getStatus());
			}
			
			ResponseBody responseBody = response.body();
			JSONObject json = new JSONObject(responseBody.string());

			Authentication auth = new Authentication();
			auth.setAccessToken(json.getString("access_token"));
			auth.setExpiresIn(json.getInt("expires_in"));
			auth.setTokenType(json.getString("token_type"));
			auth.setCreateToken(DateUtils.dataAtual());
			return auth;
		
		} catch (IOException e) {
			throw new APIException(e);
		}
	}

	public BankSlip buscar(String token, String branch, String number, String authenticationCode) throws APIException {
		StringBuilder url = new StringBuilder();
		url.append(URL_API).append("/bankslip");
		url.append("/branch/").append(branch);
		url.append("/number/").append(number);
		url.append("/").append(authenticationCode);
		
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
			  .url(url.toString())
			  .get()
			  .addHeader("Accept", "text/plain")
			  .addHeader("api-version", API_VERSION)
			  .addHeader("Authorization", "Bearer " + token)
			  .build();

			Response response = client.newCall(request).execute();
			CodigoHTTP codigo = CodigoHTTP.get(response.code());
			if (!codigo.isFlag()) {
				throw new APIException(codigo.getStatus());
			}
			
			ResponseBody responseBody = response.body();
			JSONObject json = new JSONObject(responseBody.string());
			return getBankSlip(json);
			
		} catch (IOException e) {
			throw new APIException(e);
		}
	}
	
	public BoletoEmitido emitir(String token, String alias, String branch, String number, String documentNumber,
			Double value, Date dueDate, Boolean emissionFee, String type, Person payer) throws APIException {
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		sb.append("\"account\":{");
		sb.append("\"number\":\"").append(number).append("\",");
		sb.append("\"branch\":\"").append(branch).append("\"");
		sb.append("},");
		sb.append("\"amount\":").append(value).append(",");
		sb.append("\"documentNumber\":\"").append(documentNumber).append("\",");
		sb.append("\"dueDate\":\"").append(DateUtils.formatarBankly(dueDate)).append("\",");
		if (alias != null)
			sb.append("\"alias\":\"").append(alias).append("\",");
		if (emissionFee != null)
			sb.append("\"emissionFee\":").append(emissionFee).append(",");
		if (type != null)
			sb.append("\"type\":\"").append(type).append("\",");
		if (payer != null) {
			StringBuilder sbPayer = new StringBuilder();
			sbPayer.append("\"payer\":{");
			if (payer.getAddress() != null) {
				StringBuilder sbAddress = new StringBuilder();
				sbAddress.append("\"address\":{");
				if (payer.getAddress().getAddressLine() != null)
					sbAddress.append("\"addressLine\":\"").append(payer.getAddress().getAddressLine()).append("\",");
				if (payer.getAddress().getCity() != null)
					sbAddress.append("\"city\":\"").append(payer.getAddress().getCity()).append("\",");
				if (payer.getAddress().getState() != null)
					sbAddress.append("\"state\":\"").append(payer.getAddress().getState()).append("\",");
				if (payer.getAddress().getZipCode() != null)
					sbAddress.append("\"zipCode\":\"").append(payer.getAddress().getZipCode()).append("\",");
				sbPayer.append(sbAddress.substring(0, sbAddress.lastIndexOf(","))).append("},");
			}
			if (payer.getDocument() != null)
				sbPayer.append("\"document\":\"").append(payer.getDocument()).append("\",");
			if (payer.getName() != null)
				sbPayer.append("\"name\":\"").append(payer.getName()).append("\",");
			if (payer.getTradeName() != null)
				sbPayer.append("\"tradeName\":\"").append(payer.getTradeName()).append("\",");
			sb.append(sbPayer.substring(0, sbPayer.lastIndexOf(","))).append("},");
		}

		String content = sb.substring(0, sb.lastIndexOf(",")) + "}";

		try {
			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/*+json");
			RequestBody body = RequestBody.create(content, mediaType);
			Request request = new Request.Builder()
					.url(URL_API + "/bankslip")
					.post(body)
					.addHeader("Accept", "text/plain")
					.addHeader("api-version", API_VERSION)
					.addHeader("Content-Type", "application/*+json")
					.addHeader("Authorization", "Bearer " + token)
					.build();
			
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			
			CodigoHTTP codigo = CodigoHTTP.get(response.code());
			if (!codigo.isFlag()) {
				String message = getMessage(responseBody);
				if (StringUtils.isEmpty(message))	message = codigo.getStatus();
				throw new APIException(message);
			}
			
			JSONObject json = new JSONObject(responseBody);
			BoletoEmitido bean = new BoletoEmitido();
			if (json.has("account")) {
				JSONObject jsonAccount = json.getJSONObject("account");
				Account account = new Account();
				if (jsonAccount.has("branch"))	account.setBranch(jsonAccount.getString("branch"));
				if (jsonAccount.has("number"))	account.setNumber(jsonAccount.getString("number"));
				bean.setAccount(account);
			}
			if (json.has("authenticationCode")) bean.setAuthenticationCode(json.getString("authenticationCode"));
			return bean;
			
		} catch (IOException e) {
			throw new APIException(e);
		}
	}
	
	public void cancelar(String token, String branch, String number, String authenticationCode) throws APIException {
		StringBuilder content = new StringBuilder();
		content.append("{");
		content.append("\"account\":{");
		content.append("\"branch\":\"").append(branch).append("\",");
		content.append("\"number\":\"").append(number).append("\"");
		content.append("},");
		content.append("\"authenticationCode\":\"").append(authenticationCode).append("\"");
		content.append("}");
		
		try {
			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/*+json");
			RequestBody body = RequestBody.create(content.toString(), mediaType);
			Request request = new Request.Builder()
					.url(URL_API + "/bankslip/cancel")
					.delete(body)
					.addHeader("api-version", API_VERSION)
					.addHeader("Content-Type", "application/*+json")
					.addHeader("Authorization", "Bearer " + token)
					.build();

			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			
			CodigoHTTP codigo = CodigoHTTP.get(response.code());
			if (!codigo.isFlag()) {
				String message = getMessage(responseBody);
				if (StringUtils.isEmpty(message))	message = codigo.getStatus();
				throw new APIException(message);
			}
			
		} catch (IOException e) {
			throw new APIException(e);
		}
	}
	
	public void finalizar(String token, String branch, String number, String authenticationCode) throws APIException {
		StringBuilder content = new StringBuilder();
		content.append("{");
		content.append("\"account\":{");
		content.append("\"branch\":\"").append(branch).append("\",");
		content.append("\"number\":\"").append(number).append("\"");
		content.append("},");
		content.append("\"authenticationCode\":\"").append(authenticationCode).append("\"");
		content.append("}");
		
		try {
			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/*+json");
			RequestBody body = RequestBody.create(content.toString(), mediaType);
			Request request = new Request.Builder()
					.url(URL_API + "/bankslip/settlementpayment")
					.post(body)
					.addHeader("api-version", API_VERSION)
					.addHeader("Content-Type", "application/*+json")
					.addHeader("Authorization", "Bearer " + token)
					.build();

			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			
			CodigoHTTP codigo = CodigoHTTP.get(response.code());
			if (!codigo.isFlag()) {
				String message = getMessage(responseBody);
				if (StringUtils.isEmpty(message))	message = codigo.getStatus();
				throw new APIException(message);
			}
			
		} catch (IOException e) {
			throw new APIException(e);
		}
	}
	
	public void transferencia(String token, String correlation, Double amount,
			String sender_branch, String sender_account, String sender_document, String sender_name,
			String accountType, String bankCode,
			String recipient_branch, String recipient_account, String recipient_document, String recipient_name,
			String description) throws APIException {
		
		StringBuilder content = new StringBuilder();
		content.append("{");
		content.append("\"amount\":").append(amount).append(",");
		content.append("\"sender\":{");
		content.append("\"branch\":\"").append(sender_branch).append("\",");
		content.append("\"account\":\"").append(sender_account).append("\",");
		content.append("\"document\":\"").append(sender_document).append("\",");
		content.append("\"name\":\"").append(sender_name).append("\"");
		content.append("},");
		content.append("\"recipient\":{");
		content.append("\"accountType\":\"").append(accountType).append("\",");
		content.append("\"bankCode\":\"").append(bankCode).append("\",");
		content.append("\"branch\":\"").append(recipient_branch).append("\",");
		content.append("\"account\":\"").append(recipient_account).append("\",");
		content.append("\"document\":\"").append(recipient_document).append("\",");
		content.append("\"name\":\"").append(recipient_name).append("\"");
		content.append("},");
		content.append("\"description\":\"").append(description).append("\"");
		content.append("}");
		
		try {
			OkHttpClient client = new OkHttpClient();
			MediaType mediaType = MediaType.parse("application/json");
			RequestBody body = RequestBody.create(content.toString(), mediaType);
			Request request = new Request.Builder()
					.url(URL_API + "/fund-transfers")
					.post(body)
					.addHeader("Accept", "application/json")
					.addHeader("x-correlation-id", correlation)
					.addHeader("api-version", API_VERSION)
					.addHeader("Content-Type", "application/json")
					.addHeader("Authorization", "Bearer " + token)
					.build();
			
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			
			CodigoHTTP codigo = CodigoHTTP.get(response.code());
			if (!codigo.isFlag()) {
				String message = getMessage(responseBody);
				if (StringUtils.isEmpty(message))	message = codigo.getStatus();
				throw new APIException(message);
			}
			
			
		} catch (IOException e) {
			throw new APIException(e);
		}
	}
	
	/************************************************************************************************/
	
	private BankSlip getBankSlip(JSONObject json) {
		
		BankSlip bean = new BankSlip();
		if (json.has("authenticationCode"))	bean.setAuthenticationCode(json.getString("authenticationCode"));
		if (json.has("updatedAt"))			bean.setUpdatedAt(DateUtils.converterBankly(json.getString("updatedAt")));
		if (json.has("ourNumber"))			bean.setOurNumber(json.getString("ourNumber"));
		if (json.has("digitable"))			bean.setDigitable(json.getString("digitable"));
		if (json.has("status"))		  		bean.setStatus(json.getString("status"));
		if (json.has("account")) {
			JSONObject jsonAccount = json.getJSONObject("account");
			Account account = new Account();
			if (jsonAccount.has("number"))	account.setNumber(jsonAccount.getString("number"));
			if (jsonAccount.has("branch"))	account.setBranch(jsonAccount.getString("branch"));
			bean.setAccount(account);
		}
		if (json.has("document"))		  	bean.setDocument(json.getString("document"));
		if (json.has("amount")) {
			JSONObject jsonAmount = json.getJSONObject("amount");
			Amount amount = new Amount();
			if (jsonAmount.has("currency"))	amount.setCurrency(jsonAmount.getString("currency"));
			if (jsonAmount.has("value"))	amount.setValue(jsonAmount.getDouble("value"));
			bean.setAmount(amount);
		}
		if (json.has("dueDate"))			bean.setDueDate(DateUtils.converterBankly(json.getString("dueDate")));
		if (json.has("emissionDate"))		bean.setEmissionDate(DateUtils.converterBankly(json.getString("emissionDate")));
		if (json.has("type"))		  		bean.setType(json.getString("type"));
		if (json.has("payer")) {
			JSONObject jsonPayer = json.getJSONObject("payer");
			Person payer = new Person();
			if (jsonPayer.has("document"))	payer.setDocument(jsonPayer.getString("document"));
			if (jsonPayer.has("name"))		payer.setName(jsonPayer.getString("name"));
			if (jsonPayer.has("tradeName"))	payer.setTradeName(jsonPayer.getString("tradeName"));
			if (jsonPayer.has("address")) {
				JSONObject jsonAddress = jsonPayer.getJSONObject("address");
				Address address = new Address();
				if (jsonAddress.has("addressLine"))	address.setAddressLine(jsonAddress.getString("addressLine"));
				if (jsonAddress.has("city"))		address.setCity(jsonAddress.getString("city"));
				if (jsonAddress.has("state"))		address.setState(jsonAddress.getString("state"));
				if (jsonAddress.has("zipCode"))		address.setZipCode(jsonAddress.getString("zipCode"));
				payer.setAddress(address);
			}
			bean.setPayer(payer);
		}
		if (json.has("recipientFinal")) {
			JSONObject jsonRecipientFinal = json.getJSONObject("recipientFinal");
			Person recipientFinal = new Person();
			if (jsonRecipientFinal.has("document"))		recipientFinal.setDocument(jsonRecipientFinal.getString("document"));
			if (jsonRecipientFinal.has("name"))			recipientFinal.setName(jsonRecipientFinal.getString("name"));
			if (jsonRecipientFinal.has("tradeName"))	recipientFinal.setTradeName(jsonRecipientFinal.getString("tradeName"));
			if (jsonRecipientFinal.has("address")) {
				JSONObject jsonAddress = jsonRecipientFinal.getJSONObject("address");
				Address address = new Address();
				if (jsonAddress.has("addressLine"))	address.setAddressLine(jsonAddress.getString("addressLine"));
				if (jsonAddress.has("city"))		address.setCity(jsonAddress.getString("city"));
				if (jsonAddress.has("state"))		address.setState(jsonAddress.getString("state"));
				if (jsonAddress.has("zipCode"))		address.setZipCode(jsonAddress.getString("zipCode"));
				recipientFinal.setAddress(address);
			}
			bean.setRecipientFinal(recipientFinal);
		}
		if (json.has("recipientOrigin")) {
			JSONObject jsonRecipientOrigin = json.getJSONObject("recipientOrigin");
			Person recipientOrigin = new Person();
			if (jsonRecipientOrigin.has("document"))	recipientOrigin.setDocument(jsonRecipientOrigin.getString("document"));
			if (jsonRecipientOrigin.has("name"))		recipientOrigin.setName(jsonRecipientOrigin.getString("name"));
			if (jsonRecipientOrigin.has("tradeName"))	recipientOrigin.setTradeName(jsonRecipientOrigin.getString("tradeName"));
			if (jsonRecipientOrigin.has("address")) {
				JSONObject jsonAddress = jsonRecipientOrigin.getJSONObject("address");
				Address address = new Address();
				if (jsonAddress.has("addressLine"))	address.setAddressLine(jsonAddress.getString("addressLine"));
				if (jsonAddress.has("city"))		address.setCity(jsonAddress.getString("city"));
				if (jsonAddress.has("state"))		address.setState(jsonAddress.getString("state"));
				if (jsonAddress.has("zipCode"))		address.setZipCode(jsonAddress.getString("zipCode"));
				recipientOrigin.setAddress(address);
			}
			bean.setRecipientOrigin(recipientOrigin);
		}
		if (json.has("payments")) {
			JSONArray jsonPayments = json.getJSONArray("payments");
			List<Payment> payments = new ArrayList<Payment>();
			for (int i = 0; i < jsonPayments.length(); i++) {
				JSONObject jsonPayment = jsonPayments.getJSONObject(i);
				Payment payment = new Payment();
				if (jsonPayment.has("id"))				payment.setId(jsonPayment.getString("id"));
				if (jsonPayment.has("amount"))			payment.setAmount(jsonPayment.getDouble("amount"));
				if (jsonPayment.has("paymentChannel"))	payment.setPaymentChannel(jsonPayment.getString("paymentChannel"));
				if (jsonPayment.has("paidOutDate"))		payment.setPaidOutDate(DateUtils.converterBankly(jsonPayment.getString("paidOutDate")));
				payments.add(payment);
			}
			bean.setPayments(payments);
		}
		return bean;
	}
	
	
	private String getMessage(String responseBody) {
		String errors = "";
		if (responseBody.startsWith("{")) {
			JSONObject json = new JSONObject(responseBody);
			if (json.has("errors")) {
				JSONObject obj = json.getJSONObject("errors");
				errors = obj.toString();
			}
		}
		else if (responseBody.startsWith("[")) {
			JSONArray array = new JSONArray(responseBody);
			for (int i = 0; i < array.length(); i++) {
				JSONObject json = array.getJSONObject(i);
				if (json.has("message")) {
					errors += json.getString("message");
				}	
			}
		}
		return errors.isEmpty() ? null : errors;
	}

	/************************************************************************************************/
	
	public Address buscar(String token, String zipCode) throws APIException {
		try {
			OkHttpClient client = new OkHttpClient();
			Request request = new Request.Builder()
					.url(URL_API + "/addresses/" + zipCode)
					.get()
					.addHeader("Accept", "application/json")
					.addHeader("api-version", API_VERSION)
					.addHeader("Authorization", "Bearer " + token)
					.build();
			
			Response response = client.newCall(request).execute();
			String responseBody = response.body().toString();
			CodigoHTTP codigo = CodigoHTTP.get(response.code());
			if (!codigo.isFlag()) {
				String message = getMessage(responseBody);
				if (StringUtils.isEmpty(message))	message = codigo.getStatus();
				throw new APIException(message);
			}
			
			JSONObject json = new JSONObject(responseBody);
			Address bean = new Address();
			if (json.has("addressLine"))	bean.setAddressLine(json.getString("addressLine"));
			if (json.has("city"))			bean.setCity(json.getString("city"));
			if (json.has("state"))			bean.setState(json.getString("state"));
			if (json.has("zipCode"))		bean.setZipCode(json.getString("zipCode"));
			if (json.has("neighborhood"))	bean.setNeighborhood(json.getString("neighborhood"));
			if (json.has("country"))		bean.setCountry(json.getString("country"));
			return bean;
			
		} catch (IOException e) {
			throw new APIException(e);
		}
	}
}
