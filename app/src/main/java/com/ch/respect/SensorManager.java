package com.ch.respect;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;

public class SensorManager {
    private void getAccel() {
        float[] xValue = new float[100000];
        float[] yValue = new float[100000];
        float[] zValue = new float[100000];

        double[] acceleration = { 0, 0 };
        double[] velocity = { 0, 0 };
        double[] distance = { 0, 0 };
        double deltaDistance = 0.0;
        double finalDistance = 0.0;

        double flag = 0.0;

        int i = 0;

        String value;

        try {
            File file1 = new File("01.txt");
            FileReader filereader = new FileReader(file1);
            BufferedReader bufReader = new BufferedReader(filereader);
            String line;

            i = 0;
            int index;

            while ((line = bufReader.readLine()) != null) {
                // x value
                index = line.indexOf(",");
                value = line.substring(0, index);
                line = line.substring(index + 1);
                xValue[i] = (float) Float.parseFloat(value);

                // y value
                index = line.indexOf(",");
                value = line.substring(0, index);
                line = line.substring(index + 1);
                yValue[i] = (float) Float.parseFloat(value);

                // z value
                zValue[i] = (float) (Float.parseFloat(line));
                zValue[i] = -1 * zValue[i];

                double deltaT = ((double) 20 / 1000);
                double accelerationWindow = 0.01;

                zValue[i] = (float) FilteringWindow(zValue[i], accelerationWindow);

                if (i > 10) { //i는 데이터의 인덱스를 말함
                    if (i == 11) { //맨 처음에 휴대폰 조작하면서 생기는 튀는 값 평균 구해서 뒤에서 값 이용
                        for (int k = 0; k < 10; k++) {
                            flag += zValue[k];
                        }
                        flag = flag / 10.0;
                        acceleration[0] = flag;
                    }

                    else {
                        acceleration[1] = (double) (zValue[i]);

                        acceleration[1] = FilteringWindow(acceleration[1], accelerationWindow);

                        velocity[1] = integral(velocity[0], acceleration[0], acceleration[1], deltaT);
                        distance[1] = integral(distance[0], velocity[0], velocity[1], deltaT);

                        distance[1] = Double.parseDouble(String.format(Locale.KOREA, "%.3f", distance[1]));
                        deltaDistance = distance[1] - distance[0];

                        acceleration[0] = acceleration[1];
                        velocity[0] = velocity[1];
                        distance[0] = distance[1];

                        finalDistance += deltaDistance;

                        System.out.println("Velocity = " + velocity[1]*3.6*3.6);
                    }
                }
                i++;
            }
            bufReader.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {
            System.out.println(e);
        }
        System.out.println("DISTANCE = " + finalDistance);
    }

    private double integral(double baseData, double prevData, double curData, double time) {
        double integratedData = 0;

        integratedData = baseData + (prevData + ((curData - prevData) / 2)) * time;
        return integratedData;
    }

    private double FilteringWindow(double value, double window) { //노이즈로 추정되는 작은 값 0으로 만듦
        if (Math.abs(value) <= window) {
            value = 0;
        }
        return value;
    }

}
