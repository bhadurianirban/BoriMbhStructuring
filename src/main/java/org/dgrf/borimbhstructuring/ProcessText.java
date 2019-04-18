/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dgrf.borimbhstructuring;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
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
    private PrintWriter errorWriter;
    private List<String> shlokaTextList;

    private final int SHLOKA_LINE = 0;
    private final int SHLOKA_END_LINE = 1;
    private final int UBACHA_LINE = 2;
    private final int ADHYAY_NUMBER_LINE = 3;
    private final int BAD_LINE = 99;

    private int parvaId;
    private int adhyayId;
    private int shlokaNumber;
    private int textShlokaNumber;
    private int prevTextShlokaNumber;
    private int lastShlokMaxLine;
    private int shlokaLine;
    private String ubacha;
    private String shlokaText;
    Map<Character, Character> devToEnglishMap;

    public ProcessText(String inputFileName, String outputFileName,PrintWriter errorWriter, int parvaId) {
        this.inputFileName = inputFileName;
        this.outputFileName = outputFileName;
        this.errorWriter = errorWriter;
        devToEnglishMap = new HashMap<>();
        this.parvaId = parvaId;
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
    }

    public void processFile() {
        BufferedReader reader;
        PrintWriter writer;
        

        
        adhyayId = 1;
        if (parvaId == 1) {
            shlokaNumber = 0;
        } else {
            shlokaNumber = 1;
        }
        
        textShlokaNumber = 0;
        prevTextShlokaNumber = -1;
        lastShlokMaxLine = 0;
        shlokaLine = 1;
        ubacha = "Narrator";
        shlokaText = "";
        ShlokaLine sl = new ShlokaLine();
        try {
            reader = new BufferedReader(new FileReader(inputFileName));
            writer = new PrintWriter(outputFileName, "UTF-8");
            
            String line = reader.readLine();
            String outPutLine;
            while (line != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    int lineType = decideLineType(line);
                    if (lineType != BAD_LINE) {
                        if (lineType == ADHYAY_NUMBER_LINE) {
                            adhyayId = convertDevNagariNumberString(line);
                            if (shlokaNumber != 0) {
                                shlokaNumber = 1;
                            }
                            shlokaLine = 1;
                        } else if (lineType == UBACHA_LINE) {
                            ubacha = line.replaceAll("[|]", "").trim();
                        } else if (lineType == SHLOKA_LINE) {
                            shlokaText = line.replaceAll("[|;]", "").trim();
                            outPutLine = parvaId + "," + adhyayId + "," + ubacha + "," + shlokaNumber + "," + shlokaLine + "," + shlokaText + ",|";

                            System.out.println(outPutLine);
                            writer.println(outPutLine);
                            shlokaLine++;
                        } else if (lineType == SHLOKA_END_LINE) {
                            shlokaText = line.replaceAll("[|;]", "").replaceAll("[०१२३४५६७८९]", "").trim();
                            textShlokaNumber = findShlokaNumber(line);
                            if (prevTextShlokaNumber == textShlokaNumber) {
                                shlokaNumber = textShlokaNumber;
                                shlokaLine = lastShlokMaxLine + 1;
                            }
                            outPutLine = parvaId + "," + adhyayId + "," + ubacha + "," + shlokaNumber + "," + shlokaLine + "," + shlokaText + ",||";
                            System.out.println(outPutLine);
                            writer.println(outPutLine);
                            shlokaNumber++;
                            lastShlokMaxLine = shlokaLine;
                            shlokaLine = 1;

                            prevTextShlokaNumber = textShlokaNumber;

                        }
                    } else {
                        if (!line.equals("<HR>"))
                        errorWriter.println(parvaId + "," + adhyayId + "," + ubacha + "," + shlokaNumber+ "," + shlokaLine + "," + line);
                    }

                }

                // read next line
                line = reader.readLine();
            }
            writer.close();
            reader.close();
            
        } catch (IOException | NotADevNagariNumberException e) {
            e.printStackTrace();
        }
    }

    public int decideLineType(String shlokaLine) {
        if (isAdhyayNumberLine(shlokaLine)) {
            return ADHYAY_NUMBER_LINE;
        } else if (isShlokaLine(shlokaLine)) {
            return SHLOKA_LINE;
        } else if (isShlokaEndLine(shlokaLine)) {
            return SHLOKA_END_LINE;
        } else if (isUbachaLine(shlokaLine)) {
            return UBACHA_LINE;
        } else {
            return BAD_LINE;
        }

    }

    public int findShlokaNumber(String shlokaLine) {
        int shlokaNumber;
        try {
            shlokaNumber = findShlokaNumberTypeOne(shlokaLine);
        } catch (NotADevNagariNumberException ex) {
            try {
                shlokaNumber = findShlokaNumberTypeTwo(shlokaLine);
            } catch (NotADevNagariNumberException ex1) {
                return -1;
            }
        }
        return shlokaNumber;
    }

    public int findShlokaNumberTypeOne(String shlokaLine) throws NotADevNagariNumberException {
        String regexString = Pattern.quote("||") + "(.*?)" + Pattern.quote("||");
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(shlokaLine);
        String shlokaNumberString = "0";
        if (matcher.find()) {
            shlokaNumberString = matcher.group(1);
        }

        return (convertDevNagariNumberString(shlokaNumberString));

    }

    public int findShlokaNumberTypeTwo(String shlokaLine) throws NotADevNagariNumberException {
        String regexString = Pattern.quote("| ") + "(.*?)" + Pattern.quote(" |");
        Pattern pattern = Pattern.compile(regexString);
        Matcher matcher = pattern.matcher(shlokaLine);
        String shlokaNumberString = "0";
        if (matcher.find()) {
            shlokaNumberString = matcher.group(1);
        }

        return (convertDevNagariNumberString(shlokaNumberString));

    }

    public boolean isUbachaLine(String shlokaLine) {
        if (shlokaLine.contains("वाच|")) {
            return true;
        } else {
            return shlokaLine.contains("ऊचुः|");
        }
    }

    public boolean isShlokaLine(String shlokaLine) {
        if (!isUbachaLine(shlokaLine)) {
            if (shlokaLine.contains("|")) {
                if (!shlokaLine.matches(".*[०१२३४५६७८९].*")) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isShlokaEndLine(String shlokaLine) {
        if (!isUbachaLine(shlokaLine)) {
            if (shlokaLine.contains("|")) {
                if (shlokaLine.matches(".*[०१२३४५६७८९].*")) {
                    return true;
                }
            }
        }
        return false;

    }

    public boolean isAdhyayNumberLine(String shlokaLine) {
        if (!isUbachaLine(shlokaLine)) {
            if ((!isShlokaEndLine(shlokaLine))) {
                if ((!isShlokaLine(shlokaLine))) {
                    if (shlokaLine.matches(".*[०१२३४५६७८९].*")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int convertDevNagariNumberString(String numberString) throws NotADevNagariNumberException {

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
