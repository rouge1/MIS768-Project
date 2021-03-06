/*
 * Copyright (C) 2014 United States Government as represented by the Administrator of the
 * National Aeronautics and Space Administration.
 * All Rights Reserved.
 */

package gov.nasa.worldwind.ogc.ows;

import gov.nasa.worldwind.util.xml.*;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;
import java.util.*;

/**
 * @author tag
 * @version $Id$
 */
public class OWSHTTP extends AbstractXMLEventParser
{
    protected List<AttributesOnlyXMLEventParser> gets = new ArrayList<AttributesOnlyXMLEventParser>(1);
    protected List<AttributesOnlyXMLEventParser> posts = new ArrayList<AttributesOnlyXMLEventParser>(1);

    public OWSHTTP(String namespaceURI)
    {
        super(namespaceURI);
    }

    public List<String> getGetAddresses()
    {
        if (this.gets == null)
            return null;

        List<String> addresses = new ArrayList<String>(this.gets.size());
        for (AttributesOnlyXMLEventParser parser : this.gets)
        {
            if (parser != null)
                addresses.add((String) parser.getField("href"));
        }

        return addresses;
    }

    public List<String> getPostAddresses()
    {
        if (this.posts == null)
            return null;

        List<String> addresses = new ArrayList<String>(this.posts.size());
        for (AttributesOnlyXMLEventParser parser : this.posts)
        {
            if (parser != null)
                addresses.add((String) parser.getField("href"));
        }

        return addresses;
    }

    public String getGetAddress()
    {
        List<String> addresses = this.getGetAddresses();
        Iterator<String> iter = addresses.iterator();

        return iter.hasNext() ? iter.next() : null;
    }

    public String getPostAddress()
    {
        List<String> addresses = this.getPostAddresses();
        Iterator<String> iter = addresses.iterator();

        return iter.hasNext() ? iter.next() : null;
    }

    protected void doParseEventContent(XMLEventParserContext ctx, XMLEvent event, Object... args)
        throws XMLStreamException
    {
        if (ctx.isStartElement(event, "Get"))
        {
            XMLEventParser parser = this.allocate(ctx, event);
            if (parser != null)
            {
                Object o = parser.parse(ctx, event, args);
                if (o != null && o instanceof AttributesOnlyXMLEventParser)
                    this.gets.add((AttributesOnlyXMLEventParser) o);
            }
        }
        else if (ctx.isStartElement(event, "Post"))
        {
            XMLEventParser parser = this.allocate(ctx, event);
            if (parser != null)
            {
                Object o = parser.parse(ctx, event, args);
                if (o != null && o instanceof AttributesOnlyXMLEventParser)
                    this.posts.add((AttributesOnlyXMLEventParser) o);
            }
        }
        else
        {
            super.doParseEventContent(ctx, event, args);
        }
    }
}
