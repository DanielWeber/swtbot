/*******************************************************************************
 * Copyright (c) 2008-2009 Ketan Padegaonkar and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Daniel Weber - initial API and implementation
 *******************************************************************************/

package org.eclipse.swtbot.eclipse.finder.widgets;

import org.eclipse.swtbot.eclipse.finder.SWTWorkbenchBot;
import org.eclipse.swtbot.swt.finder.exceptions.WidgetNotFoundException;
import org.eclipse.swtbot.swt.finder.finders.UIThreadRunnable;
import org.eclipse.swtbot.swt.finder.results.Result;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.IFormPage;

/**
 * @author Daniel Weber
 */
public class SWTBotFormEditor extends SWTBotEditor
{

   private FormEditor editor;
   
   /**
    * @param editorReference
    * @param bot
    * @throws WidgetNotFoundException
    */
   public SWTBotFormEditor(final IEditorReference editorReference,
         final SWTWorkbenchBot bot)
         throws WidgetNotFoundException
   {
      super(editorReference, bot);
      editor = (FormEditor)editorReference.getPart(false);
   }
   
   /**
    * @param pageId
    */
   public void setActivePage(final String pageId)
   {
      IFormPage pageActivated = UIThreadRunnable.syncExec(new Result<IFormPage>()
      {
         public IFormPage run()
         {
            return editor.setActivePage(pageId);
         }
      });
      if(null == pageActivated)
      {
         throw new WidgetNotFoundException("Page with id " + pageId
               + " not found in editor " + description.toString() + ".");
      }
   }
   
   /**
    * @return the editor reference for this editor.
    */
   public IEditorReference getEditorReference()
   {
      return partReference;
   }
}
