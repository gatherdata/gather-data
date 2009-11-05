/**
 * The contents of this file are subject to the AED Public Use License Agreement, Version 1.0 (the "License");
 * use in any manner is strictly prohibited except in compliance with the terms of the License.
 * The License is available at http://gatherdata.org/license.
 *
 * Copyright (c) AED.  All Rights Reserved
 */
package org.gatherdata.data.core.model.impl;

import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.gatherdata.commons.model.impl.UniqueEntitySupport;
import org.gatherdata.data.core.model.FlatForm;
import org.gatherdata.data.core.model.RenderedValue;

public class FlatFormSupport extends UniqueEntitySupport {
    
    public boolean equals(FlatForm lhs, FlatForm rhs) {
        boolean areEqual = super.equals(lhs, rhs);

        if (areEqual) {
            areEqual = new EqualsBuilder().append(lhs.getNamespace(), rhs.getNamespace())
            .isEquals();
        }
        
        return areEqual;
    }

    public static boolean deepEquals(FlatForm lhs, FlatForm rhs) {
        boolean areEqual = true;

        if (lhs != rhs) { // don't bother comparing object to itself
            areEqual = UniqueEntitySupport.deepEquals(lhs, rhs);

            if (areEqual) { // check namespace
                areEqual = new EqualsBuilder().append(lhs.getNamespace(), rhs.getNamespace())
                    .isEquals();

                if (areEqual) { // check RenderedValues
                    List<? extends RenderedValue> lhsValues = lhs.getValues();
                    List<? extends RenderedValue> rhsValues = rhs.getValues();
                    if (lhsValues != null) {
                        if (rhsValues != null) {
                            areEqual = lhsValues.containsAll(rhsValues);
                            if (areEqual) {
                                areEqual = rhsValues.containsAll(lhsValues);
                            }
                                    
                        } else {
                            areEqual = false;
                        }
                    } else {
                        areEqual = (rhsValues == null);
                    }
                    
                }
            }
        }

        return areEqual;
    }

}
