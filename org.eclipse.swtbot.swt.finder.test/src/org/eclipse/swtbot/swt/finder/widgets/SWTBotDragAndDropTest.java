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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.eclipse.swtbot.swt.finder.finders.AbstractSWTTestCase;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotText;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTree;
import org.eclipse.swtbot.swt.finder.widgets.SWTBotTreeItem;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Daniel Weber &lt;daniel.weber [at] harman [dot] com&gt;
 * @version $Id$
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
		final SWTBotTreeItem sourceItem = sourceTree.getTreeItem("Drag Source name 0");
		final SWTBotTree targetTree = bot.tree(1);

		// dragged element does not exist in the target before DND operation:
		for (final SWTBotTreeItem item : targetTree.getAllItems()) {
			assertFalse("Widget 'Drag Source name 0' should not have been found", item.getText().equals("Drag Source name 0"));
		}

		sourceItem.dragAndDrop(targetTree);

		// dragged element does no longer exist in the source tree...
		for (final SWTBotTreeItem item : sourceTree.getAllItems()) {
			assertFalse("Widget 'Drag Source name 0' should not have been found", item.getText().equals("Drag Source name 0"));
		}
		// ...but in the target
		targetTree.getTreeItem("Drag Source name 0");
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
		// This is odd. At least under windows, drag'n'dropping text appends a newline to the end of the text box' text.
		// Is that done deliberately? Or by the SWTExample implementation?
		assertEquals("Drop Target TextDrag Source Text\n", targetText.getText());
	}
}
