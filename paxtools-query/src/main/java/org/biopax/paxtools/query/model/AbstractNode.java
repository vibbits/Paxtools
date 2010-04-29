package org.biopax.paxtools.query.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Ozgun Babur
 */
public abstract class AbstractNode implements Node
{
	protected Graph graph;

	protected Set<Node> upperEquivalent;
	protected Set<Node> lowerEquivalent;

	private Set<Edge> upstream;
	private Set<Edge> downstream;

	protected AbstractNode(Graph graph)
	{
		this.graph = graph;
		this.upstream = new HashSet<Edge>();
		this.downstream = new HashSet<Edge>();
	}

	public Graph getGraph()
	{
		return graph;
	}

	public Collection<Edge> getUpstream()
	{
		return upstream;
	}

	public Collection<Edge> getDownstream()
	{
		return downstream;
	}

	public Collection<Node> getUpperEquivalent()
	{
		return upperEquivalent;
	}

	public Collection<Node> getLowerEquivalent()
	{
		return lowerEquivalent;
	}

	/**
	 * Assumes the node has no need to be initialized.
	 */
	public void init()
	{
	}
}
