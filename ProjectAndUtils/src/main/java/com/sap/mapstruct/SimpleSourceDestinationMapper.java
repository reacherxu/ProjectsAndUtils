/**
 * SAP Inc.
 * Copyright (c) 1972-2019 All Rights Reserved.
 */
package com.sap.mapstruct;

import org.mapstruct.Mapper;

/**
 *
 * @author richard.xu03@sap.com
 * @version $Id: SimpleSourceDestinationMapper.java, v 0.1 Sep 6, 2019 2:59:12 PM richard.xu Exp $
 */
@Mapper
public interface SimpleSourceDestinationMapper {
    SimpleDestination sourceToDestination(SimpleSource source);

    SimpleSource destinationToSource(SimpleDestination destination);
}
