/*******************************************************************************
 * Copyright (c) 2008 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Ketan Padegaonkar - initial API and implementation
 *     Ketan Padegaonkar - http://swtbot.org/bugzilla/show_bug.cgi?id=126
 *     Ketan Patel (bug 260088)
 *******************************************************************************/
package org.eclipse.swtbot.eclipse.finder.matchers;

import static org.hamcrest.Matchers.equalTo;

import org.eclipse.swtbot.swt.finder.matchers.AbstractMatcher;
import org.eclipse.ui.IWorkbenchPartReference;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;


/**
 * @author Ralf Ebert www.ralfebert.de
 * @version $Id$
 * @since 2.0
 */
public class WithPartId<T extends IWorkbenchPartReference> extends AbstractMatcher<T> {

	private final Matcher<String> idMatcher;

	public WithPartId(Matcher<String> idMatcher) {
		this.idMatcher = idMatcher;
	}

	public boolean doMatch(Object item) {
		if (item instanceof IWorkbenchPartReference) {
			IWorkbenchPartReference part = (IWorkbenchPartReference) item;
			return idMatcher.matches(part.getId());
		}
		return false;
	}

	public void describeTo(Description description) {
		description.appendText("with id '").appendDescriptionOf(idMatcher).appendText("'");
	}

	/**
	 * Matches a workbench part (view/editor) with the specified id.
	 *
	 * @param id the id of the part.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends IWorkbenchPartReference> withPartId(String id) {
		return withPartId(equalTo(id));
	}

	/**
	 * Matches a workbench part (view/editor) with the specified id.
	 *
	 * @param idMatcher the part id matcher.
	 * @return a matcher.
	 * @since 2.0
	 */
	@Factory
	public static AbstractMatcher<? extends IWorkbenchPartReference> withPartId(Matcher<String> idMatcher) {
		return new WithPartId<IWorkbenchPartReference>(idMatcher);
	}
}
