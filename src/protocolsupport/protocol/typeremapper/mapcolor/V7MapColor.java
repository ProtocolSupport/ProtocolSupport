package protocolsupport.protocol.typeremapper.mapcolor;

public enum V7MapColor implements IMapColor {

	Color0(-1, -1, -1),
	Color1(-1, -1, -1),
	Color2(-1, -1, -1),
	Color3(-1, -1, -1),
	Color4(89, 125, 39),
	Color5(109, 153, 48),
	Color6(127, 178, 56),
	Color7(67, 94, 29),
	Color8(174, 164, 115),
	Color9(213, 201, 140),
	Color10(247, 233, 163),
	Color11(130, 123, 86),
	Color12(180, 0, 0),
	Color13(220, 0, 0),
	Color14(255, 0, 0),
	Color15(135, 0, 0),
	Color16(112, 112, 180),
	Color17(138, 138, 220),
	Color18(160, 160, 255),
	Color19(84, 84, 135),
	Color20(117, 117, 117),
	Color21(144, 144, 144),
	Color22(167, 167, 167),
	Color23(88, 88, 88),
	Color24(0, 87, 0),
	Color25(0, 106, 0),
	Color26(0, 124, 0),
	Color27(0, 65, 0),
	Color28(180, 180, 180),
	Color29(220, 220, 220),
	Color30(255, 255, 255),
	Color31(135, 135, 135),
	Color32(115, 118, 129),
	Color33(141, 144, 158),
	Color34(164, 168, 184),
	Color35(86, 88, 97),
	Color36(129, 74, 33),
	Color37(157, 91, 40),
	Color38(183, 106, 47),
	Color39(96, 56, 24),
	Color40(79, 79, 79),
	Color41(96, 96, 96),
	Color42(112, 112, 112),
	Color43(59, 59, 59),
	Color44(45, 45, 180),
	Color45(55, 55, 220),
	Color46(64, 64, 255),
	Color47(33, 33, 135),
	Color48(73, 58, 35),
	Color49(89, 71, 43),
	Color50(104, 83, 50),
	Color51(55, 43, 26),
	Color52(180, 177, 172),
	Color53(220, 217, 211),
	Color54(255, 252, 245),
	Color55(135, 133, 129),
	Color56(152, 89, 36),
	Color57(186, 109, 44),
	Color58(216, 127, 51),
	Color59(114, 67, 27),
	Color60(125, 53, 152),
	Color61(153, 65, 186),
	Color62(178, 76, 216),
	Color63(94, 40, 114),
	Color64(72, 108, 152),
	Color65(88, 132, 186),
	Color66(102, 153, 216),
	Color67(54, 81, 114),
	Color68(161, 161, 36),
	Color69(197, 197, 44),
	Color70(229, 229, 51),
	Color71(121, 121, 27),
	Color72(89, 144, 17),
	Color73(109, 176, 21),
	Color74(127, 204, 25),
	Color75(66, 108, 13),
	Color76(170, 89, 116),
	Color77(208, 109, 142),
	Color78(242, 127, 165),
	Color79(128, 67, 87),
	Color80(53, 53, 53),
	Color81(65, 65, 65),
	Color82(76, 76, 76),
	Color83(40, 40, 40),
	Color84(108, 108, 108),
	Color85(132, 132, 132),
	Color86(153, 153, 153),
	Color87(81, 81, 81),
	Color88(53, 89, 108),
	Color89(65, 109, 132),
	Color90(76, 127, 153),
	Color91(40, 67, 81),
	Color92(89, 44, 125),
	Color93(109, 54, 153),
	Color94(127, 63, 178),
	Color95(67, 33, 94),
	Color96(36, 53, 125),
	Color97(44, 65, 153),
	Color98(51, 76, 178),
	Color99(27, 40, 94),
	Color100(72, 53, 36),
	Color101(88, 65, 44),
	Color102(102, 76, 51),
	Color103(54, 40, 27),
	Color104(72, 89, 36),
	Color105(88, 109, 44),
	Color106(102, 127, 51),
	Color107(54, 67, 27),
	Color108(108, 36, 36),
	Color109(132, 44, 44),
	Color110(153, 51, 51),
	Color111(81, 27, 27),
	Color112(17, 17, 17),
	Color113(21, 21, 21),
	Color114(25, 25, 25),
	Color115(13, 13, 13),
	Color116(176, 168, 54),
	Color117(215, 205, 66),
	Color118(250, 238, 77),
	Color119(132, 126, 40),
	Color120(64, 154, 150),
	Color121(79, 188, 183),
	Color122(92, 219, 213),
	Color123(48, 115, 112),
	Color124(52, 90, 180),
	Color125(63, 110, 220),
	Color126(74, 128, 255),
	Color127(39, 67, 135),
	Color128(0, 153, 40),
	Color129(0, 187, 50),
	Color130(0, 217, 58),
	Color131(0, 114, 30),
	Color132(14, 14, 21),
	Color133(18, 17, 26),
	Color134(21, 20, 31),
	Color135(11, 10, 16),
	Color136(79, 1, 0),
	Color137(96, 1, 0),
	Color138(112, 2, 0),
	Color139(59, 1, 0);

	private final int r;
	private final int g;
	private final int b;

	V7MapColor(int r, int g, int b) {
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
