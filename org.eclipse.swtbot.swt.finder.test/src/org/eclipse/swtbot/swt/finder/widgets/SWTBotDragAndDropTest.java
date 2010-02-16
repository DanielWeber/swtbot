/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Daniel Weber - initial API and implementation
 *******************************************************************************/
package org.eclipse.swtbot.swt.finder.widgets;

import static org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot.after;
import static org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot.before;
import static org.eclipse.swtbot.swt.finder.widgets.AbstractSWTBot.on;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Weber &lt;daniel.weber.dev [at] googlemail [dot] com&gt;
 */
public class SWTBotDragAndDropTest extends AbstractSWTTestCase {

	@Before
	public void beforeTest() {
		// Use the DND shell of the SWT Examples to run the tests.
		// It provides plenty of possibilities to perform drag and
		// drop actions :)
		bot.shell("DND shell").activate();
	}

	@Test
	public void dragsAndDropsFromTreeToTree() throws Exception {
		bot.comboBoxInGroup("Widget", 0).setSelection("Tree");
		bot.comboBoxInGroup("Widget", 1).setSelection("Tree");
		final SWTBotTree sourceTree = bot.tree(0);
		final SWTBotTree targetTree = bot.tree(1);
		final SWTBotTreeItem sourceItem = sourceTree.getTreeItem("Drag Source name 0");
		final SWTBotTreeItem targetItem = targetTree.getTreeItem("Drop Target name 0");

		assertDoesNotContain(targetTree, "Drag Source name 0");
		sourceItem.dragAndDrop(targetItem);
		assertDoesNotContain(sourceTree, "Drag Source name 0");
		assertTrue(targetItem.getNodes().contains("Drag Source name 0"));
	}

	@Test
	public void dragsAndDropsFromTextToText() throws Exception {
		bot.comboBoxInGroup("Widget", 0).setSelection("Text");
		bot.comboBoxInGroup("Widget", 1).setSelection("Text");
		final SWTBotText sourceText = bot.text("Drag Source Text");
		sourceText.selectAll();
		final SWTBotText targetText = bot.text("Drop Target Text");

		sourceText.dragAndDrop(targetText);

		assertEquals("", sourceText.getText());
		// The SWT DNDExample appends '\n' to text being dropped onto
		// a text field. This is *not* a feature of SWT.
		assertEquals("Drop Target TextDrag Source Text\n", targetText.getText());
	}

	@Test
	public void dropsOnCorrectLocation() throws Exception {
		bot.shell("DND Tree shell").activate();
		final SWTBotTree sourceTree = bot.tree(0);
		final SWTBotTreeItem parentItem = sourceTree.getTreeItem("item 0").expand().getNode("item 0 0");
		parentItem.expand();
		SWTBotTreeItem sourceItem = sourceTree.getTreeItem("item 0").getNode("item 0 0").getNode("item 0 0 0");
		final SWTBotTreeItem targetItem = sourceTree.getTreeItem("item 0").getNode("item 0 0").getNode("item 0 0 1");

		assertEquals("item 0 0 0", parentItem.getNodes().get(0));
		assertEquals("item 0 0 1", parentItem.getNodes().get(1));
		assertEquals("item 0 0 2", parentItem.getNodes().get(2));

		sourceItem.dragAndDrop(after(targetItem));

		// just reordered things
		assertEquals("item 0 0 1", parentItem.getNodes().get(0));
		assertEquals("item 0 0 0", parentItem.getNodes().get(1));
		assertEquals("item 0 0 2", parentItem.getNodes().get(2));

		// moving the source item disposes it, so we need to get the new one
		sourceItem = parentItem.getNode("item 0 0 0");

		sourceItem.dragAndDrop(before(targetItem));

		// just reordered things back to the original state
		assertEquals("item 0 0 0", parentItem.getNodes().get(0));
		assertEquals("item 0 0 1", parentItem.getNodes().get(1));
		assertEquals("item 0 0 2", parentItem.getNodes().get(2));

		sourceItem = parentItem.getNode("item 0 0 0");
		sourceItem.dragAndDrop(on(targetItem));

		// moved the source item a level deeper, creating a new child node
		assertEquals("item 0 0 0", targetItem.getNodes().get(0));

		assertEquals("item 0 0 1", parentItem.getNodes().get(0));
		assertEquals("item 0 0 2", parentItem.getNodes().get(1));
	}

	private void assertDoesNotContain(SWTBotTree targetTree, String itemText) {
		for (final SWTBotTreeItem item : targetTree.getAllItems()) {
			assertFalse("Widget '" + itemText + "' should not have been found", item.getText().equals(itemText));
			assertDoesNotContain(item, itemText);
		}
	}

	private void assertDoesNotContain(SWTBotTreeItem rootItem, String itemText) {
		rootItem.expand();
		for (final SWTBotTreeItem item : rootItem.getItems()) {
			assertFalse("Widget '" + itemText + "' should not have been found", item.getText().equals(itemText));
			assertDoesNotContain(item, itemText);
		}
		rootItem.collapse();
	}
}