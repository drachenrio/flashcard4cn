/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * DConfig - Free Dynamic Configuration Toolkit
 * Copyright (C) 2006, 2014 Jonathan Luo
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Helper class for loading images and icons.
 *
 * @author Jonathan Luo
 */
public class ImageUtil {

    private static final Log log = LogFactory.getLog(ImageUtil.class);
    // 11/07/14 - key: full path name for flashcard4cn
    private static Map<String, List<ImageIcon>> imageMap = new HashMap<String, List<ImageIcon>>(); 
    private static String supportedImageExt = "jpg, jpeg, png";

    public ImageUtil() {
    }

    public static void clearAll() {
        imageMap.clear();
    }
    
    /**
     * 
     * @param path full path directory which contains images
     * @return total number of images loaded
     */
    public static int preLoad(String path) {
        System.out.println("path: " + path);
        if (imageMap.get(path) != null) // already loaded
            return imageMap.get(path).size();

        File file = new File(path);
        File[] files = file.listFiles();
        List<File> fileList = new ArrayList<File>();
        if (files != null) {
            for (File item : files) {
                if (item.isDirectory() || item.isHidden())
                    continue;
                String filename = item.getName();
                String ext = FileUtil.getFileExtension(filename).toLowerCase();
                if (supportedImageExt.indexOf(ext) >= 0) { // image is supported
                    fileList.add(item);
                }
            }
        }
        // sort by name
        Collections.sort(fileList, new FileComparator());
        // add to imageMap
        for (File item : fileList) {
            String filename = item.getName();
            addImageIcon(path, filename, "");
        }
        return imageMap.get(path).size();
    }

    /**
     * 
     * @param path full image path
     * @param filename image filename
     * @param description 
     */
    private static void addImageIcon(String path, String filename, String description) {
    	List<ImageIcon> iconList = imageMap.get(path);
    	if (iconList == null)
            iconList = new ArrayList<ImageIcon>();
        iconList.add(new ImageIcon(path + "/" + filename, description));
        imageMap.put(path, iconList);
    }

    /**
     * Returns a list of ImageIcon, or null if the path was invalid.
     *
     * @param imagePath
     * @return <code>list of ImageIcon</code> created on success; null otherwise.
     */
    public static List<ImageIcon> getImageIcon(String imagePath) {
    	return imageMap.get(imagePath);
    }

    static class FileComparator implements Comparator<File>
    {
        public int compare(File file1, File file2) {
            return file1.getName().compareTo(file2.getName());
        }
    }
}

