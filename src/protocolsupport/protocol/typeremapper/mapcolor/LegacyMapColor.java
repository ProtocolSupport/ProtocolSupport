package protocolsupport.protocol.typeremapper.mapcolor;

public enum LegacyMapColor implements IMapColor {

	Color0(-1, -1, -1),
	Color1(-1, -1, -1),
	Color2(-1, -1, -1),
	Color3(-1, -1, -1),
	Color4(89, 125, 39),
	Color5(109, 153, 48),
	Color6(127, 178, 56),
	Color7(109, 153, 48),
	Color8(174, 164, 115),
	Color9(213, 201, 140),
	Color10(247, 233, 163),
	Color11(213, 201, 140),
	Color12(117, 117, 117),
	Color13(144, 144, 144),
	Color14(167, 167, 167),
	Color15(144, 144, 144),
	Color16(180, 0, 0),
	Color17(220, 0, 0),
	Color18(255, 0, 0),
	Color19(220, 0, 0),
	Color20(112, 112, 180),
	Color21(138, 138, 220),
	Color22(160, 160, 255),
	Color23(138, 138, 220),
	Color24(117, 117, 117),
	Color25(144, 144, 144),
	Color26(167, 167, 167),
	Color27(144, 144, 144),
	Color28(0, 87, 0),
	Color29(0, 106, 0),
	Color30(0, 124, 0),
	Color31(0, 106, 0),
	Color32(180, 180, 180),
	Color33(220, 220, 220),
	Color34(255, 255, 255),
	Color35(220, 220, 220),
	Color36(115, 118, 129),
	Color37(141, 144, 158),
	Color38(164, 168, 184),
	Color39(141, 144, 158),
	Color40(129, 74, 33),
	Color41(157, 91, 40),
	Color42(183, 106, 47),
	Color43(157, 91, 40),
	Color44(79, 79, 79),
	Color45(96, 96, 96),
	Color46(112, 112, 112),
	Color47(96, 96, 96),
	Color48(45, 45, 180),
	Color49(55, 55, 220),
	Color50(64, 64, 255),
	Color51(55, 55, 220),
	Color52(73, 58, 35),
	Color53(89, 71, 43),
	Color54(104, 83, 50),
	Color55(89, 71, 43);

	private final int r;
	private final int g;
	private final int b;

	LegacyMapColor(int r, int g, int b) {
		this.r = r;
		this.g = g;
		this.b = b;
	}

	@Override
	public int getRed() {
		return r;
	}

	@Override
	public int getGreen() {
		return g;
	}

	@Override
	public int getBlue() {
		return b;
	}

	@Override
	public int getId() {
		return ordinal();
	}

}
