package everyos.browser.webicity.webribbon.ui.webui;

import everyos.browser.webicity.webribbon.core.component.WebComponent;
import everyos.browser.webicity.webribbon.core.ui.WebComponentUI;
import everyos.browser.webicity.webribbon.gui.UIContext;
import everyos.browser.webicity.webribbon.gui.shape.SizePosGroup;
import everyos.engine.ribbon.core.rendering.Renderer;
import everyos.engine.ribbon.renderer.guirenderer.graphics.Color;
import everyos.engine.ribbon.renderer.guirenderer.shape.Rectangle;

public class WebUIWebAnchorComponentUI extends WebUIWebComponentUI {
	public WebUIWebAnchorComponentUI(WebComponent component, WebComponentUI parent) {
		super(component, parent);
	}
	
	@Override
	public void render(Renderer r, SizePosGroup sizepos, UIContext context) {
		renderUI(r, sizepos, context); //TODO: Styling instead
	}
	
	@Override
	public void paintUI(Renderer r, Rectangle viewport) {
		r.setForeground(Color.BLUE);
		paintChildren(r, viewport);
	}
}
