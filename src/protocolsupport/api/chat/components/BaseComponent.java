package protocolsupport.api.chat.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.Modifier;

public abstract class BaseComponent {

	private List<BaseComponent> siblings = new ArrayList<BaseComponent>();
	private Modifier modifier;
	private ClickAction clickAction;
	private HoverAction hoverAction;
	private String clickInsertion;

	public boolean isSimple() {
		return siblings.isEmpty() && getModifier().isEmpty() && clickAction == null && hoverAction == null && clickInsertion == null;
	}

	public List<BaseComponent> getSiblings() {
		return Collections.unmodifiableList(this.siblings);
	}

	public void clearSiblings() {
		this.siblings.clear();
	}

	public void addSibling(BaseComponent sibling) {
		this.siblings.add(sibling);
	}

	public void addSiblings(BaseComponent... siblings) {
		for (BaseComponent sibling : siblings) {
			this.siblings.add(sibling);
		}
	}

	public void addSiblings(Collection<BaseComponent> siblings) {
		this.siblings.addAll(siblings);
	}

	public Modifier getModifier() {
		if (this.modifier == null) {
			this.modifier = new Modifier();
		}
		return this.modifier;
	}

	public void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

	public ClickAction getClickAction() {
		return clickAction;
	}

	public void setClickAction(ClickAction clickAction) {
		this.clickAction = clickAction;
	}

	public HoverAction getHoverAction() {
		return hoverAction;
	}

	public void setHoverAction(HoverAction hoverAction) {
		this.hoverAction = hoverAction;
	}

	public String getClickInsertion() {
		return clickInsertion;
	}

	public void setClickInsertion(String clickInsertion) {
		this.clickInsertion = clickInsertion;
	}

	public abstract String getValue();

}
