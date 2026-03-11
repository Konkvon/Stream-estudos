package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

import models.entities.Employee;

public class App {
    public static void main(String[] args) throws Exception {
        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        List<Employee> list = new ArrayList<>();

        System.out.println("Enter file path: ");
        String path = sc.nextLine();

        try (BufferedReader bf = new BufferedReader(new FileReader(path))) {
            String line = bf.readLine();
            while (line != null) {
                String[] fields = line.split(",");
                String eName = fields[0];
                String eEmail = fields[1];
                Double eSalary = Double.parseDouble(fields[2]);

                Employee e = new Employee(eName, eEmail, eSalary);
                list.add(e);

                line = bf.readLine();
            } 
            
            System.out.println("Type a salary value: ");
            Double v = sc.nextDouble();

            List<String> eSup = list.stream()
                .filter(e -> e.getSalary() > v)
                .map(e -> e.getEmail())
                .sorted()
                .collect(Collectors.toList());
        
            Double mSal = list.stream()
                .filter(e -> e.getName().startsWith("M"))
                .map(e -> e.getSalary())
                .reduce(0.0, (x, y) -> x + y);

            System.out.printf("Email of people whose salary is more than %.2f: %n", v);
            eSup.forEach(System.out::println);

            System.out.printf("Sum of salary of people whose name starts with 'M': %.2f %n", mSal);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
