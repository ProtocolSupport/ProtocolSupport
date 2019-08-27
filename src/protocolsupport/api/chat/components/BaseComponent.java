package protocolsupport.api.chat.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.protocol.typeremapper.legacy.chat.LegacyChat;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.Utils;

public abstract class BaseComponent {

	private final List<BaseComponent> siblings = new ArrayList<>();
	private Modifier modifier;
	private ClickAction clickAction;
	private HoverAction hoverAction;
	private String clickInsertion;

	/**
	 * Returns true if this component consists of a single chat component with no modifiers
	 * @return true if this component consists of a single chat component with no modifiers
	 */
	public boolean isSimple() {
		return siblings.isEmpty() && getModifier().isEmpty() && (clickAction == null) && (hoverAction == null) && (clickInsertion == null);
	}

	/**
	 * Returns chat component siblings list <br>
	 * The returned list a an unmodifiable wrapper of the internal list
	 * @return chat component siblings
	 */
	public List<BaseComponent> getSiblings() {
		return Collections.unmodifiableList(this.siblings);
	}

	/**
	 * Clears siblings list
	 */
	public void clearSiblings() {
		this.siblings.clear();
	}

	/**
	 * Adds sibling to the end of the siblings list
	 * @param sibling chat component sibling
	 */
	public void addSibling(BaseComponent sibling) {
		this.siblings.add(sibling);
	}

	/**
	 * Adds sibling to the end of the siblings list
	 * @param sibling chat component sibling
	 * @return this
	 */
	public BaseComponent withSibling(BaseComponent sibling) {
		addSibling(sibling);
		return this;
	}

	/**
	 * Adds siblings to the end of the siblings list
	 * @param siblings chat component siblings
	 */
	public void addSiblings(BaseComponent... siblings) {
		for (BaseComponent sibling : siblings) {
			this.siblings.add(sibling);
		}
	}

	/**
	 * Adds siblings to the end of the siblings list
	 * @param siblings chat component siblings
	 * @return this
	 */
	public BaseComponent withSiblings(BaseComponent... siblings) {
		addSiblings(siblings);
		return this;
	}

	/**
	 * Adds siblings to the end of the siblings list
	 * @param siblings chat component siblings
	 */
	public void addSiblings(Collection<BaseComponent> siblings) {
		this.siblings.addAll(siblings);
	}

	/**
	 * Adds siblings to the end of the siblings list
	 * @param siblings chat component siblings
	 * @return this
	 */
	public BaseComponent withSiblings(Collection<BaseComponent> siblings) {
		addSiblings(siblings);
		return this;
	}


	/**
	 * Returns current modifier
	 * @return current modifier
	 */
	public Modifier getModifier() {
		if (this.modifier == null) {
			this.modifier = new Modifier();
		}
		return this.modifier;
	}

	/**
	 * Sets current modifier<br>
	 * Passing null removes the modifier
	 * @param modifier modifier
	 */
	public void setModifier(Modifier modifier) {
		this.modifier = modifier;
	}

	/**
	 * Sets current modifier<br>
	 * Passing null removes the modifier
	 * @param modifier modifier
	 * @return this
	 */
	public BaseComponent withModifier(Modifier modifier) {
		setModifier(modifier);
		return this;
	}

	/**
	 * Returns current click action or null if no action present
	 * @return current click action or null
	 */
	public ClickAction getClickAction() {
		return clickAction;
	}

	/**
	 * Sets current click action<br>
	 * Passing null removes click action
	 * @param clickAction click action
	 */
	public void setClickAction(ClickAction clickAction) {
		this.clickAction = clickAction;
	}

	/**
	 * Sets current click action<br>
	 * Passing null removes click action
	 * @param clickAction click action
	 * @return this
	 */
	public BaseComponent withClickAction(ClickAction clickAction) {
		setClickAction(clickAction);
		return this;
	}

	/**
	 * Returns current hover action or null if no action present
	 * @return current hover action or null
	 */
	public HoverAction getHoverAction() {
		return hoverAction;
	}

	/**
	 * Sets current hover action<br>
	 * Passing null removes the hover action
	 * @param hoverAction hover action
	 */
	public void setHoverAction(HoverAction hoverAction) {
		this.hoverAction = hoverAction;
	}

	/**
	 * Sets current hover action<br>
	 * Passing null removes the hover action
	 * @param hoverAction hover action
	 * @return this
	 */
	public BaseComponent withHoverAction(HoverAction hoverAction) {
		setHoverAction(hoverAction);
		return this;
	}

	/**
	 * Returns current click insertion text
	 * @return current click insertion text
	 */
	public String getClickInsertion() {
		return clickInsertion;
	}

	/**
	 * Sets current click insertion<br>
	 * Passing null removes the click insertion
	 * @param clickInsertion click insertion
	 */
	public void setClickInsertion(String clickInsertion) {
		this.clickInsertion = clickInsertion;
	}

	/**
	 * Sets current click insertion<br>
	 * Passing null removes the click insertion
	 * @param clickInsertion click insertion
	 * @return this
	 */
	public BaseComponent withClickInsertion(String clickInsertion) {
		setClickInsertion(clickInsertion);
		return this;
	}

	/**
	 * Returns component converted to text<br>
	 * Only converts component itself to text, siblings, modifiers, actions, insertion are not included<br>
	 * Uses default locale for converting
	 * @return component converted to text
	 */
	public String getValue() {
		return getValue(I18NData.DEFAULT_LOCALE);
	}

	/**
	 * Returns component converted to text<br>
	 * Only converts component itself to text, siblings, modifier, actions, insertion are not included<br>
	 * @param locale locale
	 * @return component converted to text
	 */
	public abstract String getValue(String locale);

	/**
	 * Returns component converted to legacy text<br>
	 * Converts component, it's siblings, modifier, actions, insertion<br>
	 * Uses default locale for converting
	 * @return component converted to legacy text
	 */
	public String toLegacyText() {
		return toLegacyText(I18NData.DEFAULT_LOCALE);
	}

	/**
	 * Returns component converted to legacy text<br>
	 * Converts component, it's siblings, modifier, actions, insertion<br>
	 * @param locale locale
	 * @return component converted to legacy text
	 */
	public String toLegacyText(String locale) {
		return LegacyChat.toText(this, locale);
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
