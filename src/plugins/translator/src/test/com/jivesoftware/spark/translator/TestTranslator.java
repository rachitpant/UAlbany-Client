/**
 * $Revision$
 * $Date$
 *
 * Copyright (C) 1999-2005 Jive Software. All rights reserved.
 * This software is the proprietary information of Jive Software. Use is subject to license terms.
 */
package com.jivesoftware.spark.translator;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

/**
 * A simple class to test translation functionality.
 */
public class TestTranslator {

    public static void main(String[] args) {
        Map translationMap = initalizeTranslationMap();

        boolean again = true;
        while (again) {
            System.out.println("Please enter some text");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String text = "";
            try {
                text = reader.readLine();
            } catch (IOException e) {
                System.out.println("Could not read text:" + e);
            }

            if (text == null) {
                System.out.println("Sorry, I didnt get that.");
                continue;
            }

            System.out.println("Great, now enter the translation id. Below are the following choices:");

            for (Iterator iterator = translationMap.entrySet().iterator(); iterator.hasNext(); ) {
                Map.Entry entry = (Map.Entry) iterator.next();
                System.out.println(entry.getKey() + " - " + ((TranslatorUtil.TranslationType)entry.getValue()).getName());
            }

            String translationID = "";
            try {
                translationID = reader.readLine();
            } catch (IOException e) {
                System.out.println("Could not read translationID:" + e);
            }

            Integer id = Integer.valueOf(translationID);
            TranslatorUtil.TranslationType type = (TranslatorUtil.TranslationType) translationMap.get(id);

            if (type == null) {
                System.out.println("Not a valid translation type.");
                continue;
            }

            String result = TranslatorUtil.translate(text, type);

            System.out.println("Your original text:\n" + text);
            System.out.println("Has been translated from: " + type.getName());
            System.out.println("The result is:\n" + result);

            System.out.println("Do you want to continue testing?");
            String cont = "";
            try {
                cont = reader.readLine();
                if ("yes".equals(cont.toLowerCase().trim()) || "y".equals(cont.toLowerCase().trim())) {
                    again = true;
                } else {
                    again = false;
                }
            } catch (IOException e) {
                System.out.println("Could not read text:" + e);
            }
        }
    }

    private static Map initalizeTranslationMap() {
        TranslatorUtil.TranslationType[] types = TranslatorUtil.TranslationType.getTypes();
        Map map = new TreeMap(new Comparator() {
            public int compare(Object o1, Object o2) {
                if (o1 instanceof Integer && o2 instanceof Integer) {
                    Integer i1 = (Integer) o1;
                    Integer i2 = (Integer) o2;
                    return i1.compareTo(i2);
                }

                return 0;
            }
        });

        for (int i = 1; i < types.length; i++) {
            map.put(new Integer(i), types[i]);
        }

        return map;
    }
}
