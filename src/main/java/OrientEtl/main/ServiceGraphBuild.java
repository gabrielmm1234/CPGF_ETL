package OrientEtl.main;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import paymentCard.PaymentCard;

public class ServiceGraphBuild {
	public void buildGraph(OrientGraph graph, PaymentCard card) {
		try{
		  Vertex orgaoSuperior = buildVertexOrgaoSuperior(graph, card);
		  
		  Vertex orgaoSubordinado = buildVertexOrgaoSubordinado(graph, card);
		  
		  buildEdgeSupervisiona(graph, orgaoSuperior, orgaoSubordinado);
		  
		  Vertex unidadeGestora = buildVertexUnidadeGestora(graph, card);
		  
		  buildEdgePossuiUnidadeGestora(graph, orgaoSubordinado, unidadeGestora);
		  graph.commit();
		} catch( Exception e ) {
		  graph.rollback();
		}
	}
	
	private static Vertex buildVertexOrgaoSuperior (OrientGraph graph, PaymentCard card) {
		Iterable<Vertex> vertice = graph.getVertices("OrgaoSuperior.nomeOrgSup", card.getNomeOrgSup());
		
		if(!vertice.iterator().hasNext()) {
			 Vertex orgaoSuperior = graph.addVertex("class:OrgaoSuperior");
			 orgaoSuperior.setProperty("nomeOrgSup", card.getNomeOrgSup());
			 orgaoSuperior.setProperty("codOrgSup", card.getCodOrgSup());
			 return orgaoSuperior;
		} else {
			return vertice.iterator().next();
		}
	}
	
	private Vertex buildVertexOrgaoSubordinado (OrientGraph graph, PaymentCard card) {
		Iterable<Vertex> vertice = graph.getVertices("OrgaoSubordinado.nomeOrgSub", card.getNomeOrgSub());
		if(!vertice.iterator().hasNext()) {
			 Vertex orgaoSubordinado = graph.addVertex("class:OrgaoSubordinado");
			  orgaoSubordinado.setProperty("nomeOrgSub", card.getNomeOrgSub());
			  orgaoSubordinado.setProperty("codOrgSub", card.getCodOrgSub());
			 return orgaoSubordinado;
		} else {
			return vertice.iterator().next();
		}
	}
	
	private Edge buildEdgeSupervisiona(OrientGraph graph, Vertex orgaoSuperior, Vertex orgaoSubordinado) {
		Iterable<Edge> itOut = orgaoSuperior.getEdges(Direction.OUT, "Supervisiona");
		for(Edge e:itOut){
			String nomeOrgSup = e.getVertex(Direction.OUT).getProperty("nomeOrgSup");
			String nomeOrgSub = e.getVertex(Direction.IN).getProperty("nomeOrgSub");
			
			if(nomeOrgSup.equals(orgaoSuperior.getProperty("nomeOrgSup")) && nomeOrgSub.equals(orgaoSubordinado.getProperty("nomeOrgSub"))) {
				return null;
			}
		}
		return graph.addEdge(null, orgaoSuperior, orgaoSubordinado, "Supervisiona");
	}
	
	private Vertex buildVertexUnidadeGestora(OrientGraph graph, PaymentCard card) {
		Iterable<Vertex> vertice = graph.getVertices("UnidadeGestora.nomeUniGest", card.getNomeUnidadeGestora());
		if(!vertice.iterator().hasNext()) {
			 Vertex unidadeGestora = graph.addVertex("class:UnidadeGestora");
			 unidadeGestora.setProperty("codUniGest", card.getCodUnidadeGestora());
			 unidadeGestora.setProperty("nomeUniGest", card.getNomeUnidadeGestora());
			 return unidadeGestora;
		} else {
			return vertice.iterator().next();
		}
	}
	
	private Edge buildEdgePossuiUnidadeGestora(OrientGraph graph, Vertex orgaoSubordinado, Vertex unidadeGestora) {
		Iterable<Edge> itOut = orgaoSubordinado.getEdges(Direction.OUT, "PossuiUniGest");
		for(Edge e:itOut){
			String nomeOrgSub = e.getVertex(Direction.OUT).getProperty("nomeOrgSub");
			String nomeUniGest = e.getVertex(Direction.IN).getProperty("nomeUniGest");
			
			System.out.println("nome org sub - " + nomeOrgSub);
			System.out.println("nome uni gest - " + nomeUniGest);
			System.out.println("nome org sub param - " + orgaoSubordinado.getProperty("nomeOrgSub"));
			System.out.println("nome uni gest param - " + unidadeGestora.getProperty("nomeUniGest"));

			
			if(nomeOrgSub.equals(orgaoSubordinado.getProperty("nomeOrgSub")) && nomeUniGest.equals(unidadeGestora.getProperty("nomeUniGest"))) {
				return null;
			}
		}
		return graph.addEdge(null, orgaoSubordinado, unidadeGestora, "PossuiUniGest");
	}
}
