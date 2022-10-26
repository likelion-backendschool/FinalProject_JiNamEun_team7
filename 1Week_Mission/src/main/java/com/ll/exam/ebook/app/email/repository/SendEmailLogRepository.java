package main.java.com.ll.exam.ebook.app.email.repository;

import com.ll.exam.ebook.app.email.entity.SendEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SendEmailLogRepository extends JpaRepository<SendEmailLog, Long> {
}
