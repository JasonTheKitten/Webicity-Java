package everyos.browser.webicitybrowser.ui;

import java.io.Closeable;

import everyos.browser.webicity.event.NavigateEvent;
import everyos.browser.webicity.net.URL;
import everyos.browser.webicity.renderer.Renderer;
import everyos.browser.webicitybrowser.WebicityInstance;
import everyos.browser.webicitybrowser.event.EventDispatcher;
import everyos.browser.webicitybrowser.ui.event.FrameMutationEventListener;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;

public class Tab implements Closeable {
	private EventDispatcher<TabMutationEventListener> mutationEventDispatcher = new EventDispatcher<>();
	//private WebicityFrame frame;
	private Frame frame;
	@SuppressWarnings("unused")
	private WebicityInstance instance;
	private FrameMutationListener frameMutationListener;

	public Tab(WebicityInstance instance) {
		this.instance = instance;
		this.frame = new Frame(instance);
	}
	
	public void start() {
		this.frameMutationListener = new FrameMutationListener();
		frame.addFrameMutationListener(frameMutationListener);
	};

	@Override
	public void close() {
		frame.removeFrameMutationListener(frameMutationListener);
	}

	public String getName() {
		return frame.getName();
	}

	public URL getURL() {
		return frame.getURL();
	}
	
	public void setURL(URL url) {
		frame.setURL(url);
	}
	
	public void reload() {
		frame.reload();
	}
	
	public Frame getFrame() {
		return frame;
	}

	public void addTabMutationListener(TabMutationEventListener mutationListener) {
		mutationEventDispatcher.addListener(mutationListener);
	}
	
	public void removeTabMutationListener(TabMutationEventListener mutationListener) {
		mutationEventDispatcher.removeListener(mutationListener);
	}
	
	private class FrameMutationListener implements FrameMutationEventListener {
		@Override
		public void onNavigate(NavigateEvent event) {
			mutationEventDispatcher.fire(l->l.onNavigate(event.getURL()));
		}
		
		@Override
		public void onRendererCreated(Renderer r) {
			r.addReadyHook(()->
				mutationEventDispatcher.fire(l->l.onTitleChange(frame.getName())));
		}
	}
}
