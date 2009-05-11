/*
 * (C) Copyright 2006-2009 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 *     Anahide Tchertchian
 *
 * $Id$
 */

package org.nuxeo.theme.adapters;

import java.util.List;

import org.nuxeo.theme.elements.PageElement;
import org.nuxeo.theme.elements.ThemeElement;
import org.nuxeo.theme.uids.Identifiable;

/**
 * Interface for pluggable theme (and associated elements) lookup
 *
 * @author Anahide Tchertchian
 */
public interface Adapter {

    void clear();

    /**
     * Returns adapter name, can be used to identify themes managed by it.
     */
    String getName();

    void setName(String name);

    /**
     * Returns given theme if found, else null.
     */
    ThemeElement getThemeByName(String name);

    /**
     * Returns given page if found, else null.
     */
    PageElement getPageByPath(String path);

    /**
     * Returns given object if found, else null.
     */
    Identifiable getNamedObject(String themeName, String realm, String name);

    /**
     * Returns given objects if found, else null.
     */
    List<Identifiable> getNamedObjects(String themeName, String realm);

    /**
     * Returns true if one of the adapter registered it ok
     */
    boolean setNamedObject(final String themeName, final String realm,
            final Identifiable object);

    /**
     * Returns theme name if found, else null.
     */
    String getThemeNameOfNamedObject(Identifiable object);

    // XXX are formats specific to theme?
    // List<Format> getFormatsByTypeName(final String formatTypeName);
    //
    // Set<String> getFormatTypeNames();
    //
    // List<Format> listFormats();
    //
    // void registerFormat(final Format format) throws ThemeException;

    // XXX: public void unregisterFormat(final Format format) throws
    // ThemeException {
}
