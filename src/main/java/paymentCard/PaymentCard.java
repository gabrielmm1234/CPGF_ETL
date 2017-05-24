package paymentCard;

import java.io.IOException;

public class PaymentCard {
	
	private Long codOrgSup;
	private String nomeOrgSup;
	private Long codOrgSub;
	private String nomeOrgSub;
	private Long codUnidadeGestora;
	private String nomeUnidadeGestora;
	private int anoExtrato;
	private int mesExtrato;
	private String cpfPortador;
	private String nomePortador;
	private String nometransacao;
	private String dataTransacao;
	private String cnpjOuCpfFavorecido;
	private String nomeFavorecido;
	private double valorTransacao;
	

	public PaymentCard(Long codOrgSup, String nomeOrgSup, Long codOrgSub, String nomeOrgSub, Long codUnidadeGestora,
			String nomeUnidadeGestora, int anoExtrato, int mesExtrato, String cpfPortador, String nomePortador,
			String nometransacao, String dataTransacao, String cnpjOuCpfFavorecido, String nomeFavorecido,
			double valorTransacao) {
		this.codOrgSup = codOrgSup;
		this.nomeOrgSup = nomeOrgSup;
		this.codOrgSub = codOrgSub;
		this.nomeOrgSub = nomeOrgSub;
		this.codUnidadeGestora = codUnidadeGestora;
		this.nomeUnidadeGestora = nomeUnidadeGestora;
		this.anoExtrato = anoExtrato;
		this.mesExtrato = mesExtrato;
		this.cpfPortador = cpfPortador;
		this.nomePortador = nomePortador;
		this.nometransacao = nometransacao;
		this.dataTransacao = dataTransacao;
		this.cnpjOuCpfFavorecido = cnpjOuCpfFavorecido;
		this.nomeFavorecido = nomeFavorecido;
		this.valorTransacao = valorTransacao;
	}
	
	public PaymentCard() {
		
	}

	public PaymentCard buildPaymentCard(String[] linePayment) throws IOException {
		
		final String VALOR_SIGILOSO = "Informações protegidas por sigilo";
		
    	System.out.println(linePayment.length);
    	
    	// Caso com csv mal formatado
    	if(linePayment.length == 2 || linePayment.length == 4 || linePayment.length == 6) {
    		return null;
    	}
		
    	Long codOrgSup = Long.parseLong(linePayment[0]);
    	String nomeOrgSup = linePayment[1];
    	
    	Long codOrgSub = Long.parseLong(linePayment[2]);
    	String nomeOrgSub = linePayment[3];
    	
    	Long codUnidadeGestora = Long.parseLong(linePayment[4]);
    	String nomeUnidadeGestora = linePayment[5];
    	
    	int anoExtrato = Integer.parseInt(linePayment[6]);
    	int mesExtrato = Integer.parseInt(linePayment[7]);
    	
    	String cpfPortador;
    	String nomePortador;
    	
    	String nometransacao;
    	String dataTransacao;
    	
    	String cnpjOuCpfFavorecido;
    	String nomeFavorecido;
    	
    	Double valorTransacao;
    	
    	// Caso com csv mal formatado
    	if(linePayment.length == 14) {
    		cpfPortador = linePayment[8];
	    	nomePortador = linePayment[9];
	    	
	    	nometransacao = linePayment[10];
	    	dataTransacao = linePayment[11];
	    	
	    	cnpjOuCpfFavorecido = linePayment[12];
	    	nomeFavorecido = linePayment[13];
    		valorTransacao = 0.0;
    	}
    	// Caso sigiloso
    	else if(linePayment.length == 9) {
    		cpfPortador = VALOR_SIGILOSO;
        	nomePortador = VALOR_SIGILOSO;
        	
        	nometransacao = VALOR_SIGILOSO;
        	dataTransacao = VALOR_SIGILOSO;
        	
        	cnpjOuCpfFavorecido = VALOR_SIGILOSO;
        	nomeFavorecido = VALOR_SIGILOSO;
        	
        	valorTransacao =  0.0;
    	} else {
    	
	    	cpfPortador = linePayment[8];
	    	nomePortador = linePayment[9];
	    	
	    	nometransacao = linePayment[10];
	    	dataTransacao = linePayment[11];
	    	
	    	cnpjOuCpfFavorecido = linePayment[12];
	    	nomeFavorecido = linePayment[13];
	    	
	    	valorTransacao =  Double.parseDouble(linePayment[14]);
    	}
    	
    	return new PaymentCard(codOrgSup, nomeOrgSup, 
    			codOrgSub, nomeOrgSub, codUnidadeGestora, 
    			nomeUnidadeGestora, anoExtrato, mesExtrato, 
    			cpfPortador, nomePortador, nometransacao, 
    			dataTransacao, cnpjOuCpfFavorecido, nomeFavorecido, valorTransacao);
	}


	public Long getCodOrgSup() {
		return codOrgSup;
	}


	public void setCodOrgSup(Long codOrgSup) {
		this.codOrgSup = codOrgSup;
	}


	public String getNomeOrgSup() {
		return nomeOrgSup;
	}


	public void setNomeOrgSup(String nomeOrgSup) {
		this.nomeOrgSup = nomeOrgSup;
	}


	public Long getCodOrgSub() {
		return codOrgSub;
	}


	public void setCodOrgSub(Long codOrgSub) {
		this.codOrgSub = codOrgSub;
	}


	public String getNomeOrgSub() {
		return nomeOrgSub;
	}


	public void setNomeOrgSub(String nomeOrgSub) {
		this.nomeOrgSub = nomeOrgSub;
	}


	public Long getCodUnidadeGestora() {
		return codUnidadeGestora;
	}


	public void setCodUnidadeGestora(Long codUnidadeGestora) {
		this.codUnidadeGestora = codUnidadeGestora;
	}


	public String getNomeUnidadeGestora() {
		return nomeUnidadeGestora;
	}


	public void setNomeUnidadeGestora(String nomeUnidadeGestora) {
		this.nomeUnidadeGestora = nomeUnidadeGestora;
	}


	public int getAnoExtrato() {
		return anoExtrato;
	}


	public void setAnoExtrato(int anoExtrato) {
		this.anoExtrato = anoExtrato;
	}


	public int getMesExtrato() {
		return mesExtrato;
	}


	public void setMesExtrato(int mesExtrato) {
		this.mesExtrato = mesExtrato;
	}


	public String getCpfPortador() {
		return cpfPortador;
	}


	public void setCpfPortador(String cpfPortador) {
		this.cpfPortador = cpfPortador;
	}


	public String getNomePortador() {
		return nomePortador;
	}


	public void setNomePortador(String nomePortador) {
		this.nomePortador = nomePortador;
	}


	public String getNometransacao() {
		return nometransacao;
	}


	public void setNometransacao(String nometransacao) {
		this.nometransacao = nometransacao;
	}


	public String getDataTransacao() {
		return dataTransacao;
	}


	public void setDataTransacao(String dataTransacao) {
		this.dataTransacao = dataTransacao;
	}


	public String getCnpjOuCpfFavorecido() {
		return cnpjOuCpfFavorecido;
	}


	public void setCnpjOuCpfFavorecido(String cnpjOuCpfFavorecido) {
		this.cnpjOuCpfFavorecido = cnpjOuCpfFavorecido;
	}


	public String getNomeFavorecido() {
		return nomeFavorecido;
	}


	public void setNomeFavorecido(String nomeFavorecido) {
		this.nomeFavorecido = nomeFavorecido;
	}


	public double getValorTransacao() {
		return valorTransacao;
	}


	public void setValorTransacao(double valorTransacao) {
		this.valorTransacao = valorTransacao;
	}
	
	
}
