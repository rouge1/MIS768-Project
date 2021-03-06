/*
 * Copyright (C) 2012 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.ogc.collada;

/**
 * Parser class for COLLADA elements that are not used by World Wind.
 *
 * @author pabercrombie
 * @version $Id$
 */
public class ColladaUnsupported extends ColladaAbstractObject
{
    /**
     * Construct an instance.
     *
     * @param namespaceURI the qualifying namespace URI. May be null to indicate no namespace qualification.
     */
    public ColladaUnsupported(String namespaceURI)
    {
        super(namespaceURI);
    }
}
