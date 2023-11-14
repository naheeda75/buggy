package com.testrigor;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Result {
    private static final String API_HOST = "http://20.55.57.43:21001";
    private static final String API_PATH = "/api/v1/people";
    private static final Gson GSON_MAPPER = new Gson();

    public static Collection<String> collectUniquePeopleNamesFromApiResponse() throws IOException {
        String apiRespStr = callPeopleApi();

        Response apiResponse = GSON_MAPPER.fromJson(apiRespStr, Response.class);

        List<Person> peopleList = apiResponse.getData();

        List<String> peopleNames = new ArrayList<>();
        peopleList.forEach(person -> peopleNames.add(person.getName()));

        return peopleNames;
    }

    private static String callPeopleApi() throws IOException {
        URL url = new URL(API_HOST + API_PATH);
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("Accept", "application/json"); // Correct content type

            try (InputStream is = connection.getInputStream()) {
                try (BufferedReader bf = new BufferedReader(new InputStreamReader(is))) {
                    return bf.lines().collect(Collectors.joining("\n"));
                }
            }
        } catch (IOException e) {
            // Log or handle the exception
            e.printStackTrace();
            throw e; // Re-throw the exception to indicate that an error occurred
        } finally {
            if (connection != null) {
                connection.disconnect(); // Close the connection in the finally block
            }
        }
    }

	class Response {
		List<Person> data;

		public List<Person> getData() {
			return data;
		}

		public void setData(List<Person> data) {
			this.data = data;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Response response = (Response) o;
			return Objects.equals(data, response.data);
		}

		@Override
		public int hashCode() {
			return Objects.hash(data);
		}
	}

	class Person {
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		int age;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Person person = (Person) o;
			return age == person.age && Objects.equals(name, person.name);
		}

		@Override
		public int hashCode() {
			return Objects.hash(name, age);
		}
	}
}
