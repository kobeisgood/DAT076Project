package com.ejwa.frontend.model;

import com.github.javafaker.Faker;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import lombok.Getter;

@Singleton
public class BookShelf {
        @Getter
        private Map<String, Book> books;

        @PostConstruct
        private void init() {
                books = new HashMap<>();
                Faker faker = new Faker();

                for (int i = 0; i < 30; i++) {
			final String serialNumber = faker.idNumber().ssnValid();

                        books.put(serialNumber, new Book(faker.book().title(),
				  faker.book().author(), serialNumber, faker.book().genre(),
			          faker.book().publisher()));
                }
        }
}
