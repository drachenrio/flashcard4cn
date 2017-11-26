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

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.lang.StringUtils;
import org.moonwave.util.LineReader;
import org.moonwave.util.PathUtil;
import org.moonwave.util.PropertyReader;

/**
 * TextFlashCardEntity.java
 *
 * Created on March 19, 2009, 10:03 PM
 *
 * @author Jonathan Luo
 */
public class TextFlashCardEntity implements FlashCard {

    protected String flashCardFile = PathUtil.getLeadingPath() + "/conf/FlashCards.txt";

    // Text  Flashcard - key: flashcard name, value: flashcard characters
    // Image Flashcard - key: flashcard name, value: flashcard folder
    protected Map<String, String> flashCardMap = new HashMap<String, String>(); // current working copy of flashcard

    protected String flashCardOrigValue; // saves a copy of the current flashcard value

    protected Properties flashCardProperties;
    protected List<String> flashCardNames = new ArrayList<String>();
    protected List<String> fontNames = new ArrayList<String>();
    protected String currentFlashCardName;

    protected int startIdx;
    protected int endIdx;
    protected int testCount;
    protected String[] flashCard; // current flash card characters array

    public void loadFlashCardKeys() {
        try {
            flashCardProperties = new PropertyReader().loadProperty("conf/FlashCards.txt");
            createAndSortFlashCardNames();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    /**
     * Creates and sorts flash card names
     */
    public void createAndSortFlashCardNames() {
        flashCardNames.clear();
        Iterator it;
        if (flashCardProperties.size() > 0) // used for first time only
            it = flashCardProperties.keySet().iterator();
        else
            it = flashCardMap.keySet().iterator();
        while (it.hasNext()) {
            flashCardNames.add(it.next().toString());
        }
        Collections.sort(flashCardNames);
        if (flashCardProperties.size() > 0) // clear
            flashCardProperties.clear();
    }

    /**
     * Checks whether a flashCardName exist
     *
     * @param flashCardName flash card name to check
     * @return true if flashCardName exists; false otherwise
     */
    public boolean isFlashCardNameExist(String flashCardName) {
        return flashCardNames.contains(flashCardName);
    }

    /**
     * Removes specified flashCardName and its value
     *
     * @param flashCardName flash card name to remove
     */
    public void deleteFlashCard(String flashCardName) {
        flashCardMap.remove(flashCardName);
        flashCardOrigValue = null;
        createAndSortFlashCardNames();
    }

    /**
     * Loads all flash cards from disk file
     */
    public void loadFlashCards() {
        LineReader lr = null;
        try {
            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File(flashCardFile)), "UTF8");
            lr = new LineReader(isr);
            String[] line;
            String key, value;
            String nextLine;
            while (true) {
                nextLine = lr.nextLine();
                if ((nextLine == null) || (nextLine.length() == 0))
                    break;
                line = nextLine.split("=");
                key = line[0];
                if (line.length == 2)
                    value = line[1];
                else
                    value = "";
                if (value.indexOf("\\\\") >= 0) {
                    // replce all "\" with "\\" under Windows
                    value = StringUtils.replace(value, "\\\\", "\\");
                }
                flashCardMap.put(key, value);
                if (AppState.isVerbose())
                    System.out.println(nextLine);
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (lr != null) {
                try {
                    lr.close();
                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Loads individual flash card for specified flash card name
     *
     * @param flashCardName flash card name
     */
    public void loadFlashCard(String flashCardName) {
        String testSet = flashCardMap.get(flashCardName);
        if (testSet != null)
            flashCard = StringUtils.split(testSet, ", ， "); // \uFF0C; //OK for both english and Chinese ","s and " "s

        if ((flashCard == null) || (flashCard.length == 0)) {
            flashCard = new String[1];
            flashCard[0] = "";
        }
        this.startIdx = 0;
        this.endIdx = flashCard.length - 1;
        this.testCount = flashCard.length;
    }


    /**
     * Gets flashcard map
     *
     * @return flashcard map
     */
    public Map<String, String> getFlashCardMap() {
        return flashCardMap;
    }
    
    /**
     * Gets flash card value for specified flash card name
     *
     * @param flashCardName flash card name
     * @return flash card value for specified flash card name
     */
    public String getFlashCardValue(String flashCardName) {
        return flashCardMap.get(flashCardName);
    }

    /**
     * Sets flash card value for specified flash card name
     *
     * @param flashCardName flash card name
     * @param value flash card value
     */
    public void setFlashCardValue(String flashCardName, String value) {
        flashCardMap.put(flashCardName, value);
    }

    /**
     * Gets flash card original value for specified flash card name
     *
     * @param flashCardName flash card name
     */
    public String getFlashCardOriginalValue(String flashCardName) {
        return flashCardOrigValue;
    }

    /**
     * Gets list of flash card names
     *
     * @return list of flash card names
     */
    public List<String> getFlashCardNames() {
        return flashCardNames;
    }

    /**
     * Gets current flashcard for display
     *
     * @return current flashcard for display
     */
    public String[] getFlashCard() {
        return flashCard;
    }

    /**
     * Gets current flashcard counts
     * @return current flashcard counts
     */
    public int getTestCount() {
        return testCount;
    }

    /**
     * Gets supported Chinese font name list
     * @return supported Chinese font name list
     */
    public List<String> getFontNames() {
        return fontNames;
    }

    /**
     * Gets supported font names for Chinese language
     */
    public void getCHNFontNames() {
        Font[] allfonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        String chinesesample = "\u4e2d\u56fd\u6c49\u5b57"; // \u4e00";
        for (int j = 0; j < allfonts.length; j++) {
            if (allfonts[j].canDisplayUpTo(chinesesample) == -1)
                fontNames.add(allfonts[j].getFontName());
        }
        AppState.setDefaultFontName(fontNames.get(0));
    }

    /**
     * Makes a copy of the current flashCard id value
     *
     * @param flashCardName flash card name
     */
    public void copyFlashCardValue(String flashCardName) {
        flashCardOrigValue = getFlashCardValue(flashCardName);
    }

    /**
     * Restores flash card value from saved copy
     *
     * @param flashCardName flash card name
     */
    public void restoreFlashCardValue(String flashCardName) {
        flashCardMap.put(flashCardName, flashCardOrigValue);
    }

    /**
     * Gets the current FlashCard Name
     *
     * @return the current FlashCard Name
     */
    public String getCurrentFlashCardName() {
        return currentFlashCardName;
    }

    /**
     * Sets the current FlashCard Name
     *
     * @param currentFlashCardName the current FlashCard Name to set
     */
    public void setCurrentFlashCardName(String currentFlashCardName) {
        this.currentFlashCardName = currentFlashCardName;
    }

    /**
     * Returns true if specified flashcard has changed.
     *
     * @param flashCardName flash card name to check
     * @return true if specified flashcard has changed; false otherwise
     */
    public boolean hasChanged(String flashCardName) {
        boolean bRet = false;
        String val1 = flashCardMap.get(flashCardName);
        System.out.println("flashCardName: " + flashCardName);
        System.out.println("    ==>     path: " + val1);
        System.out.println("    ==> origpath: " + flashCardOrigValue);
        if (val1 != null) {
            if (!val1.equals(flashCardOrigValue))
                bRet = true;
        } else if (flashCardOrigValue != null) {
            if (!flashCardOrigValue.equals(val1))
                bRet = true;
        }
        return bRet;
    }

    /**
     * Returns true if specified flashcard has big changes. Currently the big
     * changes is defined as the size difference is greater than 20
     *
     * @param flashCardName flash card name to check
     * @return Returns true if specified flashcard has big changes; false otherwise
     */
    public boolean hasBigChanged(String flashCardName) {
        boolean bRet = false;
        String val1 = flashCardMap.get(flashCardName);
        String val2 = flashCardOrigValue;
        if ((val1 != null) && (val2 != null)) {
            if (!val1.equals(val2) && (Math.abs(val1.length() - val2.length()) >= 20))
                bRet = true;
        }
        return bRet;
    }

    /**
     * Saves current working copy of flashcards to external file; Copy working
     * copy to stand-by copy.
     *
     * @return true on successful save; false otherwise
     */
    public boolean save() {
        boolean bRet = false;
        createAndSortFlashCardNames();
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream(new File(flashCardFile)), "UTF8"));
            pw.println("# Last Update: " + new java.util.Date());
            for (String flashCardName : flashCardNames) {
                String value = flashCardMap.get(flashCardName);
                if (value != null) {
                    if (value.indexOf("\\") >= 0) {
                        // replce all "\" with "\\" under Windows
                        value = StringUtils.replace(value, "\\", "\\\\");
                    }
                    String line = flashCardName + "=" + value;
                    pw.println(line);
                }
            }
            bRet = true;
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        return bRet;
    }
}
