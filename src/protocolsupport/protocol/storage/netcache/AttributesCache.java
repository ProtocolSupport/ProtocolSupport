package protocolsupport.protocol.storage.netcache;

import protocolsupport.protocol.utils.i18n.I18NData;
import protocolsupport.protocol.utils.types.Environment;
import protocolsupport.utils.Utils;

public class AttributesCache {

	protected Environment dimension;

	public void setCurrentDimension(Environment dimension) {
		this.dimension = dimension;
	}

	public boolean hasSkyLightInCurrentDimension() {
		return dimension == Environment.OVERWORLD;
	}

	protected float maxHealth = 20.0F;

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	protected String locale = I18NData.DEFAULT_LOCALE;

	public void setLocale(String locale) {
		this.locale = locale.toLowerCase();
	}

	public String getLocale() {
		return locale;
	}

	@Override
	public String toString() {
		return Utils.toStringAllFields(this);
	}

}
