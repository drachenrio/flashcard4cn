/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * flashcard4cn - FlashCard for Chinese Characters
 * Copyright (C) Jonathan Luo
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 *
 */

package org.moonwave.flashcard4cn;

import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;

/**
 * The main class of the application.
 * @author Jonathan Luo
 * 
 */
public class FlashCardApp extends SingleFrameApplication {

    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        show(new FlashCardView(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of FlashCardApp
     */
    public static FlashCardApp getApplication() {
        return Application.getInstance(FlashCardApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        for (int i = 0;  i < args.length; i++) {
            if (args[i].equalsIgnoreCase("verbose"))
                AppState.setVerbose(true);
        }
        launch(FlashCardApp.class, args);
    }
}
