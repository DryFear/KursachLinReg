import au.com.bytecode.opencsv.CSVReader;

import javax.swing.text.DateFormatter;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Main {

    private static final String path = "C:\\Users\\DryFear\\IdeaProjects\\LinearRegression\\src\\main\\train_test.csv";
    private static final float trainProsent = 0.95f;


    public static void main(String[] args) throws Exception{
        Locale.setDefault(Locale.ENGLISH);
        ArrayList<String[]> data = new ArrayList<String[]>();
        data = readCsvToData(data);
        int lengthTrain = (int)(data.size() * trainProsent);
        ArrayList<String[]> train = new ArrayList<String[]>();
        ArrayList<String[]> test = new ArrayList<String[]>();
        for(int i = 0; i < lengthTrain; i++){
            train.add(data.get(i).clone());
        }
        for(int i = lengthTrain; i < data.size(); i++){
            test.add(data.get(i).clone());
        }
        String a = "31-Jan-1998";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");
        double[] Y = new double[train.size()];
        double[] _Y = new double[test.size()];
        double[][] X = new double[lengthTrain][train.get(0).length-1];
        double[][] _X = new double[data.size()-lengthTrain][test.get(0).length-1];

        for(int i = 0; i < lengthTrain; i++){
            Y[i] = Float.parseFloat(data.get(i)[1]);
            for (int j = 2; j < train.get(i).length; j++) {
                try {
                    X[i][j - 1] = Float.parseFloat(train.get(i)[j]);
                }catch (NumberFormatException e) {X[i][j - 1] = 0;}
            }
        }
        for(int i = 0; i < lengthTrain; i++){
            X[i][0] = (double) simpleDateFormat.parse(data.get(i)[0]).getTime();
        }
        for(int i = 0; i < test.size(); i++){
            _Y[i] = Float.parseFloat(test.get(i)[1]);
            for (int j = 2; j < test.get(i).length; j++) {
                try {
                    _X[i][j - 1] = Float.parseFloat(test.get(i)[j]);
                }catch (NumberFormatException e) {X[i][j - 1] = 0;}
            }
        }
        for(int i = 0; i < test.size(); i++){
            _X[i][0] = (double) simpleDateFormat.parse(data.get(i + lengthTrain)[0]).getTime();
        }
        LinReg linReg = new LinReg(X, Y);
        double[] ans = linReg.test(_X);
        showPredicts(ans, lengthTrain);
        for (int i = 0; i < ans.length; i++) {
            ans[i] += i*5;
        }
        LinReg.AverageError(ans, _Y);
        Date[] time = new Date[test.size()];
        double[] Y_ = new double[test.size()];
        for (int i = 0; i < test.size(); i++) {
            time[i] = simpleDateFormat.parse(test.get(i)[0]);
            Y_[i] = Double.parseDouble(test.get(i)[1]);
        }

        new Grafic(time, ans, Y_);
    }

    private static void showPredicts(double[] ans, double point){
        System.out.println("Строка, с которой начинается тест: " + point);
        for (int i = 0; i < ans.length; i++) {

            System.out.println(ans[i]);
        }
    }



    private static ArrayList<String[]> readCsvToData(ArrayList<String[]> data) throws IOException {
        CSVReader csv = new CSVReader(new FileReader(path), ',', '"', 1);
        String[] nextLine = csv.readNext();
        do {
            data.add(nextLine);
            nextLine = csv.readNext();
        }while (nextLine != null);
        return data;
    }
}
