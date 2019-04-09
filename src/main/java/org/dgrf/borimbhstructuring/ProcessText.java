/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author bhaduri
 */
public class ProcessText {

    private String inputFileName;
    private String outputFileName;
    private List<String> shlokaTextList;

    public ProcessText(String inputFileName, String outputFileName) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
    }

    public void readInputFile() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(inputFileName));
            String line = reader.readLine();
            while (line != null) {
                line = line.trim();
                if (!line.isEmpty()) {

                    System.out.println(line);
                }
                // read next line
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void findShlokaNumber(String shlokaLine) {
        String regexString = Pattern.quote("||") + "(.*?)" + Pattern.quote("||");
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(shlokaLine);
        if (matcher.find()) {
            System.out.println(matcher.group(1));
        } else {
            System.out.println("gg");
        }
    }

    public int convertDevNagariNumberString(String numberString) throws NotADevNagariNumberException {
        Map<Character, Character> devToEnglishMap = new HashMap<Character, Character>();
        devToEnglishMap.put('०', '0');
        devToEnglishMap.put('१', '1');
        devToEnglishMap.put('२', '2');
        devToEnglishMap.put('३', '3');
        devToEnglishMap.put('४', '4');
        devToEnglishMap.put('५', '5');
        devToEnglishMap.put('६', '6');
        devToEnglishMap.put('७', '7');
        devToEnglishMap.put('८', '8');
        devToEnglishMap.put('९', '9');
        char[] numberStringArray = numberString.toCharArray();
        String englishNumberString = "";
        for (int i = 0; i < numberStringArray.length; i++) {
            Character englishChar = devToEnglishMap.get(numberStringArray[i]);
            if (englishChar == null) {
                throw new NotADevNagariNumberException("Invalid Devnagari number");
            }
            englishNumberString = englishNumberString + englishChar;
        }
        int enlishNumber = Integer.parseInt(englishNumberString);
        return enlishNumber;
    }
}
