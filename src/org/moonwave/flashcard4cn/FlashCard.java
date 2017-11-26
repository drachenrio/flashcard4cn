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

import java.util.List;
import java.util.Map;

/**
 * FlashCard.java
 *
 * Created on Sept 20, 2009, 12:27 PM
 *
 * @author Jonathan Luo
 */
public interface FlashCard {

    public void loadFlashCardKeys();

    /**
     * Creates and sorts flash card names
     */
    public void createAndSortFlashCardNames();

    /**
     * Checks whether a flashCardName exist
     *
     * @param flashCardName flash card name to check
     * @return true if flashCardName exists; false otherwise
     */
    public boolean isFlashCardNameExist(String flashCardName);


    /**
     * Removes specified flashCardName and its value
     *
     * @param flashCardName flash card name to remove
     */
    public void deleteFlashCard(String flashCardName);

    /**
     * Loads all flash cards from disk file
     */
    public void loadFlashCards();

    /**
     * Loads individual flash card for specified flash card name
     *
     * @param flashCardName flash card name
     */
    public void loadFlashCard(String flashCardName);


    /**
     * Gets flashcard map
     *
     * @return flashcard map
     */
    public Map<String, String> getFlashCardMap();
    
    /**
     * Gets flash card value for specified flash card name
     *
     * @param flashCardName flash card name
     * @return flash card value for specified flash card name
     */
    public String getFlashCardValue(String flashCardName);

    /**
     * Sets flash card value for specified flash card name
     *
     * @param flashCardName flash card name
     * @param value flash card value
     */
    public void setFlashCardValue(String flashCardName, String value);

    /**
     * Gets flash card original value for specified flash card name
     *
     * @param flashCardName flash card name
     */
    public String getFlashCardOriginalValue(String flashCardName);

    /**
     * Gets list of flash card names
     *
     * @return list of flash card names
     */
    public List<String> getFlashCardNames();

    /**
     * Gets current flashcard for display
     *
     * @return current flashcard for display
     */
    public String[] getFlashCard();

    /**
     * Gets current flashcard counts
     * @return current flashcard counts
     */
    public int getTestCount();

    /**
     * Gets the current FlashCard Name
     *
     * @return the current FlashCard Name
     */
    public String getCurrentFlashCardName();

    /**
     * Sets the current FlashCard Name
     *
     * @param currentFlashCardName the current FlashCard Name to set
     */
    public void setCurrentFlashCardName(String currentFlashCardName);

    /**
     * Returns true if specified flashcard has changed.
     *
     * @param flashCardName flash card name to check
     * @return true if specified flashcard has changed; false otherwise
     */
    public boolean hasChanged(String flashCardName);

    /**
     * Returns true if specified flashcard has big changes. Currently the big
     * changes is defined as the size difference is greater than 20
     *
     * @param flashCardName flash card name to check
     * @return Returns true if specified flashcard has big changes; false otherwise
     */
    public boolean hasBigChanged(String flashCardName);

    /**
     * Saves current working copy of flashcards to external file; Copy working
     * copy to stand-by copy.
     *
     * @return true on successful save; false otherwise
     */
    public boolean save();

    
    // ---------------------------------------------------------- may be removed

    /**
     * Gets supported Chinese font name list
     * @return supported Chinese font name list
     */
    public List<String> getFontNames();

    /**
     * Gets supported font names for Chinese language
     */
    public void getCHNFontNames();

    /**
     * Makes a copy of the current flashCard value
     *
     * @param flashCardName flash card name
     */
    public void copyFlashCardValue(String flashCardName);

    /**
     * Restores flash card value from saved copy
     *
     * @param flashCardName flash card name
     */
    public void restoreFlashCardValue(String flashCardName);
}
