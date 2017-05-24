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
		  
		  Vertex portador = buildVertexPortador(graph, card);
		  
		  buildEdgePossuiPortadores(graph, unidadeGestora, portador);
		  
		  Vertex transacao = buildVertexTransacao(graph, card);
		  graph.addEdge(null, portador, transacao, "RealizaTransacao");
		  
		  Vertex favorecido = buildVertexFavorecido(graph, card);
		  graph.addEdge(null, transacao, favorecido, "Favorece");

		  graph.commit();
		} catch( Exception e ) {
		  graph.rollback();
		} finally {
			graph.commit();
		}
	}
	
	private Vertex buildVertexFavorecido(OrientGraph graph, PaymentCard card) {
		if(seFavorecidoVazio(card)) {
			 Iterable<Vertex> vertice = graph.getVertices("Favorecido.nomeFavorecido", "Valor Desconhecido");
				
				if(!vertice.iterator().hasNext()) {
					 Vertex Favorecido = graph.addVertex("class:Favorecido");
					 Favorecido.setProperty("nomeFavorecido", "Valor Desconhecido");
					 Favorecido.setProperty("cpfOuCnpjFavorecido", "Valor Desconhecido");
					 return Favorecido;
				} else {
					return vertice.iterator().next();
				}
		}
		Iterable<Vertex> vertice = graph.getVertices("Favorecido.nomeFavorecido", card.getNomeFavorecido());
		
		if(!vertice.iterator().hasNext()) {
			 Vertex Favorecido = graph.addVertex("class:Favorecido");
			 Favorecido.setProperty("nomeFavorecido", card.getNomeFavorecido());
			 Favorecido.setProperty("cpfOuCnpjFavorecido", card.getCnpjOuCpfFavorecido());
			 return Favorecido;
		} else {
			return vertice.iterator().next();
		}
	}

	private boolean seFavorecidoVazio(PaymentCard card) {
		return card.getNomeFavorecido().isEmpty() || 
				card.getNomeFavorecido().equals("") || 
				card.getNomeFavorecido() == null || 
				card.getNomeFavorecido().equals(" ") ||
				card.getCnpjOuCpfFavorecido().equals("") ||
				card.getCnpjOuCpfFavorecido() == null;
	}
	
	private Vertex buildVertexTransacao(OrientGraph graph, PaymentCard card) {
		 Vertex transacao = graph.addVertex("class:Transacao");
		 transacao.setProperty("nomeTransacao", card.getNometransacao());
		 transacao.setProperty("dataTransacao", card.getDataTransacao());
		 transacao.setProperty("valorTransacao", String.valueOf(card.getValorTransacao()));
		 return transacao;
	}
	
	private Edge buildEdgePossuiPortadores(OrientGraph graph, Vertex unidadeGestora, Vertex portador) {
		Iterable<Edge> itOut = unidadeGestora.getEdges(Direction.OUT, "PossuiPortador");
		for(Edge e:itOut){
			String nomeUniGest = e.getVertex(Direction.OUT).getProperty("nomeUniGest");
			String nomePortador = e.getVertex(Direction.IN).getProperty("nomePortador");
			
			if(nomeUniGest.equals(unidadeGestora.getProperty("nomeUniGest")) && nomePortador.equals(portador.getProperty("nomePortador"))) {
				return null;
			}
		}
		return graph.addEdge(null, unidadeGestora, portador, "PossuiPortador");
	}
	
	private Vertex buildVertexPortador(OrientGraph graph, PaymentCard card) {
		Iterable<Vertex> vertice = graph.getVertices("Portador.nomePortador", card.getNomePortador());
		
		if(!vertice.iterator().hasNext()) {
			 Vertex portador = graph.addVertex("class:Portador");
			 portador.setProperty("nomePortador", card.getNomePortador());
			 portador.setProperty("cpfPortador", card.getCpfPortador());
			 return portador;
		} else {
			return vertice.iterator().next();
		}
	}
	
	private Vertex buildVertexOrgaoSuperior (OrientGraph graph, PaymentCard card) {
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
			
			if(nomeOrgSub.equals(orgaoSubordinado.getProperty("nomeOrgSub")) && nomeUniGest.equals(unidadeGestora.getProperty("nomeUniGest"))) {
				return null;
			}
		}
		return graph.addEdge(null, orgaoSubordinado, unidadeGestora, "PossuiUniGest");
	}
}
