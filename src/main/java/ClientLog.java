import au.com.bytecode.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.cert.CertPathValidatorSpi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClientLog {
    private List <String[]> log = new ArrayList<>();
    public void log(int productNum, int amount) {
        log.add(new String[] {String.valueOf(productNum), String.valueOf(amount)});
    }

    public void exportAsCSV(File txtFile){
        if (!txtFile.exists()){
            log.add(0, new String [] {"productNum, amount"});
        }
        try (CSVWriter csvWriter = new CSVWriter(new FileWriter(txtFile,true))) {
            csvWriter.writeAll(log);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

