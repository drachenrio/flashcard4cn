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

import java.util.Collections;
import java.util.Iterator;
import org.moonwave.util.PathUtil;
import org.moonwave.util.PropertyReader;

/**
 * ImageFlashCardEntity.java
 *
 * Created on Sept 20, 2009, 12:10 PM
 *
 * @author Jonathan Luo
 */
public class ImageFlashCardEntity extends TextFlashCardEntity {

    @Override
    public void loadFlashCardKeys() {
        flashCardFile = PathUtil.getLeadingPath() + "/conf/ImageFlashCards.txt";
        try {
            flashCardProperties = new PropertyReader().loadProperty("conf/ImageFlashCards.txt");
            createAndSortFlashCardNames();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Creates and sorts flash card names
     */
    @Override
    public void createAndSortFlashCardNames() {
        flashCardNames.clear();
        Iterator it;
        if (flashCardProperties.size() > 0) // used for the first time only
            it = flashCardProperties.keySet().iterator();
        else
            it = flashCardMap.keySet().iterator();        
        while (it.hasNext()) {
            String key = it.next().toString();
            flashCardNames.add(key);
            if (flashCardProperties.size() > 0) { // used for the first time only
                flashCardMap.put(key, flashCardProperties.getProperty(key));
            }
        }
        Collections.sort(flashCardNames);
        if (flashCardProperties.size() > 0) // clear
            flashCardProperties.clear();
    }
    
    public String getFlashcardFolder(String key) {
        System.out.println("key: " + key);
        System.out.println("getFlashcardFolder: " + flashCardMap.get(key));
        return flashCardMap.get(key);
    }

    public static void main(String[] argv) {
        ImageFlashCardEntity ii = new ImageFlashCardEntity();
        ii.loadFlashCardKeys();
        ii.createAndSortFlashCardNames();
        String str = ii.getFlashcardFolder("myNewCard");
        System.out.println(str);
    }
}
