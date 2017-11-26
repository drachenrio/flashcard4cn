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
package org.moonwave.util;

/**
 *
 * @author Jonathan Luo
 */
public class PropertyReader {

    /**
     * Loads properties from the classpath
     * 
     * @param resourceName
     * @throws java.lang.Exception
     */
    public void load(String resourceName) throws Exception{
        java.util.Properties props = new java.util.Properties();
        java.net.URL url = ClassLoader.getSystemResource(resourceName); //"myprops.props"
        props.load(url.openStream());
        System.out.println(props);
    }

    /**
     * Loads properties from startup directory
     * @param resourceName relative path of resource name
     * @throws java.lang.Exception
     * Example: Properties properties = new PropertyReader().loadProperty("conf/FlashCards.txt");
     */
    public java.util.Properties loadProperty(String resourceName) throws Exception {
        java.util.Properties props = new java.util.Properties();
        //String path = getClass().getProtectionDomain().getCodeSource().
        //        getLocation().toString().substring(5); // "/home/jonathan/project/netbeans/FlashCard/build/classes/"
        //String path = getClass().getProtectionDomain().getCodeSource().
        //        getLocation().toString(); // "file:/home/jonathan/project/netbeans/FlashCard/build/classes/"        
        java.io.FileInputStream fis = new java.io.FileInputStream
            (new java.io.File( PathUtil.getLeadingPath() + "/" + resourceName)); // path + "/myprops.props"
        props.load(fis);
        fis.close();
        return props;
    }
}
