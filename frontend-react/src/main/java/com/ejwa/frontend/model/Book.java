package com.ejwa.frontend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Book {
        private String title;
        private String author;
	private String serialNumber;
	private String genre;
	private String publisher;
}
