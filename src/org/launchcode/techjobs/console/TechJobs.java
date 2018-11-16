package org.launchcode.techjobs.console;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by LaunchCode
 */
public class TechJobs {

    private static Scanner in = new Scanner(System.in);
    //the main application runner
    public static void main (String[] args) {

        // Initialize our field map with key/name pairs
        HashMap<String, String> columnChoices = new HashMap<>();
        columnChoices.put("core competency", "Skill");
        columnChoices.put("employer", "Employer");
        columnChoices.put("location", "Location");
        columnChoices.put("position type", "Position Type");
        columnChoices.put("all", "All");

        // Top-level menu options- this is the data that is used to generate the first menu
        // we see when running the program
        HashMap<String, String> actionChoices = new HashMap<>();
        actionChoices.put("search", "Search");
        actionChoices.put("list", "List");

        System.out.println("Welcome to LaunchCode's TechJobs App!");

        // Allow the user to search until they manually quit
        while (true) {
            //getUserSelection= utility method, displays a menu of choices and returns the user's selection
            String actionChoice = getUserSelection("View jobs by:", actionChoices);

            if (actionChoice.equals("list")) {
                //getUserSelection= utility method, displays a menu of choices and returns the user's selection
                //declared at the top of main, most of the entries in columnChoices correspond to columns
                //in the jobs data set
                String columnChoice = getUserSelection("List", columnChoices);

                if (columnChoice.equals("all")) {
                    printJobs(JobData.findAll());


            } else {

                ArrayList<String> results = JobData.findAll(columnChoice);

                System.out.println("\n*** All " + columnChoices.get(columnChoice) + " Values ***");

                // Print list of skills, employers, etc
                for (String item : results) {
                    System.out.println(item);
                }
            }

            } else { // choice is "search"
                //getUserSelection= utility method, displays a menu of choices and returns the user's selection
                // How does the user want to search (e.g. by skill or employer)
                String searchField = getUserSelection("Search by:", columnChoices);

                // What is their search term?
                System.out.println("\nSearch term: ");
                String searchTerm = in.nextLine();

                if (searchField.equals("all")) {
                    printJobs(JobData.findByValue(searchTerm));
                } else {
                    printJobs(JobData.findByColumnAndValue(searchField, searchTerm));
                }
            }
        }
    }
    //getUserSelection= utility method, displays a menu of choices and returns the user's selection
    // ﻿Returns the key of the selected item from the choices Dictionary
    private static String getUserSelection(String menuHeader, HashMap<String, String> choices) {

        Integer choiceIdx;
        Boolean validChoice = false;
        String[] choiceKeys = new String[choices.size()];

        // Put the choices in an ordered structure so we can
        // associate an integer with each one
        Integer i = 0;
        for (String choiceKey : choices.keySet()) {
            choiceKeys[i] = choiceKey;
            i++;
        }

        do {

            System.out.println("\n" + menuHeader);

            // Print available choices
            for (Integer j = 0; j < choiceKeys.length; j++) {
                System.out.println("" + j + " - " + choices.get(choiceKeys[j]));
            }

            choiceIdx = in.nextInt();
            in.nextLine();

            // Validate user's input
            if (choiceIdx < 0 || choiceIdx >= choiceKeys.length) {
                System.out.println("Invalid choice. Try again.");
            } else {
                validChoice = true;
            }

        } while(!validChoice);

        return choiceKeys[choiceIdx];
    }

    // Prints a list of jobs to the console
    private static void printJobs(ArrayList<HashMap<String, String>> someJobs) {
        if (someJobs.size() == 0){
            System.out.println("No jobs found");
        }
        for (HashMap<String, String> jobs : someJobs){
            System.out.println("*****");
            for (Map.Entry<String, String> job: jobs.entrySet()){
                System.out.println(job.getKey() + ": " + job.getValue());
            }
            System.out.println("*****");

        }

    }
}
