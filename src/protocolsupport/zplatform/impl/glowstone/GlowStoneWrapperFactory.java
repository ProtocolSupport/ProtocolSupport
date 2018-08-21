//package protocolsupport.zplatform.impl.glowstone;
//
//import org.bukkit.inventory.ItemStack;
//
//import protocolsupport.protocol.packet.handler.AbstractHandshakeListener;
//import protocolsupport.protocol.packet.handler.AbstractLoginListener;
//import protocolsupport.protocol.packet.handler.AbstractStatusListener;
//import protocolsupport.zplatform.PlatformWrapperFactory;
//import protocolsupport.zplatform.impl.glowstone.itemstack.GlowStoneItemStackWrapper;
//import protocolsupport.zplatform.impl.glowstone.itemstack.GlowStoneNBTTagCompoundWrapper;
//import protocolsupport.zplatform.impl.glowstone.itemstack.GlowStoneNBTTagListWrapper;
//import protocolsupport.zplatform.impl.glowstone.network.handler.GlowStoneLoginListener;
//import protocolsupport.zplatform.itemstack.ItemStackWrapper;
//import protocolsupport.zplatform.itemstack.NBTTagCompoundWrapper;
//import protocolsupport.zplatform.itemstack.NBTTagListWrapper;
//import protocolsupport.zplatform.network.NetworkManagerWrapper;
//
//public class GlowStoneWrapperFactory implements PlatformWrapperFactory {
//
//	@Override
//	public NBTTagListWrapper createEmptyNBTList() {
//		return GlowStoneNBTTagListWrapper.create();
//	}
//
//	@Override
//	public NBTTagCompoundWrapper createNBTCompoundFromJson(String json) {
//		return GlowStoneNBTTagCompoundWrapper.fromJson(json);
//	}
//
//	@Override
//	public NBTTagCompoundWrapper createEmptyNBTCompound() {
//		return GlowStoneNBTTagCompoundWrapper.createEmpty();
//	}
//
//	@Override
//	public ItemStackWrapper createItemStack(int typeId) {
//		return GlowStoneItemStackWrapper.create(typeId);
//	}
//
//	@Override
//	public ItemStackWrapper createItemStack(ItemStack item) {
//		return GlowStoneItemStackWrapper.create(item);
//	}
//
//	@Override
//	public AbstractHandshakeListener createHandshakeListener(NetworkManagerWrapper networkmanager) {
//		return new AbstractHandshakeListener(networkmanager) {
//			@Override
//			protected AbstractStatusListener getStatusListener(NetworkManagerWrapper networkManager) {
//				return new AbstractStatusListener(networkmanager) {
//				};
//			}
//			@Override
//			protected AbstractLoginListener getLoginListener(NetworkManagerWrapper networkManager, String hostname) {
//				return new GlowStoneLoginListener(networkmanager, hostname);
//			}
//		};
//	}
//
//}
