package infinitesimalzeros.common.item;

public enum NeutronColor {
	
	
	Platinum(0xFF22FF),
	Iridium(0xCC339B);
	
	private final int color;
	
	private NeutronColor(int color) {
		this.color=color;
	}

	public int getColor() {
		return color;
	}


}
