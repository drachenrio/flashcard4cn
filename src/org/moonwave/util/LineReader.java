/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * DConfig - Free Dynamic Configuration Toolkit
 * Copyright (C) 2006, 2007 Jonathan Luo
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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.List;

/**
 * Read a line from a file.
 *
 * @author Jonathan Luo
 */
public class LineReader extends BufferedReader {

    // By default, line begining with '#', '--', or '//' is a comment line
    List<String> commentSymbol = new ArrayList<String>();

    public LineReader(String filename) throws java.io.IOException {
        super(new InputStreamReader(FileUtil.toURL(filename).openStream()));
        addDefaultCommentSymbols();
    }

    public LineReader(InputStreamReader in) {
        super(in);
        addDefaultCommentSymbols();
    }

    public LineReader(FileReader in) throws java.io.FileNotFoundException, java.net.URISyntaxException {
        super(in);
        addDefaultCommentSymbols();
    }

    public void addDefaultCommentSymbols() {
        this.commentSymbol.add("#");
        this.commentSymbol.add("--");
        this.commentSymbol.add("//");
    }

    /**
     * Clears comment symbols
     */
    public void clearCommentSymbol() {
        this.commentSymbol.clear();
    }

    /**
     * Adds an additional comment symbol
     *
     * @param symbol symbol to add
     */
    public void addCommentSymbol(String symbol) {
        this.commentSymbol.add(symbol);
    }

    private boolean isCommentLine(String line) {
        boolean bRet = false;
        for (String symbol: commentSymbol) {
            if (line.startsWith(symbol)) {
                bRet = true;
                break;
            }
        }
        return bRet;
    }

    /**
     * Gets next valid non-comment, non empty line.
     */
    @Override
    public final String readLine() throws IOException {
        String line;
        do {
            line = super.readLine();
            if (line != null)
                line = line.trim();
        } while ((line != null) && ((line.length() == 0) || isCommentLine(line)));
        return line;
    }

    /**
     * Gets the next line
     */
    public final String nextLine() throws IOException {
        return readLine();
    }

    public static void main(String args[]) {
        test();
    }

    public static String test() {
        StringBuffer sb = new StringBuffer();
        try {

            java.io.FileInputStream fis = new java.io.FileInputStream
            (new java.io.File( PathUtil.getLeadingPath() + "/conf/readme_1.txt")); // path + "/myprops.props"
            InputStreamReader reader = new InputStreamReader(fis);
            //SqlReader in = new SqlReader(PathUtil.getFullPath("conf/derby_10.1.3.1.sql"));
            LineReader in = new LineReader(reader);
            String sql;
            while (true) {
                sql = in.nextLine();
                if ((sql == null) || (sql.length() == 0))
                    break;
                sb.append(sql);
                System.out.println(sql);
            }
            in.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return sb.toString();
    }
}
