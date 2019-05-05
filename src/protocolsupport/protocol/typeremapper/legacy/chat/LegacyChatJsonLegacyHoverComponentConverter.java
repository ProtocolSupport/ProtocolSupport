package protocolsupport.protocol.typeremapper.legacy.chat;

import java.io.IOException;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.api.chat.components.BaseComponent;
import protocolsupport.api.chat.modifiers.HoverAction;
import protocolsupport.protocol.typeremapper.entity.EntityRemappersRegistry;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemapper;
import protocolsupport.protocol.typeremapper.legacy.LegacyEntityId;
import protocolsupport.protocol.types.NetworkItemStack;
import protocolsupport.protocol.types.nbt.NBTCompound;
import protocolsupport.protocol.types.nbt.NBTShort;
import protocolsupport.protocol.types.nbt.NBTString;
import protocolsupport.protocol.types.nbt.NBTType;
import protocolsupport.protocol.types.nbt.mojangson.LegacyMojangsonSerializer;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonParser;
import protocolsupport.protocol.types.nbt.mojangson.MojangsonSerializer;
import protocolsupport.protocol.types.networkentity.NetworkEntityType;
import protocolsupport.protocol.utils.CommonNBT;
import protocolsupport.protocol.utils.ItemMaterialLookup;
import protocolsupport.protocol.utils.ItemStackWriteEventHelper;

public class LegacyChatJsonLegacyHoverComponentConverter extends LegacyChatJsonComponentConverter {

	@Override
	public BaseComponent convert(ProtocolVersion version, String locale, BaseComponent component) {
		HoverAction hover = component.getHoverAction();
		if (hover != null) {
			try {
				HoverAction.Type htype = hover.getType();
				NBTCompound compound = null;
				if (htype == HoverAction.Type.SHOW_ITEM) {
					compound = remapShowItem(version, locale, MojangsonParser.parse(hover.getValue()));
				} else if (htype == HoverAction.Type.SHOW_ENTITY) {
					compound = remapShowEntity(version, MojangsonParser.parse(hover.getValue()));
				}
				if (compound != null) {
					component.setHoverAction(new HoverAction(htype, version.isBefore(ProtocolVersion.MINECRAFT_1_12) ? LegacyMojangsonSerializer.serialize(compound) : MojangsonSerializer.serialize(compound)));
				}
			} catch (IOException e) {
			}
		}
		return component;
	}

	protected static NBTCompound remapShowItem(ProtocolVersion version, String locale, NBTCompound compound) {
		NetworkItemStack itemstack = CommonNBT.deserializeItemStackFromNBT(compound);
		ItemStackWriteEventHelper.callEvent(version, locale, itemstack);
		itemstack = ItemStackRemapper.remapToClient(version, locale, itemstack);
		compound = CommonNBT.serializeItemStackToNBT(itemstack);
		if (version.isBefore(ProtocolVersion.MINECRAFT_1_13)) {
			if (version.isBefore(ProtocolVersion.MINECRAFT_1_8)) {
				compound.setTag("id", new NBTShort((short) itemstack.getTypeId()));
			} else {
				compound.setTag("id", new NBTString(String.valueOf(itemstack.getTypeId())));
			}
			compound.setTag("Damage", new NBTShort((short) itemstack.getLegacyData()));
		} else {
			compound.setTag("id", new NBTString(ItemMaterialLookup.getByRuntimeId(itemstack.getTypeId()).getKey().toString()));
		}
		return compound;
	}

	protected static NBTCompound remapShowEntity(ProtocolVersion version, NBTCompound compound) {
		NBTString etypetag = compound.getTagOfType("type", NBTType.STRING);
		if (etypetag != null) {
			NetworkEntityType etype = NetworkEntityType.getByRegistrySTypeId(etypetag.getValue());
			if (etype != NetworkEntityType.NONE) {
				etype = EntityRemappersRegistry.REGISTRY.getTable(version).getRemap(etype).getLeft();
				compound.setTag("type", new NBTString(version.isBefore(ProtocolVersion.MINECRAFT_1_11) ? LegacyEntityId.getStringId(etype) : etype.getKey()));
			}
		}
		return compound;
	}

}
