/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package com.ejwa.frontend.resources;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import net.minidev.json.JSONObject;

/**
 *
 * @author jjaok
 */
public class API {

    public static String error(String msg) {
        return "{\"error\":\"" + msg + "\"}";
    }

    public static String matchDataInput(String[] attributes, JSONObject data) {
        String error = "";

        if (attributes.length != data.keySet().size()) {
            error += "Wrong number of arguments. ";
        } else {
            for (String attribute : attributes) {
                if (!data.containsKey(attribute) || data.get(attribute) == null || data.getAsString(attribute).equals("")) {
                    error += "Missing " + attribute + ". ";
                }
            }
        }
        return error;
    }

    public static String message(String msg) {
        return "{\"message\":\"" + msg + "\"}";
    }

}
