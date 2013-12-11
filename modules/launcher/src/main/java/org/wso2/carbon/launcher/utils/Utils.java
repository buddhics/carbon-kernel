/*
*  Copyright (c) 2005-2013, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.wso2.carbon.launcher.utils;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.wso2.carbon.launcher.utils.Constants.CARBON_HOME;

public class Utils {

    private static final String VAR_REGEXP = "\\$\\{[^}]*}";
    private static final Pattern varPattern = Pattern.compile(VAR_REGEXP);

    /**
     * Replace system property holders in the property values.
     * e.g. Replace ${carbon.home} with value of the carbon.home system property.
     */
    public static String substituteVars(String value) {
        String newValue = value;

        Matcher matcher = varPattern.matcher(value);
        while (matcher.find()) {
            String sysPropKey = value.substring(matcher.start() + 2, matcher.end() - 1);
            String sysPropValue = System.getProperty(sysPropKey);
            if (sysPropValue == null || sysPropValue.length() == 0) {
                throw new RuntimeException("System property " + sysPropKey + " cannot be null");
            }
            newValue = newValue.replaceFirst(VAR_REGEXP, sysPropValue);
        }
        return newValue;
    }

    public static String getRepositoryConfDir() {
        return System.getProperty(CARBON_HOME) + File.separator + Constants.REPOSITORY_CONF_DIR_PATH;
    }

    public static void main(String[] args) {
//        String text = "Sameera${carbon.home}/test/samddddeera dfdfd ${profileID}";
//        String text2 = "${carbon.home}Sameerajasdkjflakds adsjflkasd  asldfj ${ adfadsf asdfadsf";
//
//        Properties props = new Properties();
//        props.put("temp", text);
//        props.put("temp1", text2);
//        System.setProperty("carbon.home", "/sameera");
//        System.setProperty("profileID", "default");
//        System.out.println(substituteVars(text));
//
//        File newFile = new File("temp.txt");
//        try {
//            System.out.println(newFile.toURI().toURL().toExternalForm());
//        } catch (MalformedURLException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

//        for (Map.Entry entry : props.entrySet()) {
//            System.out.println(entry.getValue());
//        }

//        System.out.println(checkForNullOrEmpty(""));
//        System.out.println(checkForNullOrEmpty(null));
//        System.out.println(checkForNullOrEmpty("sameera"));

        String name = "file:plugins/org.eclipse.equinox.console_1.0.100.v20130429-0953.jar@2:true";
        Pattern bundleEntryPattern = Pattern.compile("(file):(.*)@(.*):(.*)");
//        Pattern mvnPattern = Pattern.compile("mvn:([^/ ]+)/([^/ ]+)/([^/ ]*)(/([^/ ]+)(/([^/ ]+))?)?");
        Matcher m = bundleEntryPattern.matcher(name.toString());
        if(!m.matches()){
            System.out.println("Does not match");
        }
        System.out.println(m.group(1));
        System.out.println(m.group(2));
        System.out.println(m.group(3));
        System.out.println(m.group(4));


    }

    public static boolean checkForNullOrEmpty(String arg) {
        return (arg == null || arg.length() == 0);
    }

    public static URL getURLFromString(String arg) {
        File file = new File(arg);
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static String[] tokenize(String list, String delim){
        if (checkForNullOrEmpty(list)){
            return new String[0];
        }
        ArrayList<String> tokenList = new ArrayList<String>();
        StringTokenizer stringTokenizer = new StringTokenizer(list, delim);
        while(stringTokenizer.hasMoreElements()){
            String token = stringTokenizer.nextToken().trim();
            if(!checkForNullOrEmpty(token)){
                tokenList.add(token);
            }
        }
        return tokenList.toArray(new String[tokenList.size()]);
    }
}