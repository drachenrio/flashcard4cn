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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Jonathan Luo
 */
public class RandomGenerator {

    List generatedList = new ArrayList(); // keep track of generated ids for repeated tests
    Random r = new Random();
    int testSize;
    int numberOfWordsPerTest = 0;
    int count = 0;
    int testCount = 0;

    public RandomGenerator() {
        r.setSeed((long) new java.util.Date().getTime());
    }

    public RandomGenerator(int testSize, int numberOfWordsTest) {
        //r.setSeed((long) new java.util.Date().getTime());
        this.testSize = testSize;
        this.numberOfWordsPerTest = numberOfWordsTest;
    }
    
    public RandomGenerator(long seed, int maxValue) {
        r.setSeed(seed);
        this.testSize = maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.testSize = maxValue;
    }

    public void setNumberOfWordsPerTest(int numberOfWordsPerTest) {
        this.numberOfWordsPerTest = numberOfWordsPerTest;
    }
        
    public void removeTheFirstHalf() {
        ArrayList list = new ArrayList();
        int halfSize = generatedList.size() / 2;
        if (halfSize > 0) { // handle there is only one Character case
            for (int i = halfSize; i < generatedList.size(); i++) {
                list.add(generatedList.get(i));
            }
        }
        clearGeneratedList();
        generatedList.addAll(list);
        list.clear();
        if (AppState.isVerbose()) {
            System.out.println("generatedList.size(): " + generatedList.size());
            System.out.println("removeTheFirstHalf");
        }
    }

    public void resetTestCount() {
        testCount = 0;
    }

    public void clearGeneratedList() {
        this.generatedList.clear();
        count = 0;
        testCount = 0;
        if (AppState.isVerbose())
            System.out.println("clearGeneratedList()");
    }

    public int nextInt() {
        int iRet = 0;
        while (true) {
            // Returns a pseudorandom, uniformly distributed int value
            // between 0 (inclusive) and the specified value (exclusive)
            iRet = r.nextInt(testSize);
            if ((iRet >= testSize) || (iRet < 0)) // this check is unnecessary, keep it temporarily
                continue;
            count++;
            if (generatedList.size() >= testSize) // reset
                removeTheFirstHalf();

            if (generatedList.contains(iRet)) {
                if (AppState.isVerbose())
                    System.out.println("Random number count: " + count + " - nextInt: " + iRet + " (duplicated), generatedList.size():" + generatedList.size() + ", testCount: " + testCount);
                continue;
            }
            testCount++;
            if (AppState.isVerbose())
                System.out.println("Random number count: " + count + " - nextInt: " + iRet + ";   generatedList.size():" + generatedList.size() + ", testCount: " + testCount);
            generatedList.add(iRet);
            break;
        }
        return iRet;
    }
    
    public static void main(String[] args) {
      int testSize = 12;
      int testCount = 15; // testCount must <= testSize
      RandomGenerator r = new RandomGenerator(testSize, testCount);
      for (int i = 0; i < testCount; i++)
        System.out.println(r.nextInt());
    }
}
