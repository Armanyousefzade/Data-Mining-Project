/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package createdistancematrix;

import com.sun.jmx.snmp.BerDecoder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.abs;

/**
 *
 * @author arman
 */
public class CreateDistanceMatrix {

    public static float[][] CreateDistanceMatrix(Accident[] accidents) {
        float[][] distanceMatrix = new float[2665][2665];
        for (int i = 1; i <= 2664; i++) {
            for (int j = 1; j <= i; j++) {
                distanceMatrix[i][j] = calculateDistance(accidents[i], accidents[j]);
            }
        }
        for (int i = 1; i <= 2664; i++) {
            for (int j = i + 1; j <= 2664; j++) {
                distanceMatrix[i][j] = distanceMatrix[j][i];
            }
        }
        return distanceMatrix;
    }

    public static float calculateDistance(Accident a, Accident b) {
        int makhraj = 17;
        float soorat = 0;
        soorat += (abs(a.NumberofVehicles - b.NumberofVehicles)) / 6;
        soorat += (abs(a.AgeofCasualty - b.AgeofCasualty)) / 94;
        if (a.HolidayOrWeekend == 0 && b.HolidayOrWeekend == 0) {
            makhraj--;
        } else {
            if (a.HolidayOrWeekend != b.HolidayOrWeekend) {
                soorat += 1;
            }
        }
        if (a.RoadClass.equals("Unclassified") || b.RoadClass.equals("Unclassified")) {
            makhraj--;
        } else {
            if (!a.RoadClass.equals(b.RoadClass)) {
                soorat += 1;
            }
        }
        if (!a.AccidentDate.equals(b.AccidentDate)) {
            soorat += 1;
        }
        if (!a.Time24hr.equals(b.Time24hr)) {
            soorat += 1;
        }
        if (!a.RoadSurface.equals(b.RoadSurface)) {
            soorat += 1;
        }
        if (!a.LightingConditions.equals(b.LightingConditions)) {
            soorat += 1;
        }
        if (!a.WeatherConditions.equals(b.WeatherConditions)) {
            soorat += 1;
        }
        if (!a.CasualtyClass.equals(b.CasualtyClass)) {
            soorat += 1;
        }
        if (!a.CasualtySeverity.equals(b.CasualtySeverity)) {
            soorat += 1;
        }
        if (!a.SexofCasualty.equals(b.SexofCasualty)) {
            soorat += 1;
        }
        if (!a.TypeofVehicle.equals(b.TypeofVehicle)) {
            soorat += 1;
        }
        if (!a.DayofWeek.equals(b.DayofWeek)) {
            soorat += 1;
        }
        if (!a.Timenominal.equals(b.Timenominal)) {
            soorat += 1;
        }
        if (!a.Season.equals(b.Season)) {
            soorat += 1;
        }
        if (!a.ageOfCasualtyNominal.equals(b.ageOfCasualtyNominal)) {
            soorat += 1;
        }
        return soorat / makhraj;
    }

    public static Accident[] createAccidentsArray(String path) throws FileNotFoundException, IOException {
        String line;
        String[] lineArray;
        Accident[] accidents = new Accident[2665];
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);
        br.readLine();
        for (int i = 1; i <= 2664; i++) {
            line = br.readLine();
            lineArray = line.split(",");
            Accident accident = new Accident();
            accident.ReferenceNumber = lineArray[0];
            accident.GridRefEasting = lineArray[1];
            accident.GridRefNorthing = lineArray[2];
            accident.NumberofVehicles = Integer.valueOf(lineArray[3]);
            accident.AccidentDate = lineArray[4];
            accident.Time24hr = lineArray[5];
            accident.RoadClass = lineArray[6];
            accident.RoadSurface = lineArray[7];
            accident.LightingConditions = lineArray[8];
            accident.WeatherConditions = lineArray[9];
            accident.CasualtyClass = lineArray[10];
            accident.CasualtySeverity = lineArray[11];
            accident.SexofCasualty = lineArray[12];
            accident.AgeofCasualty = Integer.valueOf(lineArray[13]);
            accident.TypeofVehicle = lineArray[14];
            accident.DayofWeek = lineArray[15];
            if (lineArray[16].equals("Yes")) {
                accident.HolidayOrWeekend = 1;
            } else {
                accident.HolidayOrWeekend = 0;
            }
            accident.Timenominal = lineArray[17];
            accident.Season = lineArray[18];
            accident.ageOfCasualtyNominal = lineArray[19];
            accidents[i] = accident;
        }
        br.close();
        fr.close();
        return accidents;
    }

    public static void printDistanceMatrixInCsvFile(float[][] distanceMatrix, String path) throws IOException {
        FileWriter fw = new FileWriter(path);
        BufferedWriter bw = new BufferedWriter(fw);
        for (int i = 1; i <= 2664; i++) {
            for (int j = 1; j <= 2664; j++) {
                bw.write(String.valueOf(distanceMatrix[i][j]) + ",");
            }
            bw.newLine();
        }
        bw.close();
        fw.close();
    }

    public static void main(String[] args) throws IOException {
        Accident[] accidents = createAccidentsArray("C:\\Users\\arman\\Desktop\\2015New.txt");
        float[][] distanceMatrix = CreateDistanceMatrix(accidents);
        printDistanceMatrixInCsvFile(distanceMatrix, "C:\\Users\\arman\\Desktop\\DistanceMatrix.txt");
    }

}
