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

import java.io.File;

/**
 *
 * @author Jonathan Luo
 */
public class PathUtil {

    static String leadingPath;

    static {
        String currentdir = System.getProperty("user.dir"); 
        File dir = new File(currentdir); 
        leadingPath = dir.getAbsolutePath();
        System.out.println("Current Working Directory : "+ dir);
        System.out.println("leadingPath : "+ leadingPath);
    }
    
    public static String getLeadingPath() {
        return leadingPath;
    }
}
