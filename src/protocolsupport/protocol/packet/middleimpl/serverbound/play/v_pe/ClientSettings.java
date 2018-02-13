package protocolsupport.protocol.packet.middleimpl.serverbound.play.v_pe;

import org.bukkit.Bukkit;
import org.bukkit.inventory.MainHand;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.serverbound.play.MiddleClientSettings;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public class ClientSettings extends MiddleClientSettings {

	@Override
	public void readFromClientData(ByteBuf clientdata) {
		VarNumberSerializer.readSVarInt(clientdata); //pocket view distance
		locale = cache.getLocale();
		viewDist = Bukkit.getViewDistance();
		chatMode = ChatMode.NORMAL;
		chatColors = true;
		skinFlags = 0x7F;
		mainHand = MainHand.RIGHT;
	}

}
