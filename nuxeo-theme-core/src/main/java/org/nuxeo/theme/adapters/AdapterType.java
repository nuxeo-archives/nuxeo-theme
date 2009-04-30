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

import org.nuxeo.common.xmap.annotation.XNode;
import org.nuxeo.common.xmap.annotation.XObject;
import org.nuxeo.theme.types.Type;
import org.nuxeo.theme.types.TypeFamily;

/**
 * @author Anahide Tchertchian
 *
 */
@XObject("adapter")
public final class AdapterType implements Type, Comparable<AdapterType> {

    @XNode("@name")
    public String name;

    @XNode("class")
    public Class<? extends Adapter> klass;

    @XNode("order")
    public int order = 0;

    public String getTypeName() {
        return name;
    }

    public Class<? extends Adapter> getKlass() {
        return klass;
    }

    public TypeFamily getTypeFamily() {
        return TypeFamily.ADAPTER;
    }

    public Integer getOrder() {
        return order;
    }

    public int compareTo(AdapterType o) {
        return order - o.order;
    }

}
