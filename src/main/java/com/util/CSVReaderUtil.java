package com.util;

import com.dto.ContactDto;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReaderUtil {

    public static List<ContactDto> readContactsFromCSV(String filePath) {
        List<ContactDto> contacts = new ArrayList<>();

        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] row;

            // Leer la primera línea (asumiendo que es el encabezado)
            csvReader.readNext();

            // Leer cada línea del archivo CSV
            while ((row = csvReader.readNext()) != null) {
                // Crear un nuevo objeto Contact a partir de los valores de la línea
                ContactDto contact = new ContactDto(
                        row[0], // contactId
                        row[1], // name
                        row[2], // name1
                        row[3], // email
                        row[4], // postalZip
                        row[5]  // address
                );

                // Agregar el contacto a la lista
                contacts.add(contact);
            }
        } catch (IOException | CsvException e) {
            e.printStackTrace();
        }

        return contacts;
    }
}
