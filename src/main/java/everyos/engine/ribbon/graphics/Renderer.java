package everyos.engine.ribbon.graphics;

public interface Renderer {
	//This will be an abstraction for implementation-dependent rendering code
	public Renderer getSubcontext(int x, int y, int l, int h);
	public Renderer getBufferedSubcontext(int x, int y, int width, int height);
	public void drawFilledRect(int x, int y, int width, int height);
	public void drawEllipse(int x, int y, int l, int h);
	public void drawLine(int x, int y, int l, int h);
	public void drawText(int x, int y, String text);
	public void setColor(Color color);
	public void draw();
	public int getFontHeight();
	public int getFontPaddingHeight();
	public int charWidth(int ch);
	public void setFont(String name, FontStyle style, int size);
	
	//Implementations should also include a method of creating/retrieving a window or screen
}