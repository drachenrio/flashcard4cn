/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * DConfig - Free Dynamic Configuration Toolkit
 * http://dconfig.sourceforge.net
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

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 * Creates commonly used message dialogs.
 *
 * @author Jonathan Luo
 */
public class DialogUtil {
    
    public static int showConfirm(Component parentComponent, String title, String message) {
        return JOptionPane.showConfirmDialog(parentComponent,
                message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public static int showYesNoConfirm(Component parentComponent, String title, String message) {
        return JOptionPane.showConfirmDialog(parentComponent,
                message, title, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
    }

    public static void showInfo(Component parentComponent, String title, String message) {
        JOptionPane.showMessageDialog(parentComponent,
            message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showError(Component parentComponent, String title, String message) {
        JOptionPane.showMessageDialog(parentComponent,
            message, title, JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showKeyExistError(Component parentComponent, String keyName) {
        StringBuffer sb = new StringBuffer(100);
        sb.append("Cannot rename current key to '");
        sb.append(keyName);
        sb.append("'. The specified key name already exists. Pleaser try again.");
        JOptionPane.showMessageDialog(parentComponent,
            sb.toString(), "Error Renaming Key", JOptionPane.ERROR_MESSAGE);
    }
}
