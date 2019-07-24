/**
 * Copyright 2014-2018, Armenak Grigoryan, and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 */



package com.strider.datadefender.specialcase;

import static org.apache.log4j.Logger.getLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.validator.EmailValidator;
import org.apache.log4j.Logger;

import com.strider.datadefender.database.metadata.MatchMetaData;
import com.strider.datadefender.file.metadata.FileMatchMetaData;
import com.strider.datadefender.utils.CommonUtils;

/**
 * @author Armenak Grigoryan
 */
public class EmailDetector implements SpecialCase {
    private static final Logger LOG = getLogger(EmailDetector.class);
    private static final String EMAIL_REGEX = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    private static Pattern pattern;
    private static Matcher matcher;
    
    public static FileMatchMetaData detectEmail(final FileMatchMetaData metaData, final String text) {
        String emailValue = "";
        
        if (!CommonUtils.isEmptyString(text)) {
            emailValue = text;
        }

        LOG.info("Trying to find email in file " + metaData.getFileName() + " : " + emailValue);
        if (isValidEmail(emailValue)) {
                LOG.info("Email detected: " + emailValue);
                metaData.setAverageProbability(1.0);
                metaData.setModel("email");
                return metaData;
        } else {
            LOG.info("Email " + emailValue + " is not valid" );
        }

        return null;
    }    
    
    public static MatchMetaData detectEmail(final MatchMetaData metaData, final String text) {

    	String emailValue = "";
        
        if (!CommonUtils.isEmptyString(text)) {
            emailValue = text;
        }

        if (isValidEmail(emailValue)) {
                metaData.setAverageProbability(1.0);
                metaData.setModel("email");
                return metaData;
        }
        
        return null;
    }    

    /**
     * Algorithm is taken from https://en.wikipedia.org/wiki/Social_Insurance_Number
     * @param sin
     * @return boolean true, if SIN is valid, otherwise false
     */
    private static boolean isValidEmail(final String email) {
        
		EmailValidator eValidator = EmailValidator.getInstance();
		return eValidator.isValid(email);
    }    
}