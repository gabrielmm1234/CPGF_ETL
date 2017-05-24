package OrientEtl.main;

import java.io.IOException;

import com.opencsv.CSVReader;
import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;

import paymentCard.PaymentCard;

public class ServiceReader {
	public void readFileAndBuildGraph(CSVReader reader) throws IOException {
		ServiceGraphBuild serviceGraphBuilder = new ServiceGraphBuild();
		PaymentCard paymentCard = new PaymentCard();
		String[] line;
		int i = 0;
		
		try {
			ignoreHeader(reader);
			// CREATE A SERVER ADMIN CLIENT AGAINST A REMOTE SERVER TO CHECK IF DB EXISTS		
			OServerAdmin serverAdmin = new OServerAdmin("remote:localhost:2425/CartoesPagamentos").connect("root","e54gfgfgf");
			if(!serverAdmin.existsDatabase("CartoesPagamentos", "plocal")) {
				serverAdmin.createDatabase("CartoesPagamentos", "graph", "plocal");
			}
			OrientGraphFactory factory = new OrientGraphFactory("remote:localhost:2425/CartoesPagamentos").setupPool(1,10);
			OrientGraph graph = factory.getTx();
			while ((line = reader.readNext()) != null) {
				String[] lineDetail = line[0].split("\t", -1);
				PaymentCard card = paymentCard.buildPaymentCard(lineDetail);
				
				if(card == null) {
					continue;
				}
				
				serviceGraphBuilder.buildGraph(graph, card);
				i++;
			}
			graph.shutdown();
			serverAdmin.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String[] ignoreHeader(CSVReader reader) throws IOException {
		String[] line = reader.readNext();
		return line;
	}
}
