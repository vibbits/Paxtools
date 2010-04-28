/*
 * processProxy.java
 *
 * 2007.11.39 Takeshi Yoneki
 * INOH project - http://www.inoh.org
 */

package org.biopax.paxtools.proxy.level3;

import org.biopax.paxtools.model.level3.*;
import org.biopax.paxtools.model.level3.Process;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.Set;

/**
 * Proxy for process
 */
@Entity(name="l3process")
public abstract class ProcessProxy<T extends Process> extends EntityProxy<T> implements Process {
	@Transient
	public Set<Control> getControlledOf() {
		return object.getControlledOf();
	}

	@Transient
	public Set<Pathway> getPathwayComponentOf() {
		return object.getPathwayComponentOf();
	}

	@Transient
	public Set<PathwayStep> getStepInteractionsOf() {
		return object.getStepInteractionsOf();
	}

}
