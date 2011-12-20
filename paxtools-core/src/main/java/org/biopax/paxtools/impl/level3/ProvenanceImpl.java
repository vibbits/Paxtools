package org.biopax.paxtools.impl.level3;

import org.biopax.paxtools.model.level3.Provenance;
import org.biopax.paxtools.model.level3.Xref;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.Entity;
import javax.persistence.Transient;
import java.util.Set;

@Entity
 @Proxy(proxyClass= Provenance.class)
@Indexed//(index=BioPAXElementImpl.SEARCH_INDEX_NAME)
@org.hibernate.annotations.Entity(dynamicUpdate = true, dynamicInsert = true)
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class ProvenanceImpl extends NamedImpl implements Provenance
{
	public ProvenanceImpl() {
	}

	@Transient
    public Class<? extends Provenance> getModelInterface()
	{
		return Provenance.class;
	}

	@Override public String toString()
	{
		String s = "";

		for (String name : this.getName())
		{
			if (s.length() > 0) s += "; ";
			s += name;
		}
		Set<Xref> xref = this.getXref();
		if (!xref.isEmpty())
		{
			s += " (";
			for (Xref anXref : xref)
			{

				s += anXref ;
			}
			s += ")";
		}
		return s;
	}
}
