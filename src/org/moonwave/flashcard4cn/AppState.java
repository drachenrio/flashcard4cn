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

/**
 * Application state manager.
 *
 * @author Jonathan Luo
 */
public class AppState {
    private static boolean verbose = false;
    private static String defaultFontName;
    
    /** Creates a new instance of SystemState */
    public AppState() {
    }
    
    /**
     * Returns verbose mode.
     */
    public static boolean isVerbose() {
        return verbose;
    }
    
    /**
     * Sets verbose flag.
     *
     * @param b the flag value.
     */
    public static void setVerbose(boolean b) {
        verbose = b;
    }

    public static String getDefaultFontName() {
        return defaultFontName;
    }

    public static void setDefaultFontName(String defaultFontName) {
        AppState.defaultFontName = defaultFontName;
    }
}
