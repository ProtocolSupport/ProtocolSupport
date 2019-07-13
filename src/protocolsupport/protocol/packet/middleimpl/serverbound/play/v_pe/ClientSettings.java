package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;

import protocolsupport.protocol.ConnectionImpl;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ClientSettings extends MiddleClientSettings {

	public ClientSettings(ConnectionImpl connection) {
		super(connection);
	}

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		locale = cache.getAttributesCache().getLocale();
		viewDist = VarNumberSerializer.readSVarInt(clientdata);;
		chatMode = ChatMode.NORMAL;
		chatColors = true;
		skinFlags = 0x7F;
		mainHand = MainHand.RIGHT;
	}

}
