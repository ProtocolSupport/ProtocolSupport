package protocolsupport.protocol.typeremapper.particle;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.codec.ItemStackCodec;
import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.codec.VibrationPathCodec;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockDataRegistry.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.itemstack.ItemStackRemappingHelper;
import protocolsupport.protocol.types.Position;
import protocolsupport.protocol.types.particle.NetworkParticle;
import protocolsupport.protocol.types.particle.types.NetworkParticleBlock;
import protocolsupport.protocol.types.particle.types.NetworkParticleBlockMarker;
import protocolsupport.protocol.types.particle.types.NetworkParticleDust;
import protocolsupport.protocol.types.particle.types.NetworkParticleDustTransition;
import protocolsupport.protocol.types.particle.types.NetworkParticleFallingDust;
import protocolsupport.protocol.types.particle.types.NetworkParticleItem;
import protocolsupport.protocol.types.particle.types.NetworkParticleVibration;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;
import protocolsupport.protocol.utils.TypeSerializer;
import protocolsupport.protocol.utils.i18n.I18NData;

public class FlatteningNetworkParticleDataSerializer extends TypeSerializer<NetworkParticle> {

	public static final FlatteningNetworkParticleDataSerializer INSTANCE = new FlatteningNetworkParticleDataSerializer();

	protected FlatteningNetworkParticleDataSerializer() {
		register(NetworkParticle.class, (to, particle) -> {}, ProtocolVersionsHelper.UP_1_13);
		for (ProtocolVersion version : ProtocolVersionsHelper.UP_1_18) {
			FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
			register(NetworkParticleBlockMarker.class, (to, particle) -> VarNumberCodec.writeVarInt(to, flatteningBlockDataTable.getId(particle.getBlockData())), version);
		}
		register(NetworkParticleVibration.class, (to, particle) -> {
			//TODO: somehow pass the particle/entity location here?
			VibrationPathCodec.writeVibrationPathSource(to, new Position(0, 0, 0), particle.getPath());
		});
		register(NetworkParticleDustTransition.class, (to, particle) -> {
			to.writeFloat(particle.getRed());
			to.writeFloat(particle.getGreen());
			to.writeFloat(particle.getBlue());
			to.writeFloat(particle.getScale());
			to.writeFloat(particle.getTargetRed());
			to.writeFloat(particle.getTargetGreen());
			to.writeFloat(particle.getTargetBlue());
		}, ProtocolVersionsHelper.UP_1_17);
		register(NetworkParticleVibration.class, (to, particle) -> {
			VibrationPathCodec.writeVibrationPath(to, particle.getPath());
		}, ProtocolVersionsHelper.RANGE__1_17__1_18_2);
		register(NetworkParticleDust.class, (to, particle) -> {
			to.writeFloat(particle.getRed());
			to.writeFloat(particle.getGreen());
			to.writeFloat(particle.getBlue());
			to.writeFloat(particle.getScale());
		}, ProtocolVersionsHelper.UP_1_13);
		for (ProtocolVersion version : ProtocolVersionsHelper.UP_1_13) {
			FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockDataRegistry.INSTANCE.getTable(version);
			register(NetworkParticleBlock.class, (to, particle) -> VarNumberCodec.writeVarInt(to, flatteningBlockDataTable.getId(particle.getBlockData())), version);
			register(NetworkParticleFallingDust.class, (to, particle) -> VarNumberCodec.writeFixedSizeVarInt(to, flatteningBlockDataTable.getId(particle.getBlockData())), version);
			register(NetworkParticleItem.class, (to, particle) -> ItemStackCodec.writeItemStack(to, version, ItemStackRemappingHelper.toLegacyItemFormat(version, I18NData.DEFAULT_LOCALE, particle.getItemStack())), version);
		}
	}

}
