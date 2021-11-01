package web.learning.system.domain;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Table(name = "solution")
@Entity
@Data
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "algorithm")
    private String algorithm;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "created_date")
    @CreatedDate
    private LocalDateTime date;

    @Column(name = "is_send")
    private Boolean isSend;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;
}
