/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.widgets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.eclipse.swtbot.eclipse.finder.SWTEclipseBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotToolbarButton;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Test;
/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotViewTest  {

	private SWTEclipseBot bot = new SWTEclipseBot();

	@Test
	public void findsView() throws Exception {
		bot.view("Welcome");
	}

	@Test
	public void getsViewTitle() throws Exception {
		assertEquals("Welcome", bot.view("Welcome").getTitle());
	}

	@Test
	public void notFindingViewThrowsException() throws Exception {
		try {
			bot.view("Non existent view");
			fail("Expecting WidgetNotFoundException");
		} catch (WidgetNotFoundException expected) {
			// pass
		}
	}

	@Test
	public void closesAView() throws Exception {
		SWTEclipseBot bot = new SWTEclipseBot();
		SWTBotView view = bot.view("Welcome");
		view.close();

		try {
			bot.view("Welcome");
			fail("Expecting WidgetNotFoundException");
		} catch (WidgetNotFoundException expected) {
			// pass
		}
	}

	@Test
	public void openView() throws Exception {
		openSWTBotTestView();
	}

	private void openSWTBotTestView() throws Exception {
		SWTEclipseBot bot = new SWTEclipseBot();

		bot.sleep(1000);
		bot.menu("Window").menu("Show View").menu("Other...").click();
		bot.shell("Show View").activate();
		SWTBotTree tree = bot.tree();
		SWTBotTreeItem expandNode = tree.expandNode("SWTBot Test Category");
		expandNode.select("SWTBot Test View").click();
		bot.button("OK").click();

		bot.view("SWTBot Test View").show();
	}

	@Test
	public void menu() throws Exception {
		openSWTBotTestView();

		SWTBotView view = bot.view("SWTBot Test View");

		// Runs an action that is an iAction and doesn't contain a contribution id
		view.menu("IAction Type Command").click();
		bot.button("OK").click();

		// Runs an action that has a contribution ID instead of the action.
		SWTBotViewMenu cICMenu = view.menu("Contribution Item Command");
		cICMenu.click();
		bot.button("OK").click();
		// assertTrue(cICMenu.isChecked());
	}

	@Test
	public void getToolbarButtons() throws Exception {
		SWTEclipseBot bot = new SWTEclipseBot();
		SWTBotView view = bot.view("SWTBot Test View");

		List l = view.getToolbarButtons();
		assertNotNull(l);
		assertEquals(1, l.size());
	}

	@Test
	public void toolbarButton() throws Exception {
		SWTEclipseBot bot = new SWTEclipseBot();
		SWTBotView view = bot.view("SWTBot Test View");

		SWTBotToolbarButton button = view.toolbarButton("This represents an IAction command.");
		assertNotNull(button);

		button.click();
		bot.button("OK").click();
	}

	@Test
	public void toolbarButtonNotFound() throws Exception {
		SWTEclipseBot bot = new SWTEclipseBot();
		SWTBotView view = bot.view("SWTBot Test View");

		try {
			view.toolbarButton("Tooltip can not exist");
			fail("This should throw an exception of widget not being found");
		} catch (WidgetNotFoundException e) {
			// This is expected.
		}
	}
}
