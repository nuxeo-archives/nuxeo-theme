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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.theme.Manager;
import org.nuxeo.theme.Registrable;
import org.nuxeo.theme.elements.PageElement;
import org.nuxeo.theme.elements.ThemeElement;
import org.nuxeo.theme.types.Type;
import org.nuxeo.theme.types.TypeFamily;
import org.nuxeo.theme.uids.Identifiable;

/**
 * @author Anahide Tchertchian
 *
 */
// XXX: overloading getters on theme manager, needs to see if setters are needed
// from editor
public class AdapterManager implements Registrable {

    private static final Log log = LogFactory.getLog(AdapterManager.class);

    private final List<Adapter> adapters = new ArrayList<Adapter>();

    // adapters management

    public List<Adapter> getSortedAdapters() {
        if (adapters.isEmpty()) {
            // (re)-compute it
            List<Type> types = Manager.getTypeRegistry().getTypes(
                    TypeFamily.ADAPTER);
            List<AdapterType> adapterTypes = new ArrayList<AdapterType>();
            for (Type type : types) {
                adapterTypes.add((AdapterType) type);
            }
            Collections.sort(adapterTypes);
            for (AdapterType adapterType : adapterTypes) {
                try {
                    Adapter adapt = adapterType.getKlass().newInstance();
                    adapt.setName(adapterType.getTypeName());
                    adapters.add(adapt);
                } catch (Exception e) {
                    log.warn("Could not create registry: "
                            + adapterType.getTypeName());
                }
            }
        }
        return adapters;
    }

    public ThemeElement getThemeByName(final String name) {
        ThemeElement theme = null;
        // try to get it form adapters
        for (Adapter adapter : getSortedAdapters()) {
            theme = adapter.getThemeByName(name);
            if (theme != null) {
                break;
            }
        }
        return theme;
    }

    // public void deleteTheme(String src) throws ThemeIOException,
    // ThemeException
    // public void registerTheme(final ThemeElement theme)
    // public void unregisterTheme(final ThemeElement theme)

    // pages

    public PageElement getPageByPath(final String path) {
        PageElement page = null;
        for (Adapter adapter : getSortedAdapters()) {
            page = adapter.getPageByPath(path);
            if (page != null) {
                break;
            }
        }
        return page;
    }

    // public void registerPage(final ThemeElement theme, final PageElement
    // page) {
    // }
    //
    // public void registerTheme(final ThemeElement theme) {
    // }
    //
    // public void unregisterPage(PageElement page) {
    // }

    // namedObjectsByTheme

    public Identifiable getNamedObject(final String themeName,
            final String realm, final String name) {
        Identifiable namedObject = null;
        for (Adapter adapter : getSortedAdapters()) {
            namedObject = adapter.getNamedObject(themeName, realm, name);
            if (namedObject != null) {
                break;
            }
        }
        return namedObject;
    }

    public List<Identifiable> getNamedObjects(final String themeName,
            final String realm) {
        List<Identifiable> namedObjects = null;
        for (Adapter adapter : getSortedAdapters()) {
            namedObjects = adapter.getNamedObjects(themeName, realm);
            if (namedObjects != null) {
                break;
            }
        }
        return namedObjects;
    }

    // called from editor or unregister
    // public void removeNamedObject(final String themeName, final String realm,
    // final String name) {
    // }

    // called from editor or unregister
    // public void removeNamedObjects(final String themeName) {
    // }

    // called from parser => make sure this one is called in case one of the
    // adapters matches
    /**
     * returns true if one of the adapters registered it ok
     */
    public boolean setNamedObject(final String themeName, final String realm,
            final Identifiable object) {
        for (Adapter adapter : getSortedAdapters()) {
            if (adapter.setNamedObject(themeName, realm, object)) {
                return true;
            }
        }
        return false;
    }

    // themeOfNamedObjects

    public String getThemeNameOfNamedObject(Identifiable object) {
        String themeName = null;
        for (Adapter adapter : getSortedAdapters()) {
            themeName = adapter.getThemeNameOfNamedObject(object);
            if (themeName != null) {
                break;
            }
        }
        return themeName;
    }

    public void clear() {
        for (Adapter adapter : getSortedAdapters()) {
            adapter.clear();
        }
    }

    // only useful for editor (see Editor#pasteElement)
    // public static Element getElementById(final Integer id)

}
