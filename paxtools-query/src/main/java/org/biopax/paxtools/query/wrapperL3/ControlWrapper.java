package org.biopax.paxtools.query.wrapperL3;

import org.biopax.paxtools.model.BioPAXElement;
import org.biopax.paxtools.model.level3.*;
import org.biopax.paxtools.model.level3.Process;
import org.biopax.paxtools.query.model.AbstractNode;
import org.biopax.paxtools.query.model.Edge;
import org.biopax.paxtools.query.model.Graph;
import org.biopax.paxtools.query.model.Node;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Ozgun Babur
 */
public class ControlWrapper extends AbstractNode
{
	protected Control ctrl;
	protected int sign;
	protected boolean transcription;

	protected ControlWrapper(Control ctrl, Graph graph)
	{
		super(graph);
		this.ctrl = ctrl;
		this.transcription = false;
	}

	public boolean isBreadthNode()
	{
		return false;
	}

	public int getSign()
	{
		return sign;
	}

	public boolean isUbique()
	{
		return false;
	}

	public String getKey()
	{
		return ctrl.getRDFId();
	}

	@Override
	public void init()
	{
		ControlType type = ctrl.getControlType();

		if (type != null && type.toString().startsWith("I"))
		{
			sign = NEGATIVE;
		}
		else
		{
			sign = POSITIVE;
		}

	}

	private void bindUpstream(BioPAXElement element)
	{
		AbstractNode node = (AbstractNode) graph.getGraphObject(element);
		Edge edge = new EdgeL3(node, this, graph);
		node.getDownstreamNoInit().add(edge);
		this.getUpstreamNoInit().add(edge);
	}

	@Override
	public void initUpstream()
	{
		for (Controller controller : ctrl.getController())
		{
			if (controller instanceof Pathway) continue;

			PhysicalEntity pe = (PhysicalEntity) controller;
			bindUpstream(pe);
		}

		for (Control control : ctrl.getControlledOf())
		{
			bindUpstream(control);
		}
	}

	@Override
	public void initDownstream()
	{
		for (Process prc : ctrl.getControlled())
		{
			if (prc instanceof Conversion || prc instanceof Control)
			{
				AbstractNode node = (AbstractNode) graph.getGraphObject(prc);
				Edge edge = new EdgeL3(this, node, graph);
				node.getUpstreamNoInit().add(edge);
				getDownstreamNoInit().add(edge);
			}
		}
	}

	public Control getControl()
	{
		return ctrl;
	}

	@Override
	public Collection<Node> getUpperEquivalent()
	{
		return Collections.emptySet();
	}

	@Override
	public Collection<Node> getLowerEquivalent()
	{
		return Collections.emptySet();
	}

	public boolean isTranscription()
	{
		return transcription;
	}

	public void setTranscription(boolean transcription)
	{
		this.transcription = transcription;
	}
}
