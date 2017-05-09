package OrientEtl.main;

import java.io.IOException;

import com.opencsv.CSVReader;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

import paymentCard.PaymentCard;

public class ServiceReader {
	public void readFileAndBuildGraph(CSVReader reader) throws IOException {
		ServiceGraphBuild serviceGraphBuilder = new ServiceGraphBuild();
		String[] line;
		int i = 0;
		OrientGraphFactory factory = new OrientGraphFactory("plocal:/home/gabriel/Desktop/UnB/9-semestre/BDA/orient/orientdb-community-2.2.17/databases/CartõesPagamentosTeste").setupPool(1,10);
		OrientGraph graph = factory.getTx();
		
		ignoreHeader(reader);
		while ((line = reader.readNext()) != null && i != 18) {
			String[] lineDetail = line[0].split("\t", -1);
			PaymentCard card = buildPaymentCard(lineDetail);
			serviceGraphBuilder.buildGraph(graph, card);
			i++;
		}
		graph.shutdown();
	}
	
	private static String[] ignoreHeader(CSVReader reader) throws IOException {
		String[] line = reader.readNext();
		return line;
	}
	
	private static PaymentCard buildPaymentCard(String[] linePayment) throws IOException {
			
		final String VALOR_SIGILOSO = "Informações protegidas por sigilo";
		
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
    	
    	// Caso sigiloso
    	if(linePayment.length == 9) {
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
}
