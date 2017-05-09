package OrientEtl.main;

import java.io.IOException;

import com.opencsv.CSVReader;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

import paymentCard.PaymentCard;

public class ServiceReader {
	public void readFileAndBuildGraph(CSVReader reader) throws IOException {
		ServiceGraphBuild serviceGraphBuilder = new ServiceGraphBuild();
		PaymentCard paymentCard = new PaymentCard();
		String[] line;
		int i = 0;
		OrientGraphFactory factory = new OrientGraphFactory("plocal:/home/gabriel/Desktop/UnB/9-semestre/BDA/orient/orientdb-community-2.2.17/databases/CartõesPagamentosTeste").setupPool(1,10);
		OrientGraph graph = factory.getTx();
		
		ignoreHeader(reader);
		while ((line = reader.readNext()) != null && i != 18) {
			String[] lineDetail = line[0].split("\t", -1);
			PaymentCard card = paymentCard.buildPaymentCard(lineDetail);
			serviceGraphBuilder.buildGraph(graph, card);
			i++;
		}
		graph.shutdown();
	}
	
	private static String[] ignoreHeader(CSVReader reader) throws IOException {
		String[] line = reader.readNext();
		return line;
	}
}
