package protocolsupport.zplatform;

public interface PlatformInjector {

	public void inject();

	public void onFirstTick();

	public void onDisable();

	public void onEnable();

}
