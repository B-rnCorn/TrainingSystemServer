package web.learning.system.domain;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "solution")
@Entity
@Data
@EqualsAndHashCode(of = { "id" })
public class Solution {

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
