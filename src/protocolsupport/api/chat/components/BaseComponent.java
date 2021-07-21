package protocolsupport.api.chat.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import protocolsupport.api.chat.modifiers.ClickAction;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.api.chat.modifiers.Modifier;
import protocolsupport.protocol.typeremapper.legacy.LegacyChat;
import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.utils.ReflectionUtils;

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
		Collections.addAll(this.siblings, siblings);
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
	 * @return text
	 */
	public String getValue() {
		return getValue(I18NData.DEFAULT_LOCALE);
	}

	/**
	 * Returns component converted to text<br>
	 * Only converts component itself to text, siblings, modifier, actions, insertion are not included<br>
	 * @param locale locale
	 * @return text
	 */
	public abstract String getValue(String locale);

	/**
	 * Deep clone component and all it's parts
	 * @return component full clone
	 */
	@Override
	public BaseComponent clone() {
		BaseComponent clone = cloneThis();
		clone.setModifier(getModifier().clone());
		HoverAction hover = getHoverAction();
		if (hover != null) {
			clone.setHoverAction(hover.clone());
		}
		ClickAction click = getClickAction();
		if (click != null) {
			clone.setClickAction(click.clone());
		}
		clone.setClickInsertion(getClickInsertion());
		clone.addSiblings(cloneFullList(getSiblings()));
		return clone;
	}

	/**
	 * Clone only the component itself, without siblings, modifier, actions, insertion
	 * @return component itself clone
	 */
	public abstract BaseComponent cloneThis();

	protected static List<BaseComponent> cloneFullList(List<BaseComponent> components) {
		List<BaseComponent> clone = new ArrayList<>(components.size());
		for (BaseComponent component : components) {
			clone.add(component.clone());
		}
		return clone;
	}

	/**
	 * Returns component converted to legacy text<br>
	 * Converts component, it's siblings, modifier, actions, insertion<br>
	 * Uses default locale for converting
	 * @return legacy text
	 */
	public String toLegacyText() {
		return toLegacyText(I18NData.DEFAULT_LOCALE);
	}

	/**
	 * Returns component converted to legacy text<br>
	 * Converts component, it's siblings, modifier, actions, insertion<br>
	 * Colors are converted to legacy color codes, so rgb colors are replaced with similar colors
	 * @param locale locale
	 * @return legacy text
	 */
	public String toLegacyText(String locale) {
		return LegacyChat.toText(this, locale);
	}

	@Override
	public String toString() {
		return ReflectionUtils.toStringAllFields(this);
	}


	/**
	 * Returns base component converted from string message <br>
	 * Supports legacy colors codes that use § character, and rgb colors (§x)
	 * @param message string message
	 * @return json message root component
	 */
	public static BaseComponent fromMessage(String message) {
		return LegacyChat.fromMessage(message);
	}

}
