/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preparedatasource;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PrepareDataSource {

    public static void main(String[] args) throws IOException, ParseException {
        AddFurtherColumns("C:\\Users\\arman\\Desktop\\2015.txt", "C:\\Users\\arman\\Desktop\\2015New.txt");
    }

    public static void AddFurtherColumns(String Readingpath, String WritingPath) throws IOException, ParseException {
        String line;
        FileWriter fw = new FileWriter(WritingPath);
        BufferedWriter bw = new BufferedWriter(fw);
        FileReader fr = new FileReader(Readingpath);
        BufferedReader br = new BufferedReader(fr);
        line = br.readLine();
        line = line + ",Day of Week" + ",HolidayOrWeekend" + ",Time(nominal)" + ",Season" + ",Age of casualty(nominal)";
        bw.write(line);
        bw.newLine();
        String dateInput = "";
        String date;
        String time;
        String age;
        String season = "Summer";
        String[] lineArray;
        String[] dateArray;
        for (int i = 2; i <= 2665; i++) {
            line = br.readLine();
            lineArray = line.split(",");
            age = lineArray[13];
            date = lineArray[4];
            time = lineArray[5];
            dateArray = date.split("-");
            switch (dateArray[1]) {
                case "Jan":
                    season = "Winter";
                    dateInput = dateArray[0] + "/" + "1" + "/" + "2015";
                    break;
                case "Feb":
                    season = "Winter";
                    dateInput = dateArray[0] + "/" + "2" + "/" + "2015";
                    break;
                case "Mar":
                    season = "Spring";
                    dateInput = dateArray[0] + "/" + "3" + "/" + "2015";
                    break;
                case "Apr":
                    season = "Spring";
                    dateInput = dateArray[0] + "/" + "4" + "/" + "2015";
                    break;
                case "May":
                    season = "Spring";
                    dateInput = dateArray[0] + "/" + "5" + "/" + "2015";
                    break;
                case "Jun":
                    season = "Summer";
                    dateInput = dateArray[0] + "/" + "6" + "/" + "2015";
                    break;
                case "Jul":
                    season = "Summer";
                    dateInput = dateArray[0] + "/" + "7" + "/" + "2015";
                    break;
                case "Aug":
                    season = "Summer";
                    dateInput = dateArray[0] + "/" + "8" + "/" + "2015";
                    break;
                case "Sep":
                    season = "Autumn";
                    dateInput = dateArray[0] + "/" + "9" + "/" + "2015";
                    break;
                case "Oct":
                    season = "Autumn";
                    dateInput = dateArray[0] + "/" + "10" + "/" + "2015";
                    break;
                case "Nov":
                    season = "Autumn";
                    dateInput = dateArray[0] + "/" + "11" + "/" + "2015";
                    break;
                case "Dec":
                    season = "Winter";
                    dateInput = dateArray[0] + "/" + "12" + "/" + "2015";
                    break;
            }
            SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
            Date dt1 = format1.parse(dateInput);
            DateFormat format2 = new SimpleDateFormat("EEEE");
            String finalDay = format2.format(dt1);
            if (IsHolidayOrWeekend(finalDay, dateInput)) {
                line = line + "," + finalDay + ",Yes";
            } else {
                line = line + "," + finalDay + ",No";
            }
            line = line + ConvertNumericTimeToNominalTime(time) + "," + season + "," + getNominalAge(age);
            bw.write(line);
            bw.newLine();
        }
        bw.close();
        br.close();
        fr.close();
        fw.close();
    }

    public static boolean IsHolidayOrWeekend(String dayOfWeek, String date) {
        if (dayOfWeek.equals("Saturday") || dayOfWeek.equals("Sunday")) {
            return true;
        }
        switch (date) {
            case "1/1/2015":
                return true;
            case "3/4/2015":
                return true;
            case "6/4/2015":
                return true;
            case "4/5/2015":
                return true;
            case "25/5/2015":
                return true;
            case "31/8/2015":
                return true;
            case "25/12/2015":
                return true;
            case "26/12/2015":
                return true;
        }
        return false;
    }

    public static String ConvertNumericTimeToNominalTime(String time) {
        int time2 = Integer.valueOf(time);
        if (600 <= time2 && time2 < 1200) {
            return ",morning";
        }
        if (1200 <= time2 && time2 < 1800) {
            return ",afternoon";
        }
        if (1800 <= time2 && time2 < 2100) {
            return ",evening";
        }
        if (2100 <= time2 && time2 <= 2359) {
            return ",night";
        }
        if (0 <= time2 && time2 <= 600) {
            return ",night";
        }
        return ",night";
    }

    public static String getNominalAge(String Age) {
        int age = Integer.valueOf(Age);
        if (age < 11) {
            return "child";
        }
        if (11 <= age && age <= 18) {
            return "teenager";
        }
        if (18 < age && age <= 35) {
            return "young";
        }
        if (35 < age && age < 60) {
            return "middle age";
        }
        if (age >= 60) {
            return "old";
        }
        return "young";
    }
}
