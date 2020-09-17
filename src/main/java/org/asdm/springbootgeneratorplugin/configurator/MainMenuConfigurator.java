package org.asdm.springbootgeneratorplugin.configurator;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ConfiguratorWithPriority;
import org.asdm.springbootgeneratorplugin.util.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainMenuConfigurator implements AMConfigurator {

	private final NMAction[] actions;

	public MainMenuConfigurator(final NMAction[] actions) {
		this.actions = actions;
	}

	@Override
	public void configure(final ActionsManager actionsManager) {
		final ActionsCategory toolActionsCategory = actionsManager.getCategory(Constants.TOOLS_CATEGORY);
		if(toolActionsCategory != null) {
			final List<NMAction> actions = new ArrayList<>(Arrays.asList(this.actions));
			actions.addAll(toolActionsCategory.getActions());
			toolActionsCategory.setActions(actions);
		}
	}
	
	@Override
	public int getPriority()
	{
		return ConfiguratorWithPriority.MEDIUM_PRIORITY;
	}
}