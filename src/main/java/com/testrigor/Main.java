package com.testrigor;

import java.io.IOException;
import java.util.Collection;

public class Main {
    public static void main(String[] args) {
        try {
            Collection<String> peopleNames = Result.collectUniquePeopleNamesFromApiResponse();
            System.out.println(peopleNames);
        } catch (IOException e) {
            System.err.println("Error while fetching data from the API: " + e.getMessage());
            e.printStackTrace();
        }
    }
}