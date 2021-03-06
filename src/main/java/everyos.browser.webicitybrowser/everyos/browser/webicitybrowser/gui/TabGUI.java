package everyos.browser.webicitybrowser.gui;

import java.util.function.Supplier;

import everyos.browser.webicity.net.URL;
import everyos.browser.webicitybrowser.gui.behavior.ActionButtonBehavior;
import everyos.browser.webicitybrowser.gui.component.CircularText;
import everyos.browser.webicitybrowser.gui.component.URLBar;
import everyos.browser.webicitybrowser.ui.Tab;
import everyos.browser.webicitybrowser.ui.event.TabMutationEventListener;
import everyos.engine.ribbon.core.component.BlockComponent;
import everyos.engine.ribbon.core.component.Component;
import everyos.engine.ribbon.renderer.guirenderer.directive.BackgroundDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.PositionDirective;
import everyos.engine.ribbon.renderer.guirenderer.directive.SizeDirective;
import everyos.engine.ribbon.renderer.guirenderer.shape.Location;

public class TabGUI {
	private Tab tab;
	private CircularText tabButton; //TODO: This should be a TabComponent instead
	private Component tabPane;
	private boolean selected;
	private URLBar urlBar;
	private TabEventListener mutationListener;
	private FrameGUI frame;

	public TabGUI(Tab tab) {
		this.tab = tab;
	}
	
	public void start() {
		this.tabButton = new CircularText(null);
		
		createTabPane();
		
		mutationListener = new TabEventListener();
		mutationListener.onNavigate(tab.getURL());
		tab.addTabMutationListener(mutationListener);
	}

	public void cleanup() {
		
	}
	
	public Component getTabButton() {
		return this.tabButton;
	}
	
	public Component getTabPane() {
		return this.tabPane;
	}
	
	private void configureTabButton(CircularText tabButton) {
		tabButton.text(tab.getName());
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			tabButton.directive(BackgroundDirective.of(Styling.BACKGROUND_SECONDARY_ACTIVE));
		} else {
			tabButton.directive(BackgroundDirective.of(Styling.BACKGROUND_SECONDARY));
		}
	}
	
	public boolean isSelected() {
		return this.selected;
	};
	
	private void createTabPane() {
		this.tabPane = new BlockComponent(null);
		
		Component tabDecor = createTabDecorations();
		int decorHeight = Styling.BUTTON_WIDTH+(int)(Styling.ELEMENT_PADDING*1.5);
		tabDecor.directive(SizeDirective.of(new Location(1, 0, 0, decorHeight)));
		tabPane.addChild(tabDecor);
		
		this.frame = new FrameGUI(tab.getFrame());
		frame.start();
		Component frameComponent = frame.getDisplayPane();
		frameComponent.directive(SizeDirective.of(new Location(1, 0, 1, -decorHeight)));
		frameComponent.directive(PositionDirective.of(new Location(0, 0, 0, decorHeight)));
		tabPane.addChild(frameComponent);
	}

	private Component createTabDecorations() {
		int horizontalDrop = (int) (Styling.ELEMENT_PADDING*.5);
		
		Component tabDecor = new BlockComponent(null);
		tabDecor.directive(BackgroundDirective.of(Styling.BACKGROUND_PRIMARY));
		
		// Add the tab action buttons
		CircularText backButton = new CircularText(null);
		backButton.directive(PositionDirective.of(new Location(0, Styling.BORDER_PADDING, 0, horizontalDrop)));
		backButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		backButton.text("<");
		addButtonBehavior(backButton, ()->{});
		
		tabDecor.addChild(backButton);
		
		CircularText forwardButton = new CircularText(null);
		forwardButton.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING,
			0, horizontalDrop)));
		forwardButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		forwardButton.text(">");
		addButtonBehavior(forwardButton, ()->{});
		
		tabDecor.addChild(forwardButton);
		
		CircularText reloadButton = new CircularText(null);
		reloadButton.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*2,
			0, horizontalDrop)));
		reloadButton.directive(SizeDirective.of(new Location(0, Styling.BUTTON_WIDTH, 0, Styling.BUTTON_WIDTH)));
		reloadButton.text("O");
		addButtonBehavior(reloadButton, ()->tab.reload());
		
		tabDecor.addChild(reloadButton);
		
		this.urlBar = new URLBar(null);
		urlBar.directive(BackgroundDirective.of(Styling.BACKGROUND_SECONDARY));
		urlBar.directive(PositionDirective.of(new Location(
			0, Styling.BORDER_PADDING+(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*3,
			0, horizontalDrop)));
		urlBar.directive(SizeDirective.of(new Location(
			1, -Styling.BORDER_PADDING*2-(Styling.BUTTON_WIDTH+Styling.ELEMENT_PADDING)*3,
			0, Styling.BUTTON_WIDTH)));
		urlBar.text("about:blank");
		
		tabDecor.addChild(urlBar);
		
		return tabDecor;
	}
	
	private void addButtonBehavior(Component button, Runnable handler) {
		addButtonBehavior(button, handler, ()->false);
	}
	private void addButtonBehavior(Component button, Runnable handler, Supplier<Boolean> activeChecker) {
		ActionButtonBehavior.configure(button, handler, Styling.BACKGROUND_SECONDARY,
			Styling.BACKGROUND_SECONDARY_HOVER, Styling.BACKGROUND_SECONDARY_SELECTED, Styling.BACKGROUND_SECONDARY_ACTIVE,
			activeChecker);
	}
	
	private class TabEventListener implements TabMutationEventListener {
		@Override
		public void onNavigate(URL url) {
			configureTabButton(tabButton);
			urlBar.text(url.toString());
		}
		
		@Override
		public void onTitleChange(String name) {
			configureTabButton(tabButton);
		}
	}
}
