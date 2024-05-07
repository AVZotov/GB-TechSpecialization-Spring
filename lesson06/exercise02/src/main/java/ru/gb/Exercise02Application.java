package ru.gb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//Структура заметки:
//		- ID (автоинкрементное)(тип - Long)
//		- Заголовок (не может быть пустым)(тип - String)
//		- Содержимое (не может быть пустым)(тип - String)
//		- Дата создания (автоматически устанавливается при создании заметки)(тип - LocalDateTime)
//
//Подсказка:
//Репозиторий насладует JpaRepository<Note, Long>. В репозитории добавляем метод Optional<Note> findById(Long id);

@SpringBootApplication
public class Exercise02Application {

	public static void main(String[] args) {
		SpringApplication.run(Exercise02Application.class, args);
	}

}
