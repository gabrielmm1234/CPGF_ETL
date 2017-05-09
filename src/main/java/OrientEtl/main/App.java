package OrientEtl.main;

import java.io.FileReader;
import java.io.IOException;

import com.opencsv.CSVReader;

public class App 
{
    public static void main( String[] args ) {
    	String csvFile = "/home/gabriel/Desktop/UnB/9-semestre/BDA/orient/EstudosEm/dados/201601_CPGF.csv";
    	CSVReader reader = null;
    	ServiceReader serviceReader = new ServiceReader();
        try {
        	reader = new CSVReader(new FileReader(csvFile));
            serviceReader.readFileAndBuildGraph(reader);
            System.out.println("Data Transfer finished.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
