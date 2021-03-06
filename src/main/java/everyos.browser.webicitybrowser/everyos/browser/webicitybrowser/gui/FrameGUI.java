package everyos.browser.webicitybrowser.gui;

import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicity.renderer.html.HTMLRenderer;
import everyos.browser.webicitybrowser.gui.renderer.HTMLRendererGUI;
import everyos.browser.webicitybrowser.ui.Frame;
import everyos.browser.webicitybrowser.ui.event.FrameMutationEventListener;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class FrameGUI {
	private Frame frame;
	private Component outerPane;
	private FrameMutationListener mutationListener;

	public FrameGUI(Frame frame) {
		this.frame = frame;
		this.outerPane = new BlockComponent(null);
	}
	
	public void start() {
		this.mutationListener = new FrameMutationListener();
		frame.addFrameMutationListener(mutationListener);
		
		mutationListener.onRendererCreated(frame.getCurrentRenderer());
	}
	
	public void cleanup() {
		frame.removeFrameMutationListener(mutationListener);
	}

	public Component getDisplayPane() {
		return outerPane;
	}

	private class FrameMutationListener implements FrameMutationEventListener {
		@Override
		public void onRendererCreated(Renderer r) {
			if (r==null) return;
			if (r.getClass() == HTMLRenderer.class) {
				//TODO: Store "cleanup" to runnable for later
				HTMLRendererGUI rendererGUI = new HTMLRendererGUI((HTMLRenderer) r);
				rendererGUI.start();
				outerPane.children(new Component[] {
					fullsize(rendererGUI.getDisplayPane())
				});
			}
		}
	}
	
	private Component fullsize(Component c) {
		return c.directive(SizeDirective.of(new Location(1, 0, 1, 0)));
	}
}
