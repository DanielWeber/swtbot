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
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertText;
import static org.eclipse.swtbot.swt.finder.SWTBotTestCase.assertTextContains;
import static org.junit.Assert.assertEquals;

import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swtbot.swt.finder.SWTBot;
import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Test;

/**
 * @author Ketan Padegaonkar &lt;KetanPadegaonkar [at] gmail [dot] com&gt;
 * @version $Id$
 */
public class SWTBotTableColumnTest extends AbstractSWTTestCase {

	private SWTBot		bot;
	private SWTBotTable	table;

	@Test
	public void findsTableColumn() throws Exception {
		SWTBotTableColumn header = table.header("Name");
		assertText("Name", header.widget);
		assertEquals(TableColumn.class, header.widget.getClass());
	}

	@Test
	public void clicksTableColumn() throws Exception {
		SWTBotTableColumn header = table.header("Name");
		header.click();

		Text text = bot.textInGroup("Listeners").widget;

		assertTextContains("Selection [13]: SelectionEvent{TableColumn {Name}", text);
		assertTextContains("MouseUp [4]: MouseEvent{Table {}", text);
		assertTextContains("data=null button=1 stateMask=524288 x=0 y=0 count=1}", text);
	}

	public void setUp() throws Exception {
		super.setUp();
		bot = new SWTBot();
		bot.tabItem("Table").activate();
		bot.radio("SWT.MULTI").click();
		bot.checkBox("Header Visible").select();
		bot.checkBox("Listen").select();
		bot.button("Clear").click();
		table = bot.tableInGroup("Table");
	}

	public void tearDown() throws Exception {
		bot.checkBox("Listen").deselect();
		super.tearDown();
	}
}
