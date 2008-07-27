/*
 * (C) Copyright 2006-2007 Nuxeo SAS <http://nuxeo.com> and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Jean-Marc Orliaguet, Chalmers
 *
 * $Id$
 */

package org.nuxeo.theme.html;

import java.util.Map;

import org.nuxeo.theme.Manager;
import org.nuxeo.theme.models.Info;

public class InfoBean {

    public Map<String, Info> getMap() {
        return Manager.getInfoPool().getInfoMap();
    }

}