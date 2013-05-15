package org.biopax.paxtools.impl.level3;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.biopax.paxtools.model.level3.Entity;
import org.biopax.paxtools.model.level3.Interaction;
import org.biopax.paxtools.model.level3.InteractionVocabulary;
import org.biopax.paxtools.util.BiopaxSafeSet;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Proxy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate; 
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Indexed;

import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */

@javax.persistence.Entity
@Proxy(proxyClass= Interaction.class)
@Indexed
@Boost(1.5f)
@DynamicUpdate @DynamicInsert
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class InteractionImpl extends ProcessImpl implements Interaction
{
// ------------------------------ FIELDS ------------------------------

	Set<Entity> participant;
	private Set<InteractionVocabulary> interactionType;
    private final Log log = LogFactory.getLog(InteractionImpl.class);

// --------------------------- CONSTRUCTORS ---------------------------

	public InteractionImpl()
	{
		this.interactionType = new BiopaxSafeSet<InteractionVocabulary>();
		this.participant = new BiopaxSafeSet<Entity>();
	}

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface BioPAXElement ---------------------



	@Transient
	public Class<? extends Interaction> getModelInterface()
	{
		return Interaction.class;
	}

// --------------------- Interface Interaction ---------------------

// --------------------- ACCESORS and MUTATORS---------------------

    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@ManyToMany(targetEntity = InteractionVocabularyImpl.class)
	@JoinTable(name="interactionType")
	public Set<InteractionVocabulary> getInteractionType()
	{
	   return interactionType;
	}

	public void addInteractionType(
		InteractionVocabulary newinteractionType)
	{
	   if(newinteractionType != null)
			this.interactionType.add(newinteractionType);
	}

	public void removeInteractionType(
		InteractionVocabulary oldinteractionType)
	{
		if(oldinteractionType != null)
			this.interactionType.remove(oldinteractionType);
	}

	public void setInteractionType(
		Set<InteractionVocabulary> interactionType)
	{
	   this.interactionType = interactionType;
	}


	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	@ManyToMany(targetEntity = EntityImpl.class)
	@JoinTable(name="participant")
	public Set<Entity> getParticipant()
	{
		return participant;
	}

	protected void setParticipant(Set<Entity> participant)
	{
        this.participant = participant;
    }

	public void addParticipant(Entity aParticipant)
	{
		if (aParticipant != null) {
			if (aParticipant != null) {
				this.participant.add(aParticipant);
				aParticipant.getParticipantOf().add(this);
			} else {
				if (log.isWarnEnabled())
					log.warn("Null object passed to addParticipant @"
							+ this.getRDFId());
			}
		}
    }

	public void removeParticipant(Entity aParticipant)
	{
		if (aParticipant != null) {
			this.participant.remove(aParticipant);
			aParticipant.getParticipantOf().remove(this);
		}
	}
    
}
