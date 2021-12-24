package web.learning.system.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "solution")
@Entity
@Data
@EqualsAndHashCode(of = { "id" })
@NoArgsConstructor
public class Solution {

    public Solution(String algorithm, Integer mark, LocalDateTime date, Boolean isSend, User author, Task task) {
        this.algorithm = algorithm;
        this.mark = mark;
        this.date = date;
        this.isSend = isSend;
        this.author = author;
        this.task = task;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "algorithm")
    private String algorithm;

    @Column(name = "mark")
    private Integer mark;

    @Column(name = "created_date")
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
