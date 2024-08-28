package com.util;

import com.dto.ScoreResultDto;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class CSVWriteUtil {

    public static void writeMapToCSV(Map<String, ScoreResultDto> matches, String filePath) {
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(filePath))) {
            // Escribir encabezado
            String[] header = {"ContactID Source", "ContactID Match", "Score", "Accuracy"};
            csvWriter.writeNext(header);

            // Recorrer el map y escribir cada entrada
            for (Map.Entry<String, ScoreResultDto> entry : matches.entrySet()) {
                String source = String.valueOf(entry.getValue().getSourceContactId());
                String match = String.valueOf(entry.getValue().getMatchContactId());
                String score = String.valueOf(entry.getValue().getScore());
                String category = entry.getValue().getType();

                // Escribir fila
                String[] row = {source, match, score, category};
                csvWriter.writeNext(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
