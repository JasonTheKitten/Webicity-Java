package everyos.engine.ribbon.renderer.guirenderer.graphics;

public class Color {
	protected int color;

	public Color(int r, int g, int b) {
		color = (r<<16)+(g<<8)+b;
	}
	
	public static final Color BLACK = new Color(0, 0, 0);
	public static final Color WHITE = new Color(255, 255, 255);
	public static final Color DARK_GRAY = new Color(102, 102, 102);
	public static final Color LIGHT_GRAY = new Color(153, 153, 153);
	public static final Color RED = new Color(255, 0, 0);
	public static final Color BLUE = new Color(0, 0, 255);
	
	public static final Color DARK_GREY = DARK_GRAY;
	public static final Color LIGHT_GREY = LIGHT_GRAY;

	public int getRed() {
		return color>>16;
	}
	public int getGreen() {
		return color>>8&255;
	}
	public int getBlue() {
		return color&255;
	}
}
